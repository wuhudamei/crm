<template>
<div class="wrap">
        <!--<header>
            日程列表</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid">
                <div class="list-group" :key="$index" v-for="(item, $index) of listData" @click="scheduledetail(item)">
                    <a href="javascript:void(0)" class="list-group-item">
                        <p class="list-group-item-text schedule-text">
                          <span :class="[item.type === '1' ? 'red' : '']" >
                            {{item.type === 1 ? '系统' : '人工'}}
                          </span>
                          {{item.createTime}}
                          <i :class='item.scheduleTime | status'>
                            {{item.scheduleTime | time}}
                          </i> 
                        </p>
                        
                        <p class="list-group-item-text schedule-title">{{item.title}}</p>
                    </a>
                </div>
            </div>
        </div>
        <Loading v-show="loading"></Loading>
        <go-back></go-back>
    </div>
</template>
<script>
import axios from 'axios'
import Loading from './Loading.vue'
import moment from 'moment'
import GoBack from '../components/GoBack.vue'

export default {
  filters: {
    time: function(val){
      var time = moment(val).format()
      var setTime = new Date(time).getTime()
      var nowTime = new Date().getTime()
      return (setTime - nowTime > 0) ? '未过期' : '已过期'
    },
    status: function(val){
      var time = moment(val).format()
      var setTime = new Date(time).getTime()
      var nowTime = new Date().getTime()
      return (setTime - nowTime > 0) ? 'red' : ''
    }
  },
  name: 'schedule-list',
  data () {
    return {
      loading: false,
      listData: []
    }
  },
  components: {
    Loading,
    GoBack
  },
  created () {
    document.title = '日程'
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
  mounted (){
    this.fetchData()
  },
  methods: {
    fetchData () {
      this.loading = true
      axios.get('/api/schedule/searchschedulelist', {
        params: {
          date: this.query.date
        }
      }).then((res) => {
        if (res.data.code === '1') {
          this.listData = res.data.data
        }
      }).finally(() => {
        this.loading = false
      })
    },
    scheduledetail (item) {
      this.$router.push({path: '/sdetail/' + item.id})
    }
  },
  computed: {
    query () {
      return this.$route.query
    }
  }
}
</script>
