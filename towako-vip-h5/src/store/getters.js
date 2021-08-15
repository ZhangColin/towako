const getters = {
	loginStatus: state => state.user.loginStatus,
	accessToken: state => state.user.accessToken,
	userInfo: state => state.user.userInfo,
};

export default getters;
