import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/register', component: () => import('@/views/register/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
]

export const asyncRoutes = [
  {
      path: '/metting',
      component: Layout,
      redirect: '/metting',
      name: '会议室管理',
      meta: { title: '会议室管理', icon: 'example',roles: ['管理员','普通用户'] },
      children: [
        {
          path: 'list',
          name: '会议室列表',
          component: () => import('@/views/metting/list'),
          meta: { title: '会议室列表', icon: 'table',roles: ['管理员','普通用户'] }
        },
        {
          path: 'add',
          name: '会议室添加',
          component: () => import('@/views/metting/add'),
          meta: { title: '会议室添加', icon: 'tree' ,roles: ['管理员','普通用户']}
        },
        {
          path: 'edit/:id',
          name: '会议室编辑',
          component: () => import('@/views/metting/add'),
          meta: { title: ' 会议室编辑', icon: 'tree' ,roles: ['管理员','普通用户']},
          hidden:true
        }
      ]
    },{
      path: '/employee',
      component: Layout,
      redirect: '/employee',
      name: '人员管理',
      meta: { title: '人员管理', icon: 'example',roles: ['管理员','普通用户'] },
      children: [
        {
          path: 'list',
          name: '会议室列表',
          component: () => import('@/views/employee/list'),
          meta: { title: '人员列表', icon: 'table',roles: ['管理员','普通用户'] }
        },
        {
          path: 'add',
          name: '人员添加',
          component: () => import('@/views/employee/add'),
          meta: { title: '人员添加', icon: 'tree' ,roles: ['管理员','普通用户']}
        },
        {
          path: 'edit/:id',
          name: '人员编辑',
          component: () => import('@/views/employee/add'),
          meta: { title: ' 人员编辑', icon: 'tree' ,roles: ['管理员','普通用户']},
          hidden:true
        }
      ]
    }
  // {
  //   path: '/user',
  //   component: Layout,
  //   redirect: '/englishWriting/user',
  //   name: '用户管理',
  //   meta: { title: '用户管理', icon: 'user',roles: ['管理员'] },
  //   children: [
  //     {
  //       path: 'list',
  //       name: '用户列表',
  //       component: () => import('@/views/user/list'),
  //       meta: { title: '用户列表', icon: 'table',roles: ['管理员'] }
  //     },
  //     {
  //       path: 'add',
  //       name: '用户添加',
  //       component: () => import('@/views/user/add'),
  //       meta: { title: '用户添加', icon: 'tree',roles: ['管理员'] }
  //     },
  //     {
  //       path: 'edit/:loginName',
  //       name: '用户编辑',
  //       component: () => import('@/views/user/add'),
  //       meta: { title: '用户编辑', icon: 'tree',roles: ['管理员'] },
  //       hidden:true
  //     }
  //   ]
  // },
  // {
  //   path: '/fanwenku',
  //   component: Layout,
  //   redirect: '/englishWriting/fanwenku',
  //   name: '范文库管理',
  //   meta: { title: '范文库管理', icon: 'example',roles: ['管理员','普通用户'] },
  //   children: [
  //     {
  //       path: 'list',
  //       name: '范文库列表',
  //       component: () => import('@/views/fanwenku/list'),
  //       meta: { title: '范文库列表', icon: 'table',roles: ['管理员','普通用户'] }
  //     },
  //     {
  //       path: 'add',
  //       name: '范文库添加',
  //       component: () => import('@/views/fanwenku/add'),
  //       meta: { title: '范文库添加', icon: 'tree' ,roles: ['管理员','普通用户']}
  //     },
  //     {
  //       path: 'edit/:fanwenkuId',
  //       name: '范文库编辑',
  //       component: () => import('@/views/fanwenku/add'),
  //       meta: { title: ' 范文库编辑', icon: 'tree' ,roles: ['管理员','普通用户']},
  //       hidden:true
  //     }
  //   ]
  // },
  // {
  //   path: '/article',
  //   component: Layout,
  //   redirect: '/englishWriting/article',
  //   name: '文章管理',
  //   meta: { title: '文章管理', icon: 'form' ,roles: ['管理员','普通用户']},
  //   children: [
  //     {
  //       path: 'list',
  //       name: '文章列表',
  //       component: () => import('@/views/article/list'),
  //       meta: { title: '文章列表', icon: 'table',roles: ['管理员','普通用户'] }
  //     },
  //     {
  //       path: 'add',
  //       name: '文章添加',
  //       component: () => import('@/views/article/add'),
  //       meta: { title: '文章添加', icon: 'tree',roles: ['管理员','普通用户'] }
  //     },
  //     {
  //       path: 'edit/:articleId',
  //       name: '文章编辑',
  //       component: () => import('@/views/article/add'),
  //       meta: { title: ' 文章编辑', icon: 'tree' ,roles: ['管理员','普通用户']},
  //       hidden:true
  //     }
  //   ]
  // },
  // {
  //   path: '/impArticle',
  //   component: Layout,
  //   redirect: '/englishWriting/impArticle',
  //   name: '待改进文章管理',
  //   meta: { title: '待改进文章管理', icon: 'link',roles: ['管理员','普通用户'] },
  //   children: [
  //     {
  //       path: 'list',
  //       name: '待改进文章列表',
  //       component: () => import('@/views/impArticle/list'),
  //       meta: { title: '待改进文章列表', icon: 'table' ,roles: ['管理员','普通用户']}
  //     },
  //     {
  //       path: 'add',
  //       name: '待改进文章添加',
  //       component: () => import('@/views/impArticle/add'),
  //       meta: { title: '待改进文章添加', icon: 'tree' ,roles: ['管理员','普通用户']}
  //     },
  //     {
  //       path: 'edit/:impArticleId',
  //       name: '待改进文章修改',
  //       component: () => import('@/views/impArticle/add'),
  //       meta: { title: ' 待改进文章修改', icon: 'tree' ,roles: ['管理员','普通用户']},
  //       hidden:true
  //     }
  //   ]
  // },
  // {
  //   path: '/recommend',
  //   component: Layout,
  //   redirect: '/englishWriting/recommend',
  //   name: '文章推荐',
  //   meta: { title: '文章推荐', icon: 'nested',roles: ['管理员','普通用户'] },
  //   children: [
  //     {
  //       path: 'list',
  //       name: '文章推荐',
  //       component: () => import('@/views/recommend/list'),
  //       meta: { title: '文章推荐', icon: 'table' ,roles: ['管理员','普通用户']}
  //     }
  //   ]
  // },
  // {
  //   path: '/history',
  //   component: Layout,
  //   redirect: '/englishWriting/history',
  //   name: '文章操作记录',
  //   meta: { title: '文章操作记录', icon: 'table' ,roles: ['管理员','普通用户']},
  //   children: [
  //     {
  //       path: 'list',
  //       name: '文章预览记录',
  //       component: () => import('@/views/history/list'),
  //       meta: { title: '文章操作记录', icon: 'table' ,roles: ['管理员','普通用户']}
  //     }
  //   ]
  // }
]

const createRouter = () => new Router({
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRouterMap
})

const router = createRouter()
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}
export default router
