import Vue from 'vue'
import Router from 'vue-router'
import IndexPage from '../pages/appIndex'
Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'index'
    },
    {
      path: '/appindex',
      name: 'appindex',
      components: IndexPage
    }
  ]
})
