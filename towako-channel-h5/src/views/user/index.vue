<template>
  <div id="mineEntry">
    <div class="user-template">
      <van-sticky>
        <header class="aui-header-default aui-header aui-header-bg gp-nav-bar">
          <a href="javascript: scroll(0, 0)" class="aui-header-item">
            <!--<i class="aui-icon aui-icon-back-white" id="scrollSearchI" style="display:block"></i>-->
            <div id="scrollSearchDiv">
              <img :src="imgs.head" alt>
            </div>
          </a>
          <div class="aui-header-center aui-header-center-clear">
            <div class="aui-header-title">{{ $route.meta.title }}</div>
          </div>
          <a class="aui-header-item-icon">
            <i class="aui-icon aui-icon-Set" />
          </a>
        </header>
      </van-sticky>

      <section class="aui-me-content">
        <div class="aui-me-content-box">
          <div class="aui-me-content-info" />
          <div class="aui-me-content-list">
            <div class="aui-me-content-item">
              <div class="aui-me-content-item-head">
                <div class="aui-me-content-item-img">
                  <img :src="imgs.head3" alt>
                </div>
                <div class="aui-me-content-item-title">
                  {{ channelInfo.name || '未登录' }}
                </div>
              </div>
              <div class="aui-me-content-item-text">
                <a>
                  <span>{{ channelInfo.recommends }}</span>
                  <span>推广</span>
                </a>
                <!--                <a>-->
                <!--                  <span>0</span>-->
                <!--                  <span>下级</span>-->
                <!--                </a>-->
              </div>
            </div>
            <div class="aui-me-content-card">
              <h3><i class="aui-icon aui-card-me" />{{ {DOCTOR: '医生', TOWAKO_DOCTOR: '永远幸医生', FAMILY_HOTEL: '家庭旅馆', OTHER: '其它'}[channelInfo.type] || '' }}</h3>
            </div>
          </div>
        </div>
      </section>
    </div>

    <div class="gp-h10" />
    <div class="list">
      <van-cell title="我的推广码" />
      <van-row>
        <van-col span="24"><van-image ref="qrCodeImage" :src="`https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${channelInfo.ticket}`" alt="" /></van-col>
      </van-row>
    </div>

    <div class="gp-h10" />
    <div class="list">
      <van-cell title="我的渠道码" />
      <van-row>
        <van-col span="24"><van-image ref="channelQrCodeImage" :src="`http://service.lanmedical.com/traffic/mychannel/qrcode?channelId=${channelInfo.id}`" alt="" /></van-col>
      </van-row>
    </div>

    <div class="gp-h10" />

    <gp-footer />

    <div class="gp-h50" />
    <div class="gp-h50" />

    <gp-bottom-tabbar />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { myChannel } from '@/api/traffic/mychannel-api'

export default {
  components: {},
  data() {
    return {
      imgs: {
        head: require('@/assets/img/mineEntry/head-3.jpg'),
        sf12: require('@/assets/img/mineEntry/sf-12.jpg'),
        sf13: require('@/assets/img/mineEntry/sf-13.jpg'),
        icontj3: require('@/assets/img/mineEntry/icon-tj3.jpg'),
        sf14: require('@/assets/img/mineEntry/sf-14.jpg'),
        sf15: require('@/assets/img/mineEntry/sf-15.jpg'),
        head3: require('@/assets/img/mineEntry/head-3.jpg')
      },
      imgtemplate: require('@/assets/img/template/200307221709.png'),
      channelInfo: {}
    }
  },
  computed: {
    ...mapGetters(['user_info'])
  },
  created() {
    myChannel().then(response => {
      this.channelInfo = response.data
    })
  },
  methods: {

  }
}
</script>

<style lang="scss" src='./index.scss'></style>
