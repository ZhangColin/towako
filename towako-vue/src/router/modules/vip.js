/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const vip = {
  path: '/vip',
  component: Layout,
  redirect: '/vip/membership',
  name: 'vip',
  meta: { title: '会员管理', icon: 'component' },
  children: [{
    path: 'membership',
    name: 'membership',
    component: () => import('@/views/vip/membership/membership'),
    meta: { title: '会员信息', icon: 'nested' }
  }]
}

export default vip
