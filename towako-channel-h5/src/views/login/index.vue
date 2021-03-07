
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
        v-model="form.username"
        required
        type="tel"
        label="手机号"
        placeholder="请输入手机号"
        :error-message="fields.username ? rule.username.message : ''"
      />

      <van-field v-model="form.password" type="password" required label="密码" placeholder="请输入密码" />
    </div>

    <div class="gp-h30" />

    <div class="footer">
      <div class="main">
        <van-button
          class="login_btn"
          type="primary"
          round
          block
          size="normal"
          @click="onSubmit"
        >登录</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { encrypt } from '@/utils/crypto'
import { login } from '@/api/user'
import Schema from 'async-validator'
export default {
  components: {},
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      fields: {
        username: '',
        password: ''
      },
      rule: {
        username: {
          type: 'string',
          len: 11,
          required: true,
          message: '手机号格式错误'
        },
        password: {
          type: 'string',
          required: true
        }
      }
    }
  },
  computed: {
    //
  },
  created() {
    //
  },
  // https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
  beforeRouteUpdate(to, from, next) {
  },
  async mounted() {
    //
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

      const loginData = {
        username: this.form.username,
        password: encrypt(this.form.password)
      }

      login(loginData).then(response => {
        this.$toast('登录成功')
        this.$store.commit('user/SET_LOGIN_TOKEN', response.data.token)
        // this.$store.commit('user/SET_USER_INFO', response.data.detail)
        this.$router.push({ path: '/' })
      })
        .catch(response => {
          this.$toast(response.message)
        })
    }
  }
}
</script>

<style lang="scss" scoped src="./index.scss"></style>
