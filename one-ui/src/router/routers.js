import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout/'

Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  /*{
    path: '/leaving-message',
    component: () => import('@/views/message/index'),
    hidden: true
  },*/
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('@/views/index/index'),
      meta: {title: '首页', icon: 'dashboard'}
    }]
  },
  {
    path: '/setting',
    component: Layout,
    redirect: '/setting',
    hidden: true,
    children: [{
      path: '/setting',
      name: 'setting',
      component: () => import('@/views/system/setting/index'),
      meta: {title: '系统设置'}
    }]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    children: [{
      path: 'info',
      name: 'info',
      component: resolve => require(['../views/userinfo/index'], resolve),
      meta: {title: '个人中心'}
    }]
  },
  {
    path: '/GitHub',
    component: Layout,
    iFrame: false,
    children: [
      {
        path: 'https://github.com/mingeminge/onepunch',
        meta: {title: 'GitHub', icon: 'github'}
      }
    ]
  },

]
export default new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
