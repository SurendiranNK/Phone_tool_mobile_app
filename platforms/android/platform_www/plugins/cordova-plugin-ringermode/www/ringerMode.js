cordova.define("cordova-plugin-ringermode.ringermode", function(require, exports, module) {
var ringerModeExport = {};

ringerModeExport.getRingerMode = function(successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "ringerMode", "getRingerMode", []);
};

module.exports = ringerModeExport;

});
