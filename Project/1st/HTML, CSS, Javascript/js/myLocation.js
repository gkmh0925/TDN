
////////강사님 알려주신 부분////////////
////////스토리지에 저장된 아이디 불러오는 법////////
// function saveName() {
//     var name = document.getElementById("nameInput").value;
//     localStorage.setItem("savedName", name);
//     loadName(); // 추가: 이름을 저장한 후에도 즉시 화면에 반영되도록 loadName() 호출
// }

// function loadName() {
//     var savedName = localStorage.getItem("savedName");
//     document.getElementById("nameOutput").innerHTML = savedName ? "Hello, " + savedName : "No name saved.";
// }


var ID = localStorage.getItem("ID") || "Guest";
console.log(ID)
document.querySelector(".nameOutput").innerHTML = ID;