<template>
  <div class='wrap' v-cloak>
    <!--<Header-component>{{title}}</Header-component>-->
    <div class='content'>
      <div class='container-fluid'>
        <!--<div class='thumbnail mine-person'>
                    <img class='avatar' width='94' height='94'>-->
        <!--<div class='caption'>
                        <h5>[ 客服经理 : {{personInfo.name}} ] </h5>
                        <h5>[ 邀约码：{{personInfo.invitationCode}} ]</h5>
                    </div>-->
        <!--</div>-->
        <!--/.mine-person-->
        <div class='cd-main'>
          <h4>当日统计</h4>
          <form class='form-horizontal cd-con'>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>待邀约数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.toBeInvited}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>已邀约数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.hasBeenInvited}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>接待客户数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.dayReceptionCustomers}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal pr0'>大定数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.bigSet}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>小定数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.smallSet}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>退单数：</label>
              <p class='col-xs-8 text-bold'>{{details.dayData.newClosedOrdNum}}</p>
            </div>
          </form>
        </div>
        <!--/.cd-main-->
        <div class='cd-main'>
          <h4>当月统计</h4>
          <form class='form-horizontal cd-con'>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>接待客户数：</label>
              <p class='col-xs-8 text-bold'>{{details.monthData.monthReceptionCustomers}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal pr0'>大定数：</label>
              <p class='col-xs-8 text-bold'>{{details.monthData.bigSet}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>小定数：</label>
              <p class='col-xs-8 text-bold'>{{details.monthData.smallSet}}</p>
            </div>
            <div class='form-inline'>
              <label class='col-xs-4 text-normal'>退单数：</label>
              <p class='col-xs-8 text-bold'>{{details.monthData.newClosedOrdNum}}</p>
            </div>
          </form>
        </div>
        <!--/.cd-main-->
        <!--<div class='bg'>
                  <ul class='mine-tab' style='margin:10px 5px'>
                      <li @click="echart($index)" :class="[selectedIndex === $index ? 'active' : '']" v-for="(item, $index) of tabs" :key="$index">
                        <a href='javascript:;'>{{item}}</a>
                      </li>
                  </ul>
                  <div id='main' style='width: 100%;height:400px;background-color:#fff'></div>
                </div>
                <div class='map-title'>近三十日业绩图趋势图</div>-->
      </div>
      <!--/.container-fluid-->
    </div>
    <!--/.content-->
    <Loading :show="loading"></Loading>
    <Footer-component :active='2'></Footer-component>
  </div>
</template>

<script>
import axios from 'axios'
import Loading from '../components/Loading'
import FooterComponent from '../components/Footer'
import HeaderComponent from '../components/Header'
import echarts from 'echarts'
export default {
  name: 'my',
  data() {
    return {
      title: '',
      loading: false,
      personInfo: {},        // 我的信息
      dayStatistics: {},     // 当日统计
      monthStatistics: {},    // 当月统计
      details: {
        employee: {},
        dayData: {
          bigSet: 0,
          smallSet: 0,
          hasBeenInvited: 0,
          recMoney: null,
          backMoney: null,
          toBeInvited: 0
        },
        monthData: {
          bigSet: 0,
          smallSet: 0,
          hasBeenInvited: 0,
          recMone: null,
          backMoney: null,
          toBeInvited: 0
        },
        tabs: [],   // legend
        X: [],  // xAxis
        Y: []
      },
      tabs: ['客户数', '订单数', '收款数'],
      selectedIndex: 0
    }
  },
  components: {
    FooterComponent,
    HeaderComponent,
    Loading
  },
  created() {
    this.fetchAll()
    // this.fetchData()
  },
  mounted() {
    // this.$nextTick(function () {
    //   this.echart()
    // })
  },
  methods: {
    fetchData() {
      let self = this
      this.loading = true
      axios.get('/api/employee/getInfo').then(function (res) {
        if (res.data.code === '1') {
          self.personInfo = res.data.personInfo
          self.dayStatistics = res.data.dayStatistics
          self.monthStatistics = res.data.monthStatistics
          self.details = res.data.data
          self.title = (self.details.employee.position || '') + self.details.employee.empName
          self.details.Y[3].data.forEach((val, index, arr) => {
            if (Object.prototype.toString.call(val) === '[object Null]') {
              arr[index] = 0
            }
          })
          self.$nextTick(function () {
            self.echart(0)
          })
        }
      }).finally(_ => {
        self.loading = false
      })
    },
    echart(tab) {
      console.log(tab)
      this.selectedIndex = tab
      var myChart = echarts.init(document.getElementById('main'))
      myChart.clear()
      // 指定图表的配置项和数据
      var option = {
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          x: 70
        },
        legend: {},
        xAxis: { data: this.details.X },
        yAxis: {},
        series: {}
      }
      if (tab === 0) {
        option.legend.data = new Array(this.details.tabs[0])
        option.series = new Array(this.details.Y[0])
      } else if (tab === 1) {
        option.legend.data = [this.details.tabs[1], this.details.tabs[2]]
        option.series = [this.details.Y[1], this.details.Y[2]]
      } else {
        option.legend.data = new Array(this.details.tabs[3])
        option.series = new Array(this.details.Y[3])
      }

      // var option = {
      //   // title: {
      //   //   text: '近三十日业绩趋势图',
      //   //   textStyle: {
      //   //     fontWeight: 'normal',
      //   //     fontSizeL: 12
      //   //   }
      //   // },
      //   tooltip: {
      //     trigger: 'axis'
      //   },
      //   legend: {
      //     data: ['客户数', '大定数', '小定数', '收款数']
      //   },
      //   xAxis: {
      //     data: ['衬衫1', '羊毛衫1', '雪纺衫1', '裤子1', '高跟鞋1', '袜子1', '衬衫2', '羊毛衫2', '雪纺衫2', '裤子2', '高跟鞋2', '袜子2']
      //   },
      //   yAxis: {},
      //   series: [
      //     {
      //       name: '客户数',
      //       type: 'line',
      //       data: [1, 25, 36, 10, 10, 20, 1, 25, 36, 10, 10, 20]
      //     },
      //     {
      //       name: '大定数',
      //       type: 'line',
      //       data: [2, 28, 36, 10, 10, 20, 1, 25, 36, 10, 10, 20]
      //     },
      //     {
      //       name: '小定数',
      //       type: 'line',
      //       data: [3, 20, 36, 10, 10, 20, 1, 25, 36, 10, 10, 20]
      //     },
      //     {
      //       name: '收款数',
      //       type: 'line',
      //       data: [40000, 150000, 360000, 100000, 100000, 200000, 10000, 250000, 360000, 100000, 100000, 200000]
      //     }]
      // }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
    fetchDay() {
      return axios.get('/api/employee/getDayInformation')
    },
    fetchMonth() {
      return axios.get('/api/employee/getMonthInformation')
    },
    fetchChart() {
      return axios.get('/api/employee/getLInformation')
    },
    fetchAll() {
      this.loading = false
      axios.all([this.fetchDay(), this.fetchMonth()]).then(axios.spread((day, month) => {
        if (day.data.code === '1') {
          this.details.dayData = day.data.data.dayData
          this.details.employee = day.data.data.employee
          document.title = this.details.employee.empName
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
        }
        if (month.data.code === '1') {
          this.details.monthData = month.data.data.monthData
        }
        // if(chart.data.code === '1'){
        //   this.details.X = chart.data.data.X
        //   this.details.Y = chart.data.data.Y
        //   this.details.tabs = chart.data.data.tabs
        //   this.details.Y[3].data.forEach((val, index, arr) => {
        //     if(Object.prototype.toString.call(val) === '[object Null]'){
        //       arr[index] = 0
        //     }
        //   })
        //   this.$nextTick(function () {
        //     this.echart(0)
        //   })
        // }
      })).finally(_ => {
        this.loading = false
      })
    }
  }
}
</script>

<style>
body {
  color: #555;
}

.map-title {
  margin: 10px auto;
  text-align: center;
  font-size: 18px;
}

.bg {
  background-color: #fff;
  border-radius: 3%;
  padding-top: 20px;
}

.cd-main {
  border: none;
  border-radius: 3%;
  padding: 10px 0 0 10px;
  margin-bottom: 15px;
}

.mine-person {
  border: none;
  border-radius: 4%;
  margin-bottom: 15px;
}

.cd-main .cd-con {
  padding-left: 5px;
  padding-top: 10px
}
</style>
