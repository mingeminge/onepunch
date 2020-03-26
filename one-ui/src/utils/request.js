import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import store from '../store'


const service = axios.create({
  baseURL: process.env.BASE_API,
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
      MessageBox.confirm(res.message, '异常', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/logout').then(() => {
          location.reload();
        })
      });
      return Promise.reject('error')
    } else {
      MessageBox.confirm(res.message, '异常', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      });
      return Promise.reject('error')
    }
  },
  error => {
    Message({
      message: '无法连接到本地代理程序，请确认代理程序是否运行正常！',
      type: 'error',
      duration: 5 * 1000
    });
    return Promise.reject(error)
  }
);

export default service
