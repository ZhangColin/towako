-- 渠道推荐表
CREATE TABLE `tfc_recommends` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_id` bigint NULL COMMENT '渠道Id',
  `channel_type` varchar(32) NOT NULL COMMENT '渠道类型（DOCTOR、FAMILY_HOTEL）',
  `member_id` bigint NULL COMMENT '会员Id',
  `nickname` varchar(32) NOT NULL COMMENT '昵称',
  `recommend_date` datetime NULL COMMENT '推荐时间',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_recommend_channel_id`(`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道推荐表';

insert into tfc_recommends(`channel_id`, `channel_type`, `member_id`, `nickname`, `recommend_date`)
select r.channel_id, r.channel_type, vwm.member_id, vm.nickname, r.created
from tfc_scan_qr_code_records as r
    inner join vip_wechat_memberships vwm on vwm.open_id=r.open_id
    inner join vip_memberships vm on vwm.member_id = vm.id
where r.event='subscribe';
