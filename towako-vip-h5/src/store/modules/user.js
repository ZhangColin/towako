import Cookies from 'js-cookie';
import storage from 'good-storage';

const LOGIN_STATUS = 'Login_Status';
const ACCESS_TOKEN = 'Access_Token';
const USER_INFO = 'User_Info';

const SET_LOGIN_STATUS = 'SET_LOGIN_STATUS';
const SET_ACCESS_TOKEN = 'SET_ACCESS_TOKEN';
const SET_USER_INFO = 'User_Info';

const state = {
	loginStatus: Number(Cookies.get('Login_Status') || 0),
	accessToken: storage.get('Access_Token', ''),
	userInfo: storage.get('User_Info', {}),
};

const mutations = {
	[SET_LOGIN_STATUS]: (state, loginStatus) => {
		state.loginStatus = loginStatus;
	},

	[SET_ACCESS_TOKEN]: (state, accessToken) => {
		state.accessToken = accessToken;
	},

	[SET_USER_INFO]: (state, userInfo) => {
		state.userInfo = userInfo;
	},
};

const actions = {
	setLoginStatus({ commit, state }, query) {
		if ((query === 0 || query === 1) && process.env.NODE_ENV === 'production') {
			storage.remove(ACCESS_TOKEN);
			storage.remove(USER_INFO);
		}
		commit(SET_LOGIN_STATUS, Cookies.set(LOGIN_STATUS, query, { expires: 7 }));
	},
	setUserInfo({ commit, state }, query) {
		commit(SET_USER_INFO, storage.set(USER_INFO, query));
	},
	setAccessToken({ commit, state }, query) {
		commit(SET_ACCESS_TOKEN, storage.set(ACCESS_TOKEN, query));
	},
};

export default {
	namespaced: true,
	state,
	mutations,
	actions,
};
