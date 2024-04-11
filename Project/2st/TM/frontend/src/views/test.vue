<template>
  <div v-for="rs in response">
    {{rs}}
    <br>
    <br>
    <br>
  </div>
</template>

<!--<script setup>
const { dataObj } = history.state; // 이렇게 받는다.
</script>-->

<script>
import axios from "axios";
import router from "@/router";
export default ({
  data() {
    return {
      userData : {},  //로그인 데이터 저장공간
    };
  },
  methods : {
    init(){
      if(sessionStorage.getItem("loginYN")){  //세션 로그인 yn이 ture면 저장할래
        this.userData = JSON.parse(sessionStorage.getItem("userData")); //유저 데이터 저장
        console.log(this.userData)
      }
    },
  },
  mounted() { //화면 진입시 바로 실행
    this.init();  //요놈 바로 실행
  }
})
</script>