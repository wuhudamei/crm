<template>
  <div class="pos-bottom">
      <div class="go-back" @click="goBack">
        <i class="fa fa-reply" aria-hidden="true"></i>
      </div>
  </div>
</template>
<script>
import store from '../config/store.js'
export default {
  name: 'footer',
  props: {
    active: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
    }
  },
  methods: {
    goBack () {
      store.remove('indextab')
      window.location.href = 'appindex.html#/tab/0'
    }
  },
  created () {
    this.activeIndex = this.active
  }
  // computed: {
  //   activeIndex: function() {
  //     return this.active
  //   }
  // }
}
</script>
<style scoped>
  .pos-bottom {
    bottom: 30px;
    position: fixed;
    right: 20px;
    z-index: 100;
    }
  .go-back {
    background-attachment: initial;
    background-clip: initial;
    background-color: #1AB394;
    background-image: initial;
    background-origin: initial;
    background-position-x: initial;
    background-position-y: initial;
    background-repeat-x: initial;
    background-repeat-y: initial;
    background-size: initial;
    border-bottom-left-radius: 50%;
    border-bottom-right-radius: 50%;
    border-top-left-radius: 50%;
    border-top-right-radius: 50%;
    color: white;
    height: 38px;
    margin-top: 5px;
    opacity: 0.5;
    padding-bottom: 9px;
    padding-left: 8px;
    padding-right: 8px;
    padding-top: 9px;
    text-align: center;
    width: 38px;
  }
</style>
