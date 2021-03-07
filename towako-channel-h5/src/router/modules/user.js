/** When your routing table is too long, you can split it into small modules **/
const router = [
  {
    path: '/user',
    name: 'user',
    component: () => import('@/views/user/index.vue'),
    meta: {
      permission: ['login'],
      title: '我的'
    }
  }
]
export default router
