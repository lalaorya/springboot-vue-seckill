import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    mode: 'history',
    // base:'/seckill',
    routes: [
        {
            path: '/goodlist',
            // redirect: '/sysindex'
            component: () => import('../views/GoodList.vue'),
        },
        {
            path: '/seclist',
            component: () => import(/* webpackChunkName: "login" */ '../views/SecList.vue'),
            // meta: { title: '登录' }
        },
        {
            path: '/good/:id',
            component: () => import(/* webpackChunkName: "login" */ '../views/GoodDetail.vue'),
            // meta: { title: '登录' }
        },
        {
            path: '/secorder',
            component: () => import(/* webpackChunkName: "login" */ '../views/SecOrder.vue'),
            // meta: { title: '登录' }
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue'),
            // meta: { title: '登录' }
        },
        {
            path: '*',
            // redirect: '/404'
        }
    ]
});
