var gulp = require('gulp')
var sass = require('gulp-sass')
// var wrap = require('gulp-wrap')
// var through = require('through2')
var sourcemaps = require('gulp-sourcemaps')
var cleanCss = require('gulp-clean-css')
var autoprefixer = require('gulp-autoprefixer')
var browserSync = require('browser-sync').create()
var plumber = require('gulp-plumber')
var notify = require('gulp-notify')
const venderSrc = './static/'
const rename = require('gulp-rename')
var plumberOptions = {
  errorHandler: notify.onError('Error:<%= error.message %>')
}




// 样式编译
gulp.task('sass', function () {
  var array = [
    `${venderSrc}/bootstrap/css/bootstrap.css`,
    `./scss/style.scss`
  ]
  gulp.src(array)
    .pipe(plumber(plumberOptions))
    .pipe(sourcemaps.init())
    .pipe(sass())
    .pipe(autoprefixer({
      browsers: ['last 20 versions', 'ie > 8']
    }))
    .pipe(cleanCss({
      compatibility: 'ie8'
    }))
    // 不需要重命名
    // .pipe(rename('rocoui.css'))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest('./static/css'))
    .pipe(browserSync.stream())
})

// browsersync
gulp.task('server', ['sass'], function () {
  browserSync.init({
    server: './'
  })
  gulp.watch('./scss/**/*.scss', ['sass'])
  gulp.watch('./templates/*.html').on('change', browserSync.reload)
})
// 发布时修改文件名
gulp.task('pub', function () {
  gulp.watch('./dist/*.html').on('change', function (event) {
    console.log(event)
    gulp.src('./dist/*.html')
      // .pipe(wrap({src: './src/assets/javainit.html'}))
      // .pipe(concat(['./src/assets/javainit.html']))
      .pipe(rename(function (path) {
        path.extname = '.jsp'
      }))
      .pipe(gulp.dest('./dist'))
  })
})

// 默认开发
gulp.task('default', ['server'])



// fontspider
//  先将源文件搬家
// gulp.task('fontspider-pre', function () {
//   return gulp.src('./webfont/font/.font-spider/*.ttf').
//   pipe(gulp.dest('./webfont/font'))
// })

// 抽取文字
// gulp.task('fontspider-pre2', function () {
//   return gulp.src('./webfont/index.html')
//     .pipe(plumber(plumberOptions))
//     .pipe(fontSpider())
// })

// var fontSpider = require('gulp-font-spider')
// // 将生成的字体加入到 最终的font目录下
// gulp.task('fontspider', ['fontspider-pre', 'fontspider-pre2'], function () {
//   return gulp.src('./webfont/font/*.{eot,ttf,woff,svg}')
//     .pipe(gulp.dest('./static/font'))
// })

gulp.task('publish', ['pub_img'])

gulp.task('pub_move', function () {
  return gulp.src('./dist/**/*.*')
    .pipe(gulp.dest('../../source/pc_webapp/WebApp/m/'))
})


// var imagemin = require('gulp-imagemin')
// var imageminJpegRecompress = require('imagemin-jpeg-recompress')
// var jpgmin = imageminJpegRecompress({
//   accurate: true,
//   quality: 'high',
//   min: 70,
//   loops: 0,
//   progressive: true
// })
// gulp.task('pub_img', ['pub_move'], function () {
//   return gulp.src(`./static/img/**/*.*`)
//     .pipe(imagemin([
//       imagemin.gifsicle({
//         interlaced: true
//       }),
//       jpgmin,
//       imagemin.optipng({
//         optimizationLevel: 5
//       }),
//       imagemin.svgo({
//         plugins: [{
//           removeViewBox: true
//         }]
//       })
//     ], {
//       verbose: true
//     }))
//     .pipe(gulp.dest('../../source/pc_webapp/WebApp/m/static/img/'))
// })
