import request from '@/utils/request'

export function searchPhotos(query) {
  return request({
    url: '/photo/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      albumId: query.albumId
    }
  })
}

export function searchSysPhotos(query) {
  return request({
    url: '/photo/sys/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      keyword: query.keyword
    }
  })
}

export function deletePhotos(data) {
  return request({
    url: '/photo/search',
    method: 'get',
    data
  })
}

export function movePhotos(data, albumId) {
  return request({
    url: '/photo/move',
    method: 'post',
    params: {
      albumId: albumId
    },
    data
  })
}

export function updatePhoto(data) {
  return request({
    url: '/photo/update',
    method: 'put',
    data
  })
}

export function insertPhoto(data) {
  return request({
    url: '/photo/insert',
    method: 'post',
    data
  })
}

