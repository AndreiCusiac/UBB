import Router from 'koa-router';
import BookRowStore from './store';
import { broadcast } from "../utils";

export const router = new Router();

router.get('/', async (ctx) => {
  const response = ctx.response;
  const userId = ctx.state.user._id;
  response.body = await BookRowStore.find({ userId });
  response.status = 200; // ok
});

router.get('/:id', async (ctx) => {
  const userId = ctx.state.user._id;
  const bookRow = await BookRowStore.findOne({ _id: ctx.params.id });
  const response = ctx.response;
  if (bookRow) {
    if (bookRow.userId === userId) {
      response.body = bookRow;
      response.status = 200; // ok
    } else {
      response.status = 403; // forbidden
    }
  } else {
    response.status = 404; // not found
  }
});

const createBookRow = async (ctx, bookRow, response) => {
  try {
    const userId = ctx.state.user._id;
    bookRow.userId = userId;
    response.body = await BookRowStore.insert(bookRow);
    response.status = 201; // created
    broadcast(userId, { type: 'created', payload: bookRow });
  } catch (err) {
    response.body = { message: err.message };
    response.status = 400; // bad request
  }
};

router.post('/', async ctx => await createBookRow(ctx, ctx.request.body, ctx.response));

router.put('/:id', async (ctx) => {
  const bookRow = ctx.request.body;
  const id = ctx.params.id;
  const bookRowId = bookRow._id;
  const response = ctx.response;
  if (bookRowId && bookRowId !== id) {
    response.body = { message: 'Param id and body _id should be the same' };
    response.status = 400; // bad request
    return;
  }
  if (!bookRowId) {
    await createBookRow(ctx, bookRow, response);
  } else {
    const userId = ctx.state.user._id;
    bookRow.userId = userId;
    const updatedCount = await BookRowStore.update({ _id: id }, bookRow);
    if (updatedCount === 1) {
      response.body = bookRow;
      response.status = 200; // ok
      broadcast(userId, { type: 'updated', payload: bookRow });
    } else {
      response.body = { message: 'Resource no longer exists' };
      response.status = 405; // method not allowed
    }
  }
});

router.del('/:id', async (ctx) => {
  const userId = ctx.state.user._id;
  const note = await BookRowStore.findOne({ _id: ctx.params.id });
  if (note && userId !== note.userId) {
    ctx.response.status = 403; // forbidden
  } else {
    await BookRowStore.remove({ _id: ctx.params.id });
    ctx.response.status = 204; // no content
  }
});
