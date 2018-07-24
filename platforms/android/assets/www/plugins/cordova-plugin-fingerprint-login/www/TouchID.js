cordova.define("cordova-plugin-fingerprint-login.TouchID", function(require, exports, module) {
cordova.define("window.plugins.touchid", function(require, exports, module){
  var exec = require("cordova/exec");

  function TouchID() {
  }

  TouchID.prototype.isAvailable = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "TouchID", "isAvailable", []);
  };

  TouchID.prototype.didFingerprintDatabaseChange = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "TouchID", "didFingerprintDatabaseChange", []);
  };

  TouchID.prototype.verifyFingerprint = function (message, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "TouchID", "verifyFingerprint", [message]);
  };

  TouchID.prototype.verifyFingerprintWithCustomPasswordFallback = function (message, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "TouchID", "verifyFingerprintWithCustomPasswordFallback", [message]);
  };

  TouchID.prototype.verifyFingerprintWithCustomPasswordFallbackAndEnterPasswordLabel = function (message, enterPasswordLabel, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "TouchID", "verifyFingerprintWithCustomPasswordFallbackAndEnterPasswordLabel", [message, enterPasswordLabel]);
  };

  // TouchID.install = function () {
  //   if (!window.plugins) {
  //     window.plugins = {};
  //   }
  //
  //   window.plugins.touchid = new TouchID();
  //   return window.plugins.touchid;
  // };

  var touchId = new TouchID();
  module.exports = touchId;
  //cordova.addConstructor(TouchID.install);

});

});
