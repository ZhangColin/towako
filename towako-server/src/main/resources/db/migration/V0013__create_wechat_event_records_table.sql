-- 微信事件记录表
CREATE TABLE `vip_wechat_event_records` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `qr_scene_str` varchar(255) NOT NULL DEFAULT '' COMMENT '扫码场景值',
    `open_id` varchar(255) NOT NULL DEFAULT ''  COMMENT '用户open_id',
    `event` varchar(255) NOT NULL DEFAULT ''  COMMENT '事件',
    `event_date` datetime NULL COMMENT '事件日期',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信事件记录表';

insert into `vip_wechat_event_records` (`qr_scene_str`, `open_id`, `event`, `event_date`)
select qr_scene_str, open_id,  event, created
from tfc_scan_qr_code_records
