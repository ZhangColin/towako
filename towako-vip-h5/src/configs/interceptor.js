import Vue from 'vue';
import qs from 'qs';
import router from '@/router';
import store from '@/store';
import wechatAuth from '@/plugins/wechatAuth';
import { login } from '@/api/user';

Vue.use(wechatAuth, {
	appid: process.env.VUE_APP_WECHAT_APP_ID,
});

router.beforeEach(async(to, from, next) => {
	const { loginStatus } = store.state.user;

	console.log('loginStatus', loginStatus)
	switch (loginStatus) {
	case 0:
		wechatAuth.redirect_uri = processUrl();
		console.log('redirect_uri', wechatAuth.authUrl)
		await store.dispatch('user/setLoginStatus', 1);
		window.location.href = wechatAuth.authUrl;
		break;
	case 1:
		try {
			wechatAuth.returnFromWechat(to.fullPath);
			await processLogin(wechatAuth.code);
			if (router.history.list) {
				router.history.list.push(to);
			} else {
				router.history.list = [to];
			}
			store.commit('history/SET_LIST', to);
			document.body.scrollTop = 0;
			next();
		} catch (err) {
			await store.dispatch('user/setLoginStatus', 0);
			next();
		}
		break;
	case 2:
		next();
		break;
	default:
		break;
	}
});

// https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
router.afterEach((to, from) => {
	if (to.meta.title) {
		// 在 src\locales\index.js 中已针对 document.title 进行了国际化的处理
	} else {
		document.title = '\u200E';
		setTimeout(() => { document.title = '\u200E'; }, 100);
		setTimeout(() => { document.title = '\u200E'; }, 300);
		setTimeout(() => { document.title = '\u200E'; }, 600);
	}
});

function processUrl() {
	const url = window.location.href;
	// 解决多次登录url添加重复的code与state问题
	const urlParams = qs.parse(url.split('?')[1]);
	let redirectUrl = url;
	if (urlParams.code && urlParams.state) {
		delete urlParams.code;
		delete urlParams.state;
		const query = qs.stringify(urlParams);
		if (query.length) {
			redirectUrl = `${url.split('?')[0]}?${query}`;
		} else {
			redirectUrl = `${url.split('?')[0]}`;
		}
	}

	return redirectUrl;
}

function processLogin(code) {
	const data = { code };

	// eslint-disable-next-line no-async-promise-executor
	return new Promise(async(resolve, reject) => {
		try {
			// const { userInfo, accessToken } = await login(data);

			await store.dispatch('user/setLoginStatus', 2);
			// await store.dispatch('user/setAccessToken', accessToken);
			await store.dispatch('user/setAccessToken', '');
			// await store.dispatch('user/setUserInfo', userInfo);
			await store.dispatch('user/setUserInfo', {});

			resolve({ status: 1, data: '登录成功' });
		} catch (err) {
			reject(err);
		}
	});
}
