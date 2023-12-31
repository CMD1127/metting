'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

/**
 *  修改后端访问接口
 */
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  BASE_API: '"http://localhost:8201"',
})
