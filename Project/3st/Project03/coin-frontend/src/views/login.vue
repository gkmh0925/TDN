

<template>

  <div class="container-scroller" >
    <Transition @before-enter="onBeforeEnter"
                @enter="onEnter"
                @leave="onLeave"
                :css="false">
      <div class="container-fluid page-body-wrapper full-page-wrapper"  v-if="show">
        <div class="row w-100 m-0">
          <div class="content-wrapper full-page-wrapper d-flex align-items-center auth ">
            <div class="card col-lg-5 mx-auto">
                <div :class="{ shake: disabled }">
                  <div class="card-body px-4 py-5">
                    <h3>Login</h3>

                    <form>
                      <div class="row">
                        <div class="col-7">
                          <div class="form-group">
                            <label>Username *</label>
                            <input type="password" class="form-control p_input" v-model="userID" id="userID" @keydown.enter="login">
                          </div>
                          <div class="form-group">
                            <label>Password *</label>
                            <input type="password" class="form-control p_input" v-model="userPW" id="userPW" @keydown.enter="login">
                          </div>
                          <div class="form-group d-flex align-items-center logincheck">
                            <input type="checkbox" id="check1" @change="remember" v-model="saveId">
                            <label for="check1" class="hover-pointer"></label><label for="check1" class="hover-pointer"> Remember me </label>
                          </div>
                          <div class="form-group d-flex  ">
                          </div>
                          <div class="text-center">
                            <button type="button" class="btn btn-primary btn-block enter-btn" @click="login" id="login">{{ loginText }}</button>
                          </div>
                        </div>
                        <div class="col-5 px-4">
                          <div class="form-group">
                            <label>QR *</label>
                            <br>
                            <img :src='qrUrl' class="image-tile">
                          </div>
                        </div>
                      </div>

                    </form>

                    <form v-if="loginType==='QR'">
                      <div class="form-group">
                        <label>QR *</label>
                        <br>
                        <img :src='qrUrl' class="image-tile">
                      </div>
                    </form>


                  </div>
                </div>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>


<script setup>
import {ref} from "vue";
import router from "@/router";
import {io} from "socket.io-client";
import axios from "axios";
import gsap from 'gsap'

/* Login */
const disabled = ref(false);
const show = ref(true);
let userID = ref("");
let userPW = ref("");
let loginText = "Login";
let saveId = localStorage.getItem("saveId") || false;

if(saveId){
  userID.value = localStorage.getItem("id") || "";
}

async function login() {
  let param = {
    id : userID.value,
    pw : userPW.value
  }
  await axios.post('/api/userLogin', param)
    .then(res => {
      if(res.data.length === 1){
        sessionStorage.setItem("user", JSON.stringify(res.data[0]));
        setTimeout(() => {
          show.value = false;
        },150)
        setTimeout(() => {
          if(saveId){
            localStorage.setItem("id", userID.value); //아이디 로컬스트로지 저장!
          }else{
            localStorage.removeItem("id"); //아이디 로컬스트로지 저장!
          }
          saveIp(userID.value);
        }, 900)
      }else{
        loginText = "You do not have permission.";
        disabled.value = true
        setTimeout(() => {
          loginText = "Login";
          disabled.value = false
        }, 2000)
      }

    })
    .catch(function(error){
      console.log(error);
    })
}

async function saveIp(id){
  if (/Mobi|Android/i.test(navigator.userAgent)) {
    alert("mobile")
    // 모바일인 경우 ip 저장 로직
    let param = {
      id : id,
    }

    await axios.post('/api/userIpUpdate', param)
        .then(res => {

          router.push({ //페이지 이동
            name: "main",
            state: {
              /*dataObj : this.response,*/
            }
          })

        })
        .catch(function(error){
          console.log(error);
        })
  }else{
    router.push({ //페이지 이동
      name: "main",
      state: {
        /*dataObj : this.response,*/
      }
    })
  }

}

function remember(){
  saveId != saveId;
  localStorage.setItem("saveId", saveId);
}
/* Login */


/* QR */
// 브로드캐스팅 받은 데이터 구분을 위한 id
let user = JSON.parse(sessionStorage.getItem("user"));
//const socket = io('3.39.202.228:3000')
//const socket = io('192.168.219.55:3000')
const socket = io('localhost:3000')

const message = ref('')

function sendMessage() {
  console.log("socket.on.sendMessage")

  const data = {
    message: message.value,
    user : user
  }
  socket.timeout(5000).emit('qr', data)
  message.value = "";

}

socket.on('qr', (data) => {
  console.log(data)
  console.log(data.status === "0000")
  console.log(localStorage.getItem("saveId") === "true")
  console.log(localStorage.getItem("id") === data.id)
  console.log(localStorage.getItem("id"))
  console.log(data.id)
  if (data.status === "0000" && localStorage.getItem("saveId") === "true" && localStorage.getItem("id") === data.id) {
    let param = {
      id: data.id,
      pw: data.pw
    }
    axios.post('/api/userLogin', param)
        .then(res => {
          sessionStorage.setItem("user", JSON.stringify(res.data[0]));
          router.push({ //페이지 이동
            name: "main",
            state: {
              /*dataObj : this.response,*/
            }
          })
        })
        .catch(function (error) {
          console.log(error);
        })
  }else{
    loginText = "You do not have permission.";
    disabled.value = true
    setTimeout(() => {
      loginText = "Login";
      disabled.value = false
    }, 2000)
  }
})
  /* QR */

  /*라이브러리 효과*/
  function onBeforeEnter(el) {
    gsap.set(el, {
      scaleX: 0.25,
      scaleY: 0.25,
      opacity: 1
    })
  }

  function onEnter(el, done) {
    gsap.to(el, {
      duration: 1,
      scaleX: 1,
      scaleY: 1,
      opacity: 1,
      ease: 'elastic.inOut(2.5, 1)',
      onComplete: done
    })
  }

  function onLeave(el, done) {
    gsap.to(el, {
      duration: 0.7,
      scaleX: 1,
      scaleY: 1,
      x: 1200,
      ease: 'elastic.inOut(2.5, 1)'
    })
    gsap.to(el, {
      duration: 0.3,
      delay: 0.5,
      opacity: 0,
      onComplete: done
    })
  }
/*라이브러리 효과*/
</script>

<script>
import axios from "axios";

export default {
  data(){
    return{
      qrUrl : "",
    }
  },
  methods : {
    async goQR(){
      await axios.get('/api/qrCode')
          .then(res => {
            //this.qrUrl = 'http://13.209.199.128:8090/qrCode';
            //this.qrUrl = 'http://192.168.219.55:8090/qrCode';
            this.qrUrl = 'http://localhost:8090/qrCode';
          })
          .catch(function(error){
            console.log(error);
          })
    }
  },
  mounted() {
    this.goQR();
  }
}
</script>


<style scoped>
label{
  color: #a3a4cc;
}
input[type="checkbox"]{
  display: none;
}
input[type="checkbox"] + label{
  display: inline-block;
  width: 20px;
  height: 20px;
  border:3px solid #a3a4cc;
  position: relative;
}
input[id="check1"]:checked + label::after{
  content:'✔';
  font-size: 18px;
  width: 30px;
  height: 30px;
  text-align: left;
  position: absolute;
  left: 0;
  top:0;
  color: #a3a4cc;
}

.hover-pointer:hover{
  cursor: pointer;
}



</style>