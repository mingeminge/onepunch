import request from '../utils/request'

export function list(param) {
  return request({
    url: '/menu/list',
    method: 'get',
    params: param
  })
}

export function insert(data) {
  return request({
    url: '/menu/insert',
    method: 'post',
    data: data
  })
}

export function edit(data) {
  return request({
    url: '/menu/edit',
    method: 'post',
    data: data
  })
}

export function findById(params) {
  return request({
    url: '/menu/' + params,
    method: 'get'
  })
}

export function deletePermissionById(data) {
  return request({
    url: '/menu/delete',
    method: 'post',
    data: data
  })
}

export function buildMenus() {
  return request({
    url:'/menu/buildMenus',
    method:'get'
  })
}
