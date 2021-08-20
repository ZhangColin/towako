-- 添加病历管理员角色
INSERT INTO `sys_roles`(`id`, `name`) VALUES (8, '病历管理员');

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (8, 14);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (8, 15);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (8, 16);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (8, 17);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (8, 18);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (8, 11);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (8, 12);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (8, 13);

-- 默认病历管理员角色
INSERT INTO `sys_user_roles`(`user_id`, `role_id`) VALUES (1265586318612762624, 8); -- admin
INSERT INTO `sys_user_roles`(`user_id`, `role_id`) VALUES (1507569684071976960, 8); -- 赵元章
INSERT INTO `sys_user_roles`(`user_id`, `role_id`) VALUES (1500353679843266560, 8); -- colin


