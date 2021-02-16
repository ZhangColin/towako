drop index index_wechat_qr_code_channel_id_channel_type on tfc_wechat_qr_codes;

alter table tfc_wechat_qr_codes drop column channel_type;

create index index_wechat_qr_code_channel_id
	on tfc_wechat_qr_codes (channel_id);
