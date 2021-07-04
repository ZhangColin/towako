/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const assistedReproduction = {
  path: '/assisted-reproduction',
  component: Layout,
  redirect: '/assisted-reproduction/medical-record',
  // name: 'assisted-reproduction',
  meta: { title: '辅助生殖', icon: 'component' },
  children: [{
    path: '/medical-record',
    // name: 'medical-record',
    component: () => import('@/views/assisted-reproduction/medical-record/medical-record'),
    meta: { title: '病历管理', icon: 'nested' }
  }, {
    path: '/medical-team',
    // name: 'hospital',
    component: () => import('@/views/assisted-reproduction/medical-team/medical-team'),
    meta: { title: '医疗团队', icon: 'nested' }
  }, {
    path: '/treatment-period',
    // name: 'treatment-period',
    component: () => import('@/views/assisted-reproduction/treatment-period/treatment-period'),
    meta: { title: '疗程', icon: 'nested' }
  }, {
    path: '/inspection-report',
    // name: 'inspection-report',
    component: () => import('@/views/assisted-reproduction/inspection-report/inspection-report'),
    meta: { title: '检查报告', icon: 'nested' }
  }]
}

export default assistedReproduction
