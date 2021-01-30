/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'
import nested from '@/layout/nested'

const vip = {
  path: '/vip',
  component: Layout,
  redirect: '/vip/membership',
  name: 'vip',
  meta: { title: '会员管理', icon: 'component' },
  children: [{
    path: 'membership',
    name: 'membership',
    component: nested,
    redirect: '/vip/membership/membership-list',
    meta: { title: '会员信息', icon: 'nested' },
    children: [{
      path: 'membership-list',
      component: () => import('@/views/vip/membership/membership'),
      meta: { title: '会员信息', breadcrumb: false },
      hidden: false
    }, {
      path: 'wechat-event-record',
      component: () => import('@/views/vip/membership/wechat-event-record'),
      meta: { title: '微信事件记录' },
      hidden: true
    }]
  }]
}

export default vip

