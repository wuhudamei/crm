<template>
    <div class="wrap">
        <!--<header>
            新建联系人</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid">
                 <vue-form class="nc-form" :state="formstate" @submit.prevent="sub">
                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.name.$invalid ? 'has-error' : '']">
                        <label for="exampleInputEmail1">*姓名</label>
                        <validate>
                          <input name="name" maxlength="4" type="text" class="form-control" id="exampleInputEmail1" placeholder="请输入姓名" v-model="name" required>
                          <field-messages name="name">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入姓名</span>
                          </field-messages>
                        </validate>

                    </div>
                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.telphone.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*电话</label>
                        <validate>
                          <input name="telphone" type="text" maxlength="11" class="form-control" id="exampleInputPassword1" placeholder="请输入电话" v-model="mobile" custom-tel required>
                          <field-messages name="telphone">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入电话</span>
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="custom-tel">请输入正确电话</span>
                          </field-messages>
                        </validate>
                    </div>
                    <div class="form-group" :class="[(formstate.$touched || formstate.$submitted) && formstate.relationship.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*关系</label>
                        <validate>
                          <select class="form-control" v-model="relationship" name="relationship" required>
                            <option value="">请选择</option>
                            <option :value="item.code" v-for="item of relations">{{item.text}}</option>
                          </select>
                          <field-messages name="relationship">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请选择关系</span>
                          </field-messages>
                        </validate>

                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">备注</label>
                        <!--<validate>
                          <textarea class="form-control" v-model="remark" name="remark" required maxlength="200"></textarea>
                          <field-messages name="remark">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入备注</span>
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">最多200字</span>
                          </field-messages>
                        </validate>-->
                        <textarea class="form-control" v-model="remark" name="remark" maxlength="200"></textarea>

                    </div>
                    <button :disabled="onOff" type="submit" class="btn btn-default">提交</button>
                    <!--<button type="submit" class="btn btn-default" @click="sub">提交</button>-->
                </vue-form>
            </div>
        </div>
        <Loading :show="onOff"></Loading>
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>

        </Modal>
    </div>
</template>

<script>
import axios from 'axios'
import Modal from '../Modal.vue'
import VueForm from 'vue-form'
import Loading from '../Loading'
import { PopupDatePicker } from 'rocoui'
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
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
      modal: false,
      msg: '',
      formstate: {},
      param: null, // 地址
      name: '', // 姓名
      mobile: null, // 电话
      relationship: '', // 关系
      remark: null, // 备注
      onOff: false, // 防止按钮连击
      /**
     * 客户编号
     */
      customerNo: null,
      relations: []
    }
  },
  mixins: [new VueForm(options)],
  components: {
    Loading,
    Modal,
    PopupDatePicker
  },
  methods: {
    sub() {
      var self = this
      if(this.formstate.$invalid || self.onOff){
        return false
      }
      self.onOff = true
      // var subParams = JSON.stringify({
      //   name: this.name,
      //   mobile: this.mobile,
      //   relationship: this.relationship,
      //   remark: this.remark
      // })
      axios.post('/api/contacts/add', {
        customerNo: this.customerNo,
        name: this.name,
        mobile: this.mobile,
        relationship: this.relationship,
        remark: this.remark
      }).then(function (res) {
        if (res.data.code === '1') {
          setTimeout(function(){
            self.$router.go(-1)
          }, 1000)
        }
        self.modal = true
        self.msg = res.data.message
      }).catch(function(){
        // self.$router.replace('/customer')
        self.onOff = false
      })
    },
    fetchData (){
      axios.get('/api/dict/findChildrenByCode?code=GUAN').then((res) => {
        if(res.data.code === '1'){
          this.relations = res.data.data
        }
      })
    }
  },
  created(){
    this.customerNo = this.query.customerNo
    this.fetchData()
  },
  computed: {
    query (){
      return this.$route.query
    }
  }
}
</script>
