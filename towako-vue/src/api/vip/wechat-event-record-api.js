import request from '@/utils/request'

export function findByMemberId(memberId, params) {
  return request.get(`/vip/wechatEventRecords/findByMemberId/${memberId}`, { params })
}
