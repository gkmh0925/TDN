
//localStorage.clear(); //로컬스토리지 초기화
let user = JSON.parse(localStorage.getItem("user") || "{}");  // user 키값이 있으면 가져오고, 없으면 빈 오브젝트({})
// 위의 코드 설명 : user JSON 데이터 받아오기 / 
// 없으면 빈 배열 -> JSON.parse로 STRING 형태 => JSON 형태로 변환
let member = user.member || []; // user(JSON) > member(arr) //초기진입 : []

function handleSubmitId(){  //회원가입 버튼 온클릭 
    let memberId = document.querySelector(".placehold-text");
    let memberPassword = document.querySelector(".userpw");
    alert("회원가입이 완료되었습니다");


    let param = {}; //임이의 선언 
    param.currentId = memberId.value;
    param.currentPassword = memberPassword.value; // 1)

    // let parma2 = {   // 위랑 동일
    //     currentId : memberId.value,
    //     currentPassword : memberPassword.value
    // }
    // console.log(parma2)

    member.push(param)// member 배열에 새로운 데이터 push 2)

    user.member = member;   //user (JSON) 에 추가된 member 넣기
    localStorage.setItem("user", JSON.stringify(user)); //로컬 스토리지에 추가된 user(JSON) 넣기
    // localStorage.setItem("user", user);
}
/*
    fuction () => 함수 인자(파라메터) 
    {} => 얘는 오브젝트구나 // JSON(오브젝트)를 나타내는 틀?
    [] => 얘는 배열이구나 // 배열 (Arr) 를 나타내는 틀
    1)    
    param = {
        currentId : "sd".
        currentPassword : "123"
    }

    param = "{
        currentId : "sd".
        currentPassword : "123"
    }"

    2)
    member = [{
        currentId : "sd".
        currentPassword : "123"
    }]

    3)user = {
        member : [{
        currentId : "sd".
        currentPassword : "123"
    }]
    }

    localStorage = {user : {
        member : [{
        currentId : "sd".
        currentPassword : "123"
    }]}
        
    }

*/
// localStorage.getItem("user"); //user 키값의 해당하는 데이터를 가져옴.
// localStorage.setItem("user",JSON.stringify(user));

// JavaScript 비밀번호 유효성 검사 로직
const inputElem = document.querySelector('.userpw'); // 입력란 엘리먼트 가져오기
const lowercaseElem = document.querySelector('.lowercase'); // 소문자 요소 가져오기
const uppercaseElem = document.querySelector('.uppercase'); // 대문자 요소 가져오기
const numberElem = document.querySelector('.number'); // 숫자 요소 가져오기
const specialElem = document.querySelector('.special'); // 특수문자 요소 가져오기
const characterLengthElem = document.querySelector('.character-length'); // 길이 요소 가져오기

//비밀번호 충족했는지 확인
const corrElem = document.querySelector(".corr");

//포함 여부 확인
const isValidLowercase = (password) => {
    return password.search(/[a-z]/g) >= 0; // 소문자 포함 여부 확인/정규표현식? g는 전역으로 본다
}
const isValidUppercase = (password) => {
    return  password.search(/[A-Z]/g) >= 0; // 대문자 포함 여부 확인
}
const isValidNumber = (password) => {
    return  password.search(/[0-9]/g) >= 0; // 숫자 포함 여부 확인
}
const isValidSpecial = (password) => {
    return password.search(/[~!@#$%^&*()_+|<>?:{}`]/gi) >= 0; // 특수문자 포함 여부 확인
}
const isValidCaracterLength = (password) => {
    return password.length >= 8 // 최소 8자 이상 여부 확인
}

// 비밀번호 입력 시 유효성 검사 상태 업데이트
inputElem.addEventListener('input', (e) => {
    const password = e.target.value;
    isValidLowercase(password) ? lowercaseElem.classList.add('active') : lowercaseElem.classList.remove('active'); // 소문자 유효성 표시 여부 업데이트
    isValidUppercase(password) ? uppercaseElem.classList.add('active') : uppercaseElem.classList.remove('active'); // 대문자 유효성 표시 여부 업데이트
    isValidNumber(password) ? numberElem.classList.add('active') : numberElem.classList.remove('active'); // 숫자 유효성 표시 여부 업데이트
    isValidSpecial(password) ? specialElem.classList.add('active') : specialElem.classList.remove('active'); // 특수문자 유효성 표시 여부 업데이트
    isValidCaracterLength(password) ? characterLengthElem.classList.add('active') : characterLengthElem.classList.remove('active'); // 길이 유효성 표시 여부 업데이트
    
    (isValidLowercase(password) === true && 
        isValidUppercase(password) === true &&
        isValidNumber(password) === true &&
        isValidSpecial(password) === true &&
        isValidCaracterLength(password) === true
        )? corrElem.classList.add('active') : corrElem.classList.remove('active');

    })