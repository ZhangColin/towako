-- 医生表
CREATE TABLE `chl_doctors` (
  `id` bigint NOT NULL COMMENT '用户Id',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0：禁用  1：启用 ）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生表';

-- 家庭旅馆表
CREATE TABLE `chl_family_hotels` (
  `id` bigint NOT NULL COMMENT '用户Id',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0：禁用  1：启用 ）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家庭旅馆表';

-- 微信带参二维码表
CREATE TABLE `chl_wechat_qr_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_id` bigint NULL COMMENT '渠道Id',
  `channel_type` varchar(32) NOT NULL COMMENT '渠道类型（DOCTOR、FAMILY_HOTEL）',
  `qr_scene_str` varchar(64) NOT NULL DEFAULT '' COMMENT '扫码场景值',
  `image_url` varchar(255) NOT NULL DEFAULT ''  COMMENT '二维码图片地址',
  `ticket` varchar(255) NOT NULL DEFAULT ''  COMMENT '获取二维码ticket',
  `expire_seconds` bigint NULL COMMENT '过期时间',
  `url` varchar(255) NOT NULL DEFAULT ''  COMMENT '二维码图片解析后的地址',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_wechat_qr_code_channel_id_channel_type`(`channel_id`, `channel_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信带参二维码表';

-- 扫码记录表
CREATE TABLE `chl_scan_qr_code_records` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_id` bigint NULL COMMENT '渠道Id',
  `channel_type` varchar(32) NOT NULL COMMENT '渠道类型（DOCTOR、FAMILY_HOTEL）',
  `qr_scene_str` varchar(255) NOT NULL DEFAULT '' COMMENT '扫码场景值',
  `open_id` varchar(255) NOT NULL DEFAULT ''  COMMENT '用户open_id',
  `event` varchar(255) NOT NULL DEFAULT ''  COMMENT '事件',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_scan_qr_code_record_channel_id_channel_type`(`channel_id`, `channel_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='扫码记录表';
