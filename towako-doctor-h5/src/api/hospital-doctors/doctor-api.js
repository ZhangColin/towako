import request from '@/utils/request'

export function doctorRegister(params) {
  return request.post(`/hospital-doctors/doctors/register`, params)
}

export function getMyHospitals() {
  return request.get(`/hospital-doctors/doctors/myHospitals`)
}
export function getCurrentDoctor() {
  return request.get(`/hospital-doctors/doctors/currentDoctor`)
}
