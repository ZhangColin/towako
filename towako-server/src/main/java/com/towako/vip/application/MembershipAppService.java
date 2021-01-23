package com.towako.vip.application;

import com.cartisan.utils.SnowflakeIdWorker;
import com.towako.vip.domain.Gender;
import com.towako.vip.domain.Membership;
import com.towako.vip.domain.WechatMembership;
import com.towako.vip.repositories.MembershipRepository;
import com.towako.vip.repositories.WechatMembershipRepository;
import com.towako.vip.response.MembershipConverter;
import com.towako.vip.response.MembershipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
@Slf4j
public class MembershipAppService {
    private final MembershipConverter membershipConverter = MembershipConverter.CONVERTER;

    private final MembershipRepository membershipRepository;
    private final WechatMembershipRepository wechatMembershipRepository;
    private final SnowflakeIdWorker idWorker;

    public MembershipAppService(MembershipRepository membershipRepository, WechatMembershipRepository wechatMembershipRepository, SnowflakeIdWorker idWorker) {
        this.membershipRepository = membershipRepository;
        this.wechatMembershipRepository = wechatMembershipRepository;
        this.idWorker = idWorker;
    }

    public Optional<MembershipDto> findByOpenId(String appId, String openId) {
        return wechatMembershipRepository.findByAppIdAndOpenId(appId, openId)
                .flatMap(wechatMembership -> membershipRepository.findById(wechatMembership.getMemberId()))
                .map(membershipConverter::convert);
    }

    @Transactional(rollbackOn = Exception.class)
    public MembershipDto registerByWechat(String appId, String openId, String unionId, String phone,
                                           String nickName, String avatarUrl, Integer gender, String city,
                                           String province, String country, String qrSceneStr) {
        return register(appId, openId, unionId, phone, nickName, avatarUrl, gender, city, province, country, qrSceneStr, "Wechat");

    }

    @Transactional(rollbackOn = Exception.class)
    public MembershipDto registerByMiniApp(String appId, String openId, String unionId, String phone,
                                           String nickName, String avatarUrl, Integer gender, String city,
                                           String province, String country) {
        return register(appId, openId, unionId, phone, nickName, avatarUrl, gender, city, province, country, "", "MiniApp");

    }

    private MembershipDto register(String appId, String openId, String unionId, String phone,
                                   String nickName, String avatarUrl, Integer gender, String city,
                                   String province, String country, String qrSceneStr, String source) {
        final List<WechatMembership> wechatMemberships = wechatMembershipRepository.findByAppIdAndUnionId(appId, unionId);

        if (wechatMemberships.size() > 0) {
            final Long memberId = wechatMemberships.get(0).getMemberId();

            final WechatMembership wechatMembership = new WechatMembership(memberId, appId, openId, unionId, qrSceneStr, source);

            wechatMembershipRepository.save(wechatMembership);

            return membershipConverter.convert(requirePresent(membershipRepository.findById(memberId)));
        } else {
            final Long membershipId = idWorker.nextId();
            final WechatMembership wechatMembership = new WechatMembership(membershipId, appId, openId, unionId, qrSceneStr, source);
            wechatMembershipRepository.save(wechatMembership);

            final Membership membership = Membership.createByWechat(membershipId, phone, nickName, avatarUrl,
                    Gender.getInstance(gender), null, city, province, country);
            membershipRepository.save(membership);

            return membershipConverter.convert(membership);
        }
    }

    public void recordLogin(Long userId) {
        final Membership user = requirePresent(membershipRepository.findById(userId));

        user.recordLogin("");

        membershipRepository.save(user);
    }
}
