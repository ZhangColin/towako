import request from '@/utils/request'

export function searchMedicalRecords(params) {
  return request.get(`/assisted-reproduction/medical-records/search`, { params })
}

export function getMedicalRecordFullInfo(id) {
  return request.get(`/assisted-reproduction/medical-records/${id}/fullInfo`)
}

export function reportTreatmentPeriod(id, reportInfo) {
  return request.put(`/assisted-reproduction/treatment-periods/${id}/report`, reportInfo)
}
