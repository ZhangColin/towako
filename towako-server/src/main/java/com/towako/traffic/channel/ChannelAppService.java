package com.towako.traffic.channel;

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
import com.towako.system.user.application.UserAppService;
import com.towako.system.user.request.CreateAccountCommand;
import com.towako.system.user.response.UserDetailDto;
import com.towako.traffic.channel.request.ChannelParam;
import com.towako.traffic.channel.request.ChannelQuery;
import com.towako.traffic.channel.request.RegisterChannelCommand;
import com.towako.traffic.channel.response.ChannelBaseInfoConverter;
import com.towako.traffic.channel.response.ChannelBaseInfoDto;
import com.towako.traffic.channel.response.ChannelConverter;
import com.towako.traffic.channel.response.ChannelDto;
import com.towako.traffic.recommend.RecommendAppService;
import com.towako.traffic.wechatqrcode.WeChatQrCodeAppService;
import com.towako.traffic.wechatqrcode.response.WeChatQrCodeDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.responses.ResponseUtil.success;
import static com.cartisan.utils.AssertionUtil.requirePresent;
import static com.google.common.primitives.Longs.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Service
@Slf4j
public class ChannelAppService {
    public static final String ERR_NAME_EXISTS = "渠道已存在。";

    private final ChannelRepository repository;
    private final WeChatQrCodeAppService weChatQrCodeAppService;
    private final SnowflakeIdWorker idWorker;
    private final UserAppService userAppService;
    private final RecommendAppService recommendAppService;
    private final ValueOperations<String, String> valueOperations;

    private final ChannelConverter channelConverter = ChannelConverter.CONVERTER;

    public ChannelAppService(ChannelRepository repository, WeChatQrCodeAppService weChatQrCodeAppService,
                             UserAppService userAppService, RecommendAppService recommendAppService,
                             SnowflakeIdWorker idWorker, ValueOperations<String, String> valueOperations) {
        this.repository = repository;
        this.weChatQrCodeAppService = weChatQrCodeAppService;
        this.idWorker = idWorker;
        this.userAppService = userAppService;
        this.recommendAppService = recommendAppService;
        this.valueOperations = valueOperations;
    }

    public PageResult<ChannelDto> searchChannels(@NonNull ChannelQuery channelQuery, @NonNull Pageable pageable) {
        final Page<Channel> searchResult = repository.findAll(querySpecification(channelQuery),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        final List<ChannelDto> channels = channelConverter.convert(searchResult.getContent());

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(
                channels.stream().map(ChannelDto::getId).collect(toList()));

        channels.forEach(channelDto -> qrCodeDtos.stream()
                .filter(qrCodeDto -> qrCodeDto.getChannelId().equals(channelDto.getId()))
                .findFirst().ifPresent(qrCodeDto -> {
                    channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

                    channelDto.setTicket(qrCodeDto.getTicket());
                    channelDto.setImageUrl(qrCodeDto.getImageUrl());
                    channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
                    channelDto.setUrl(qrCodeDto.getUrl());
                }));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                channels);
    }

    public ChannelDto getByChannelId(Long channelId){
        final ChannelDto channelDto = channelConverter.convert(requirePresent(repository.findById(channelId)));

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(asList(channelId));
        qrCodeDtos.stream().findFirst().ifPresent(qrCodeDto -> {
            channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

            channelDto.setTicket(qrCodeDto.getTicket());
            channelDto.setImageUrl(qrCodeDto.getImageUrl());
            channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            channelDto.setUrl(qrCodeDto.getUrl());
        });

        return channelDto;
    }

    public List<ChannelBaseInfoDto> findAllEffectiveChannels() {
        return ChannelBaseInfoConverter.CONVERTER.convert(repository.findByStatus(1));
    }

    public Optional<ChannelDto> findByUserId(Long userId) {
        return repository.findByUserId(userId).map(channelConverter::convert);
    }

    public Optional<ChannelDto> findById(Long channelId) {
        return repository.findById(channelId).map(channelConverter::convert);
    }


    public ChannelDto getChannelByUserId(Long userId) {
        final ChannelDto channelDto = channelConverter.convert(requirePresent(repository.findByUserId(userId)));

        final List<WeChatQrCodeDto> qrCodeDtos = weChatQrCodeAppService.findByChannelIds(asList(channelDto.getId()));

        channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

        qrCodeDtos.stream().filter(qrCodeDto -> qrCodeDto.getChannelId().equals(channelDto.getId()))
                .findFirst().ifPresent(qrCodeDto -> {
            channelDto.setRecommends(recommendAppService.getRecommendCount(channelDto.getId()));

            channelDto.setTicket(qrCodeDto.getTicket());
            channelDto.setImageUrl(qrCodeDto.getImageUrl());
            channelDto.setExpireSeconds(qrCodeDto.getExpireSeconds());
            channelDto.setUrl(qrCodeDto.getUrl());
        });
        return channelDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public void registerChannel(RegisterChannelCommand command) {
        final String code = valueOperations.get(command.getPhone());

        if (code==null || !code.equals(command.getCode())){
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("验证码不正确。"));
        }

        if (repository.existsByName(command.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        if (!repository.findById(command.getParentId()).isPresent()){
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("推荐渠道不存在。"));
        }

        final long channelId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(channelId);

        final UserDetailDto account = createUserAccount(command.getName(), command.getPhone(), command.getEmail(), ChannelType.OTHER);

        userAppService.changePassword(account.getId(), command.getPassword());

        final Channel channel = new Channel(channelId,
                command.getParentId(),
                account.getId(), command.getName(), command.getPhone(),
                ChannelType.OTHER);
        repository.save(channel);

        sendSuccessSms(channel.getPhone());

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
        final Session session = initSession();
        MimeMessage message = createMimeMessage(session, "service@lanmedical.com", email, name, content);

        Transport transport = session.getTransport();
        transport.connect("service@lanmedical.com", "LqhqzBbUKckqem2n");

        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

    private Session initSession(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("account", "password");
            }
        });
        session.setDebug(true);

        return session;
    }

    private MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String name, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, name+"_优生慧推广渠道注册成功", "UTF-8"));
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, name, "UTF-8"));

        message.setSubject("感谢您加入优生慧推广渠道！", "UTF-8");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);

        message.setContent(mm);
        message.saveChanges();

        return message;
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
            String templateID = "880371";
            req.setTemplateID(templateID);

            String[] phoneNumbers = {"+86"+ phone};
            req.setPhoneNumberSet(phoneNumbers);

            SendSmsResponse res = client.SendSms(req);
            log.info("腾讯短信接口返回结果：[{}]", SendSmsResponse.toJsonString(res) );
        } catch (TencentCloudSDKException e) {
            log.error("腾讯短信接口调用失败：[{}]", e.getMessage());
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void addChannel(ChannelParam channelParam) {
        if (repository.existsByName(channelParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final long channelId = idWorker.nextId();

        weChatQrCodeAppService.applyWechatQrCode(channelId);

        final UserDetailDto account = createUserAccount(channelParam.getName(), channelParam.getPhone(), "", channelParam.getType());

        final Channel channel = new Channel(channelId,
                Optional.ofNullable(channelParam.getParentId()).orElse(0L),
                account.getId(), channelParam.getName(), channelParam.getPhone(),
                channelParam.getType());
        repository.save(channel);
    }

    private UserDetailDto createUserAccount(String name, String phone, String email, String type) {
        final CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setUsername(phone);
        createAccountCommand.setPhone(phone);
        createAccountCommand.setRealName(name);
        createAccountCommand.setEmail(email);
        createAccountCommand.setOrganizationIds(asList(1377345482606645249L));
        Long roleId = 0L;
        if (type.equals(ChannelType.DOCTOR)) {
            roleId = 3L;
        } else if (type.equals(ChannelType.FAMILY_HOTEL)) {
            roleId = 4L;
        } else if (type.equals(ChannelType.OTHER)) {
            roleId = 5L;
        } else {
            throw new CartisanException(CodeMessage.FAIL.fillArgs("渠道类型不正确"));
        }
        createAccountCommand.setRoleIds(asList(roleId));
        final UserDetailDto account = userAppService.createAccount(createAccountCommand);
        return account;
    }

    @Transactional(rollbackOn = Exception.class)
    public void createAccount(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        if (channel.getUserId() == null) {
            final UserDetailDto userAccount = createUserAccount(channel.getName(), channel.getPhone(), "", channel.getType());
            channel.setUserId(userAccount.getId());

            repository.save(channel);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void editChannel(Long id, ChannelParam channelParam) {
        if (repository.existsByNameAndIdNot(channelParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }
        final Channel channel = requirePresent(repository.findById(id));

        channel.describe(Optional.ofNullable(channelParam.getParentId()).orElse(0L),
                channelParam.getName(), channelParam.getPhone(), channelParam.getType());

        repository.save(channel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeChannel(long id) {
        repository.deleteById(id);
    }


    @Transactional(rollbackOn = Exception.class)
    public void enable(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        channel.enable();
        repository.save(channel);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long id) {
        final Channel channel = requirePresent(repository.findById(id));
        channel.disable();
        repository.save(channel);
    }
}
