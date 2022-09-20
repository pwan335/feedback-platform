import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// static router
export const constantRoutes = [
    {
        path: '',
        name: 'home',
        component: resolve => require(['@/views/home/HomePage.vue'], resolve),
        hidden: true
    },
    {
        path: '/reset-password',
        component: resolve => require(['@/views/reset-password/ResetPassword.vue'], resolve),
        hidden: true
    },
    {
        path: '/user-info',
        component: resolve => require(['@/views/user-info/UserInfo.vue'], resolve),
        hidden: true
    },
    {
        path: '/details',
        component: resolve => require(['@/views/topic-details/TopicDetails.vue'], resolve),
        hidden: true
    },
    {
        path: '/401',
        component: resolve => require(['@/views/error-page/401.vue'], resolve),
        hidden: true
    },
    {
        path: '*',
        component: resolve => require(['@/views/error-page/404.vue'], resolve),
        hidden: true
    },
]

const originalPush = Router.prototype.push
Router.prototype.push = function push(location){
    return originalPush.call(this, location).catch( ()=> {})
}

const createRouter = () => new Router({
    mode: 'history',
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})
const router = createRouter()

router.beforeEach((to, from, next) => {
    next()
})

export default router