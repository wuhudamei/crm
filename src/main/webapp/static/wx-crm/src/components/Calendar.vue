<template>
  <div class="wrapper calendar-wrapper">
    <slot></slot>
    <div class="calendar-header">
      <div class="date-box">
        <div class="item item-years">
          <span class="month">{{selectedMonth}}月</span>{{selectedYear}}
        </div>
        <div @click="todayClickHandler" class="item item-today">
          今天
        </div>
      </div>
      <!--/.date-box-->
      <div class="week">
        <div class="item _state-weekend">日</div>
        <div class="item">一</div>
        <div class="item">二</div>
        <div class="item">三</div>
        <div class="item">四</div>
        <div class="item">五</div>
        <div class="item _state-weekend">六</div>
      </div>
      <!--/.week-->
      <div v-if="showSwiper" ref="swiper" class="swiper-container">
        <div ref="swiperWrapper" class="swiper-wrapper">
        </div>
      </div>
    </div>
    <!--/.calendar-header-->
    <div class="calendar-content">
      <div v-for="(hour,index) in hours" class="time-line">
        <div class="time">{{hour.hour}}</div>
        <div class="note" :class="{'_active':(hour.events && hour.events.length > 0)}">
          <template v-if="hour.events">
            <div v-for="hEvent in hour.events" class="item _ellipsis" @click="eventClickHandler(hEvent, hour)">
              {{hEvent.title}}
            </div>
          </template>
        </div>
      </div>
      <div class="time-line">
        <div class="time">24</div>
        <div class="note">
        </div>
      </div>
    </div>
    <!--/.calendar-content-->
  </div>
</template>
<script>

import Swiper from 'swiper/dist/js/swiper'
require('swiper/dist/css/swiper.css')
import { isWeekEnd, isToday, dayToMilliseconds, isSelectedDate, dateYYYYMMDDFormat } from '../utils/date'


function dayNumberClickHandler(e) {
  try {
    let node = e.target
    let nodeName = node.nodeName.toLowerCase()
    let index = node.className.indexOf('state-item')
    // div 且 class 为 state-item 的可以进行操作
    if (nodeName === 'div' && index !== -1) {
      // 已被选择时不做任何操作
      if (node.className.indexOf('_select') !== -1) {
        console.log('returned')
        return false
      }
      // 清除所有选中的
      let selectedNode = this.$refs.swiper.querySelector('._select')
      if (selectedNode) {
        selectedNode.className = selectedNode.className.replace('_select', '').trim()
      }
      if (node) {
        node.className += ' _select'
        let time = new Date(node.dataset.time)
        // 向父元素传递选中日期
        this.$emit('update:selectedDate', new Date(time))
      }
      e.stopPropagation && e.stopPropagation()
    }
  } catch (e) {
    console.log(e)
  }
}

function touchmoveHandler(e) {
  e.preventDefault()
}

export default {
  data() {
    return {
      showSwiper: true,
      // 内部维护的当前日期选择位
      currDate: null,
      // 生成的模板片段
      fragment: '',
      // Swiper 对象
      swiper: null
    }
  },
  props: {
    // 小时数组内部包含事件
    hours: {
      type: Array,
      required: false,
      default: function () {
        return new Array(24)
      }
    },
    // 选中的日期
    selectedDate: {
      type: Date,
      required: true
    },
    // 是否自动选择
    autoSelect: {
      type: Boolean,
      required: false,
      default: function () {
        return true
      }
    }
  },
  created() {
    // 默认当前日期为传送的选中日期
    this.currDate = new Date(this.selectedDate.getTime())
  },
  mounted() {
    this.$nextTick(function () {
      // 计算今天
      let today = new Date()
      // 今天星期几
      let todayDay = today.getDay()
      // 本周的开始时间
      let currWeekStart = new Date(today.getTime() - dayToMilliseconds(todayDay))
      // 上周的开始时间
      let previousWeekStart = new Date(currWeekStart.getTime() - dayToMilliseconds(7))
      // 下周的开始时间
      let nextWeekStart = new Date(currWeekStart.getTime() + dayToMilliseconds(7))
      let fragment = ''
      fragment += this.generateWeek(previousWeekStart, true)
      fragment += this.generateWeek(currWeekStart, true)
      fragment += this.generateWeek(nextWeekStart, true)
      this.$refs.swiperWrapper.innerHTML = fragment
      this.activeSwiper()
    })
  },
  beforeDestroy() {
    this.swiper.destroy()
  },
  methods: {
    todayClickHandler(e) {
      // 默认当前日期为传送的选中日期
      this.$emit('update:selectedDate', new Date())
      this.currDate = new Date()
      this.showSwiper = false
      this.$nextTick(function () {
        // 计算今天
        let today = new Date()
        // 今天星期几
        let todayDay = today.getDay()
        // 本周的开始时间
        let currWeekStart = new Date(today.getTime() - dayToMilliseconds(todayDay))
        // 上周的开始时间
        let previousWeekStart = new Date(currWeekStart.getTime() - dayToMilliseconds(7))
        // 下周的开始时间
        let nextWeekStart = new Date(currWeekStart.getTime() + dayToMilliseconds(7))
        let fragment = ''
        fragment += this.generateWeek(previousWeekStart, true)
        fragment += this.generateWeek(currWeekStart, true)
        fragment += this.generateWeek(nextWeekStart, true)
        this.showSwiper = false
        this.showSwiper = true
        this.$nextTick(function(){
          this.$refs.swiper.removeEventListener('click', dayNumberClickHandler, false)
          this.swiper.destroy()
          this.$refs.swiperWrapper.innerHTML = fragment
          this.activeSwiper()
        })
      })
    },
    eventClickHandler(hourEvent, hour) {
      this.$emit('calendarEventClick', hourEvent, hour)
    },
    generateWeek(startDate) {
      let fragment = ''
      fragment += '<div class="day-slider swiper-slide"><div class="slider-item">'
      for (let i = 0; i <= 6; i++) {
        let tempDate = new Date(startDate.getTime() + dayToMilliseconds(i))
        fragment += `
        <div class="day-item">
          <div data-time="${dateYYYYMMDDFormat(tempDate)}" class="state-item
            ${isToday(tempDate) ? '_today' : ''}
            ${isSelectedDate(tempDate, this.selectedDate) ? '_select' : ''}
            ${isWeekEnd(i) ? '_state-weekend' : ''}">${tempDate.getDate()}</div>
        </div>
      `
      }
      fragment += '</div></div>'
      return fragment
    },
    activeSwiper() {
      let self = this
      self.swiper = new Swiper(this.$refs.swiper, {
        initialSlide: 1,
        speed: 500,
        onSlideChangeStart: function (swiper) {
          // 禁用触摸
          swiper.disableTouchControl()
          // 锁定 swiper
          swiper.lockSwipes()
          // 以上两项不一定起作用
          console.log(swiper.activeIndex, 'start')
          // 解决苹果手机在页面滑动容易触发反弹造成假死的问题
          document.addEventListener('touchmove', touchmoveHandler)
        },
        onSlideChangeEnd: function (swiper) {
          console.log(swiper.activeIndex, 'end')
          if (swiper.activeIndex === 0) {
            self.setCurrDate(swiper.activeIndex)
            self.leftSlideHandle()
          } else if (swiper.activeIndex === 2) {
            self.setCurrDate(swiper.activeIndex)
            self.rightSlideHandle()
          } else {

          }
          // swiper start 上的封锁
          swiper.unlockSwipes()
          swiper.enableTouchControl()
          document.removeEventListener('touchmove', touchmoveHandler)
        }
      })
      self.$refs.swiper.addEventListener('click', dayNumberClickHandler.bind(self), false)
    },
    setCurrDate(direction) {
      try {
        switch (direction) {
          case 0:
            this.currDate = new Date(this.currDate.getTime() - dayToMilliseconds(7))
            if (this.autoSelect) {
              this.$emit('update:selectedDate', new Date(this.selectedDate.getTime() - dayToMilliseconds(7)))
            }

            break
          case 2:
            this.currDate = new Date(this.currDate.getTime() + dayToMilliseconds(7))
            if (this.autoSelect) {
              this.$emit('update:selectedDate', new Date(this.selectedDate.getTime() + dayToMilliseconds(7)))
            }
            break
          default:
            break
        }
        if (this.autoSelect) {
          this.$nextTick(function () {
            let selectedNode = this.$refs.swiper.querySelector('._select')
            if (selectedNode) {
              selectedNode.className = selectedNode.className.replace('_select', '').trim()
            }
            let node = this.$refs.swiper.querySelector(`[data-time="${dateYYYYMMDDFormat(this.currDate)}"]`)
            if (node) {
              node.className += ' _select'
            }
          })
        }
      } catch (e) {
        console.log(e, direction)
      }
    },
    rightSlideHandle() {
      try {
        let self = this
        self.swiper.removeSlide(0)
        let number = 6 - this.currDate.getDay() + 1
        let fragment = self.generateWeek(new Date(this.currDate.getTime() + dayToMilliseconds(number)))
        self.swiper.appendSlide(fragment)
        self.swiper.setWrapperTranslate(-1 * self.$refs.swiper.clientWidth)
        self.swiper.slideTo(1, 0)
      } catch (e) {
        console.log(e, 'rightslide')
      }
    },
    leftSlideHandle() {
      try {
        let self = this
        let number = this.currDate.getDay() + 7
        let fragment = self.generateWeek(new Date(this.currDate.getTime() - dayToMilliseconds(number)))
        self.swiper.prependSlide(fragment)
        self.swiper.setWrapperTranslate(-1 * self.$refs.swiper.clientWidth)
        self.swiper.slideTo(1, 0)
        self.swiper.removeSlide(3)
      } catch (e) {
        console.log(e, 'leftslide')
      }
    }
  },
  watch: {

  },
  computed: {
    selectedMonth() {
      return (this.currDate.getMonth() + 1)
    },
    selectedYear() {
      return (this.currDate.getFullYear())
    }
  }
}
</script>

<style lang="less">
//日历组件
.calendar-wrapper {
  display: flex;
  flex-flow: column;
  height: 100%;
  flex: 1;
}

.calendar-header {
  background: #ffffff;
  padding: 6px 0;
  border-bottom: 1px solid #d5d5d5;
}

.date-box {
  padding-bottom: 10px;
  font-size: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .item {
    padding: 0 20px;
  }
  .month {
    display: inline-block;
    font-size: 22px;
    color: #fa4545;
    margin-right: 5px;
  }
  .item-today {
    font-size: 20px;
    color: #fa4545;
  }
}



.week {
  display: flex;
  justify-content: space-between;
  margin: 5px 0;
  .item {
    text-align: center;
    flex: 1;
    font-size: 10px;
  }
}

.day-slider {
  padding: 8px 0;
  .slider-item {
    display: flex;
  }
}

.day-item {
  flex: 1;
  display: flex;
  justify-content: center;
  .state-item {
    width: 20px;
    height: 20px;
    padding: 8px;
    border-radius: 50%;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    transition: all linear .1s;
    font-size: 16px;
    justify-content: center;
    .lunar {
      font-size: 10px;
      width: 100%;
      text-align: center;
    } //日期状态——今天
    &._today {
      color: #fa4545;
      &._select {
        background: #fa4545;
        color: #ffffff;
      }
    } //日期状态——普通选中
    &._select {
      background: #222222;
      color: #ffffff;
    }
  }
}

.calendar-content {
  flex: 1;
  overflow-y: scroll;
  padding-top: 10px;
  -webkit-overflow-scrolling: touch;
  background: #f5f5f5;
}

.time-line {
  display: flex;
  min-height: 40px;
  width: 100%;
  &:last-child {
    height: 1px;
    min-height: 0;
  }
  .time {
    flex-shrink: 0;
    position: relative;
    margin-top: -5px;
    padding: 0 5px;
    color: #c5c5c5;
    font-size: 12px;
  }
  .note {
    padding: 5px 10px;
    flex: 1;
    border-top: 1px solid #d5d5d5;
    font-size: 12px;
    width: calc( 100% - 40px);
    .item {
      width: 100%;
      line-height: 1.8;
    }
    &._active {
      background: #f4dafb;
      color: #5d1272;
      border-left: 3px solid #b14fcd;
    }
  }
}

//周末字体变灰状态
._state-weekend {
  color: #c5c5c5;
}

._ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
