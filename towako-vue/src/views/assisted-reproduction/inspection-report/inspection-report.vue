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
      <el-table-column align="center" label="疗程Id" prop="treatmentPeriodId" />
      <el-table-column align="center" label="检查日期" prop="inspectionDate" />
      <el-table-column align="center" label="周期天数" prop="cycleNumber" />
      <el-table-column align="center" label="来曲唑" prop="letrozole" />
      <el-table-column align="center" label="HMG(IU)" prop="hmg" />
      <el-table-column align="center" label="MPA(mg)" prop="mpa" />
      <el-table-column align="center" label="CC(mg)" prop="cc" />
      <el-table-column align="center" label="加尼瑞克/思则瑞" prop="ganirelix" />
      <el-table-column align="center" label="芬吗通" prop="femoston" />
      <el-table-column align="center" label="他达拉非" prop="tadalafil" />
      <el-table-column align="center" label="内膜" prop="intima" />
      <el-table-column align="center" label="内膜类型" prop="intimaType" />
      <el-table-column align="center" label="LOF1" prop="lof1" />
      <el-table-column align="center" label="LOF2" prop="lof2" />
      <el-table-column align="center" label="LOF3" prop="lof3" />
      <el-table-column align="center" label="LOF4" prop="lof4" />
      <el-table-column align="center" label="LOF5" prop="lof5" />
      <el-table-column align="center" label="LOF6" prop="lof6" />
      <el-table-column align="center" label="ROF1" prop="rof1" />
      <el-table-column align="center" label="ROF2" prop="rof2" />
      <el-table-column align="center" label="ROF3" prop="rof3" />
      <el-table-column align="center" label="ROF4" prop="rof4" />
      <el-table-column align="center" label="ROF5" prop="rof5" />
      <el-table-column align="center" label="ROF6" prop="rof6" />
      <el-table-column align="center" label="FSH(mlU/ml)" prop="fsh" />
      <el-table-column align="center" label="LH(mlU/ml)" prop="lh" />
      <el-table-column align="center" label="E2(pg/ml)" prop="e2" />
      <el-table-column align="center" label="T(ng/dl)" prop="t" />
      <el-table-column align="center" label="P(ng/ml)" prop="p" />
      <el-table-column align="center" label="PRL(ng/ml)" prop="prl" />
      <el-table-column align="center" label="B-HCG(mlU/ml)" prop="bhcg" />
      <el-table-column align="center" label="白带检查" prop="leucorrhea" />
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
          
          <el-form-item label="疗程Id" prop="treatmentPeriodId">
             <el-input v-model="entityData.treatmentPeriodId" />
           </el-form-item>
          <el-form-item label="检查日期" prop="inspectionDate">
             <el-input v-model="entityData.inspectionDate" />
           </el-form-item>
          <el-form-item label="周期天数" prop="cycleNumber">
             <el-input v-model="entityData.cycleNumber" />
           </el-form-item>
          <el-form-item label="来曲唑" prop="letrozole">
             <el-input v-model="entityData.letrozole" />
           </el-form-item>
          <el-form-item label="HMG(IU)" prop="hmg">
             <el-input v-model="entityData.hmg" />
           </el-form-item>
          <el-form-item label="MPA(mg)" prop="mpa">
             <el-input v-model="entityData.mpa" />
           </el-form-item>
          <el-form-item label="CC(mg)" prop="cc">
             <el-input v-model="entityData.cc" />
           </el-form-item>
          <el-form-item label="加尼瑞克/思则瑞" prop="ganirelix">
             <el-input v-model="entityData.ganirelix" />
           </el-form-item>
          <el-form-item label="芬吗通" prop="femoston">
             <el-input v-model="entityData.femoston" />
           </el-form-item>
          <el-form-item label="他达拉非" prop="tadalafil">
             <el-input v-model="entityData.tadalafil" />
           </el-form-item>
          <el-form-item label="内膜" prop="intima">
             <el-input v-model="entityData.intima" />
           </el-form-item>
          <el-form-item label="内膜类型" prop="intimaType">
             <el-input v-model="entityData.intimaType" />
           </el-form-item>
          <el-form-item label="LOF1" prop="lof1">
             <el-input v-model="entityData.lof1" />
           </el-form-item>
          <el-form-item label="LOF2" prop="lof2">
             <el-input v-model="entityData.lof2" />
           </el-form-item>
          <el-form-item label="LOF3" prop="lof3">
             <el-input v-model="entityData.lof3" />
           </el-form-item>
          <el-form-item label="LOF4" prop="lof4">
             <el-input v-model="entityData.lof4" />
           </el-form-item>
          <el-form-item label="LOF5" prop="lof5">
             <el-input v-model="entityData.lof5" />
           </el-form-item>
          <el-form-item label="LOF6" prop="lof6">
             <el-input v-model="entityData.lof6" />
           </el-form-item>
          <el-form-item label="ROF1" prop="rof1">
             <el-input v-model="entityData.rof1" />
           </el-form-item>
          <el-form-item label="ROF2" prop="rof2">
             <el-input v-model="entityData.rof2" />
           </el-form-item>
          <el-form-item label="ROF3" prop="rof3">
             <el-input v-model="entityData.rof3" />
           </el-form-item>
          <el-form-item label="ROF4" prop="rof4">
             <el-input v-model="entityData.rof4" />
           </el-form-item>
          <el-form-item label="ROF5" prop="rof5">
             <el-input v-model="entityData.rof5" />
           </el-form-item>
          <el-form-item label="ROF6" prop="rof6">
             <el-input v-model="entityData.rof6" />
           </el-form-item>
          <el-form-item label="FSH(mlU/ml)" prop="fsh">
             <el-input v-model="entityData.fsh" />
           </el-form-item>
          <el-form-item label="LH(mlU/ml)" prop="lh">
             <el-input v-model="entityData.lh" />
           </el-form-item>
          <el-form-item label="E2(pg/ml)" prop="e2">
             <el-input v-model="entityData.e2" />
           </el-form-item>
          <el-form-item label="T(ng/dl)" prop="t">
             <el-input v-model="entityData.t" />
           </el-form-item>
          <el-form-item label="P(ng/ml)" prop="p">
             <el-input v-model="entityData.p" />
           </el-form-item>
          <el-form-item label="PRL(ng/ml)" prop="prl">
             <el-input v-model="entityData.prl" />
           </el-form-item>
          <el-form-item label="B-HCG(mlU/ml)" prop="bhcg">
             <el-input v-model="entityData.bhcg" />
           </el-form-item>
          <el-form-item label="白带检查" prop="leucorrhea">
             <el-input v-model="entityData.leucorrhea" />
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
      apiBaseUrl: '/inspectionReports',

      defaultData: {
        
        treatmentPeriodId: '',
        inspectionDate: '',
        cycleNumber: '',
        letrozole: '',
        hmg: '',
        mpa: '',
        cc: '',
        ganirelix: '',
        femoston: '',
        tadalafil: '',
        intima: '',
        intimaType: '',
        lof1: '',
        lof2: '',
        lof3: '',
        lof4: '',
        lof5: '',
        lof6: '',
        rof1: '',
        rof2: '',
        rof3: '',
        rof4: '',
        rof5: '',
        rof6: '',
        fsh: '',
        lh: '',
        e2: '',
        t: '',
        p: '',
        prl: '',
        bhcg: '',
        leucorrhea: ''
      },
      title: '检查报告',
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

