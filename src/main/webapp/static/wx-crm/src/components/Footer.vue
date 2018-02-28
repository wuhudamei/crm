<template>
  <footer>
      <div class="row foot-nav">
          <div v-for="(item, $index) of items" class="col-xs-4 foot-list">
            <a href="javascript:void(0);" @click=handleClick($index)>
              <span :class="[item.name,$index === activeIndex? 'on' : '']"></span><br>
              {{item.text}}
              </a>
            </div>
      </div>
  </footer>
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
      items: [
        {name: 'groups', text: '我的客户'},
        {name: 'schedule', text: '日程'},
        {name: 'upstage', text: '我的'}
      ],
      activeIndex: null
    }
  },
  methods: {
    handleClick ($index) {
      this.activeIndex = $index
      let url = 'appindex.html'
      if(this.activeIndex === 1) {
        url = 'calendar.html'
      }else if (this.activeIndex === 2) {
        url = 'my.html'
      }else{
        url = 'appindex.html#/tab/0'
      }
      store.remove('indextab')
      this.$emit('footer-click', $index)
      window.location.href = url
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
