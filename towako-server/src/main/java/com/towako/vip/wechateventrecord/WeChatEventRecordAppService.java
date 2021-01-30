package com.towako.vip.wechateventrecord;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author colin
 */
@Service
public class WeChatEventRecordAppService {
    private final WeChatEventRecordRepository repository;

    public WeChatEventRecordAppService(WeChatEventRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void eventRecord(String openId, String event, String qrSceneStr) {

        final WeChatEventRecord weChatEventRecord = new WeChatEventRecord(openId, event, qrSceneStr);

        repository.save(weChatEventRecord);
    }

}
