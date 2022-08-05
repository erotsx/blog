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
