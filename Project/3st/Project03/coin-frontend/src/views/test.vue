<template>
  <div class="row">
    <div class="col-md-9 grid-margin stretch-card" style="padding-right: 0.05rem; !important; padding-left: 0.05rem; !important;">
      <!-- Chart -->
      <div class="col-12">
        <div class="card" style="height: 500px">
          <div class="card-body">
            <h4 class="card-title">Chart</h4>
            <Chart :options="{
              autosize: true,
              symbol: 'BITHUMB:USDTKRW',
              interval: '240',
              timezone: 'Asia/Seoul',
              theme: 'dark',
              style: '1',
              locale: 'kr',
              enable_publishing: false,
              allow_symbol_change: true,
              save_image: false,
              calendar: false,
              support_host: 'https://www.tradingview.com'
            }" style="height: 92%; width: 100%"/>
          </div>
        </div>

        <!-- Standard Buy -->
        <div class="row mt-4" >
          <div class="col-md-6 grid-margin stretch-card">
            <div class="card">
              <div class="card-body">
                <h4 class="card-title" style="font-weight: 600">매수</h4>
                <form class="forms-sample mt-4">
                  <div class="form-group">
                    <label class="inputLabel">현재 총 보유 자산 (KRW)</label>
                    <input type="text" class="form-control" id="totalKRW" style="text-align: right" :value=krw disabled>
                  </div>
                  <div class="form-group mt-4">
                    <label class="inputLabel">구매 방법</label><br>
                    <div class="btn-group" role="group" aria-label="Basic example">
                      <button id="buyLimit" type="button" class="btn btn-outline-secondary"
                              @click="choiceContent('choiceBuyWayId', 'buyLimit')">지정가
                      </button>
                      <button id="buyMarket" type="button" class="btn btn-outline-secondary"
                              @click="choiceContent('choiceBuyWayId', 'buyMarket')">시장가
                      </button>
                      <button id="buyCond" type="button" class="btn btn-outline-secondary"
                              @click="choiceContent('choiceBuyWayId', 'buyCond')">예약가
                      </button>
                    </div>
                    <input type="text" class="form-control mt-2" id="buyPrice" style="text-align: right"
                           v-model="buyPrice" placeholder="주문가격을 입력해주세요."
                           :disabled="this.choiceBuyWayId === 'buyMarket' || this.choiceBuyWayId === ''">
                    <input type="text" class="form-control mt-1" id="watchBuyPrice" style="text-align: right"
                           v-model="watchBuyPrice" placeholder="감시가격을 입력해주세요 (예약가 선택 시)"
                           :disabled="this.choiceBuyWayId !== 'buyCond'">
                  </div>
                  <div class="form-group mt-4">
                    <label class="inputLabel">구매 개수</label>
                    <input type="text" class="form-control" style="text-align: right" id="buyCount"
                           @input="calTotalBuyPrice()" v-model="buyCount" :disabled="this.choiceBuyPercentId !== ''">
                  </div>
                  <div class="form-group mt-4">
                    <label class="inputLabel">퀵메뉴</label><br>
                    <div class="btn-group quickMenu" role="group" aria-label="Basic example">
                      <button id="ten" type="button" class="btn btn-outline-secondary"
                              v-bind:disabled="this.choiceBuyWayId === 'buyMarket'"
                              @click="choiceContent('choiceBuyPercentId', 'ten')">10%
                      </button>
                      <button id="twentyfive" type="button" class="btn btn-outline-secondary"
                              v-bind:disabled="this.choiceBuyWayId === 'buyMarket'"
                              @click="choiceContent('choiceBuyPercentId', 'twentyfive')">25%
                      </button>
                      <button id="fifty" type="button" class="btn btn-outline-secondary"
                              v-bind:disabled="this.choiceBuyWayId === 'buyMarket'"
                              @click="choiceContent('choiceBuyPercentId', 'fifty')">50%
                      </button>
                      <button id="hundred" type="button" class="btn btn-outline-secondary"
                              v-bind:disabled="this.choiceBuyWayId === 'buyMarket'"
                              @click="choiceContent('choiceBuyPercentId', 'hundred')">100%
                      </button>
                    </div>
                  </div>
                  <div class="form-group mt-4">
                    <label class="inputLabel">총 구매 금액 (KRW)</label>
                    <input type="text" class="form-control" id="totalBuyPrice" v-model="totalBuyPrice"
                           style="text-align: right" placeholder="I am 거지예요 (ㅇ_<)-★" disabled>
                  </div>
                  <div style="text-align: center; padding-top: 8px;">
                    <button class="btn btn-outline-light btn-md btn-execution" type="button" style="width: 120px;"
                            @click="classifyBuyType" :disabled="executionDisabled">Execution
                    </button>
                    &nbsp&nbsp&nbsp&nbsp
                    <button class="btn btn-outline-light btn-md" type="button" style="width: 120px;"
                            @click="resetbuy()">Reset
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <!-- Standard Sell -->
          <div class="col-md-6 grid-margin stretch-card">
            <div class="col-12 " style="padding-right: 0.05rem; !important; padding-left: 0.05rem; !important;">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title" style="font-weight: 600">매도</h4>
                  <form class="forms-sample mt-4">
                    <div class="form-group">
                      <label class="inputLabel">현재 총 보유 테더 (USDT)</label>
                      <input type="text" class="form-control" id="totalUSDT" style="text-align: right" :value=usdt
                             disabled>
                    </div>
                    <div class="form-group mt-4">
                      <label class="inputLabel">청산 방법</label><br>
                      <div class="btn-group" role="group" aria-label="Basic example">
                        <button id="sellLimit" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellWayId', 'sellLimit')">지정가
                        </button>
                        <button id="sellMarket" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellWayId', 'sellMarket')">시장가
                        </button>
                        <button id="sellCond" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellWayId', 'sellCond')">예약가
                        </button>
                      </div>
                      <input type="text" class="form-control mt-2" id="sellPrice" style="text-align: right"
                             v-model="sellPrice" placeholder="주문가격을 입력해주세요."
                             :disabled="this.choiceSellWayId === 'sellMarket' || this.choiceSellWayId === ''">
                      <input type="text" class="form-control mt-1" id="watchSellPrice" style="text-align: right"
                             v-model="watchSellPrice" placeholder="감시가격을 입력해주세요 (예약가 선택 시)"
                             :disabled="this.choiceSellWayId !== 'sellCond'">
                    </div>

                    <div class="form-group mt-4">
                      <label class="inputLabel">매도 개수</label>
                      <input type="text" class="form-control" style="text-align: right" id="sellCount"
                             v-model="sellCount">

                    </div>
                    <div class="form-group mt-4">
                      <label class="inputLabel">퀵메뉴</label><br>
                      <div class="btn-group quickMenu" role="group" aria-label="Basic example">
                        <button id="sellTen" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellPercentId', 'sellTen')">10%
                        </button>
                        <button id="sellTwentyfive" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellPercentId', 'sellTwentyfive')">25%
                        </button>
                        <button id="sellFifty" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellPercentId', 'sellFifty')">50%
                        </button>
                        <button id="sellHundred" type="button" class="btn btn-outline-secondary"
                                @click="choiceContent('choiceSellPercentId', 'sellHundred')">100%
                        </button>
                      </div>
                    </div>

                    <div class="form-group mt-4">
                      <label class="inputLabel">총 매도 금액 (KRW)</label>
                      <!--                      <input type="text" class="form-control" id="totalSellPrice" v-model="totalSellPrice" style="text-align: right" :value=this.decimalPointFix(sellCount*sellPrice) placeholder="꺄아ㅏㅏ아악" disabled>-->
                      <input type="text" class="form-control" id="totalSellPrice" v-model="totalSellPrice"
                             style="text-align: right" :value=this.decimalPointFix(this.calTotalSellPrice())
                             placeholder="꺄아ㅏㅏ아악" disabled>
                    </div>

                    <div style="text-align: center; padding-top: 8px;">
                      <button class="btn btn-outline-light btn-md btn-execution" type="button" style="width: 120px;"
                              @click="classifySellType()" :disabled="executionDisabled">Execution
                      </button>
                      &nbsp&nbsp&nbsp&nbsp
                      <button class="btn btn-outline-light btn-md" type="button" style="width: 120px;"
                              @click="resetSell()">Reset
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>

          <!-- Personal Sell -->
          <div class="col-md-6 stretch-card" style="align-content: center !important;">
            <div class="card">
              <div class="card-body" :style="'font-weight: 600; border: 2px solid #' + personalStyle">
                <h4 class="card-title" style="font-weight: 600">우리가 원하는 그거</h4>
                <form class="forms-sample mt-4">
                  <div class="form-group">
                    <label class="inputLabel">최소 청산 금액 지정</label>
                    <input type="text" class="form-control" id="lowPrice" v-model="lowPrice" style="text-align: right;"
                           placeholder="희망 매도 최소 금액">
                  </div>
                  <div class="form-group">
                    <label class="inputLabel">수량 지정</label>
                    <input type="text" class="form-control" id="lowCount" v-model="lowCount" style="text-align: right;"
                           placeholder="수량">
                  </div>
                  <div class="form-group mt-5">
                    <label class="inputLabel">최대 청산 금액 지정</label>
                    <input type="text" class="form-control" id="hightPrice" v-model="hightPrice"
                           style="text-align: right;" placeholder="희망 매도 최대 금액">
                  </div>
                  <div class="form-group">
                    <label class="inputLabel">수량 지정</label>
                    <input type="text" class="form-control" id="hightCount" v-model="hightCount"
                           style="text-align: right;" placeholder="수량">
                  </div>
                  <div style="text-align: center; padding-top: 8px;">
                    <button class="btn btn-outline-light btn-md btn-execution" type="button" style="width: 120px;"
                            @click="personalInsert('personal')" :disabled="executionDisabled">Execution
                    </button>
                    &nbsp&nbsp&nbsp&nbsp
                    <button class="btn btn-outline-light btn-md" type="button" style="width: 120px;"
                            @click="resetTrade()">Reset
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <!-- Kimchi -->
          <div class="col-md-6 stretch-card" style="align-content: center !important;">
            <div class="card">
              <div class="card-body" :style="'font-weight: 600; border: 2px solid #' + kimchiStyle" style="font-weight: 600">
                <h4 class="card-title" >Premium : {{kimchi}}%</h4>
                <form class="forms-sample mt-4">
                  <div class="form-group">
                    <label class="inputLabel">매수 Premium</label>
                    <input type="text" class="form-control" v-model="kimchiBids" style="text-align: right" placeholder="단위='%'">
                  </div>
                  <div class="form-group">
                    <label class="inputLabel">매수 수량</label>
                    <input type="text" class="form-control" v-model="kimchiBidsCount" style="text-align: right" placeholder="수량">

                  </div>
                  <div class="form-group mt-5">
                    <label class="inputLabel">매도 Premium</label>
                    <!--  전체 보유 개수 반영되도록  -->
                    <input type="text" class="form-control" style="text-align: right" v-model="kimchiAsks" placeholder="단위='%'">
                  </div>
                  <div class="form-group">
                    <label class="inputLabel">매도 수량</label>
                    <input type="text" class="form-control" v-model="kimchiAsksCount" style="text-align: right" placeholder="수량">
                  </div>
                  <div style="text-align: center; padding-top: 8px;">
                    <button class="btn btn-outline-light btn-md btn-execution" id="classifyKimchiType" type="button" style="width: 120px;"  @click="classifyKimchiType()" :disabled="executionDisabled">Execution</button>&nbsp&nbsp&nbsp&nbsp
                    <button class="btn btn-outline-light btn-md" type="button" style="width: 120px;" @click="resetkimchi()">Reset</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>

        <!-- TradingResult -->
        <div class="row mt-4">
          <div class="col-md-12 grid-margin stretch-card">
            <div class="card" style="color: #b6afc8;">
              <div class="card-body" style="line-height: 2;">
                <h4 class="card-title" style="font-weight: 600">TradingResult
                  <div style="text-align: right" >
                    <select class="form-control-sm" v-model="this.resultType" style="font-size: 13px; padding: 0 !important; height: 1.6rem; padding-right: 2px !important; padding-left: 4px !important;">
                      <option>Personal Trade Result</option>
                      <option>Premium Trade Result</option>
                    </select>
                  </div>
                </h4>

                <div class="table-responsive">
                  <table class="table-hover" v-if="this.resultType === 'Personal Trade Result'">
                    <thead>
                    <tr>
                      <th> No</th>
                      <th> 주문일자</th>
                      <th> 체결일자</th>
                      <th> 체결번호</th>
                      <th> Low Price</th>
                      <th> Low Count</th>
                      <th> Hight Price</th>
                      <th> Hight Count</th>
                      <th> Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr style="font-size: 14px" v-for="(data, index) in this.personalTradeList" :key="index"
                        @click="clickTradeList(index, 'personalTradeList')">
                      <td> {{ index + 1 }}</td>
                      <td> {{ timestampString(data.tradeDate) }}</td>
                      <td> {{ timestampString(data.orderDate) || "-" }}</td>
                      <td> {{ data.orderId || "-" }}</td>
                      <td> {{ data.lowPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.lowCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.hightPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.hightCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.status }}</td>
                    </tr>
                    </tbody>
                  </table>

                  <table class="table-hover" v-if="this.resultType === 'Premium Trade Result'">
                    <thead>
                    <tr>
                      <th> No</th>
                      <th> 주문일자</th>
                      <th> 체결일자</th>
                      <th> 체결번호</th>
                      <th> BID persent</th>
                      <th> BID Count</th>
                      <th> ASK persent</th>
                      <th> ASK Count</th>
                      <th> Status</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr style="font-size: 14px" v-for="(data, index) in this.kimchiTradeList" :key="index"
                        @click="clickTradeList(index, 'kimchiTradeList')">
                      <td> {{ index + 1 }}</td>
                      <td> {{ timestampString(data.tradeDate)}}</td>
                      <td> {{ timestampString(data.orderDate) || "-" }}</td>
                      <td> {{ data.orderId  || "-" }}</td>
                      <td> {{ data.tradeBidPersent }}%</td>
                      <td> {{ data.tradeBidCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.tradeAskPersent }}%</td>
                      <td> {{ data.tradeAskCount.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</td>
                      <td> {{ data.tradeStatus }}</td>
                    </tr>
                    </tbody>
                  </table>


                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- orderbook -->
    <div class="col-md-3 stretch-card" style="height: 86%; width: 350px !important; position: fixed; right: 1%; z-index: 8"><!-- /* position: fixed; left: 78%*/-->
      <div class="card">
        <div class="card-body ">
          <h4 class="card-title mb-1" style="font-weight: 600">OrderBook</h4>
          <div class="table-responsive order-book-table-responsive">
            <table class="table order-book-table">
              <thead>
              <tr class="order-book-th">
                <th class="order-book-th"> 가격 (KRW) </th>
                <th class="order-book-th"> 수량(USDT) </th>
              </tr>
              </thead>
              <tbody>

              <tr class="order-book-tr" v-for=" ask in asks" :id="'bd-' + ask.price.toString()">

                <td class="order-book-td-asks" v-if="ask.price - prev_closing_price < 0" style="color: #5492fa;">
                  <div class="text-md-center text-xl-right">
                    {{ ask.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (ask.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-asks" v-else-if="ask.price - prev_closing_price > 0" style="color: #f56c7e">
                  <div class="text-md-center text-xl-right">
                    {{ ask.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (ask.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-asks" v-else-if="ask.price - prev_closing_price === 0">
                  <div class="text-md-center text-xl-right">
                    {{ ask.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (ask.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-asks">
                  <div style="position: absolute; color: #2b2c40;">{{ ask.quantity.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}</div>
                  <div class="progress progress-asks">
                    <div class="progress-bar bg-asks" role="progressbar" :style="'width: ' + (parseInt(ask.quantity) / topQuantityAsks * 100).toString().split('.')[0] + '%'" :aria-valuenow="(parseInt(ask.quantity) / topQuantityAsks * 100).toString().split('.')[0]" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                </td>
              </tr>


              <tr class="order-book-tr" v-for=" bid in bids" :id="'bd-' + bid.price.toString()">
                <td class="order-book-td-bids" v-if="bid.price - prev_closing_price < 0" style="color: #5492fa">
                  <div class="text-md-center text-xl-right">
                    {{ bid.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (bid.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-bids" v-else-if="bid.price - prev_closing_price > 0" style="color: #f56c7e">
                  <div class="text-md-center text-xl-right">
                    {{ bid.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (bid.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-bids" v-else-if="bid.price - prev_closing_price === 0">
                  <div class="text-md-center text-xl-right">
                    {{ bid.price.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") }}&nbsp&nbsp&nbsp&nbsp
                    {{ ((100 / prev_closing_price).toFixed(2) * (bid.price - prev_closing_price)).toFixed(2) }}%
                  </div>
                </td>
                <td class="order-book-td-bids">
                  <div style="position: absolute; color: #2b2c40">{{ bid.quantity.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")  }}</div>
                  <div class="progress progress-bids">
                    <div class="progress-bar bg-bids" role="progressbar" :style="'width: ' + (parseInt(bid.quantity) / topQuantityBids * 100).toString().split('.')[0] + '%'" :aria-valuenow="(parseInt(bid.quantity) / topQuantityBids * 100).toString().split('.')[0]" aria-valuemin="0" aria-valuemax="100">  </div>
                  </div>
                </td>
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
import { Chart,} from 'vue-tradingview-widgets';
import { defineComponent } from 'vue'
import axios from "axios";

export default defineComponent({
  name: 'App',
  components: {
    Chart,
  },
  data() {
    return {
      asks : {},  // 매도 데이터
      bids : {}, // 매수 데이터
      topQuantityAsks : 0, // 매도 최고수량 (그래프 채우기 위함)
      topQuantityBids : 0, // 매수 최고수량 (그래프 채우기 위함)
      prev_closing_price : 0, // 전일 종가
      kimchi : 0,// 지금 김치 가격
      USDTPrice : 0, //USDT 가격
      USDTP : 0, //임시
      kimchiBids : "", // 김치 매수 %
      kimchiAsks : "", // 김치 매도 %
      kimchiBidsCount : "", // 김치 매수 수량
      kimchiAsksCount : "", // 김치 매도 수량
      kimchiStyle : "2b2c40", // 김프 보더
      personalStyle : "2b2c40", // 우리가원하는그것 보더
      executionDisabled : false, // executionDisabled false true
      resultType : "Personal Trade Result",

      choiceBuyWayId: "", // 매수가 선택방법
      choiceBuyPercentId: "", // 매수 퀵메뉴 선택내역
      choiceSellPercentId: "", // 매도 퀵메뉴 선택내역
      choiceSellWayId: "", // 이득청산 매도가 선택방법
      user: JSON.parse(sessionStorage.getItem("user")), // 세션스토리지에 저장되어있는 로그인유저정보
      krw: 0, // 현재 총 보유 자산(tatalKrw)
      usdt: 0, // 현재 총 보유 테더
      tradeCount: "",
      trPrice: 0,
      buyPrice : "",
      buyCount : "",
      sellPrice : "",
      sellCount : "",
      lossPrice : "",
      stopLossCount : "",
      tradePrice : "",
      totalBuyPrice : "",
      totalSellPrice: "",
      watchBuyPrice : "",
      watchSellPrice: "",
      tradePlaceType: "",
      lowPrice: "",
      lowCount: "",
      hightPrice: "",
      hightCount: "",
      personalTradeList: {},
      kimchiTradeList : {},
    }
  },

  methods: {
    // 구매방법, 청산방법, 퀵메뉴 선택에 따른 style
    choiceContent(type, id) {
      // choiceBuyWayId : 매수가 선택방법 선택에 따라 이펙트
      // choiceBuyPercentId : 매수 퀵메뉴 선택내역 선택에 따라 이펙트
      // choiceSellPercentId : 매도 퀵메뉴 선택내역 선택에 따라 이펙트
      // choiceSellWayId : 매도가 선택방법 선택에 따라 이펙트
      if (this[type] === "") {
        document.querySelector("#" + id).style.color = "#212529";
        document.querySelector("#" + id).style.backgroundColor = "#e4eaec";
        document.querySelector("#" + id).style.borderColor = "#e4eaec";
        this[type] = id;
      }

      if (this[type] !== id) {
        document.querySelector("#" + id).style.color = "#212529";
        document.querySelector("#" + id).style.backgroundColor = "#e4eaec";
        document.querySelector("#" + id).style.borderColor = "#e4eaec";

        document.querySelector("#" + this[type]).style.color = "#e4eaec";
        document.querySelector("#" + this[type]).style.backgroundColor = "#2b2c40";
        document.querySelector("#" + this[type]).style.border = "1px solid #e4eaec";
        this[type] = id;
      }

      if (type === "choiceBuyPercentId") {
        this.calBuyCount();
        this.calTotalBuyPrice();

      }
      if (type === "choiceSellPercentId") {
        this.calSellCount();
      }
    },

    // 매수 초기화
    resetbuy() {
      if (this.choiceBuyWayId !== "") {
        document.querySelector("#" + this.choiceBuyWayId).style.color = "#e4eaec";
        document.querySelector("#" + this.choiceBuyWayId).style.backgroundColor = "#2b2c40";
        document.querySelector("#" + this.choiceBuyWayId).style.border = "1px solid #e4eaec";
        this.choiceBuyWayId = "";
      }
      if (this.choiceBuyPercentId !== "") {
        document.querySelector("#" + this.choiceBuyPercentId).style.color = "#e4eaec";
        document.querySelector("#" + this.choiceBuyPercentId).style.backgroundColor = "#2b2c40";
        document.querySelector("#" + this.choiceBuyPercentId).style.border = "1px solid #e4eaec";
        this.choiceBuyPercentId = "";
      }
      // v-model 사용
      this.buyPrice = "";
      this.buyCount = "";
      this.totalBuyPrice = "";
      this.watchBuyPrice = "";
    },

    // 매도 초기화
    resetSell() {
      if (this.choiceSellWayId !== "") {
        document.querySelector("#" + this.choiceSellWayId).style.color = "#e4eaec";
        document.querySelector("#" + this.choiceSellWayId).style.backgroundColor = "#2b2c40";
        document.querySelector("#" + this.choiceSellWayId).style.border = "1px solid #e4eaec";
        this.choiceSellWayId = "";
      }
      if (this.choiceSellPercentId !== "") {
        document.querySelector("#" + this.choiceSellPercentId).style.color = "#e4eaec";
        document.querySelector("#" + this.choiceSellPercentId).style.backgroundColor = "#2b2c40";
        document.querySelector("#" + this.choiceSellPercentId).style.border = "1px solid #e4eaec";
        this.choiceSellPercentId = "";
      }
      // v-model 사용
      this.sellPrice = "";
      this.sellCount = "";
      this.totalSellPrice = "";
      this.watchSellPrice = "";
    },

    // 우리가 원하는 그거 초기화
    resetTrade() {
      this.lowPrice = "";
      this.lowCount = "";
      this.hightPrice = "";
      this.hightCount = "";
    },

    // 현재 총 보유 자산(toatlKRW), 현재 총 보유 테더수량(totalUSDT) 가져오기
    balance() {
      this.krw = parseFloat(sessionStorage.getItem("total_krw")).toFixed(8);
      this.usdt = parseFloat(sessionStorage.getItem("total_usdt")).toFixed(8);
      if (parseInt(this.usdt) === 0) {
        this.usdt = "";
      }
    },

    // Execution 눌리면 매수 구매방법부터 확인
    classifyBuyType() {
      if (this.choiceBuyWayId === "buyLimit") {
        this.tradePlace(this.buyPrice, this.buyCount, "bid"); // 지정가 매매 로직
      } else if (this.choiceBuyWayId === "buyMarket") {
        this.tradeMarket(this.buyCount, "/trade/market_buy"); // 시장가 매매 로직
      } else if (this.choiceBuyWayId === "buyCond") {
        this.tradeStopLimit(this.buyCount, this.buyPrice, this.watchBuyPrice, "bid"); // 예약가 매매 로직
      } else if (this.choiceBuyWayId === "") {
        alert("선택하고 입력을 해야 매수를 실행하지");
      }
    },

    // 매도 방법 구분
    classifySellType() {
      if (this.choiceSellWayId === "sellLimit") {
        this.tradePlace(this.sellPrice, this.sellCount, "ask"); // 지정가 매매 로직
      } else if (this.choiceSellWayId === "sellMarket") {
        this.tradeMarket(this.sellCount, "/trade/market_sell"); // 시장가 매매 로직
      } else if (this.choiceSellWayId === "sellCond") {
        this.tradeStopLimit(this.sellCount, this.sellPrice, this.watchSellPrice, "ask"); // 예약가 매매 로직
      } else if (this.choiceSellWayId === "") {
        alert("선택하고 입력을 해야 매도를 실행하지");
      }
    },

    // 지정가 매매
    async tradePlace(price, count, type) {
      let param = { // param에 backend으로 보낼 변수 값들 object 형태로 저장
        id: this.user.id,
        tradeCount: count,
        tradePrice: price,
        tradeType: type,
      }

      await axios.post('/api/tradePlace', param)
          .then(res => {
            console.log(res.data);
            console.log(res.data.order_id);
          })
          .catch(function (error) {
            console.log("tradePlace error : " + error);
          })
      // 다 돌았으면 초기화 ㄱㄱ
      if (type === "bid") {
        this.resetbuy();  // 매수 초기화
      } else if (type === "ask") {
        this.resetSell();  // 매도 초기화
      }
    },

    // 시장가 매매
    async tradeMarket(count, url) {
      let param = {
        id: this.user.id,
        tradeCount: count,
        url: url,
      }

      await axios.post('/api/tradeMarket', param)
          .then(res => {
            console.log(res.data);
            console.log(res.data.order_id);
          })
          .catch(function (error) {
            console.log("tradeMarket error : " + error);
          })
      // 다 돌았으면 초기화 ㄱㄱ
      if (url === "/trade/market_buy") {
        this.resetbuy();  // 매수 초기화
      } else if (url === "/trade/market_sell") {
        this.resetSell();  // 매도 초기화
      }
    },

    // 예약가 매매
    async tradeStopLimit(count, price, watchPrice, type) {
      let param = {
        id: this.user.id,
        tradeCount: count,
        tradePrice: price,
        watchPrice: watchPrice,
        tradeType: type,
      }

      await axios.post('/api/tradeStopLimit', param)
          .then(res => {
            console.log(res.data);
            console.log(res.data.order_id);
          })
          .catch(function (error) {
            console.log("tradeStopLimit error : " + error);
          })
      // 다 돌았으면 초기화 ㄱㄱ
      if (type === "bid") {
        this.resetbuy();  // 매수 초기화
      } else if (type === "ask") {
        this.resetSell();  // 매도 초기화
      }
    },

    // 소수점 자리수 제한
    decimalPointFix(flaot) {
      return parseFloat(flaot).toFixed(8);
    },

    // 퀵메뉴 value값 가져오기
    selectQuickMenu(id) {
      let choicePercentValue = 0;

      if (id === "ten" || id === "sellTen") {
        choicePercentValue = 0.1;
      } else if (id === "twentyfive" || id === "sellTwentyfive") {
        choicePercentValue = 0.25;
      } else if (id === "fifty" || id === "sellFifty") {
        choicePercentValue = 0.5;
      } else if (id === "hundred" || id === "sellHundred") {
        choicePercentValue = 1.0;
      }
      return choicePercentValue;
    },

    // 퀵메뉴 선택 시 매수 개수 계산
    calBuyCount() {
      if (this.choiceBuyPercentId !== "" && this.choiceBuyWayId !== "") {
        this.buyCount = this.decimalPointFix((this.krw * this.selectQuickMenu(this.choiceBuyPercentId)) / parseFloat(this.buyPrice));
      } else {
        this.buyCount = "";
      }
    },

    // 퀵메뉴 선택 시 매도 개수 계산
    calSellCount() {
      if (this.choiceSellPercentId !== "") {
        this.sellCount = this.decimalPointFix(this.usdt * this.selectQuickMenu(this.choiceSellPercentId))
      } else {
        this.sellCount = "";
      }
      return this.sellCount;
    },

    // 퀵메뉴 선택 안했을 때 구매개수 입력에 따른 총 구매 금액
    calTotalBuyPrice() {
      this.totalBuyPrice = this.decimalPointFix(this.buyPrice * this.buyCount);
    },

    // 총 매도 금액
    calTotalSellPrice() {
      this.totalSellPrice = this.sellPrice * this.sellCount;
      return this.decimalPointFix(this.totalSellPrice);
    },

    // Personal Trade Insert 우리가 원하는 그거! >> DB저장
    async personalInsert(type) {
      this.formController(type)
      this.executionDisabled = true;


      let param = {
        userNo: this.user.no,
        lowPrice: this.lowPrice,
        lowCount: this.lowCount,
        hightPrice: this.hightPrice,
        hightCount: this.hightCount,
        status: "미체결",
      }
      console.log(param)
      await axios.post('/api/personalInsert', param)
          .then(res => {
            console.log(res);
            console.log(res.statusText);
            this.personalExecution();
            if (res.statusText === "OK") {
              this.formController("personalStyle");
              alert("등록 완료");
            }
          })
          .catch(function (error) {
            console.log("personalInsert error : " + error);
          })
      // 다 돌았으면 초기화 ㄱㄱ
      this.resetTrade();
    },

    // Personal Trade Start! >> 한무로직 돌아보쟈
    personalExecution() {
      let param = {
        userNo : this.user.no, // 유저 입력 정보 조회 목적
        id : this.user.id,  // api key 조회 목적
      }
      axios.post('/api/personalExecution', param)
          .then( res => {

          })
          .catch( function (error) {
            console.log(error);
          })
    },


    async listSet() {
      this.balance();
      this.kimchiPrice();
      this.orderBook();
      let param = {
        userNo: this.user.no,
      }
      await axios.post('/api/personalTradeList', param)
          .then(res => {
            this.personalTradeList = res.data;
          })
          .catch(function (error) {
            console.log("personalListSet error : " + error);
          })

      await axios.post('/api/kimchiSelect', param)
          .then(res => {
            this.kimchiTradeList = res.data;
          })
          .catch(function (error) {
            console.log("personalListSet error : " + error);
          })
      if(this.personalTradeList[0].status === "미체결") {
        this.formController("personalStyle")
      }else if(this.kimchiTradeList[0].tradeStatus === "미체결"){
        // 미체결건 있으면 폼에 수량 그려라
        this.kimchiBids = this.kimchiTradeList[0].tradeBidPersent;
        this.kimchiBidsCount = this.kimchiTradeList[0].tradeBidCount;
        this.kimchiAsks = this.kimchiTradeList[0].tradeAskPersent;
        this.kimchiAsksCount = this.kimchiTradeList[0].tradeAskCount;
        this.formController("kimchiStyle")
      }else{
        this.formController("none")
      }
      setTimeout(() => {
        this.listSet();
      }, 1000)

    },

    // 시간 형식 바꾸기
    timestampString(timestamp) {
      if (timestamp === null) {
        return null;
      } else {
        // 밀리초를 Date 객체로 변환
        let dateObject = new Date(timestamp);

        // 각 부분을 가져와서 형식에 맞게 조합
        let year = dateObject.getFullYear();
        let month = ('0' + (dateObject.getMonth() + 1)).slice(-2);
        let day = ('0' + dateObject.getDate()).slice(-2);
        let hours = ('0' + dateObject.getHours()).slice(-2);
        let minutes = ('0' + dateObject.getMinutes()).slice(-2);
        let seconds = ('0' + dateObject.getSeconds()).slice(-2);

        // 형식에 맞춰 문자열 생성
        let formatDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes;

        return formatDate;
      }
    },

    // 리스트 클릭!
    async clickTradeList(index, type) {
      // personalTradeList kimchiTradeList
      let clickedList = this[type][index];
      let status = type === "personalTradeList" ? "status" : "tradeStatus";
      let style = type === "personalTradeList" ? "personalStyle" : "kimchiStyle";
      let url = type === "personalTradeList" ? "personalUpdate" : "kimchiUpdate";

      console.log(clickedList)
      console.log(status)
      console.log(style)
      console.log(url)
      if (clickedList[status] === "미체결") {
        let isCancel = confirm("취소하시겠습니까?")
        if (isCancel) {
          let param = {
            userNo: this.user.no,
            no: clickedList.no,
            [status]: "취소완료",
          }
          await axios.post('/api/' + url, param)
              .then(res => {
                console.log(res);

                this.formController(style);
              })
              .catch(function (error) {
                console.log("personalInsert error : " + error);
              })
        }
      } else {
        alert("끝난거 누르지마!")
      }

    },


    ticker() { //전일 종가 가격 가주와
      axios.get('https://api.bithumb.com/public/ticker/USDT_KRW')
          .then(res => {
            this.prev_closing_price = res.data.data.prev_closing_price;
          })
          .catch(function (error) {
            console.log("error : " + error);
          })
    },
    orderBook() {
      axios.get('https://api.bithumb.com/public/orderbook/USDT_KRW')
          .then(res => {
            const reverseAsks = [];// ASK는 역순으로 나와서 뒤저어줄 배열

            let topQuantityAsks = parseFloat(res.data.data.asks[0].quantity); //최고 ASK 수량
            let thisQuantityAsks = 0;
            let topQuantityBids = parseFloat(res.data.data.bids[0].quantity); //최고 BID 수량
            let thisQuantityBids = 0;

            // ASK는 역순으로 나와서 뒤저어줘라
            for (let i = res.data.data.asks.length - 1; i >= 0; i--) {
              // ASK 최고수량 뽑자
              thisQuantityAsks = parseFloat(res.data.data.asks[i].quantity);
              if (topQuantityAsks < thisQuantityAsks) {
                topQuantityAsks = thisQuantityAsks
              }

              // 뒤집어준거 넣어주자
              reverseAsks.push(res.data.data.asks[i]);
            }

            for (let i = res.data.data.bids.length - 1; i >= 0; i--) {
              // BID 최고수량 뽑자
              thisQuantityBids = parseFloat(res.data.data.bids[i].quantity);
              if (topQuantityBids < thisQuantityBids) {
                topQuantityBids = thisQuantityBids
              }
            }

            this.topQuantityAsks = topQuantityAsks;
            this.topQuantityBids = topQuantityBids;

            this.asks = reverseAsks;
            this.bids = res.data.data.bids;
          })
          .catch(function (error) {
            console.log("error : " + error);
          })
    },

    //김치 가격 가쥬와
    kimchiPrice() {
      axios.get("https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=%ED%99%98%EC%9C%A8&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1")
          .then(res => {
            this.USDTPrice = sessionStorage.getItem("usdt_price")
            this.kimchi = ((parseFloat(this.USDTPrice) / parseFloat(res.data.country[1].value.replaceAll(",", "")) - 1) * 100).toFixed(2)

            if(document.querySelector("#bd-" + this.USDTPrice) !== null){
              document.querySelector("#bd-" + this.USDTP).style.border = "";
              document.querySelector("#bd-" + this.USDTPrice).style.border = "4px solid red";
            }
            this.USDTP = this.USDTPrice;
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    //김치 초기화
    resetkimchi() {
      this.kimchiBids = "";
      this.kimchiAsks = "";
      this.kimchiBidsCount = "";
      this.kimchiAsksCount = "";
    },


    classifyKimchiType() {
      // 선택 활성, 비활성
      this.borderStyle("kimchiStyle");
    },

    //실행중인 카드
    async borderStyle(type){

      this.formController(type);
      let param = {
        userNo : this.user.no,
        tradeBidPersent : this.kimchiBids, // 김치 매수 %
        tradeBidCount : this.kimchiBidsCount, // 김치 매수 수량
        tradeAskPersent : this.kimchiAsks, // 김치 매도 %
        tradeAskCount : this.kimchiAsksCount, // 김치 매도 수량
        tradeStatus : '미체결', // 체결완료, 주문취소, 미체결
      }
      await axios.post('/api/kimchiInsert', param)
          .then( res => {
            this.kimchiExecution();
          })
          .catch( function (error) {
            console.log(error);
          })
    },
    kimchiExecution(){
      let param = {
        userNo : this.user.no, // 유저 입력 정보 조회 목적
        id : this.user.id,  // api key 조회 목적
      }
      axios.post('/api/kimchiExecution', param)
          .then( res => {
            alert("거래 신청을 완료하였습니다.")
          })
          .catch( function (error) {
            console.log(error);
          })
    },
    formController(type){
      this.kimchiStyle = "2b2c40"; // 김프 보더
      this.personalStyle = "2b2c40"; // 김프 보더
      // A-n 이 추가된다면 스타일 추가 해주자
      if(type === "none"){
        this.executionDisabled = false;
      }else{
        this.executionDisabled = true;
        this[type] = "a3a4cc";
      }
    },
  },
  mounted() {
    this.ticker();
    this.listSet()
  }
});


</script>



<style scoped>

.btn-group {
  width: 100%;
}

.inputLabel {
  color: #e4eaec;
}

table > thead > tr > th {
  color: #fff;
}

table td, table th {
  text-align: center;
  vertical-align: bottom;
}

.table-hover tbody tr:hover {
  color: #e4eaec;
  background-color: #474656;
}



.order-book-td-asks{
  background-color: #eef6ff;/*일반 : #eef6ff, 그래프 : #cde0fe // 숫자 : #5492fa*/
  padding: 3px;
}
.progress-asks{
  background-color: #eef6ff;
}
.bg-asks{
  background-color: #cde0fe !important;
}

.order-book-td-asks:hover{
  background-color: #cde0fe
}


.order-book-td-bids{
  background-color: #fff0ef;/*일반 : #fff0ef, 그래프 : #fdd2d7 // 숫자 : #f56c7e*/
  padding: 3px;
}
.progress-bids{
  background-color: #fff0ef;
}
.bg-bids{
  background-color: #fdd2d7 !important;
}
.order-book-td-bids:hover{
  background-color: #fdd2d7
}

.order-book-table-responsive{
  width: 100% !important;
  height: 97%;
  overflow: auto;
  position: sticky;
}

.order-book-table{
  position: sticky;

  border: 0;
  border-collapse: collapse;
}

.order-book-th{
  position: sticky;
  top: 0;
  color: black;
  padding: 5px;
  position: sticky;
  background-color: #e4eaec !important;

}

.order-book-tr{
  padding: 5px;
}


/* 스크롤바 설정*/
.order-book-table-responsive::-webkit-scrollbar {
  width: 20px;
}

/* 스크롤바 막대 설정*/
.order-book-table-responsive::-webkit-scrollbar-thumb {
  background-color: #d6d6e1;
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
  border: 7px solid #2b2c40;
}

/* 스크롤바 뒷 배경 설정*/
.order-book-table-responsive::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0);
}
</style>

