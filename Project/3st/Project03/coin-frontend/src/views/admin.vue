<template>
  <div class="row">
    <div class="col-md-5 grid-margin stretch-card" style="height: 100%">
      <div class="card">
        <div class="card-body">
          <h4 class="card-title">User Search</h4>
          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control user-form" placeholder="Search User ID" @input="searchUser" v-model="searchText">
            </div>
          </div>
          <div class="chat-container">
            <div class="chat-messages">
              <div class="bg-gray-dark d-flex d-md-block d-xl-flex flex-row py-3 px-4 px-md-3 px-xl-4 rounded mt-3 userDiv" v-for="user in userData" :id="user.id" :style="'display:' + userHide[user.id] + '!important;'" @click="updateUser(user)">
                <div class="text-md-center text-xl-left">
                  <h6 class="mb-1">{{ user.mail }}</h6>
                  <p class="text-muted mb-0">{{ user.ph.toString().substring(0,3) }}-{{ user.ph.toString().substring(3,7) }}-{{ user.ph.toString().substring(7,11) }}</p>
                </div>
                <div class="align-self-center flex-grow text-right text-md-center text-xl-right py-md-2 py-xl-0">
                  <div class="text-md-center text-xl-right" >
                    <h6 class="mb-1 mg-right">{{ user.id }}</h6>
                  </div>
                </div>
                <i class="mdi icon-md mdi-delete-forever" @click="deleteUser(user.id)"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-7 grid-margin stretch-card" style="height: 100%">
      <div class="card">
        <div class="card-body">
          <h4 class="card-title">{{ userType }} User</h4>
          <p class="card-description"> Input user info </p>

          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Name" :disabled="checkId" v-model="userName" @keydown.enter="checkUser()" maxlength="20" @input="textCheck('userName', 'english')">
              <div class="input-group-append">
                <button class="btn btn-sm btn-primary" type="button" @click="checkUser()" :disabled="checkId">Check</button>
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <input type="password" class="form-control user-form" placeholder="Pass" :disabled="checkForm" v-model="userPass" maxlength="20" @input="textCheck('userPass', null)">
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control user-form" placeholder="Phone Number 1" :disabled="checkForm" v-model="userPh01" @input="textCheck('userPh01', 'number')" maxlength="3">
              <div class="input-group-prepend">
                <span class="input-group-text">-</span>
              </div>
              <input type="text" class="form-control user-form" placeholder="Phone Number 2" :disabled="checkForm" v-model="userPh02" @input="textCheck('userPh02', 'number')" maxlength="4">
              <div class="input-group-prepend">
                <span class="input-group-text">-</span>
              </div>
              <input type="text" class="form-control user-form" placeholder="Phone Number 3" :disabled="checkForm" v-model="userPh03" @input="textCheck('userPh03', 'number')" maxlength="4">
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control user-form" placeholder="Email" :disabled="checkForm" v-model="userMail" maxlength="30" @input="textCheck('userMail', 'english')">
              <div class="input-group-prepend">
                <span class="input-group-text">@</span>
              </div>
              <input type="text" class="form-control user-form" placeholder="Domain" :disabled="checkForm" v-model="userDomain" maxlength="15" @input="textCheck('userDomain', null)">
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control user-form" placeholder="Connect key" :disabled="checkForm" v-model="userCkey" maxlength="200" @input="textCheck('userCkey', null)">
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <input type="text" class="form-control user-form" placeholder="Secret key" :disabled="checkForm" v-model="userSkey" maxlength="200" @input="textCheck('userSkey', null)">
            </div>
          </div>

          <button type="button" class="btn btn-outline-secondary btn-lg btn-block" :disabled="checkBtn" @click="joinUpdateUser(userType)">{{ userType }}</button>

        </div>
      </div>
    </div>
  </div>


</template>

<script>
import axios from "axios";

export default {
  data(){
    return{
      checkId : false,  //id disabled
      checkForm : true, //그외 disabled
      checkBtn : true, //버튼 disabled
      userData : {},
      userHide : {},
      searchText : "",
      updateUserName : "",
      userNo : "",
      userName : "",
      userPass : "",
      userPh01 : "",
      userPh02 : "",
      userPh03 : "",
      userMail : "",
      userDomain : "",
      userSkey : "",
      userCkey : "",
      number : /\D/gi,  //정규화 숫자 아닌것
      english : /\W/gi, //정규화 알파벳 소문자 + 숫자 + '_'
      checkKeySuccess : false, // api ket 확인
      keyYn : "N",
      userClickID : "",
      userType : "Insert",
      userDeleteID : "",
    }
  },
  methods : {
    async selectUser(){
      await this.goAxios("userList");
    },
    async checkUser() {
      let param = {
        id:this.userName,
      }
      if(this.userName === ""){
        alert("불가능한 ID 입니다.");
        return;
      }
      await this.goAxios("userIdList", param);
    },
    async checkKey(){
      let param = {
        cKey : this.userCkey,
        sKey : this.userSkey,
        currency: "ALL",
      }
      await this.goAxios("balance", param)
    },
    async joinUpdateUser(type){
      let confirmText = "";
      let axiosUrl = "";
      if(type === "Update"){  //Update
        confirmText = "수정";
        axiosUrl = "userUpdate";
      }else if(type === "Insert"){  //Insert
        confirmText = "등록";
        axiosUrl = "userJoin";
      }else{
        return;
      }

      if(confirm("User ID : " + this.userName + " 를(을) " + confirmText + "하시겠습니까?")){
        if(this.userCkey !== "" && this.userSkey !== "" && this.userCkey !== null && this.userSkey !== null){
          await this.checkKey();
        }else if((this.userCkey === "" && this.userSkey !== "") || (this.userCkey !== "" && this.userSkey === "") || (this.userCkey === null && this.userSkey !== null) || (this.userCkey !== null && this.userSkey === null)) {
          alert("Connect key와 Secret key 모두 입력 바랍니다.");
          return;
        }else{
          this.checkKeySuccess = true;
        }//f2c17879fe3746932ba09acab8a08f4d
        //65beddbce011b5ef9ba34afa44f2805e


        if(this.checkKeySuccess){
          let param = {
            no : this.userNo,
            id : this.userName,
            pw : this.userPass,
            ph : this.userPh01.toString() + this.userPh02.toString() + this.userPh03.toString(),
            mail : this.userMail + "@" + this.userDomain,
            cKey : this.userCkey,
            sKey : this.userSkey,
            keyYn : this.keyYn,
            adYn : "N",
          }
          await this.goAxios(axiosUrl, param)
        }
      }

    },
    async deleteUser(userID){
      if(userID === "Host"){
        alert("관리자 계정은 삭제할수 없습니다.");
        return;
      }
      this.userDeleteID = userID;

      if(confirm("User ID : " + userID + "을(를) 삭제하시겠습니까?")){
        let param = {
          id:userID,
        }
        await this.goAxios("userDelete", param);
      }
    },
    searchUser(){ //검색
      this.userData.forEach((item) => {
        this.userHide[item.id] = "none";
        if (item.id.indexOf(this.searchText) !== -1) {
          this.userHide[item.id] = "";
        }
      });
    },
    textCheck(text, type){  //Input 유효성
      if(this[text] === "" || this[text].indexOf(" ") >= 0){
        this[text] = this[text].toString().replaceAll(' ', '');
      }
      if(type !== null){
        this[text] = this[text].toString().replace(this[type], '');
      }
    },
    updateUser(userData){
      console.log(userData.id)
      if(this.userDeleteID === userData.id){
        this.userDeleteID = "";
        return;
      }

      if(this.userClickID !== ""){
        document.querySelector("#"+this.userClickID).style.border = "2px solid #2b2c40";
      }
      if(this.userClickID === userData.id){
        this.userClickID = "";
        document.querySelector("#"+userData.id).style.border = "2px solid #2b2c40";
        this.resetForm("Insert");
        return;
      }else{
        if(!confirm("User ID : " + userData.id + "을(를) 수정하시겠습니까?") && this.updateUserName === ""){
          return;
        }
        this.userClickID = userData.id;
        document.querySelector("#"+userData.id).style.border = "2px solid #a3a4cc";
      }
      this.resetForm("Update", userData);

    },
    goAxios(type, param){
      console.log(param)

      axios.post('/api/'+type, param)
          .then(res => {

            if(type === "userList"){  // 고객정보 조회 : 초기진입.
              this.userData = res.data;
              this.userData.forEach( (data) => {
                this.userHide[data.id] = "";
              })
            }

            if(type === "userIdList"){  // 중복 ID 체크
              if(res.data.length !==0 && this.userType === "Insert"){
                alert("중복 ID 입니다.");
                return;
              }else if(res.data.length !==0 && this.userType === "Update"){
                if(res.data[0].id !== this.updateUserName){
                  alert("중복 ID 입니다.");
                  return;
                }
              }
              if(confirm("User ID : " + this.userName + " 사용하시겠습니까?")){
                this.checkId = true;
                this.checkForm = false;
                this.checkBtn = false;
              }
            }

            if(type === "balance"){  // API KEY 검증
              if(res.data !== null && res.data.status === "0000"){
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
                alert("API KEY 확인이 완료되었습니다.\nKRW : " + krw.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "\n금액 확인 바랍니다.");
                this.checkKeySuccess = true;
                this.keyYn = "Y";
              }else{
                this.checkKeySuccess = false;
                alert("올바르지 않은 API KEY입니다.")
                return;
              }
            }

            if(type === "userJoin" || type === "userUpdate") {  // 고객 Insert
              let confirmText = type === "userJoin" ?  "등록" : "수정";
              alert("User ID : " + this.userName + " 를(을) " + confirmText + "하였습니다.")
              this.resetForm("Insert");
              this.selectUser();  //목록 갱신
            }

            if(type === "userDelete") {  // 고객 Delete
              alert("User ID : " + param.id + " 를(을) 삭제하였습니다.")
              this.selectUser();  //목록 갱신
            }

          })
          .catch(function(error){
            console.log(error);
          })


    },
    resetForm(type, userData){
      if(type === "Insert"){
        this.updateUserName = "";
        this.userName = "";
        this.userPass = "";
        this.userPh01 = "";
        this.userPh02 = "";
        this.userPh03 = "";
        this.userMail = "";
        this.userDomain = "";
        this.userCkey = "";
        this.userSkey = "";
        this.checkId = false;
        this.checkForm = true;
        this.checkBtn = true;
        this.userType = "Insert"
      }else{
        let mail = userData.mail.split("@");
        this.updateUserName = userData.id;
        this.userNo = userData.no;
        this.userName = userData.id;
        this.userPass = userData.pw;
        this.userPh01 = userData.ph.substring(0,3);
        this.userPh02 = userData.ph.substring(3,7);
        this.userPh03 = userData.ph.substring(7,11);
        this.userMail = mail[0];
        this.userDomain = mail[1];
        this.userCkey = userData.cKey;
        this.userSkey = userData.sKey;
        this.checkId = false;
        this.checkForm = true;
        this.checkBtn = true;
        this.userType = "Update"
      }
    }
  },
  mounted () {
    this.selectUser();
  }
}
</script>

<style scoped>
  .mg-right{
    margin-right: 0.65rem !important;
  }

  .icon-md:hover{
    cursor: pointer;
  }
</style>

<style scoped>

.chat-container {
  display: flex;
  flex-direction: column;
  height: 550px;
  padding: 20px;
}

.chat-messages {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: auto;
}



/* 스크롤바 설정*/
.chat-messages::-webkit-scrollbar {
  width: 20px;
}

/* 스크롤바 막대 설정*/
.chat-messages::-webkit-scrollbar-thumb {
  background-color: #a3a4cc;
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
  border: 7px solid #2b2c40;
}

/* 스크롤바 뒷 배경 설정*/
.chat-messages::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0);
}

.userDiv{
  border : 2px solid #2b2c40;
}

.userDiv:hover{
  cursor: pointer;
}

</style>