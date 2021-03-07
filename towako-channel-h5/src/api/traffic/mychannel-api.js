import request from '@/utils/request'

export function myChannel() {
  return request.get(`/traffic/mychannel`)
}

export function channelRegister(params) {
  return request.post(`/traffic/mychannel/register`, params)
}
