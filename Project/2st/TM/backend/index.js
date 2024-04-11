/* Backend 서버 메인JS 라고 보면 됨 */

/*
* ------- backend install 목록 -------
* npm init -y
*   -> package.json 기본양식 만들어준다.
*   -> "type" : "module" 추가 하자
* npm install express mysql2 cors
*   -> express 설치했다. : node 서버 열기위함.
*   -> mysql2 설치했다. : database 관리 하기위함
*   -> cors 설치했다. : 모든 도메인에서 제한 없이 해당 서버에 요청을 보내고 응답을 받을 수 있음 (안깔면 로컬에서 3000번 express로 통신(axios) 못해)
* npm install -g nodemon
*   -> nodemon 설치했다. : 파일이 수정된다면 서버 재시작 해야하지만, 요놈깔면 재시작 안해도 반영된다. (nodemon server.js 로 서버열자!)
* */

import express from "express"; //express install 한거 써야겠지?
import cors from "cors"; //cors install 한거 써야겠지?
import Router from "./router/router.js";    // router랑 헷갈리지 말라고 Router로...

import path from 'path';
const __dirname = path.resolve(); // 안해주면 아래 __dirname 부분 에러난다.  (CommonJS에서 사용하던 __dirname 변수가 ES 모듈에서는 없기 때문에 => type : moduile)

const app = express(); //그냥 express 쓰겠다는거야 이름만 app이지..

app.use(express.json()); //express 사용할 때 json 포멧 사용할거야
app.use(cors()); //난 모든 도메인에서 요청 받을거야.
app.use(Router); //내가 만들어놓은 router.js 사용 할거야
app.listen(3000, () => console.log('Server running at http://localhost:3000')); // 5000번으로 express서버 열거야. 열리면 아래 콘솔 찍어줘


/*frontend에서 build한 dist디렉토리 바라볼수있는 구문*/
app.use('/', express.static( path.join(__dirname, '/dist') )); //이 부분이 없으면 아래코드에서 dist/index.html을 로드하지 못해
app.get('/', (req, res)=>{ //기본 경로 '/'을 통해 빌드된 dist/index.html 파일을 로드시킬거야.
    res.sendFile(path.join(__dirname, '/dist/index.html'));
})

/*새로고침시 오류나는거 해결 코드*/ /*이해하려 하지 말구 그냥 첨부만 해놓자.*/
app.get('/*', function(req, res) {
    res.sendFile(path.join(__dirname, '/dist/index.html'), function(err) {
        if (err) {
            res.status(500).send(err)
        }
    })
})