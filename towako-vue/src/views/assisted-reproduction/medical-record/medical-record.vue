<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
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
            <el-table-column align="center" label="病历Id" prop="id" />
      <el-table-column align="center" label="病案号" prop="recordNo" />
      <el-table-column align="center" label="IVF(AIH)号" prop="ivf" />
      <el-table-column align="center" label="姓名" prop="name" />
      <el-table-column align="center" label="电话" prop="phone" />
      <el-table-column align="center" label="身份证" prop="idCard" />
      <el-table-column align="center" label="年龄" prop="age" />
      <el-table-column align="center" label="主诉" prop="mainAppeal" />
      <el-table-column align="center" label="现病史" prop="hpi" />
      <el-table-column align="center" label="既往史" prop="medicalHistory" />
      <el-table-column align="center" label="男方" prop="man" />
      <el-table-column align="center" label="民族" prop="nation" />
      <el-table-column align="center" label="婚姻状况" prop="maritalStatus" />
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
          
          <el-form-item label="病案号" prop="recordNo">
             <el-input v-model="entityData.recordNo" />
           </el-form-item>
          <el-form-item label="IVF(AIH)号" prop="ivf">
             <el-input v-model="entityData.ivf" />
           </el-form-item>
          <el-form-item label="姓名" prop="name">
             <el-input v-model="entityData.name" />
           </el-form-item>
          <el-form-item label="电话" prop="phone">
             <el-input v-model="entityData.phone" />
           </el-form-item>
          <el-form-item label="身份证" prop="idCard">
             <el-input v-model="entityData.idCard" />
           </el-form-item>
          <el-form-item label="年龄" prop="age">
             <el-input v-model="entityData.age" />
           </el-form-item>
          <el-form-item label="主诉" prop="mainAppeal">
             <el-input v-model="entityData.mainAppeal" />
           </el-form-item>
          <el-form-item label="现病史" prop="hpi">
             <el-input v-model="entityData.hpi" />
           </el-form-item>
          <el-form-item label="既往史" prop="medicalHistory">
             <el-input v-model="entityData.medicalHistory" />
           </el-form-item>
          <el-form-item label="男方" prop="man">
             <el-input v-model="entityData.man" />
           </el-form-item>
          <el-form-item label="民族" prop="nation">
             <el-input v-model="entityData.nation" />
           </el-form-item>
          <el-form-item label="婚姻状况" prop="maritalStatus">
             <el-input v-model="entityData.maritalStatus" />
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
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { CudMixin } from '@/mixins/cud-mixin'

export default {
  name: 'Role',
  mixins: [PaginationMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/medicalRecords',

      defaultData: {
        
        recordNo: '',
        ivf: '',
        name: '',
        phone: '',
        idCard: '',
        age: '',
        mainAppeal: '',
        hpi: '',
        medicalHistory: '',
        man: '',
        nation: '',
        maritalStatus: ''
      },
      title: '病历',
      rules: {
        name: [
        ]
      }
    }
  },
  created() {
  },
  methods: {
  }
}
</script>

