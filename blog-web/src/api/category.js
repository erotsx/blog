import request from '@/request'

export function getAllCategories() {
  return request({
    url: '/category/getAllCategories',
    method: 'get',
  })
}

export function getCategoryInfo(id) {
  return request({
    url: '/category/info/' + id,
    method: 'get',
  })
}

export function getAllCategorysDetail() {
  return request({
    url: '/categorys/detail',
    method: 'get',
  })
}

export function getCategory(id) {
  return request({
    url: `/categorys/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/categorys/detail/${id}`,
    method: 'get',
  })
}
