package com.towako.traffic.scanqrcoderecord;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author colin
 */
@Service
public class ScanQrCodeRecordAppService {
    private final ScanQrCodeRecordRepository repository;

    public ScanQrCodeRecordAppService(ScanQrCodeRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void scanRecord(String qrSceneStr, String openId, String event) {
        //TODO: 首次扫码关注是qrscene_开头，下面的值的获取方式需要调整修改
        final String[] strings = qrSceneStr.split("_");
        Long channelId = null;
        String channelType = null;
        if (strings.length==2){
            channelType = strings[0];
            channelId = Long.parseLong(strings[1]);
        }
        else if(strings.length==3){
            channelType = strings[1];
            channelId = Long.parseLong(strings[2]);
        }

        final ScanQrCodeRecord scanQrCodeRecord = new ScanQrCodeRecord(channelId, channelType, qrSceneStr, openId, event);

        repository.save(scanQrCodeRecord);
    }

}
