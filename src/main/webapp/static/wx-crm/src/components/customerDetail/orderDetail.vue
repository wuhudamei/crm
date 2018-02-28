<template>
  <div class="wrap">
        <!--<header>
            <h3>订单详情</h3>
        </header>-->
        <div class="content">
            <div class="container">
                <div class="cd-main">
                    <form class="form-horizontal cd-con">
                      <div class="form-inline h-title">
                        <h4>订单信息</h4>
                      </div>
                        <div class="form-inline">
                            <label class="col-xs-5">订单编号：</label>
                            <p class="col-xs-7">{{detail.order.orderNo}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">设计师：</label>
                            <p class="col-xs-7">{{detail.order.stylistName || '/'}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">监理：</label>
                            <p class="col-xs-7">{{detail.order.supervisorName || '/'}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">项目经理：</label>
                            <p class="col-xs-7">{{detail.order.contact || '/'}}</p>
                        </div>

                         <div class="form-inline">
                            <label class="col-xs-5">合同日期：</label>
                            <p class="col-xs-7">{{detail.order.signFinishTime | date-filter}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">开工日期：</label>
                            <p class="col-xs-7">{{detail.order.contractStartTime | date-filter}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">竣工日期：</label>
                            <p class="col-xs-7">{{detail.order.contractCompleteTime | date-filter}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">订单标签：</label>
                            <p class="col-xs-7">{{detail.order.tagName}}</p>
                        </div>

                        <div class="form-inline h-title">
                          <h4>房屋信息</h4>
                        </div>

                        <div class="form-inline">
                            <label class="col-xs-5">房屋户型：</label>
                            <p class="col-xs-7">{{detail.order.roomNum}}室{{detail.order.hallNum}}厅{{detail.order.toiletNum}}卫</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">房屋面积：</label>
                            <p class="col-xs-7">{{detail.order.floorArea}}㎡</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">房屋类型：</label>
                            <p class="col-xs-7">{{detail.order.isNew === 1 ? '新' : '旧'}}房</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">是否期房：</label>
                            <p class="col-xs-7">{{detail.order.isFDH === 1 ? '' : '非'}}期房</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">有无电梯：</label>
                            <p class="col-xs-7">{{detail.order.isLift === 1 ? '有' : '无'}}</p>
                        </div>

                        <div class="form-inline">
                            <label class="col-xs-5">计划装修时间：</label>
                            <p class="col-xs-7">{{detail.order.planDecorateYear}}年{{detail.order.planDecorateMonth}}月</p>
                        </div>

                         <div class="form-inline">
                            <label class="col-xs-5">房屋地址：</label>
                            <p class="col-xs-7">{{detail.order.province ? detail.order.province + '&nbsp;&nbsp;' : ''}}{{detail.order.city}}&nbsp;&nbsp;{{detail.order.district}}&nbsp;&nbsp;{{detail.order.address ? detail.order.address :''}}</p>
                        </div>

                        <div class="form-inline h-title">
                          <h4>签单信息</h4>
                        </div>



                        <div class="form-inline">
                            <label class="col-xs-5">套餐类型：</label>
                            <p class="col-xs-7">{{detail.order.comboType}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">折扣名称：</label>
                            <p class="col-xs-7">{{detail.order.discount}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">活动名称：</label>
                            <p class="col-xs-7">{{detail.order.activity}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">预约量房时间：</label>
                            <p class="col-xs-7">{{detail.placeOrder.planMeasureTime | date-filter}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">拆除修复费：</label>
                            <p class="col-xs-7">{{detail.placeOrder.removingRepairFee}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">搬运费：</label>
                            <p class="col-xs-7">{{detail.placeOrder.carryFee}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">远程费：</label>
                            <p class="col-xs-7">{{detail.placeOrder.remotingFee}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">预算总金额：</label>
                            <p class="col-xs-7">{{detail.placeOrder.budgetAmount}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">预付款定金：</label>
                            <p class="col-xs-7">{{detail.placeOrder.imprest}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">设计师：</label>
                            <p class="col-xs-7">{{detail.order.stylistName || '/'}}</p>
                        </div>

                         <!--<div class="form-inline">
                            <label class="col-xs-5">订单创建时间：</label>
                            <p class="col-xs-7">{{detail.order.createTime}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">订单更新时间：</label>
                            <p class="col-xs-7">{{detail.order.updateTime}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">签单人：</label>
                            <p class="col-xs-7">{{detail.order.contractor}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">签单人手机：</label>
                            <p class="col-xs-7">{{detail.order.courierMobile}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">签单时间：</label>
                            <p class="col-xs-7">{{detail.order.placeOrderTime}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">合同开工时间：</label>
                            <p class="col-xs-7">{{detail.order.contractStartTime}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">合同完工时间：</label>
                            <p class="col-xs-7">{{detail.order.contractCompleteTime}}</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">实际开工时间：</label>
                            <p class="col-xs-7">{{detail.order.actualStartTime}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">实际完工时间：</label>
                            <p class="col-xs-7">{{detail.order.actualCompletionTime}}</p>
                        </div>

                        <div class="form-inline">
                            <label class="col-xs-5">合同工期：</label>
                            <p class="col-xs-7">{{detail.order.contractDays}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">房屋户型：</label>
                            <p class="col-xs-7">{{detail.order.roomNum}}室{{detail.order.hallNum}}厅{{detail.order.toiletNum}}卫</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">房屋面积：</label>
                            <p class="col-xs-7">{{detail.order.floorArea}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">有无电梯：</label>
                            <p class="col-xs-7">{{detail.order.isLift === 1 ? '有' : '无'}}电梯</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">是否新房：</label>
                            <p class="col-xs-7">{{detail.order.isNew === 1 ? '' : '非'}}新房</p>
                        </div>
                         <div class="form-inline">
                            <label class="col-xs-5">房屋地址：</label>
                            <p class="col-xs-7">{{detail.order.province ? detail.order.province + '&nbsp;&nbsp;' : ''}}{{detail.order.city}}&nbsp;&nbsp;{{detail.order.district}}&nbsp;&nbsp;{{detail.order.address ? detail.order.address :''}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">监工：</label>
                            <p class="col-xs-7">{{detail.order.supervisorName}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">监工电话：</label>
                            <p class="col-xs-7">{{detail.order.supervisorMobile}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">设计师名称：</label>
                            <p class="col-xs-7">{{detail.order.stylistName}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">设计师电话：</label>
                            <p class="col-xs-7">{{detail.order.stylistMobile}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">客服名称：</label>
                            <p class="col-xs-7">{{detail.order.serviceName}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">客服电话：</label>
                            <p class="col-xs-7">{{detail.order.serviceMobile}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">客服所在组：</label>
                            <p class="col-xs-7">{{detail.order.serviceDepartment}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">选材时间：</label>
                            <p class="col-xs-7">{{detail.order.selectMaterialTime}}</p>
                        </div>
                        <div class="form-inline">
                            <label class="col-xs-5">合同工期：</label>
                            <p class="col-xs-7">{{detail.order.contractDays}}</p>
                        </div>
                        <div class="form-inline m-t">
                          <h4>签单信息</h4>
                        </div>-->
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
import Loading from '../Loading'
export default {
  name: 'order-detail',
  data () {
    return {
      detail: {
        order: {},
        placeOrder: {}
      }
    }
  },
  components: {
    Loading
  },
  methods: {
    fetchDate() {
      this.loading = true
      axios.get('/api/order/orderdetail?customerNo=' + this.query.customerNo + '&orderNo=' + this.query.orderNo).then((res) => {
      // axios.get('/api/order/orderdetail?customerNo=XMT17061505160&orderNo=BJ1170602004').then((res) => {
        if(res.data.code === '1'){
          this.detail = JSON.parse(res.data.data.order)
        }
      }).finally(_ => {
        this.loading = false
      })
    }
  },
  created (){
    this.fetchDate()
  },
  computed: {
    query(){
      return this.$route.query
    }
  }
}
</script>
