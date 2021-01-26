package com.towako.vip.membership.mapper;

import com.towako.vip.membership.response.MembershipDto;
import com.towako.vip.membership.response.MembershipRecommendDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author colin
 */
public interface MembershipRecommendMapper {
    @Select("<script>" +
            "select m.member_id as id, c.name as recommend, c.channel \n" +
            "    from vip_wechat_memberships as m \n" +
            "      left join ( \n" +
            "      select concat('DOCTOR_', id) as qr_scene, name, '医生' as channel from chl_doctors \n" +
            "      union \n" +
            "      select concat('FAMILYHOTEL_', id) as qr_scene, name, '家庭旅馆' as channel from chl_family_hotels) as c on m.qr_scene_str=c.qr_scene \n" +
            "    where m.member_id in "+
             "<foreach item='memberId' index='index' collection='memberIds' open='(' separator=',' close=')'>"
            + "#{memberId}"
            + "</foreach>" +
    "</script>")
    List<MembershipRecommendDto> findByMemberIds(@Param("memberIds")List<Long> memberIds);

    @Select("select m.id, m.phone, m.nickname, m.created as createDateTime \n" +
            "from vip_memberships as m \n" +
            "    inner join vip_wechat_memberships as wm on m.id=wm.member_id \n" +
            "    inner join chl_doctors as d on concat('DOCTOR_', d.id)=wm.qr_scene_str and d.id=${doctorId}")
    List<MembershipDto> findByDoctorId(@Param("doctorId")Long doctorId);

    @Select("select m.id, m.phone, m.nickname, m.created as createDateTime \n" +
            "from vip_memberships as m \n" +
            "    inner join vip_wechat_memberships as wm on m.id=wm.member_id \n" +
            "    inner join chl_family_hotels as d on concat('FAMILYHOTEL_', d.id)=wm.qr_scene_str and d.id=${familyHotelId}")
    List<MembershipDto> findByFamilyHotelId(@Param("familyHotelId") Long familyHotelId);
}
