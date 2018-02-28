import Vue from 'vue'
import Router from 'vue-router'
import IndexTab from '../components/IndexTab'
import TabList from '../components/TabList'
import Search from '../components/Search'
import AddTask from '../components/AddTask'
Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/tab',
      name: 'indexTab',
      component: IndexTab,
      children: [{
        path: ':id',
        component: TabList
      }]
    },
    // {
    //   path: '/tab',
    //   name: 'indexTab',
    //   component: TabList
    // },
    {
      path: '/addTask',
      name: 'addTask',
      component: AddTask
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    }
  ]
})
