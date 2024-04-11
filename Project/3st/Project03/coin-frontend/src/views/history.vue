<template>

  <div class="row">
    <div class="col-12 grid-margin">
      <div class="card">
        <div class="card-body">
          <h4 class="card-title">History</h4>

                <div class="row justify-content-center">
                  <div class="col-md-4">
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label text-gray">자산전체</label>
                      <div class="col-sm-9">
                        <select class="form-control" id="form-change" @change="currencyType(this.currencyTypeValue)" v-model="this.currencyTypeValue">
                          <option disabled value="Please select one">Please select one</option>
                          <option>전체</option>
                          <option>KRW</option>
                          <option>USDT</option>
                          <option>BTC</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="form-group row">
                      <label class="col-sm-3 col-form-label text-gray">거래전체</label>
                      <div class="col-sm-9">
                        <select class="form-control" id="form-control" @change="tradeType(this.tradeTypeValue)" v-model="this.tradeTypeValue">
                          <option disabled value="Please select one">Please select one</option>
                          <option>전체</option>
                          <option>매수</option>
                          <option>매도</option>
                          <option>입금</option>
                          <option>출금</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>

          <div class="table-responsive">
            <table class="table table-dark" >

              <thead class="history-head">
              <tr>
                <th> <span class="history-span-gray history-bd-kr">거래일시</span> </th>
                <th> <span class="history-span-gray history-bd-kr">자산</span> </th>
                <th> <span class="history-span-gray history-bd-kr">거래구분</span> </th>
                <th> <span class="history-span-gray history-bd-kr">거래수량</span> </th>
                <th> <span class="history-span-gray history-bd-kr">체결가격</span> </th>
                <th> <span class="history-span-gray history-bd-kr">거래금액</span> </th>
                <th> <span class="history-span-gray history-bd-kr">수수료</span> </th>
                <th> <span class="history-span-gray history-bd-kr">정산금액</span> </th>
<!--                <th> <span class="history-span-gray history-bd-kr">지갑</span> </th>-->
                <th class="min-width"> </th>
              </tr>
              </thead>

              <tbody>
              <tr v-for="data in record" :class="'history-tr ' + data.search + ' ' + data.order_currency">

                <td>
                  <span class="history-span-white history-bd-num">{{ data.transfer_date.split(" ")[0] }}</span><p/>
                  <span class="history-span-white history-bd-num">{{ data.transfer_date.split(" ")[1] }}</span>
                </td>
                <td>
                  <span class="history-span-white history-bd-kr" v-if="data.order_currency === 'USDT'">테더</span>
                  <span class="history-span-white history-bd-kr" v-else-if="data.order_currency === 'KRW'">원화</span><p/>
                  <span class="history-span-gray history-bd-num">{{data.order_currency}}</span>
                </td>
                <td> <span :class="'history-span history-bd-kr ' + data.color">{{data.search}}</span> </td>
                <td>
                  <span class="history-span-white history-bd-num" v-if="data.order_currency === 'USDT'">{{data.order_balance}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'USDT'">USDT</span>
                  <span class="history-span-white history-bd-num" v-else-if="data.order_currency === 'KRW'">{{data.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'KRW'">KRW</span>
                </td>
                <td>
                  <span class="history-span-white history-bd-num" v-if="data.order_currency === 'USDT'">{{data.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'USDT'">USDT</span>
                  <span class="history-span-white history-bd-num" v-else-if="data.order_currency === 'KRW'">{{data.order_balance}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'KRW'">KRW</span>
                </td>
                <td>
                  <span class="history-span-white history-bd-num" v-if="data.order_currency === 'USDT'">{{data.amount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'USDT'">KRW</span>
                  <span class="history-span-white history-bd-num" v-else-if="data.order_currency === 'KRW'">{{data.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")}}</span><p/>
                  <span class="history-span-gray history-bd-num" v-if="data.order_currency === 'KRW'">KRW</span>
                </td>
                <td> <span class="history-span-white history-bd-num">{{data.fee}}</span><p/><span class="history-span-gray history-bd-num">KRW</span> </td>

                <td> <span :class="'history-span-white history-bd-num ' + data.color ">{{data.finalPrice}}</span><p/>
                     <span class="history-span-gray history-bd-num">KRW</span>

                </td><th class="min-width"> </th>
              </tr>
              </tbody>

            </table>
          </div>
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
      user : JSON.parse(sessionStorage.getItem("user")),  //api 요청 할 때 유저정보 필요해 세션에서 가져옴
      record : {}, //거래내역 api 응답 데이터를 여기에 담을것임.
      tradeTypeValue : "Please select one",
      currencyTypeValue : "Please select one",
    }
  },
  methods : {
    async init(){
      //1. 들어오자마자 백엔드 컨트롤러에 거래내역 api 요청
      //2. 거래내역 api 응답값 데이터에 담아주자.
      let param = {
        id : this.user.id,
      }
      await axios.post('/api/userTransactions', param)
          .then(res => {
            let response = res.data.data;
            let finalPrice = 0;
            let color = "";

            response.forEach(function (data, index) {
              console.log(data);
              console.log(index);
              console.log(data.search);
              if (data.search === "0") {
                response[index].search = "전체";
              } else if (data.search === "1") {
                color = "history-span-blue";
                finalPrice = "-" + (parseInt(data.amount) + parseInt(data.fee)).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
                response[index].search = "매수";
              } else if (data.search === "2") {
                color = "history-span-red";
                finalPrice = "+" + (parseInt(data.amount) - parseInt(data.fee)).toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
                response[index].search = "매도";
              } else if (data.search === "3") {
                response[index].search = "출금중";
              } else if (data.search === "4") {
                color = "history-span-red";
                finalPrice = "+" + data.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")
                response[index].search = "입금";
              } else if (data.search === "5") {
                color = "history-span-blue";
                finalPrice = "-" + data.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")
                response[index].search = "출금";
              } else {
                response[index].search = "입금중";
              }

              // for(let i=finalPrice[0]; i<finalPrice.length; i++) {
              //   wallet에서 finalPrice 배열 순서 대로 더해서 돌릴 자리
              // }


              response[index].transfer_date = this.timestampString(data.transfer_date);
              response[index].finalPrice = finalPrice;
              response[index].color = color;
              // response[index].wallet = wallet;



            }, this)
            console.log(response);
            this.record = response;

            // console.log(res.data)
            // console.log(res.data.data)
            // console.log(this.record[1].transfer_date)
            // console.log(this.record[1].fee)
            // console.log(this.record[1].order_currency)
            // console.log(this.record[1].search)

            // search가 1이라면 매수
            // if(this.record[1].search == 0){
            //   this.record[1].search = "전체";
            // } else if(this.record[1].search == 1) {
            //   this.record[1].search = "매수";
            // } else if(this.record[1].search == 2){
            //   this.record[1].search = "매도";
            // } else if(this.record[1].search == 3){
            //   this.record[1].search = "출금중";
            // } else if(this.record[1].search == 4) {
            //   this.record[1].search = "입금";
            // } else if(this.record[1].search == 5) {
            //   this.record[1].search = "출금";
            // } else {
            //   this.record[1].search = "입금중";
            // }

            // // 빗썸 API에서 제공된 타임 스탬프
            // let timestamp = this.record[1].transfer_date;

            // 함수 호출하여 변환된 문자열 출력
          // console.log(this.timestampString(timestamp));
          })
          .catch(function(error){
            console.log(error);
          })
    },

    timestampString(timestamp) {
    // 밀리초로 변환 (마이크로초를 밀리초로 변환)
    let milliseconds = timestamp / 1000;

    // 밀리초를 Date 객체로 변환
    let dateObject = new Date(milliseconds);

    // 각 부분을 가져와서 형식에 맞게 조합
    let year = dateObject.getFullYear();
    let month = ('0' + (dateObject.getMonth() + 1)).slice(-2);
    let day = ('0' + dateObject.getDate()).slice(-2);
    let hours = ('0' + dateObject.getHours()).slice(-2);
    let minutes = ('0' + dateObject.getMinutes()).slice(-2);
    let seconds = ('0' + dateObject.getSeconds()).slice(-2);

    // 형식에 맞춰 문자열 생성
    let formatDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

    return formatDate;
  },
    tradeType(type){
      console.log(type);
      document.querySelectorAll(".history-tr").forEach(function (e){
        e.style.display="none";
      })

      if(type === "매수"){
        console.log(document.querySelectorAll(".매수"));
        document.querySelectorAll(".매수").forEach(function (e){
          e.style.display="";
        })
        console.log("매수가 선택됨");
      } else if(type === "매도"){
        document.querySelectorAll(".매도").forEach(function (e){
          e.style.display="";
        })
        console.log("매도가 선택됨");
      } else if(type === "입금"){
        document.querySelectorAll(".입금").forEach(function (e){
          e.style.display="";
        })
        console.log("입금이 선택됨");
      } else if(type === "출금"){
        document.querySelectorAll(".출금").forEach(function (e){
          e.style.display="";
        })
        console.log("출금이 선택됨");
      } else if(type === "전체"){
        document.querySelectorAll(".history-tr").forEach(function (e){
          e.style.display="";
        })
      }
    },
    currencyType(type){
      document.querySelectorAll(".history-tr").forEach(function (e){
        e.style.display="none";
      })
      console.log(document.querySelector(".history-tr"));
      if(type === "KRW"){
        document.querySelectorAll(".KRW").forEach(function (e){
          e.style.display="";
        })
        console.log("KRW 선택됨");
      } else if(type === "USDT"){
        document.querySelectorAll(".USDT").forEach(function (e){
          e.style.display="";
        })
        console.log("USDT 선택됨");
      } else if(type === "BTC"){
        document.querySelectorAll(".BTC").forEach(function (e){
          e.style.display="";
        })
        console.log("BTC 선택됨");
      } else if(type === "전체"){
        document.querySelectorAll(".history-tr").forEach(function (e){
          e.style.display="";
        })
      }
    },
  },

  mounted() {
    //1. 들어오자마자 백엔드 컨트롤러에 거래내역 api 요청
    this.init();
  }
}
</script>


<style scoped>
p { margin-bottom: 0.2em; }

th, td{
  text-align: right;
}

.min-width{
  width : 5% !important;
}


.history-bd-kr{
  font-weight: 700;
}

.history-bd-num{
  font-weight: 500;
}


.history-span-black{
  color: #151515;
}

.history-span-gray{
  color: #8d8d8d;
}

.history-span-blue{
  color: #4f72ff;
}

.history-span-red{
  color: #fc3d3d;
}

.history-head{
  background: black;
}
</style>