var koa = require('koa');
var app = module.exports = new koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');

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
    quantity: [{ value: getRandomInt(0, 10) + 1, user: users[getRandomInt(0, users.length)] }],
    status,
  };
  if (status === 'done') {
    item.totalPrice = getRandomInt(0, 100) + 1;
    item.boughtBy = users[getRandomInt(0, users.length)];
  }
  items.push(item);
}

const router = new Router();

router.get('/item', ctx => {
  const { postBy } = ctx.query
  if (postBy) {
    ctx.response.body = items.filter(({ quantity }) => quantity.some(({ user }) => user === postBy));
  } else {
    const { status: qStatus } = ctx.query;
    ctx.response.body = qStatus ? items.filter(({ status }) => status === qStatus) : items;
  }
  ctx.response.status = 200;
});

const broadcast = data => {
  const stringifiedData = JSON.stringify(data);
  console.log(`boadcast ${stringifiedData}`);
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(stringifiedData);
    }
  });
};

router.post('/item', ctx => {
  const { name, value, user } = ctx.request.body;
  if ([name, value, user].some(it => typeof it === 'undefined')) {
    ctx.response.body = { text: 'Missing or invalid fields!' };
    ctx.response.status = 400;
    return;
  }
  const index = items.findIndex(it => it.name === name);
  let item;
  if (index === -1) {
    item = {
      id: Math.max.apply(Math, items.map(it => it.id)) + 1,
      name,
      quantity: [{ value, user }],
      status: 'active',
    }
    items.push(item);
  } else {
    item = items[index];
    if (item.status === 'done') {
      ctx.response.body = { text: 'Cannot add values for done items' };
      ctx.response.status = 400;
      return;
    }
    const qIndex = item.quantity.findIndex(q => q.user === user);
    if (qIndex === -1) {
      item.quantity.push({ value, user });
    } else {
      item.quantity = item.quantity
        .map(q => q.user === user ? ({ ...q, value: q.value + value }) : q);
    }
  }
  broadcast(item);
  ctx.response.body = item;
  ctx.response.status = 200;
});

router.patch('/item/:id', ctx => {
  const { id } = ctx.params;
  const index = items.findIndex(it => it.id == id);
  if (index === -1) {
    ctx.response.body = {text: 'Invalid item id'};
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
