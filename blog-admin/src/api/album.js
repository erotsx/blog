import request from '@/utils/request'

export function searchAlbum(query) {
  return request({
    url: '/album/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      keyword: query.keyword
    }
  })
}

export function addAlbum(data) {
  return request({
    url: '/album/add',
    method: 'post',
    data
  })
}

export function updateAlbum(data) {
  return request({
    url: '/album/update',
    method: 'put',
    data
  })
}

export function deleteAlbum(id) {
  return request({
    url: '/album/delete/' + id,
    method: 'delete'
  })
}

export function getAlbumInfo(id) {
  return request({
    url: '/album/info/' + id,
    method: 'get'
  })
}

export function listAlbums() {
  return request({
    url: '/album/albums',
    method: 'get'
  })
}
