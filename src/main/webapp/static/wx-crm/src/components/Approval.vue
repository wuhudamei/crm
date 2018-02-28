<template>
  <div class="wrap">
    <!--<header>
      <h3>
        待审批列表</h3>
    </header>-->
    <div class="content">
      <ul class="mine-tab">
          <li><a href="calendar.html">日程</a></li>
          <li class="active"><a href="javascript:void(0)">待审批</a></li>
      </ul>
      <div class="container-fluid">
        <div class="list-group" v-for="(item, $index) of listData" :key="$index" @click="handleDetail(item)">
          <a href="javascript:void(0)" class="list-group-item">
            <h4 class="list-group-item-heading">申请编号：{{item.applyNo}}</h4>
            <p class="list-group-item-text">申请标题：{{item.applyTitle}}</p>
            <p class="list-group-item-text">申请时间：{{item.applyTime}}</p>
            <p class="list-group-item-text">申请人：{{item.applyUserName}}</p>
          </a>
        </div>

      </div>
    </div>
    <Footer-component :active="1"></Footer-component>
    <Loading v-show="loading"></Loading>
  </div>
</template>
<script>
import axios from 'axios'
import FooterComponent from '../components/Footer'
import Loading from '../components/Loading.vue'
export default {
  name: 'approval-list',
  data() {
    return {
      loading: false,
      listData: []
    }
  },
  created () {
  },
  mounted () {
    this.fetchList()
  },
  components: {
    Loading,
    FooterComponent
  },
  methods: {
    // 获取列表
    fetchList () {
      this.loading = true
      axios.get('/api/schedule/applylist').then((res) => {
        if(res.data.code === '1'){
          this.listData = res.data.data
        }
      }).finally(_ => {
        this.loading = false
      })
    },
    // 跳转详情
    handleDetail (item) {
      this.$router.push('/detail?type=' + item.type + '&applyNo=' + item.applyNo)
    }
  }
}
</script>
