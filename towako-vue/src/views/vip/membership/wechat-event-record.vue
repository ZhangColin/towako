<template>
  <div class="app-container">
    <el-table
      v-loading="loading"
      :data="dataSource"
      row-key="id"
      class="table-container"
      element-loading-text="加载中..."
      stripe
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="事件" prop="event">
        <template slot-scope="{row}">
          <span>{{ row.event==='subscribe'?'关注':row.event==='SCAN'?'扫码':row.event }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="事件时间" prop="eventDate" />
      <el-table-column align="center" label="qrSceneStr" prop="qrSceneStr" />
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

import { findByMemberId } from '@/api/vip/wechat-event-record-api'

export default {
  name: 'WechatEventRecord',
  data() {
    return {
      memberId: 0,
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
    this.memberId = this.$route.query.memberId
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      this.queryParam.page = this.page.currentPage - 1
      this.queryParam.size = this.page.pageSize

      findByMemberId(this.memberId, this.queryParam).then(response => {
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

