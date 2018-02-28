<template>
<transition name="router-fade" mode="out-in">
  <div :class="['tanboxbg', visiable ? '' : 'hidden']" v-show="visiable">
      <div class="tanbox">
        <slot></slot>
          <!--<ul>
              <li>0001-张三</li>
              <li>0001-张三</li>
              <li>0001-张三</li>

          </ul>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary bg578f6e">Save changes</button>
          </div>-->
      </div>
  </div>
</transition>
</template>
<script>
export default {
  name: 'modal',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    time: {
      type: Number,
      default: 1000
    },
    auto: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
    }
  },
  watch: {
    auto (val) {
      if(this.visiable && val){
        window.setTimeout(() => {
          this.$emit('update:show', false)
        }, this.time)
      }
    },
    visiable (val) {
      if(this.auto && val){
        window.setTimeout(() => {
          this.$emit('update:show', false)
        }, this.time)
      }
    }
  },
  computed: {
    visiable () {
      return this.show
    }
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

