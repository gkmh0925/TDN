<template>
  <div class="navbar p-0 fixed-top d-flex flex-row">
    <div class="navbar-brand-wrapper d-flex d-lg-none align-items-center justify-content-center">
      <a class="navbar-brand brand-logo-mini" href="#"><img src="../../public/boot/images/logo-mini.svg"
                                                            alt="logo"/></a>
    </div>
    <div class="navbar-menu-wrapper flex-grow d-flex align-items-stretch">
      <ul class="navbar-nav navbar-nav-right">


        <li class="nav-item nav-settings d-none d-lg-block">
          <a class="nav-link" href="#">
            <i class="mdi mdi-view-grid"></i>
          </a>
        </li>


        <li class="nav-item dropdown border-left">
          <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-toggle="dropdown">
            <i class="mdi mdi-bell"></i>
            <span class="count bg-danger"></span>
          </a>
          <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
               aria-labelledby="notificationDropdown">
            <h6 class="p-3 mb-0">Notifications</h6>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-calendar text-success"></i>
                </div>
              </div>
              <div class="preview-item-content">
                <p class="preview-subject mb-1">Event today</p>
                <p class="text-muted ellipsis mb-0"> Just a reminder that you have an event today </p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-settings text-danger"></i>
                </div>
              </div>
              <div class="preview-item-content">
                <p class="preview-subject mb-1">Settings</p>
                <p class="text-muted ellipsis mb-0"> Update dashboard </p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-link-variant text-warning"></i>
                </div>
              </div>
              <div class="preview-item-content">
                <p class="preview-subject mb-1">Launch Admin</p>
                <p class="text-muted ellipsis mb-0"> New admin wow! </p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <p class="p-3 mb-0 text-center">See all notifications</p>
          </div>
        </li>


        <li class="nav-item dropdown border-left" @mouseenter="msgList()" @mouseleave="msgList()">
          <a class="nav-link count-indicator dropdown-toggle" id="messageDropdown" href="#" data-toggle="dropdown"
             aria-expanded="false">
            <i class="mdi mdi-email"></i>
            <span class="count bg-success" id="msgActive"></span>
          </a>
          <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="messageDropdown"
               v-if="msgMouse" style="display: block">
            <h6 class="p-3 mb-0 text-center">Chatting</h6>

            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item" v-on:click="message()">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-message-processing text-success"></i>
                </div>
              </div>
              <div class="preview-item-content">
                <p class="preview-subject mb-1">Chat {{ formState }}</p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <p class="p-3 mb-0 text-center" style="color: #a3a4cc" v-on:click="this.msgCnt=0">{{ msgCnt }} new
              messages</p>
          </div>
        </li>


        <li class="nav-item dropdown">
          <a class="nav-link" id="profileDropdown" href="#" data-toggle="dropdown">
            <div class="navbar-profile">
              <p class="mb-0 d-none d-sm-block navbar-profile-name">{{ krw }}</p>
            </div>
          </a>

        </li>


        <li class="nav-item dropdown" @mouseenter="userList()" @mouseleave="userList()">
          <a class="nav-link" id="profileDropdown" href="#" data-toggle="dropdown">
            <div class="navbar-profile">
              <p class="mb-0 d-none d-sm-block navbar-profile-name">User {{ user.id }}</p>
              <i class="mdi mdi-menu-down d-none d-sm-block"></i>
            </div>
          </a>
          <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="profileDropdown"
               v-if="userMouse" style="display: block">
            <h6 class="p-3 mb-0 text-center">Profile</h6>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item" v-if="this.user.adYn === 'Y'" @click="goPage('admin')">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-settings text-success"></i>
                </div>
              </div>
              <div class="preview-item-content" >
                <p class="preview-subject mb-1">Admin</p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item preview-item" @click="logout">
              <div class="preview-thumbnail">
                <div class="preview-icon bg-dark rounded-circle">
                  <i class="mdi mdi-logout text-danger"></i>
                </div>
              </div>
              <div class="preview-item-content">
                <p class="preview-subject mb-1">Log out</p>
              </div>
            </a>
            <div class="dropdown-divider"></div>
            <p class="p-3 mb-0 text-center" style="color: #a3a4cc">Advanced settings</p>
          </div>
        </li>


      </ul>
      <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
              data-toggle="offcanvas">
        <span class="mdi mdi-format-line-spacing"></span>
      </button>
    </div>
  </div>
  <Chat style="z-index: 9; position: fixed" :chatYN='chatYN' :chatWidth='chatWidth' :msgCnt='msgCnt'
        @readCnt='changeCnt'/>
</template>

<script>
import Chat from "@/layout/Chat.vue";
import router from "@/router";
import axios from "axios";

export default {
  components: {Chat},
  data() {
    return {
      chatYN: true,
      chatWidth: "width : 400px",
      user: JSON.parse(sessionStorage.getItem("user")),
      userMouse: false,
      msgMouse: false,
      formState: "Hide",
      msgCnt: 0,
      krw: 0,
      key: [],
      val: [],
    }
  },
  methods: {
    message() {
      this.chatYN = !this.chatYN;
      this.chatWidth = this.chatYN ? "width : 400px" : "width : 160px";
      if (this.chatYN) {
        this.formState = "Hide";
      } else {
        this.formState = "Show";
      }
    },
    userList() {
      this.userMouse = !this.userMouse;
    },
    msgList() {
      this.msgMouse = !this.msgMouse;
      document.getElementById('msgActive').classList.remove('bg-success');
    },
    logout() {
      //sessionStorage.clear();
      this.user = {};
      router.push({ //페이지 이동
        name: "login",
        state: {}
      })
    },
    changeCnt(cnt) {
      document.getElementById('msgActive').classList.add('bg-success');
      this.msgCnt = cnt;
    },
    async balance() {
      this.user = JSON.parse(sessionStorage.getItem("user"));
      if (this.user.keyYn !== "Y") {
        this.krw = "API Key를 등록해 주세요.";
        setTimeout(() => {
          this.balance();
        }, 1000)
      }else{
        let param = {
          id: this.user.id,
          currency: "ALL",
        }
        await axios.post('/api/balance', param)
            .then(res => {
              sessionStorage.setItem("usdt_price",res.data.data.xcoin_last_usdt)  //USDT 가격 여러곳에서 쓰기때문에 올려두자
              sessionStorage.setItem("total_usdt",res.data.data.total_usdt)  //USDT 수량 여러곳에서 쓰기때문에 올려두자
              sessionStorage.setItem("total_krw",res.data.data.available_krw)  //KRW 수량 여러곳에서 쓰기때문에 올려두자
              let krw = 0;

              let key = Object.keys(res.data.data);
              let val = Object.values(res.data.data);


              val.forEach(function (data, index) {
                if (data.indexOf("0.00000000") >= 0) {
                  key.splice(index, 1)
                  val.splice(index, 1)
                }
              })
              key.forEach(function (data, index) {
                if (data.indexOf("total_") === 0) {
                  if (data === "total_krw") {
                    krw = parseInt(val[index]);
                  } else {
                    krw += parseInt(val[index] * val[index + 2]); //.replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")
                  }
                }
              })
              this.krw = krw.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " KRW";
              setTimeout(() => {
                this.balance();
              }, 1000)
            })
            .catch(function (error) {
              console.log(error);
            })
      }

    },
    goPage(nav) {
      router.push({
        name: nav,
        state: {
          /*dataObj : this.response,*/
        }
      })
    }
  },
  watch: {},
  mounted() {
    this.balance();
  }
}
</script>

<style scoped>

</style>