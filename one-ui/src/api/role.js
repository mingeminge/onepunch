import request from '../utils/request'

export function list(param) {
  return request({
    url: '/role/list',
    method: 'get',
    params: param
  })
}

export function findById(id) {
  return request({
    url: '/role/' + id,
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '/role/edit',
    method: 'post',
    data: data
  })
}

export function insert(data) {
  return request({
    url: '/role/insert',
    method: 'post',
    data: data
  })
}

export function deleteRole(data) {
  return request({
    url: '/role/delete',
    method: 'post',
    data: data
  })
}

export function menuList() {
  return request({
    url: '/menu/list',
    method: 'get'
  })
}

export function deptList(param) {
  return request({
    url: '/dept/list',
    method: 'get',
    params: param
  })
}
