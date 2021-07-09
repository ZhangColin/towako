/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const assistedReproduction = {
  path: '/assisted-reproduction',
  component: Layout,
  redirect: '/assisted-reproduction/medical-record',
  name: 'assisted-reproduction',
  meta: { title: '辅助生殖', icon: 'component' },
  children: [{
    path: 'medical-record',
    name: 'medical-record',
    component: () => import('@/views/assisted-reproduction/medical-record/medical-record'),
    meta: { title: '病历管理', icon: 'nested' },
    hidden: false
  }, {
    path: 'medical-team',
    // name: 'medical-team',
    component: () => import('@/views/assisted-reproduction/medical-team/medical-team'),
    meta: { title: '医疗团队', icon: 'nested' },
    hidden: true
  }, {
    path: 'medical-record-full-info',
    // name: 'treatment-period',
    component: () => import('@/views/assisted-reproduction/medical-record/medical-record-full-info'),
    meta: { title: '病历详情', icon: 'nested' },
    hidden: true
  }]
}

export default assistedReproduction
