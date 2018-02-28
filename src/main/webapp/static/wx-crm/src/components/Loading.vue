<template>
  <div v-show="show" class="loading">
    <p>
      <img src="/static/img/loading.gif">
      <br>
      <slot>正在加载，请稍后...</slot>
    </p>
  </div>
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean,
      required: false,
      default: function () {
        return false
      }
    }
  }
}
</script>
