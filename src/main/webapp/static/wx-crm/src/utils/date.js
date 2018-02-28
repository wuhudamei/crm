
/**
* 判断是否是周末
* @param Number day
*/
function isWeekEnd(day) {
  return day === 0 || day === 6
}

/**
 * 判断是否是今天
 * @param  {Date} compareDate
 */
function isToday(compareDate) {
  let today = new Date()
  let todayStr = `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
  let compareDateStr = `${compareDate.getFullYear()}-${compareDate.getMonth()}-${compareDate.getDate()}`
  return todayStr === compareDateStr
}

/**
 * 天数转化成毫秒
 * @param {Number} day
 */
function dayToMilliseconds(day) {
  return day * 24 * 60 * 60 * 1000
}
/**
 * 日历日期是否是选中的日期
 * @param {Date} calendarDate 日历日期
 * @param {Date} selectedDate 选中日期
 */
function isSelectedDate(calendarDate, selectedDate) {
  let calendarDateStr = `${calendarDate.getFullYear()}-${calendarDate.getMonth()}-${calendarDate.getDate()}`
  let selectedDateStr = `${selectedDate.getFullYear()}-${selectedDate.getMonth()}-${selectedDate.getDate()}`
  return calendarDateStr === selectedDateStr
}

/**
 * 单数进来双数出去
 * @param {Number} number
 */
function getTwoDigits(number) {
  if (number >= 10) {
    return number
  } else {
    return '0' + number
  }
}

/**
 * 将日期格式化成 YYYY-MM-DD 不考虑小于10补0的情况
 * @param {Date} date 要格式化的日期
 */
function dateYYYYMMDDFormat(date) {
  return `${date.getFullYear()}-${getTwoDigits(date.getMonth() + 1)}-${getTwoDigits(date.getDate())}`
}

/**
 * 将日期格式化成 YYYY-MM-DD hh:mm 不考虑小于10补0的情况
 * @param {Date} date 要格式化的日期
 */
function dateYYYYMMDDHHMMFormat(date) {
  return `${date.getFullYear()}-${getTwoDigits(date.getMonth() + 1)}-${getTwoDigits(date.getDate())} ${getTwoDigits(date.getHours())}:${getTwoDigits(date.getMinutes())}`
}

module.exports = {
  isWeekEnd,
  isToday,
  dayToMilliseconds,
  isSelectedDate,
  dateYYYYMMDDFormat,
  dateYYYYMMDDHHMMFormat
}
