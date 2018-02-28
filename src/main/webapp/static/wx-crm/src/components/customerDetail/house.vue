<template>
    <div class="container cd-content">
        <div class="cd-main" v-for="(item, $index) of listData" :key="$index">
            <dl class="home-title">
                <dt><img src="../../../static/img/home.png"></dt>
                <dd>
                    <h5 v-if="item.orderNum">{{item.orderNum}}</h5>
                    <p>{{item.renovationTime}}</p>
                </dd>
            </dl>
            <p v-if="item.orderNum"  class="home-state">已生成订单</p>
            <span v-if="!item.orderNum && item.houseArea && item.address && !(item.room==='0'&&item.hall==='0'&&item.toilet==='0')"  @click="handleOrder(item.orderNum, item.id)" class="order-btn">生成订单</span>
            <form class="form-horizontal cd-con">

                <div class="form-inline">
                    <label class="col-xs-4 text-normal">房屋户型：</label>
                    <p class="col-xs-8">{{item.houseLayout}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">房屋面积：</label>
                    <p class="col-xs-8">{{item.houseArea}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">房屋类型：</label>
                    <p class="col-xs-8">{{item.houseType === '1' ? '新房' : '旧房'}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">是否期房：</label>
                    <p class="col-xs-8">{{item.hoursing === '1' ? '期房' : '非期房'}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">有无电梯：</label>
                    <p class="col-xs-8">{{item.elevator === '1' ? '有' : '无'}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">装修时间：</label>
                    <p class="col-xs-8">{{item.renovationTime}}</p>
                </div>
                <div class="form-inline">
                    <label class="col-xs-4 text-normal">房屋地址：</label>
                    <p class="col-xs-8">{{item.provinceName + item.cityName + item.areaName + item.address}}</p>
                </div>
                 <div class="btn-box" v-if="!item.orderNum">
                    <button @click="handleEdit(item.id)" type="button" class="btn btn-success">编辑信息</button>
                    <button @click="handleDel(item.id, $index)" type="button" class="btn btn-success">删除信息</button>
                </div>
            </form>
        </div>
        <div v-if="listData.length === 0" class="text-blank">暂无数据</div>
        <div class="row">
          <div class="col-xs-12 cd-con">
            <button @click="handleCreate" type="button" class="btn btn-success  btn-block">新增房产</button>
          </div>
        </div>
          <Modal :show.sync="modal" :auto="auto">
          <div class="text-center" style="padding:20px 0">{{msg}}</div>
          <div class="modal-footer" style="padding-bottom:0">
            <button v-show="btnShow" @click="cancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-show="btnShow" @click="confirm" type="button" class="btn btn-primary bg578f6e">确定删除</button>
           </div>
        </Modal>
    </div>
</template>


<script>
import axios from 'axios'
import Modal from '../Modal.vue'
export default {
  name: 'customerDetail',
  data (){
    return {
      modal: false,   // 显示弹窗
      msg: '',    // 弹窗文字
      btnShow: false,  // 显示按钮
      auto: true,   // 自动隐藏
      selectNo: '', //
      selectIndex: null,
      listData: [],
      customerInfo: null, // 客户信息
      houseInfo: null, // 房屋信息
      orderInfo: null, // 订单信息
      record: null // 跟踪记录
    }
  },
  components: {
    Modal
  },
  methods: {
    fetchDate() {
      this.loading = true
      axios.get('/api/customer/houseListByTaskNo?taskNo=' + this.query.id).then((res) => {
        if(res.data.code === '1'){
          if(res.data.data.length !== 0){
            res.data.data.forEach((val, index, array) => {
              let newVal = val.houseLayout.replace(/[^0-9]/ig, '-')
              let arr = newVal.split('-')
              array[index].room = arr[0]
              array[index].hall = arr[1]
              array[index].toilet = arr[2]
            })
          }
          this.listData = res.data.data
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 编辑房产
    handleEdit (id) {
      this.$router.push({
        path: '/editHouse/edit?id=' + this.query.id + '&customerNo=' + this.query.customerNo + '&houseId=' + id
      })
    },
    // 新增房产
    handleCreate () {
      this.$router.push({
        path: '/editHouse/create?id=' + this.query.id + '&customerNo=' + this.query.customerNo
      })
    },
    // 删除房产
    handleDel (val, $index) {
      this.modal = true   // 显示弹窗
      this.msg = '确认删除吗'    // 弹窗文字
      this.btnShow = true  // 显示按钮
      this.auto = false   // 自动隐藏
      this.selectNo = val
      this.selectIndex = $index
    },
    // 删除房屋确认
    confirm () {
      this.btnShow = false
      axios.post('/api/customer/deletehouse/' + this.selectNo).then((res) => {
        if(res.data.code === '1'){
          this.listData.splice(this.selectIndex, 1)
        }
        this.auto = true
        this.msg = res.data.message
      }).finally(_ => {
      })
    },
    // 取消删除
    cancel () {
      this.modal = false
      this.btnShow = false
      this.auto = true
    },
    // 生成订单
    handleOrder (orderNum, houseId) {
      if(orderNum){
        return
      }
      this.$router.push('/editOrder?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id + '&houseId=' + houseId)
    }
  },
  computed: {
    query () {
      return this.$route.query
    }
  },
  created(){
    this.fetchDate()
  }
}
</script>
