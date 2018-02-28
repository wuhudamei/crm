<template>
  <div class="wrap">
    <transition name="router-fade" mode="out-in">
      <router-view></router-view>
    </transition>
    <!--<header>
      客户信息</h3>
    </header>
    <div class="content">
      <div class="container-fluid">
        <ul class="mine-tab">
            <li :class='currentTab.customer'><router-link to="/customer">客户信息</router-link></li>
            <li :class='currentTab.house'><router-link to="/house">房屋信息</router-link></li>
            <li :class='currentTab.order'><router-link to="/order">订单信息</router-link></li>
            <li :class='currentTab.record'><router-link to="/record">沟通记录</router-link></li>
        </ul>
      </div>
      <div class="container cd-content">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </div>
    </div>-->
    <go-back></go-back>
  </div>
</template>


<script>
import GoBack from '../components/GoBack.vue'
import axios from 'axios'
export default {
  name: 'customerDetail',
  data (){
    return {
      customerInfo: {
        phone: '1010101010',
        address: '为 i 为 i 发 i 俄方还'
      }, // 客户信息
      currentTab: {
        customer: null,
        house: null,
        order: null,
        record: null
      }
    }
  },
  components: {
    GoBack
  },
  methods: {
    fetchDate() {
      axios.get('').then(function(res){})
    },
    tab(current){
      this.currentTab = {
        customer: '',
        house: '',
        order: '',
        record: ''
      }
      for(var key in this.currentTab){
        if(current === key){
          this.currentTab[key] = 'active'
          break
        }
      }
    }
  },
  created(){
    var current = this.$route.name
    this.tab(current)
  },
  watch: {
    '$route' (to, from){
      this.tab(to.name)
    }
  }
}
</script>

<style lang="less">
    .container-fluid{
        margin-right: 0;
        margin-left: 0;
    }
    .router-fade-enter-active, .router-fade-leave-active {
	  	transition: opacity .3s;
    }
    .router-fade-enter, .router-fade-leave-active {
      opacity: 0;
    }
</style>


