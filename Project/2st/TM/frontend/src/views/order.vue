<template>
  <section class="rooms-section spad">
    <div class="breadcrumb-section">
      <div class="container">
        <h2>예약정보</h2>
      </div>
    </div>
    <div class="container" style="padding-bottom: 20%;" v-for="(od ,index) in orders" key="od.OrderNum" v-if="orders.length>0">
      <div class="row" style="padding: 1%">
        <div class="col-lg-4" >
          <div class="contact-text">
            <h3>선택사항 안내</h3>
            <hr>
            <img src="../img/hero/hero-1.jpg" alt="" style="height: 200px; width: 100%">
            <hr>
            <p style="font-weight: bold">예약당시 선택하신 목록 내역입니다.</p>
            <table>
              <tbody>
              <tr>
                <td class="c-o">날짜 </td>
                <td>: {{od.OrderDate.substring(0,10)}}</td>
              </tr>
              <tr>
                <td class="c-o">추가 인원</td>
                <td>: {{od.AddMemberCnt}} 명</td>
              </tr>
              <tr>
                <td class="c-o">펫 수탁 여부 </td>
                <td v-if="od.PetCnt>0">: {{ od.PetCnt }}마리, {{ getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10)) }}박</td>
                <td v-else>: 미수탁함</td>
              </tr>
              <tr>
                <td class="c-o">조식 여부 </td>
                <td v-if="od.Breakfast===1">: 이용함</td>
                <td v-else>: 이용안함</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <!--        <div class="col-lg-1">
                </div>-->
        <div class="col-lg-7 offset-lg-1">
          <div class="row" style="padding: 1%">
            <div class="col-lg-3" >
              <h3>예약정보</h3>
              <hr>
            </div>
            <div class="col-lg-8" >
              <h3>ㅤ</h3>
              <hr>
              <div class="row" style="padding: 1%;">
                <div class="col-lg-6" >
                  <p class="pClass">{{ od.Rating }}/{{ getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10)) }}박 :</p>
                  <p class="pClass">인원 규정 :</p>
                  <p class="pClass">펫 수탁 :</p>
                  <p class="pClass">조식 선택 :</p>
                  <p class="pClass">변상 안내 :</p>
                </div>
                <div class="col-lg-5" >
                  <p class="pClass" style="text-align: right">{{ (od.RoomPrice * getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10))).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right">{{ (od.AddMemberCnt * od.AddPrice * getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10))).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right">{{ (od.PetCnt * 200000 * getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10))).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right" v-if="od.Breakfast===1">{{ ((od.AddMemberCnt+2) * getDateDiff(od.CheckIn.substring(0,10), od.CheckOut.substring(0,10)) * 100000 ).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right" v-else>0원</p>
                  <p class="pClass" style="text-align: right">0원</p>
                </div>
              </div>
              <hr>
              <div class="row" style="padding-top: 3%; text-align: center">
                <div class="col-lg-5" >
                  <p style="font-weight: bold; font-size: 22px">총 결제 금액 :</p>
                </div>
                <div class="col-lg-6" >
                  <p style="font-weight: bold; text-align: right; font-size: 22px" >{{ od.OrderPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                </div>
                <button type="button" class="orderBtns" disabled>예약완료</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container" style="padding-bottom: 20%;" v-else>
      <div class="row" style="padding: 1%">
        <div class="col-lg-4" >
          <div class="contact-text">
            <h3>내역이 없습니다.</h3>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>

import {defineComponent} from "vue";
import axios from 'axios'

export default defineComponent({
  data() {
    return {
      orders : [],
      userData : {},  //로그인 데이터 저장공간
    };
  },
  methods : {
    async init() {  // db 불러와
      console.log("order.vue init()")
      if(sessionStorage.getItem("loginYN")){  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        console.log(this.userData)
      }
      console.log(this.userData.UserId)
      await axios.post('http://localhost:3000/orders',{ userId : this.userData.UserNum })
        .then(res => {
          console.log(res.data)
          this.orders = res.data; //orders에 응답값 박아
        })
        .catch(function(error){
          console.log(error);
        })


    },
    getDateDiff(d1, d2){  //날짜 계산 일수 구하기
      const date1 = new Date(d1);
      const date2 = new Date(d2);

      const diffDate = date1.getTime() - date2.getTime();
      return Math.abs(diffDate / (1000 * 60 * 60 * 24)); // 밀리세컨 * 초 * 분 * 시 = 일
    },
    async deleteBtn(){
      console.log(this.od.OrderNum)
      try{
      await axios.delete('http://localhost:3000/deleteOrder')
       this.od.OrderNum
      
    }catch (err) {
        alert(err);
       }
    }
  },
  mounted() { //init이라고 봐라
    this.init();
  }
});

</script>

<style>

.contact-text table tbody tr td.c-o {
  color: #707079;
  width: 200px;
}

.breadcrumb-section {
  padding-top: 60px;
  padding-bottom: 40px;
}

.pClass{
  padding-top: 5%;
  font-size: 20px;
}

.orderBtns{
  display: block;
  font-size: 14px;
  text-transform: uppercase;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  font-weight: 500;
  background: transparent;
  width: 85%;
  height: 46px;
  margin-top: 10px;
  margin-left: 4%;
}

</style>