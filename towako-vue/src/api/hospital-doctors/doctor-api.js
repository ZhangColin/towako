import request from '@/utils/request'

export function getHospitals(id) {
  return request.get(`/hospital-doctors/doctors/${id}/hospitals`)
}

export function assignHospitals(id, hospitalIds) {
  return request.put(`/hospital-doctors/doctors/${id}/hospitals`, { hospitalIds })
}
