-- 修改原角色"医生->医生渠道"
UPDATE `sys_roles` SET `name`='医生渠道' WHERE `id`=3;

-- 添加医生角色
INSERT INTO `sys_roles`(`id`, `name`) VALUES (7, '医生');

-- 菜单
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (14, 0, '医院医生', 'hospital-doctors', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (15, 14, '医生管理', 'doctor', 'component', 0, 10);
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (16, 14, '医院管理', 'hospital', 'component', 0, 20);

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 14);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 15);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (1, 16);

-- 资源分类
INSERT INTO `sys_resource_categories`(`id`, `name`, `sort`) VALUES (4, '医院医生', 20);

-- 资源
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (11, 4, '医生管理', 'hospital-doctors:doctors', '/hospital-doctors/doctors', 10);
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (12, 4, '医院管理', 'hospital-doctors:hospitals', '/hospital-doctors/hospitals', 20);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 11);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (1, 12);
