<template>
<div class="wrap">
      <!--<header>
          客户信息</h3>
      </header>-->
      <div class="container-fluid">
          <ul class="mine-tab">
            <li :class="{active:activePath === 'index'}"><router-link :to="'/customer?id='+ query.id + '&customerNo=' + query.customerNo">客户信息</router-link></li>
            <li :class="{active:activePath === 'house'}"><router-link :to="'/customer/house?id='+ query.id + '&customerNo=' + query.customerNo">房屋信息</router-link></li>
            <li :class="{active:activePath === 'order'}"><router-link :to="'/customer/order?id='+ query.id + '&customerNo=' + query.customerNo">订单信息</router-link></li>
            <li :class="{active:activePath === 'contact'}"><router-link :to="'/customer/contact?id='+ query.id + '&customerNo=' + query.customerNo">联系人</router-link></li>
            <li :class="{active:activePath === 'record'}"><router-link :to="'/customer/record?id='+ query.id + '&customerNo=' + query.customerNo">沟通记录</router-link></li>
          </ul>
      </div>
      <div class="content">
        <transition name="router-fade" mode="out-in">
           <router-view></router-view>
        </transition>
      </div>
  </div>
</template>
<script>
export default {
  name: 'index-customer',
  data () {
    return {
    }
  },
  methods: {
    handleClick () {

    }
  },
  beforeRouteEnter(to, from, next){
    console.log(to)
    next()
  },
  computed: {
    activePath () {
      return this.$route.name
    },
    query () {
      return this.$route.query
    }
  },
  created () {
  }
}
</script>
<style>
.router-fade-enter-active, .router-fade-leave-active {
	  	transition: opacity .3s;
}
.router-fade-enter, .router-fade-leave-active {
	  	opacity: 0;
}
</style>
