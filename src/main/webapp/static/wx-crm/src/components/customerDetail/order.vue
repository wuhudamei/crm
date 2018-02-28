<template>
    <!--<div class="wrap">
      <header>
          客户信息</h3>
      </header>
      <div class="content">
        <div class="container-fluid">
            <ul class="mine-tab">
              <li><router-link to="/customer">客户信息</router-link></li>
              <li><router-link to="/house">房屋信息</router-link></li>
              <li class="active"><router-link to="/order">订单信息</router-link></li>
              <li><router-link to="/record">沟通记录</router-link></li>
            </ul>
        </div>-->
        <div class="container cd-content">
            <div class="cd-main" v-for="item in list">
                <dl class="home-title">
                    <dt><img src="../../../static/img/order.png"></dt>
                    <dd>
                        <h5>{{item.orderNo}}</h5>
                        <p>{{item.createTime}}</p>
                    </dd>
                </dl>
                <p class="home-state">{{item.orderStatus}}</p>
                <form class="form-horizontal cd-con relative">
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">客户姓名：</label>
                        <p class="col-xs-8">{{item.customerName}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">监理：</label>
                        <p class="col-xs-8">{{item.supervisorName}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">订单创建人：</label>
                        <p class="col-xs-8">{{item.serviceName}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">设计师：</label>
                        <p class="col-xs-8">{{item.stylistName}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">合同编号：</label>
                        <p class="col-xs-8">{{item.contractNo}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">预算金额：</label>
                        <p class="col-xs-8">{{item.budgetAmount}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">预付金额：</label>
                        <p class="col-xs-8">{{item.imprest}}</p>
                    </div>
                    <div class="form-inline">
                        <label class="col-xs-4 text-normal">标签：</label>
                        <p class="col-xs-8">{{item.orderTagName}}</p>
                    </div>
                    <!--<div class="form-inline">
                        <label class="col-xs-4">实收金额：</label>
                        <p class="col-xs-8">{{}}</p>
                    </div>-->
                    <div class="btn-box">
                      <button v-if="ordderStatus[item.orderId.toLowerCase()] === 1" type="button" @click="chargeback(item.orderNo)" class="btn btn-success">退单</button>
                      <button v-if="ordderStatus[item.orderId.toLowerCase()] === 1" type="button" @click="editOrder(item.orderNo, item.orderId)" class="btn btn-success">编辑订单</button>
                      <button type="button" @click="orderDetail(item.orderNo)" class="btn btn-success">查看详情</button>
                      <button v-if="ordderStatus[item.orderId.toLowerCase()] === 1" type="button" @click="labelList(item.orderId, item.tagId)" class="btn btn-success">设置标签</button>
                    </div>
                    <!--<div class="row margin0">
                    <div class="col-xs-3">
                      <button type="button" @click="chargeback(item.orderNo)" class="btn btn-success  btn-block">退单</button>

                    </div>
                    <div class="col-xs-3">
                      <button type="button" @click="editOrder(item.orderNo, item.orderId)" class="btn btn-success  btn-block">编辑订单</button>
                    </div>
                     <div class="col-xs-3">
                    <button type="button" @click="orderDetail(item.orderNo)" class="btn btn-success  btn-block">查看详情</button>
                    </div>
                     <div class="col-xs-3">
                    <button type="button" @click="labelList(item.orderId, item.tagId)" class="btn btn-success  btn-block">设置标签</button>
                    </div>
                  </div>-->
                    <!--<button type="button" @click="chargeback" class="btn btn-success  btn-block">退单</button>
                    <button type="button" @click="editOrder" class="btn btn-success  btn-block">编辑订单</button>
                    <button type="button" class="btn btn-success  btn-block">查看详情</button>
                    <button type="button" @click="labelList()" class="btn btn-success  btn-block">设置标签</button>-->
                    <div class="statusMark" v-if="ordderStatus[item.orderId.toLowerCase()] !== 1">
                      {{ordderStatus[item.orderId.toLowerCase()] === 2 ? '退单中' : '退单通过'}}
                    </div>
                </form>
            </div>
            <div v-if="list.length === 0" class="text-blank">暂无数据</div>
            <Loading v-show="loading"></Loading>
        </div>
</template>


<script>
import axios from 'axios'
import Loading from '../Loading.vue'
export default {
  name: 'customerDetail',
  data (){
    return {
      ordderStatus: null,
      loading: false,
      customerInfo: null, // 客户信息
      houseInfo: null, // 房屋信息
      orderInfo: null, // 订单信息
      record: null, // 跟踪记录
      list: []
    }
  },
  components: {
    Loading
  },
  methods: {
    fetchDate() {
      var self = this
      this.loading = true
      axios.get('/api/order/orderlist?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id).then(function(res){
        if(res.data.code === '1'){
          self.list = JSON.parse(res.data.data.orders)
          self.ordderStatus = res.data.data.ordderStatus
        }else if(res.data.code === '0'){
          self.list = []
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 编辑订单
    editOrder (orderNo, orderId) {
      this.$router.push({
        path: '/editOrder',
        query: {
          taskNo: this.query.id,
          orderNo: orderNo,
          orderId: orderId,
          customerNo: this.query.customerNo
        }
      })
    },
    orderDetail (orderNo){
      this.$router.push('/orderDetail?customerNo=' + this.query.customerNo + '&orderNo=' + orderNo)
    },
    // 退单
    chargeback (orderNo) {
      this.$router.push({
        path: '/changeback?customerNo=' + this.query.customerNo + '&orderNo=' + orderNo + '&taskNo=' + this.query.id
      })
    },
    labelList (id, tag) {
      this.$router.push({
        path: '/labelList',
        query: {
          id: id,
          tag: tag
        }
      })
    }
  },
  created(){
    this.fetchDate()
  },
  ready(){
    console.log(this.ordderStatus[this.list[0].orderId.toLowerCase()])
  },
  computed: {
    query () {
      return this.$route.query
    }
  }
}
</script>
