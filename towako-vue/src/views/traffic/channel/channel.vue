<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.name" class="filter-item" placeholder="请输入渠道名称查询" />
      </el-col>
      <el-col :span="6">
        <el-select v-model="queryParam.type" placeholder="请选择渠道类型" clearable style="width: 100%">
          <el-option label="医生" value="DOCTOR" />
          <el-option label="永远幸医生" value="TOWAKO_DOCTOR" />
          <el-option label="家庭旅馆" value="FAMILY_HOTEL" />
          <el-option label="其它" value="OTHER" />
        </el-select>
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="handleAdd">新增</el-button>
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
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column align="center" label="手机" prop="phone" />
      <el-table-column align="center" label="上级渠道" prop="parentId">
        <template slot-scope="{row}">
          <span>{{ showParentChannel(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="类型" prop="type">
        <template slot-scope="{row}">
          <span>{{
            {DOCTOR: '医生', TOWAKO_DOCTOR: '永远幸医生', FAMILY_HOTEL: '家庭旅馆', OTHER: '其它'}[row.type] || ''
          }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否启用" prop="status">
        <template slot-scope="scope">
          <el-popconfirm
            :title="`确定要${(scope.row.status===1?'启用':'禁用')}[${scope.row.name}]`"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @onConfirm="handleStatusConfirmChange(scope.$index, scope.row)"
            @onCancel="handleStatusCancelChange(scope.$index, scope.row)"
          >
            <el-switch
              slot="reference"
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
            />
          </el-popconfirm>
        </template>
      </el-table-column>
      <el-table-column align="center" label="推荐数" prop="recommends" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleShowQr(scope.$index, scope.row)">推广码
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleShowChannelQr(scope.$index, scope.row)">渠道码
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleRecommendList(scope.$index, scope.row)">推荐列表
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
    <el-drawer
      :title="drawerTitle"
      :visible.sync="drawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="entityDataForm" :model="entityData" :rules="rules" label-width="120px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="entityData.name" />
          </el-form-item>
          <el-form-item label="手机" prop="phone">
            <el-input v-model="entityData.phone" />
          </el-form-item>
          <el-form-item label="上级渠道" prop="parentId">
            <el-select v-model="entityData.parentId" placeholder="请选择上級渠道" filterable clearable style="width: 100%">
              <el-option v-for="channel in allEffectiveChannels" :key="channel.id" :value="channel.id" :label="channel.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select v-model="entityData.type" placeholder="请选择渠道类型" style="width: 100%">
              <el-option label="医生" value="DOCTOR" />
              <el-option label="永远幸医生" value="TOWAKO_DOCTOR" />
              <el-option label="家庭旅馆" value="FAMILY_HOTEL" />
              <el-option label="其它" value="OTHER" />
            </el-select>
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="drawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      :title="qrCodeDrawerTitle"
      :visible.sync="qrCodeDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <img ref="qrCodeImage" style="width: 300px;" :src="qrCodeUrl" :alt="qrCodeDrawerTitle">
        <div class="drawer__footer">
          <!--          <el-button type="primary" @click="downloadQrCode()">下载</el-button>-->
          <el-button type="primary" @click="qrCodeDrawerVisible=false">关闭</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      :title="recommendDrawerTitle"
      :visible.sync="recommendDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-table
          v-loading="recommendLoading"
          :data="recommendDataSource"
          row-key="id"
          class="table-container"
          element-loading-text="加载中..."
          stripe
          border
          fit
          highlight-current-row
        >
          <el-table-column align="center" label="昵称" prop="nickName" />
          <el-table-column align="center" label="手机" prop="phone" />
          <el-table-column align="center" label="推荐时间" prop="recommendDate" />
        </el-table>
        <el-pagination
          :current-page.sync="recommendPage.currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="recommendPage.pageSize"
          :total="recommendPage.total"
          class="pagination-container"
          background
          align="right"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleRecommendSizeChange"
          @current-change="handleRecommendCurrentChange"
        />
        <div class="drawer__footer">
          <el-button type="primary" @click="handleDownload()">导出</el-button>
          <el-button type="primary" @click="recommendDrawerVisible=false">关闭</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { CudMixin } from '@/mixins/cud-mixin'
import { enableChannel, disableChannel, getAllEffectiveChannels }
  from '@/api/traffic/channel-api'
import { findByChannelId } from '@/api/traffic/recommend-api'

export default {
  name: 'Channel',
  mixins: [PaginationMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/traffic/channels',

      defaultData: {
        parentId: '',
        name: '',
        phone: '',
        type: 'DOCTOR'
      },
      title: '渠道',
      rules: {
        name: [
          { required: true, message: '请输入渠道名称', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ]
      },

      allEffectiveChannels: [],

      qrCodeDrawerTitle: '',
      qrCodeDrawerVisible: false,
      qrCodeUrl: '',

      channelId: 0,
      recommendDrawerTitle: '',
      recommendDrawerVisible: false,
      recommendDataSource: [],
      recommendQueryParam: {},
      recommendLoading: true,
      recommendPage: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      }
    }
  },
  created() {
    getAllEffectiveChannels().then(response => { this.allEffectiveChannels = response.data })
  },
  methods: {
    handleStatusConfirmChange(index, row) {
      if (row.status === 1) {
        enableChannel(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '启用成功'
          })
        }).catch(() => {
          row.status = 0
        })
      } else {
        disableChannel(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '禁用成功'
          })
        }).catch(() => {
          row.status = 1
        })
      }
    },
    handleStatusCancelChange(index, row) {
      row.status = row.status === 1 ? 0 : 1
    },
    handleShowQr(index, row) {
      this.qrCodeDrawerTitle = `${row.name} 推广码`
      this.qrCodeDrawerVisible = true
      this.qrCodeUrl = `http://service.lanmedical.com/traffic/mychannel/recommendPoster?channelId=${row.id}`
    },
    handleShowChannelQr(index, row) {
      this.qrCodeDrawerTitle = `${row.name} 渠道码`
      this.qrCodeDrawerVisible = true
      this.qrCodeUrl = `http://service.lanmedical.com/traffic/mychannel/qrcode?channelId=${row.id}`
    },

    // downloadQrCode() { // 下载图片地址和图片名
    //   const image = this.$refs.qrCodeImage
    //   image.setAttribute('crossOrigin', 'anonymous')
    //   const canvas = document.createElement('canvas')
    //   canvas.width = image.width
    //   canvas.height = image.height
    //   const context = canvas.getContext('2d')
    //   context.drawImage(image, 0, 0, image.width, image.height)
    //   const url = canvas.toDataURL('image/jpeg') // 得到图片的base64编码数据
    //
    //   const a = document.createElement('a') // 生成一个a元素
    //   const event = new MouseEvent('click') // 创建一个单击事件
    //   a.download = this.qrCodeDrawerTitle || 'qrCode' // 设置图片名称
    //   a.href = url // 将生成的URL设置为a.href属性
    //   a.dispatchEvent(event) // 触发a的单击事件
    // },
    handleRecommendList(index, row) {
      this.recommendDrawerTitle = `${row.name} 推荐列表`
      this.recommendDrawerVisible = true
      this.channelId = row.id
      this.handleRecommendSearch()
    },
    handleDownload() {
      findByChannelId(this.channelId, { page: 0, size: 10000 }).then(response => {
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['昵称', '手机', '推荐时间']
          excel.export_json_to_excel({
            header: tHeader,
            data: response.data.rows.map(v => ['nickName', 'phone', 'recommendDate'].map(fieldName => v[fieldName])),
            filename: this.recommendDrawerTitle
          })
        })
      })
    },
    fetchRecommendData() {
      this.recommendLoading = true
      this.recommendQueryParam.page = this.recommendPage.currentPage - 1
      this.recommendQueryParam.size = this.recommendPage.pageSize

      findByChannelId(this.channelId, this.recommendQueryParam).then(response => {
        this.recommendDataSource = response.data.rows
        this.recommendPage.total = response.data.total
        this.recommendLoading = false
      })
    },
    handleRecommendSearch() {
      this.recommendPage.currentPage = 1
      this.fetchRecommendData()
    },
    handleRecommendSizeChange(pageSize) {
      this.recommendPage.currentPage = 1
      this.recommendPage.pageSize = pageSize
      this.fetchRecommendData()
    },
    handleRecommendCurrentChange(currentPage) {
      this.recommendPage.currentPage = currentPage
      this.fetchRecommendData()
    },
    showParentChannel(row) {
      if (row.parentId === '0') {
        this.$set(row, 'parentId', '')
        return ''
      }

      const channelTree = this.getChannelTree(row.parentId)
      return channelTree.map(channel => channel.name).join(' -> ')
    },
    getChannelTree(channelId) {
      const channel = this.allEffectiveChannels.find(channel => channel.id === channelId)
      if (channel) {
        const channels = this.getChannelTree(channel.parentId)
        channels.push(channel)

        return channels
      } else {
        return []
      }
    }
  }
}
</script>

