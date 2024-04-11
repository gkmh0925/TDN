<template>
    

    <section class="rooms-section spad">
        <div class="breadcrumb-section">
            <div class="container">
                <h2>아이디 찾기</h2>
            </div>
            <div class="row">
                <div class="col-lg-7">                    
                    <div id="find_id">
                        <div class="checkout__input">
                            <p>이름<span>*</span></p>
                            <input type="text" class="id_find_name" v-model="form.name">
                        </div>
                        <div class="checkout__input">
                            <p>전화번호<span>*</span></p>
                            <input type="text" class="id_find_phone" v-model="form.phone" @keyup="phone(form.phone)" maxlength="13"
                            oninput="this.value = this.value.replace(/[^-0-9]/g, '')">
                        </div>
                        <button type="button" class="orderBtn" @click="idFindButton">아이디 찾기</button>
                    </div>

                    <div id="find_pw">
                        <div class="find_text">
                            <h3>비밀번호 찾기</h3>
                        </div>
                        <div class="checkout__input">
                            <p>아이디<span>*</span></p>
                            <input type="text" class="pw_find_id" v-model="form.id">
                        </div>
                        <div class="checkout__input">
                            <p>전화번호<span>*</span></p>
                            <input type="text" class="pw_find_phone" v-model="form.phone1"  @keyup="phone1(form.phone1)" maxlength="13"
                            oninput="this.value = this.value.replace(/[^-0-9]/g, '')">
                        <button type="button" class="orderBtn" @click="pwFindButton">비밀번호 찾기</button>
                          </div>
                        
                        
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>
<script>
import {defineComponent} from "vue";
import axios from 'axios';
export default defineComponent({
  data() {
    return {
      form :{
        id : "",
        password : "",
        mail : "",
        name : "",
        phone : "",
        phone1 : "",
        checkedtype : [],
      }
    };
  },methods: {
    phone(val){
      let res = val;
      if(res.length === 3 || res.length === 8){
        this.form.phone = res + "-";
      } 
    },
    phone1(val){
      let res = val;
      if(res.length === 3 || res.length === 8){
        this.form.phone1 = res + "-";
      } 
    },
    async idFindButton(){
      try {
        let data = await axios.post("http://localhost:3000/find",{
           UserName: this.form.name,
           UserPhNum1: this.form.phone.replaceAll("-","").substring(0,3),
           UserPhNum2: this.form.phone.replaceAll("-","").substring(3,7),
           UserPhNum3: this.form.phone.replaceAll("-","").substring(7,11)       
         });
         if(this.form.name ===data.data[0].UserName && 
         this.form.phone.replaceAll("-","").substring(0,3) === data.data[0].UserPhNum1&&
         this.form.phone.replaceAll("-","").substring(3,7) === data.data[0].UserPhNum2&&
         this.form.phone.replaceAll("-","").substring(7,11) === data.data[0].UserPhNum3){
          alert("아이디는 : " + data.data[0].UserId)
         }
         
          
        //  }
       } catch (err) {
        alert("없다");
       }
    },
     async pwFindButton(){
       try {
      let data = await axios.post("http://localhost:3000/findPw",{
           UserId: this.form.id,
           UserPhNum1: this.form.phone1.replaceAll("-","").substring(0,3),
           UserPhNum2: this.form.phone1.replaceAll("-","").substring(3,7),
           UserPhNum3: this.form.phone1.replaceAll("-","").substring(7,11)
         });
         if(this.form.id ===data.data[0].UserId && 
         this.form.phone1.replaceAll("-","").substring(0,3) === data.data[0].UserPhNum1&&
         this.form.phone1.replaceAll("-","").substring(3,7) === data.data[0].UserPhNum2&&
         this.form.phone1.replaceAll("-","").substring(7,11) === data.data[0].UserPhNum3){
          alert("비밀번호는 : " + data.data[0].UserPw)
         }
       } catch (err) {
        alert("없다");
       }
      }
    }}
  )

</script>
<style>
.signin_id_pw {
    float: right;
}

.signin_id_pw a{
    color: rgb(85, 85, 85);
    font-size: 14px;
    margin-right: 25px;
}
.log_out {
    color: #000;
    font-size: 15px;
    font-weight: 600;
  }
  .log_out:hover {
    color: #000;
  }
  .log_out:focus {
    color: #000;
  }

input:active{
border: 2px solid #fdd8a0;;
}
input:focus{
border: 2px solid #fdd8a0;;
}

#find_id {
    margin-top: 50px;
    margin-bottom: 30px;
    margin-left:490px
    
}

#find_pw {
    border-top: 1px solid rgba(0, 0, 0, 0.623);
    padding-top: 30px;
    margin-left:490px
}

.find_text {
    display: inline-block;
    border-bottom: 1px solid rgb(192, 192, 192);
}

.find_text h3{
    font-size: 25px;
    color: rgb(48, 48, 48);
}

.checkout__input p{
    margin-top: 20px;
}



.breadcrumb__text h2 {
    font-size: 40px;
    color: #000000;
    text-align: left;
}
.orderBtn{
  display: block;
  font-size: 14px;
  text-transform: uppercase;
  border: 1px solid #dfa974;
  border-radius: 2px;
  color: #dfa974;
  font-weight: 500;
  background: transparent;
  width: 100%;
  height: 46px;
  margin-top: 20px;
  
}
</style>