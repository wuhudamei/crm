<template>
    <div class="wrap">
        <!--<header>
            编辑客户信息</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid">
                <vue-form class="nc-form no-back" :state="formstate" @submit.prevent="sub">
                  <div :class="['form-group', (formstate.$touched || formstate.$submitted) && formstate.name.$invalid ? 'has-error' : '']">
                        <label for="exampleInputEmail1">*姓名</label>
                         <validate>
                          <input name="name" type="text" maxlength="20" class="form-control" id="exampleInputPassword1" placeholder="请输入姓名" v-model="subObj.customerName" required>
                          <field-messages name="name">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入姓名</span>
                          </field-messages>
                        </validate>
                        <!--<input type="email" class="form-control" id="exampleInputEmail1" placeholder="19922200999">-->
                    </div>


                    <!--<div :class="['form-group', (formstate.$touched || formstate.$submitted) && formstate.customerMobile.$invalid ? 'has-error' : '']">-->
                      <div class="form-group">
                        <label for="exampleInputEmail1">客户电话</label>
                        <div>
                          {{vagueMobile}}
                        </div>
                        <!--<p class="col-xs-8">{{subObj.customerMobile}}</p>-->
                         <!--<validate>-->
                          <!--<input name="customerMobile" type="number" readonly maxlength="11" class="form-control" id="exampleInputPassword1" placeholder="请输入电话" value="{{vagueMobile}}" custom-tel>-->
                          <!--<field-messages name="customerMobile">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入电话</span>
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="custom-tel">请输入正确电话</span>
                          </field-messages>-->
                        <!--</validate>-->
                        <!--<input type="email" class="form-control" id="exampleInputEmail1" placeholder="19922200999">-->
                    </div>
                    <div :class="['form-group', (formstate.$touched || formstate.$submitted) && formstate.homePhone.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">家庭固话</label>
                        <validate>
                          <input name="homePhone" type="text" maxlength="11" class="form-control" id="exampleInputPassword1" placeholder="请输入电话" v-model="subObj.homePhone">
                        </validate>
                    </div>
                    <div :class="['form-group', (formstate.$touched || formstate.$submitted) && formstate.reserveMobile.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">备用电话</label>
                        <validate>
                          <input name="reserveMobile" type="number" maxlength="11" class="form-control" id="exampleInputPassword1" placeholder="请输入电话" v-model="subObj.reserveMobile" custom-tel>
                          <field-messages name="reserveMobile">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="custom-tel">请输入正确电话</span>
                          </field-messages>
                        </validate>                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">客户标签</label>
                          <select class="form-control" name="" id="" v-model="subObj.customerTag">
                            <option value="">请选择</option>
                            <option :value="item.code" v-for="item of selectDate">{{item.text}}</option>
                          </select>
                    </div>

                    <div class="form-group">
                      <label for="exampleInputPassword1">客户级别</label>
                      <br>
                        <div class="btn-box">
                          <button type="button"
                          class="btn btn-default"
                          :class="{'btn-success':subObj.taskDistribute.taskLevel === item.code}"
                          @click="subObj.taskDistribute.taskLevel=item.code"
                          v-for="(item, index) of levels"
                          :key="item.code">{{item.text}}</button>
                        </div>
                    </div>





                    <button :disabled="loading" type="submit" class="btn btn-default">提交</button>
                </vue-form>
            </div>
        </div>
        <Modal :show.sync="modal">
          <div class="text-center">
            {{msg}}
          </div>
        </Modal>
        <Loading v-show="loading"></Loading>
    </div>
</template>


<script>
import axios from 'axios'
import Loading from '../Loading'
import Modal from '../Modal'
import VueForm from 'vue-form'
let options = {
  validators: {
    // 正整数不包括0
    'custom-integer': function (value, attrValue, vnode) {
      // return true to set input as $valid, false to set as $invalid
      return /^[0-9]*[1-9][0-9]*$/.test(value)
    },
    'custom-tel': function(value){
      return /^1[3|4|5|7|8]\d{9}$/.test(value)
    }
  }
}
export default {
  name: 'customerDetail',
  data (){
    return {
      taskLevel: 'A',
      modal: false,
      msg: '',
      loading: false,
      formstate: {},
      selectDate: null,
      // 客户级别
      levels: [],
      subObj: {
        // 客户编号
        customerNo: '',
        // 客户在订单系统中的id(与客户编号一一对应),去订单系统中查询时  需要使用该字段,代替客户编号
        custIdInOrder: null,
        // 姓名
        customerName: null,
        // 手机
        customerMobile: null,
        // 家庭固话
        homePhone: null,
        // 备用电话
        reserveMobile: null,
        taskDistribute: {
          taskLevel: ''
        }
      }
    }
  },
  mixins: [new VueForm(options)],
  components: {
    Loading,
    Modal
  },
  methods: {
    fetchDate() {
      this.loading = true
      axios.get('/api/customer/getCustomerByCustNo?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.taskNo).then((res) => {
        if(res.data.code === '1'){
          this.subObj = res.data.data
          if(!this.subObj.customerTag){
            this.subObj.customerTag = ''
          }
          this.subObj.taskDistribute.taskNo = this.query.taskNo
        }
      }).finally(_ => {
        this.loading = false
      })
    },
     // 获取客户级别
    fetchLevels (){
      return axios.get('/api/dict/findChildrenByCode?code=KHJB')
    },
    // 客户标签数据
    fetchSelectDate () {
      return axios.get('/api/dict/findChildrenByCode?code=KHBQ')
      // .then((res) => {
      //   if(res.data.code === '1'){
      //     this.selectDate = res.data.data
      //   }
      // })
    },
    fetchAll () {
      this.submitting = true
      axios.all([this.fetchLevels(), this.fetchSelectDate()]).then(axios.spread((level, data) => {
        if(level.data.code === '1'){
          this.levels = level.data.data
        }
        if(data.data.code === '1'){
          this.selectDate = data.data.data
        }
      })).finally(_ => {
        this.fetchDate()
        this.submitting = false
      })
    },
    // // 客户级别
    // fetchLevel () {
    //   return axios.get('/api/dict/findChildrenByCode?code=KHJB')
    // },
    // // 客户类型
    // fetchType () {
    //   return axios.get('/api/dict/findChildrenByCode?code=KHLX')
    // },
    sub () {
      if(this.formstate.$invalid){
        return
      }

      var self = this

      self.$nextTick(() => {
        if (self.loading) return

        self.loading = true
        axios.post('/api/customer/updateCustomer', self.subObj).then((res) => {
          if(res.data.code === '1'){
            setTimeout(() => {
              self.$router.go(-1)
            }, 1000)
          }
          self.modal = true
          self.msg = res.data.message
        }).finally(_ => {
          self.loading = false
        })
      })
    }
  },
  computed: {
    query (){
      return this.$route.query
    },
    vagueMobile () {
      return this.subObj.customerMobile
      ? this.subObj.customerMobile.substring(0, 3) + '****' + this.subObj.customerMobile.substring(7, this.subObj.customerMobile.length)
      : ''
    }
  },
  created(){
    this.fetchAll()
  }
}
</script>
