import request from '../utils/request'

export function list(param) {
  return request({
    url: '/user/list',
    method: 'get',
    params: param
  })
}

export function findById(id) {
  return request({
    url: '/user/' + id,
    method: 'get'
  })
}

export function findByUsername(username) {
  return request({
    url: '/user/findByUsername/' + username,
    method: 'get'
  })
}

export function updateAvatar(data) {
  return request({
    url: '/user/updateAvatar',
    method: 'post',
    data: data
  })
}

export function roleList() {
  return request({
    url: 'role/all',
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '/user/edit',
    method: 'post',
    data: data
  })
}

export function insert(data) {
  return request({
    url: '/user/insert',
    method: 'post',
    data: data
  })
}

export function deleteUser(data) {
  return request({
    url: '/user/delete',
    method: 'post',
    data: data
  })
}

export function deptList(param) {
  return request({
    url: '/dept/list',
    method: 'get',
    params: param
  })
}

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data: data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}

export function userInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function updatePass(data) {
  return request({
    url: '/user/changePassword',
    method: 'post',
    data: data
  })
}

export function updateUserInfo(data) {
  return request({
    url: '/user/updateInfo',
    method: 'post',
    data: data
  })
}
