import request from '@/utils/request'

export function fetchTags() {
  return request({
    url: '/tag/getAllTags',
    method: 'GET'
  })
}
export function searchTags(query) {
  return request({
    url: '/tag/search',
    method: 'GET',
    params: {
      keyword: query.tagName,
      page: query.page,
      pageSize: query.pageSize
    }
  })
}
export function remove(id) {
  return request({
    url: '/tag/delete/' + id,
    method: 'delete'
  })
}
export function deleteBatch(data) {
  return request({
    url: '/system/tags/deleteBatch',
    method: 'delete',
    data
  })
}
export function add(data) {
  return request({
    url: '/tag/add',
    method: 'POST',
    data
  })
}
export function info(id) {
  return request({
    url: '/tag/info/' + id,
    method: 'get'
  })
}
export function update(data) {
  return request({
    url: '/tag/update',
    method: 'put',
    data
  })
}
export function top(id) {
  return request({
    url: '/system/tags/top',
    method: 'get',
    params: {
      id: id
    }
  })
}
