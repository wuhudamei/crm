<template>
  <div class="wrap">
    <div class="content">
      <div class="container-fluid">
        <vue-form class="nc-form" :state="formstate" @submit.prevent="sub">
          <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.name.$invalid ? 'has-error' : '']">
            <label for="exampleInputEmail1">*客户姓名</label>
            <validate>
              <input name="name" maxlength="4" type="text" class="form-control" id="exampleInputEmail1" placeholder="请输入姓名" v-model="subObj.customerName" required>
              <field-messages name="name">
                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入姓名</span>
              </field-messages>
            </validate>

          </div>
          <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.telphone.$invalid ? 'has-error' : '']">
            <label for="exampleInputPassword1">*客户电话</label>
            <validate>
              <input name="telphone" type="text" maxlength="11" class="form-control" id="exampleInputPassword1" placeholder="请输入电话" v-model="subObj.customerMobile" custom-tel required>
              <field-messages name="telphone">
                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入电话</span>
                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="custom-tel">请输入正确电话</span>
              </field-messages>
            </validate>
          </div>

          <div class="form-group"
               :class="[(formstate.$touched || formstate.$submitted) && formstate.promoteSource.$invalid ? 'has-error' : '']">
            <label for="promoteSource">*推广来源</label>
            <validate>
              <select class="form-control" name="promoteSource" id="promoteSource" v-model="subObj.promoteSource" required>
                <option value="">请选择推广来源</option>
                <option :value="source.code" v-for="source of sources">{{source.name}}</option>
              </select>
              <field-messages name="promoteSource">
                <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择推广来源</span>
              </field-messages>
            </validate>
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">客户级别</label>
            <br>
            <div class="btn-box">
              <button type="button"
                      class="btn btn-default"
                      :class="{'btn-success':subObj.taskLevel === item.code}"
                      @click="subObj.taskLevel=item.code"
                      v-for="(item, index) of levels"
                      :key="item.code">{{item.text}}

              </button>
            </div>
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">客户标签</label>
            <select class="form-control" name="" id="" v-model="subObj.taskTag">
              <option value="">请选择</option>
              <option :value="item.code" v-for="item of selectDate">{{item.text}}</option>
            </select>
          </div>

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
  import VueForm from 'vue-form'
  import Loading from './Loading.vue'
  import qs from 'qs'
  import Modal from '../components/Modal'
  let options = {
    validators: {
      'custom-tel': function (value) {
        return /^1[3|4|5|7|8]\d{9}$/.test(value)
      }
    }
  }
  export default {
    name: 'add-task',
    data (){
      return {
        dateVisible: false,
        formstate: {},
        modal: false,
        msg: '',
        subObj: {
          // 姓名
          customerName: null,
          // 手机
          customerMobile: null,
          // 跟单员
          mechandiser: '',
          // 邀约
          newTaskType: 'INVITATION',
          // 推广来源
          promoteSource: '',
          // 客户标签
          taskTag: '',
          dataSource: 'MDQT',
          showHouseFlag: false,
          taskLevel: ''
        },
        loading: false,
        // 客户级别集合
        levels: [],
        // 客户标签集合
        selectDate: null,
        // 推广来源集合
        sources: null
      }
    },
    mixins: [new VueForm(options)],
    components: {
      Loading,
      Modal,
      GoBack
    },
    methods: {
      // 获取推广来源
      fetchSource (){
        return axios.get('/api/dict/findSubDictListByCode?code=TGLY')
      },
      // 获取客户级别
      fetchLevels (){
        return axios.get('/api/dict/findChildrenByCode?code=KHJB')
      },
      // 客户标签数据
      fetchSelectDate () {
        return axios.get('/api/dict/findChildrenByCode?code=KHBQ')
      },
      // 获取当前人的jubNum
      fetchJobNum () {
        return axios.get('/api/backTask/getJobNum')
      },
      fetchAll () {
        this.submitting = true
        axios.all([this.fetchLevels(), this.fetchSelectDate(), this.fetchSource(), this.fetchJobNum()]).then(axios.spread((level, data, source, use) => {
          if (level.data.code === '1') {
            this.levels = level.data.data
          }
          if (data.data.code === '1') {
            this.selectDate = data.data.data
          }
          if (source.data.code === '1') {
            this.sources = source.data.data
          }
          if (use.data.code === '1') {
            this.subObj.mechandiser = use.data.data
          }
        })).finally(_ => {
          this.submitting = false
        })
      },
      sub() {
        var self = this
        if (this.formstate.$invalid) {
          return false
        }
        self.loading = true
        if (self.subObj.taskLevel === ''){
          self.subObj.taskLevel = null
        }
        axios.post('/api/backTask/addTask', qs.stringify(self.subObj), {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).then(function (res) {
          if (res.data.code === '1') {
            setTimeout(function () {
              self.$router.go(-1)
            }, 1500)
          }
          self.modal = true
          self.msg = res.data.message
        }).finally(() => {
          this.loading = false
        })
      }
    },
    created(){
      document.title = '新建任务'
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
      this.fetchAll()
    }
  }
</script>
