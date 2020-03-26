import request from '@/utils/request'


export function findAll(params) {
  return request({
    url: '/setting/list',
    method: 'get',
    params: params
  })
}

export function update(params) {
  return request({
    url: '/setting/update',
    method: 'post',
    data: params
  })
}

export function deleteSetting(params) {
  return request({
    url: '/setting/' + params,
    method: 'post'
  })
}

export function save(params) {
  return request({
    url: '/setting/save',
    method: 'post',
    data: params
  })
}

export function getSettings() {
  return request({
    url: '/setting/bg',
    method: 'get'
  })
}
