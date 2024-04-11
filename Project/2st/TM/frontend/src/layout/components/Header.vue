<template>
  <header>
    <transition name="fade">
      <div class ="black-bg" v-if="modal">
        <div class="white-bg">
          <div class="modal-close">
            <a herf="#" @click="modalOpen()" class="headerText">x</a>
          </div>
          <div class="modal-name" style="text-align: center">
            <p>예약일을 선택해 주세요.</p>
            <br>
              <div>
                <VDatePicker v-model.range="inputDate" mode="date" style="width: 100%;" :disabled-dates="disabledDates"/>
              </div>
            <div class="divOOO">
              <div class="inputDiv" style="font-weight: bold; text-align: right">
                체크인 : <input type="text" class="inputText" disabled :value="getFormatDate(inputDate.start)" style="font-weight: bold">
                <br>
                <br>
                체크아웃 : <input type="text" class="inputText" disabled :value="getFormatDate(inputDate.end)" style="font-weight: bold">
                <br>
              </div>
              <div class="buttonDiv">
                <router-link to="/rooms"><button type="button" class="inputBtn" @click="goRooms(getFormatDate(inputDate.start),getFormatDate(inputDate.end))">검색</button></router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </transition>
    <div class="menu-item">
      <div class="container mainCon">
        <div class="row rowrow">
          <div class="col-npm lg-2">
            <div class="logo">
              <a href="#">
                <router-link to="/main" class="headerText"><img src="../../img/logo.png" alt="" height="40"></router-link>
              </a>
            </div>
          </div>
          <div class="col-lg-8 logoM">
            <div class="nav-menu" >
              <nav class="mainmenuu mainmenuuli">
                <ul >
                  <li><span class="headerText modal-start" @click="modalOpen()">예약일을 선택해 주세요.</span></li>
                </ul>
              </nav>
            </div>
          </div>
          <div class="col-lg-2 logoM offset-xl-2 offset-lg-1">
            <div class="nav-menu">
              <nav class="mainmenu">
                <ul>
                  <li class="col-6-li"><router-link to="/rooms" class="headerText">방목록</router-link></li><!--class="active"-->

                  <li class="col-6-li" v-if="!loginData || null"><router-link to="/login" class="headerText">로그인</router-link>
                  </li>

                  <li class="col-6-li" v-else><router-link to="/main" class="headerText" @click="logout()">로그아웃</router-link>
                    <ul class="dropdown">
                      <li><router-link to="/order" class="headerText">예약내역</router-link></li>
                      <li v-if="userData.Admin===0"><router-link to="/user" class="headerText">내정보</router-link></li>
                      <li v-else><router-link to="/admin" class="headerText">관리자</router-link></li>
                    </ul>
                  </li>

                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>
<script>
import { ref } from 'vue';
import router from "@/router";

export default ({
  data() {
    return {
      modal : ref(false),
      inputDate : ref({ //시작, 끝 날짜 저장
        start: new Date(),
        end : new Date(),
      }),
      disabledDates : ref( //비활성 저장
          [{ start: new Date(2024, 0, 7), end: new Date(2024, 0, 7)}]
      ),
      userData : {},  //로그인 데이터 저장공간
      loginData : sessionStorage.getItem("loginYN"),
    };
  },
  methods : {
    init(){
      if(sessionStorage.getItem("loginYN")){  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        console.log(this.userData)
      }
    },
    getFormatDate(date) { //yyyy.mm.dd 포멧으로 변경
      const YYYY = String(date.getFullYear())
      const MM = String((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1): "0" + (date.getMonth() + 1))
      const dd = String(date.getDate() >= 10 ? date.getDate() : "0" + date.getDate())
      return YYYY + "." + MM + "." + dd
    },
    goRooms(st,en){
      console.log("START : " + st.replaceAll(".","-"));
      console.log("END : " + en.replaceAll(".","-"));
      alert("START : " + st.replaceAll(".","-") + "\n" + "END : " + en.replaceAll(".","-"))
      this.modal = !this.modal;
    },
    modalOpen(){
      this.modal = !this.modal;
    },
    logout(){
      this.loginData =! this.loginData;
      sessionStorage.clear();
    },

  },
  mounted() { //화면 진입시 바로 실행
    this.init();  //요놈 바로 실행
  }
})

</script>

<style>
.logoM{
  padding-right: 0;
  margin-left: 1.5%;
  margin-top: 0.4%;
}
.mainmenuu{
  text-align: center;
}
.col-1-li{
  margin-left: 2px;
}

.dropdown li a{
  /*dkdkdkdkkdkdkd*/
}

.menu-item .nav-menu .mainmenu li a {
  font-size: 16px;
  color: #19191a;
  margin-left: 40px;
  font-weight: 700;
  display: inline-block;
  padding: 27px 0;
  position: relative;
  -webkit-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
  text-align: right;
}

.menu-item .nav-menu .mainmenu li .dropdown {
  width: 75px;
  position: absolute;
  left: -10px;
  top: 97px;
  background: #fffff0;
  z-index: 9;
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
  -webkit-box-shadow: 0px 9px 15px rgba(25, 25, 26, 0.05);
  box-shadow: 0px 9px 15px rgba(25, 25, 26, 0.05);
  margin-left: 40px;
}

.menu-item .nav-menu .mainmenu li .dropdown li a {
  width: 90px;
  right: 40px;
  font-size: 14px;
  color: #19191a;
  display: block;
  text-align: left;
  padding: 8px 7px;
  -webkit-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}


/* 애니메이션 */
.fade-enter {
  opacity: 0;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.rowrow {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  margin-right: -95px;
  margin-left: -35px;
}

.mainmenuuli li {
  font-size: 16px;
  color: #19191a;
  margin-right: 42px;
  font-weight: 500;
  padding: 27px 0;
  position: relative;
  -webkit-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
  list-style: none;
  display: inline-block;
  z-index: 1;
  left:88px;
}

</style>