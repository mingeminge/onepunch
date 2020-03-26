'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://127.0.0.1:8081"',
  //BASE_API: '"http://192.168.1.2:8081"',
  IMG_URL: '"http://47.104.20.179/images/"',
  WS_URL:'"ws://localhost:8081/monitor"'
})
