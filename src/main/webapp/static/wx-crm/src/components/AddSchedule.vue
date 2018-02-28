<template>
    <div class="wrap">
        <!--<header>
            <h3>新增日程</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid">
                 <vue-form class="nc-form" :state="formstate" @submit.prevent="sub">
                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.title.$invalid ? 'has-error' : '']">
                        <label for="exampleInputEmail1">*标题</label>
                        <validate>
                          <input name="title" maxlength="20" type="text" class="form-control" id="exampleInputEmail1" placeholder="请输入日程标题" v-model="subData.title" required>
                          <field-messages name="title">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入日程标题</span>
                          </field-messages>
                        </validate>

                    </div>
                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.content.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*内容</label>
                        <validate>
                          <textarea name="content" type="text" maxlength="250" class="form-control" id="exampleInputPassword1" placeholder="请输入内容" v-model="subData.content" required>
                          </textarea>
                          <field-messages name="content">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入日程内容</span>
                          </field-messages>
                        </validate>
                    </div>

                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.scheduleType.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*日程类型</label>
                        <validate>
                          <select class="form-control" name="scheduleType" id="" v-model="subData.scheduleType" required>
                            <option value="">请选择</option>
                            <option value="INVITATIONSTORE">邀约到店</option>
                            <option value="ROOMRESERVATIONS">预约量房</option>
                            <option value="OTHER">其他</option>
                          </select>
                          <field-messages name="scheduleType">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择日程类型</span>
                          </field-messages>
                        </validate>
                    </div>

                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && subData.scheduleTime === '' ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*日程时间</label>
                        <input ref="planMeasureTime" readonly id="scheduleTime" name="scheduleTime" v-model="subData.scheduleTime" @click="dateVisible=true" type="text" class="form-control">
                        <PopupDatePicker 
                        v-model="popDate" 
                        :visible.sync="dateVisible" 
                        :minDate="minDate" 
                        mode="DATETIME" 
                        title="选择时间">
                        </PopupDatePicker>
                        <!--<validate>
                          <input ref="scheduleTime" readonly id="scheduleTime" name="scheduleTime" v-model="subData.scheduleTime" type="text" class="form-control datepicker" required>
                          <field-messages name="scheduleTime">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择日程时间</span>
                          </field-messages>
                        </validate>-->
                    </div>
                    <!--<div class="form-group">
                        <label for="exampleInputPassword1">备注</label>-->
                        <!--<validate>
                          <textarea class="form-control" v-model="remark" name="remark" required maxlength="200"></textarea>
                          <field-messages name="remark">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入备注</span>
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">最多200字</span>
                          </field-messages>
                        </validate>-->
                        <!--<textarea class="form-control" v-model="remark" name="remark" maxlength="200"></textarea>

                    </div>-->
                    <button :disabled="loading" type="submit" class="btn btn-default">提交</button>
                </vue-form>
            </div>
        </div>
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>
        </Modal>
        <Loading :show="loading"></Loading>
        <go-back></go-back>
    </div>
</template>

<script>
import axios from 'axios'
import GoBack from './GoBack.vue'
import moment from 'moment'
import { PopupDatePicker } from 'rocoui'
// import VueForm from 'vue-form'
import Loading from './Loading.vue'
import $ from 'jquery'
import { dateYYYYMMDDHHMMFormat } from '../utils/date.js'
import Modal from '../components/Modal'
export default {
  name: 'add-schedule',
  data (){
    return {
       // datetimepicker用
      minDate: moment(),
      popDate: moment(),
      dateVisible: false,
      formstate: {},
      modal: false,
      msg: '',
      subData: {
        title: '',   // 标题
        content: '', // 内容
        scheduleType: '',  // 日程类型
        scheduleTime: moment().format('YYYY-MM-DD HH:mm')  // 时间
      },
      loading: false
    }
  },
  // mixins: [new VueForm()],
  components: {
    Loading,
    Modal,
    GoBack,
    PopupDatePicker
  },
  methods: {
    sub() {
      var self = this
      if(this.formstate.$invalid){
        return false
      }
      self.loading = true
      axios.post('/api/schedule/add', self.subData).then(function (res) {
        if (res.data.code === '1') {
          setTimeout(function(){
            window.location.href = 'calendar.html'
          }, 1500)
        }
        self.modal = true
        self.msg = res.data.message
      }).finally(() => {
        this.loading = false
      })
    },
    activeDatetimepicker() {
      let self = this
      $(self.$refs.scheduleTime).datetimepicker({
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        startDate: dateYYYYMMDDHHMMFormat(new Date())
      }).on('changeDate', function (e) {
        self.subData.scheduleTime = dateYYYYMMDDHHMMFormat(e.date)
      })
    }
  },
  created(){
    document.title = '新建日程'
    if (/ip(hone|od|ad)/i.test(navigator.userAgent)) {
      var i = document.createElement('iframe')
      i.src = '/favicon.ico'
      i.style.display = 'none'
      i.onload = function () {
        setTimeout(function () {
          i.remove()
        }, 9)
      }
      document.body.appendChild(i)
    }
  },
  mounted() {
    // this.$nextTick(function () {
    //   this.activeDatetimepicker()
    // })
  },
  watch: {
    popDate (val) {
      this.subData.scheduleTime = moment(val).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>
