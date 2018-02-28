<template>
<div class="wrap">
        <!--<header>
            日程详情</h3>
        </header>-->
        <div class="content">
            <div class="container-fluid sd-content">
                <h4>{{listData.title}}</h4>
                <div class="cd-text">
                    <p>{{listData.content}}</p>
                </div>
            </div>
        </div>
        <go-back></go-back>
    </div>
</template>
<script>
import axios from 'axios'
import Loading from './Loading.vue'
import GoBack from '../components/GoBack.vue'
export default {
  name: 'schedule-detail',
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
    // 获取详情数据
    fetchData (){
      this.loading = true
      axios.get('/api/schedule/' + this.$route.params.id).then((res) => {
        if(res.data.code === '1'){
          this.listData = res.data.data
        }
      }).finally(_ => {
        this.loading = false
      })
    }
  },
  computed: {
    query (){
      return this.$route.query
    }
  }
}
</script>
