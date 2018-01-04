ConnectFourWeb
=====================================
ConnectFourWeb is a web implementation of the game Connect Four.

See also: https://en.wikipedia.org/wiki/Connect_Four.

[![Build Status](https://travis-ci.org/danielfranze/ConnectFourWeb.svg?branch=master)](https://travis-ci.org/danielfranze/ConnectFourWeb) [![Coverage Status](https://coveralls.io/repos/github/danielfranze/ConnectFourWeb/badge.svg?branch=master)](https://coveralls.io/github/danielfranze/ConnectFourWeb?branch=master) [![codecov](https://codecov.io/gh/danielfranze/ConnectFourWeb/branch/master/graph/badge.svg)](https://codecov.io/gh/danielfranze/ConnectFourWeb)
 [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/461242ae078b4751bb56ea0ff4ebfe87)](https://www.codacy.com/app/danielfranze/ConnectFourWeb?utm_source=github.com&utm_medium=referral&utm_content=danielfranze/ConnectFourWeb&utm_campaign=Badge_Coverage)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/461242ae078b4751bb56ea0ff4ebfe87)](https://www.codacy.com/app/danielfranze/ConnectFourWeb?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=danielfranze/ConnectFourWeb&amp;utm_campaign=Badge_Grade)

Preview
--------------------

Start Screen                       |  Winner Screen
:----------------------------------:|:---------------------------------:
![](assets/screenshots/empty.png)  |  ![](assets/screenshots/won.png)

Live Demo
--------------------
https://connectfourweb.herokuapp.com/

Installation
--------------------

1. Clone this Repository
2. Open it with IntelliJ IDEA (with Scala plugin)
3. Change in "public\javascripts\app.js" the line 5 (for local usage):
```javascript
var scheme   = "ws://";
```
* local mode: "ws://"
* production mode: "wss://"

4. Start Play

Technologies
----------------------

<img src="https://dl.dropboxusercontent.com/s/xc5bnntgqlqa4wj/scala_logo.png?dl=0" height="50"> <img src="https://dl.dropboxusercontent.com/s/yr615075l2z8ln5/play_logo.png?dl=0" height="50"> <img src="https://dl.dropboxusercontent.com/s/7avawscnenwjfwn/travis_logo.png?dl=0" height="50">

License
-------
ConnectFourWeb is released under the terms of the MIT license. See https://opensource.org/licenses/MIT for more information.
