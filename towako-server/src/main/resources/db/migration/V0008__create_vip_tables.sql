-- 会员表
CREATE TABLE `vip_memberships` (
  `id` bigint NOT NULL COMMENT '会员Id',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `nickname` varchar(32) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `gender` tinyint NOT NULL DEFAULT 1 COMMENT '性别（0:未知 1：男 2：女）',
  `birthday` date COMMENT '生日',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `province` varchar(32) NOT NULL COMMENT '省份',
  `country` varchar(32) NOT NULL COMMENT '国家',
  `last_login_time` datetime NULL COMMENT '最近一次登录时间',
  `last_login_ip` varchar(64)  NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(1：正常  2：禁用  3：注销 ）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 微信会员信息表
CREATE TABLE `vip_wechat_memberships` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint NULL COMMENT '会员Id',
  `app_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'appId',
  `open_id` varchar(32) NOT NULL DEFAULT '' COMMENT '微信openid',
  `union_id` varchar(128) NOT NULL DEFAULT '' COMMENT '微信unionid',
  `subscribe_time` datetime NULL COMMENT '关注时间',
  `subscribe` tinyint unsigned NULL COMMENT '是否关注',
  `subscribe_scene` varchar(64) NOT NULL DEFAULT '' COMMENT '关注场景',
  `qr_scene_str` varchar(64) NOT NULL DEFAULT '' COMMENT '扫码场景值',
  `remark` varchar(255) NOT NULL DEFAULT ''  COMMENT '备注',
  `source` varchar(32) NOT NULL COMMENT '来源（公众号、小程序、公众平台）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_wechat_membership_member_id`(`member_id`),
  INDEX `index_wechat_membership_union_id`(`union_id`),
  INDEX `index_wechat_membership_open_id`(`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信用户信息表';





