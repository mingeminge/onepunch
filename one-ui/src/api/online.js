import request from '../utils/request'

export function getOnlineUserList() {
  return request({
    url: '/user/onlineList',
    method: 'get'
  })
}

export function killOnlineUser(data) {
  return request({
    url: '/user/kickout/',
    method: 'post',
    data: data
  })
}
