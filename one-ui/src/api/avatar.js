import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/avatar/list',
    method: 'get',
    params: params
  })
}

export function updateImg(params) {
  return request({
    url: '/avatar/update',
    method: 'post',
    data: params
  })
}

export function deleteImg(params) {
  return request({
    url: '/avatar/' + params,
    method: 'post'
  })
}

export function save(params) {
  return request({
    url: '/avatar/save',
    method: 'post',
    data: params
  })
}
