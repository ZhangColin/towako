-- 医院表
CREATE TABLE `hd_hospitals` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '医院Id',
  `name` varchar(128) NOT NULL COMMENT '医院名称',
  `description` varchar(255) NULL DEFAULT '' COMMENT '描述',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院';

-- 医生表
CREATE TABLE `hd_doctors` (
   `id` bigint NOT NULL COMMENT '医生Id',
   `user_id` bigint NOT NULL COMMENT '用户Id',
   `name` varchar(32) NOT NULL COMMENT '姓名',
   `phone` varchar(32) NOT NULL COMMENT '电话',
   `title` varchar(128) NULL COMMENT '职称',
   `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0：禁用  1：启用 ）',
   `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生';

-- 医院医生关联表
CREATE TABLE `hd_doctor_hospitals` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctor_id` bigint NULL COMMENT '医生Id',
  `hospital_id` bigint NULL COMMENT '医院Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_hospital_doctors__doctor_idhospital_id`(`doctor_id`, `hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医院医生';
