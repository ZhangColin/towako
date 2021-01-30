<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.nickname" class="filter-item" placeholder="请输入会员名称查询" />
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
      </el-col>
    </el-row>
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
      <el-table-column align="center" label="编号" prop="id" />
      <el-table-column align="center" label="昵称" prop="nickname" />
      <el-table-column align="center" label="手机" prop="phone" />
      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <span>{{ scope.row.gender===1?'男':'女' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="头像" prop="avatar">
        <template slot-scope="scope">
          <img :src="scope.row.avatar">
        </template>
      </el-table-column>
      <el-table-column align="center" label="渠道" prop="channel">
        <template slot-scope="{row}">
          <span>{{ row.channel==='DOCTOR'?'医生':row.channel==='FAMILY_HOTEL'?'家庭旅馆':row.channel==='OTHER'?'其它':'' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="推荐" prop="recommend">
        <template slot-scope="{row}">
          <span>{{ row.recommend||'用户自寻' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="注册时间" prop="createDateTime" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="{row}">
          <el-button type="primary" class="filter-item" @click="handleWechatEventRecord(row.id)">微信事件记录</el-button>
        </template>
      </el-table-column>
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
import { PaginationMixin } from '@/mixins/pagination-mixin'

export default {
  name: 'Membership',
  mixins: [PaginationMixin],
  data() {
    return {
      apiBaseUrl: '/vip/memberships'
    }
  },
  created() {
  },
  methods: {
    handleWechatEventRecord(memberId) {
      this.$router.push({ path: '/vip/membership/wechat-event-record', query: { memberId }})
    }
  }
}
</script>

