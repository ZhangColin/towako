
<template>
  <div class="home">
    <div class="header">
      <van-nav-bar
        title="病历"
        style="display: block"
        safe-area-inset-top
        fixed
        border
        placeholder
      />
    </div>
    <div class="section">
      <van-search
        v-model="queryParam.blurry"
        style="padding: 10px;"
        shape="round"
        show-action
        placeholder="请输入病案号、姓名、电话搜索"
        @search="onSearch"
        @cancel="onCancel"
      />
      <van-list
        v-model="loading"
        :finished="finished"
        :error.sync="error"
        error-text="请求失败，点击重新加载"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <van-cell v-for="(item, index) in dataSource" :key="index" :title="item.name" :value="item.phone" :label="item.ivf" @click="viewDetails(item)" />
      </van-list>
    </div>
    <div class="footer" />

    <gp-bottom-tabbar />
  </div>
</template>

<script>
import { searchMedicalRecords } from '@/api/assisted-reproduction/medical-records-api'

export default {
  components: {
  },
  data() {
    return {
      queryParam: { blurry: '' },
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
      this.fetchData()
    },
    fetchData() {
      this.loading = true
      // this.queryParam.page = this.page.currentPage - 1
      // this.queryParam.size = this.page.pageSize
      this.queryParam.page = 1
      this.queryParam.size = 1000

      searchMedicalRecords(this.queryParam).then(response => {
        this.dataSource = response.data.rows
        // this.page.total = response.data.total
        this.loading = false
        this.finished = true
      })
    },
    onSearch() {
      this.fetchData()
    },
    onCancel() {
      this.queryParam.blurry = ''
      this.fetchData()
    },
    viewDetails(item) {
      this.$router.push({ path: '/assisted-reproduction/medical-record/full-info', query: { medicalRecordId: item.id }})
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
