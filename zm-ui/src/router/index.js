import Vue from 'vue';
import VueRouter from 'vue-router';
import Layout from '@/views/layout'

Vue.use(VueRouter);


export const constantRouterMap = [
    {path: '/login', component: () => import('@/views/login/index')},
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
];
const router = new VueRouter({
    mode: 'history',
    scrollBehavior: () => ({y: 0}),
    routes: constantRouterMap
})

export default router;