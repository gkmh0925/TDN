<template>
  <nav class="sidebar sidebar-offcanvas" id="sidebar" >
    <div class="sidebar-brand-wrapper d-none d-lg-flex align-items-center fixed-top"> <!-- 추가 클래스 fixed-top-->
      <a class="nav-link" data-toggle="collapse" href="" aria-expanded="false" aria-controls="ui-basic" @click="goPage('main')">
        <a class="sidebar-brand brand-logo" href="#"><img src="../../public/boot/images/logo.svg" alt="logo" /></a>
      </a>
      <a class="nav-link" data-toggle="collapse" href="" aria-expanded="false" aria-controls="ui-basic" @click="goPage('main')">
        <a class="sidebar-brand brand-logo-mini" href="#"><img src="../../public/boot/images/logo-mini.svg" alt="logo" /></a>
      </a>
    </div>
    <ul class="nav" style="position: fixed; width: 244px">
      <li class="nav-item menu-items nav-main active" id="nav-main">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('main')"><!--/main-->
              <span class="menu-icon">
                <i class="mdi mdi-chart-areaspline"></i>
              </span>
          <span class="menu-title">Chart</span>
        </a>
      </li>
      <li class="nav-item menu-items nav-key" id="nav-key">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('key')">
              <span class="menu-icon">
                <i class="mdi mdi-account-key"></i>
              </span>
          <span class="menu-title">API</span>
        </a>
      </li>
      <li class="nav-item menu-items nav-trading" id="nav-trading">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('trading')">
              <span class="menu-icon">
                <i class="mdi mdi-tune-vertical "></i><!--mdi mdi-lan-connect-->
              </span>
          <span class="menu-title">Trading</span>
        </a>
      </li>
      <li class="nav-item menu-items nav-record" id="nav-record">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('record')">
              <span class="menu-icon">
                <i class="mdi mdi-briefcase-check"></i><!--mdi mdi-lan-connect-->
              </span>
          <span class="menu-title">History</span>
        </a>
      </li>
      <li class="nav-item menu-items nav-notification" id="nav-notification">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('notification')">
              <span class="menu-icon">
                <i class="mdi mdi-human-greeting"></i><!--mdi mdi-lan-connect-->
              </span>
          <span class="menu-title">Notification</span>
        </a>
      </li>

<!--      <li class="nav-item menu-items nav-bootExm" id="nav-bootExm">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic" @click="goPage('bootExm')">
              <span class="menu-icon">
                <i class="mdi mdi-laptop"></i>
              </span>
          <span class="menu-title">BootEmx</span>
        </a>
        <div class="collapse" id="ui-basic">
          <ul class="nav flex-column sub-menu">
            <li class="nav-item"> <a class="nav-link" href="">Buttons</a></li>
            <li class="nav-item"> <a class="nav-link" href="">Dropdowns</a></li>
            <li class="nav-item"> <a class="nav-link" href="">Typography</a></li>
          </ul>
        </div>
      </li>
      <li class="nav-item menu-items nav-test" id="nav-test" @click="goPage('test')">
        <a class="nav-link" data-toggle="collapse" href="#" aria-expanded="false" aria-controls="ui-basic">
              <span class="menu-icon">
                <i class="mdi mdi-ghost"></i>
              </span>
          <span class="menu-title">Test</span>
        </a>
      </li>-->
<!--      <li class="nav-item menu-items">
        <a class="nav-link" href="">
              <span class="menu-icon">
                <i class="mdi mdi-chart-bar"></i>
              </span>
          <span class="menu-title">Charts</span>
        </a>
      </li>
      <li class="nav-item menu-items">
        <a class="nav-link" href="">
              <span class="menu-icon">
                <i class="mdi mdi-contacts"></i>
              </span>
          <span class="menu-title">Icons</span>
        </a>
      </li>
      <li class="nav-item menu-items">
        <a class="nav-link" data-toggle="collapse" href="" aria-expanded="false" aria-controls="auth">
              <span class="menu-icon">
                <i class="mdi mdi-security"></i>
              </span>
          <span class="menu-title">User Pages</span>
          <i class="menu-arrow"></i>
        </a>
        <div class="collapse" id="auth">
          <ul class="nav flex-column sub-menu">
            <li class="nav-item menu-items"> <a class="nav-link" href=""> Blank Page </a></li>
            <li class="nav-item"> <a class="nav-link" href=""> 404 </a></li>
            <li class="nav-item"> <a class="nav-link" href=""> 500 </a></li>
            <li class="nav-item"> <a class="nav-link" href=""> Login </a></li>
            <li class="nav-item"> <a class="nav-link" href=""> Register </a></li>
          </ul>
        </div>
      </li>
      <li class="nav-item menu-items">
        <a class="nav-link" href="">
              <span class="menu-icon">
                <i class="mdi mdi-file-document-box"></i>
              </span>
          <span class="menu-title">Documentation</span>
        </a>
      </li>-->
    </ul>
  </nav>
</template>
<script>
  import router from "@/router";
  import trading from "@/views/trading.vue";

  export default {
    computed: {
      trading() {
        return trading
      }
    },
    data(){
      return{
        active : "main",
      }
    },
    methods: {
      goPage(nav) {
        document.getElementById('nav-'+this.active).classList.remove('active');
        this.active = nav;
        sessionStorage.setItem("active",this.active);
        document.getElementById('nav-'+nav).classList.add('active');
        router.push({
          name: nav,
          state: {
            /*dataObj : this.response,*/
          }
        })
      }
    },
    mounted() {
      this.goPage(sessionStorage.getItem("active") || this.active);
    }
  }
</script>

<style>

</style>