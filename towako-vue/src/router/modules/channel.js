/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const channel = {
  path: '/channel',
  component: Layout,
  redirect: '/channel/doctor',
  name: 'channel',
  meta: { title: '渠道管理', icon: 'component' },
  children: [{
    path: 'doctor',
    name: 'doctor',
    component: () => import('@/views/channel/doctor/doctor'),
    meta: { title: '医生管理', icon: 'nested' }
  }, {
    path: 'family-hotel',
    name: 'family-hotel',
    component: () => import('@/views/channel/family-hotel/family-hotel'),
    meta: { title: '家庭旅馆管理', icon: 'nested' }
  }]
}

export default channel
