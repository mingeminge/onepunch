import axios from 'axios'
import store from '../store'
import {Message} from "element-ui";

const service = axios.create({
    baseURL: 'http://localhost:8081',
   /* baseURL:'http://47.104.20.179:9091',*/
    timeout: 30000,
})

// request拦截器
service.interceptors.request.use(
    config => {
        if (localStorage.getItem('Authorization')) {
            config.headers['Authorization'] = localStorage.getItem('Authorization');
        }
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    }
);

// response 拦截器
service.interceptors.response.use(
    response => {
        const res = response.data;
        if (res.code === 200) {
            return response.data;
        } else if (res.code === 0) {
            store.dispatch('user/logout').then(() => {
                location.reload();
            })
        } else if (res.code === 10000) {//身份过期
            Message({type: 'warning', message: '身份已过期，将重新登录'});
            return Promise.reject('error')
        } else {
            Message({type: 'warning', message: res.msg});
            return Promise.reject('error')
        }
    },
    error => {
        Message({type: 'danger', message: '无法连接到本地代理程序，请确认代理程序是否运行正常！'});
        return Promise.reject(error)
    }
);

export default service
