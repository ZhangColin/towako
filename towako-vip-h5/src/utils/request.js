import { get as getLanguage } from '@/utils/language.js';
import axios from 'axios';
import Vue from 'vue';
import { Toast, Dialog } from 'vant';
import store from '@/store/index.js';

// 这样的话就可以使用 this.$axios 方法
// axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/';
axios.defaults.baseURL = process.env.VUE_APP_BASE_API;
axios.defaults.headers.common['Accept-Language'] = getLanguage();
const service = axios.create({
	// baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/', // url = baseURL + request url

	// 参考 : https://blog.csdn.net/HermitSun/article/details/100797223
	// withCredentials: true, // send cookies when cross-domain requests

	// axios 其实会给请求自动转码, 一般来说不需要进行额外的设置
	// headers: {
	//   'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
	// },

	timeout: process.env.NODE_ENV === 'production' ? 3000 : 15000, // request timeout
});
Vue.prototype.$axios = axios;

// const defaultParams = {
// 	api_version: '1.0.0',
// 	ts: Math.round(new Date() / 1000),
// };

// request interceptor
service.interceptors.request.use(
	config => {
		const { accessToken } = store.getters;
		// 在请求发出之前进行一些操作
		if (accessToken) {
			config.headers.Authorization = `Bearer ${accessToken}`;
		}

		console.log(`--------> http ajax request`, config);
		// config.data = Object.assign({}, defaultParams, config.data);

		return config;
	},
	error => {
		// do something with request error
		console.log(error); // for debug
		return Promise.reject(error);
	}
);

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
		return response;
	},
	error => {
		if (error.response.status === 401 || error.response.status === 403) {
			// Toast(`会话已过期，请重新登录`);
			store.dispatch('user/setLoginStatus', 0)
				.then(() => location.reload());
		} else {
			Toast.clear();
			Dialog.alert({
				title: '提示',
				message: error.response.data.message || '服务器异常\n请稍后再试',
			});
		}
		return Promise.reject(error.response.data);
	}

);

export default service;
