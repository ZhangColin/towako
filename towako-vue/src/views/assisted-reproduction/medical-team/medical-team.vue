<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6" />
      <el-col :span="12">
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
      <el-table-column align="center" label="医生" prop="doctorId">
        <template slot-scope="{row}">
          <span>{{
            (doctors.find(doctor=>row.doctorId===doctor.id) || {name: ''}).name
          }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="排序" prop="sort" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleDelete(scope.$index, scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-drawer
      :title="drawerTitle"
      :visible.sync="drawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="entityDataForm" :model="entityData" :rules="rules" label-width="120px">
          <el-form-item label="医生" prop="doctorId">
            <el-select v-model="entityData.doctorId" placeholder="请选择医生" style="width: 100%">
              <el-option v-for="doctor in doctors" :key="doctor.id" :label="doctor.name" :value="doctor.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model="entityData.sort" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="drawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { ListMixin } from '@/mixins/list-mixin'
import { CudMixin } from '@/mixins/cud-mixin'
import { getAll, search } from '@/api/common-api'

export default {
  name: 'Role',
  mixins: [ListMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/assisted-reproduction/medical-teams',
      useSearch: true,

      medicalRecordId: 0,
      defaultData: {
        medicalRecordId: '',
        doctorId: '',
        sort: ''
      },
      title: '医疗团队',
      rules: {
        name: [
        ]
      },
      doctors: []
    }
  },
  created() {
    getAll('/hospital-doctors/doctors/list').then(response => { this.doctors = response.data })
  },
  methods: {
    fetchData() {
      this.medicalRecordId = this.$route.query.medicalRecordId
      this.queryParam.medicalRecordId = this.medicalRecordId
      this.defaultData.medicalRecordId = this.medicalRecordId
      this.loading = true
      search(this.apiBaseUrl, this.queryParam).then(response => {
        this.dataSource = response.data
        this.loading = false
      })
    }
  }
}
</script>

