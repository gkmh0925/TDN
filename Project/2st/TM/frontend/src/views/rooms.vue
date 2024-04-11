<template>
  <section class="rooms-section spad">
    <div class="breadcrumb-section">
      <div class="container">
        <h2>방 목록</h2>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-4 col-md-6" v-for="(rm, index) in room" key="rm.RoomNum">
          <div class="room-item">
            <img :src="rm.RoomImg" alt="">
            <div class="ri-text kmh">
              <table>
                <tbody>
                <tr>
                  <td class="r-o">등급 :</td>
                  <td>{{rm.Rating}}</td>
                </tr>
                <tr>
                  <td class="r-o">방 크기 :</td>
                  <td>{{rm.Size+"㎡"}}</td>
                </tr>
                <tr>
                  <td class="r-o">가용 인원 :</td>
                  <td>{{rm.BasicPersonnel+"인"+" "+"~"+" "+rm.MaxPersonnel+"인"}}</td>
                </tr>
                <tr>
                  <td class="r-o">침대 :</td>
                  <td>{{rm.Bed}}</td>
                </tr>
                <tr>
                  <td class="r-o">서비스 :</td>
                  <td v-if="rm.Bath==1 && rm.Terrace==1 && rm.Spa==1 && rm.Pool==0 && rm.Rooftop==0">욕조, 테라스, 스파</td>
                  <td v-if="rm.Bath==1 && rm.Terrace==1 && rm.Spa==1 && rm.Pool==1 && rm.Rooftop==0">테라스,스파,독립 수영장</td>
                  <td v-if="rm.Bath==1 && rm.Terrace==1 && rm.Spa==1 && rm.Pool==1 && rm.Rooftop==1">스파,독립 수영장,루프탑</td>
                </tr>
                </tbody>
              </table>
              <!--              <router-link to="/roomDetail"><a href="#" class="primary-btn">상세 보기</a></router-link>-->
                <a href="#" class="primary-btn" @click="nextPage(index)">상세 보기</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--페이지로 열 경우-->
<!--    <div class="col-lg-12">-->
<!--      <div class="room-pagination">-->
<!--        <a href="#">1</a>-->
<!--        <a href="#">2</a>-->
<!--        <a href="#">Next <i class="fa fa-long-arrow-right"></i></a>-->
<!--      </div>-->
<!--    </div>-->
  </section>


</template>
<style>
.room-item{
  border-radius: 1%;
  overflow: hidden;
  word-break: keep-all;
}

.room-item .ri-text {
  border: 2px double #ebebeb;
  border-top: none;
  padding: 24px 24px 30px 28px;
}

.breadcrumb-section {
  padding-top: 60px;

}


</style>

<script>
import {defineComponent} from "vue";
import axios from "axios";
import router from "@/router";

export default defineComponent({
  data() {
    return {
      room: [],
      userData : {},
    };
  },
  methods :{
    init() { //db 호출
      axios.get('http://localhost:3000/rooms')
          .then(res => {
            console.log(res.data)
            this.room = res.data; //room에 응답값 박아
          })
          .catch(function (error) {
            console.log(error);
          })
      if(sessionStorage.getItem("loginYN")) {  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        console.log(this.userData)
      }
    },
    nextPage(index){  //for문 순번 받아옴
      console.log(index)
      console.log(this.room[index])
      router.push({
        name: "roomDetail",
        state: {  //고정이름
          dataObj : JSON.stringify(this.room[index]),  //dataObj : 변수(변동가능)
        }
      })
    }
    //문자열 형태의 이미지 주소를 이미지 URL로 변환하는 메서드
    },
    mounted() { //init이라고 봐라
      this.init();
    }
  });
</script>