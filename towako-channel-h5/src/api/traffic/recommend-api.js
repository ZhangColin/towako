import request from '@/utils/request'

export function myRecommends(params) {
  return request.get(`/traffic/recommends/myRecommends`, { params })
}
