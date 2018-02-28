<template>
  <!--<div class="wrap">-->

  <!--<div class="container-fluid search">
      <div class="row">
        <div class="col-lg-6">
          <form action="" @submit.prevent.stop="handleSearch">
            <div class="input-group">

              <input type="text" class="form-control"
              v-model="keyWord"
              placeholder="客户姓名/电话/地址"
              @focus="handleFocus"
              >
              <span class="input-group-btn">
                <button class="btn btn-default" type="submit">搜索</button>
              </span>

            </div>
          </form>
        </div>
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
          <router-view>

          </router-view>

      </div>

    </div>
  </div>-->

  <!--<Footer-component @footer-click="clearAll"></Footer-component>-->
  <!--</div>-->
  <router-view></router-view>
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
        if (q) {
          this.$router.push({
            path: '/tab/' + $index,
            query: {
              scrollX: q.scrollX,
              scrollY: q.scrollY
            }
          })
        } else {
          this.$router.push({
            path: '/tab/' + $index
          })
        }

        this.activeTab = $index
      },
      // 搜索列表
      handleSearch() {
        axios.get('/api/customer/query?type=SEARCH&keywork=' + this.keyWord).then((res) => {
          this.listData.splice(this.activeTab, 1, res.data)
        })
      },
      handleFocus () {
        this.$router.push('/search')
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


