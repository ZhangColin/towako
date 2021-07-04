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
            <el-table-column align="center" label="主键" prop="id" />
      <el-table-column align="center" label="病历Id" prop="medicalRecordId" />
      <el-table-column align="center" label="第几疗程" prop="period" />
      <el-table-column align="center" label="末次月经" prop="lastMenstrualPeriod" />
      <el-table-column align="center" label="BMI" prop="bmi" />
      <el-table-column align="center" label="AMH" prop="amh" />
      <el-table-column align="center" label="方案" prop="plan" />
      <el-table-column align="center" label="诊断报告" prop="report" />
      <el-table-column align="center" label="报告日期" prop="reportDate" />
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
          
          <el-form-item label="病历Id" prop="medicalRecordId">
             <el-input v-model="entityData.medicalRecordId" />
           </el-form-item>
          <el-form-item label="第几疗程" prop="period">
             <el-input v-model="entityData.period" />
           </el-form-item>
          <el-form-item label="末次月经" prop="lastMenstrualPeriod">
             <el-input v-model="entityData.lastMenstrualPeriod" />
           </el-form-item>
          <el-form-item label="BMI" prop="bmi">
             <el-input v-model="entityData.bmi" />
           </el-form-item>
          <el-form-item label="AMH" prop="amh">
             <el-input v-model="entityData.amh" />
           </el-form-item>
          <el-form-item label="方案" prop="plan">
             <el-input v-model="entityData.plan" />
           </el-form-item>
          <el-form-item label="诊断报告" prop="report">
             <el-input v-model="entityData.report" />
           </el-form-item>
          <el-form-item label="报告日期" prop="reportDate">
             <el-input v-model="entityData.reportDate" />
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
      apiBaseUrl: '/treatmentPeriods',

      defaultData: {
        
        medicalRecordId: '',
        period: '',
        lastMenstrualPeriod: '',
        bmi: '',
        amh: '',
        plan: '',
        report: '',
        reportDate: ''
      },
      title: '疗程',
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

