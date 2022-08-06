import request from '@/utils/request'

export function listPermissions() {
  return request({
    url: '/permission/listAll',
    method: 'get'
  })
}

export function listCategoryPermissions() {
  return request({
    url: '/permissionCategory/listAll',
    method: 'get'
  })
}

export function searchCategoryPermissions(query) {
  return request({
    url: '/permissionCategory/search',
    method: 'get',
    params: {
      keyword: query
    }
  })
}

export function createCategoryPermissions(data) {
  return request({
    url: '/permissionCategory/create',
    method: 'post',
    data
  })
}

export function updateCategoryPermissions(data) {
  return request({
    url: '/permissionCategory/update',
    method: 'put',
    data
  })
}

export function createPermission(data) {
  return request({
    url: '/permission/create',
    method: 'post',
    data
  })
}

export function updatePermission(data) {
  return request({
    url: '/permission/update',
    method: 'put',
    data
  })
}

export function deleteCategoryPermissions(id) {
  return request({
    url: '/permissionCategory/delete/' + id,
    method: 'delete'
  })
}

export function deletePermission(id) {
  return request({
    url: '/permission/delete/' + id,
    method: 'delete'
  })
}
