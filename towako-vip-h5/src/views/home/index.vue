
<template>
	<div class="page ">
		<div class="header">
			<van-nav-bar
				title="上传"
				fixed
				border
				placeholder
				safe-area-inset-top
			/>
		</div>
		<div class="section">
			<van-sticky :offset-top="1">
				<!--								<van-uploader-->
				<!--									upload-text="上传病历图片"-->
				<!--									:max-size="10 * 1024 * 1024"-->
				<!--									:after-read="afterRead"-->
				<!--									@oversize="onOversize"-->
				<!--								/>-->
				<van-button icon="photograph" @click="uploadImage">上传</van-button>
			</van-sticky>
<!--      <van-steps direction="vertical">-->
<!--        <van-step v-for="group in pictures" :key="group">-->
<!--          <h3>{{ group }}</h3>-->
<!--          <p>2016-07-12 12:40</p>-->
<!--        </van-step>-->
<!--      </van-steps>-->
			<van-grid :border="false" :column-num="2" :square="true" :center="true" :clickable="true">
				<van-grid-item v-for="(picture, index) in pictures" :key="picture.id" @click="picturesPreview(index)">
					<van-image :src="picture.url" fit="contain" width="100%" height="100%" />
				</van-grid-item>
			</van-grid>
		</div>
		<div class="footer">
			<!--  -->
		</div>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
import request from '@/utils/request.js';
import { ImagePreview } from 'vant';
import wx from 'weixin-js-sdk';

export default {
	name: 'Home',
	components: {},
	data() {
		return {
		  imgData: '',
		  pictures: [],
		};
	},
	computed: {
		// ...mapGetters(['userInfo', 'accessToken', 'loginStatus']),
		...mapGetters('user', ['userInfo', 'accessToken', 'loginStatus']),
	},
	created() {
		//
	},
	async mounted() {
	  this.getConfig();
		this.fetchData();
		// this.$toast.loading({
		// 	message: '加载中...',
		// 	duration: 1200,
		// 	forbidClick: true,
		// });
	},
	methods: {
	  getConfig() {
	    request.post('/mp/jsapi', { url: location.href.split('#')[0] })
				.then(response => {
					const data = response.data;
					wx.config({
						debug: false, // 开启调试模式,
						appId: data.appId, // 必填，企业号的唯一标识，此处企业号corpid
						timestamp: data.timestamp, // 必填，生成签名的时间戳
						nonceStr: data.nonceStr, // 必填，生成签名的随机串
						signature: data.signature, // 必填，签名，见附录1
						jsApiList: ['chooseImage', 'uploadImage', 'downloadImage', 'getLocalImgData'], // 必填，需要使用的JS接口列表JS接口列表见附录2
					});
					// wx.ready(function() {
					// 	// alert('ready');
					// 	// 在这里调用 API
					// 	// 判断当前客户端版本是否支持指定JS接口
					// 	wx.checkJsApi({
					// 		jsApiList: [
					// 			// 所有要调用的 API 都要加到这个列表中
					// 			'chooseImage', 'uploadImage', 'downloadImage', 'getLocalImgData'
					// 		], // 需要检测的JS接口列表，所有JS接口列表见附录2,
					// 		success: function(res) {
					// 			console.log('checkJsApi成功=====', res);
					// 			// alert('支持chooseImage');
					// 			// 以键值对的形式返回，可用的api值true，不可用为false
					// 			// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
					// 		},
					// 		fail: function(e) {
					// 			// alert('不支持');
					// 		},
					// 	});
					// });
					// wx.error(function(res) {
					// 	// wx.config注册失败就会执行
					// 	// alert("error");
					// });
				});
		},
		fetchData() {
			request.get('/assisted-reproduction/medical-member-pictures').then(response => {
				this.pictures = response.data;
			});
		},
		// 图片上传
		uploadImage() {
	    let that = this;
			wx.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album', 'camera'],
				success: function(res) {
					// console.log('localId', res.localIds);
					wx.uploadImage({
						localId: res.localIds[0],
						isShowProgressTips: 1,
						success: function(r) {
						  // console.log('serverid', r.serverId);
							request.post('/assisted-reproduction/medical-member-pictures/wechatMedia/' + r.serverId)
								.then(result => {
									// alert(JSON.stringify(result.data));
									// alert(JSON.stringify(this.pictures));
									that.$toast('上传成功');
									that.pictures.push(result.data);
								});
						},
					});
				},
				fail: function(e) {
					console.log('=====', e);
				},
				complete: function() {
				},
			});
		},
		onOversize() {
			this.$toast('文件大小不能超过 10MB');
		},
		afterRead(file) {
		  const formData = new FormData();
			formData.append('file', file.file, file.file.name);
			const config = { headers: {
				'Content-Type': 'multipart/form-data' }};
			request.post('/assisted-reproduction/medical-member-pictures', formData, config)
				.then(response => {
					this.$toast('上传成功');
				  this.pictures.push(response.data);
				})
				.catch(err => console.log(err));
		},
		picturesPreview(index) {
		  ImagePreview({
				images: this.pictures.map(picture => picture.url),
				startPosition: index,
			});
		},
	},
};
</script>

<style lang="scss" scoped src="./index.scss"></style>
