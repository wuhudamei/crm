import Vue from 'vue'
// import VueForm from 'vue-form'
import Router from 'vue-router'
import index from '../components/customerDetail/index'
import coustomer from '../components/customerDetail/customer'
import house from '../components/customerDetail/house'
import order from '../components/customerDetail/order'
import record from '../components/customerDetail/record'
import editCustomer from '../components/customerDetail/editCustomer'
import editOrder from '../components/customerDetail/editOrder'
import editHouse from '../components/customerDetail/editHouse'
import addContants from '../components/customerDetail/addContants'
import changeBack from '../components/customerDetail/chargeback'
import labelList from '../components/customerDetail/labelList'
import newLabel from '../components/customerDetail/newLabel'
import orderDetail from '../components/customerDetail/orderDetail'
import contact from '../components/customerDetail/contact'
// var options = {
//   validators: {
//     // 正整数不包括0
//     'custom-integer': function (value, attrValue, vnode) {
//       // return true to set input as $valid, false to set as $invalid
//       return /^[0-9]*[1-9][0-9]*$/.test(value)
//     },
//     // 非负整数
//     'non-negative-integer': function (value, attrValue, vnode) {
//       // return true to set input as $valid, false to set as $invalid
//       return /^[0-9]*[1-9][0-9]*$/.test(value) || (value === '0')
//     },
//     // 两位小数
//     'two-decimal': function (value){
//       return /^\d{0,8}\.{0,1}(\d{1,2})?$/.test(value)
//     },
//     'custom-tel': function(value){
//       return /^1[3|4|5|7|8]\d{9}$/.test(value)
//     }
//   }
// }

Vue.use(Router)
// install globally
// Vue.use(VueForm, options)

export default new Router({
  // mode: 'history',
  routes: [
    {
      path: '/customer',
      // name: 'customer',
      component: index,
      children: [
        {
          path: '',
          name: 'index',
          component: coustomer
        },
        {
          path: 'house',
          name: 'house',
          component: house
        },
        {
          path: 'order',
          name: 'order',
          component: order
        },
        {
          path: 'record',
          name: 'record',
          component: record
        },
        {
          path: 'contact',
          name: 'contact',
          component: contact
        }
      ]
    },
    // {
    //   path: '/house',
    //   name: 'house',
    //   component: house
    // },
    // {
    //   path: '/order',
    //   name: 'order',
    //   component: order
    // },
    // {
    //   path: '/record',
    //   name: 'record',
    //   component: record
    // },
    {
      path: '/editOrder',
      name: 'editOrder',
      component: editOrder
    },
    {
      path: '/editHouse/:id',
      name: 'editHouse',
      component: editHouse
    },
    {
      path: '/editCustomer',
      name: 'editCustomer',
      component: editCustomer
    },
    {
      path: '/addContants',
      name: 'addContants',
      component: addContants
    },
    // 退单
    {
      path: '/changeback',
      name: 'changeback',
      component: changeBack
    },
    // 标签列表
    {
      path: '/labelList',
      name: 'labelList',
      component: labelList
    },
    // 新建标签
    {
      path: '/newLabel',
      name: 'newLabel',
      component: newLabel
    },
    // 新建标签
    {
      path: '/orderDetail',
      name: 'orderDetail',
      component: orderDetail
    }
  ]
})
