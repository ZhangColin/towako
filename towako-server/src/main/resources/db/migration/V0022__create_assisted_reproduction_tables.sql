-- 病历表
CREATE TABLE `ar_medical_records` (
   `id` bigint NOT NULL COMMENT '病历Id',
   `record_no` varchar(32) NOT NULL COMMENT '病案号',
   `ivf` varchar(32) NOT NULL COMMENT 'IVF(AIH)号',
   `name` varchar(32) NOT NULL COMMENT '姓名',
   `phone` varchar(32) NOT NULL COMMENT '电话',
   `id_card` varchar(32) NOT NULL COMMENT '身份证',
   `age` tinyint NULL COMMENT '年龄',
   `main_appeal` varchar(256) NULL COMMENT '主诉',
   `hpi` varchar(1024) NULL COMMENT '现病史',
   `medical_history` varchar(1024) NULL COMMENT '既往史',
   `man` varchar(32) NULL COMMENT '男方',
   `nation` varchar(32) NULL COMMENT '民族',
   `marital_status` tinyint NULL COMMENT '婚姻状况',
   `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`) USING BTREE,
   INDEX `index_medical_records_record_no`(`record_no`),
   INDEX `index_medical_records_phone`(`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='病历';

-- 医疗团队表
CREATE TABLE `ar_medical_teams` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `medical_record_id` bigint NULL COMMENT '病历Id',
  `doctor_id` bigint NULL COMMENT '医生Id',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_medical_teams_medical_record_id_sort`(`medical_record_id`, `sort`),
  INDEX `index_medical_teams_medical_doctor_id`(`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医疗团队';

-- 疗程表
CREATE TABLE `ar_treatment_periods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `medical_record_id` bigint NULL COMMENT '病历Id',
  `period` tinyint NOT NULL COMMENT '第几疗程',
  `last_menstrual_period` date NOT NULL COMMENT '末次月经',
  `bmi` varchar(32) NULL COMMENT 'BMI',
  `amh` varchar(32) NULL COMMENT 'AMH',
  `plan` varchar(32) NULL COMMENT '方案',
  `report` varchar(1024) NULL COMMENT '诊断报告',
  `report_date` date NULL COMMENT '报告日期',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_medical_periods_medical_record_id`(`medical_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='疗程';

-- 检查报告
CREATE TABLE `ar_inspection_reports` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `treatment_period_id` bigint NULL COMMENT '疗程Id',
  `inspection_date` date NULL COMMENT '检查日期',
  `cycle_number` tinyint NULL COMMENT '周期天数',
  `letrozole` varchar(32) NULL COMMENT '来曲唑',
  `hmg` varchar(32) NULL COMMENT 'HMG(IU)',
  `mpa` varchar(32) NULL COMMENT 'MPA(mg)',
  `cc` varchar(32) NULL COMMENT 'CC(mg)',
  `ganirelix` varchar(32) NULL COMMENT '加尼瑞克/思则瑞',
  `femoston` varchar(32) NULL COMMENT '芬吗通',
  `tadalafil` varchar(32) NULL COMMENT '他达拉非',
  `intima` varchar(32) NULL COMMENT '内膜',
  `intima_type` varchar(32) NULL COMMENT '内膜类型',
  `lof1` varchar(32) NULL COMMENT 'LOF1',
  `lof2` varchar(32) NULL COMMENT 'LOF2',
  `lof3` varchar(32) NULL COMMENT 'LOF3',
  `lof4` varchar(32) NULL COMMENT 'LOF4',
  `lof5` varchar(32) NULL COMMENT 'LOF5',
  `lof6` varchar(32) NULL COMMENT 'LOF6',
  `rof1` varchar(32) NULL COMMENT 'ROF1',
  `rof2` varchar(32) NULL COMMENT 'ROF2',
  `rof3` varchar(32) NULL COMMENT 'ROF3',
  `rof4` varchar(32) NULL COMMENT 'ROF4',
  `rof5` varchar(32) NULL COMMENT 'ROF5',
  `rof6` varchar(32) NULL COMMENT 'ROF6',
  `fsh` varchar(32) NULL COMMENT 'FSH(mlU/ml)',
  `lh` varchar(32) NULL COMMENT 'LH(mlU/ml)',
  `e2` varchar(32) NULL COMMENT 'E2(pg/ml)',
  `t` varchar(32) NULL COMMENT 'T(ng/dl)',
  `p` varchar(32) NULL COMMENT 'P(ng/ml)',
  `prl` varchar(32) NULL COMMENT 'PRL(ng/ml)',
  `bhcg` varchar(32) NULL COMMENT 'B-HCG(mlU/ml)',
  `leucorrhea` varchar(32) NULL COMMENT '白带检查',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_inspection_report_treatment_period_id`(`treatment_period_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检查报告';

