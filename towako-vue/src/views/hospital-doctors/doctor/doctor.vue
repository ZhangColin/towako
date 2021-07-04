<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-select v-model="queryParam.hospitalId" placeholder="请选择医院" clearable style="width: 100%">
          <el-option
            v-for="hospital in hospitals"
            :key="hospital.id"
            :label="hospital.name"
            :value="hospital.id"
          />
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
      <el-table-column align="center" label="医生Id" prop="id" />
      <el-table-column align="center" label="姓名" prop="name" />
      <el-table-column align="center" label="电话" prop="phone" />
      <el-table-column align="center" label="职称" prop="title" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleHospitalsAssign(scope.$index, scope.row)">分配医院</el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.$index, scope.row)">删除</el-dropdown-item>
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
          <el-form-item label="姓名" prop="name">
            <el-input v-model="entityData.name" />
          </el-form-item>
          <el-form-item v-if="!entityData.id" label="电话" prop="phone">
            <el-input v-model="entityData.phone" />
          </el-form-item>
          <el-form-item label="职称" prop="title">
            <el-input v-model="entityData.title" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="drawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="分配医院"
      :visible.sync="hospitalDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-checkbox-group
          v-model="hospitalIds"
          style="flex: 1"
        >
          <template v-for="hospital in hospitals">
            <el-checkbox :key="hospital.id" :label="hospital.id">{{ hospital.name }}</el-checkbox>
          </template>

        </el-checkbox-group>
        <div class="drawer__footer">
          <el-button @click="hospitalDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleHospitalsConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { CudMixin } from '@/mixins/cud-mixin'
import { assignHospitals, getHospitals } from '@/api/hospital-doctors/doctor-api'
import { getAll } from '@/api/common-api'

export default {
  name: 'Role',
  mixins: [PaginationMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/hospital-doctors/doctors',

      defaultData: {
        name: '',
        phone: '',
        title: ''
      },
      title: '医生',
      rules: {
        name: [
        ]
      },

      hospitalDrawerVisible: false,
      hospitals: [],
      hospitalIds: [],
      currentDoctorId: 0
    }
  },
  created() {
    getAll('/hospital-doctors/hospitals').then(response => {
      this.hospitals = response.data
    })
  },
  methods: {
    handleHospitalsAssign(index, row) {
      getHospitals(row.id).then(response => {
        this.currentDoctorId = row.id
        this.hospitalIds = response.data
        this.hospitalDrawerVisible = true
      })
    },
    handleHospitalsConfirm() {
      assignHospitals(this.currentDoctorId, this.hospitalIds).then(() => {
        this.$notify.success({
          title: '成功',
          message: '医院成功'
        })
        this.hospitalDrawerVisible = false
        this.fetchData()
      })
    }
  }
}
</script>

