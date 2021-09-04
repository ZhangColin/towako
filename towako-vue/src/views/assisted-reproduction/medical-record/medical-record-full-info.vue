<template>
  <div class="app-container">
    <el-form label-position="right" label-width="140px">
      <el-col :span="8">
        <el-form-item label="医院">
          <el-input v-model="(hospitals.find(hospital=>medicalRecordFullInfo.medicalRecordDetail.hospitalId===hospital.id) || {name: ''}).name" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="病案号">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.recordNo" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="IVF(AIH)号">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.ivf" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="姓名">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.name" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="电话">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.phone" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="出生日期">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.birthday" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="年龄">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.age" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="身份证">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.idCard" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="主诉">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.mainAppeal" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="现病史">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.hpi" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="既往史">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.medicalHistory" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="男方">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.man" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="民族">
          <el-input v-model="medicalRecordFullInfo.medicalRecordDetail.nation" :readonly="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="婚姻状况">
          <el-input v-model="{0:'未婚', 1:'已婚'}[medicalRecordFullInfo.medicalRecordDetail.maritalStatus]" :readonly="true" />
        </el-form-item>
      </el-col>
    </el-form>
    <el-card v-if="medicalRecordFullInfo.treatmentPeriods.length>0">
      <div slot="header" class="clearfix">
        <span>诊断小结</span>
      </div>
      <el-collapse>
        <el-collapse-item v-for="period in medicalRecordFullInfo.treatmentPeriods" :key="period.treatmentPeriod.id" :title="`第 ${ period.treatmentPeriod.period } 疗程`">
          <p>{{ period.treatmentPeriod.report || '尚未出具诊断小结' }}</p>
          <p align="right">{{ period.treatmentPeriod.reportDate }}</p>
        </el-collapse-item>
      </el-collapse>
      <!--      <el-timeline>-->
      <!--        <el-timeline-item-->
      <!--          v-for="period in medicalRecordFullInfo.treatmentPeriods"-->
      <!--          :key="period.treatmentPeriod.id"-->
      <!--          :type="period.treatmentPeriod.report?'success':''"-->
      <!--          :timestamp="`第 ${ period.treatmentPeriod.period } 疗程`"-->
      <!--          placement="top"-->
      <!--        >-->
      <!--          <el-card>-->
      <!--            <p>{{ period.treatmentPeriod.report || '尚未出具诊断小结' }}</p>-->
      <!--            <p align="right">{{ period.treatmentPeriod.reportDate }}</p>-->
      <!--          </el-card>-->
      <!--        </el-timeline-item>-->
      <!--      </el-timeline>-->
    </el-card>
    <el-row :gutter="24" class="filter-container" style="padding-top: 10px">
      <el-col align="right">
        <el-button type="primary" align="right" @click="handleEditMedicalRecord()">编辑</el-button>
        <el-button type="primary" align="right" @click="handleAddTreatmentPeriod()">新疗程</el-button>
      </el-col>
    </el-row>
    <el-tabs v-if="medicalRecordFullInfo.treatmentPeriods.length>0" type="border-card">
      <el-tab-pane v-for="period in medicalRecordFullInfo.treatmentPeriods" :key="period.treatmentPeriod.id" :label="`第 ${ period.treatmentPeriod.period } 疗程`">
        <el-form label-position="right" label-width="140px">
          <el-col :span="12">
            <el-form-item label="末次月经">
              <el-input v-model="period.treatmentPeriod.lastMenstrualPeriod" :readonly="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="BMI">
              <el-input v-model="period.treatmentPeriod.bmi" :readonly="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="AMH">
              <el-input v-model="period.treatmentPeriod.amh" :readonly="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方案">
              <el-input v-model="period.treatmentPeriod.plan" :readonly="true" />
            </el-form-item>
          </el-col>
        </el-form>
        <el-row v-if="period.treatmentPeriod.period===medicalRecordFullInfo.treatmentPeriods.length" :gutter="24" class="filter-container">
          <el-col align="right">
            <el-button type="primary" align="right" @click="handleEditTreatmentPeriod(period.treatmentPeriod)">编辑</el-button>
            <el-button type="primary" align="right" @click="handleReportTreatmentPeriod(period.treatmentPeriod)">填写诊断报告</el-button>
            <el-button type="primary" align="right" @click="handleAddInspectionReport(period.treatmentPeriod.id)">填写检查</el-button>
          </el-col>
        </el-row>
        <el-table
          v-if="!!period.inspectionReports.length"
          :data="inspectionReportRowToCol(period.inspectionReports)"
          row-key="key"
          class="table-container"
          element-loading-text="加载中..."
          stripe
          border
          fit
          highlight-current-row
          :show-header="!!period.inspectionReports.length && period.treatmentPeriod.period===medicalRecordFullInfo.treatmentPeriods.length"
        >
          <el-table-column align="center" label="" prop="column0">
            <template slot-scope="{row}">
              <span><b v-html="row.column0 || '&nbsp;'" /></span>
            </template>
          </el-table-column>
          <el-table-column v-for="item in period.inspectionReports.length" :key="item" align="center" label="" :prop="'column'+item">
            <template v-if="item <= period.inspectionReports.length" slot="header" slot-scope="">
              <el-button type="primary" @click="handleEditInspectionReport(period.inspectionReports[item-1])">编辑</el-button>
              <el-button @click="handleDeleteInspectionReport(period.inspectionReports[item-1].id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-divider />
        <el-form label-position="right" label-width="140px">
          <el-col :span="24">
            <el-form-item label="诊断报告">
              <el-input v-model="period.treatmentPeriod.report" :readonly="true" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报告日期">
              <el-input v-model="period.treatmentPeriod.reportDate" :readonly="true" />
            </el-form-item>
          </el-col>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <el-card v-if="medicalRecordFullInfo.pictureGroups && medicalRecordFullInfo.pictureGroups.length>0">
      <div slot="header" class="clearfix">
        <span>病人自主上传病历图片</span>
      </div>
      <el-timeline>
        <el-timeline-item v-for="group in medicalRecordFullInfo.pictureGroups" :key="group.group" :timestamp="group.group" placement="top">
          <el-image v-for="picture in group.data" :key="picture.id" style="width: 200px; height: 200px" :src="picture.url" fit="contain" :preview-src-list="medicalRecordFullInfo.pictureGroups.flatMap(group=>group.data.map(p=>p.url))" />
        </el-timeline-item>
      </el-timeline>
    </el-card>
    <el-drawer
      title="修改病历"
      :visible.sync="medicalRecordDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="medicalRecordForm" :model="medicalRecordDetail" :rules="medicalRecordDetailRules" label-width="120px">
          <el-form-item label="医院" prop="hospitalId">
            <el-select v-model="medicalRecordDetail.hospitalId" placeholder="请选择医院" style="width: 100%">
              <el-option v-for="hospital in hospitals" :key="hospital.id" :label="hospital.name" :value="hospital.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="病案号" prop="recordNo">
            <el-input v-model="medicalRecordDetail.recordNo" />
          </el-form-item>
          <el-form-item label="IVF(AIH)号" prop="ivf">
            <el-input v-model="medicalRecordDetail.ivf" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="medicalRecordDetail.name" />
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="medicalRecordDetail.phone" />
          </el-form-item>
          <el-form-item label="身份证" prop="idCard">
            <el-input v-model="medicalRecordDetail.idCard" />
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
            <el-date-picker
              v-model="medicalRecordDetail.birthday"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="medicalRecordDetail.age" />
          </el-form-item>
          <el-form-item label="主诉" prop="mainAppeal">
            <el-input v-model="medicalRecordDetail.mainAppeal" />
          </el-form-item>
          <el-form-item label="现病史" prop="hpi">
            <el-input v-model="medicalRecordDetail.hpi" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" />
          </el-form-item>
          <el-form-item label="既往史" prop="medicalHistory">
            <el-input v-model="medicalRecordDetail.medicalHistory" type="textarea" :autosize="{ minRows: 2, maxRows: 4}" />
          </el-form-item>
          <el-form-item label="男方" prop="man">
            <el-input v-model="medicalRecordDetail.man" />
          </el-form-item>
          <el-form-item label="民族" prop="nation">
            <el-input v-model="medicalRecordDetail.nation" />
          </el-form-item>
          <el-form-item label="婚姻状况" prop="maritalStatus">
            <el-select v-model="medicalRecordDetail.maritalStatus" placeholder="请选择婚姻状况" style="width: 100%">
              <el-option label="已婚" :value="1" />
              <el-option label="未婚" :value="0" />
            </el-select>
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="medicalRecordDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleMedicalRecordConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer
      :title="treatmentPeriodDrawerTitle"
      :visible.sync="treatmentPeriodDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="treatmentPeriodForm" :model="treatmentPeriod" label-width="120px">
          <el-form-item label="末次月经" prop="lastMenstrualPeriod">
            <el-date-picker
              v-model="treatmentPeriod.lastMenstrualPeriod"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="BMI" prop="bmi">
            <el-input v-model="treatmentPeriod.bmi" />
          </el-form-item>
          <el-form-item label="AMH" prop="amh">
            <el-input v-model="treatmentPeriod.amh" />
          </el-form-item>
          <el-form-item label="方案" prop="plan">
            <el-input v-model="treatmentPeriod.plan" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="treatmentPeriodDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleTreatmentPeriodConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer
      :title="reportTreatmentPeriodDrawerTitle"
      :visible.sync="reportTreatmentPeriodDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="reportTreatmentPeriodForm" :model="treatmentPeriod" label-width="120px">
          <el-form-item label="诊断报告" prop="report">
            <el-input v-model="treatmentPeriod.report" type="textarea" :autosize="{ minRows: 6, maxRows: 10}" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="reportTreatmentPeriodDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleReportTreatmentPeriodConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer
      title="检查报告"
      :visible.sync="inspectionReportDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="inspectionReportForm" :model="inspectionReport" :rules="inspectionReportRules" label-width="120px">
          <el-form-item label="检查日期" prop="inspectionDate">
            <el-date-picker
              v-model="inspectionReport.inspectionDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="周期天数" prop="cycleNumber">
            <el-input v-model="inspectionReport.cycleNumber" />
          </el-form-item>
          <el-form-item label="来曲唑" prop="letrozole">
            <el-input v-model="inspectionReport.letrozole" />
          </el-form-item>
          <el-form-item label="HMG(IU)" prop="hmg">
            <el-input v-model="inspectionReport.hmg" />
          </el-form-item>
          <el-form-item label="HCG" prop="hmg">
            <el-input v-model="inspectionReport.hcg" />
          </el-form-item>
          <el-form-item label="达必佳" prop="decapeptyl">
            <el-input v-model="inspectionReport.decapeptyl" />
          </el-form-item>
          <el-form-item label="MPA(mg)" prop="mpa">
            <el-input v-model="inspectionReport.mpa" />
          </el-form-item>
          <el-form-item label="CC(mg)" prop="cc">
            <el-input v-model="inspectionReport.cc" />
          </el-form-item>
          <el-form-item label="加尼瑞克/思则瑞" prop="ganirelix">
            <el-input v-model="inspectionReport.ganirelix" />
          </el-form-item>
          <el-form-item label="芬吗通" prop="femoston">
            <el-input v-model="inspectionReport.femoston" />
          </el-form-item>
          <el-form-item label="他达拉非" prop="tadalafil">
            <el-input v-model="inspectionReport.tadalafil" />
          </el-form-item>
          <el-form-item label="内膜" prop="intima">
            <el-input v-model="inspectionReport.intima" />
          </el-form-item>
          <el-form-item label="内膜类型" prop="intimaType">
            <el-input v-model="inspectionReport.intimaType" />
          </el-form-item>
          <el-form-item label="LOF" prop="lof1">
            <el-input v-model="inspectionReport.lof1" />
          </el-form-item>
          <el-form-item label="" prop="lof2">
            <el-input v-model="inspectionReport.lof2" />
          </el-form-item>
          <el-form-item label="" prop="lof3">
            <el-input v-model="inspectionReport.lof3" />
          </el-form-item>
          <el-form-item label="" prop="lof4">
            <el-input v-model="inspectionReport.lof4" />
          </el-form-item>
          <el-form-item label="" prop="lof5">
            <el-input v-model="inspectionReport.lof5" />
          </el-form-item>
          <el-form-item label="" prop="lof6">
            <el-input v-model="inspectionReport.lof6" />
          </el-form-item>
          <el-form-item label="ROF" prop="rof1">
            <el-input v-model="inspectionReport.rof1" />
          </el-form-item>
          <el-form-item label="" prop="rof2">
            <el-input v-model="inspectionReport.rof2" />
          </el-form-item>
          <el-form-item label="" prop="rof3">
            <el-input v-model="inspectionReport.rof3" />
          </el-form-item>
          <el-form-item label="" prop="rof4">
            <el-input v-model="inspectionReport.rof4" />
          </el-form-item>
          <el-form-item label="" prop="rof5">
            <el-input v-model="inspectionReport.rof5" />
          </el-form-item>
          <el-form-item label="" prop="rof6">
            <el-input v-model="inspectionReport.rof6" />
          </el-form-item>
          <el-form-item label="FSH(mlU/ml)" prop="fsh">
            <el-input v-model="inspectionReport.fsh" />
          </el-form-item>
          <el-form-item label="LH(mlU/ml)" prop="lh">
            <el-input v-model="inspectionReport.lh" />
          </el-form-item>
          <el-form-item label="E2(pg/ml)" prop="e2">
            <el-input v-model="inspectionReport.e2" />
          </el-form-item>
          <el-form-item label="T(ng/dl)" prop="t">
            <el-input v-model="inspectionReport.t" />
          </el-form-item>
          <el-form-item label="P(ng/ml)" prop="p">
            <el-input v-model="inspectionReport.p" />
          </el-form-item>
          <el-form-item label="PRL(ng/ml)" prop="prl">
            <el-input v-model="inspectionReport.prl" />
          </el-form-item>
          <el-form-item label="B-HCG(mlU/ml)" prop="bhcg">
            <el-input v-model="inspectionReport.bhcg" />
          </el-form-item>
          <el-form-item label="白带检查" prop="leucorrhea">
            <el-input v-model="inspectionReport.leucorrhea" />
          </el-form-item>
          <el-form-item label="取卵时间" prop="takeOvumDate">
            <el-date-picker
              v-model="inspectionReport.takeOvumDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="inspectionReportDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleInspectionReportConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getMedicalRecordFullInfo, reportTreatmentPeriod } from '@/api/assisted-reproduction/medical-record-api'
import { getMyHospitals } from '@/api/hospital-doctors/doctor-api'
import { add, edit, remove } from '@/api/common-api'

export default {
  data() {
    return {
      medicalRecordId: 0,
      medicalRecordFullInfo: {
        medicalRecordDetail: {
          id: '',
          hospitalId: '',
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
        treatmentPeriods: [

        ]
      },
      hospitals: [],

      medicalRecordDrawerVisible: false,
      medicalRecordDetail: {},
      medicalRecordDetailRules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        birthday: [{ required: true, message: '请输入出生日期', trigger: 'blur' }]
      },

      treatmentPeriodDrawerTitle: '',
      treatmentPeriodDrawerVisible: false,
      treatmentPeriod: {},

      reportTreatmentPeriodDrawerTitle: '',
      reportTreatmentPeriodDrawerVisible: false,

      inspectionReportDrawerTitle: '',
      inspectionReportDrawerVisible: false,
      inspectionReport: {},
      inspectionReportRules: {
        inspectionDate: [{ required: true, message: '请输入检查日期', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.medicalRecordId = this.$route.query.medicalRecordId
    getMyHospitals().then(response => {
      this.hospitals = response.data
    })
    this.fetchData()
  },
  methods: {
    fetchData() {
      getMedicalRecordFullInfo(this.medicalRecordId).then(response => {
        this.medicalRecordFullInfo = response.data
      })
    },
    handleEditMedicalRecord() {
      this.medicalRecordDetail = Object.assign({}, this.medicalRecordFullInfo.medicalRecordDetail)
      this.medicalRecordDrawerVisible = true
    },
    handleMedicalRecordConfirm() {
      this.$refs['medicalRecordForm'].validate((valid) => {
        if (valid) {
          edit('/assisted-reproduction/medical-records', this.medicalRecordId, this.medicalRecordDetail).then(() => {
            this.$notify.success({
              title: '成功',
              message: '病历修改成功'
            })
            this.medicalRecordDrawerVisible = false
            this.fetchData()
          }).catch(() => {})
        }
      })
    },
    handleAddTreatmentPeriod() {
      this.treatmentPeriodDrawerTitle = `第 ${this.medicalRecordFullInfo.treatmentPeriods.length + 1} 疗程`
      this.treatmentPeriod = {
        medicalRecordId: this.medicalRecordId,
        period: this.medicalRecordFullInfo.treatmentPeriods.length + 1
      }
      this.treatmentPeriodDrawerVisible = true
    },
    handleEditTreatmentPeriod(tp) {
      this.treatmentPeriodDrawerTitle = `第 ${tp.period} 疗程`
      this.treatmentPeriod = { ...tp }
      this.treatmentPeriodDrawerVisible = true
    },
    handleTreatmentPeriodConfirm() {
      if (!this.treatmentPeriod.id) {
        add('/assisted-reproduction/treatment-periods', this.treatmentPeriod).then(() => {
          this.$notify.success({
            title: '成功',
            message: '开启新疗程'
          })
          this.treatmentPeriodDrawerVisible = false
          this.fetchData()
        }).catch(() => {
        })
      } else {
        edit('/assisted-reproduction/treatment-periods', this.treatmentPeriod.id, this.treatmentPeriod).then(() => {
          this.$notify.success({
            title: '成功',
            message: '疗程编辑成功'
          })
          this.treatmentPeriodDrawerVisible = false
          this.fetchData()
        }).catch(() => {
        })
      }
    },
    handleReportTreatmentPeriod(tp) {
      this.reportTreatmentPeriodDrawerTitle = `第 ${tp.period} 疗程诊断报告`
      this.treatmentPeriod = { ...tp }
      this.reportTreatmentPeriodDrawerVisible = true
    },
    handleReportTreatmentPeriodConfirm() {
      reportTreatmentPeriod(this.treatmentPeriod.id, { report: this.treatmentPeriod.report }).then(() => {
        this.$notify.success({
          title: '成功',
          message: '诊断报告填写成功'
        })
        this.reportTreatmentPeriodDrawerVisible = false
        this.fetchData()
      }).catch(() => {
      })
    },
    handleAddInspectionReport(treatmentPeriodId) {
      this.inspectionReport = {
        treatmentPeriodId: treatmentPeriodId
      }
      this.inspectionReportDrawerVisible = true
    },
    handleEditInspectionReport(ir) {
      this.inspectionReport = { ...ir }
      this.inspectionReportDrawerVisible = true
    },
    handleInspectionReportConfirm() {
      this.$refs['inspectionReportForm'].validate((valid) => {
        if (valid) {
          if (!this.inspectionReport.id) {
            add('/assisted-reproduction/inspection-reports', this.inspectionReport).then(() => {
              this.$notify.success({
                title: '成功',
                message: '检查报告填写成功'
              })
              this.inspectionReportDrawerVisible = false
              this.fetchData()
            }).catch(() => {
            })
          } else {
            edit('/assisted-reproduction/inspection-reports', this.inspectionReport.id, this.inspectionReport).then(() => {
              this.$notify.success({
                title: '成功',
                message: '检查报告填写成功'
              })
              this.inspectionReportDrawerVisible = false
              this.fetchData()
            }).catch(() => {
            })
          }
        }
      })
    },
    handleDeleteInspectionReport(id) {
      remove('/assisted-reproduction/inspection-reports', id).then(() => {
        this.$notify.success({
          title: '成功',
          message: '检查报告删除成功'
        })
        this.inspectionReportDrawerVisible = false
        this.fetchData()
      }).catch(() => {
      })
    },
    inspectionReportRowToCol(inspectionReports) {
      const datas = []
      for (let i = 0; i < 34; i++) {
        datas.push({ key: i })
      }

      datas[0]['column0'] = '检查日期'
      datas[1]['column0'] = '周期天数'
      datas[2]['column0'] = '来曲唑'
      datas[3]['column0'] = 'HMG(IU)'
      datas[4]['column0'] = 'HCG'
      datas[5]['column0'] = '达必佳'
      datas[6]['column0'] = 'MPA(mg)'
      datas[7]['column0'] = 'CC(mg)'
      datas[8]['column0'] = '加尼瑞克/思则瑞'
      datas[9]['column0'] = '芬吗通'
      datas[10]['column0'] = '他达拉非'
      datas[11]['column0'] = '内膜'
      datas[12]['column0'] = '内膜类型'
      datas[13]['column0'] = 'LOF'
      datas[14]['column0'] = ''
      datas[15]['column0'] = ''
      datas[16]['column0'] = ''
      datas[17]['column0'] = ''
      datas[18]['column0'] = ''
      datas[19]['column0'] = 'ROF'
      datas[20]['column0'] = ''
      datas[21]['column0'] = ''
      datas[22]['column0'] = ''
      datas[23]['column0'] = ''
      datas[24]['column0'] = ''
      datas[25]['column0'] = 'FSH(mlU/ml)'
      datas[26]['column0'] = 'LH(mlU/ml)'
      datas[27]['column0'] = 'E2(pg/ml)'
      datas[28]['column0'] = 'T(ng/dl)'
      datas[29]['column0'] = 'P(ng/ml)'
      datas[30]['column0'] = 'PRL(ng/ml)'
      datas[31]['column0'] = 'B-HCG(mlU/ml)'
      datas[32]['column0'] = '白带检查'
      datas[33]['column0'] = '取卵时间'

      for (let i = 0; i < inspectionReports.length; i++) {
        const report = inspectionReports[i]

        datas[0]['column' + (i + 1)] = report.inspectionDate
        datas[1]['column' + (i + 1)] = report.cycleNumber
        datas[2]['column' + (i + 1)] = report.letrozole
        datas[3]['column' + (i + 1)] = report.hmg
        datas[4]['column' + (i + 1)] = report.hcg
        datas[5]['column' + (i + 1)] = report.decapeptyl
        datas[6]['column' + (i + 1)] = report.mpa
        datas[7]['column' + (i + 1)] = report.cc
        datas[8]['column' + (i + 1)] = report.ganirelix
        datas[9]['column' + (i + 1)] = report.femoston
        datas[10]['column' + (i + 1)] = report.tadalafil
        datas[11]['column' + (i + 1)] = report.intima
        datas[12]['column' + (i + 1)] = report.intimaType
        datas[13]['column' + (i + 1)] = report.lof1
        datas[14]['column' + (i + 1)] = report.lof2
        datas[15]['column' + (i + 1)] = report.lof3
        datas[16]['column' + (i + 1)] = report.lof4
        datas[17]['column' + (i + 1)] = report.lof5
        datas[18]['column' + (i + 1)] = report.lof6
        datas[19]['column' + (i + 1)] = report.rof1
        datas[20]['column' + (i + 1)] = report.rof2
        datas[21]['column' + (i + 1)] = report.rof3
        datas[22]['column' + (i + 1)] = report.rof4
        datas[23]['column' + (i + 1)] = report.rof5
        datas[24]['column' + (i + 1)] = report.rof6
        datas[25]['column' + (i + 1)] = report.fsh
        datas[26]['column' + (i + 1)] = report.lh
        datas[27]['column' + (i + 1)] = report.e2
        datas[28]['column' + (i + 1)] = report.t
        datas[29]['column' + (i + 1)] = report.p
        datas[30]['column' + (i + 1)] = report.prl
        datas[31]['column' + (i + 1)] = report.bhcg
        datas[32]['column' + (i + 1)] = report.leucorrhea
        datas[33]['column' + (i + 1)] = report.takeOvumDate
      }

      return datas
    }
  }
}
</script>
