var path = require('path')
var fs = require('fs')
// 目标目录
var distPath = path.join(__dirname, '../../../../webapp/static/js/wx-crm')
// 生成jsp目录
var publistPath = path.join(__dirname, '../../../WEB-INF/views/dist')
var files = fs.readdirSync(distPath)
files.forEach(function (element, index, array) {
  if (element.indexOf('.html') >= 0) {
    let newFile = element.replace('.html', '.jsp')
    let filecon = fs.createWriteStream(publistPath + '/' + newFile, {
      flags: 'w',
      defaultEncoding: 'utf8',
      fd: null,
      mode: 0o666,
      autoClose: true,
      start: 0
    })
    filecon.write('<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>\n<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>\n<%@ page contentType="text/html;charset=UTF-8" language="java" %>\n', 'utf8', (er) => {
    })
    fs.readFile(distPath + '/' + element, (errors, buf) => {
      if(buf){
        fs.appendFile(publistPath + '/' + newFile, buf, (err) => {
          if (err) throw err
          console.log('The "data to append" was appended to file!')
        })
      }
    })
  }
})
