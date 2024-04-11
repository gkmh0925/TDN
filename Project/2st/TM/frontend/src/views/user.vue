<template>
  <section class="rooms-section spad">
    <div class="breadcrumb-section">
      <div class="container">
        <h2>회원정보 수정</h2>
        <div class="checkout__form">
          <div class="row">
            <div class="col-12">
              <div class="row">
                <div class="col-lg-12">
                  <div class="checkout__input">
                    <p>이메일<span>*</span></p>
                    <input type="text" class="id_find_email" v-model="user.UserEmail" disabled>
                  </div>
                </div>
                <div class="col-lg-12">
                  <div class="checkout__input">
                    <p>이름<span>*</span></p>
                    <input type="text" class="id_find_name" v-model="user.UserName" disabled>
                  </div>
                </div>
                <div class="col-lg-12">
                  <div class="checkout__input">
                    <p>휴대전화번호<span>*</span></p>
                    <input type="text" class="pw_find_phone" v-model="user.Phone" disabled>
                  </div>
                </div>
                <div class="col-lg-4">
                  <div class="checkout__input">
                  </div>
                </div>
                <div class="col-lg-12">
                  <div class="checkout__input">
                    <p>비밀번호<span>*</span></p>
                    <input name="pw" type="text" value="변경할 비밀번호" class="change_pw" maxlength="16" v-model="user.UserPw" disabled>
                  </div>
                </div>
                <div class="col-lg-12">
                  <div class="checkout__input">
                    <p>비밀번호 확인<span>*</span></p>
                    <input name="pw_check" type="text" placeholder="변경할 비밀번호 확인" class="change_pw_check"  maxlength="16"  v-model="password">
                  </div>
                </div>
                <div class="col-lg-4">
                  <div class="checkout__input">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="checkout__input">
          <button type="button" class="userInfoSave" @click="userInfoSave()">저장</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      user : [],
      password:""
    }
  },

  methods: {
    async init() {
      /*await axios.post('http://localhost:3000/finds',{
        UserId : 'son'
      })
        .then(res => {
          this.user = res.data[0]; //orders에 응답값 박아
          console.log(this.user)
          this.user.Phone = this.user.UserPhNum1 + '-' + this.user.UserPhNum2 + '-' + this.user.UserPhNum3
        })
        .catch(function(error){
          console.log(error);
        })*/
      this.user = JSON.parse(sessionStorage.getItem("userData"));
      this.user.Phone = this.user.UserPhNum1 + '-' + this.user.UserPhNum2 + '-' + this.user.UserPhNum3   
       try{
      await axios.post('http://localhost:3000/finds',{
          UserName: this.user.UserName,
          UserPhNum1: this.user.UserPhNum1,
          UserPhNum2: this.user.UserPhNum2,
          UserPhNum3: this.user.UserPhNum3
          
      })   
    }catch(err){
          console.log(err);
        }
    },
   async userInfoSave(){
    try {
        await axios.put(
          `http://localhost:3000/putPassword`,
          {
           UserPw : this.password,
           UserId : this.user.UserId
          }
        );
       
      } catch (err) {
        console.log(err);
      }
    this.loginData =! this.loginData;
      sessionStorage.clear();
      alert("변경")
      window.location.href= '/login';


    }
  },
  mounted() { //init이라고 봐라
    this.init();
  }
}

</script>

<style>
.breadcrumb__text h2 {
  color: #111111;
}
.tellCheckBtn  {
  margin-top: 60px;
  display: block;
  width: 150px;
  font-size: 14px;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  height: 49px;
  background: transparent;
}
.userInfoSave  {
  margin-top: 60px;
  margin: auto;
  display: block;
  width: 300px;
  font-size: 14px;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  height: 49px;
  background: transparent;

}
input:active{
  border: 2px solid #fdd8a0;
}
input:focus{
  border: 2px solid #fdd8a0;
}
.breadcrumb-section{
  overflow: hidden;
}

footer{
  position : relative;
  transform : translateY(90%);
}
</style>