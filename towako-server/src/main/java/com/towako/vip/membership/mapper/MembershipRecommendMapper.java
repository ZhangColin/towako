package com.towako.vip.membership.mapper;

import com.towako.vip.membership.response.MembershipRecommendDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author colin
 */
public interface MembershipRecommendMapper {
    @Select("<script>" +
            "select r.member_id as id, c.name as recommend, c.type as channel\n" +
            "from tfc_recommends as r\n" +
            "    left join tfc_channels c on r.channel_id=c.id and r.member_id in "+
             "<foreach item='memberId' index='index' collection='memberIds' open='(' separator=',' close=')'>"
            + "#{memberId}"
            + "</foreach>" +
    "</script>")
    List<MembershipRecommendDto> findByMemberIds(@Param("memberIds")List<Long> memberIds);

    @Select("select phone from vip_memberships\n" +
            "where phone<>'' and active=true\n" +
            "group by phone\n" +
            "having count(phone)>=2")
    List<String> findDuplicatePhone();

    @Update("update vip_memberships set created=#{createDateTime, jdbcType=TIMESTAMP} where id=#{memberId}")
    void updateMembershipCreateDate(@Param("memberId") Long memberId, @Param("createDateTime") LocalDateTime createDateTime);
}
