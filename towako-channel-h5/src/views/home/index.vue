
<template>
  <div class="home">
    <div class="header">
      <van-nav-bar
        title="我的推荐"
        style="display: block"
        safe-area-inset-top
        fixed
        border
        placeholder
      />
    </div>
    <div class="section">
      <van-list
        v-model="loading"
        :finished="finished"
        :error.sync="error"
        error-text="请求失败，点击重新加载"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <van-cell v-for="(item, index) in dataSource" :key="index" :title="item.nickName" :value="item.recommendDate" />
      </van-list>
    </div>
    <div class="footer" />

    <gp-bottom-tabbar />
  </div>
</template>

<script>
import { myRecommends } from '@/api/traffic/recommend-api'
export default {
  components: {
  },
  data() {
    return {
      page: 0,
      dataSource: [],
      error: false,
      loading: false,
      finished: false
    }
  },
  computed: {
  },
  watch: {
  },
  created() {},
  methods: {
    onLoad() {
      myRecommends({ page: this.page, size: 1000 }).then(response => {
        response.data.rows.forEach(recommend => this.dataSource.push(recommend))
        this.loading = false
        this.page++
        if (response.data.total === this.dataSource.length) {
          this.finished = true
        }
      }).catch(() => {
        this.error = true
      })
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
