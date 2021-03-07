
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
      <ul class="content">
        <li>甲方：晴岚医疗科技（上海）有限公司</li>
      </ul>
      <ul class="content">
        <li>乙方：注册人</li>
      </ul>
      <ul class="content">
        <li>鉴于甲方系依法成立的从事医疗科技服务领域的专业机构，乙方系依法成立的从事服务领域的专业机构或个人，甲方和乙方(甲方和乙方以下合称为“双方”)本着平等自愿，共同发展的原则，经过友好协商，决定达成本合作协议（以下简称“本协议”）。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第一条、合作原则</div>
      <ul class="content">
        <li>（一）平等原则。双方在自愿、平等的前提下签署本协议，协议内容经过双方充分协商。</li>
      </ul>
      <ul class="content">
        <li>（二）长期、稳定合作原则。双方的合作基于彼此充分信任，着眼于长期利益，双方致力于长期、稳定的合作。</li>
      </ul>
      <ul class="content">
        <li>（三）共同发展原则。本协议旨在促进双方的共同发展，且符合双方的根本利益。</li>
      </ul>
      <ul class="content">
        <li>（四）诚实守信原则。双方恪守本协议中所作之承诺，确保双方的共同利益。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第二条、合作目标</div>
      <ul class="content">
        <li>双方本次打造双赢、可持续发展的合作关系，旨在通过发挥双方各自的资源优势，为双方客户提供全方位的高效、便捷、温馨的专业服务。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第三条、合作模式</div>
      <ul class="content">
        <li>（一）乙方自愿加入“优生慧”服务平台（公众号：优生慧）（以下简称“平台”），成为平台的合作机构，在日常业务中，积极宣传和推介客户通过专属二维码注册登录平台。</li>
      </ul>
      <ul class="content">
        <li>（二）甲方作为运营服务平台，为乙方提供唯一识别二维码，乙方通过引导客户扫描专属二维码登陆平台的行为锁定乙方的客户。</li>
      </ul>
      <ul class="content">
        <li>（三）甲方为乙方提供平台账号，供乙方登陆查看乙方客户注册、消费和返佣等信息。并且甲方同意按照平台运营规划，根据唯一识别二维码绑定的客户消费情况为乙方返佣，返佣规则参考附件。</li>
      </ul>
      <ul class="content">
        <li>（四）在乙方需要的情况下，甲方同意为乙方提供税务解决方案服务。</li>
      </ul>
      <ul class="content">
        <li>（五）乙方可以申请成为甲方平台服务供应商，根据平台规则为已入驻平台的客户提供陪诊等一系列有偿增值服务。</li>
      </ul>
      <ul class="content">
        <li>（六）甲方作为乙方在中国范围内的唯一医疗服务合作平台，该合作具有排他性。</li>
      </ul>
      <ul class="content">
        <li>（七）双方应充分利用各自的资源、技能和经验方面的优势，为双方的合作提供充分的支持和便利。</li>
      </ul>
      <ul class="content">
        <li>（八）乙方应按照平台要求积极自行发布宣传甲方信息至各信息平台，并对发布信息的真实性、安全性、有效性负责。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第四条、费用及支付方式</div>
      <ul class="content">
        <li>（一）甲方应根据返佣规则，在乙方客户产生消费后进行返佣结算。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第五条、保密条款</div>
      <ul class="content">
        <li>（一）对于本协议签署前或签署后，一方为合作披露的任何包含其非公开信息的文件或信息（包括但不限于商业计划、价格信息、财务信息、客户资料等），接收该等文件或信息的一方应予严格保密，未经披露方书面允许，不得以任何方式披露这些文件或信息，不得为合作以外的目的使用或利用该等文件或信息。</li>
      </ul>
      <ul class="content">
        <li>（二）乙方对于其作为平台合作方通过平台所获取的任何包含其非公开信息的文本或信息（包括但不限于推广计划、价格信息、财务信息、客户资料等），应予严格保密，未经平台客户和/或甲方书面允许，不得以任何方式披露这些文本或信息给任何第三方，亦不得为合作以外的目的使用或利用该等文件或信息。</li>
      </ul>
      <ul class="content">
        <li>（三）任何一方不得就本协议之主题、条款或存在的事实发布任何新闻稿、公告或公开声明（合称“公告”），除非发布该等公告已经征得另一方的书面同意，或是依据法律要求或有关监管机关的规定，惟被要求发布公告的一方应就该公告的发布时间安排以及具体内容与另一方进行磋商。</li>
      </ul>
      <ul class="content">
        <li>（四）以下信息不属于机密信息,可不参照保密条款的规定。<br>
          1、信息公开时或公开前,为公共的信息；<br>
          2、信息公开时，双方当事人持有的信息、或是从其他当事人以外的情报源获得的信息。 这种时候,当事人可以合理地认为其情报源不是违反保密义务规定的对象。</li>
      </ul>
      <ul class="content">
        <li>（五）在本协议期满或终止之后，此保密条款的约定仍将继续有效，双方仍需履行其所承诺的保密义务。</li>
      </ul>
      <ul class="content">
        <li>（六）针对投融资项目，双方另行签署通用版的保密协议，供双方投融资项目信息共享使用（附件2）。若一方客户要求另行签署保密协议，另一方应积极配合。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第六条、知识产权条款</div>
      <ul class="content">
        <li>一方向另一方提供的资料、信息标注知识产权标记、标识时，应当具备相应的证明权利合法有效的文件。接收第三方授权经营的，应当具备有效授权文件。一方有权要求另一方提供上述各款所列文件。一方可要求另一方提供复印件、加盖相关单位公章的复印件，也可以要求提供原件以作验证。</li>
      </ul>
      <ul class="content">
        <li>一方应当保证其提供的资料、信息所标注的知识产权标记、标识符合法律、法规的规定；如果一方发现向另一方提供的知识产权标记、标识错误，应当立即改正，并书面通知另一方。一方发现另一方提供的知识产权标记、标识错误，有权通知另一方，要求另一方在指定期限内改正。</li>
      </ul>
      <ul class="content">
        <li>甲乙双方应当互相配合，迅速妥善地解决知识产权投诉、行政查处或民事诉讼。</li>
      </ul>
      <ul class="content">
        <li>乙方明确授权甲方在平台上无偿使用乙方提供资料、信息所涉及的任何知识产权及相关权益。</li>
      </ul>
      <ul class="content">
        <li>一方违反上述约定给另一方造成损失时，应承担全部赔偿责任。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第七条、违约</div>
      <ul class="content">
        <li>（1）发生下列任何一个或多个事件都将构成对本协议的违约：<br>
          1．任何一方实质性违反本协议的条款，或未能在任一实质方面履行其在本协议项下的义务，且在收到另一方要求补救的书面通知后10日内，未能补救上述违约或不履约行为；<br>
          2、任何一方在本协议项下的承诺、声明或保证在任一实质方面被证明是虚假的或具误导性的。</li>
      </ul>
      <ul class="content">
        <li>（2）违约责任<br>
          甲乙双方应当依照本协议约定履行相应义务，任何一方违约的，应当承担相应违约责任。违约方应就其违约行为对另一方造成的任何及全部损害承担赔偿责任。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第八条、争议解决方式</div>
      <ul class="content">
        <li>本协议在履行过程中发生的一切争议，由双方协商解决。如果协商不成的，可以提请中国国际经济贸易仲裁委员会通过仲裁解决，适用中华人民共和国法律。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第九条、未尽事宜</div>
      <ul class="content">
        <li>本协议未尽事宜，双方本着友好协商原则解决。对本协议条款的任何修改、变更或增减，须经双方一致同意并以书面形式作出。</li>
      </ul>
      <ul class="content">
        <li>本协议任何补充文件、修改文件作为本协议的一部分，与本协议具有同等的法律效力。当文件条款有冲突时，以修改后的文件条款优先适用；前后修改文件有冲突时，以后修改的文件优先适用。</li>
      </ul>
      <ul class="content">
        <li>本协议的补充文件自甲、乙双方授权代表签字之日开始生效，有效期将根据补充文件中的规定进行计算，但不得超过本协议的期限。</li>
      </ul>
    </div>

    <div class="van-panel__footer">
      <div class="title">第十条、其他事宜</div>
      <ul class="content">
        <li>（1）一方的通讯方式等重要信息发生变更的，应及时书面通知另一方。</li>
      </ul>
      <ul class="content">
        <li>（2）双方确认，双方之间的信任与相互合作是本协议得以履行和合作目标得以实现的重要基础，一方在未经另一方事先书面认可的情况下，不得将本协议项下的全部或部分权利或义务转让给第三方。</li>
      </ul>
      <ul class="content">
        <li>（3）双方均有权在合法范围内为实现双方合作目的而免费披露双方之间的战略合作关系。</li>
      </ul>
      <ul class="content">
        <li>（4）本协议有效期为3年，自注册日起生效。</li>
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
