<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="dataSource"
      row-key="id"
      empty-text="暂未有用户扫码，请加油推广"
      class="table-container"
      element-loading-text="加载中..."
      stripe
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="昵称" prop="nickName" />
      <el-table-column align="center" label="推荐时间" prop="recommendDate" />
    </el-table>
    <el-pagination
      :current-page.sync="page.currentPage"
      :page-sizes="[5, 10, 20]"
      :page-size="page.pageSize"
      :total="page.total"
      class="pagination-container"
      background
      align="right"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>

import { myRecommends } from '@/api/traffic/recommend-api'

export default {
  name: 'MyRecommend',
  data() {
    return {
      queryParam: {},
      toggleSearchStatus: false,
      dataSource: [],
      loading: true,
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      this.queryParam.page = this.page.currentPage - 1
      this.queryParam.size = this.page.pageSize

      myRecommends(this.queryParam).then(response => {
        this.dataSource = response.data.rows
        this.page.total = response.data.total
        this.loading = false
      })
    },
    handleSearch() {
      this.page.currentPage = 1
      this.fetchData()
    },
    handleSizeChange(pageSize) {
      this.page.currentPage = 1
      this.page.pageSize = pageSize
      this.fetchData()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage
      this.fetchData()
    }
  }
}
</script>

