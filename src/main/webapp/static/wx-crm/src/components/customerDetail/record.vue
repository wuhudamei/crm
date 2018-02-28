<template>
    <div class="container cd-content scroller-wrapper">
        <div class="cd-main scroller-content">
            <!--<scroller :on-refresh="refresh">-->
                <div class="list-group-item list-commounication" v-for="(item, $index) of listData" :key="$index">
                    <h4 class="list-group-item-heading">
                        {{item.communicateType | type-filter}}
                        <span class="time">{{item.createTime}}</span>
                    </h4>
                    <div class="list-group-item-text ">{{item.remark}}</div>
                </div>
                <div v-if="listData.length === 0" class="text-blank">暂无数据</div>
                <!--<div class="list-group-item list-commounication">
                    <h4 class="list-group-item-heading">
                        进店沟通
                        <span class="time">13:38</span>
                    </h4>
                    <div class="list-group-item-text ">已预约下周进店已预约下周进店已预约下周进店已预约下周进店已预约下周进店已预约下周进店已预约下周进店</div>
                </div>-->
                <!--/.list-commounication-->
            <!--</scroller>-->
        </div>
        <!--/.scroller-content-->
        <div class="cd-con btn-group">
            <button type="button" @click="addRecord" class="btn btn-success btn-block">新增沟通记录</button>
        </div>
        <loading v-if="loading"></loading>
    </div>
    <!--/.scroller-wrapper-->
</template>

<script>
import axios from 'axios'
import Loading from '../Loading'
export default {
  name: 'customerrecord',
  data (){
    return {
      loading: false,
      customerInfo: null, // 客户信息
      houseInfo: null, // 房屋信息
      orderInfo: null, // 订单信息
      record: null, // 跟踪记录
      params: null,
      listData: [1],
      levelSecond: []
    }
  },
  components: {
    Loading
  },
  created(){
    this.fetchData()
    this.fetchSelect()
  },
  methods: {
    fetchData () {
      this.loading = true
      axios.get('/api/customer/findByTaskNo?taskNo=' + this.query.id).then((res) => {
        if(res.data.code === '1'){
          this.listData = res.data.data
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    fetchSelect (){
      axios.get('/api/dict/findChildrenByCode?code=GTFS').then((res) => {
        if(res.data.code === '1'){
          res.data.data.forEach((value) => {
            value.children.forEach((val) => {
              val.proId = value.code
              this.levelSecond.push(val)
            })
          })
        }
      })
    },
    refresh () {
      this.fetchData()
    },
    addRecord () {
      window.location.href = 'communicationRecord.html#/?customerNo=' + this.query.customerNo + '&taskNo=' + this.query.id
    }
  },
  computed: {
    query () {
      return this.$route.query
    }
  }
}
</script>

<style lang="less">
    .scroller-wrapper{
        display: flex;
        flex-flow: column;
        height: 100%;
    }
    .scroller-content{
        flex: 1;
    }
    .list-commounication{
        border-width: 0 0 1px 0;
        margin: 0 10px;
        padding-left: 0;
        padding-right: 0;
        &:last-child{
            border: none;
        }
        .list-group-item-heading{
            display: flex;
            justify-content: space-between;
            .time{
                color: #888;
                font-size: 12px;
            }
        }
        .list-group-item-text{
            color: #888;
        }
    }
    .btn-group{
        .btn{
            margin-bottom: 30px;
        }
    }
</style>
