// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
require('./config/axios.js')
import Vue from 'vue'
import CommunicationRecord from './pages/CommunicationRecord'
import Router from 'vue-router'
Vue.use(Router)
// import router from './router/index'
var router = new Router({
  routes: []
})
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<CommunicationRecord/>',
  components: { CommunicationRecord }
})
