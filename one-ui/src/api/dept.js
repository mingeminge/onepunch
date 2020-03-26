import request from '../utils/request'

export function list(param) {
  return request({
    url: '/dept/list',
    method: 'get',
    params: param
  })
}
export function insert(data) {
  return request({
    url: '/dept/insert',
    method: 'post',
    data: data
  })
}

export function edit(data) {
  return request({
    url: '/dept/edit',
    method: 'post',
    data: data
  })
}

export function findById(params) {
  return request({
    url: '/dept/' + params,
    method: 'get'
  })
}

export function deleteDeptById(data) {
  return request({
    url: '/dept/delete',
    method: 'post',
    data: data
  })
}
