////////////////////////로그인부분////////////////////////
///////////입력한 id,pw를 저장된 id,pw와 비교///////////
const check_id = document.querySelector(".loginId");
const check_password = document.querySelector(".loginPassword");
let logOn = document.querySelector(".logOn")
let memberOn = document.querySelector(".memberOn")
let logOutbtn = document.querySelector(".logOutbtn");
logOutbtn.style.display="none";

// 로컬 > user(JSON) > member(arr)
var user = JSON.parse(localStorage.getItem("user") || "{}");    //로컬에 저장된 유저 JSON
console.log(user)

let member = user.member || [];   //로컬에 저장된 멤버 배열 

function CheckValue(){
    const ID = check_id.value;
    const PASSWORD = check_password.value;

    let currentPassword = ""; //로컬에 저장된 비밀번호
    for(let i=0; i<member.length; i++){
        //1번
        if(member[i].currentId === ID){
            currentPassword = member[i].currentPassword;
            console.log("if문 탔다")
            break;  //찾았으면 for문 그만돌려
        }
    }

    if(PASSWORD === currentPassword && PASSWORD !== null && PASSWORD !== ""){
        localStorage.setItem("ID",ID); //단일 객체 
        alert("로그인 성공");
        logOn.style.display="none"
        memberOn.style.display="none"
        logOutbtn.style.display="block"
    }else{
        alert("ID 와 PASSWORD 를 확인해주세요");
    }
}





////////////////////////소셜로그인부분////////////////////////
function socialing() {
    alert("구현중입니다");
}