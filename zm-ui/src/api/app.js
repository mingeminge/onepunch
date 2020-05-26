import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}

export function userInfo() {
    return request({
        url: '/info/',
        method: 'get',
    })
}

export function getSetting(type) {
    return request({
        url: '/setting/getSetting',
        method: 'get',
        params: type
    })
}