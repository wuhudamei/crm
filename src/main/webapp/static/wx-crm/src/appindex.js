require('./config/axios.js')
require('./config/store.js')
import Vue from 'vue'
import router from './router/appIndex'
import IndexPage from './pages/AppIndex'
import VueScroller from 'vue-scroller'
import axios from 'axios'
Vue.use(VueScroller)
let currentStatus = {
  'NEWCUSTOMER': '待邀约',
  'TALKING': '邀约中',
  'TALKSUCCESS': '邀约成功',
  'INTOSHOP': '进店',
  'ORDERSUCCESS': '已生单',
  'BACKORDER': '退单',
  'INVALID': '无效',
  'FREEZE': 'FREEZE',
  'LARGESET': '大定',
  'SMALLSET': '小定'
  // NEWCUSTOMER("待邀约"),TALKING("邀约中"),TALKSUCCESS("邀约成功"),INTOSHOP("进店"), ORDERSUCCESS("已生单"), BACKORDER("退单"), INVALID("无效"), FREEZE("冻结");
}
Vue.filter('status-filter', function (value) {
  return value ? currentStatus[value] : ''
})
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
  router,
  template: '<IndexPage/>',
  components: { IndexPage }
})
