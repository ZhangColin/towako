-- 菜单
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (17, 0, '生殖辅助', 'assisted-reproduction', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (18, 17, '病历管理', 'medical-record', 'component', 0, 10);

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (7, 17);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (7, 18);

-- 资源分类
INSERT INTO `sys_resource_categories`(`id`, `name`, `sort`) VALUES (5, '生殖辅助', 30);

-- 资源
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (13, 5, '病历管理', 'assisted-reproduction:medical-record', '/assisted-reproduction/medical-records', 10);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (7, 13);
