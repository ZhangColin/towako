
<template>
	<div class="page ">
		<div class="header">
			<!--  -->
		</div>
		<div class="section">
			<!--  -->
			<van-cell-group>
				<van-cell title="微信授权登录测试"></van-cell>
				<van-cell title="登录状态" :value="loginStatus"></van-cell>
				<van-cell title="accessToken" :value="accessToken"></van-cell>
				<van-cell title="userInfo" :value="JSON.stringify(userInfo)"></van-cell>
			</van-cell-group>
		</div>
		<div class="footer">
			<!--  -->
		</div>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';

// import { commonFun } from '@/utils/webview-sdk.js'
export default {
	name: 'Home',
	components: {},
	data() {
		return {
			//
		};
	},
	computed: {
		...mapGetters(['userInfo', 'accessToken', 'loginStatus']),
		// ...mapGetters('user', ['userInfo', 'accessToken', 'loginStatus']),
	},
	created() {
		//
	},
	// https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
	beforeRouteUpdate(to, from, next) {
		// if (from.name === 'security-center') {
		//   setTimeout(async() => {
		//     commonFun('HP00010000')
		//   }, 0)
		// } else {
		//   next()
		// }
	},
	async mounted() {
		this.$toast.loading({
			message: '加载中...',
			duration: 1200,
			forbidClick: true,
		});
	},
	methods: {
		//
	},
};
</script>

<style lang="scss" scoped src="./index.scss"></style>
