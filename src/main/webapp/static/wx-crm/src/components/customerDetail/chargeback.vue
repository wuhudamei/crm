<template>
  <div class='wrap'>
        <!--<header>
            <h3>订单01<i class='feedback'></i></h3>
        </header>-->
        <!--<headercomponent>退单申请</headercomponent>-->
        <div class='content'>
            <div class='container'>
                <div class='cd-main'>
                    <h4 class="h-title">房屋信息</h4>
                    <form class="form-horizontal cd-con" action="">
                        <div class='form-inline'>
                            <label class='col-xs-3'>房屋户型：</label>
                            <p class='col-xs-9'>{{detail.order.roomNum}}室{{detail.order.hallNum}}厅{{detail.order.toiletNum}}卫</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>房屋面积：</label>
                            <p class='col-xs-9'>{{detail.order.floorArea}}㎡</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>房屋类型：</label>
                            <p class='col-xs-9'>{{detail.order.isNew === 1 ? '新' : '旧'}}房</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>是否期房：</label>
                            <p class='col-xs-9'>{{detail.order.isFDH === 1 ? '' : '非'}}期房</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>有无电梯：</label>
                            <p class='col-xs-9'>{{detail.order.isLift === 1 ? '有' : '无'}}</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>装修时间：</label>
                            <p class='col-xs-9'>{{detail.order.planDecorateYear}}年{{detail.order.planDecorateMonth}}月</p>
                        </div>
                        <div class='form-inline'>
                            <label class='col-xs-3'>房屋地址：</label>
                            <p class='col-xs-9'>{{detail.order.province ? detail.order.province + '&nbsp;&nbsp;' : ''}}{{detail.order.city}}&nbsp;&nbsp;{{detail.order.district}}&nbsp;&nbsp;{{detail.order.address ? detail.order.address :''}}</p>
                        </div>
                    </form>
                </div>
            </div>
            <div class='container aw-content'>
                    <h4 class="h-title">申请信息</h4>

                    <vue-form class="nc-form" :state="formstate" @submit.prevent="onSubmit">
                        <div class='form-group' :class="[(formstate.$touched || formstate.$submitted) && formstate.reason.$invalid ? 'has-error' : '']">
                            <label for='exampleInputEmail1'>*退单原因</label>
                            <validate>
                              <select class='form-control' v-model="subObj.backReason" name="reason" required>
                                <option value="">请选择</option>
                                <option :value="item.text" v-for="(item, $index) of returnReason" :key="$index">{{item.text}}</option>
                              </select>
                              <field-messages name="reason">
                                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择退单原因</span>
                              </field-messages>
                            </validate>


                        </div>
                        <div class='form-group' :class="[(formstate.$touched || formstate.$submitted) && formstate.remark.$invalid ? 'has-error' : '']">
                            <label for='exampleInputPassword1'>*退单说明</label>
                            <validate>
                              <textarea class='form-control' v-model="subObj.backRemark" maxlength="200" name="remark" required></textarea>
                              <field-messages name="remark">
                                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入退单说明</span>
                              </field-messages>
                            </validate>
                            <!--<textarea class='form-control' v-model="subObj.backRemark" maxlength="200"></textarea>-->
                        </div>
                        <div class='form-group' :class="[(formstate.$touched || formstate.$submitted) && formstate.debitamount.$invalid ? 'has-error' : '']">
                            <label for='exampleInputPassword1'>*扣款金额</label><br>
                             <validate>
                              <input type='number' name="debitamount" class='form-control' v-model="subObj.debitAmount" required>
                              <field-messages name="debitamount">
                                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入扣款金额</span>
                              </field-messages>
                            </validate>

                        </div>
                        <div class='form-group' :class="[(formstate.$touched || formstate.$submitted) && formstate.amount.$invalid ? 'has-error' : '']">
                            <label for='exampleInputPassword1'>*实退金额</label><br>
                            <validate>
                              <input type='number' name="amount" class='form-control' v-model="subObj.amount" required>
                              <field-messages name="amount">
                                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入实退金额</span>
                              </field-messages>
                            </validate>


                        </div>
                        <button type='submit' class='btn btn-default'>提交</button>
                    </vue-form>
                </div>
        </div>
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>
        </Modal>
        <Loading v-if="loading"></Loading>
    </div>
</template>
<script>
import axios from 'axios'
import headercomponent from '../Header'
import Loading from '../Loading'
import Modal from '../Modal.vue'

// import footercomponent from '../Footer'
export default {
  name: 'changeback',
  data () {
    return {
      formstate: {},
      modal: false,
      msg: '',
      loading: false,
      detail: {
        order: {
           // 房屋户型
          houseLayout: '',
          // 面积
          houseArea: null,
          // 房屋类型：0旧房，1新房
          houseType: 0,
          //  是否期房 0-否 1-是
          hoursing: 1,
          //  * 有无电梯 0-无 1-有
          elevator: 1,
          // 装修时间
          renovationTime: null,
          // 省市县
          provinceCode: '',
          provinceName: null,
          cityCode: '',
          cityName: null,
          areaCode: '',
          areaName: null,
          // 房屋地址
          address: ''
        }
      },
      subObj: {
        orderId: '',
        taskNo: '',
        backReason: '',
        backRemark: '',
        debitAmount: '',
        amount: '',
        store: '',
        allotState: ''
      },
      returnReason: []
    }
  },
  components: {
    headercomponent,
    Loading,
    Modal
  },
  methods: {
    onSubmit () {
      if(this.formstate.$invalid || this.loading){
        return false
      }
      this.loading = true
      axios.post('/api/order/returnOrder', this.subObj).then((res) => {
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
    // 获取detail
    fetchData () {
      return axios.get('/api/order/orderdetail?customerNo=' + this.query.customerNo + '&orderNo=' + this.query.orderNo)
      // axios.get('/api' + this.$route.params.id).then((res) => {
      //   if(res.data.code === '1'){
      //     this.details = res.data.data
      //   }
      // })
    },
    fetchReason () {
      return axios.get('/api/dict/findChildrenByCode?code=TDYY')
      // axios.get('/api/dict/findChildrenByCode?code=RETURNREASON').then((res) => {
      //   if(res.data.code === '1'){
      //     this.returnReason = res.data.data
      //   }
      // })
    },
    fetchAll () {
      this.loading = true
      axios.all([this.fetchReason(), this.fetchData()]).then(axios.spread((reason, detail) => {
        // Both requests are now complete
        // if(data.data.code === '1'){
        //   this.details = data.data.data
        // }
        if(reason.data.code === '1'){
          this.returnReason = reason.data.data
        }
        if(detail.data.code === '1'){
          this.detail = JSON.parse(detail.data.data.order)
          this.subObj.allotState = this.detail.order.allotState
          this.subObj.orderId = this.detail.order.orderId || ''
        }
      })).finally(_ => {
        this.loading = false
      })
    }
  },
  created () {
    this.fetchAll()
    this.subObj.taskNo = this.query.taskNo
  },
  computed: {
    query(){
      return this.$route.query
    }
  }
}
</script>
