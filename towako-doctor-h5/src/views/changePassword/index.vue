
<template>
  <div class="login">
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

    <div class="section">
      <van-field
        v-model="form.oldPassword"
        type="password"
        required
        label="旧密码"
        placeholder="请输入旧密码"
        :error-message="fields.oldPassword ? rule.oldPassword.message : ''"
      />
      <van-field
        v-model="form.newPassword"
        type="password"
        required
        label="新密码"
        placeholder="请输入新密码"
        :error-message="fields.newPassword ? rule.newPassword.message : ''"
      />
      <van-field
        v-model="form.confirmPassword"
        type="password"
        required
        label="确认密码"
        placeholder="请输入密码"
        :error-message="fields.confirmPassword ? rule.confirmPassword.message : ''"
      />
    </div>

    <div class="footer">
      <div class="main">
        <van-button
          class="login_btn"
          type="primary"
          round
          block
          size="normal"
          @click="onSubmit"
        >确认</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { changePassword } from '@/api/user'
import Schema from 'async-validator'
import { encrypt } from '@/utils/crypto'
export default {
  components: {},
  data() {
    return {
      form: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      fields: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rule: {
        oldPassword: {
          type: 'string',
          required: true,
          message: '请填写旧密码'
        },
        newPassword: {
          type: 'string',
          required: true,
          message: '请填写新密码'
        },
        confirmPassword: {
          type: 'string',
          required: true,
          message: '请填写确认密码'
        }
      }
    }
  },
  computed: {
  },
  created() {
  },
  beforeRouteUpdate(to, from, next) {
  },
  async mounted() {
  },
  methods: {
    async onSubmit() {
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

      if (this.form.newPassword !== this.form.confirmPassword) {
        this.$toast.fail({
          message: '新密码与确认密码不一致',
          duration: 1200
        })
        return
      }

      changePassword(encrypt(this.form.oldPassword), encrypt(this.form.newPassword)).then(() => {
        this.$toast('修改成功')
        this.$store.commit('user/SET_LOGOUT')
        this.$router.push({ path: '/login' })
      }).catch(error => {
        this.$toast.fail(error.message)
      })
    },
    goBack() {
      const { query } = this.$route
      if (query.redirect) {
        this.$router.replace(query.redirect)
      } else {
        this.$router.back(-1)
      }
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
