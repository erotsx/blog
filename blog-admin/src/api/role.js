import request from '@/utils/request'

export function searchRoles(query) {
  return request({
    url: '/role/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      keyword: query.keyword
    }
  })
}

export function createRole(data) {
  return request({
    url: '/role/create',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/role/update',
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: '/role/delete/' + id,
    method: 'delete'
  })
}
