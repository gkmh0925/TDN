<template>
  <div class="row">
    <div class="col-lg-12 grid-margin stretch-card">
      <div class="card">
        <div class="card-body">

          <!--검색-->
          <div class="form-group">
            <div class="input-group justify-content-end">
              <!--셀렉트-->
              <select class="form-control col-1 mx-2" id="exampleFormControlSelect2" v-model="searchType">
                <option>total</option>
                <option>title</option>
                <option>content</option>
              </select>
              <!--검색창-->
              <input type="text" class="form-control col-4" placeholder="검색할 단어를 입력해주세요"
                     aria-label="Recipient's username" aria-describedby="basic-addon2" v-model="keyword">
              <!--검색버튼-->
              <button class="btn btn-sm btn-secondary" type="button" @click="notification()">Search</button>
            </div>
          </div>
          <!--검색 끝-->

          <!--공지, 정보 버튼-->
          <button type="button" id="b1" class="btn btn-info btn-fw py-3 boardNone" @click="buttonB1()">NoticeBoard
          </button>
          <button type="button" id="b2" class="btn btn-inverse-info btn-fw py-3 boardNone" @click="buttonB2()">
            InfoBoard
          </button>

          <!-- v-show="'this.user.id' === 'Host"-->
          <button type="button" class="btn btn-outline-secondary btn-fw " style="float: right;"
                  v-bind:style={display:writeButtonState} @click="write()">글쓰기
          </button>

          <!--공지 테이블-->
          <div class="table-responsive" v-bind:style={display:noticeBoard}>
            <table class="table table-dark">
              <thead>
              <tr class="table-dark">
                <th> No</th>
                <th> Title</th>
                <th> Writer</th>
                <th> Date</th>
                <th> ReadCount</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="nBoardList in noticeBoardData" @click="detail(nBoardList)">
                <td> {{ nBoardList.no }}</td>
                <td> {{ nBoardList.title }}</td>
                <td> {{ nBoardList.writer }}</td>
                <td> {{ nBoardList.regDate }}</td>
                <td> {{ nBoardList.readCount }}</td>
              </tr>
              </tbody>
            </table>

            <!--페이징처리-->
<!--            <button type="button" class="btn btn-outline-secondary mt-3" @click="sendPageNum(num)"><</button>-->
            <div class="btn-group mt-3" role="group" aria-label="Basic example" v-for="num in this.noticePageCount">
              <button type="button" class="btn btn-outline-secondary" @click="sendPageNum(num)">{{ num }}</button>
            </div>
<!--            <button type="button" class="btn btn-outline-secondary mt-3" @click="sendPageNum(num)">></button>-->
            <h5 class="text-center mt-4">{{ noticeSearchText }} </h5>
          </div>
          <!--공지 테이블 끝-->

          <!--정보 테이블-->
          <div class="table-responsive" v-bind:style={display:infoBoard}>
            <table class="table table-dark">
              <thead>
              <tr class="table-dark">
                <th> No</th>
                <th> Title</th>
                <th> Writer</th>
                <th> Date</th>
                <th> ReadCount</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="iBoardList in infoBoardData" @click="detail(iBoardList)">
                <td> {{ iBoardList.no }}</td>
                <td> {{ iBoardList.title }}</td>
                <td> {{ iBoardList.writer }}</td>
                <td> {{ iBoardList.regDate }}</td>
                <td> {{ iBoardList.readCount }}</td>
              </tr>
              </tbody>
            </table>
            <!--페이징처리-->
            <div class="btn-group mt-3" role="group" aria-label="Basic example" v-for="num in this.infoPageCount">
              <button type="button" class="btn btn-outline-secondary" @click="sendPageNum(num)" value="info">{{ num }}
              </button>
            </div>
            <h5 class="text-center mt-4"> {{ infoSearchText }} </h5>
          </div>
          <!--정보 테이블 끝-->
        </div>
      </div>
    </div>
  </div>


</template>


<script>
import router from "@/router";
import axios from "axios";
import {assertBoolean} from "@babel/core/lib/config/validation/option-assertions";

export default {
  data() {
    return {
      searchType: "total", //검색타입
      noticeSearchText: "", //검색결과 0일 경우 해당 text 출력
      infoSearchText: "",

      noticeBoard: "block",
      infoBoard: "none",
      noticeBoardData: {}, //공지게시판 데이터
      infoBoardData: {}, //정보게시판 데이터

      noticePageCount: "", //공지게시판 조회수
      infoPageCount: "", //정보게시판 조회수

      boardType: "",
      keyword: null,
      pageNum: "1", //페이지번호 기본적으로 1로 설정
      noticePagingData: {},
      infoPagingData: {},

      writeButtonState: "none",//글쓰기 버튼

      user: JSON.parse(sessionStorage.getItem("user")), //user

    }
  },
  methods: {
    assertBoolean,

    write() {
      if (this.user.id === "Host") {
        router.push({ //페이지 이동
          name: "notificationWrite",
          state: {
            /*dataObj : this.response,*/
            writeType: "Board Insert",
            disabledState: false,
          }
        })
      }
    },
    detail(board) { //상세페이지
      // console.log(board);
      router.push({ //페이지 이동
        name: "notificationDetail",
        state: {
          no: board.no,
          boardType: board.boardType,
          regDate: board.regDate,
        },
      })
    },
    admin() {
      if (this.user.id === "Host") {
        this.writeButtonState="block"
      }
    },

    sendPageNum(num) { //페이징 번호 넘기기
      this.pageNum = num;
      this.noticeBoardList();
      this.infoBoardList();
    },
    notification() { //검색버튼 클릭시 조회
      this.noticeBoardList();
      this.infoBoardList();
    },

    //페이징, 검색
    async noticeBoardList() { //공지게시판 리스트
      let param = {
        pageNum: this.pageNum,
        type: this.searchType, //type
        keyword: this.keyword
      }
      // let param2 = `pageNum=1&type=${this.searchType}&keyword=${this.keyword}`;
      // console.log("param : ", param);
      await axios.post('/api/noticeBoardList', param)
          .then(res => {
            this.noticePagingData = res.data;
            console.log("공지게시판 resdata",this.noticePagingData);

            this.noticePageCount = res.data.pageCount;
            this.noticeBoardData = res.data.noticeBoardList;
            if (res.data.noticeBoardList.length === 0) { //데이터가 없으면 댓글이 없습니다 출력
              this.noticeSearchText = "공지게시판 검색 결과가 없습니다";
            } else {
              this.noticeSearchText = "";
            }
            this.dateChange("noticeBoardData");

          })
          .catch(function (error) {
            console.log(error);
          })
    },


    async infoBoardList() { //정보게시판 리스트
      let param = {
        pageNum: this.pageNum,
        type: this.searchType, //type
        keyword: this.keyword
      }
      await axios.post('/api/infoBoardList', param)
          .then(res => {
            console.log("정보",res.data);
            this.infoBoardData = res.data.infoBoardList;
            this.infoPageCount = res.data.pageCount;
            if (res.data.infoBoardList.length === 0) { //데이터가 없으면 댓글이 없습니다 출력
              this.infoSearchText = "정보게시판 검색 결과가 없습니다";
            } else {
              this.infoSearchText = "";
            }
            this.dateChange("infoBoardData");
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    buttonB1() { //버튼 - notice
      this.boardType = "noticeBoard";
      let el_b1 = document.getElementById('b1');
      let el_b2 = document.getElementById('b2');
      // console.log(el_b2.classList.contains('btn-inverse-info'));
      // console.log(el_b2.className==="btn-inverse-info");
      if (el_b1.classList.contains('btn-inverse-info') && el_b2.classList.contains('btn-info')) {
        el_b1.classList.replace('btn-inverse-info', 'btn-info');
        el_b2.classList.replace('btn-info', 'btn-inverse-info');
        this.noticeBoard = "block";
        this.infoBoard = "none";
      } else if (el_b1.classList.contains('btn-info') && el_b2.classList.contains('btn-inverse-info')) {
        el_b1.classList.replace('btn-info', 'btn-info');
      }
    },
    buttonB2() { //버튼 - info
      this.boardType = "infoBoard";
      let el_b1 = document.getElementById('b1');
      let el_b2 = document.getElementById('b2');
      // console.log(el_b2.classList.contains('btn-inverse-info'));
      // console.log(el_b2.className==="btn-inverse-info");
      if (el_b2.classList.contains('btn-inverse-info')) {
        el_b2.classList.replace('btn-inverse-info', 'btn-info');
        el_b1.classList.replace('btn-info', 'btn-inverse-info');
        this.noticeBoard = "none";
        this.infoBoard = "block";
      } else if (el_b2.classList.contains('btn-info')) {
        // el_b1.classList.replace('btn-inverse-info', 'btn-inverse-info');
        el_b2.classList.replace('btn-info', 'btn-info');
      }
    },
    dateChange(type) { //DATE
      let date; //타임스탬프를 인자로 받아 Date 객체 생성
      let year; //년도 뒤에 두자리
      let month; //월 2자리 (01, 02 ... 12)
      let day; //일 2자리 (01, 02 ... 31)
      let hour; //시 2자리 (00, 01 ... 23)
      let minute; //분 2자리 (00, 01 ... 59)

      //for(i=0 i<infoBoardData.leng i++) 이게 아래 포이치, data = infoBoardData[i], index = i
      this[type].forEach(function (data, index) {
        //type = infoBoardData noticeBoardData
        date = new Date(data.regDate); //타임스탬프를 인자로 받아 Date 객체 생성
        year = date.getFullYear().toString().slice(-2); //년도 뒤에 두자리
        month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
        day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
        hour = ("0" + date.getHours()).slice(-2); //시 2자리 (00, 01 ... 23)
        minute = ("0" + date.getMinutes()).slice(-2); //분 2자리 (00, 01 ... 59)

        this[type][index].regDate = year + "." + month + "." + day + " " + hour + ":" + minute;
      }, this)  //this.this 라고 생각하시오
      // console.log(this[type]);
    },




  },
  mounted() {
    this.noticeBoardList();
    this.infoBoardList();
    this.admin();
  }
}
</script>


<style>
/*버튼 테두리 없애기*/
/*.boardNone:active, */
.boardNone:focus {
  outline: none !important;
  box-shadow: none !important;
}
</style>