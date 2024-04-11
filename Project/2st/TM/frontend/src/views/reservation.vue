<template>
  <section class="rooms-section spad">
    <div class="breadcrumb-section">
      <div class="container">
        <h2>예약정보</h2>
      </div>
    </div>
    <div class="container" style="padding-bottom: 20%;">
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
                <td>: {{od.today}}</td>
              </tr>
              <tr>
                <td class="c-o">추가 인원</td>
                <td>: {{od.AddMemberCnt}} 명</td>
              </tr>
              <tr>
                <td class="c-o">펫 수탁 여부 </td>
                <td v-if="od.tfPet>0">: {{ od.petCnt }}마리, {{ od.getDateD }}박</td>
                <td v-else>: 미수탁함</td>
              </tr>
              <tr>
                <td class="c-o">조식 여부 </td>
                <td v-if="od.tfMeal===1">: 이용함</td>
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
                  <p class="pClass">{{ od.Rating }}/{{ od.getDateD }}박 :</p>
                  <p class="pClass">인원 규정 :</p>
                  <p class="pClass">펫 수탁 :</p>
                  <p class="pClass">조식 선택 :</p>
                  <p class="pClass">변상 안내 :</p>
                </div>
                <div class="col-lg-5" >
                  <p class="pClass" style="text-align: right">{{ (od.RoomPrice * od.getDateD).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right">{{ (od.AddMemberCnt * od.AddPrice * od.getDateD).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right">{{ (od.petCnt * 200000 * od.getDateD).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
                  <p class="pClass" style="text-align: right" v-if="od.tfMeal===1">{{ ((od.AddMemberCnt+2) * od.getDateD * 100000 ).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</p>
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
                  <p style="font-weight: bold; text-align: right; font-size: 22px">{{ od.OrderPricee }}원</p>
                </div>
                <button type="button" class="orderBtns" @click="showMeTheMoney()">결제</button>
              </div>
            </div>
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
      userData : {},
      od : {},
    };
  },
  methods : {
    async init() {  // db 불러와
      this.od = JSON.parse(history.state.options);
      let newDate = new Date();
      this.od.today = this.getFormatDate(newDate);
      console.log(this.od.CheckOut.replaceAll("\"","").substring(0,10));
      this.od.getDateD = this.getDateDiff(this.od.CheckIn.replaceAll("\"","").substring(0,10), this.od.CheckOut.replaceAll("\"","").substring(0,10))
      if(sessionStorage.getItem("loginYN")){  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        console.log(this.userData)
      }

      if(this.od.tfMeal === 1){
        console.log(1);
        this.od.OrderPricee = ((this.od.RoomPrice * this.od.getDateD) + (this.od.AddMemberCnt * this.od.AddPrice * this.od.getDateD) + (this.od.petCnt * 200000 * this.od.getDateD) + (this.od.getDateD * 100000 * (this.od.AddMemberCnt+2))).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this.od.OrderPrice = (this.od.RoomPrice * this.od.getDateD) + (this.od.AddMemberCnt * this.od.AddPrice * this.od.getDateD) + (this.od.petCnt * 200000 * this.od.getDateD) + (this.od.getDateD * 100000 * (this.od.AddMemberCnt+2));
      }else{
        console.log(0);
        this.od.OrderPricee = ((this.od.RoomPrice * this.od.getDateD) + (this.od.AddMemberCnt * this.od.AddPrice * this.od.getDateD) + (this.od.petCnt * 200000 * this.od.getDateD)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this.od.OrderPrice = (this.od.RoomPrice * this.od.getDateD) + (this.od.AddMemberCnt * this.od.AddPrice * this.od.getDateD) + (this.od.petCnt * 200000 * this.od.getDateD);
      }

      // console.log((this.od.RoomPrice * this.od.getDateD))
      // console.log((this.od.AddMemberCnt * this.od.AddPrice * this.od.getDateD))
      // console.log((this.od.petCnt * 200000 * this.od.getDateD))
      // console.log((this.od.getDateD * 100000 * (this.od.AddMemberCnt+2)))

    },
    getDateDiff(d1, d2){  //날짜 계산 일수 구하기
      const date1 = new Date(d1);
      const date2 = new Date(d2);

      const diffDate = date1.getTime() - date2.getTime();
      return Math.abs(diffDate / (1000 * 60 * 60 * 24)); // 밀리세컨 * 초 * 분 * 시 = 일
    },
    async showMeTheMoney(){
      alert("예약이 완료되었습니다.");

        try {
          let data = {
            OrderDate: this.od.today,
            AddMemberCnt: this.od.AddMemberCnt,
            PetCnt : this.od.petCnt,
            Breakfast : this.od.tfMeal,
            CheckIn : this.od.CheckIn.replaceAll("\"","").substring(0,10),
            CheckOut: this.od.CheckOut.replaceAll("\"","").substring(0,10),
            ROOM_RoomNum : this.od.RoomNum,
            USER_UserNum : this.userData.UserNum,
            OrderPrice : this.od.OrderPrice,
          }
          await axios.post('http://localhost:3000/reserve', data);
          this.$router.push("/order");

            console.log(JSON.stringify(data))
        } catch (err) {
          console.log(err)
          console.log(" 뭐가문제야say something");
        }
    },
    getFormatDate(date) { //yyyy.mm.dd 포멧으로 변경
      const YYYY = String(date.getFullYear())
      const MM = String((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1): "0" + (date.getMonth() + 1))
      const dd = String(date.getDate() >= 10 ? date.getDate() : "0" + date.getDate())
      return YYYY + "-" + MM + "-" + dd
    },

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