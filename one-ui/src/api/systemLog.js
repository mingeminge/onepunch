import request from '../utils/request'

export function findAll(params) {
  return request({
    url: '/systemLog/list',
    method: 'get',
    params: params
  })
}

export function deleteLog(log) {
  return request({
    url: '/systemLog/delete',
    method: 'post',
    data: log
  })
}
