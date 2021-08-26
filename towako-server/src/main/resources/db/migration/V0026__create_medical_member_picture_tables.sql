-- 病历表
alter table ar_medical_records
    add member_id bigint NULL COMMENT '会员Id' after hospital_id;

-- 用户自传病历图片
CREATE TABLE `ar_medical_member_pictures` (
  `id` bigint NOT NULL COMMENT '主键',
  `medical_record_id` bigint NULL COMMENT '病历Id',
  `member_id` bigint NULL COMMENT '用户Id',
  `url` varchar(256) NULL COMMENT '图片地址',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户自传病历图片';
