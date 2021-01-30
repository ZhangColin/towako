-- 菜单
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (8, 0, '渠道管理', 'traffic', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (9, 8, '医生管理', 'doctor', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (10, 8, '家庭旅馆管理', 'family-hotel', 'component', 0, 20);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (11, 0, '会员管理', 'vip', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (12, 11, '会员信息', 'membership', 'component', 0, 10);

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 8);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 9);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 10);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 11);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 12);

-- 资源分类
INSERT INTO `sys_resource_categories`(`id`, `name`, `sort`) VALUES (2, '渠道管理', 10);
INSERT INTO `sys_resource_categories`(`id`, `name`, `sort`) VALUES (3, '会员管理', 10);

-- 资源
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (7, 2, '医生管理', 'channel:doctors', '/channel/doctors', 10);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (8, 2, '家庭旅馆管理', 'channel:familyhotels', '/channel/familyhotels', 20);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (9, 3, '会员信息', 'vip:memberships', '/vip/memberships', 10);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 7);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 8);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 9);
