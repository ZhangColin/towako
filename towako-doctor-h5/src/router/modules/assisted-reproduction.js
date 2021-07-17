/** When your routing table is too long, you can split it into small modules **/
const router = [
  {
    path: '/assisted-reproduction/medical-record/full-info',
    name: 'user',
    component: () => import('@/views/assisted-reproduction/medical-record/medical-record-full-info/index.vue'),
    meta: {
      permission: ['login'],
      title: '病历详情'
    }
  }
]
export default router
