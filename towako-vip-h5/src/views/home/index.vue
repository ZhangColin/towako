
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
				<van-uploader
					upload-text="上传病历图片"
					:max-size="10 * 1024 * 1024"
					:after-read="afterRead"
					@oversize="onOversize"
				/>
			</van-sticky>
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

export default {
	name: 'Home',
	components: {},
	data() {
		return {
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
	  this.fetchData();
		// this.$toast.loading({
		// 	message: '加载中...',
		// 	duration: 1200,
		// 	forbidClick: true,
		// });
	},
	methods: {
		fetchData() {
			request.get('/assisted-reproduction/medical-member-pictures').then(response => {
				this.pictures = response.data;
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
