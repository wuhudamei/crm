<template>
  <div class="wrap">
        <!--<header>
            待审批详情</h3>
        </header>-->
        <div class="content">
            <div class="container">
                <div class="cd-main">
                    <form class="form-horizontal cd-con">

                        <!--<div class="form-inline">
                            <label class="col-xs-5">申请编号：</label>
                            <p class="col-xs-7">{{detail.applyNo}}</p>
                        </div>-->
                        <div class="form-inline" v-if="query.type === '1'">
                            <label class="col-xs-5">订单编号：</label>
                            <p class="col-xs-7">20</p>
                        </div>
                        <!--<div class="form-inline">
                            <label class="col-xs-5">任务编号：</label>
                            <p class="col-xs-7">{{detail.taskNo}}</p>
                        </div>-->
                        <!--<div class="form-inline">
                            <label class="col-xs-5">客户编号：</label>
                            <p class="col-xs-7">{{detail.customerNo}}</p>
                        </div>-->

                        <div class="form-inline">
                            <label class="col-xs-5">申请人：</label>
                            <p class="col-xs-7">{{detail.applyUserName}}</p>
                        </div>

                        <div class="form-inline">
                            <label class="col-xs-5 pr0">申请标题：</label>
                            <p class="col-xs-7">{{detail.applyTitle}}</p>
                        </div>

                        <div class="form-inline">
                            <label class="col-xs-5 pr0">申请时间：</label>
                            <p class="col-xs-7">{{detail.applyTime}}</p>
                        </div>
                        <div class="form-inline" v-if="query.type === '0'">
                            <label class="col-xs-5 pr0">申请原因：</label>
                            <p class="col-xs-7">{{detail.applyReason}}</p>
                        </div>
                         <div class="form-inline" v-if="query.type === '0'">
                              <label class="col-xs-5 pr0">客户名称：</label>
                              <p class="col-xs-7">{{detail.customerName}}</p>
                          </div>
                          <div class="form-inline" v-if="query.type === '0'">
                              <label class="col-xs-5 pr0">客户电话：</label>
                              <p class="col-xs-7">{{detail.customerMobile}}</p>
                          </div>

                        <div class="" v-if="query.type === '1'">
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">退单原因：</label>
                              <p class="col-xs-7">{{detail.backReason}}</p>
                          </div>
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">客户名称：</label>
                              <p class="col-xs-7">{{detail.customerName}}</p>
                          </div>
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">客户电话：</label>
                              <p class="col-xs-7">{{detail.customerMobile}}</p>
                          </div>
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">客户地址：</label>
                              <p class="col-xs-7">{{detail.provinceName}}{{detail.cityName}}{{detail.areaName}}{{detail.address}}</p>
                          </div>

                          <div class="form-inline">
                              <label class="col-xs-5 pr0">扣款金额：</label>
                              <p class="col-xs-7">{{detail.debitAmount}}</p>
                          </div>
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">实退金额：</label>
                              <p class="col-xs-7">{{detail.amount}}</p>
                          </div>
                          <div class="form-inline">
                              <label class="col-xs-5 pr0">退单说明：</label>
                              <p class="col-xs-7">{{detail.backRemark}}</p>
                          </div>
                        </div>
                        <button :disabled="loading" @click="boxStatus=true;agree=true" type="button" class="btn btn-success  btn-block">通过</button>
                        <button @click="update=true" :disabled="loading" v-if="query.type === '0'" type="button" class="btn btn-success  btn-block" >转派</button>
                        <button @click="boxStatus=true;agree=false"  :disabled="loading" v-if="query.type === '1'" type="button" class="btn btn-success  btn-block" >拒绝</button>
                    </form>
                </div>
            </div>
        </div>
        <div :class="['tanboxbg',update ? '' : 'hidden']" @click.self="update=false">
          <div class="tanbox">
              <ul>
                <li v-for="item of personList"><label class="lebal-tag"><input name="radio" type="radio" v-model="jobNum" :value="item.jobNum">{{item.empName}}</label></li>
              </ul>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal" @click.self="update=false">关闭</button>
                  <button type="button" class="btn btn-primary bg578f6e" @click="handleUpdate">确认</button>
              </div>
          </div>
      </div>

      <!--通过，拒绝二次确认-->
      <div :class="['tanboxbg',boxStatus ? '' : 'hidden']" @click.self="boxStatus=false">
          <div class="tanbox">
              <h4 class="text-center" style="padding:15px 0;">{{agree ? '确认通过?' : '确认拒绝?'}}</h4>

              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal" @click.self="boxStatus=false">关闭</button>
                  <button type="button" class="btn btn-primary bg578f6e" @click="handleConfirm">确认</button>
              </div>
          </div>
      </div>
      <go-back></go-back>
        <modal :show.sync="modal">
          <div class="text-center">
            {{msg}}
          </div>
        </modal>
        <loading v-show="loading"></loading>
    </div>
</template>
<script>
import axios from 'axios'
// axios form-data用
import qs from 'qs'
import Loading from './Loading.vue'
import Modal from './Modal'
import GoBack from '../components/GoBack.vue'
export default {
  name: 'approve-detail',
  data () {
    return {
      boxStatus: false,   // 确认拒绝弹窗
      agree: true,      // 确认or拒绝
      jobNum: '',
      loading: false,
      detail: {},
      modal: false,
      msg: '',
      // 显示联系人弹窗
      update: false,
      selectedId: '',
      personList: []
    }
  },
  components: {
    Loading,
    Modal,
    GoBack
  },
  methods: {
    // 人员列表
    fetchList (){
      this.loading = true
      axios.get('/api/employee/listCollection', {params: {
        store: this.detail.store,
        jobNum: this.detail.applyUser,
        isWchat: true
        // dataSource: this.detail.dataSource
      }}).then((res) => {
        if(res.data.code === '1'){
          this.personList = res.data.data.rows
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 获取详情
    fetchData (){
      this.loading = true
      // 0是无效  1是退单
      let url = this.query.type === '1' ? '/api/order/returnorderdetail/' : '/api/schedule/invalidatedetail/'
      axios.get(url + this.query.applyNo).then((res) => {
        if(res.data.code === '1'){
          this.detail = res.data.data
          this.fetchList()
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 同意事件
    handleAgree (){
      this.loading = true
      let url = this.query.type === '1' ? '/api/schedule/agreeereturnorder' : '/api/schedule/invalidateagree/'
      let data = {
        id: this.detail.id,
        taskNo: this.detail.taskNo,
        orderId: this.detail.orderId || ''
      }
      axios.get(url, {params: data}).then((res) => {
        if(res.data.code === '1'){
          setTimeout(() => {
            this.$router.go(-1)
          }, 1500)
        }
        this.modal = true
        this.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    },
    // 拒绝事件
    handleDisagree (){
      this.loading = true
      let data = {
        id: this.detail.id,
        taskNo: this.detail.taskNo,
        orderId: this.detail.orderId || ''
      }
      axios.get('/api/schedule/refusereturnorder', {params: data}).then((res) => {
        if(res.data.code === '1'){
          setTimeout(() => {
            this.$router.go(-1)
          }, 1500)
        }
        this.modal = true
        this.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    },
    handleConfirm (){
      this.boxStatus = false
      if(this.agree){
        this.handleAgree()
      }else{
        this.handleDisagree()
      }
    },
    // 转发事件
    handleUpdate (){
      if(this.jobNum === ''){
        return false
      }
      this.loading = true
      let postData = {
        taskNo: this.detail.taskNo,
        jobNum: this.jobNum,
        isWchat: true,
        applyId: this.detail.id
      }
      axios.post('/api/task/update', qs.stringify(postData), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then((res) => {
        if(res.data.code === '1'){
          this.update = false
          setTimeout(() => {
            this.$router.go(-1)
          }, 1500)
        }
        this.modal = true
        this.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    }
  },
  computed: {
    query () {
      return this.$route.query
    }
  },
  created () {
    this.fetchData()
  }
}
</script>
