import {
  asyncRoutes,
  constantRouterMap
} from '@/router'

/**
 * 使用 meta.role 以确定当前用户是否具有权限
 * @param roles
 * @param route
 */
// 匹配权限
function hasPermission(roles, route) {
  // if (route.meta && route.meta.roles) {
  //   debugger
  //   return roles.some(role => route.meta.roles.includes(role))
  // } else {
    return true
  // }
}

/**
 * 通过递归过滤异步路由表
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []
debugger
  routes.forEach(route => {
    const tmp = {
      ...route
    }
    debugger
    if (hasPermission(roles, tmp)&&tmp.meta.roles.includes('普通用户')) {
      
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRouterMap.concat(routes) // 将过滤后的路由和constantRouterMap存起来
  }
}

// 筛选
const actions = {
  generateRoutes({
    commit
  }, roles) {
      
    return new Promise(resolve => {
      let accessedRoutes
      console.log("permission.js")
      console.log(roles)
      // 管理员admin显示全部路由，
      // 我这里admin是想让它不显示全部的 想要admin能看见全部的话把注释去掉
      if (roles.includes('管理员')) {
        accessedRoutes = asyncRoutes || []
        console.log("if语句我进来了")
      } else {
      //过滤路由
      accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      console.log("permission.js:"+accessedRoutes)
      console.log("else语句我进来了")
      // accessedRoutes这个就是当前角色可见的动态路由
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}



