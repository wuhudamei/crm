<template>
    <div class="wrap">
        <div class="content">
            <div class="container">
                <vue-form class="nc-form" :state="formstate" @submit.prevent="sub">
                    <div class="form-group" :class="[(formstate.touched || formstate.$submitted) && formstate.name.$invalid ? 'has-error' : '']">
                        <label for="exampleInputPassword1">*标签名称</label>
                        <validate>
                        <input type="text" class="form-control" id="exampleInputPassword1" required placeholder="请输入标签名称" name="name" v-model="name">
                        <field-messages name="name">
                            <span class="help-block" v-if="(formstate.$touched || formstate.$submitted)" slot="required">请输入标签名称</span>
                          </field-messages>
                        </validate>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1" >标签简介</label>

                        <textarea class="form-control" placeholder="请输入标签简介" v-model="tagContent">
                        </textarea>
                    </div>
                    <button type="submit" class="btn btn-default">保存</button>
                </vue-form>
            </div>
        </div>
        <!--<Modal :show="modal">{{msg}}</Modal>-->
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>
        </Modal>
    </div>
</template>


<script>
import axios from 'axios'
import VueForm from 'vue-form'
import Loading from '../Loading'
import Modal from '../Modal'

export default {
  name: 'customerDetail',
  data (){
    return {
      formstate: {},
      name: '',
      tagContent: '',
      modal: false,
      msg: '',
      loading: false
    }
  },
  mixins: [new VueForm()],
  components: {
    Loading,
    Modal
  },
  methods: {
    sub () {
      if(this.formstate.$invalid){
        return
      }
      var self = this
      this.loading = true
      axios.get('/api/order/ordTag/add?tagName=' + this.name + '&tagContent=' + this.tagContent).then((res) => {
        if(res.data.code === '1'){
          setTimeout(() => {
            self.$router.go(-1)
          }, 1500)
        }
        self.modal = true
        self.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    }
  },
  computed: {
    query (){
      return this.$router.query
    }
  },
  created(){
  }
}
</script>
