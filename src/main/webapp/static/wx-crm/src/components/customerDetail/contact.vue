<template>
<div class="container cd-content scroller-wrapper">
        <div class="cd-main scroller-content">
            <!--<scroller :on-refresh="refresh">-->
                <div class="list-group-item list-commounication list-concat" v-for="(item, $index) of listData" :key="$index">
                   <span>{{item.name}}</span>
                   <span class="text-center">{{item.mobile}}</span>
                   <span>{{item.relationship || '' | contact-filter}}</span>
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
        <loading v-if="loading"></loading>
    </div>




<!--
  <div class="list-group mine-list">
    <ul class="list-group-item list-concat">
        <li v-for="(item, index) of listData" :key="index">
          <span>{{item.name}}</span>
          <span>{{item.mobile}}</span>
          <span>{{item.relationship || '' | contact-filter}}</span>
        </li>
        <li class="text-center" style="border:none" v-if="listData.length === 0">暂无数据</li>
      </ul>
      <loading v-if="loading"></loading>
</div>-->
</template>
<script>
import axios from 'axios'
import Loading from '../Loading'
export default {
  name: 'contact',
  data () {
    return {
      loading: false,
      listData: []
    }
  },
  components: {
    Loading
  },
  methods: {
    fetchData () {
      this.loading = true
      axios.get('/api/contacts/list?customerNo=' + this.query.customerNo).then((res) => {
        if(res.data.code === '1'){
          this.listData = res.data.data.rows
        }
      }).finally(_ => {
        this.loading = false
      })
    }
  },
  computed: {
    query () {
      return this.$route.query
    }
  },
  created () {
    this.fetchData()
  }
}
</script>
