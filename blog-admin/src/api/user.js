import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/getUserInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}

export function searchUser(query) {
  return request({
    url: '/user/search',
    method: 'get',
    params: {
      keyword: query.keyword,
      page: query.page,
      pageSize: query.pageSize
    }
  })
}

export function updateUserRole(id, data) {
  return request({
    url: '/user/updateRole',
    method: 'put',
    params: {
      userId: id
    },
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
}

export function updateUserInfo(id, data) {
  return request({
    url: '/user/updateInfo',
    method: 'put',
    params: {
      id: id
    },
    data
  })
}
