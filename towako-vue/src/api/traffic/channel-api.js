import request from '@/utils/request'

export function enableChannel(id) {
  return request.put(`/traffic/channels/${id}/enable`)
}

export function disableChannel(id) {
  return request.put(`/traffic/channels/${id}/disable`)
}
