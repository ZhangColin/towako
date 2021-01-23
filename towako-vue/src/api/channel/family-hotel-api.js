import request from '@/utils/request'

export function enableFamilyHotel(id) {
  return request.put(`/channel/familyhotels/${id}/enable`)
}

export function disableFamilyHotel(id) {
  return request.put(`/channel/familyhotels/${id}/disable`)
}
