import request from '@/request'


export function getBloggerInfo() {
  return request({
    url: '/user/getBloggerInfo',
    method: 'get'
  })
}
