import request from '@/utils/request'

export function enableDoctor(id) {
  return request.put(`/channel/doctors/${id}/enable`)
}

export function disableDoctor(id) {
  return request.put(`/channel/doctors/${id}/disable`)
}
