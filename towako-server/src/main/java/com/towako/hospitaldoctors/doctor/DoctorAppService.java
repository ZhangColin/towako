package com.towako.hospitaldoctors.doctor;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.AesUtil;
import com.cartisan.utils.SnowflakeIdWorker;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.towako.hospitaldoctors.doctor.mapper.DoctorQueryMapper;
import com.towako.hospitaldoctors.hospital.Hospital;
import com.towako.hospitaldoctors.hospital.HospitalDto;
import com.towako.hospitaldoctors.hospital.HospitalRepository;
import com.towako.security.CurrentUser;
import com.towako.system.role.response.RoleDto;
import com.towako.system.user.application.UserAppService;
import com.towako.system.user.request.AssignRolesCommand;
import com.towako.system.user.request.CreateAccountCommand;
import com.towako.system.user.response.UserDetailDto;
import com.towako.traffic.channel.Channel;
import com.towako.traffic.channel.ChannelType;
import com.towako.traffic.channel.request.RegisterChannelCommand;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.utils.AssertionUtil.requirePresent;
import static com.google.common.primitives.Longs.asList;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class DoctorAppService {
    public static final String ERR_PHONE_EXISTS = "医生手机号已存在。";

    private final DoctorRepository repository;
    private final HospitalRepository hospitalRepository;
    private final UserAppService userAppService;
    private final DoctorQueryMapper doctorQueryMapper;
    private final CurrentUser currentUser;
    private final SnowflakeIdWorker idWorker;
    private final ValueOperations<String, String> valueOperations;

    private final DoctorConverter converter = DoctorConverter.CONVERTER;

    public DoctorAppService(DoctorRepository repository, HospitalRepository hospitalRepository,
                            UserAppService userAppService, DoctorQueryMapper doctorQueryMapper,
                            ValueOperations<String, String> valueOperations,
                            CurrentUser currentUser, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.hospitalRepository = hospitalRepository;
        this.userAppService = userAppService;
        this.doctorQueryMapper = doctorQueryMapper;
        this.valueOperations = valueOperations;
        this.currentUser = currentUser;
        this.idWorker = idWorker;
    }

    public PageResult<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery, @NonNull Pageable pageable) {
        final Page<Doctor> searchResult = doctorQuery.getHospitalId() != null ?
                repository.findByIdIn(doctorQueryMapper.getDoctorIdsByHospitalId(doctorQuery.getHospitalId()),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())) :
                repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }

    public List<DoctorDto> searchDoctors(@NonNull DoctorQuery doctorQuery) {
        final List<Doctor> searchResult = doctorQuery.getHospitalId() != null ?
                repository.findByIdIn(doctorQueryMapper.getDoctorIdsByHospitalId(doctorQuery.getHospitalId())) :
                repository.findAll();

        return converter.convert(searchResult);
    }

    public DoctorDto getDoctor(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    public DoctorDto getDoctorByCurrentUser() {
        return converter.convert(requirePresent(repository.findByUserId(currentUser.getUserId())));
    }

    public List<Long> getHospitals(Long id) {
        final Doctor doctor = requirePresent(repository.findById(id));

        return doctor.getHospitals().stream().map(DoctorHospital::getHospitalId).collect(toList());
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto addDoctor(DoctorParam doctorParam) {
        if (repository.existsByPhone(doctorParam.getPhone())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_PHONE_EXISTS));
        }

        final Optional<UserDetailDto> userDetailDtoOptional = userAppService.getUserByPhone(doctorParam.getPhone());

        Long userId = 0L;
        if (userDetailDtoOptional.isPresent()) {
            final AssignRolesCommand command = new AssignRolesCommand();
            final UserDetailDto userDetailDto = userDetailDtoOptional.get();

            final List<Long> roleIds = userDetailDto.getRoles().stream().map(RoleDto::getId).collect(toList());
            roleIds.add(7L);
            command.setRoleIds(roleIds);

            userAppService.assignRoles(userDetailDto.getId(), command);

            userId = userDetailDto.getId();
        } else {
            final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
            createAccountCommand.setUsername(doctorParam.getPhone());
            createAccountCommand.setPhone(doctorParam.getPhone());
            createAccountCommand.setRealName(doctorParam.getName());
            createAccountCommand.setRoleIds(asList(7L));
            final UserDetailDto account = userAppService.createAccount(createAccountCommand);

            userId = account.getId();
        }

        final Doctor doctor = new Doctor(idWorker.nextId(),
                userId,
                doctorParam.getName(),
                doctorParam.getPhone(),
                doctorParam.getTitle());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public DoctorDto editDoctor(Long id, DoctorParam doctorParam) {
        final Doctor doctor = requirePresent(repository.findById(id));

        doctor.describe(
                doctorParam.getName(),
                doctorParam.getTitle());

        return converter.convert(repository.save(doctor));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDoctor(long id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignHospitals(Long id, List<Long> hospitalIds) {
        final Doctor doctor = requirePresent(repository.findById(id));

        final List<Long> ensureHospitalIds = hospitalRepository.findByIdIn(hospitalIds)
                .stream().map(Hospital::getId).collect(toList());

        doctor.assignHospitals(ensureHospitalIds);
        repository.save(doctor);
    }

    public List<HospitalDto> getDoctorHospitals(Long doctorId) {
        return doctorQueryMapper.getDoctorHospitals(doctorId);
    }

    public List<HospitalDto> getMyHospitals() {
        return doctorQueryMapper.getHospitalsByUserId(currentUser.getUserId());
    }

    @Transactional(rollbackOn = Exception.class)
    public void registerDoctor(RegisterDoctorCommand command) {
        final String code = valueOperations.get(command.getPhone());

        if (code==null || !code.equals(command.getCode())){
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("验证码不正确。"));
        }

        if (repository.existsByPhone(command.getPhone())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_PHONE_EXISTS));
        }

        if (!hospitalRepository.findById(command.getHospitalId()).isPresent()){
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("医院不存在。"));
        }

        final Optional<UserDetailDto> userDetailDtoOptional = userAppService.getUserByPhone(command.getPhone());

        Long userId = 0L;
        if (userDetailDtoOptional.isPresent()) {
            final AssignRolesCommand assignRolesCommand = new AssignRolesCommand();
            final UserDetailDto userDetailDto = userDetailDtoOptional.get();

            final List<Long> roleIds = userDetailDto.getRoles().stream().map(RoleDto::getId).collect(toList());
            roleIds.add(7L);
            assignRolesCommand.setRoleIds(roleIds);

            userAppService.assignRoles(userDetailDto.getId(), assignRolesCommand);

            userId = userDetailDto.getId();
        } else {
            final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
            createAccountCommand.setUsername(command.getPhone());
            createAccountCommand.setPhone(command.getPhone());
            createAccountCommand.setRealName(command.getName());
            createAccountCommand.setRoleIds(asList(7L));
            final UserDetailDto account = userAppService.createAccount(createAccountCommand);

            userId = account.getId();
        }

        userAppService.changePassword(userId, command.getPassword());

        final Doctor doctor = new Doctor(idWorker.nextId(),
                userId,
                command.getName(),
                command.getPhone(),
                command.getTitle());

        doctor.assignHospitals(asList(command.getHospitalId()));
        repository.save(doctor);

        sendSuccessSms(command.getPhone());

        try {
            final String content = buildContent(StringUtils.isEmpty(command.getEmail()) ? "" : command.getEmail(),
                    command.getName(), command.getPhone(), AesUtil.aesDecode(command.getPassword()));

            if (!StringUtils.isEmpty(command.getEmail())){
                sendEmail(command.getEmail(), command.getName(), content);
            }
            sendEmail("customer.service@lanmedical.com", command.getName(), content);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    private void sendEmail(String email, String name, String content) throws Exception {
//        MimeMessage message = createMimeMessage("service@lanmedical.com", email, name, content);
//
//        javaMailSender.send(message);
    }


    private MimeMessage createMimeMessage(String sendMail, String receiveMail, String name, String content) throws Exception {
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        message.setFrom(new InternetAddress(sendMail, name+"_优生慧推广渠道注册成功", "UTF-8"));
//        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, name, "UTF-8"));
//
//        message.setSubject("感谢您加入优生慧推广渠道！", "UTF-8");
//
//        MimeBodyPart text = new MimeBodyPart();
//        text.setContent(content, "text/html;charset=UTF-8");
//
//        MimeMultipart mm = new MimeMultipart();
//        mm.addBodyPart(text);
//
//        message.setContent(mm);
//        message.saveChanges();
//
//        return message;
        return null;
    }


    private String buildContent(String email, String name, String phone, String password){
        return "<table cellpadding=\"0\" align=\"center\"\n" +
                "    style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <th valign=\"middle\"\n" +
                "                style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">\n" +
                "                <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">注册成功! （优生慧）</font>\n" +
                "            </th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <div style=\"padding:25px 35px 40px; background-color:#fff;\">\n" +
                "                    <h2 style=\"margin: 5px 0px; \">\n" +
                "                        <font color=\"#333333\" style=\"line-height: 20px; \">\n" +
                "                            <font style=\"line-height: 22px; \" size=\"4\">亲爱的"+name+"</font>\n" +
                "                        </font>\n" +
                "                    </h2>\n" +
                "                    <p>感谢您加入优生慧推广渠道！</p>\n" +
                "                    <p>下面是您的账号信息：</p>\n" +
                "                    <p>登录地址：<a href=\"http://channel-h5.lanmedical.com/\" target=\"_blank\">http://channel-h5.lanmedical.com/</a></p>\n" +
                "                    <p>账号：<b>"+phone+"</b><br>密码：<b>"+password+"</b><br>邮箱：<b>"+email+"</b><br><br></p>\n" +
                "                    <p><a href=\"http://channel-h5.lanmedical.com/1615646028591233.mp4\" target=\"_blank\">http://channel-h5.lanmedical.com/1615646028591233.mp4</a>\n" +
                "                    <br/><a href=\"http://channel-h5.lanmedical.com/1615646028591233.mp4\" target=\"_blank\">所附链接为优生慧推广渠道操作指引视频，请按照演示视频指引进行操作，祝您一切顺利，日进斗金！</a></p>\n" +
                "                    <p>当您在使用本网站时，遵守当地法律法规。<br>如果您有什么疑问可以联系管理员，Email: <a href=\"mailto:customer.service@lanmedical.com\"\n" +
                "                            target=\"_blank\">customer.service@lanmedical.com</a></p>\n" +
                "                    <p align=\"right\">优生慧</p><br>\n" +
                "                    <div\n" +
                "                        style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                "                        <p>此为系统邮件，请勿直接回复！<br>请保管好您的邮箱地址，避免账号被他人盗用</p>\n" +
                "                        <p>优生慧©2021</p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>";
    }

    private void sendSuccessSms(String phone) {
        try {
            Credential cred = new Credential("AKIDLbgsjNrGlmbXFjKSilbEfs6NSLHxuccP", "vTFduoW45EsrYIuWDjTCiZEmdCfw7221");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "",clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String appid = "1400486413";
            req.setSmsSdkAppid(appid);
            String sign = "优生慧";
            req.setSign(sign);
            String templateID = "1046737";
            req.setTemplateID(templateID);

            String[] phoneNumbers = {"+86"+ phone};
            req.setPhoneNumberSet(phoneNumbers);

            SendSmsResponse res = client.SendSms(req);
            log.info("腾讯短信接口返回结果：[{}]", SendSmsResponse.toJsonString(res) );
        } catch (TencentCloudSDKException e) {
            log.error("腾讯短信接口调用失败：[{}]", e.getMessage());
        }
    }
}
