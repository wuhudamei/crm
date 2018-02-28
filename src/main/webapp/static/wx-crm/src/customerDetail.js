require('./config/axios.js')
require('font-awesome-webpack')
import Vue from 'vue'
import detail from './pages/customerDetail'
import router from './router/customerDetail'
import VueScroller from 'vue-scroller'
import axios from 'axios'
import VueForm from 'vue-form'
Vue.filter('date-filter', function (value) {
  if(value === '1980-01-01 00:00:00'){
    return '-'
  }else{
    return value
  }
})
let contact = {
  'QIZI': '妻子',
  'ZHANGFY': '丈夫',
  'MUQIN': '母亲',
  'FUQIN': '父亲'
}
let comunicateType = {
  'YCYY': '一次邀约',
  'ECYY': '二次邀约',
  'THREE': '三次邀约',
  'MORE': '多次邀约',
  'IN': '进店',
  'MOBILE': '电话',
  'WEIXIN': '微信',
  'YYLF': '预约量房'

}
let tag = {
  'VIP': 'VIP',
  'ZHD': '串单',
  'PT': '普通'
}
Vue.filter('tag-filter', function (value) {
  return tag[value]
})
Vue.filter('type-filter', function (value) {
  return comunicateType[value]
})
Vue.filter('contact-filter', function (value) {
  return contact[value]
})
var options = {
  validators: {
    // 正整数不包括0
    'custom-integer': function (value, attrValue, vnode) {
      // return true to set input as $valid, false to set as $invalid
      return /^[0-9]*[1-9][0-9]*$/.test(value)
    },
    // 非负整数
    'non-negative-integer': function (value, attrValue, vnode) {
      // return true to set input as $valid, false to set as $invalid
      return /^[0-9]*[1-9][0-9]*$/.test(value) || (value === '0')
    },
    // 两位小数
    'two-decimal': function (value){
      return /^\d{0,8}\.{0,1}(\d{1,2})?$/.test(value)
    },
    'custom-tel': function(value){
      return /^1[3|4|5|7|8]\d{9}$/.test(value)
    }
  }
}
Vue.use(VueForm, options)
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
  router,
  template: '<detail/>',
  components: { detail }
})
