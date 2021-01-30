-- 修改表名
rename table chl_wechat_qr_codes to tfc_wechat_qr_codes;
rename table chl_scan_qr_code_records to tfc_scan_qr_code_records;

-- 渠道表
CREATE TABLE `tfc_channels` (
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
INSERT INTO `tfc_channels`(`id`, `name`, `type`)
SELECT `id`, `name`, 'DOCTOR' AS `type`
FROM `chl_doctors`;

-- 从原家庭旅馆表中插入渠道数据
INSERT INTO `tfc_channels`(`id`, `name`, `type`)
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


-- 菜单
DELETE FROM `sys_menus` WHERE `id`=10;
UPDATE `sys_menus` SET `title`='流量中心', `name`='traffic' WHERE `id`=8;
UPDATE `sys_menus` SET `title`='渠道管理', `name`='channel' WHERE `id`=9;

-- 角色菜单
DELETE FROM `sys_role_menus` WHERE `menu_id`=10;

-- 资源分类
UPDATE `sys_resource_categories` set `name`='流量中心' WHERE `id`=2;

-- 资源
UPDATE `sys_resources` SET `name`='渠道管理', `code`='traffic:channels', `url`='/traffic/channels' WHERE `id`=7;
DELETE FROM `sys_resources` WHERE `id`=8;

-- 角色资源
DELETE FROM `sys_role_resources` WHERE `resource_id`=8;
