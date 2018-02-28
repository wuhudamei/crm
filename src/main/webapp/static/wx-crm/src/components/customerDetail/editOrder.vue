<template>
  <div class="wrap">
    <!--<header>
              编辑订单</h3>
          </header>-->
    <!--<Header-component>{{title}}订单</Header-component>-->
    <div class="content">
      <div class="container-fluid no-content">
        <h4>签单信息</h4>
        <vue-form :state="formstate" @submit.prevent="handleEvent">
          <div class="nc-form">
            <div :class="[(touched || formstate.$touched) && formstate.packageType.$invalid ? 'has-error' : '']" class="form-group">
              <label for="packageType">*套餐类型</label>
              <validate>
                <input v-model="comboType" required type="input" id="packageType" name="packageType" class="form-control" placeholder="请输入套餐类型">
                <field-messages name="packageType" show="$touched || $submitted">
                  <div slot="required">
                    <span class="help-block">请输入套餐类型</span>
                  </div>
                </field-messages>
              </validate>
            </div>
            <!--form-group-->
            <div :class="[(touched || formstate.$touched) && planMeasureTime === '' ? 'has-error' : '']" class="form-group">
              <label for="planMeasureTime">*预计量房时间</label>
              <input ref="planMeasureTime" readonly id="planMeasureTime" name="planMeasureTime" v-model="planMeasureTime" @click="dateVisible=true" type="text" class="form-control">
              <PopupDatePicker
              v-model="popDate"
              :visible.sync="dateVisible"
              :minDate="minDate"
              mode="DATETIME"
              title="选择时间">
              </PopupDatePicker>
              <span v-if="(touched || formstate.$touched) && planMeasureTime === '' ? 'has-error' : ''" class="help-block">请选择预计量房时间</span>
              <!--<validate>
                <input ref="planMeasureTime" readonly required v-model="planMeasureTime" id="planMeasureTime" name="planMeasureTime" class="form-control datepicker">
                <field-messages name="planMeasureTime" show="$touched || $submitted">
                  <div slot="required">
                    <span class="help-block">请选择预计量房时间</span>
                  </div>
                </field-messages>
              </validate>-->
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="activityName">活动名称</label>
              <input v-model="activity" type="text" class="form-control" id="activityName" placeholder="请输入活动名称">
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="discountName">折扣名称</label>
              <input v-model="discount" type="text" class="form-control" id="discountName" placeholder="请输入折扣名称">
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="repairCost">拆除修复费</label>
              <input v-model="removingRepairFee" type="number" class="form-control" id="repairCost" placeholder="请输入拆除修复费">
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="distanceCost">远程费</label>
              <input v-model="remotingFee" type="number" class="form-control" id="distanceCost" placeholder="请输入远程费">
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="carryCost">搬运费</label>
              <input v-model="carryFee" type="number" class="form-control" id="carryCost" placeholder="请输入搬运费">
            </div>
            <!--form-group-->
            <div :class="[(touched || formstate.$touched) && formstate.budgetAmount.$invalid ? 'has-error' : '']" class="form-group">
              <label for="budgetAmount">*预算总金额</label>
              <validate>
                <input v-model="budgetAmount" required type="number" id="budgetAmount" name="budgetAmount" class="form-control" placeholder="请输入预算总金额">
                <field-messages name="budgetAmount" show="$touched || $submitted">
                  <div slot="required">
                    <span class="help-block">请输入预算总金额</span>
                  </div>
                </field-messages>
              </validate>
            </div>
            <!--form-group-->
            <div :class="[(touched || formstate.$touched) && formstate.advance.$invalid ? 'has-error' : '']" class="form-group">
              <label for="advance">*预付款</label>
              <validate>
                <input v-model="imprest" required type="number" id="advance" name="advance" class="form-control" placeholder="请输入预付款">
                <field-messages name="advance" show="$touched || $submitted">
                  <div slot="required">
                    <span class="help-block">请输入预付款</span>
                  </div>
                </field-messages>
              </validate>
            </div>
            <!--form-group-->
            <div class="form-group">
              <label for="note">备注</label>
              <textarea id="note" maxlength="250" required v-model="remarkContent" class="form-control"></textarea>
            </div>
            <!--form-group-->
            <button :disabled="submitting" type="submit" class="btn btn-default">保存</button>
          </div>
        </vue-form>
      </div>
    </div>
    <!--<Footer-component :active="0"></Footer-component>-->
    <Loading :show="submitting"></Loading>
    <Modal :show.sync="modal">
      <div class="text-center" v-text="msg"></div>
    </Modal>
  </div>
</template>
<script>
import axios from 'axios'
import Vue from 'vue'
import Loading from '../../components/Loading'
import Modal from '../../components/Modal.vue'
import FooterComponent from '../../components/Footer'
import HeaderComponent from '../../components/Header'
import { PopupDatePicker } from 'rocoui'
import VueForm from 'vue-form'
import $ from 'jquery'
// import { dateYYYYMMDDHHMMFormat } from '../../utils/date.js'
import moment from 'moment'
Vue.use(VueForm)
export default {
  data() {
    return {
      // datetimepicker用
      minDate: moment(),
      popDate: moment(),
      dateVisible: false,
      modal: false,
      msg: '',
      title: '新增',
      submitting: false,
      formstate: {},
      touched: false,
      orderId: null, //
      houseId: null, // 房屋id
      taskNo: null, //
      customerNo: null, //
      placeOrder: null, //
      order: null, //
      comboType: null, // 套餐类型
      // planMeasureTime: null, // 预约量房时间
      activity: null, // 活动名称
      discount: null, // 折扣名称
      removingRepairFee: null, // 拆除修复费
      remotingFee: null, // 远程费
      carryFee: null, // 搬运费
      budgetAmount: null, // 预算总金额
      imprest: null, // 预付款
      remarkContent: null, // 备注
      houseOrder: null
    }
  },
  created() {
    this.orderNo = this.$router.currentRoute.query.orderNo
    this.customerNo = this.$router.currentRoute.query.customerNo
    this.taskNo = this.$router.currentRoute.query.taskNo
    this.orderId = this.$router.currentRoute.query.orderId
    this.houseId = this.$router.currentRoute.query.houseId
    // this.houseId = 40
    if (this.orderNo) {
      this.fetchData()
      this.title = '编辑'
    }
    if (this.houseId) {
      this.fetchHouseData()
    }
  },
  mounted() {
    // this.$nextTick(function () {
    //   this.activeDatetimepicker()
    // })
  },
  methods: {
    fetchData() {
      let self = this
      axios.get('/api/order/orderdetail?customerNo=' + self.customerNo + '&orderNo=' + this.orderNo).then(function (res) {
        if (res.data.code === '1') {
          self.placeOrder = JSON.parse(res.data.data.order).placeOrder
          self.order = JSON.parse(res.data.data.order).order
          self.comboType = self.placeOrder.comboType
          // self.planMeasureTime = self.placeOrder.planMeasureTime
          self.popDate = moment(self.placeOrder.planMeasureTime)
          if (self.planMeasureTime === '1980-01-01 00:00:00') {
            self.planMeasureTime = ''
          }
          self.activity = self.order.activity
          self.discount = self.order.discount
          self.removingRepairFee = self.placeOrder.removingRepairFee
          self.carryFee = self.placeOrder.carryFee
          self.remotingFee = self.placeOrder.remotingFee
          self.budgetAmount = self.placeOrder.budgetAmount
          self.imprest = self.placeOrder.imprest
          self.remarkContent = JSON.parse(res.data.data.order).remark.remarkContent
        }
      })
    },
    fetchHouseData() {
      let self = this
      axios.get('/api/customer/housedetail?id=' + this.houseId).then(function (res) {
        if (res.data.code === '1') {
          self.houseOrder = res.data.data
          if (res.data.data.renovationTime) {
            self.popDate = moment(res.data.data.renovationTime)
          }
        }
      })
    },
    handleEvent: function () {
      if (this.formstate.$invalid) {
        this.touched = true
        return false
      } else {
        this.$nextTick(() => {
          if (!this.submitting) {
            this.doSubmit()
          }
        })
      }
    },
    activeDatetimepicker() {
      let self = this
      $(self.$refs.planMeasureTime).datetimepicker({
        minView: 2,
        format: 'yyyy-mm-dd',
        todayBtn: true,
        startDate: moment(new Date()).format('YYYY-MM-DD')
      }).on('changeDate', function (e) {
        self.planMeasureTime = moment(e.date).format('YYYY-MM-DD')
      })
    },
    doSubmit() {
      let self = this
      self.submitting = true
      var data = {
        order: {
          orderNo: self.orderNo,
          activity: self.activity,
          discount: self.discount,
          orderId: self.orderId,
          comboType: self.comboType
        },
        placeOrder: {
          orderId: self.orderId,
          comboType: self.comboType,
          planMeasureTime: moment(self.planMeasureTime).format('YYYY-MM-DD HH:mm:ss'),
          renovationTime: moment(self.houseOrder.renovationTime).format('YYYY-MM-DD HH:mm:ss'),
          removingRepairFee: self.removingRepairFee,
          remotingFee: self.remotingFee,
          carryFee: self.carryFee,
          budgetAmount: self.budgetAmount,
          imprest: self.imprest
        },
        remark: {
          remarkContent: self.remarkContent
        },
        taskNo: self.taskNo,
        customerNo: self.customerNo,
        houseId: self.houseId
      }
      if (self.houseId) {
        // data.order.customerId = ''
        // data.order.tagId = ''
        var roomIndex = self.houseOrder.houseLayout.indexOf('室')
        data.order.roomNum = self.houseOrder.houseLayout.slice(0, roomIndex)
        var hallIndex = self.houseOrder.houseLayout.indexOf('厅')
        data.order.hallNum = self.houseOrder.houseLayout.slice(roomIndex + 1, hallIndex)
        var toiletIndex = self.houseOrder.houseLayout.indexOf('卫')
        data.order.toiletNum = self.houseOrder.houseLayout.slice(hallIndex + 1, toiletIndex)
        data.order.floorArea = self.houseOrder.houseArea
        data.order.isNew = self.houseOrder.houseType
        data.order.isLift = self.houseOrder.elevator
        data.order.isFDH = self.houseOrder.hoursing
        data.order.province = self.houseOrder.provinceName
        data.order.city = self.houseOrder.cityName
        data.order.district = self.houseOrder.areaName
        data.order.address = self.houseOrder.address
        data.order.districtCode = self.houseOrder.areaCode
        data.order.cityCode = self.houseOrder.cityCode
        data.order.provinceCode = self.houseOrder.provinceCode
        data.order.activity = self.activity
        data.order.discount = self.discount
        // data.order.planDecorateYear = ''
        // data.order.planDecorateMonth = ''
        // data.order.reMeasureCreateTime = ''
        // data.order.serviceUserId = ''
        // data.order.comboType = self.comboType
      }
      axios.post('/api/order', data).then(function (res) {
        if (res.data.code === '1') {
          setTimeout(() => {
            self.$router.push('/customer/order?id=' + self.$route.query.taskNo + '&customerNo=' + self.$route.query.customerNo)
          }, 1500)
        }
        self.modal = true
        self.msg = res.data.message
      }).catch(function () { }).finally(function () {
        self.submitting = false
      })
    }
  },
  components: {
    Loading,
    FooterComponent,
    HeaderComponent,
    Modal,
    PopupDatePicker
  },
  computed: {
    planMeasureTime () {
      return moment(this.popDate).format('YYYY-MM-DD')
    }
  },
  watch: {
  }
}
</script>
