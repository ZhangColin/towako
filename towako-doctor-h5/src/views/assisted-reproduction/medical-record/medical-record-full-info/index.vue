
<template>
  <div class="">
    <div class="header">
      <van-nav-bar
        :title="$route.meta.title"
        left-text="返回"
        left-arrow
        fixed
        border
        placeholder
        safe-area-inset-top
        @click-left="goBack"
      />
    </div>
    <van-row class="section">
      <van-tabs v-model="active" sticky animated>
        <van-tab title="基本信息">
          <van-swipe v-if="medicalRecordFullInfo.pictures && medicalRecordFullInfo.pictures.length>0" :autoplay="3000" style="height: 200px;">
            <van-swipe-item v-for="(image, index) in medicalRecordFullInfo.pictures" :key="index" @click="picturesPreview(index)">
              <van-image :src="image" fit="contain" width="100%" height="100%" />
            </van-swipe-item>
          </van-swipe>
          <van-cell-group>
            <van-cell title="医院" :value="(hospitals.find(hospital=>medicalRecordFullInfo.medicalRecordDetail.hospitalId===hospital.id) || {name: ''}).name" />
            <van-cell title="病案号" :value="medicalRecordFullInfo.medicalRecordDetail.recordNo" />
            <van-cell title="IVF(AIH)号" :value="medicalRecordFullInfo.medicalRecordDetail.ivf" />
            <van-cell title="姓名" :value="medicalRecordFullInfo.medicalRecordDetail.name" />
            <van-cell title="电话" :value="medicalRecordFullInfo.medicalRecordDetail.phone" />
            <van-cell title="年龄" :value="medicalRecordFullInfo.medicalRecordDetail.age" />
            <van-cell title="身份证" :value="medicalRecordFullInfo.medicalRecordDetail.idCard" />
            <van-cell title="主诉" :label="medicalRecordFullInfo.medicalRecordDetail.mainAppeal" />
            <van-cell title="现病史" :label="medicalRecordFullInfo.medicalRecordDetail.hpi" />
            <van-cell title="既往史" :label="medicalRecordFullInfo.medicalRecordDetail.medicalHistory" />
            <van-cell title="男方" :value="medicalRecordFullInfo.medicalRecordDetail.man" />
            <van-cell title="民族" :value="medicalRecordFullInfo.medicalRecordDetail.nation" />
            <van-cell title="婚姻状况" :value="{0:'未婚', 1:'已婚'}[medicalRecordFullInfo.medicalRecordDetail.maritalStatus]" />
          </van-cell-group>
        </van-tab>
        <van-tab title="诊断小节">
          <van-collapse v-model="collapseNames">
            <van-collapse-item v-for="period in medicalRecordFullInfo.treatmentPeriods" :key="period.treatmentPeriod.id" :title="`第 ${ period.treatmentPeriod.period } 疗程`" :name="period.treatmentPeriod.period">
              <p align="right"><van-button v-if="period.treatmentPeriod.period===medicalRecordFullInfo.treatmentPeriods.length" round plain type="primary" size="small" icon="edit" @click="showEditReport=true" /></p>
              <p>{{ period.treatmentPeriod.report || '尚未出具诊断小结' }}</p>
              <p align="right">{{ period.treatmentPeriod.reportDate }}</p>
            </van-collapse-item>
          </van-collapse>
        </van-tab>
        <van-tab title="检查报告">
          <van-row type="flex">
            <van-col span="6">
              <van-sidebar v-model="activeKey" style="width: 100%">
                <van-sidebar-item v-for="period in medicalRecordFullInfo.treatmentPeriods" :key="period.treatmentPeriod.id" :title="`第 ${ period.treatmentPeriod.period } 疗程`" @click="periodActive=period.treatmentPeriod.period" />
              </van-sidebar>
            </van-col>
            <van-col span="18">
              <template v-for="period in medicalRecordFullInfo.treatmentPeriods">
                <div v-if="periodActive===period.treatmentPeriod.period" :key="period.treatmentPeriod.id">
                  <van-row>
                    <van-cell title="末次月经" :value="period.treatmentPeriod.lastMenstrualPeriod" />
                    <van-cell title="BMI" :value="period.treatmentPeriod.bmi" />
                    <van-cell title="AMH" :value="period.treatmentPeriod.amh" />
                    <van-cell title="方案" :value="period.treatmentPeriod.plan" />
                  </van-row>
                  <van-row style="padding-top: 5px;">
                    <van-search
                      v-model="reportFilter"
                      style="padding: 10px;"
                      shape="round"
                      placeholder=""
                    />
                  </van-row>
                  <van-row v-for="(inpectionReport,index) in period.inspectionReports" :key="index">
                    <h1 style="padding-top: 15px; padding-bottom: 5px;">{{ inpectionReport.inspectionDate }}</h1>
                    <van-cell v-show="reportIsShow('周期天数')" title="周期天数" :value="inpectionReport.cycleNumber" />
                    <van-cell v-show="reportIsShow('来曲唑')" title="来曲唑" :value="inpectionReport.letrozole" />
                    <van-cell v-show="reportIsShow('hmg')" title="HMG(IU)" :value="inpectionReport.hmg" />
                    <van-cell v-show="reportIsShow('hcg')" title="HCG" :value="inpectionReport.hcg" />
                    <van-cell v-show="reportIsShow('达必佳')" title="达必佳" :value="inpectionReport.decapeptyl" />
                    <van-cell v-show="reportIsShow('mpa')" title="MPA(mg)" :value="inpectionReport.mpa" />
                    <van-cell v-show="reportIsShow('cc')" title="CC(mg)" :value="inpectionReport.cc" />
                    <van-cell v-show="reportIsShow('加尼瑞克/思则瑞')" title="加尼瑞克/思则瑞" :value="inpectionReport.ganirelix" />
                    <van-cell v-show="reportIsShow('芬吗通')" title="芬吗通" :value="inpectionReport.femoston" />
                    <van-cell v-show="reportIsShow('他达拉非')" title="他达拉非" :value="inpectionReport.tadalafil" />
                    <van-cell v-show="reportIsShow('内膜')" title="内膜" :value="inpectionReport.intima" />
                    <van-cell v-show="reportIsShow('内膜类型')" title="内膜类型" :value="inpectionReport.intimaType" />
                    <van-cell v-show="reportIsShow('lof')" title="LOF" :value="inpectionReport.lof1" />
                    <van-cell v-show="reportIsShow('lof')" title="" :value="inpectionReport.lof2" />
                    <van-cell v-show="reportIsShow('lof')" title="" :value="inpectionReport.lof3" />
                    <van-cell v-show="reportIsShow('lof')" title="" :value="inpectionReport.lof4" />
                    <van-cell v-show="reportIsShow('lof')" title="" :value="inpectionReport.lof5" />
                    <van-cell v-show="reportIsShow('lof')" title="" :value="inpectionReport.lof6" />
                    <van-cell v-show="reportIsShow('rof')" title="ROF" :value="inpectionReport.rof1" />
                    <van-cell v-show="reportIsShow('rof')" title="" :value="inpectionReport.rof2" />
                    <van-cell v-show="reportIsShow('rof')" title="" :value="inpectionReport.rof3" />
                    <van-cell v-show="reportIsShow('rof')" title="" :value="inpectionReport.rof4" />
                    <van-cell v-show="reportIsShow('rof')" title="" :value="inpectionReport.rof5" />
                    <van-cell v-show="reportIsShow('rof')" title="" :value="inpectionReport.rof6" />
                    <van-cell v-show="reportIsShow('fsh')" title="FSH(mlU/ml)" :value="inpectionReport.fsh" />
                    <van-cell v-show="reportIsShow('lh')" title="LH(mlU/ml)" :value="inpectionReport.lh" />
                    <van-cell v-show="reportIsShow('e2')" title="E2(pg/ml)" :value="inpectionReport.e2" />
                    <van-cell v-show="reportIsShow('t')" title="T(ng/dl)" :value="inpectionReport.t" />
                    <van-cell v-show="reportIsShow('p')" title="P(ng/ml)" :value="inpectionReport.p" />
                    <van-cell v-show="reportIsShow('prl')" title="PRL(ng/ml)" :value="inpectionReport.prl" />
                    <van-cell v-show="reportIsShow('b-hcg')" title="B-HCG(mlU/ml)" :value="inpectionReport.bhcg" />
                    <van-cell v-show="reportIsShow('白带检查')" title="白带检查" :value="inpectionReport.leucorrhea" />
                    <van-cell v-show="reportIsShow('取卵时间')" title="取卵时间" :value="inpectionReport.takeOvumDate" />
                  </van-row>
                </div>
              </template>
            </van-col>
          </van-row>
        </van-tab>
      </van-tabs>
    </van-row>
    <div class="footer">
    <!--  -->
    </div>
    <van-action-sheet v-model="showEditReport" title="诊断小结">
      <van-field
        v-model="report.content"
        rows="5"
        autosize
        label=""
        type="textarea"
        maxlength="1000"
        placeholder="请输入诊断小结"
        show-word-limit
      />
      <div style="margin: 16px;">
        <van-button round block type="info" @click="submitReport">提交</van-button>
      </div>
    </van-action-sheet>
  </div>
</template>

<script>

import { getMedicalRecordFullInfo } from '@/api/assisted-reproduction/medical-records-api'
import { getMyHospitals } from '@/api/hospital-doctors/doctor-api'
import { reportTreatmentPeriod } from '@/api/assisted-reproduction/medical-records-api'
import { Notify, ImagePreview } from 'vant'

export default {
  components: {},
  data() {
    return {
      active: 1,
      activeKey: 0,

      collapseNames: [],

      periodActive: 0,

      reportFilter: '',

      showEditReport: false,
      report: {
        content: '',
        periodId: 0
      },

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
      hospitals: []
    }
  },
  computed: {
  },
  created() {
  },
  beforeRouteUpdate(to, from, next) {

  },
  async mounted() {
    this.medicalRecordId = this.$route.query.medicalRecordId
    getMyHospitals().then(response => {
      this.hospitals = response.data
    })
    this.fetchData()
  },
  methods: {
    fetchData() {
      getMedicalRecordFullInfo(this.medicalRecordId).then(response => {
        for (const index in response.data.treatmentPeriods) {
          response.data.treatmentPeriods[index].inspectionReports.reverse()
        }

        this.medicalRecordFullInfo = response.data
        this.collapseNames = this.medicalRecordFullInfo.treatmentPeriods.map(p => p.treatmentPeriod.period)
        this.periodActive = this.medicalRecordFullInfo.treatmentPeriods[0].treatmentPeriod.period
        this.report.content = this.medicalRecordFullInfo.treatmentPeriods[0].treatmentPeriod.report
        this.report.periodId = this.medicalRecordFullInfo.treatmentPeriods[0].treatmentPeriod.id
      })
    },
    submitReport() {
      reportTreatmentPeriod(this.report.periodId, { report: this.report.content }).then(() => {
        Notify({ type: 'success', message: '诊断报告填写成功' })
        this.showEditReport = false
        this.fetchData()
      }).catch(() => {
      })
    },
    goBack() {
      const { query } = this.$route
      if (query.redirect) {
        this.$router.replace(query.redirect)
      } else {
        this.$router.back(-1)
      }
    },
    reportIsShow(title) {
      if (!this.reportFilter) {
        return true
      }

      const str = this.reportFilter.replace('，', ',')

      return str.split(',').filter(s => title.toLowerCase().startsWith(s.trim().toLowerCase())).length > 0
    },
    picturesPreview(index) {
      ImagePreview({
        images: this.medicalRecordFullInfo.pictures,
        startPosition: index
      })
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
