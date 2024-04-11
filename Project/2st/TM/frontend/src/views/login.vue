<template>
  <section class="rooms-section spad">
    <div class="breadcrumb-section">
      <div class="container">
        <h2>로그인</h2>
      </div>
    </div>
    <div class="container">
  
      <div class="row">
        <div class="col-lg-8 ">

          <div class="checkout__input">
            <p>아이디</p>
            <input type="text" id="login_id" v-model="form.id">
          </div>
          <div class="checkout__input">
            <p>비밀번호</p>
            <input type="password" id="login_pw" v-model="form.password">
          </div>

          <div class="checkout__input__checkbox">
            <label for="keepId">
              <p>아이디 저장</p>
              <input type="checkbox" id="keepId" v-model="keepId">
              <span class="checkmark"></span>
            </label>
          </div>
          <div class="signin_id_pw">
            <router-link to="/join"><a href="#">회원가입</a></router-link>
            <router-link to="/find"><a href="#">ID/PW찾기</a></router-link>
          </div>
        </div>
        <div class="col-lg-5">
          <button type="button" class="orderBtn5" @click="loginBtn">로그인</button>
        </div>
        <div class="col-lg-5">
          <button class="orderBtn5" v-on:click="kakaoLoginBtn">카카오 연동</button>
         </div>
        
      </div>
    </div>
  </section>
</template>
<script>
import {defineComponent} from "vue";
import axios from 'axios';
import router from "@/router";
export default defineComponent({
  data() {
    return {
      form :{
        id : "",
        password : "",
        mail : "",
        checkedtype : [],
        keepId : false,
      }
    };
  },
  methods: {
   
    async loginBtn(){
      try {
        let data = await axios.post("http://localhost:3000/login",{
           UserId: this.form.id,
           UserPw: this.form.password
         });
         if(this.form.id === data.data[0].UserId && this.form.password === data.data[0].UserPw){
          alert("로그인");
         }
        
         //사용자 정보는 여기에 담았다
        sessionStorage.setItem("userData", JSON.stringify(data.data[0]));
        sessionStorage.setItem("loginYN", true);
        sessionStorage.setItem("routerGo", true);
        
        //-----------------------아이디저장
        
        if (this.keepId) {
          localStorage.setItem('savedUsername', this.form.id);
        } else {
          localStorage.removeItem('savedUsername');
        }
         window.location.href= '/main';

         
       } catch (err) {
        alert("로그인 실패");
       }
    },
    
    kakaoLoginBtn:function(){
  
  window.Kakao.init('697524357430f22b5b827f6462d4f4a8') // Kakao Developers에서 요약 정보 -> JavaScript 키

  if (window.Kakao.Auth.getAccessToken()) {
    window.Kakao.API.request({
      url: '/v1/user/unlink',
      success: function (response) {
        console.log(response)
      },
      fail: function (error) {

      },
    })
    window.Kakao.Auth.setAccessToken(undefined)
  }
      window.Kakao.Auth.login({
    success: function () {
      window.Kakao.API.request({
        url: '/v2/user/me',
        data: {
          property_keys: ["kakao_account.email"]
        },
        success: async function (response) {
          console.log(response);
        },  
        fail: function (error) {
         
        },
      })
    },
    fail: function (error) {
      console.log(error)
    },
  })
}
  },
  mounted() {
      const savedUsername = localStorage.getItem('savedUsername');
      if (savedUsername) {
        this.form.id = savedUsername;
        this.keepId = true;
      }
    }
});

</script>

<style>

.checkout__input p {
  color: #444444;
  font-weight: 500;
  margin-bottom: 12px;
}
.checkout__input p span {
  color:#d9d6d6;
}
.checkout__input input {
  height: 50px;
  width: 100%;
  border: 1px solid #e1e1e1;
  font-size: 14px;
  color: #666666;
  padding-left: 20px;
}

.checkout__input__checkbox label {
  font-size: 14px;
  color: #444444;
  position: relative;
  padding-left: 30px;
  font-weight: 500;
  cursor: pointer;
  margin-bottom: 16px;
  display: block;
}
.checkout__input__checkbox label input {
  position: absolute;
  visibility: hidden;
}
.checkout__input__checkbox label input:checked ~ .checkmark {
  border-color:#d9d6d6;
}
.checkout__input__checkbox label input:checked ~ .checkmark:after {
  opacity: 1;
}
.checkout__input__checkbox label .checkmark {
  position: absolute;
  left: 0;
  top: 3px;
  height: 14px;
  width: 14px;
  border: 1.5px solid #888888;
  content: "";
  border-radius: 2px;
}
.checkout__input__checkbox label .checkmark:after {
  position: absolute;
  left: 1px;
  top: -3px;
  width: 14px;
  height: 7px;
  border: solid#d9d6d6;
  border-width: 1.5px 1.5px 0px 0px;
  -webkit-transform: rotate(127deg);
  -ms-transform: rotate(127deg);
  transform: rotate(127deg);
  content: "";
  opacity: 0;
}
.checkout__input__checkbox p {
  color: #666666;
  font-size: 14px;
  line-height: 24px;
  margin-bottom: 22px;
}


.site-btn {
  display: block;
  width: 70%;
  color: black;
  background-color: #dfa974;
  height: 40px;
}
.site-btn :hover{
  background-color:  rgb(87, 86, 86);;
}


.checkout__input__checkbox {
  display: inline-block;
}
.checkout__input__checkbox p{
  font-size: 15px;
}


.breadcrumb__text h2 {
  font-size: 40px;
  color: #000000;
  text-align: left;
}


input:active{
  border: 2px solid #fdd8a0;;
}
input:focus{
  border: 2px solid #fdd8a0;;
}
.orderBtn5{
  display: block;
  font-size: 14px;
  text-transform: uppercase;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  font-weight: 500;
  background: transparent;
  width: 50%;
  height: 46px;
  margin-top: 10px;

}
</style>