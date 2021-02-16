alter table tfc_channels
    add parent_id bigint default 0 null comment '上级渠道' after id;
