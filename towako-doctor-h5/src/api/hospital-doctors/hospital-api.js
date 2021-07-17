import request from '@/utils/request'

export function getAllHospitals() {
  return request.get(`/hospital-doctors/hospitals`)
}
