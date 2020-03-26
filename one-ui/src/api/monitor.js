import request from '../utils/request'

export function getDiskInfo() {
  return request({
    url: '/actuator/health',
    method: 'get'
  })
}

export function getServerInfo() {
  return request({
    url: '/monitor/serverInfo',
    method: 'get'
  })
}
