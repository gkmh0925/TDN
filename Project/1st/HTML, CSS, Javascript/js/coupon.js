/////////////////////////쿠폰코딩원본//////////////////////////////

///// onclick 작성 /////
function showAlert() {
  alert('쿠폰이 다운로드 되었습니다.');
}

///// getElementById 작성 /////
document.getElementById('coupon_done').addEventListener('click',function(){
  alert('*이미 다운받으신 쿠폰입니다.*');
})

///// getElementById 작성 /////
document.querySelector('#coupon_btn').addEventListener('click',function(){
  alert('모든 쿠폰이 다운로드 되었습니다.');
})



////////쿠폰alert 보충부분////////////
document.getElementById('coupon1').addEventListener('click',function(){
  alert('쿠폰이 다운로드 되었습니다.!');
})
document.getElementById('coupon2').addEventListener('click',function(){
  alert('쿠폰이 다운로드 되었습니다.!');
})
document.getElementById('coupon3').addEventListener('click',function(){
  alert('쿠폰이 다운로드 되었습니다.!');
})
document.getElementById('coupon4').addEventListener('click',function(){
  alert('쿠폰이 다운로드 되었습니다.!');
})




//쿠폰.html알림뜨는거
// function showAlert() {
//     alert('쿠폰이 다운로드 되었습니다.');
//   }

//   function showAlertDone() {
//     alert('이미 다운받은 쿠폰입니다.ㅠㅠ');
//   }