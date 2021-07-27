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
                  {{ doctor.name || '未登录' }}
                </div>
              </div>
              <div class="aui-me-content-item-text">
                <!--                <a>-->
                <!--                  <span>{{ channelInfo.recommends }}</span>-->
                <!--                  <span>推广</span>-->
                <!--                </a>-->
                <!--                <a>-->
                <!--                  <span>0</span>-->
                <!--                  <span>下级</span>-->
                <!--                </a>-->
              </div>
            </div>
            <div class="aui-me-content-card">
              <h3><i class="aui-icon aui-card-me" />{{ doctor.title }}</h3>
            </div>
          </div>
        </div>
      </section>
    </div>

    <div class="gp-h10" />

    <div class="list">
      <van-row>
        <van-col span="2" />
        <van-col span="20">
          <van-button
            class="login_btn"
            type="primary"
            round
            block
            size="normal"
            @click="goChangePassword"
          >修改密码
          </van-button>
        </van-col>
        <van-col span="2" />
      </van-row>

    </div>

    <gp-footer />

    <div class="gp-h50" />
    <div class="gp-h50" />
    <gp-bottom-tabbar />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCurrentDoctor } from '@/api/hospital-doctors/doctor-api'

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
      doctor: {}
    }
  },
  computed: {
    ...mapGetters(['user_info'])
  },
  created() {
    getCurrentDoctor().then(response => {
      this.doctor = response.data
    })
  },
  methods: {
    goChangePassword() {
      this.$router.push({ path: '/changePassword' })
    }
  }
}
</script>

<style lang="scss" src='./index.scss'></style>
