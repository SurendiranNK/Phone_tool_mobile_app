cordova.define("window.plugins.samsungpass", function(require, exports, module) {
    var exec = require("cordova/exec");

    function SamsungPass() {
    }

    SamsungPass.prototype.isAvailable = function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "SamsungPass", "isSamsungPassSupported", []);
    };

    SamsungPass.prototype.hasRegisteredFingers = function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "SamsungPass", "hasRegisteredFingers", []);
    };

    SamsungPass.prototype.getFingerprintName = function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "SamsungPass", "getFingerprintName", []);
    };

    SamsungPass.prototype.startIdentify = function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "SamsungPass", "startIdentify", []);
    };


    var samsungPass = new SamsungPass();
    module.exports = samsungPass;
});