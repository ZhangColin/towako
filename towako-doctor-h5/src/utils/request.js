import axios from 'axios'
import Vue from 'vue'
import { Toast } from 'vant'
import store from '@/store/index.js'

// 这样的话就可以使用 this.$axios 方法
// axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/'
axios.defaults.baseURL = process.env.VUE_APP_BASE_API
const service = axios.create({
  // baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/', // url = baseURL + request url

  timeout: process.env.NODE_ENV === 'production' ? 3000 : 15000 // request timeout
})
Vue.prototype.$axios = axios

const defaultParams = {
  api_version: '1.0.0',
  ts: Math.round(new Date() / 1000)
}

// request interceptor
service.interceptors.request.use(
  config => {
    // 在请求发出之前进行一些操作
    // config.headers['x-access-login_token'] = localStorage.getItem('login_token')
    const token = localStorage.getItem('login_token')
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }

    console.log(`--------> http ajax request`, config)
    config.data = Object.assign({}, defaultParams, config.data)

    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    return response
  },
  error => {
    console.error(error) // for debug

    if (error.response.status === 401) {
      store.commit('user/SET_LOGOUT')
      // Toast(`会话已过期，请重新登录`)
      return Promise.reject('会话已过期，请重新登录')
    } else {
      Toast.fail('服务器异常\n请稍后再试')
      return Promise.reject(error.response.data)
    }
  }
)

export default service
