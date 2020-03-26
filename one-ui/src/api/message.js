import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/message/list',
    method: 'get'
  })
}

export function save(data) {
  return request({
    url: '/message/save',
    method: 'post',
    data: data
  })
}
