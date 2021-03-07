
<template>
  <div>
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

    <div class="interval" />

    <div class="van-panel__footer">
      <div class="title">第一条、服务内容</div>
      <ul class="content">
        <li>1. “优生慧”平台（以下简称“平台”）为渠道方生成专属二维码，任何扫此二维码登录并注册的用户即锁定为该渠道客户。渠道方将可以获得该锁定用户在平台消费而产生的佣金。</li>
      </ul><ul class="content">
        <li>2. 对于来自多个渠道推荐的同一注册用户，以平台系统后台登记的首次注册渠道二维码信息为渠道认定方式。渠道方可以随时登录平台账户查看客户扫码注册情况。</li>
      </ul><ul class="content">
        <li>3. 锁定用户在平台上选择指定医院预约并产生实际就诊，平台按每位就诊用户￥1,000元（人民币壹仟元）向渠道方支付佣金。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第二条、佣金结算及支付</div>
      <ul class="content">
        <li>1. 佣金结算：甲方以自然月为单位，为乙方统一结算佣金并出具结算通知单。乙方核准结算金额无误后，签署确认，并提供相应金额发票给甲方。甲方在收到乙方发票后，于3个工作日内完成佣金支付。</li>
      </ul>
      <ul class="content">
        <li>2. 支付方式：甲方以银行转账的方式向乙方支付服务费，乙方指定下列账户为收取本合同项下相关款项的唯一账户。乙方向甲方出具发票。<br>
          开户行: 请注册完成后，在“我的”信息中补充填写<br>
          户  名: 请注册完成后，在“我的”信息中补充填写<br>
          账  号: 请注册完成后，在“我的”信息中补充填写</li>
      </ul>
      <ul class="content">
        <li>3. 双方应根据中国法律规定自行各自承担因签订、执行本协议而产生的全部税费。</li>
      </ul>
    </div>

    <div class="open-floor" />

    <van-button
      type="info"
      block
      safe-area-inset-bottom
      @click="goBack"
    >我已阅读并同意</van-button>
  </div>
</template>

<script>
import defaultSettings from '@/settings.js'
export default {
  data() {
    return {
      defaultSettings
    }
  },
  computed: {},
  created() {},
  methods: {
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
