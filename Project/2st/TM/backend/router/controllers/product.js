/* Model(DB호출) 과 화면(VUE)의 중계페이지 */

import db from "../../config/database.js"; // database에 const로 선언해놓은 db가져올거임+_+!


export const PostOrders = (req, res) => {  //req, res 형태로 나오는걸 promise "약속" 형태라 한다 async await 요놈들도 promise 형태로 나온다는데
    // Post
    //ORDER, ROOM LEFT JOIN : 사용자별 예약내역 조회
    let query = "SELECT * FROM orders left JOIN room ON orders.ROOM_RoomNum = room.RoomNum WHERE USER_UserNum=?";
    let data = [req.body.userId];
    db.query(query, data, function (err, result){ // database.js에서 연동한 DB 데이터베이스에 쿼리 던질거야.
        if(err){ //쿼리 던졌는데 에러 떳다!
            console.log("Error : " + err); //에러내용 뭔데
            res.send(err);   //화면(VUE) 에 send 할거야 err를
        }else{
            res.json(result);  //JSON 형태로 보낼거야, index.js에 express.JSON 사용한다 했지? 여기서 쓸라고..
        }
    });
}

export const deleteOrders = (req, res) => {
    let query = "DELETE FROM orders WHERE OrderNum = ?" 
    let data = [req.body]
    console.log(data);
    db.query(query, data, function (err, results) {             
        if(err) {
            console.log(err);
            res(err);
        } else {
           res.json(results);
           console.log(results);
        }
    });   
}




export const PostUserId = (req, res) => {  //req, res 형태로 나오는걸 promise "약속" 형태라 한다 async await 요놈들도 promise 형태로 나온다는데
    // Post
    // 사용자 아이디 중복 확인
    let query = "SELECT * FROM user WHERE UserId= ? " ;
    let data = [req.body.UserId];  
    // console.log(data);
    db.query(query, data, function (err, result){ // database.js에서 연동한 DB 데이터베이스에 쿼리 던질거야.
        if(err){ //쿼리 던졌는데 에러 떳다!
            console.log("Error : " + err); //에러내용 뭔데
            res.send(err);   //화면(VUE) 에 send 할거야 err를
        }else{
            res.json(result);  //JSON 형태로 보낼거야, index.js에 express.JSON 사용한다 했지? 여기서 쓸라고..
        }
    });
}
export const UserId = (req, res) => {  
    let query = "SELECT * FROM user WHERE UserId=? AND UserPw=?" ;
    let data =[req.body.UserId, req.body.UserPw];  
    console.log(data);
    db.query(query, data, function (err, result){ 
        if(err){
            console.log("Error : " + err); 
            res.send(err); 
        }else{
            res.json(result);  
            console.log(result);
        }
    });
}

export const findId = (req, res) => {  
    let query = "SELECT * FROM user WHERE UserName=? AND UserPhNum1=? AND UserPhNum2=? AND UserPhNum3=?" ;
    let data =[req.body.UserName, req.body.UserPhNum1, req.body.UserPhNum2, req.body.UserPhNum3,];  
    console.log(data);
    db.query(query, data, function (err, result){ 
        if(err){
            console.log("Error : " + err); 
            res.send(err); 
        }else{
            res.json(result);  
            console.log(result);
        }
    });
}
export const findPw = (req, res) => {  
    let query = "SELECT * FROM user WHERE UserId=? AND UserPhNum1=? AND UserPhNum2=? AND UserPhNum3=?" ;
    let data =[req.body.UserId, req.body.UserPhNum1, req.body.UserPhNum2, req.body.UserPhNum3,];  
    console.log(data);
    db.query(query, data, function (err, result){ 
        if(err){
            console.log("Error : " + err); 
            res.send(err); 
        }else{
            res.json(result);  
            console.log(result);
        }
    });
}

export const joinId = (req,res) => {
    let query = "INSERT INTO user SET ?";
    let data = [req.body]
    db.query(query, data ,function(err, results){             
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(results);
        }
    });   
}

export const putPassword = (req,res) => {
    let query = "UPDATE user SET Userpw = ? WHERE UserId = ?";
    let data = [req.body.UserPw, req.body.UserId]
    db.query(query,data, function (err, results){             
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(results);
        }
    });   
}

//방디테일
export const showDetails = (req, res) => {
    let query = "SELECT * FROM room where RoomNum=?";
    // let query = "SELECT * FROM room";
    let data = [req.body.RoomNum]
    db.query(query, data, (err,result) => {
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(result);
        }
    })
}

// Room List
export const showRooms = (req, res) => {
    let query = "SELECT * FROM room";
    db.query(query,(err,result) => {
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(result);
        }
    })
}

//User
export const showUser = (req, res) => {
    let query = "SELECT * FROM user";
    db.query(query, (err,result) => {
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(result);
        }
    })
}

//admin
export const admin = (req, res) => {
    let query = "SELECT orders.CheckIn, orders.USER_UserNum, orders.OrderPrice, orders.OrderDate, room.Rating, orders.AddMemberCnt, orders.Breakfast, user.UserName  FROM (orders left JOIN room ON orders.ROOM_RoomNum = room.RoomNum ), user WHERE orders.CheckIn>? AND orders.CheckIn<? AND (room.Rating=? OR room.Rating=? OR room.Rating=?) AND orders.AddMemberCnt<? AND Breakfast=? AND user.UserNum=orders.USER_UserNum";
    let data = [req.body.CheckIn_1 ,req.body.CheckIn_2 ,req.body.Rating_1 ,req.body.Rating_2 ,req.body.Rating_3 ,req.body.AddMemberCnt ,req.body.Breakfast];
    db.query(query, data, (err,result) => {
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            console.log(result)
            res.json(result);
        }
    })
}

export const reserve = (req,res) => {
    let query = "INSERT INTO orders SET ?";
    let data = [req.body]
    db.query(query, data ,function(err, results){
        if(err) {
            console.log(err);
            res.send(err);
        } else {
            res.json(results);
        }
    });
}


