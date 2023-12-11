import request from '@/utils/request'

export function login(userInfo) {
  return request({
    url: '/login/login',
    method: 'post',
    data: userInfo
  })
}

export function getInfo(token) {
  return request({
    url: '/login/info',
    method: 'post',
    data: token
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
