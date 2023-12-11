import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login','/register'] // 不重定向白名单
router.beforeEach(async (to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      // 确定用户是否通过getInfo获得了权限角色
      const hasRoles = store.getters.roles && store.getters.roles.length > 0 //这里指的是src/store/getters.js的roles
      // console.log(hasRoles)
      //判断是否已经有roles了
      if (hasRoles) {
        next(); //当有用户权限的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
      }else {
        try {
          // get user info
          // 注意: roles 角色必须是对象数组! 例如: ['admin'] 或 ,['developer','editor']
          // 1. 获取roles
          const data = await store.dispatch('GetInfo') //第一步
          // var aaa=store.dispatch('GetInfo')
          // aaa=JSON.parse(aaa);
    console.log('src/permission.js:'+data.data.roles)
// console.log('src/permission.js:'+aaa)
          // const roles= store.getters.roles;
          // 2. 根据角色生成可访问路由图
          // 获取通过权限验证的路由
          const accessRoutes = await store.dispatch('permission/generateRoutes', data.data.roles) //第二步
          // 3. 更新加载路由
          router.options.routes = store.getters.permission_routes //第三步
          // 动态添加可访问路由
          router.addRoutes(accessRoutes)
          // console.log(store)
          // console.log(accessRoutes);
          // hack方法 确保addRoutes已完成，以确保地址是完整的
          // 设置replace: true，这样导航就不会留下历史记录
          next({
            ...to,
            replace: true
          })
        } catch (error) {
          // 删除token并转到登录页面重新登录
          await store.dispatch('FedLogOut')
          Message.error('出现错误~请重新登录')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
