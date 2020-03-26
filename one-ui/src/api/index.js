import request from '../utils/request'

export function getInfo() {
  return request({
    url: '/user/info/',
    method: 'get'
  })
}

export function getBarChart(username) {
  return request({
    url: '/index/barChart/' + username,
    method: 'get'
  })
}

export function getLoginInfo() {
  return request({
    url: '/index/loginInfo',
    method: 'get'
  })
}

export function getRegionalDistribution() {
  return request({
    url: '/index/getRegionalDistribution',
    method: 'get'
  })
}
