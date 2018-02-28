<template>
    <div class="wrap">
        <div class="content">
            <div class="container-fluid search">
                <div class="row">
                  <button @click="newLabel" type="button" class="btn btn-default border-none add2" aria-label="Left Align" style="height:30px;padding:0">
                      <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    </button>
                    <!--<i class="add2" @click="newLabel"></i>-->
                    <div class="col-xs-11 col-xs-offset-1">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="标签名/创建者姓名" v-model="keyword">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button" @click="search">搜索</button>
                            </span>
                        </div><!-- /input-group -->
                    </div><!-- /.col-lg-6 -->
                </div>
            </div>
            <div class="container-fluid lebal-content">
                <form>
                    <div class="form-group">
                        <div class="checkbox lebal-text" v-for="list in labelList">
                            <label class="lebal-tag">
                                <input type="radio" name="orderLabel" @click="set(list.tagId, list.createUserId)" :checked="tag == list.tagId">{{list.tagName}}
                            </label>
                            <span class="lebal-name">{{list.createUserName}}</span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <Loading :show="loading"></Loading>
        <Modal :show.sync="modal">
          <div class="text-center" v-text="msg"></div>
        </Modal>
    </div>
</template>

<script>
import axios from 'axios'
import Loading from '../Loading'
import Modal from '../Modal'

export default {
  name: 'customerDetail',
  data (){
    return {
      modal: false,
      msg: '',
      loading: false,
      keyword: '',
      labelList: [], // 列表数据
      tag: ''
    }
  },
  components: {
    Loading,
    Modal
  },
  methods: {
    fetchDate() {
      this.loading = true
      axios.get('/api/order/ordTag/list?keyword=' + this.keyword).then((res) => {
        if(res.data.code === '1'){
          this.labelList = res.data.data
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    search () {
      this.fetchDate()
    },
    set (tagId, createUserId) {
      this.loading = true
      console.log(this.$router.currentRoute.query.id)
      axios.get('/api/order/updateOrdWithTag?tagId=' + tagId + '&addTagUserId=' + createUserId + '&orderId=' + this.$router.currentRoute.query.id).then((res) => {
        if(res.data.code === '1'){
          setTimeout(_ => {
            this.$router.go(-1)
          }, 1500)
        }
        this.modal = true
        this.msg = res.data.message
      }).finally(_ => {
        this.loading = false
      })
    },
    newLabel () {
      this.$router.push({
        path: '/newLabel'
      })
    }
  },
  computed: {
    query (){
      return this.$router.query
    }
  },
  created(){
    this.fetchDate()
    this.tag = this.$router.currentRoute.query.tag
  }
}
</script>
