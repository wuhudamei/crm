<template>
  <div class="calendar-test-wrapper wrap">
    <!--<Header-component>新建沟通记录</Header-component>-->
    <div class="content">
      <div class="container">
        <vue-form :state="formstate" @submit.prevent="handleEvent">
          <div class="nc-form no-back">
            <div :class="[(touched || formstate.$touched) && formModel.communicateMode === '' ? 'has-error' : '']" class="form-group">
              <label for="communicateMode">*沟通分类</label>
              <br>
              <!--<validate>-->
                <div class="btn-box">
                  <button type="button"
                  class="btn btn-default"
                  :class="{'btn-success':formModel.communicateMode === item.code}"
                  @click="firstClick(item.code)"
                  v-for="(item, index) of levelFirst"
                  :key="item.code">{{item.text}}</button>
                </div>

                <!--<select v-model="formModel.communicateMode" id="communicateMode" name="communicateMode" required class="form-control" @change="formModel.communicateType = ''">
                  <option value="">请选择</option>
                  <option :value="item.code" v-for="(item, $index) of levelFirst">{{item.text}}</option>
                </select>-->
                <!--<field-messages name="communicateMode" show="$touched || $submitted">
                  <div slot="required" v-show="formstate.$touched || formstate.$$submitted">
                    <span class="help-block">请选择沟通分类</span>
                  </div>
                </field-messages>
              </validate>-->
            </div>
            <!--has-error-->
            <div :class="[(formstate.$touched || formstate.$submitted) && formModel.communicateType === '' ? 'has-error' : '']" class="form-group">
              <label for="communicateType">*沟通类型</label>
              <!--<validate>-->

                <div class="btn-box">
                  <button type="button"
                  v-if="item.proId === formModel.communicateMode"
                  class="btn btn-default"
                  :class="{'btn-success':formModel.communicateType === item.code}"
                  @click="formModel.communicateType=item.code"
                  v-for="(item, index) of levelSecond"
                  :key="item.code">{{item.text}}</button>
                </div>
                <span v-show="(formstate.$touched || formstate.$submitted) && formModel.communicateType === ''" class="help-block">请选择沟通类型</span>
                <!--<select v-model="formModel.communicateType" id="communicateType" name="communicateType" required class="form-control">
                  <option value="">请选择</option>
                  <option :value="item.code" v-for="(item, $index) of levelSecond" v-if="item.proId === formModel.communicateMode">{{item.text}}</option>
                </select>-->
                <!--<field-messages name="communicateType">
                  <div slot="required" v-show="formstate.$touched || formstate.$$submitted">
                    <span class="help-block">请选择沟通类型</span>
                  </div>
                </field-messages>
              </validate>-->
            </div>
            <div class="form-group" v-if="showInvalid">
              <label for="invalidFlag">无效客户</label>
              <br>
              <label class="radio-inline">
                <input v-model="formModel.invalidFlag" :disabled="invalidDisabled" type="radio" name="invalidFlag" value="0"> 是
              </label>
              <label class="radio-inline">
                <input v-model="formModel.invalidFlag" :disabled="invalidDisabled" type="radio" name="invalidFlag" value="1"> 否
              </label>
            </div>
            <div class="form-group" v-show="formModel.communicateMode === 'YY'">
              <label for="exampleInputPassword1">*邀约到店</label>
              <br>
              <label class="radio-inline">
                <input v-model="formModel.invitationStore" type="radio" name="invitationStore" value="1"> 是
              </label>
              <label class="radio-inline">
                <input v-model="formModel.invitationStore" type="radio" name="invitationStore" value="0"> 否
              </label>
              <!--<span id="helpBlock2" class="help-block">请选择是否邀约到店</span>-->
            </div>
            <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formModel.invitationStore == '1' && formModel.storeTime === '' ? 'has-error' : '']" v-show="formModel.communicateMode === 'YY' && formModel.invitationStore === '1'">
              <label for="storeTime">*到店时间</label>
              <br>
               <input ref="storeTime"
               readonly
               id="storeTime"
               name="storeTime"
               v-model="formModel.storeTime"
               @click="storeVisible=true"
               type="text" class="form-control">
              <PopupDatePicker :minDate="minDate"
               v-model="popStore"
               :visible.sync="storeVisible"
               mode="DATETIME"
               title="选择时间" ></PopupDatePicker>



              <!--<input ref="storeTime" readonly id="storeTime" name="storeTime" v-model="formModel.storeTime" type="text" class="form-control datepicker">-->
              <span v-if="(formstate.$touched || formstate.$submitted) && formModel.invitationStore == '1' && formModel.storeTime === ''" class="help-block">请选择到店时间</span>
            </div>
            <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formModel.communicateType === 'YYLF' && formModel.amontRoomTime === '' ? 'has-error' : '']" v-show="formModel.communicateType === 'YYLF'">
              <label for="amontRoomTime">量房时间</label>
              <input ref="amontRoomTime"
               readonly
               id="amontRoomTime"
               name="amontRoomTime"
               v-model="formModel.amontRoomTime"
               @click="roomVisible=true"
               type="text" class="form-control">
                <PopupDatePicker v-model="popRoom"
                 :visible.sync="roomVisible"
                 :minDate="minDate"
                 mode="DATETIME" title="选择时间" ></PopupDatePicker>
              <span v-if="(formstate.$touched || formstate.$submitted) && formModel.communicateType === 'YYLF' && formModel.amontRoomTime === ''" class="help-block">请选择量房时间</span>
            </div>
            <!--<div class="form-group">
              <label for="exampleInputEmail1">客户意向</label>
              <br>
              <div class="btn-box">
                <button type="button"
                class="btn btn-default"
                :class="{'btn-success':formModel.customerIntention === item.code}"
                @click="formModel.customerIntention=item.code"
                v-for="(item, index) of customerIntention"
                :key="item.code">{{item.text}}</button>
              </div>
            </div>-->

             <label for="exampleInputEmail1">客户级别</label>
              <br>
              <div class="btn-box">
                <button type="button"
                class="btn btn-default"
                :class="{'btn-success':formModel.customerIntention === item.code}"
                @click="formModel.customerIntention=item.code"
                v-for="(item, index) of levels"
                :key="item.code">{{item.text}}</button>
              </div>

            <div class="form-group" v-if="selectedList.length!==0">
              <label for="exampleInputEmail1">选择订单</label>

              <select v-model="formModel.orderId" class="form-control">
                <option :value="item.id" v-for="(item, $index) of selectedList">{{item.provinceName||''}}{{item.cityName || ''}}{{item.areaName || ''}}{{item.address || ''}}</option>
              </select>
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">客户标签</label>
                 <br>
                <div class="btn-box">
                  <button type="button"
                  class="btn btn-default"
                  :class="{'btn-success':formModel.customerTag === item.code}"
                  @click="formModel.customerTag=item.code"
                  v-for="(item, index) of selectDate"
                  :key="item.code">{{item.text}}</button>
                </div>
                <!--<select class="form-control" name="" id="" v-model="formModel.customerTag">
                  <option value="">请选择</option>
                  <option :value="item.code" v-for="item of selectDate">{{item.text}}</option>
                </select>            </div>-->
            </div>
            <div :class="[(formstate.$touched || formstate.$submitted) && formstate.remark.$invalid ? 'has-error' : '']" class="form-group">
              <label for="remark">*沟通内容</label>
              <validate>
                <textarea id="remark" name="remark" maxlength="250" required v-model="formModel.remark" class="form-control">
                </textarea>
                <field-messages name="remark">
                  <div slot="required" v-show="formstate.$touched || formstate.$submitted">
                    <span class="help-block">请填写沟通内容</span>
                  </div>
                  <div slot="maxlength" v-show="formstate.$touched || formstate.$submitted">
                    <span class="help-block">不能输入超过250个字</span>
                  </div>
                </field-messages>
              </validate>
            </div>
            <!--<div>
              <input type="text" class="form-control" @click="popupVisible=true" >
            </div>-->
            <button :disabled="submitting" type="submit" class="btn btn-default">保存</button>
          </div>
        </vue-form>
      </div>
    </div>
    <!--<PopupDatePicker v-model="popupDate" :visible.sync="popupVisible" mode="DATETIME" title="选择时间" ></PopupDatePicker>-->
    <go-back></go-back>
    <!--<Footer-component :active="0"></Footer-component>-->
    <Loading :show="submitting"></Loading>
    <modal :show.sync="modal">
      <div class="text-center">
        {{msg}}
      </div>
    </modal>
  </div>
</template>
<script>
require('../config/axios')
import { PopupDatePicker } from 'rocoui'
import axios from 'axios'
import moment from 'moment'
import Vue from 'vue'
import Loading from '../components/Loading'
import GoBack from '../components/GoBack.vue'
import FooterComponent from '../components/Footer'
import HeaderComponent from '../components/Header'
import VueForm from 'vue-form'
import Modal from '../components/Modal'
import $ from 'jquery'
import { dateYYYYMMDDHHMMFormat } from '../utils/date.js'
Vue.use(VueForm)
export default {
  data() {
    return {
      minDate: moment(),
      storeVisible: false,   // 到店时间pop
      roomVisible: false,
      popStore: moment(),
      popRoom: moment(),
      // 显示弹窗
      modal: false,
      // 弹窗内容
      msg: '',
      // 显示无效客户
      showInvalid: true,
      submitting: false,
      formstate: {},
      touched: false,
      selectDate: [],
      formModel: {
        orderId: '',
        orderNum: '',
        taskNo: '',
        customerNo: '',
        // 沟通方式
        communicateMode: '',
        // 沟通类型
        communicateType: '',
        // 是否无效客户
        invalidFlag: '1',
        // 邀约到店
        invitationStore: '1',
        // 到店时间
        storeTime: moment().format('YYYY-MM-DD HH') + ':00',
        // 量房时间
        amontRoomTime: moment().format('YYYY-MM-DD HH:mm'),
        // 客户意向
        customerIntention: '',
        // 客户标签
        customerTag: '',
        // 沟通内容
        remark: '',
        // 前台处理状态 TALKING("邀约中"),TALKSUCCESS("邀约成功"),INTOSHOP("进店")
        currentStatus: ''
      },
      selectedList: [],
      customerIntention: [],
      // 沟通分类一级和二级
      levelFirst: null,
      levelSecond: [],
      levels: []
    }
  },
  created() {
    this.formModel.customerNo = this.query.customerNo
    this.formModel.taskNo = this.query.taskNo
    this.fetchAll()
    this.fetchSelect()
    this.fetchSelectDate()
  },
  mounted() {
    // this.$nextTick(function () {
    //   this.activeDatetimepicker()
    // })
  },
  methods: {
    firstClick (code) {
      this.formModel.communicateMode = code
      this.formModel.communicateType = ''
    },
    handleEvent: function () {
      if (this.formstate.$invalid) {
        this.touched = true
        return false
      } else {
        // 分类不是邀约 邀约到店和到店时间 设置为空
        if(this.formModel.communicateMode !== 'YY' && this.formModel.invitationStore){
          this.formModel.invitationStore = ''
          this.formModel.storeTime = ''
        }
        // 邀约 + 预约到店时     到店时间不能为空
        if(this.formModel.communicateMode === 'YY' && this.formModel.invitationStore === '1' && this.formModel.storeTime === ''){
          return false
        }
        // 沟通类型不是预约量房，量房时间置空.,为预约量房时，量放时间不能为空
        if(this.formModel.communicateType !== 'YYLF'){
          this.formModel.amontRoomTime = ''
        }else if(this.formModel.communicateType === 'YYLF' && this.formModel.amontRoomTime === ''){
          return false
        }
        // 设置状态
        if(this.formModel.communicateMode === 'YY' && this.formModel.invitationStore === '1'){
          this.formModel.currentStatus = 'TALKSUCCESS'
        }else if (this.formModel.communicateType === 'IN'){    // 类型为进店 状态为进店
          this.formModel.currentStatus = 'INTOSHOP'
        }else {
          this.formModel.currentStatus = 'TALKING'
        }
        this.$nextTick(() => {
          if (this.submitting) return
          this.doSubmit()
        })
      }
    },
    activeDatetimepicker() {
      let self = this
      console.log($(self.$refs.storeTime))
      $(self.$refs.storeTime).datetimepicker({
        minView: 1,
        format: 'yyyy-mm-dd hh',
        todayBtn: true,
        startDate: dateYYYYMMDDHHMMFormat(new Date())
      }).on('changeDate', function (e) {
        console.log(e)
        self.formModel.storeTime = moment(e.date).format('YYYY-MM-DD HH')
        // self.formModel.amontRoomTime = dateYYYYMMDDHHMMFormat(e.date)   // amontRoomTime 和 storeTime  混乱，暂时互调位置
      })
      $(self.$refs.amontRoomTime).datetimepicker({
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        startDate: dateYYYYMMDDHHMMFormat(new Date())
      }).on('changeDate', function (e) {
        // console.log(e)
        self.formModel.amontRoomTime = dateYYYYMMDDHHMMFormat(e.date)
        // self.formModel.storeTime = dateYYYYMMDDHHMMFormat(e.date)
      })
    },
    doSubmit() {
      let self = this
      this.submitting = true
      axios.post('/api/customer/communicate', self.formModel).then(function (res) {
        if (res.data.code === '1') {
          setTimeout(() => {
            window.location.href = 'customerDetail.html#/customer/record??id=' + self.query.taskNo + '&customerNo=' + self.query.customerNo
          }, 1000)
        }
        self.modal = true
        self.msg = res.data.message
      }).finally(_ => {
        self.submitting = false
      })
    },
    // 客户标签数据
    fetchSelectDate () {
      axios.get('/api/dict/findChildrenByCode?code=KHBQ').then((res) => {
        if(res.data.code === '1'){
          this.selectDate = res.data.data
        }
      })
    },
    // 获取选中地址
    fetchSelect (){
      axios.get('/api/customer/orderListWithHouse?taskNo=' + this.query.taskNo).then((res) => {
        if(res.data.code === '1'){
          this.selectedList = res.data.data
          if(this.selectedList.length !== 0){
            this.showInvalid = false
            this.formModel.invalidFlag = '1'
            this.formModel.orderId = this.selectedList[0].orderId
            this.formModel.orderNum = this.selectedList[0].orderNum
          }
        }
      })
    },
     // 获取客户级别
    fetchLevels (){
      return axios.get('/api/dict/findChildrenByCode?code=KHJB')
    },
    // 沟通分类
    fetchType () {
      return axios.get('/api/dict/findChildrenByCode?code=GTFS')
    },
    // 客户意向
    fetchCustomer () {
      return axios.get('/api/dict/findChildrenByCode?code=CUSTOMERINTENTION')
    },
    fetchAll () {
      this.submitting = true
      axios.all([this.fetchType(), this.fetchCustomer(), this.fetchLevels()]).then(axios.spread((type, cus, levels) => {
        if(type.data.code === '1'){
          this.levelFirst = type.data.data
          this.levelFirst.forEach((value) => {
            value.children.forEach((val) => {
              val.proId = value.code
              this.levelSecond.push(val)
            })
          })
          this.formModel.communicateMode = this.levelFirst[0].code
        }
        if(cus.data.code === '1'){
          this.customerIntention = cus.data.data
        }
        if(levels.data.code === '1'){
          this.levels = levels.data.data
        }
      })).finally(_ => {
        this.submitting = false
      })
    }
  },
  components: {
    Loading,
    FooterComponent,
    HeaderComponent,
    Modal,
    GoBack,
    PopupDatePicker
  },
  computed: {
    invalidDisabled (){
      if(this.formModel.communicateType === 'YYLF' || (this.formModel.communicateMode === 'YY' && this.formModel.invitationStore === '1')){
        this.formModel.invalidFlag = '1'
        return true
      }else {
        return false
      }
    },
    query (){
      return this.$route.query
    }
  },
  watch: {
    popStore (val) {
      this.formModel.storeTime = moment(val).format('YYYY-MM-DD HH') + ':00'
    },
    popRoom (val) {
      this.formModel.amontRoomTime = moment(val).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>

<style scope>
.datepicker {
  background-color: #fff !important;
}
</style>
