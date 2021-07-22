alter table ar_medical_records
    add birthday date NULL COMMENT '生日' after id_card;

alter table ar_inspection_reports
    add hcg varchar(32) NULL COMMENT 'HCG' after hmg;
alter table ar_inspection_reports
    add decapeptyl varchar(32) NULL COMMENT '达必佳0.1' after hcg;
alter table ar_inspection_reports
    add take_ovum_date date NULL COMMENT '取卵时间' after leucorrhea;
