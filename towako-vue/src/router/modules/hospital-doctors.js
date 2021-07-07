/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const hospitalDoctors = {
  path: '/hospital-doctors',
  component: Layout,
  redirect: '/hospital-doctors/doctor',
  name: 'hospital-doctors',
  meta: { title: '医院医生', icon: 'component' },
  children: [{
    path: 'doctor',
    name: 'doctor',
    component: () => import('@/views/hospital-doctors/doctor/doctor'),
    meta: { title: '医生管理', icon: 'nested' }
  }, {
    path: 'hospital',
    name: 'hospital',
    component: () => import('@/views/hospital-doctors/hospital/hospital'),
    meta: { title: '医院管理', icon: 'nested' }
  }]
}

export default hospitalDoctors
