// DB와 접속통로 열어주기

import mysql from  "mysql2";

const db = mysql.createPool({
    host: '3.35.129.159',
    user: 'son',
    password: '1234',
    database: 'hotel'
//     port번호가 3306이 아니면 port: '3308' 이런식으로 포트번호 써줘야함
});

export default db;

