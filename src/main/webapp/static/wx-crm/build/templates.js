var path = require('path')
var fs = require('fs')
var HtmlWebpackPlugin = require('html-webpack-plugin')
var templatesPath = path.join(__dirname, '../src/templates')

var files = fs.readdirSync(templatesPath)
var plugins = []
var productPlugins = []
files.forEach(function (element, index, array) {
  console.log(element)
  var chunkName = element.replace('.html', '')
  if (/.html$/.test(element)) {
    var plugin = new HtmlWebpackPlugin({
      filename: element,
      template: 'src/templates/' + element,
      inject: true,
      chunks: [chunkName]
      // excludeChunks:['app']
    })
    plugins.push(plugin)


    var productPlugin = new HtmlWebpackPlugin({
      filename: element,
      template: 'src/templates/' + element,
      inject: true,
      minify: {
        removeComments: true,
        collapseWhitespace: true,
        removeAttributeQuotes: true
      },
      chunks: ['manifest', 'vendor', chunkName],
      chunksSortMode: 'dependency'
    })
    productPlugins.push(productPlugin)
  }
})

module.exports = { plugins: plugins, productPlugins: productPlugins }

