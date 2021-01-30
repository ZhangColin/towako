package com.towako.vip.wechateventrecord;

import com.cartisan.repositories.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WechatEventRecordRepository extends BaseRepository<WechatEventRecord, Long> {
    Page<WechatEventRecord> findByOpenId(String openId, Pageable pageable);
}
