/* VUE에서 axios 호출 받으면 model(DB connection : product.js)에 요청하는 페이지. */

import Express from "express"; //express 사용할거야.
import {joinId, PostOrders, putPassword , PostUserId, showDetails, showRooms, showUser, admin, UserId, findId, findPw, reserve } from "./controllers/product.js";

const router = Express.Router(); //express의 router 사용하기위함.

router.post('/orders', PostOrders);
router.post('/finds', PostUserId);
router.post('/login',UserId);
router.post('/find',findId);
router.post('/findPw',findPw);
router.post('/create', joinId);
router.put('/putPassword', putPassword);
router.post('/roomDetail', showDetails);
router.get('/rooms', showRooms);
router.get('/user', showUser);
router.post('/admin', admin);
router.post('/reserve', reserve);

export default router;  // 아니 그래서 Router랑 router랑 뭔차인데... 왜 어떤건 대문자고 어떤건 소문자임