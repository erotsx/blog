import request from '@/utils/request'

export function searchCategories(query) {
  return request({
    url: '/category/search',
    method: 'GET',
    params: {
      keyword: query.categoryName,
      page: query.page,
      pageSize: query.pageSize
    }
  })
}
export function fetchCategory() {
  return request({
    url: '/category/getAllCategories',
    method: 'GET'
  })
}
export function deleteBatch(data) {
  return request({
    url: '/system/category/deleteBatch',
    method: 'delete',
    data
  })
}
export function remove(id) {
  return request({
    url: '/category/delete/' + id,
    method: 'delete'
  })
}
export function add(data) {
  return request({
    url: '/category/add',
    method: 'POST',
    data
  })
}
export function info(id) {
  return request({
    url: '/category/info/' + id,
    method: 'get'
  })
}
export function update(data) {
  return request({
    url: '/category/update',
    method: 'put',
    data
  })
}
export function top(id) {
  return request({
    url: '/system/category/top',
    method: 'get',
    params: {
      id: id
    }
  })
}
