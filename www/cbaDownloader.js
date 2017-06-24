var exec = require('cordova/exec');
var PLUGIN_NAME = 'CbaDownloaderPlugin';

  module.exports = {
  /*echo: function(phrase, cb) {
    exec(cb, null, PLUGIN_NAME, 'echo', [phrase]);
  },
  getDate: function(cb) {
    exec(cb, null, PLUGIN_NAME, 'getDate', []);
  },*/
  startDownload: function(data, cb) {
    exec(cb, null, PLUGIN_NAME, 'startDownload', [data]);
  }
};

