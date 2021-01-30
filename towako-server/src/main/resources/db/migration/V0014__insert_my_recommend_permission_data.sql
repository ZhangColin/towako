-- 菜单
INSERT INTO `sys_menus`(`id`, `parent_id`, `title`, `name`, `icon`, `hidden`, `sort`) VALUES (13, 8, '我的推荐', 'my-recommend', 'component', 0, 20);

-- 角色菜单
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (3, 8);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (4, 8);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (5, 8);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (3, 13);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (4, 13);
INSERT INTO `sys_role_menus`(`role_id`, `menu_id`) VALUES (5, 13);

-- 资源
INSERT INTO `sys_resources`(`id`, `category_id`, `name`, `code`, `url`, `sort`) VALUES (10, 2, '我的推荐', 'traffic:myrecommend', '/traffic/recommends/myRecommends', 10);

-- 角色资源
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (13, 10);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (13, 10);
INSERT INTO `sys_role_resources`(`role_id`, `resource_id`) VALUES (13, 10);
