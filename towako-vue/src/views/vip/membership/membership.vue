<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.nickname" class="filter-item" placeholder="请输入会员名称查询" />
      </el-col>
      <el-col :span="9">
        <el-date-picker
          v-model="registerRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptions"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-col>
      <el-col :span="9">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="handleExport">导出</el-button>
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
          <img :src="scope.row.avatar" style="width: 50px; height: 50px" alt="">
        </template>
      </el-table-column>
      <el-table-column align="center" label="渠道" prop="channel">
        <template slot-scope="{row}">
          <span>{{
            {DOCTOR: '医生', TOWAKO_DOCTOR: '永远幸医生', FAMILY_HOTEL: '家庭旅馆', OTHER: '其它'}[row.channel] || ''
          }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="推荐" prop="recommend">
        <template slot-scope="{row}">
          <span>{{ row.recommend || '用户自寻' }}</span>
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
import { search } from '@/api/common-api'

export default {
  name: 'Membership',
  data() {
    return {
      apiBaseUrl: '/vip/memberships',
      queryParam: {},
      toggleSearchStatus: false,
      dataSource: [],
      loading: true,
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      },
      registerRange: null,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
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

      if (this.registerRange) {
        this.queryParam.startDate = this.registerRange[0]
        this.queryParam.endDate = this.registerRange[1]
      } else {
        delete this.queryParam.startDate
        delete this.queryParam.endDate
      }

      search(this.apiBaseUrl, this.queryParam).then(response => {
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
    },
    handleWechatEventRecord(memberId) {
      this.$router.push({ path: '/vip/membership/wechat-event-record', query: { memberId }})
    },
    handleExport() {
      const exportParam = Object.assign({}, this.queryParam)
      exportParam.page = 0
      exportParam.size = 10000

      if (this.registerRange) {
        exportParam.startDate = this.registerRange[0]
        exportParam.endDate = this.registerRange[1]
      } else {
        delete exportParam.startDate
        delete exportParam.endDate
      }

      search(this.apiBaseUrl, exportParam).then(response => {
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['昵称', '手机', '渠道', '推荐', '推荐时间']
          excel.export_json_to_excel({
            header: tHeader,
            data: response.data.rows.map(v => ['nickname', 'phone', 'channel', 'recommend', 'createDateTime'].map(fieldName => {
              if (fieldName === 'channel') {
                return { DOCTOR: '医生', TOWAKO_DOCTOR: '永远幸医生', FAMILY_HOTEL: '家庭旅馆', OTHER: '其它' }[v[fieldName]] || ''
              } else if (fieldName === 'recommend') {
                return v[fieldName] || '用户自寻'
              }
              return v[fieldName]
            })),
            filename: '会员列表'
          })
        })
      })
    }
  }
}
</script>

