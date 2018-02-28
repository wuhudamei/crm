<template>
  <!--<div class="wrap">
      <header>
          客户信息</h3>
      </header>
      <div class="content">
        <div class="container-fluid">
            <ul class="mine-tab">
              <li class="active"><router-link to="/customer">客户信息</router-link></li>
              <li><router-link to="/house">房屋信息</router-link></li>
              <li><router-link to="/order">订单信息</router-link></li>
              <li><router-link to="/record">沟通记录</router-link></li>
            </ul>
        </div>-->
        <div class="container cd-content">
            <div class="cd-main">
              <form class="form-horizontal cd-con relative" v-if="customerInfo">
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">客户姓名：</label>
                      <p class="col-xs-8">{{customerInfo.customerName}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">客户电话：</label>
                      <p class="col-xs-8">{{vagueMobile}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">家庭固话：</label>
                      <p class="col-xs-8">{{customerInfo.homePhone ? customerInfo.homePhone : ''}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">备用电话：</label>
                      <p class="col-xs-8">{{vaguereserveMobile}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">邀约码：</label>
                      <p class="col-xs-8">{{(customerInfo.taskDistribute&&customerInfo.taskDistribute.invitationCode) || ''}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">标签：</label>
                      <p class="col-xs-8">{{customerInfo.customerTag || '' | tag-filter}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">客户级别：</label>
                      <p class="col-xs-8">{{customerInfo.taskDistribute.taskLevel}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">介绍人：</label>
                      <p class="col-xs-8">{{(customerInfo.taskDistribute&&customerInfo.taskDistribute.introducer) || ''}}</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4 text-normal">介绍人电话：</label>
                      <p class="col-xs-8">{{(customerInfo.taskDistribute&&customerInfo.taskDistribute.introducerTel)|| ''}}</p>
                  </div>
                <div class="form-inline">
                  <label class="col-xs-4 text-normal">推广来源：</label>
                  <p class="col-xs-8">{{(customerInfo.taskDistribute&&customerInfo.taskDistribute.promoteSource)|| ''}}</p>
                </div>
                <div class="form-inline">
                  <label class="col-xs-4 text-normal">备注：</label>
                  <p class="col-xs-8">{{(customerInfo.taskDistribute&&customerInfo.taskDistribute.remark)|| ''}}</p>
                </div>
                  <!--<div class="form-inline">
                      <label class="col-xs-4 text-normal">客户地址：</label>
                      <p class="col-xs-8">北京市丰台区莲花南路27号北京市丰台区莲花南路27号</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4">客户来源：</label>
                      <p class="col-xs-8">报纸媒体</p>
                  </div>
                  <div class="form-inline">
                      <label class="col-xs-4">客户类型</label>
                      <p class="col-xs-8">A</p>
                  </div>-->
                  <div class="btn-box">
                      <button :disabled="loading" v-if="customerInfo.taskDistribute.status === '1'" type="button" class="btn btn-success" @click="handleEdit">编辑信息</button>
                      <button :disabled="loading" v-if="customerInfo.taskDistribute.status === '1'" type="button" class="btn btn-success" @click="handleNew">新增联系人</button>
                      <button :disabled="loading" v-if="customerInfo.taskDistribute.status === '1'" type="button" class="btn btn-success" @click="handleInvalid">无效客户</button>
                      <button :disabled="loading" v-if="customerInfo.taskDistribute.status === '1'" type="button" class="btn btn-success" @click="update=true">转派</button>
                    </div>
                  <!--<div class="row margin0">-->
                    <!--<div class="col-xs-4">
                        <button type="button" class="btn btn-success  btn-block" @click="invalid">无效客户</button>
                    </div>-->
                    <!--<div class="col-xs-6">
                        <button type="button" class="btn btn-success  btn-block" @click="handleEdit">编辑信息</button>
                    </div>
                     <div class="col-xs-6">
                        <button type="button" class="btn btn-success  btn-block" @click="handleNew">新增联系人</button>
                    </div>
                  </div>-->
                  <!--<button type="button" class="btn btn-success  btn-block" @click="invalid">无效客户</button>-->
                  <!--<button type="button" class="btn btn-success  btn-block">
                    <router-link to="/editCustomer">编辑信息</router-link>
                  </button>-->
                  <!--<button type="button" class="btn btn-success  btn-block" @click="handleEdit">编辑信息</button>-->
                  <!--<button type="button" class="btn btn-success  btn-block">
                    <router-link to="/addContants">新增联系人</router-link>

                  </button>-->
                  <!--<button type="button" class="btn btn-success  btn-block" @click="handleNew">新增联系人</button>-->
                  <div class="statusMark" v-if="customerInfo.taskDistribute.status !== '1'">
                    无效
                  </div>
              </form>

              <form class="form-horizontal cd-con relative" v-if="!customerInfo">
                <div class="form-inline">
                      <p class="col-xs-12 text-center">暂无数据</p>
                </div>
              </form>

          </div>
          <Loading v-show="loading"></Loading>
           <Modal :show.sync="modal" :auto="auto">
            <div class="text-center" style="padding:20px 0">{{msg}}</div>
            <div class="modal-footer" style="padding-bottom:0">
              <button v-show="btnShow" @click="cancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
              <button :disabled="loading" v-show="btnShow" @click="confirm" type="button" class="btn btn-primary bg578f6e">确定</button>
            </div>
          </Modal>

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
        </div>

</template>


<script>
import qs from 'qs'
import axios from 'axios'
import Modal from '../Modal.vue'
import Loading from '../Loading.vue'
// import Util from '../../utils/common'
export default {
  name: 'customerDetail',
  data (){
    return {
      loading: false,
      customerInfo: {
        taskDistribute: {status: null, taskLevel: null}
      }, // 客户信息
      houseInfo: null, // 房屋信息
      orderInfo: null, // 订单信息
      record: null, // 跟踪记录
      modal: false,   // 显示弹窗
      msg: '',    // 弹窗文字
      btnShow: false,  // 显示按钮
      auto: true,   // 自动隐藏
      // 显示联系人弹窗
      update: false,
      personList: [],
      // 转派的跟单员
      jobNum: ''
    }
  },
  components: {
    Modal,
    Loading
  },
  methods: {
    // 转发事件
    handleUpdate(){
      if(this.jobNum === ''){
        return false
      }
      this.loading = true
      let postData = {
        taskNo: this.customerInfo.taskDistribute.taskNo,
        jobNum: this.jobNum,
        oldJobNum: this.customerInfo.taskDistribute.mechandiser,
        isWchat: true
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
    },
    // 人员列表
    fetchList () {
      this.loading = true
      axios.get('/api/employee/listCollection', {params: {
        store: this.customerInfo.taskDistribute.store,
        jobNum: this.customerInfo.taskDistribute.mechandiser,
        isWchat: true
      }}).then((res) => {
        if(res.data.code === '1'){
          this.personList = res.data.data.rows
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 获取客户信息
    fetchDate () {
      var self = this
      this.loading = true
      axios.get('/api/customer/getCustomerByCustNo?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id).then(function(res){
        if(res.data.code === '1'){
          self.customerInfo = res.data.data
          self.fetchList()
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 设置为无效客户
    // 新增联系人
    handleNew () {
      this.$router.push('/addContants?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id)
    },
     // 编辑信息
    handleEdit () {
      this.$router.push('/editCustomer?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id)
    },
    handleInvalid () {
      this.modal = true   // 显示弹窗
      this.msg = '确认设置无效吗'    // 弹窗文字
      this.btnShow = true  // 显示按钮
      this.auto = false   // 自动隐藏
    },
    // 删除房屋确认
    confirm () {
      this.loading = true
      this.btnShow = false
      axios.post('/api/customer/invalidateuserapply?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id).then((res) => {
        if(res.data.code === '1'){
          this.customerInfo.taskDistribute.status = '2'
        }
        this.auto = true
        this.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    },
    // 取消删除
    cancel () {
      this.modal = false
      this.btnShow = false
      this.auto = true
    }
  },
  created(){
    this.fetchDate()
  },
  computed: {
    query () {
      return this.$route.query
    },
    vagueMobile () {
      return this.customerInfo.customerMobile
      ? this.customerInfo.customerMobile.substring(0, 3) + '****' + this.customerInfo.customerMobile.substring(7, this.customerInfo.customerMobile.length)
      : ''
    },
    vaguereserveMobile () {
      return this.customerInfo.reserveMobile
      ? this.customerInfo.reserveMobile.substring(0, 3) + '****' + this.customerInfo.reserveMobile.substring(7, this.customerInfo.reserveMobile.length)
      : ''
    }
  },
  watch: {

  }
}
</script>
