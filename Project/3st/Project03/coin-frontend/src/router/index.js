import {
    createRouter,
    createWebHistory,
} from 'vue-router'

import index from '../layout/index.vue'
import login from '../views/login.vue'
import main from '../views/main.vue'
import key from '../views/key.vue'
import trading from '../views/trading.vue'
import record from '../views/history.vue'
import notification from '../views/notification.vue'
import admin from '../views/admin.vue'
import notificationWrite from '../views/notificationWrite.vue'
import notificationDetail from '../views/notificationDetail.vue'

import bootExm from '../views/bootExm.vue'
import test from '../views/test.vue'


const routes = [
    {
        path: '/',
        name: "layout",
        redirect: "/login",
        component: index,
        children: [
            {
                path: "/main",
                name: 'main',
                component: main
            },
            {
                path: "/key",
                name: 'key',
                component: key
            },
            {
                path: "/trading",
                name: 'trading',
                component: trading
            },
            {
                path: "/record",
                name: 'record',
                component: record
            },
            {
                path: "/notification",
                name: 'notification',
                component: notification
            },

            {
                path: "/bootExm",
                name: 'bootExm',
                component: bootExm
            },
            {
                path: "/test",
                name: 'test',
                component: test
            },
            {
                path: "/admin",
                name: 'admin',
                component: admin
            },
            {
                path: "/notificationWrite",
                name: 'notificationWrite',
                component: notificationWrite
            },
            {
                path: "/notificationDetail",
                name: 'notificationDetail',
                component: notificationDetail
            },

        ],
    },
    {
        path: '/login',
        name: "login",
        component: login,
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    scrollBehavior(){   //라우터 사용시 스크롤 초기화
        return { top: 0 }
    },
})
export default router;