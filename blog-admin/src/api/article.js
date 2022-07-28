import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/articles/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      tagId: query.tagId,
      keyword: query.title,
      status: query.status,
      categoryId: query.categoryId
    }
  })
}
export function getArticle(id) {
  return request({
    url: '/articles/article/' + id,
    method: 'get'
  })
}

export function postArticle(data) {
  return request({
    url: '/articles/postArticle/',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/articles/updateArticle/',
    method: 'put',
    data
  })
}

export function removeArticle(id) {
  return request({
    url: '/articles/delete/' + id,
    method: 'delete'
  })
}
export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/article/pv',
    method: 'get',
    params: { pv }
  })
}

export function createArticle(data) {
  return request({
    url: '/vue-element-admin/article/create',
    method: 'post',
    data
  })
}

