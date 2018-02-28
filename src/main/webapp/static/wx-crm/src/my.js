// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
require('./config/axios.js')
import Vue from 'vue'
import my from './pages/my'
import VueScroller from 'vue-scroller'
import axios from 'axios'
// import router from './router/index'

Vue.use(VueScroller)

axios.interceptors.request.use(function (config) {
  if (config.method === 'get') {
    if (!config.params) {
      config.params = {}
    }
    config.params._ = new Date().getTime()
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
  if(response.data.code === 0){
    alert(response.data.message)
  }else if(response.data.code === 2){

  } else {

  }
  // Do something with response data
  return response
}, function (error) {
  // Do something with response error
  return Promise.reject(error)
})

Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  // router,
  template: '<my/>',
  components: { my }
})
