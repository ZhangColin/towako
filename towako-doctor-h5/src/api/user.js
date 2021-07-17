
import request from '@/utils/request.js'

export function login(data) {
  return request({
    url: '/system/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}

export function changePassword(oldPassword, newPassword) {
  return request.put('/system/users/profile/changePassword', { oldPassword, newPassword })
}

// /**
//  * 用户通过手机短信登录/注册 ,如果用户未注册则直接注册并登录
//  *
//  * @param {*} data
//  */
// export function smscode_login(data) {
//   return request({
//     url: '/client/user/smscode_login',
//     method: 'post',
//     data
//   })
// }
//
// /**
//  * 用户获得短信验证码 登录/注册
//  *
//  * @param {*} data
//  */
// export function get_sms_code(params) {
//   return request({
//     url: '/client/user/get_sms_code',
//     method: 'get',
//     params
//   })
// }
