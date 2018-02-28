<template>
  <div class="list-group mine-list">
    <scroller :on-refresh="refresh" :on-infinite="infinite">
      <ul class="list-group-item" v-if="listData.length !== 0">
        <li v-for="(item, index) of listData" :key="index">
          <h4 class="list-group-item-heading" @click.self="handleCustomer(item.taskNo,item.customerNo)">
            {{item.customerName}}<template v-if="item.taskLevel"> - {{item.taskLevel}}级</template>
            <small> {{item.currentStatus | status-filter}}</small>
            <span>
              <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
                <i class="glyphicon glyphicon-earphone phone"></i>打给Ta</a>
            </span>
            <!--<span :class="[ item.today ? '' : 'text-red']">{{item.distributeTime}}</span>-->
          </h4>
          <p class="list-group-item-text height18">
            <i class="glyphicon glyphicon-plus add" @click="handleNew(item)"></i>
            <span class="ellipsis" @click="handleNew(item)">{{item.remark ? item.remark : ''}}</span>
            <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
              <!--<span>
                <i class="phone"></i>打给Ta
              </span>-->
              <span :class="[ item.today ? '' : 'text-red']">{{item.distributeTime}}</span>
            </a>

          </p>
        </li>
        <!--<li class="text-center" style="border:none" v-if="listData.length === 0">暂无数据</li>-->
      </ul>
    </scroller>
    <Loading v-if="loading"></Loading>
  </div>
</template>
<script>
import store from '../config/store.js'
import axios from 'axios'
import Loading from './Loading'
import moment from 'moment'
export default {
  name: 'tablist',
  data() {
    return {
      loading: false,
      params: null,
      listData: [],
      paramsObj: {
        '0': 'NEWCUSTOMER',
        '1': 'TALKING',
        '2': 'ORDER',
        '3': 'OTHER',
        '4': 'CONTACT'
      },
      pageIndex: 0,    // 当前页数
      limits: 10,       // 每页条数
      total: 0
    }
  },
  components: {
    Loading
  },
  methods: {
    fetchData(done) {
      // if(done(true)){
      //   return
      // }
      this.loading = true
      // 添加分页
      // axios.get('/api/customer/query?type=' + this.paramsObj[this.params.id]).then((res) => {
      axios.get('/api/customer/queryForPage?type=' + this.paramsObj[this.params.id], { params: { pageIndex: this.pageIndex, limit: this.limits } }).then((res) => {
        if (res.data.code === '1') {
          // 联系人列表单独处理下
          if(this.paramsObj[this.params.id] === 'CONTACT'){
            let middleArr = res.data.data
          // this.listData = res.data.data.rows
          // 时间比较并格式化
            middleArr.forEach((val) => {
              val.today = +moment(val.distributeTime || val.createTime) > +moment(moment().format('YYYY-MM-DD'))
              val.distributeTime = moment(val.distributeTime || val.createTime).format('HH:mm:ss')
            })
            this.listData = middleArr
            return
          }
          let middleArr = res.data.data.rows
          // this.listData = res.data.data.rows
          // 时间比较并格式化
          middleArr.forEach((val) => {
            val.today = +moment(val.distributeTime || val.createTime) > +moment(moment().format('YYYY-MM-DD'))
            val.distributeTime = moment(val.distributeTime || val.createTime).format('HH:mm:ss')
          })
          // pageindex = 0 刷新，不为0加载
          if(this.pageIndex === 0){
            this.listData = middleArr
          }else{
            this.listData = this.listData.concat(middleArr)
          }
          this.total = res.data.data.total
          if(this.pageIndex !== this.total){
            this.pageIndex += 1
          }
        }
        // done && done()
      }).finally(_ => {
        this.loading = false
      })
    },
    // 下拉刷新
    refresh(done) {
      if(done(true)){
        return
      }
      this.pageIndex = 0
      this.fetchData(done)
    },
    // 上拉加载
    infinite(done) {
      if(done(true)){
        return
      }
      this.fetchData()
    },
    // 新建沟通记录信息
    handleNew(item) {
      // store.clearAll()
      window.location.href = 'communicationRecord.html#/?customerNo=' + item.customerNo + '&taskNo=' + item.taskNo
    },
    // 打开客户信息
    handleCustomer(taskNo, customerNo) {
      store.set('indextab', this.params.id)
      // store.clearAll()
      window.location.href = 'customerDetail.html#/customer?id=' + taskNo + '&customerNo=' + customerNo
    }
  },
  computed: {
  },
  beforeRouteLeave(to, from, next) {
    next()
    // store.set('indextab' + this.params.id, {scrollX: window.scrollX, scrollY: window.scrollY, data: this.listData})

    // 在当前路由改变，但是该组件被复用时调用
    // 举例来说，对于一个带有动态参数的路径 /foo/:id，在 /foo/1 和 /foo/2 之间跳转的时候，
    // 由于会渲染同样的 Foo 组件，因此组件实例会被复用。而这个钩子就会在这个情况下被调用。
    // 可以访问组件实例 `this`
  },
  created() {
    this.params = this.$route.params
    // this.fetchData()
  },
  watch: {
    '$route'(to, from) {
      this.params = this.$route.params
      this.pageIndex = 0
      this.fetchData()
      // let storeData = store.get('indextab' + this.params.id).data
      // if(storeData && storeData.length !== 0){
      //   this.listData = storeData
      // }else{
      //   this.fetchData()
      // }
      // if (from.query.scrollY) {
      //   setTimeout(function () {
      //     window.scrollTo(from.query.scrollX, from.query.scrollY)
      //   }, 300)
      // }
    }
  }
}
</script>
