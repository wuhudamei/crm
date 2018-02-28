var engine = require('store/src/store-engine')
var storages = [
  require('store/storages/sessionStorage')
]
var plugins = [
  require('store/plugins/defaults'),
  require('store/plugins/expire')
]
export default engine.createStore(storages, plugins)
