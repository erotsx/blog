import request from '@/request'


export function getCommentsByArticle(params) {
  return request({
    url: `/comment/list`,
    method: 'get',
    params: {
      id: params.id,
      page: params.page
    }
  })
}

export function publishComment(data) {
  return request({
    url: '/comment/publish',
    method: 'post',
    data
  })
}

export function getReplyByParentId(params) {
  return request({
    url: `/comment/reply`,
    method: 'get',
    params: {
      id: params.id,
      page: params.page,
      pageSize: params.pageSize
    }
  })
}

export function likeComment(id) {
  return request({
    url: '/comment/like/' + id,
    method: 'post'
  })
}
