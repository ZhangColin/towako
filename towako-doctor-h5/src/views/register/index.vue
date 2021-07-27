<template>
  <div class="login">
    <div class="header">
      <van-nav-bar
        :title="$route.meta.title"
        style="display: block"
        safe-area-inset-top
      />
    </div>

    <div class="section">
      <van-field
        readonly
        required
        clickable
        name="picker"
        :value="(hospitals.find(h=>form.hospitalId===h.id)||{name: ''}).name"
        label="医院"
        placeholder="点击选择医院"
        :error-message="fields.hospitalId ? rule.hospitalId.message : ''"
        @click="showPicker = true"
      />
      <van-popup v-model="showPicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="hospitals"
          value-key="name"
          @confirm="onConfirm"
          @cancel="showPicker = false"
        />
      </van-popup>
      <van-field
        v-model="form.name"
        required
        label="姓名"
        placeholder="请输入姓名"
        :error-message="fields.name ? rule.name.message : ''"
      />
      <van-field
        v-model="form.phone"
        required
        type="tel"
        label="手机号"
        placeholder="请输入手机号"
        :error-message="fields.phone ? rule.phone.message : ''"
      />

      <van-field
        v-model="form.code"
        center
        clearable
        type="digit"
        required
        label="短信验证码"
        placeholder="请输入短信验证码"
        :error-message="fields.code ? rule.code.message : ''"
      >
        <template #button>
          <van-button
            :disabled="config.sms_text !== '发送验证码'"
            size="mini"
            type="primary"
            @click="onSendSmsCode"
          >{{ config.sms_text }}
          </van-button>
        </template>
      </van-field>
      <van-field
        v-model="form.email"
        label="邮箱"
        placeholder="请输入邮箱"
      />
      <van-field
        v-model="form.password"
        type="password"
        required
        label="密码"
        placeholder="请输入密码"
        :error-message="fields.password ? rule.password.message : ''"
      />
      <van-field
        v-model="form.confirmPassword"
        type="password"
        required
        label="确认密码"
        placeholder="请输入密码"
        :error-message="fields.confirmPassword ? rule.confirmPassword.message : ''"
      />
      <van-field
        readonly
        clickable
        name="picker"
        :value="form.title"
        label="职称"
        placeholder="点击选择职称"
        @click="showTitlePicker = true"
      />
      <van-popup v-model="showTitlePicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="['主任医师','副主任医师','主治医师','住院医师','医助']"
          @confirm="onTitleConfirm"
          @cancel="showTitlePicker = false"
        />
      </van-popup>
    </div>

    <van-checkbox
      v-model="config.agreement"
      icon-size="16px"
      class="agree"
      checked-color="#ee0a24"
    >
      <span>阅读并同意</span>
      <span class="content" @click.stop="$router.push({ path: '/agreement/cooperation' })">《渠道推广合作协议》</span>
      <span>、</span>
      <span class="content" @click.stop="$router.push({ path: '/agreement/commission' })">《优生惠平台返佣规则》</span>
    </van-checkbox>

    <div class="footer">
      <div class="main">
        <van-button
          class="login_btn"
          type="primary"
          round
          block
          size="normal"
          @click="onSubmit"
        >注册
        </van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { sendCode } from '@/api/sms/captcha-api'
import { doctorRegister } from '@/api/hospital-doctors/doctor-api'
import { getAllHospitals } from '@/api/hospital-doctors/hospital-api'
import Schema from 'async-validator'
import { encrypt } from '@/utils/crypto'

export default {
  components: {},
  data() {
    return {
      hospitals: [],
      showPicker: false,
      showTitlePicker: false,
      config: {
        agreement: false,
        sms_text: '发送验证码'
      },
      form: {
        hospitalId: '',
        name: '',
        phone: '',
        code: '',
        email: '',
        title: '',
        password: '',
        confirmPassword: ''
      },
      fields: {
        tel: '',
        code: ''
      },
      rule: {
        hospitalId: {
          type: 'string',
          required: true,
          message: '请选择医院'
        },
        name: {
          type: 'string',
          required: true,
          message: '请填写姓名'
        },
        phone: {
          type: 'string',
          len: 11,
          required: true,
          message: '手机号格式错误'
        },
        code: {
          type: 'string',
          len: 6,
          required: true,
          message: '验证码格式错误'
        },
        password: {
          type: 'string',
          required: true,
          message: '请填写密码'
        },
        confirmPassword: {
          type: 'string',
          required: true,
          message: '请填写确认密码'
        }
      }
    }
  },
  computed: {},
  created() {
    getAllHospitals().then(response => {
      this.hospitals = response.data
    })
  },
  beforeRouteUpdate(to, from, next) {
  },
  async mounted() {
  },
  methods: {
    async onSubmit() {
      if (!this.getAgreementStatus()) return

      const validator = new Schema(this.rule)
      let pass = false
      this.fields = {}
      validator.validate(this.form, (errors, fields) => {
        if (errors) {
          this.fields = fields
          pass = false
          return
        }
        pass = true
      })
      if (!pass) return

      if (this.form.password !== this.form.confirmPassword) {
        this.$toast.fail({
          message: '密码不一致',
          duration: 1200
        })
        return
      }

      doctorRegister({
        hospitalId: this.form.hospitalId,
        name: this.form.name,
        title: this.form.title,
        phone: this.form.phone,
        code: this.form.code,
        email: this.form.email,
        password: encrypt(this.form.password)
      }).then(() => {
        this.$toast('注册成功')
        this.$router.push({ path: '/login' })
      }).catch(error => {
        this.$toast.fail(error.message)
      })
    },

    async onSendSmsCode() {
      const validator = new Schema(this.rule)
      let pass = false
      this.fields = {}
      validator.validate(this.form, (errors, fields) => {
        if (fields.phone) {
          this.fields = { phone: fields.phone }
          pass = false
          return
        }
        pass = true
      })
      if (!pass) return

      this.$toast.loading({
        message: '发送中...',
        forbidClick: true,
        duration: 0
      })
      const response = await sendCode(this.form.phone).catch(() => {
      })
      if (response.data) {
        this.$toast('已发送短信验证码')
        this.onCountdownSms()
      }
    },

    getAgreementStatus() {
      const { agreement } = this.config
      if (!agreement) {
        this.$toast.fail({
          message: '请阅读并同意\n《渠道合作协议》',
          duration: 1200
        })
      }
      return agreement
    },
    onCountdownSms() {
      let sms_text = 60
      const countdown = setInterval(() => {
        if (sms_text <= 0) {
          clearInterval(countdown)
          this.config.sms_text = '发送验证码'
          return
        }
        sms_text--
        this.config.sms_text = ' ' + sms_text + ' s '
      }, 1000)
    },
    onConfirm(value) {
      this.form.hospitalId = value.id
      this.showPicker = false
    },
    onTitleConfirm(value) {
      this.form.title = value
      this.showTitlePicker = false
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
