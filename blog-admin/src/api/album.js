import request from '@/utils/request'

export function searchAlbum(query) {
  return request({
    url: '/photo/album/search',
    method: 'get',
    params: {
      page: query.page,
      pageSize: query.pageSize,
      keyword: query.title
    }
  })
}
