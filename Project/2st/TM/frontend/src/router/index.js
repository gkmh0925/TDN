import {
    createRouter,
    createWebHistory,
} from 'vue-router'

import index from '../layout/index.vue'
import main from '../views/main.vue'
import test from '../views/test.vue'
import roomDetail from '../views/roomDetail.vue'
import reservation from '../views/reservation.vue'
import login from '../views/login.vue'
import rooms from '../views/rooms.vue'
import order from '../views/order.vue'
import find from '../views/find.vue'
import join from '../views/join.vue'
import user from '../views/user.vue'
import admin from '../views/admin.vue'

const routes = [
        {
        path: '/',
        name: "layout",
        redirect: "/main",
        component: index,
            children: [
                {
                    path: "/main",
                    name: 'main',
                    component: main
                },
                {
                    path: "/test",
                    name: 'test',
                    component: test
                },
                {
                    path: "/roomDetail",
                    name: 'roomDetail',
                    component:roomDetail
                },
                {
                    path: "/reservation",
                    name: 'reservation',
                    component:reservation
                },
                {
                    path: "/login",
                    name: 'login',
                    component:login
                },
                {
                    path: "/rooms",
                    name: 'rooms',
                    component:rooms
                },
                {
                    path: "/order",
                    name: 'order',
                    component:order
                },
                {
                    path: "/join",
                    name: 'join',
                    component:join
                },
                {
                    path: "/find",
                    name: 'find',
                    component:find
                },
                {
                    path: "/user",
                    name: 'user',
                    component:user
                },
                {
                    path: "/admin",
                    name: 'admin',
                    component:admin
                },
            ],
        },
    ]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    scrollBehavior(){   //라우터 사용시 스크롤 초기화
        return { top: 0 }
    },
})
export default router;