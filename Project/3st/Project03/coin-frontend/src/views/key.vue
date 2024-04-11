<template>
  <div class="row">
    <div class="col-12">
      <div class="row">
        <div class="col-md-3 grid-margin stretch-card">
        </div>
        <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-row justify-content-between">
                <h4 class="card-title mb-1">Details</h4>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="preview-list">

                    <div class="preview-item border-bottom">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-success">
                          <i class="mdi mdi-email-open-outline"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow">
                        <div class="flex-grow">
                          <h6 class="preview-subject">Confirmation Message</h6>
                          <p class="text-muted mb-0" style="font-size: 13px">* SMS 인증</p>
                        </div>
                        <div class="mr-auto text-sm-right pt-2 pt-sm-0">
                          <p class="text-muted"> </p>
                          <p class="text-muted mb-0" style="font-size: 12px">발급된 API Key를 활성화할 때 문자(SMS) 인증이 추가로 진행됩니다.</p>
                        </div>
                      </div>
                    </div>

                    <div class="preview-item border-bottom">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-primary">
                          <i class="mdi mdi-earth"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow">
                        <div class="flex-grow">
                          <h6 class="preview-subject">Official URL</h6>
                          <a class="text-muted mb-0" href="https://www.bithumb.com/customer_support/info_guide?seq=4205&categorySeq=612" style="font-size: 13px">* 공식 사이트 바로가기</a>
                        </div>
                        <div class="mr-auto text-sm-right pt-2 pt-sm-0">
                          <p class="text-muted"> </p>
                          <p class="text-muted mb-0" style="font-size: 12px">자세한 내용은 가이드를 참조하세요.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-3 grid-margin stretch-card">
        </div>
        <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-row justify-content-between">
                <h4 class="card-title mb-1">API Key</h4>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="preview-list">

                    <div class="preview-item border-bottom">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-a3a4cc">
                          <i class="mdi mdi-key-plus"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow" >
                        <div class="flex-grow" style="max-width: 300px">
                          <h6 class="preview-subject">Connect Key</h6>
                          <a class="text-muted mb-0" style="font-size: 13px" @click="originalKey('cKey')">* Connect Key 원본 보기</a>
                        </div>
                        <div class="mr-auto text-sm-right" style="width: 60%">
                          <div :class="{ shake: disabled }" >
                            <input class="typeahead message-input" type="text" placeholder="Input your Connect key" v-model="userCkey">
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="preview-item">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-info">
                          <i class="mdi mdi-key-plus"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow" >
                        <div class="flex-grow" style="max-width: 300px">
                          <h6 class="preview-subject">Secret Key</h6>
                          <a class="text-muted mb-0" style="font-size: 13px" @click="originalKey('sKey')">* Secret Key 원본 보기</a>
                        </div>
                        <div class="mr-auto text-sm-right" style="width: 60%">
                          <div :class="{ shake: disabled }" >
                            <input class="typeahead message-input" type="text" placeholder="Input your Secret key" v-model="userSkey">
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-3 grid-margin stretch-card">
        </div>
        <div class="col-md-6 grid-margin stretch-card">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-row justify-content-between">
                <h4 class="card-title mb-1">Save & Delete</h4>
              </div>
              <div class="row">
                <div class="col-12">
                  <div class="preview-list">

                    <div class="preview-item border-bottom">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-gradient-warning">
                          <i class="mdi mdi-content-save-all"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow" >
                        <div class="flex-grow">
                          <div class="flex-grow">
                            <h6 class="preview-subject">Save Key</h6>
                            <p class="text-muted" style="font-size: 13px">* API Key 저장</p>
                          </div>
                        </div>
                        <div class="mr-auto text-sm-right pt-2 pt-sm-0" style="padding-top: 10px !important;">
                          <div class="row">
                            <div class="col-5">
                              <button type="button" class="btn btn-outline-light btn-fw" style="min-height: 35px; left: 100px !important;" @click="saveKey">Save key</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="preview-item">
                      <div class="preview-thumbnail">
                        <div class="preview-icon bg-gradient-danger">
                          <i class="mdi mdi-delete"></i>
                        </div>
                      </div>
                      <div class="preview-item-content d-sm-flex flex-grow" >
                        <div class="flex-grow">
                          <h6 class="preview-subject">Delete key</h6>
                          <p class="text-muted" style="font-size: 13px">* API Key 삭제</p>
                        </div>
                        <div class="mr-auto text-sm-right pt-2 pt-sm-0" style="padding-top: 10px !important;">
                          <div class="row">
                            <div class="col-5">
                              <button type="button" class="btn btn-outline-light btn-fw" style="min-height: 35px; left: 100px !important;" @click="updateKey('', '', 'Delete')">Delete key</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import router from "@/router";

export default {
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem("user")),
      userCkey : "", //cKey
      userSkey : "",  //sKey
      keyYn : "N",  //key 검증 YN
      original : {},
    }
  },
  methods : {
    async saveKey(){
      if(this.userCkey !== "" && this.userSkey !== "" && this.userCkey !== null && this.userSkey !== null){
        let param = {
          cKey : this.original.cKey.length > 0 && this.userCkey.indexOf("*") >=0 ? this.original.cKey : this.userCkey,
          sKey : this.original.sKey.length > 0 && this.userSkey.indexOf("*") >=0 ? this.original.sKey : this.userSkey,
          currency: "ALL",
        }
        console.log(param)
        axios.post('/api/balance', param)
            .then(res => {
              if(res.data !== null && res.data.status === "0000"){
                let key = Object.keys(res.data.data);
                let val = Object.values(res.data.data);
                let krw = 0;
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
                this.keyYn = "Y";
                this.updateKey(krw, param, "Update");
              }else{
                alert("올바르지 않은 API KEY입니다.")
              }
            })
            .catch(function(error){
              console.log(error);
            })
      }else if((this.userCkey === "" && this.userSkey !== "") || (this.userCkey !== "" && this.userSkey === "") || (this.userCkey === null && this.userSkey !== null) || (this.userCkey !== null && this.userSkey === null) || (this.userCkey === "" && this.userSkey === "") || (this.userCkey === null && this.userSkey === null)) {
        alert("Connect key와 Secret key 모두 입력 바랍니다.");
      }
    },
    updateKey(krw, data, type){
      let param = {
        id : this.user.id,
        cKey : type === "Update" ? data.cKey : "",
        sKey : type === "Update" ? data.sKey : "",
        keyYn : type === "Update" ? this.keyYn : "N",
      }
      if(type === "Delete"){
        if(!confirm("API Key를 삭제하시겠습니까?")){
          return;
        }
      }

      axios.post('/api/userKeyUpdate', param)
          .then(res => {
            if(type === "Update"){
              this.user.keyYn = "Y";
              alert("API KEY 등록이 완료되었습니다.\nKRW : " + krw.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + "\n금액 확인 바랍니다.");
            }else if(type === "Delete"){
              this.user.keyYn = "N";
              alert("API KEY 삭제가 완료되었습니다.");
              this.userCkey = "";
              this.userSkey = "";
            }
            sessionStorage.setItem("user",JSON.stringify(this.user));
          })
          .catch(function(error){
            console.log(error);
          })
    },
    async userKey(){
      let param = {
        id : this.user.id,
      }
      await axios.post('/api/userKey', param)
          .then(res => { //
            this.userCkey = res.data[0].cKey.substring(0, res.data[0].cKey.length/2) + res.data[0].cKey.substring(res.data[0].cKey.length/2, res.data[0].cKey.length).replace(/\w/g,"*");
            this.userSkey = res.data[0].sKey.substring(0, res.data[0].sKey.length/2) + res.data[0].sKey.substring(res.data[0].sKey.length/2, res.data[0].sKey.length).replace(/\w/g,"*");
            this.original = res.data[0];
            console.log(res.data[0].cKey.length)
          })
          .catch(function(error){
            console.log(error);
          })
    },
    originalKey(key){
      if(key === "sKey" && this.userSkey.indexOf("*") !== -1){
        this.userSkey = this.original.sKey;
      }else if(key === "cKey" && this.userCkey.indexOf("*") !== -1){
        this.userCkey = this.original.cKey;
      }else{
        alert("원본 key 입니다.")
      }
    },
  },
  mounted() {
    this.userKey()
  }
}

</script>

<style scoped>
input[type="text"] {
  flex: 1;
  padding: 8px;
  border: 1px solid #a3a4cc;
  border-radius: 5px;
  margin-right: 10px;
  color: #a3a4cc;
  width: 100%;
  height: 10%;
}
</style>



