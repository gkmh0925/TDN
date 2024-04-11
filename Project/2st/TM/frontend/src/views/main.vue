<template>
  <section class="hero-section">
    <div class="mainContainer" style="margin-bottom: 5%">
      <div class="wrapper">
        <Carousel :autoplay="7000" :wrap-around="true">
          <Slide v-for="slide in slides" :key="slide">
            <div class="carousel__item" style="height: 750px">
              <img class="slideImg" :src="slide" />
            </div>
          </Slide>

          <template #addons>
            <Pagination />
          </template>
        </Carousel>
      </div>
    </div>

    <div class="container" style="margin-bottom: 15%">
      <div class="row">
        <div class="col-lg-6">
          <div class="about-text">
            <div class="section-title">
              <span>About Us</span>
              <h3>많은 것이 새롭지만 모든 것이 <br> 매력적인 어반 라이프스타일 호텔 <br />Nonamed Hotel</h3>
            </div>
            <p class="f-para">어반 라이프스타일을 추구하는 Nonamed는 젊고 합리적인 품격, <br> 그리고 세심한 배려로 무장하여 이 곳의 방문객들이 공감할 수 있는 많은 이야기를 담고 있습니다.</p>
            <p class="s-para">여러분이 머무르는 공간들에, 그리고 호텔에서 경험하게 될 많은 서비스들에 반짝이는 아이디어를 숨겨 놓았습니다. 심각하지 않도록 당신은 그저 Nonamed가 준비한 많은 서비스를 즐기기만 하세요. <br>아무것도 하지 않아도, 머무르기만 해도 놀라운 일들이 생길거에요.</p>
            <router-link to="/rooms"><a href="#" class="primary-btn about-btn">Read More</a></router-link>

<!--            <a href="#" class="primary-btn about-btn" @click="reqData('orders')">Read More</a>-->
          </div>
        </div>
        <div class="col-lg-6">
          <div class="about-pic">
            <div class="row">
              <div class="col-sm-12">
                <img src="../img/hero/hero-1.jpg" alt="" style="height: 400px">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>



    <div class="container" style="margin-bottom: 15%">
      <div class="row">

        <div class="col-lg-6">
          <div class="about-pic">
            <div class="row">
              <div class="col-sm-12">
                <img src="../img/hero/hero-2.jpg" alt="" style="height: 400px">
              </div>
              <!--              <div class="col-sm-6">
                              <img src="../img/about/about-2.jpg" alt="">
                            </div>-->
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="about-text">
            <div class="section-title">
              <span>About Us</span>
              <h3>시간과 삶이 녹아<br>
                풍요로움이 가득한 곳<br />Nonamed Hotel</h3>
            </div>
            <p class="f-para">현대적인 감각이 돋보이는 인테리어와 통유리창으로
              들어오는 탁 트인 전망이 인상적인 이곳은 Nonamed 입니다. 한식과 제철 해산물,
              그릴 스테이션, 동남아, 유럽 음식, 조식 필수템인 즉석 쌀국수와
              에그 스테이션등 다채롭고 다양한 음식을 맛볼 수 있습니다.</p>
            <p class="s-para">Nonamed 에는 셰프 만의 예술성과 창의성을 기반으로<br>
              팔레트 위에 요리를 창조한다는 모토가 담겨있으며 국내에서 보기 드문 유러피안 퀴진을
            선보이고 있습니다. </p>
            <router-link to="/rooms"><a href="#" class="primary-btn about-btn">Read More</a></router-link>
          </div>
        </div>

      </div>
    </div>



    <div class="container">
      <div class="row">
        <div class="col-lg-6">
          <div class="about-text">
            <div class="section-title">
              <span>About Us</span>
              <h3>오롯이 한 공간을 마주한채<br> 자유로이 보내는 시간 <br />Nonamed Hotel</h3>
            </div>
            <p class="f-para">사람과 동물 모두 자신의 감정을 표현하는 수단들이 있습니다.
              사람은 눈꼬리와 입꼬리로 우리는 울고 웃습니다. 강아지는 작고 귀여운 꼬리가 있을 것입니다.
              다양한 표정과 몸짓을 통하여 우리만의 방식으로 자유로이 이야기하는 공간, 이곳은 Nonamed 입니다.</p>
            <p class="s-para">사람과 반려견 모두의 움직임과 시선을 고려한 공간을 만들고자 했습니다.
              반려견이 낯선 공간에 익숙해지고 자유롭게 놀 수 있도록 Nonamed 웰컴 키트도 준비했습니다.
              어메니티와 간식, 노즈 워크 장난감으로 반려동물과 함께 즐거운 추억을 만들 수 있습니다.
            </p>
            <router-link to="/rooms"><a href="#" class="primary-btn about-btn">Read More</a></router-link>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="about-pic">
            <div class="row">
              <div class="col-sm-12">
                <img src="../img/hero/hero-3.jpg" alt="" style="height: 400px">
              </div>
              <!--              <div class="col-sm-6">
                              <img src="../img/about/about-2.jpg" alt="">
                            </div>-->
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { defineComponent } from "vue";
import { Carousel, Pagination, Slide } from "vue3-carousel";
import home_1 from "../img/hero/main01.png";
import home_2 from "../img/hero/main02.png";
import home_3 from "../img/hero/main03.png";
import home_4 from "../img/hero/main04.png";
import axios from "axios";
import router from "@/router";
import "vue3-carousel/dist/carousel.css";

export default defineComponent({
  components: {
    Carousel,
    Slide,
    Pagination,
  },
  data() {
    return {
      slides: [home_1, home_2, home_3, home_4],
      response : "",
      userData : {},
    };
  },
  methods : {
    reqData(type){
      /*let name = type;
      if(type !== "test"){
        name = "test";
      }
      axios.get('http://localhost:3000/'+type)
          .then(res => {
            console.log(res)
            this.response = JSON.stringify(res.data);
            router.push({
              name: name,
              state: {
                dataObj : this.response,
              }
            })
          })
          .catch(function(error){
            console.log(error);
          })*/
      router.push({
        name: "admin",
        state: {
          dataObj : this.response,
        }
      })
    },
    init(){
      if(sessionStorage.getItem("loginYN")){
        this.userData = JSON.parse(sessionStorage.getItem("userData"));
        console.log(this.userData)
      }
      console.log("main.vue init()")
    }
  },
  mounted() { //init이라고 봐라
    this.init();
  },
 
});
</script>



<style scoped>
.f-para {
  word-break: keep-all;
}
.s-para {
  word-break: keep-all;
}
</style>
