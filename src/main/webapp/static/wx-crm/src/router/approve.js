import Vue from 'vue'
import Router from 'vue-router'
import Approve from '../components/Approval'
import Detail from '../components/ApproveDetail'
import Schedule from '../components/Schedule'
import ScheduleDetail from '../components/ScheduleDetail'
import AddSchedule from '../components/AddSchedule'

Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'list',
      component: Approve
    },
    {
      path: '/detail',
      name: 'detail',
      component: Detail
    },
    {
      path: '/schedule',
      name: 'schedule',
      component: Schedule
    },
    {
      path: '/sdetail/:id',
      name: 'scheduleDetail',
      component: ScheduleDetail
    },
    {
      path: '/addchedule',
      name: 'addchedule',
      component: AddSchedule
    }
  ]
})
