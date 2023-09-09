require('source-map-support/register');
module.exports =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/index.js":
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }
function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? ownKeys(Object(source), !0).forEach(function (key) { _defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }
function _defineProperty(obj, key, value) { key = _toPropertyKey(key); if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }
function _toPropertyKey(arg) { var key = _toPrimitive(arg, "string"); return typeof key === "symbol" ? key : String(key); }
function _toPrimitive(input, hint) { if (typeof input !== "object" || input === null) return input; var prim = input[Symbol.toPrimitive]; if (prim !== undefined) { var res = prim.call(input, hint || "default"); if (typeof res !== "object") return res; throw new TypeError("@@toPrimitive must return a primitive value."); } return (hint === "string" ? String : Number)(input); }
var koa = __webpack_require__(/*! koa */ "koa");
var app = module.exports = new koa();
const server = __webpack_require__(/*! http */ "http").createServer(app.callback());
const WebSocket = __webpack_require__(/*! ws */ "ws");
const wss = new WebSocket.Server({
  server
});
const Router = __webpack_require__(/*! koa-router */ "koa-router");
const cors = __webpack_require__(/*! @koa/cors */ "@koa/cors");
const bodyParser = __webpack_require__(/*! koa-bodyparser */ "koa-bodyparser");
app.use(bodyParser());
app.use(cors());
app.use(middleware);
function middleware(ctx, next) {
  const start = new Date();
  return next().then(() => {
    const ms = new Date() - start;
    console.log(`${start.toLocaleTimeString()} ${ctx.request.method} ${ctx.request.url} ${ctx.response.status} - ${ms}ms`);
  });
}
const getRandomInt = (min, max) => {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min)) + min;
};
const items = [];
const users = ['a', 'b', 'c'];
const products = ['p1', 'p2', 'p3'];
const statuses = ['active', 'done'];
for (let i = 0; i < products.length; i++) {
  const status = statuses[i % 2];
  const item = {
    id: i + 1,
    name: products[i],
    quantity: [{
      value: getRandomInt(0, 10) + 1,
      user: users[getRandomInt(0, users.length)]
    }],
    status
  };
  if (status === 'done') {
    item.totalPrice = getRandomInt(0, 100) + 1;
    item.boughtBy = users[getRandomInt(0, users.length)];
  }
  items.push(item);
}
const router = new Router();
router.get('/item', ctx => {
  const {
    postBy
  } = ctx.query;
  if (postBy) {
    ctx.response.body = items.filter(({
      quantity
    }) => quantity.some(({
      user
    }) => user === postBy));
  } else {
    const {
      status: qStatus
    } = ctx.query;
    ctx.response.body = qStatus ? items.filter(({
      status
    }) => status === qStatus) : items;
  }
  ctx.response.status = 200;
});
const broadcast = data => {
  const stringifiedData = JSON.stringify(data);
  console.log(`boadcast ${stringifiedData}`);
  wss.clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(stringifiedData);
    }
  });
};
router.post('/item', ctx => {
  const {
    name,
    value,
    user
  } = ctx.request.body;
  if ([name, value, user].some(it => typeof it === 'undefined')) {
    ctx.response.body = {
      text: 'Missing or invalid fields!'
    };
    ctx.response.status = 400;
    return;
  }
  const index = items.findIndex(it => it.name === name);
  let item;
  if (index === -1) {
    item = {
      id: Math.max.apply(Math, items.map(it => it.id)) + 1,
      name,
      quantity: [{
        value,
        user
      }],
      status: 'active'
    };
    items.push(item);
  } else {
    item = items[index];
    if (item.status === 'done') {
      ctx.response.body = {
        text: 'Cannot add values for done items'
      };
      ctx.response.status = 400;
      return;
    }
    const qIndex = item.quantity.findIndex(q => q.user === user);
    if (qIndex === -1) {
      item.quantity.push({
        value,
        user
      });
    } else {
      item.quantity = item.quantity.map(q => q.user === user ? _objectSpread(_objectSpread({}, q), {}, {
        value: q.value + value
      }) : q);
    }
  }
  broadcast(item);
  ctx.response.body = item;
  ctx.response.status = 200;
});
router.patch('/item/:id', ctx => {
  const {
    id
  } = ctx.params;
  const index = items.findIndex(it => it.id == id);
  if (index === -1) {
    ctx.response.body = {
      text: 'Invalid item id'
    };
    ctx.response.status = 404;
    return;
  }
  Object.assign(items[index], ctx.request.body);
  ctx.response.body = items[index];
  ctx.response.status = 200;
  broadcast(items[index]);
});
app.use(router.routes());
app.use(router.allowedMethods());
server.listen(3000);

/***/ }),

/***/ 0:
/*!****************************!*\
  !*** multi ./src/index.js ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\Users\Andrei\Desktop\UBB\Semestru 5\Programare pentru dispozitive mobile\Examen\rest\src/index.js */"./src/index.js");


/***/ }),

/***/ "@koa/cors":
/*!****************************!*\
  !*** external "@koa/cors" ***!
  \****************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("@koa/cors");

/***/ }),

/***/ "http":
/*!***********************!*\
  !*** external "http" ***!
  \***********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("http");

/***/ }),

/***/ "koa":
/*!**********************!*\
  !*** external "koa" ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa");

/***/ }),

/***/ "koa-bodyparser":
/*!*********************************!*\
  !*** external "koa-bodyparser" ***!
  \*********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa-bodyparser");

/***/ }),

/***/ "koa-router":
/*!*****************************!*\
  !*** external "koa-router" ***!
  \*****************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("koa-router");

/***/ }),

/***/ "ws":
/*!*********************!*\
  !*** external "ws" ***!
  \*********************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = require("ws");

/***/ })

/******/ });
//# sourceMappingURL=main.map