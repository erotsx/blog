import request from '@/request'

export function getAllTags() {
  return request({
    url: '/tag/getAllTags',
    method: 'get',
  })
}

export function getTagInfo(id) {
  return request({
    url: '/tag/info/' + id,
    method: 'get',
  })
}

export function getAllTagsDetail() {
  return request({
    url: '/tags/detail',
    method: 'get',
  })
}

export function getHotTags() {
  return request({
    url: '/tag/getHotTags',
    method: 'get',
  })
}

export function getTag(id) {
  return request({
    url: `/tags/${id}`,
    method: 'get',
  })
}

export function getTagDetail(id) {
  return request({
    url: `/tags/detail/${id}`,
    method: 'get',
  })
}
