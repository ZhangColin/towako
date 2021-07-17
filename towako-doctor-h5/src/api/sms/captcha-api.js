import request from '@/utils/request'

export function sendCode(phone) {
  return request.get(`/captcha/send`, { params: { phone }})
}
