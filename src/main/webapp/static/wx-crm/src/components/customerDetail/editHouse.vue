<template>
    <div class="wrap">
        <!--<header>
            <h3>{{isCreate ? '新增房产' : '编辑房产'}}</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid no-content">
                <h4>房屋信息</h4>
                <!--<form class="nc-form">-->
                  <vue-form class="nc-form" :state="formstate" @submit.prevent="onSubmit">
                    <div class="form-group">
                        <label for="exampleInputEmail1">*房屋户型</label>
                        <div class="row">
                            <div class="col-xs-4">
                                <div :class="['col-xs-9', (formstate.$touched || formstate.$submitted) && formstate.room.$invalid ? 'has-error' : '']">
                                    <validate tag="label">
                                      <input class="form-control" id="exampleInputEmail1" v-model="room" name="room" type="number" min="1" custom-integer  required />

                                      <field-messages name="room">
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入室</span>
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="custom-integer">请输入正整数</span>
                                      </field-messages>
                                    </validate>
                                </div>
                                <label class="col-xs-3">室</label>
                            </div>
                            <div class="col-xs-4">
                                <div :class="['col-xs-9', (formstate.$touched || formstate.$submitted) && formstate.hall.$invalid ? 'has-error' : '']">
                                    <validate tag="label">
                                      <input class="form-control" id="exampleInputEmail1" v-model="hall" name="hall" type="number" min="0" non-negative-integer  required />
                                      <field-messages name="hall">
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入厅</span>
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="non-negative-integer">请输入非负整数</span>
                                      </field-messages>
                                    </validate>
                                </div>
                                <label class="col-xs-3">厅</label>
                            </div>
                            <div class="col-xs-4">
                                <div :class="['col-xs-9', (formstate.$touched || formstate.$submitted) && formstate.toilet.$invalid ? 'has-error' : '']">
                                    <validate tag="label">
                                      <input class="form-control" id="exampleInputEmail1" v-model="toilet" name="toilet" type="number" min="0" non-negative-integer  required />

                                      <field-messages name="toilet">
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入卫</span>
                                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="non-negative-integer">请输入非负整数</span>
                                      </field-messages>
                                    </validate>
                                </div>
                                <label class="col-xs-3">卫</label>
                            </div>
                        </div>

                    </div>
                    <div :class="[(formstate.$touched || formstate.$submitted) && formstate.houseArea.$invalid ? 'has-error' : '', 'form-group']">
                        <label for="exampleInputPassword1">*房屋面积</label>
                        <validate>
                          <input class="form-control" id="exampleInputPassword1" v-model="subObj.houseArea" name="houseArea" type="number" min="0" two-decimal  required />

                          <field-messages name="houseArea">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入房屋面积</span>
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="two-decimal">请输入两位小数</span>
                          </field-messages>
                        </validate>
                        <!--<input type="password" class="form-control" id="exampleInputPassword1" placeholder="请输入房屋面积">-->
                    </div>
                    <div :class="[(formstate.$touched || formstate.$submitted) && !(subObj.houseType || subObj.houseType == 0)? 'has-error' : '', 'form-group']">
                        <label for="exampleInputPassword1">*房屋类型</label><br>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions0" id="inlineRadio1" v-model="subObj.houseType" value="0"> 旧房
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions0" id="inlineRadio2" v-model="subObj.houseType" value="1"> 新房
                        </label>
                        <span class="help-block">请选择房屋类型</span>
                    </div>
                    <div :class="[(formstate.$touched || formstate.$submitted) && !(subObj.hoursing || subObj.hoursing == 0) ? 'has-error' : '', 'form-group']">
                        <label for="exampleInputPassword1">*是否期房</label><br>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions1" id="inlineRadio1" v-model="subObj.hoursing" value="1"> 期房
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions1" id="inlineRadio2" v-model="subObj.hoursing" value="0"> 现房
                        </label>
                    </div>
                    <div :class="[(formstate.$touched || formstate.$submitted) && !subObj.elevator ? 'has-error' : '', 'form-group']">
                        <label for="exampleInputPassword1">*有无电梯</label><br>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions2" id="inlineRadio1" v-model="subObj.elevator" value="1"> 有
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="inlineRadioOptions2" id="inlineRadio2" v-model="subObj.elevator" value="0"> 无
                        </label>
                    </div>
                    <div :class="[(formstate.$touched || formstate.$submitted) && !subObj.renovationTime ? 'has-error' : '', 'form-group']">
                        <label for="exampleInputPassword1">*装修时间</label>

                        <input ref="renovationTime" readonly id="exampleInputPassword1" name="renovationTime" v-model="subObj.renovationTime" @click="dateVisible=true" type="text" class="form-control">
                        <PopupDatePicker
                        v-model="popDate"
                        :visible.sync="dateVisible"
                        :minDate="minDate"
                        mode="DATETIME"
                        title="选择时间">
                        </PopupDatePicker>

                        <span class="help-block" v-if="(formstate.$touched || formstate.$submitted) && !subObj.renovationTime" >请选择装修时间</span>
                        <!--<validate>
                          <input ref="renovationTime" name="renovationTime" type="text" class="form-control datepicker" readonly required v-model="subObj.renovationTime" id="exampleInputPassword1">

                          <field-messages name="renovationTime">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择装修时间</span>
                          </field-messages>
                        </validate>-->


                        <!--<span class="help-block" v-if="(formstate.$touched || formstate.$submitted) && !subObj.renovationTime">请选择装修时间</span>-->
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">*房屋地址</label>
                        <div class="row">
                            <div class="col-xs-4" :class="[(formstate.$touched || formstate.$submitted) && !subObj.provinceCode ? 'has-error' : '', '']">
                              <validate>
                                <select class="form-control provinceCode" name="provinceCode" v-model="subObj.provinceCode" required @change="subObj.cityCode='';subObj.areaCode=''">
                                      <option value="">省份</option>
                                      <option :value="item.ProID" v-for="(item, $index) of province" :key="$index">{{item.ProName}}</option>
                                  </select>
                                <field-messages name="provinceCode">
                                  <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择省</span>
                                </field-messages>
                              </validate>
                            </div>
                            <div class="col-xs-4" :class="[(formstate.$touched || formstate.$submitted) && !subObj.cityCode ? 'has-error' : '', '']">

                                <validate>
                                <select name="cityCode" class="form-control cityCode" v-model="subObj.cityCode" required @change="subObj.areaCode=''">
                                    <option value="">市</option>
                                    <option :value="item.CityID" v-for="(item, $index) of city" :key="$index" v-if="item.ProID == subObj.provinceCode">{{item.CityName}}</option>
                                </select>
                                <field-messages name="cityCode">
                                  <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择市</span>
                                </field-messages>
                              </validate>
                            </div>
                            <div class="col-xs-4" :class="[(formstate.$touched || formstate.$submitted) && !subObj.areaCode ? 'has-error' : '']">
                                <validate>
                                <select name="areaCode" class="form-control areaCode" v-model="subObj.areaCode" required>
                                    <option value="">区</option>
                                    <option :value="item.Id" v-for="(item, $index) of district" :key="$index" v-if="item.CityID == subObj.cityCode">{{item.DisName}}</option>
                                </select>
                                <field-messages name="areaCode">
                                  <span class="help-block" v-if="formstate.$touched || formstate.$submitted" slot="required">请选择区</span>
                                </field-messages>
                              </validate>


                                <!--<select class="form-control" v-model="subObj.areaCode">
                                    <option value="">区</option>
                                    <option :value="item.Id" v-for="(item, $index) of district" :key="$index" v-if="item.CityID === subObj.cityCode">{{item.DisName}}</option>
                                </select>
                                 <span class="help-block" v-if="(formstate.$touched || formstate.$submitted) && !subObj.provinceCode">请选择区</span>-->
                            </div>
                        </div>
                        <div :class="[(formstate.$touched || formstate.$submitted) && !subObj.address ? 'has-error' : '']">

                          <validate>
                              <input style="margin-top:10px;" name="address" type="text" class="form-control" id="exampleInputPassword1" v-model="subObj.address" required placeholder="请输入详细地址">
                              <field-messages name="address">
                                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入详细地址</span>
                              </field-messages>
                            </validate>
                          <!--<input style="margin-top:10px;" type="text" class="form-control" id="exampleInputPassword1" v-model="subObj.address" placeholder="请输入详细地址">
                          <span class="help-block" v-if="(formstate.$touched || formstate.$submitted) && !subObj.provinceCode">请输入详细地址</span>-->
                        </div>

                    </div>
                    <button type="submit" class="btn btn-default">保存</button>
                </vue-form>
            </div>
        </div>
        <Loading v-if="loading"></Loading>
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>

        </Modal>
        <!--<div class="loading">
            <p><img src="../../../static/img/loading.gif"><br>正在加载，请稍后...</p>
        </div>-->
    </div>
</template>
<script>
// 引入省市县
import axios from 'axios'
import VueForm from 'vue-form'
import Loading from '../Loading'
import Modal from '../Modal'
import moment from 'moment'
import { PopupDatePicker } from 'rocoui'
import {province, city, district} from '../../../static/city.js'
import $ from 'jquery'
import { dateYYYYMMDDHHMMFormat } from '../../utils/date.js'
let options = {
  validators: {
    // 正整数不包括0
    'custom-integer': function (value, attrValue, vnode) {
      // return true to set input as $valid, false to set as $invalid
      return /^[0-9]*[1-9][0-9]*$/.test(value)
    },
    // 非负整数
    'non-negative-integer': function (value, attrValue, vnode) {
      // return true to set input as $valid, false to set as $invalid
      return /^[0-9]*[1-9][0-9]*$/.test(value) || (value === '0')
    },
    // 两位小数
    'two-decimal': function (value){
      return /^\d{0,8}\.{0,1}(\d{1,2})?$/.test(value)
    }
  }
}
export default {
  name: 'edit-house',
  data () {
    return {
      minDate: moment(),
      popDate: moment(),
      dateVisible: false,
      loading: false,
      modal: false, // 控制modal
      msg: '',   // 错误信息
      formstate: {},
      // 室厅卫
      room: null,
      hall: null,
      toilet: null,
      subObj: {
        customerNo: '',
        taskNo: '',
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
        renovationTime: moment().format('YYYY-MM-DD HH:mm'),
        // 省市县
        provinceCode: '',
        provinceName: null,
        cityCode: '',
        cityName: null,
        areaCode: '',
        areaName: null,
        // 房屋地址
        address: ''
      },
      arr: ['供应商A', '供应商B'],
      arr2: ['供应商A123', '供应商B123'],
      sel: '',
      province,
      city,
      district
    }
  },
  components: {
    Loading,
    Modal,
    PopupDatePicker
  },
  mixins: [new VueForm(options)],
  methods: {
    // 提交
    onSubmit () {
      if(this.formstate.$invalid || !(this.subObj.houseType || this.subObj.houseType === 0) || !(this.subObj.hoursing || this.subObj.hoursing === 0) || !(this.subObj.hoursing || this.subObj.hoursing === 0)){
        return false
      }

      if (this.loading) return false

      this.loading = true
      this.subObj.houseLayout = this.room + '室' + this.hall + '厅' + this.toilet + '卫'

      // 设置省市县text
      let provinceCode = document.querySelector('.provinceCode')
      let cityCode = document.querySelector('.cityCode')
      let areaCode = document.querySelector('.areaCode')
      this.subObj.provinceName = provinceCode.options[provinceCode.selectedIndex].text
      this.subObj.cityName = cityCode.options[cityCode.selectedIndex].text
      this.subObj.areaName = areaCode.options[areaCode.selectedIndex].text
      axios.post('/api/customer/house', this.subObj).then((res) => {
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
    // datetimepicker初始化
    activeDatetimepicker() {
      let self = this
      $(self.$refs.renovationTime).datetimepicker({
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        startDate: dateYYYYMMDDHHMMFormat(new Date())
      }).on('changeDate', function (e) {
        self.subObj.renovationTime = dateYYYYMMDDHHMMFormat(e.date)
      })
    },
    // 判断是创建还是编辑
    fetchData () {
      if(!this.isCreate){
        var self = this
        axios.get('/api/customer/housedetail?id=' + this.query.houseId).then((res) => {
          if(res.data.code === '1'){
            self.subObj = res.data.data
          }
        })
      }else{
        this.subObj.customerNo = this.query.customerNo
        this.subObj.taskNo = this.query.id
      }
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {
    // this.$nextTick(_ => {
    //   this.activeDatetimepicker()
    // })
  },
  computed: {
    isCreate (){
      return this.$route.params.id === 'create'
    },
    query () {
      return this.$route.query
    }
  },
  watch: {
    'subObj.houseLayout': function(val){
      if(val){
        let newVal = val.replace(/[^0-9]/ig, '-')
        let arr = newVal.split('-')
        this.room = arr[0]
        this.hall = arr[1]
        this.toilet = arr[2]
      }else{
        return false
      }
    },
    popDate (val) {
      this.subObj.renovationTime = moment(val).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>
