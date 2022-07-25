import request from '@/request'


export function getArticles(page) {
  return request({
    url: '/articles/getArticles',
    method: 'get',
    page
  })
}

export function getArticle(id) {
  return request({
    url: '/articles' + id,
    method: 'get'
  })
}

export function getHotArticles() {
  return request({
    url: '/articles/getHotArticles',
    method: 'get'
  })
}

export function getNewArticles(limit) {
  return request({
    url: '/articles/getNewArticles',
    method: 'get',
    params: {
      limit: limit
    }
  })
}

export function viewArticle(id) {
  return request({
    url: `/articles/view/${id}`,
    method: 'post'
  })
}

export function getArticlesByCategory(id) {
  return request({
    url: `/articles/category/${id}`,
    method: 'post'
  })
}

export function getArticlesByTag(id) {
  return request({
    url: `/articles/tag/${id}`,
    method: 'post'
  })
}


export function publishArticle(article, token) {
  return request({
    headers: {'Authorization': token},
    url: '/articles/publish',
    method: 'post',
    data: article
  })
}

export function searchArticle(params) {
  return request({
    url: '/articles/search',
    method: 'get',
    params: params

  })
}

export function listArchives() {
  return request({
    url: '/articles/getArchives',
    method: 'get'
  })
}

export function getArticleById(id) {
  return request({
    url: `/articles/${id}`,
    method: 'post'
  })
}
