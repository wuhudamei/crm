<template>
  <div class="wrap">
    <Header-component>我的客户</Header-component>
    <div class="container-fluid search">
        <div class="row">
          <div class="col-lg-6">
            <form action="" @submit.prevent.stop="handleSearch">
              <div class="input-group">

                <input type="text" class="form-control"
                placeholder="客户姓名/电话/地址"
                @focus="searchStatus=true"
                @blur="handleBlur">
                <span class="input-group-btn">
                  <button class="btn btn-default" type="submit">搜索</button>
                </span>

              </div>
            </form>
            <!-- /input-group -->
          </div>
          <!-- /.col-lg-6 -->
        </div>
        <ul class="mine-tab" v-show="!searchStatus">
            <li @click="fetchList($index)"
              v-for="(item, $index) of tabArr"
              :key=$index :class="[$index === activeTab ? 'active' : '']">
              <a href="javascript:void(0)">{{item}}</a>
            </li>
          </ul>
    </div>
    <div class="content">
      <div class="container-fluid search">

        <div class="" v-show="!searchStatus">

            <!--<keep-alive  v-for="(item, $index) in listData" :key=$index>
              <tab-list :key=$index :list="item" v-if="$index === activeTab"></tab-list>
            </keep-alive>-->
            <router-view>

            </router-view>

          <!--<div class="list-group mine-list" v-for="(item, $index) of listData">
            <ul class="list-group-item">
              <li v-for="(ite, index) of item">
                <h4 class="list-group-item-heading">张三
                  <small>待邀约</small>
                  <span>13:38</span>
                </h4>
                <p class="list-group-item-text ">
                  <i class="add"></i>已预约下周进店
                  <span>
                    <i class="phone"></i>打给Ta</span>
                </p>
              </li>
            </ul>
          </div>-->
        </div>

      </div>
    </div>
    <Footer-component @footer-click="clearAll"></Footer-component>
  </div>
</template>
<script>
import store from 'store'
import axios from 'axios'
import FooterComponent from '../components/Footer'
import HeaderComponent from '../components/Header'
import TabList from '../components/TabList'
export default {
  name: 'appindex',
  data() {
    return {
      tabs: {},  // tab类型
      activeTab: 0,   // 激活的tab
      searchList: [],   // 搜索列表
      limit: 10,   // 每次加载数量
      keyWord: '',  // 搜索关键词
      searchStatus: false,   // 是否搜索状态
      tabArr: ['新客户', '沟通中', '订单', '其他', '最新联系']
    }
  },
  components: {
    FooterComponent,
    HeaderComponent,
    TabList
  },
  methods: {
    // 获取列表详情
    fetchList($index) {
      let q = store.get('indextab' + $index)
      if(q){
        this.$router.push({
          path: '/tab/' + $index,
          query: {
            scrollX: q.scrollX,
            scrollY: q.scrollY
          }
        })
      }else{
        this.$router.push({
          path: '/tab/' + $index
        })
      }

      this.activeTab = $index
    },
    // 搜索列表
    handleSearch() {
      axios.get('/api/' + this.keyWord).then((res) => {
        this.listData.splice(this.activeTab, 1, res.data)
      })
    },
    handleBlur () {
      this.searchStatus = false
      this.searchList = []
    },
    clearAll () {
      store.clearAll()
    }
  },
  created() {
    this.fetchList(this.activeTab)   // 默认显示第一页
  }
}
</script>


