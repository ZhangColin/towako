/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const traffic = {
  path: '/traffic',
  component: Layout,
  redirect: '/traffic/traffic',
  name: 'traffic',
  meta: { title: '渠道管理', icon: 'component' },
  children: [{
    path: '/channel',
    name: 'channel',
    component: () => import('@/views/traffic/channel/channel'),
    meta: { title: '渠道管理', icon: 'nested' }
  }]
}

export default traffic
