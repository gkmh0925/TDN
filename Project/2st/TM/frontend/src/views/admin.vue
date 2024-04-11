<template>
  <transition name="fade">
    <div class ="black-bg" v-if="modal">
      <div class="white-bg">
        <div class="modal-close">
          <a herf="#" @click="modalOpen()" class="headerText">x</a>
        </div>
        <div class="modal-name" style="text-align: center">
          <p>조회하실 입실일을 선택해 주세요.</p>
          <br>
          <div>
            <VDatePicker v-model.range="inputDate" mode="date" style="width: 100%;" />
          </div>
          <div class="divOOO">
            <div class="inputDiv" style="font-weight: bold; text-align: right">
              시작 : <input type="text" class="inputText" disabled :value="getFormatDate(inputDate.start)" style="font-weight: bold">
              <br>
              <br>
              끝 : <input type="text" class="inputText" disabled :value="getFormatDate(inputDate.end)" style="font-weight: bold">
              <br>
            </div>
            <div class="buttonDiv">
             <button type="button" class="inputBtn" @click="goRooms(getFormatDate(inputDate.start),getFormatDate(inputDate.end))">선택</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
  <div class="container">
    <nav class="mainmenu admin_mainmenu">
        <span class="admin_sapn admin_active" id="reservation"><a class="headerText admin_headerText " @click="change('reservation')">예약정보</a></span><!--class="active"-->
        <span class="admin_sapn" id="user"><a class="headerText admin_headerText" @click="change('user')">고객정보</a></span>
    </nav>
    <div class="row" id="reservation_row" style="display: none">
      <div class="col-xs-12">
        <div class="reservation_div">
          <div class="row">

            <div class="col-4 admin-div">
              <div class="row">
                <div class="col-4 colcol">
                  <p class="admin_p">입실일</p>
                </div>
                <div class="col-8 colcol">
                  <input type="text" placeholder="" class="checkInput" @click="modalOpen()" :value="getFormatDate(inputDate.start) + ' ~ ' + getFormatDate(inputDate.end)">
                </div>
              </div>
              <div class="row">
                <div class="col-4 colcol">
                  <p class="admin_p">등급</p>
                </div>
                <div class="col-8 colcol">
                  <p><input type="checkbox" class="admin_check" name="rating" value="standard" id="superior" checked> <label for="superior">Superior</label></p>
                  <p><input type="checkbox" class="admin_check" name="rating" value="deluxe" id="deluxe" checked> <label for="deluxe">Deluxe</label></p>
                  <p><input type="checkbox" class="admin_check" name="rating" value="suite" id="suite" checked> <label for="suite">Suite</label></p>
                </div>
              </div>
            </div>

            <div class="col-4 admin-div">
              <div class="row">
                <div class="col-6 colcol">
                  <p class="admin_p"><label for="pet">펫 수탁 이용</label></p>
                </div>
                <div class="col-6 colcol">
                  <input type="checkbox" class="admin_check" name="pet" id="pet">
                </div>
              </div>
              <div class="row">
                <div class="col-6 colcol">
                  <p class="admin_p"><label for="checkIn">체크인</label></p>
                </div>
                <div class="col-6 colcol">
                  <input type="checkbox" class="admin_check" name="checkIn" id="checkIn">
                </div>
              </div>
              <div class="row">
                <div class="col-6 colcol">
                  <p class="admin_p"><label for="breakfast">조식 이용</label></p>
                </div>
                <div class="col-6 colcol">
                  <input type="checkbox" class="admin_check" name="breakfast" id="breakfast">
                </div>
              </div>
            </div>

            <div class="col-4 admin-div">
              <div class="row">
                <div class="col-6 colcol">
                  <p class="admin_p"><label for="addMemberCnt">추가 인원 유무</label></p>
                </div>
                <div class="col-6 colcol">
                  <input type="checkbox" class="admin_check" name="addMemberCnt" id="addMemberCnt">
                </div>
              </div>
              <div class="row">
                <div class="col-6 colcol">
                  <p class="admin_p"><label for="checkOut">체크아웃</label></p>
                </div>
                <div class="col-6 colcol">
                  <input type="checkbox" class="admin_check" name="checkOut" id="checkOut">
                </div>
              </div>
              <div class="row">
                <div class="col-1 colcol">
                </div>
                <div class="col-11 colcol">
                  <input type="button" value="조회하기" class="admin_btn" @click="makeData()">
                </div>
              </div>
            </div>
          </div>
        </div>
        <br>
        <br>
        <table class="table table-bordered table-hover dt-responsive" v-if="showData">
          <thead>
          <tr>
            <th>입실일</th>
            <th>예약자</th>
            <th>결제금액</th>
            <th>결제일</th>
            <th>등급</th>
            <th>추가인원</th>
            <th>조식</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="a in admin" class="admin_user_tr">
            <td>{{ a.CheckIn.substring(0,10) }}</td>
            <td>{{ a.UserName }}</td>
            <td>{{ a.OrderPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원</td>
            <td>{{ a.OrderDate.substring(0,10) }}</td>
            <td>{{ a.Rating }}</td>
            <td>{{ a.AddMemberCnt }}</td>
            <td v-if="a.Breakfast===1">이용함</td>
            <td v-else>이용안함</td>
          </tr>

          </tbody>

        </table>
      </div>
    </div>



    <div class="row" id="user_row" style="display: none">
      <div class="col-xs-12">
        <table class="table table-bordered table-hover dt-responsive">
          <thead>
          <tr>
            <th>번호</th>
            <th>회원ID</th>
            <th>이름</th>
            <th>연락처</th>
            <th>E-mail</th>
            <th>생년월일</th>
            <th>가입일자</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="u in user" key="user.UserNum" class="admin_user_tr">
            <td>{{ u.UserNum }}</td>
            <td>{{ u.UserId }}</td>
            <td>{{ u.UserName }}</td>
            <td>{{ u.UserPhNum1 }}-{{ u.UserPhNum2 }}-{{ u.UserPhNum3 }}</td>
            <td>{{ u.UserEmail }}</td>
            <td>{{ u.UserSsn1 }}</td>
            <td>{{ u.JoinDate.substring(0,10) }}</td>
          </tr>

          </tbody>

        </table>
      </div>
    </div>
  </div>
</template>
<script>
import {defineComponent, ref} from "vue";
import axios from "axios";

export default defineComponent({
  data() {
    return {
      user : [],
      admin : [],
      nav : "reservation",
      inputDate : ref({
        start: new Date(),
        end : new Date(),
      }),
      modal : ref(false),
      showData : ref(false),
    };
  },
  methods : {
    async init() {  // db 불러와
      console.log("order.vue init()")
      document.getElementById("reservation_row").style.display="block";
      await axios.get('http://localhost:3000/user')
          .then(res => {
            console.log(res.data)
            this.user = res.data; //orders에 응답값 박아
          })
          .catch(function(error){
            console.log(error);
          })
    },
    change(type){
      console.log(type)
      document.getElementById(this.nav).classList.remove('admin_active');
      document.getElementById(this.nav+'_row').style.display="none";
      document.getElementById(type+'_row').style.display="block";
      document.getElementById(type).classList.add('admin_active');
      this.nav = type;
    },
    getFormatDate(date) {
      const YYYY = String(date.getFullYear())
      const MM = String((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1): "0" + (date.getMonth() + 1))
      const dd = String(date.getDate() >= 10 ? date.getDate() : "0" + date.getDate())
      return YYYY + "-" + MM + "-" + dd
    },
    goRooms(st,en){
      this.modal = !this.modal;
    },
    modalOpen(){
      this.modal = !this.modal;
    },
    async makeData(){
      let dbData = {
        CheckIn_1 : this.getFormatDate(this.inputDate.start),
        CheckIn_2 : this.getFormatDate(this.inputDate.end),
        Rating_1 : '',
        Rating_2 : '',
        Rating_3 : '',
        AddMemberCnt : 1,
        Breakfast : 0
      }
      if(document.querySelector("#superior").checked){
        dbData.Rating_1 = 'Superior';
      }else{
        dbData.Rating_1 = '';
      }

      if(document.querySelector("#deluxe").checked){
        dbData.Rating_2 = 'Deluxe';
      }else{
        dbData.Rating_2 = '';
      }

      if(document.querySelector("#suite").checked){
        dbData.Rating_3 = 'Suite';
      }else{
        dbData.Rating_3 = '';
      }

      if(document.querySelector("#addMemberCnt").checked){
        dbData.AddMemberCnt = 10;
      }else{
        dbData.AddMemberCnt = 1;
      }

      if(document.querySelector("#breakfast").checked){
        dbData.Breakfast = 1;
      }else{
        dbData.Breakfast = 0;
      }


      console.log(dbData)
      await axios.post('http://localhost:3000/admin',dbData).then(res => {
        console.log(res.data)
        console.log(JSON.stringify(res.data))
            this.admin = res.data; //orders에 응답값 박아
            this.showData = true;
          })
          .catch(function(error){
            console.log(error);
          })
    },
  },

  mounted() { //init이라고 봐라
    this.init();
  }
});
</script>
<style>
.col-xs-12{
  text-align: center;
  padding-right: 7%;
}

.admin_mainmenu{
  padding-bottom: 25px;
}

.admin_headerText{
  color : black;
  font-size: 30px;
}

.admin_sapn{
  padding-right: 40px;
}

.admin_mainmenu span a:hover {
  color: #7a7979;
  cursor: pointer;
}

.admin_active{
  text-decoration-line: overline ;
  text-decoration-thickness: 3px;
}

.reservation_div{
  height: 250px;
  background: #fdfdf7;
  padding: 10px;
  border-radius: 3px;
  border: 2px solid rgb(237, 237, 210);
}

.admin-div{
  text-align: left;
}

.admin_p{
  font-size: 20px;
  text-align : right;
}

.colcol{
  padding-left : 13px;
  padding-top : 20px;
}

.reservation_div{
  padding: 15px;
}

.admin_btn{
  display: block;
  font-size: 14px;
  text-transform: uppercase;
  border: 2px solid rgb(237, 237, 210);
  border-radius: 2px;
  color: #666666;
  font-weight: 500;
  background: transparent;
  width: 70%;
  height: 46px;
}



.admin_check{
    height: 20px;
    width: 20px;
    border: 1px solid #e1e1e1;
    font-size: 14px;
    color: #666666;
}

.checkInput {
  height: 40px;
  width: 106%;
  border: 1px solid #e1e1e1;
  font-size: 14px;
  color: #666666;
  padding-left: 20px;
}
</style>