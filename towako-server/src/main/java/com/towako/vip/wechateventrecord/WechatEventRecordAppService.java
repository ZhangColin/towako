package com.towako.vip.wechateventrecord;

import com.cartisan.dtos.PageResult;
import com.towako.vip.wechateventrecord.response.WeChatEventRecordConverter;
import com.towako.vip.wechateventrecord.response.WeChatEventRecordDto;
import com.towako.vip.wechatmembership.WechatMembershipAppService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

/**
 * @author colin
 */
@Service
public class WechatEventRecordAppService {
    private final WechatEventRecordRepository repository;
    private final WechatMembershipAppService wechatMembershipAppService;
    private final WeChatEventRecordConverter converter = WeChatEventRecordConverter.CONVERTER;

    public WechatEventRecordAppService(WechatEventRecordRepository repository, WechatMembershipAppService wechatMembershipAppService) {
        this.repository = repository;
        this.wechatMembershipAppService = wechatMembershipAppService;
    }

    @Transactional(rollbackOn = Exception.class)
    public void eventRecord(String openId, String event, String qrSceneStr) {

        final WechatEventRecord weChatEventRecord = new WechatEventRecord(openId, event, qrSceneStr);

        repository.save(weChatEventRecord);
    }

    public PageResult<WeChatEventRecordDto> findByMemberId(@NonNull Long memberId, @NonNull Pageable pageable) {
        final String openId = wechatMembershipAppService.getOpenId(memberId);
        if (openId.isEmpty()){
            return new PageResult<>(0L, 0, Collections.emptyList());
        }

        final Page<WechatEventRecord> searchResult = repository.findByOpenId(openId,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "eventDate")));

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                converter.convert(searchResult.getContent()));
    }
}
