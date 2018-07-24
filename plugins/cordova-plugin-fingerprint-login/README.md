# Cordova FingerPrint Plugin
by yurish

This is a fork of Cordova TouchID Plugin by Eddy Verbruggen and Cordova Fingerprint ID Plugin by susanne
The original plugins are located here: https://github.com/EddyVerbruggen/cordova-plugin-touch-id and https://github.com/susannegeorge/cordova-plugin-fingerprint-id

//TODO
Add in support for Samsung Devices (Samsung Pass)


## Index
1. [Description](#1-description)
2. [Installation](#2-installation)
	2. [Automatically (CLI / Plugman)](#automatically-cli--plugman)
3. [Usage](#3-usage)
4. [Security++](#4-security)
5. [License](#5-license)

## 1. Description

Scan the fingerprint of your user with the TouchID sensor (iPhone 5S) and Samsung Android Phones.

* Compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman).
* Minimum iOS version is 8 (error callbacks will be gracefully invoked on lower versions).
* Requires a fingerprint scanner, so an iPhone 5S or newer is required.

//TODO - Samsung Android Description

## 2. Installation

### Automatically (CLI / Plugman)
Compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman), compatible with [PhoneGap 3.0 CLI](http://docs.phonegap.com/en/3.0.0/guide_cli_index.md.html#The%20Command-line%20Interface_add_features), here's how it works with the CLI (backup your project first!):

From npm:
```
$ cordova plugin add cordova-plugin-fingerprint-login
$ cordova prepare
```

The latest, from the master repo:
```
$ cordova plugin add <>
$ cordova prepare
```

TouchID.js is brought in automatically. There is no need to change or add anything in your html.

//TODO - Samsung Android Installation

## 3. Usage
First you'll want to check whether or not the user has a configured fingerprint scanner.
You can use this to show a 'log in with your fingerprint' button next to a username/password login form.
```js
window.plugins.touchid.isAvailable(
  function() {alert('available!')}, // success handler: TouchID available
  function(msg) {alert('not available, message: ' + msg)} // error handler: no TouchID available
);
```

If the onSuccess handler was called, you can scan the fingerprint.
There are two options: `verifyFingerprint` and `verifyFingerprintWithCustomPasswordFallback`.
The first method will offer a fallback option called 'enter passcode' which shows the default passcode UI when pressed.
The second method will offer a fallback option called 'enter password' (not passcode) which allows you to provide your own password dialog.
```js
window.plugins.touchid.verifyFingerprint(
  'Scan your fingerprint please', // this will be shown in the native scanner popup
   function(msg) {alert('ok: ' + msg)}, // success handler: fingerprint accepted
   function(msg) {alert('not ok: ' + JSON.stringify(msg))} // error handler with errorcode and localised reason
);
```
The errorhandler of the method above can receive an error code of `-2` which means the user pressed the 'enter password' fallback.

```js
window.plugins.touchid.verifyFingerprintWithCustomPasswordFallback(
  'Scan your fingerprint please', // this will be shown in the native scanner popup
   function(msg) {alert('ok: ' + msg)}, // success handler: fingerprint accepted
   function(msg) {alert('not ok: ' + JSON.stringify(msg))} // error handler with errorcode and localised reason
);
```

This will render a button labelled 'Enter password' in case the fingerprint is not recognized.
If you want to provide your own label ('Enter PIN' perhaps), you can use awkwardly named function (added in version 3.1.0):

```js
window.plugins.touchid.verifyFingerprintWithCustomPasswordFallbackAndEnterPasswordLabel(
  'Scan your fingerprint please', // this will be shown in the native scanner popup
  'Enter PIN', // this will become the 'Enter password' button label
   function(msg) {alert('ok: ' + msg)}, // success handler: fingerprint accepted
   function(msg) {alert('not ok: ' + JSON.stringify(msg))} // error handler with errorcode and localised reason
);
```

You can copy-paste these lines of code for a quick test:
```html
<button onclick="window.plugins.touchid.isAvailable(function(msg) {alert('ok: ' + msg)}, function(msg) {alert('not ok: ' + msg)})">Touch ID available?</button>
<button onclick="window.plugins.touchid.verifyFingerprint('Scan your fingerprint please', function(msg) {alert('ok: ' + msg)}, function(msg) {alert('not ok: ' + JSON.stringify(msg))})">Scan fingerprint</button>
```

//TODO - Samsung Android Usage

## 4. Security++
Since iOS9 it's possible to check whether or not the list of enrolled fingerprints changed since
the last time you checked it. It's recommended you add this check so you can counter hacker attacks
to your app. See [this article](https://godpraksis.no/2016/03/fingerprint-trojan/) for more details.

So instead of checking the fingerprint after `isAvailable` add another check.
In case `didFingerprintDatabaseChange` returns `true` you probably want to re-authenticate your user
before accepting valid fingerprints again.

```js
window.plugins.touchid.isAvailable(
    // success handler; available
    function() {
      window.plugins.touchid.didFingerprintDatabaseChange(
          function(changed) {
            if (changed) {
              // re-auth the user by asking for his credentials before allowing a fingerprint scan again
            } else {
              // call the fingerprint scanner
            }
          }
      );
    },
    // error handler; not available
    function(msg) {
      // use a more traditional auth mechanism
    }
);
```

//TODO - Samsung Android Security

## 5. License

[The MIT License (MIT)](http://www.opensource.org/licenses/mit-license.html)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.