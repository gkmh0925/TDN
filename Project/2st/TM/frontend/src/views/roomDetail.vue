<template>
  <section class="rooms-section spad">
    <div class="rooms-section spad">
      <div class="breadcrumb-section">
        <div class="container">
          <h2>상세</h2>
        </div>
      </div>
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="room-details-item">
              <img src="../img/room/room-details.jpg" alt="show me!">
              <div class="rd-text">
                <div class="rd-title">
                  <h3>{{ dt.Rating }} ( {{ dt.View }} )</h3>
                  <br>
                  <br>
                  <br>
                  <hr>
                </div>
                <table>
                  <tbody>
                  <tr>
                    <td class="rd-o"><span style="font-weight: bold">- 체크인 </span>{{ dt.CheckinTime }} / <span style="font-weight: bold">체크아웃 </span>{{ dt.CheckoutTime }}</td>
                  </tr>
                  <tr>
                    <td class="rd-o"><span style="font-weight: bold">- 기준인원 </span>{{ dt.BasicPersonnel }}명 (최대 {{ dt.MaxPersonnel }}명)</td>
                  </tr>
                  <tr>
                    <td class="rd-o"><span style="font-weight: bold">- 객실면적 </span>약 {{ dt.Size }}㎡</td>
                  </tr>
                  </tbody>
                </table>
                <hr>
                <div class="survice">
                  <p style="font-weight: bold; margin-top: 30px">- 서비스</p>
                  <table>
                    <tbody>
                    <tr>
                      <td>{{ dt.Bed }}</td>
                      <td v-if="dt.Bath == 1">욕조</td>
                      <td v-if="dt.Terrace == 1">테라스</td>
                      <td v-if="dt.Spa == 1">스파</td>
                      <td v-if="dt.Pool == 1">독립수영장</td>
                      <td v-if="dt.Rooftop == 1">루프탑</td>
                    </tr>
                    </tbody>
                  </table>
                  <hr>
                  <p style="font-weight: bold; margin-top: 30px">* Nonamed의 이야기</p>
                  <p class="explain">
                    &nbsp;{{ dt.Explain }}
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="room-booking">
              <h3>예약 옵션 선택</h3>
              <form action="#">
                <div class="check-date">
                  <label for="check-in">체크인</label>
                  <input type="text" class="date-input" id="check-in" @click="modalOpen()" :value="getFormatDate(inputDate.start)" style="font-weight: bold"><br>

                  <!--calender modal-->
                  <div class="white-bg" v-if="modal">
                    <div class="modal-name" style="text-align: center">
                      <p>체크인/체크아웃 날짜를 선택하세요</p>
                      <div>
                        <VDatePicker v-model.range="inputDate" mode="date" :disabled-dates="disabledDates"/>
                      </div>
                      <div class="buttonDiv">
                        <button type="button" class="checkBtn" @click="modalClose()">확인</button>
                      </div>
                    </div>
                  </div>

                  <label for="check-out">체크아웃</label>
                  <input type="text" class="date-input" id="check-out" @click="modalOpen()" :value="getFormatDate(inputDate.end)" style="font-weight: bold">
                </div>
                <div class="select-option" style="margin-bottom: 30px">
                  <label for="guest">인원</label>
                  <select @change="inputPerson" id="guest">
                    <option id="gueCnt" :value="index+1" v-for="(max, index) in dt.MaxPersonnel">
                      {{ index+1 }}명<span v-if="index+1>dt.BasicPersonnel">(+{{ dt.AddPrice * (index-dt.BasicPersonnel+1) }}원 추가)</span>
                    </option>
                  </select>
                </div>
                <div class="row">
                  <div class="check-option col-8">
                    <label for="meal">조식(포함의 경우 체크)</label><br>
                    <label for="pet">펫 위탁(위탁 경우 체크)</label><br>
                    <label for="countPet">위탁 마리 수</label>
                  </div>
                  <div class="ckbox col-4">
                    <input type="checkbox" id="meal"><br>
                    <input type="checkbox" id="pet" style="margin-top: 13px" @click="toggleTextbox(this)"><br>
                    <input @change="inputPet" type="text" id="countPet" style="width: 35px; height: 25px; margin-top: 8px; font-size: 15px; text-align: right" disabled>마리
                  </div>
                </div>
                <button class="reservBtn" type="button" style="font-size: 20px; font-weight: bold" @click="goReservation()">예약하기</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import {defineComponent, ref} from 'vue';
import axios from 'axios';
import router from "@/router";

export default defineComponent({
  data(){
    return {
      userData : {},  //로그인 데이터 저장공간
      modal : ref(false),
      dt : [],

      //시작, 끝 날짜 저장
      inputDate : ref({
        start: new Date(),
        end : new Date(),
      }),

      //비활성 저장
      disabledDates : ref(
          [{ start: new Date(2024, 0, 7), end: new Date(2024, 0, 7)}]
      ),
      inputPetCnt : 0,
      inputPersonCnt : 0,

      //rooms의 stringfy를 전달해서 parsing해 화면에 표현(민현추가)
      response : {},
    }
  },

  methods : {
    //yyyy.mm.dd 포멧으로 변경
    getFormatDate(date) {
      const YYYY = String(date.getFullYear())
      const MM = String((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1): "0" + (date.getMonth() + 1))
      const dd = String(date.getDate() >= 10 ? date.getDate() : "0" + date.getDate())
      return YYYY + "-" + MM + "-" + dd
    },

    //modal 열기
    modalOpen(){
      this.modal = !this.modal;
      // console.log(this.modal)
    },

    //modal 닫기
    modalClose(){
      this.modal = !this.modal;
    },

    // db 불러와
    async init(){
      this.response = JSON.parse(history.state.dataObj);
      // console.log(this.response.RoomNum)
      if(sessionStorage.getItem("loginYN")){  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        // console.log(this.userData)
      }
      await axios.post('http://localhost:3000/roomDetail',{ RoomNum : this.response.RoomNum })
          .then(res => {
            this.dt = res.data[0]; //orders에 응답값 박아
            // console.log(this.dt);
          })
          .catch(function(error){
            // console.log(error);
          });
    },

    // 펫위탁 checkbox 선택 시 마리 수 입력하는 input박스 활성화
    toggleTextbox(checkbox) {
      const textbox_elem = document.querySelector('#countPet');
      textbox_elem.disabled = document.querySelector('#pet').checked ? false : true;
      if(textbox_elem.disabled) {
        textbox_elem.value = null;
      } else {
        textbox_elem.focus();
      }
    },

    // 입력된 마리수 inputPetCnt에 저장
    inputPet(e){
      return this.inputPetCnt = e.target.value
    },

    // 선택 된 인원 수 inputPersonCnt에 저장
    inputPerson(e){
      return this.inputPersonCnt = e.target.value
    },

    // 예약하러 갑시다
    goReservation() {
      let options = {};
      options = this.dt;
      options.tfMeal = 0;
      options.tfPet = 0;
      options.petCnt = this.inputPetCnt;
      options.CheckIn = this.inputDate.start;
      options.CheckOut = this.inputDate.end;
      options.AddMemberCnt = this.inputPersonCnt-2;
      // let options = {
      //   tfMeal : 0, // 조식선택여부 0은 선택x
      //   tfPet : 0,  // 펫위탁선택여부 0은 선택x
      //   petCnt : this.inputPetCnt,  // 몇마리 맡김?
      //   RoomNum : this.dt.RoomNum, // 몇호실?
      //   Rating : this.dt.Rating,  // 방등급뭐임?
      //   CheckIn : this.inputDate.start,  // 언제 입실?
      //   CheckOut : this.inputDate.end, // 언제 퇴실?
      //   AddMemberCnt : this.inputPersonCnt-2, // 숙박인원수
      // }

      if (document.querySelector("#pet").checked){
        options.tfPet = 1;
      }

      if (document.querySelector("#meal").checked){
        options.tfMeal = 1;
      }
      if(sessionStorage.getItem("loginYN")){
        router.push({ // 자 예약페이지로 넘어가자
          name: "reservation",
          state:{
            options : JSON.stringify(options),
          }
        })
      }else{
      if(confirm("로그인이 필요합니다.\n로그인 하시겠습니까?")){
          router.push({ // 자 예약페이지로 넘어가자
            name: "login",
            state:{
            }
          })
        }
      }
    },
  },
  mounted() { //init이라고 봐라
    this.init();
  }
});

</script>


<style scoped>

table {
  border-collapse: collapse;
  width: 300px;
}

.breadcrumb-section {
  padding-top: 60px;
  padding-bottom: 40px;
}

.rd-o {
  padding-left: 3%;
}


.check-option {
  font-size: 16px;
  color: #707079;
  padding-right: 0;
  padding-left: 5%;
  text-align: left;
}

.ckbox {
  text-align: right;
  margin-top: 0.5%;
  margin-bottom: 0.5%;
  padding-bottom: 0.5%;
}

.select-option, .check-date, .check-option {
  font-weight: bold;
}

.room-booking form .check-date input {
  width: 100%;
  height: 40px;
  border: 1px solid #ebebeb;
  border-radius: 2px;
  font-size: 16px;
  color: #19191a;
  text-transform: uppercase;
  padding-left: 20px;
}

.col-8 {
  position: relative;
  width: 100%;
}

.col-4 {
  -ms-flex: 0 0 25%;
  flex: 0 0 25%;
  max-width: 25%;
  padding-right: 0;
}

.reservBtn {
  display: block;
  font-size: 14px;
  text-transform: uppercase;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  font-weight: 500;
  background: transparent;
  width: 100%;
  height: 46px;
  margin-top: 30px;
}

.modal-name{
  text-align: center;
  position: fixed;
  z-index: 9;
}
.modal-name > p{
  font-size: 15px;
  margin: 4%;
}

.white-bg {
  background: white;
  border-radius: 8px;
  z-index: 10;
  width: 290px;
  height: 400px;
  position: fixed;
  padding-top: 5px;
  align-items : center;
}

.checkBtn {
  width: 60px;
  height: 30px;
  font-size: 15px;
  background-color: rgb(77, 76, 76);
  color: #d9d6d6;
  border:none;
  border-radius:20px;
  text-align: center;
}

.date-input{
  margin-bottom: 4%;
}

.buttonDiv {
  margin-left: 38%;
  margin-top: 0;
}

.room-details-item .rd-text p {
  color: #19191A;
  margin-left: 1%;
}

.survice > table {
  width: auto;
  margin-left: 2%;
}

.survice > table > tbody > tr > td {
  text-align: center;
  padding-right: 15px;
  padding-left: 15px;
}

.explain {
  color: #707079;
  padding-left: 2%;
  padding-right: 3%
}

</style>