import request from '@/utils/request'

export function findByChannelId(channelId, params) {
  return request.get(`/traffic/recommends/findByChannelId/${channelId}`, { params })
}

export function myRecommends(params) {
  return request.get(`/traffic/recommends/myRecommends`, { params })
}
