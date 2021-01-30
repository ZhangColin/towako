package com.towako.vip.membership.mapper;

import com.towako.vip.membership.response.MembershipRecommendDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author colin
 */
public interface MembershipRecommendMapper {
    @Select("<script>" +
            "select m.member_id as id, c.name as recommend, c.type as channel \n" +
            "from vip_wechat_memberships as m \n" +
            "    left join tfc_channels as c on concat(c.type, '_', c.id)=m.qr_scene_str \n"+
            "    where m.member_id in "+
             "<foreach item='memberId' index='index' collection='memberIds' open='(' separator=',' close=')'>"
            + "#{memberId}"
            + "</foreach>" +
    "</script>")
    List<MembershipRecommendDto> findByMemberIds(@Param("memberIds")List<Long> memberIds);

}
