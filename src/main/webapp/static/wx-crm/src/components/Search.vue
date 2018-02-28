<template>
  <div class="wrap">
    <!--<Header-component>我的客户</Header-component>-->
    <div class="container-fluid search">
        <div class="row">
          <div class="col-lg-6">
            <form action="" @submit.prevent.stop="handleSearch">
              <div class="input-group">

                <input type="text" class="form-control"
                v-model="keyWord"
                placeholder="客户姓名/电话/地址"
                >
                <span class="input-group-btn">
                  <button class="btn btn-default" type="submit">搜索</button>
                </span>
              </div>
            </form>
            <!-- /input-group -->
          </div>
          <!-- /.col-lg-6 -->
        </div>
    </div>
    <div class="content">
      <div class="container-fluid search">
        <div class="">
            <div class="list-group mine-list">
              <scroller>








                <h3 v-if="listData.NEWCUSTOMER.length !== 0" style="pglyphicon glyphicon-plus adding:10px 15px">新客户</h3>
                <ul class="list-group-item" v-if="listData.NEWCUSTOMER.length !== 0">
                  <li v-for="(item, index) of listData.NEWCUSTOMER" :key="index">
                    <h4 class="list-group-item-heading" @click.self="handleCustomer(item.taskNo,item.customerNo)">{{item.customerName}}
                      <small>{{item.currentStatus | status-filter}}</small>
                      <span><a :href="'tel:' + item.customerMobile" @click="handleNew(item)"><i class="glyphicon glyphicon-earphone phone"></i>打给Ta</a></span>
                      <!--<span >{{item.distributeTime}}</span>-->
                    </h4>
                    <p class="list-group-item-text height18" >
                      <i class="glyphicon glyphicon-plus add" @click="handleNew(item)"></i><span class="ellipsis"  @click="handleNew(item)">{{item.remark ? item.remark : ''}}</span>
                      <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
                        <!--<span>
                          <i class="glyphicon glyphicon-earphone phone"></i>打给Ta
                        </span>-->
                        <span >{{item.distributeTime}}</span>
                      </a>

                    </p>
                  </li>
                </ul>
               <h3 v-if="listData.TALKING.length !== 0" style="pglyphicon glyphicon-plus adding:10px 15px">沟通中</h3>
              <ul class="list-group-item" v-if="listData.TALKING.length !== 0">
                  <li v-for="(item, index) of listData.TALKING" :key="index">
                    <h4 class="list-group-item-heading" @click.self="handleCustomer(item.taskNo,item.customerNo)">{{item.customerName}}
                      <small>{{item.currentStatus | status-filter}}</small>
                      <span><a :href="'tel:' + item.customerMobile" @click="handleNew(item)"><i class="glyphicon glyphicon-earphone phone"></i>打给Ta</a></span>
                      <!--<span >{{item.distributeTime}}</span>-->
                    </h4>
                    <p class="list-group-item-text height18" >
                      <i class="glyphicon glyphicon-plus add" @click="handleNew(item)"></i><span class="ellipsis"  @click="handleNew(item)">{{item.remark ? item.remark : ''}}</span>
                      <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
                        <!--<span>
                          <i class="glyphicon glyphicon-earphone phone"></i>打给Ta
                        </span>-->
                        <span >{{item.distributeTime}}</span>
                      </a>

                    </p>
                  </li>
                </ul>


               <h3 v-if="listData.ORDER.length !== 0" style="pglyphicon glyphicon-plus adding:10px 15px">订单</h3>
              <ul class="list-group-item" v-if="listData.ORDER.length !== 0">
                  <li v-for="(item, index) of listData.ORDER" :key="index">
                    <h4 class="list-group-item-heading" @click.self="handleCustomer(item.taskNo,item.customerNo)">{{item.customerName}}
                      <small>{{item.currentStatus | status-filter}}</small>
                      <span><a :href="'tel:' + item.customerMobile" @click="handleNew(item)"><i class="glyphicon glyphicon-earphone phone"></i>打给Ta</a></span>
                      <!--<span >{{item.distributeTime}}</span>-->
                    </h4>
                    <p class="list-group-item-text height18" >
                      <i class="glyphicon glyphicon-plus add" @click="handleNew(item)"></i><span class="ellipsis"  @click="handleNew(item)">{{item.remark ? item.remark : ''}}</span>
                      <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
                        <!--<span>
                          <i class="glyphicon glyphicon-earphone phone"></i>打给Ta
                        </span>-->
                        <span >{{item.distributeTime}}</span>
                      </a>

                    </p>
                  </li>
                </ul>


               <h3 v-if="listData.OTHER.length !== 0" style="pglyphicon glyphicon-plus adding:10px 15px">其他</h3>
              <ul class="list-group-item" v-if="listData.OTHER.length !== 0">
                  <li v-for="(item, index) of listData.OTHER" :key="index">
                    <h4 class="list-group-item-heading" @click.self="handleCustomer(item.taskNo,item.customerNo)">{{item.customerName}}
                      <small>{{item.currentStatus | status-filter}}</small>
                      <span><a :href="'tel:' + item.customerMobile" @click="handleNew(item)"><i class="glyphicon glyphicon-earphone phone"></i>打给Ta</a></span>
                      <!--<span >{{item.distributeTime}}</span>-->
                    </h4>
                    <p class="list-group-item-text height18" >
                      <i class="glyphicon glyphicon-plus add" @click="handleNew(item)"></i><span class="ellipsis"  @click="handleNew(item)">{{item.remark ? item.remark : ''}}</span>
                      <a :href="'tel:' + item.customerMobile" @click="handleNew(item)">
                        <!--<span>
                          <i class="glyphicon glyphicon-earphone phone"></i>打给Ta
                        </span>-->
                        <span >{{item.distributeTime}}</span>
                      </a>

                    </p>
                  </li>
                </ul>

              <ul style="margin:10px 0" class="list-group-item" v-if="listData.OTHER.length === 0 && listData.ORDER.length === 0 && listData.TALKING.length === 0 && listData.NEWCUSTOMER.length === 0">
                 <li class="text-center" style="border:none" >暂无数据</li>
              </ul>

              </scroller>
            </div>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import FooterComponent from './Footer.vue'
import HeaderComponent from '../components/Header'
import TabList from '../components/TabList'
export default {
  name: 'appindex',
  data() {
    return {
      tabs: {},  // tab类型
      activeTab: 0,   // 激活的tab
      listData: {
        NEWCUSTOMER: [],
        TALKING: [],
        ORDER: [],
        OTHER: []
      },   // 搜索列表
      limit: 10,   // 每次加载数量
      keyWord: ''  // 搜索关键词
    }
  },
  components: {
    FooterComponent,
    HeaderComponent,
    TabList
  },
  methods: {
    // 搜索列表
    handleSearch() {
      if(this.keyWord.trim() === ''){
        return false
      }
      axios.get('/api/customer/query?type=SEARCH&keywork=' + this.keyWord).then((res) => {
        if(res.data.code === '1'){
          this.listData = res.data.data
        }
      })
    },
    // 新建沟通记录信息
    handleNew () {
      // store.clearAll()
      window.location.href = 'communicationRecord.html'
    },
    // 打开客户信息
    handleCustomer (taskNo, customerNo) {
      // store.clearAll()
      window.location.href = 'customerDetail.html#/customer?id=' + taskNo + '&customerNo=' + customerNo
    }
  },
  created() {
    // this.handleSearch()
  }
}
</script>
