-- 渠道表
CREATE TABLE `chl_channels` (
    `id` bigint NOT NULL COMMENT '渠道Id',
    `user_id` bigint NULL COMMENT '系统用户Id',
    `name` varchar(128) NOT NULL COMMENT '名称',
    `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '电话',
    `type` varchar(32) NOT NULL COMMENT '渠道类型（DOCTOR、FAMILY_HOTEL）',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0：禁用  1：启用 ）',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道表';

-- 从原医生表中插入渠道数据
INSERT INTO `chl_channels`(`id`, `name`, `type`)
SELECT `id`, `name`, 'DOCTOR' AS `type`
FROM `chl_doctors`;

-- 从原家庭旅馆表中插入渠道数据
INSERT INTO `chl_channels`(`id`, `name`, `type`)
SELECT `id`, `name`, 'FAMILY_HOTEL' AS `type`
FROM `chl_family_hotels`;

-- 删除医生表
DROP TABLE `chl_doctors`;

-- 删除家庭旅馆表
DROP TABLE `chl_family_hotels`;

-- 添加外部用户的组织
INSERT INTO `sys_organizations`
(`id`, `parent_id`, `name`, `type`, `code`, `description`, `enabled`, `sort`)
VALUES
(1377345482606645249, 0, '外部用户', 1, 'ExternalUsers', '外部用户', 1, 99);

-- 添加渠道相关角色
INSERT INTO `sys_roles`(`id`, `name`) VALUES (3, '医生');
INSERT INTO `sys_roles`(`id`, `name`) VALUES (4, '家庭旅馆');
INSERT INTO `sys_roles`(`id`, `name`) VALUES (5, '其它渠道');
