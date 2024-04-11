<template>

  <!---------디테일부분---------->
  <div class="col-12 grid-margin stretch-card">
    <div class="card">
      <div class="card-body">
        <h6 class="card-title">{{ boardType }} &nbsp;No.{{ no }}</h6>
        <form class="forms-sample">
          <div class="form-group">
            <label for="exampleInputPassword4">Title</label>
            <h4> {{ title }} </h4>
          </div>
          <div class="form-group">
            <label for="exampleTextarea1">Content</label>
            <h6 for="exampleTextarea1" style="float: right;"> {{ regDate }} </h6>
            <textarea class="form-control" id="exampleTextarea1" v-bind:rows="rows" :disabled="true"
                      v-model="content"> </textarea>
          </div>
            <button class="btn btn-secondary mr-2" style="float: left" @click="notification()">Back</button> <!--얘만 모든 화면에 보이게 해야됨-->
            <!--Host 때만 block 처리하기-->
<!--            <button type="submit" class="btn btn-outline-secondary mr-2 px-1" style="float: left" v-bind:style={display:updateButton} @click="updateBoard()">Update</button>-->
            <button type="submit" class="btn btn-outline-secondary mr-2 px-1" style="float: left" v-show=boardBtn @click="updateBoard()">Update</button>
            <button type="submit" class="btn btn-outline-secondary mr-2 px-1" style="float: left" v-show=boardBtn @click="deleteBoard()">Delete</button>

          <!--아이콘인데 색깔없음 ㅠ-->
          <h5 class="menu-icon" style="float: right;" >
<!--            <i class="mdi mdi-heart-outline"></i>-->
            <i id="icon" class="mdi mdi-heart-outline" @click="likeCountADD()"></i> {{ likeCount }}
          </h5>
          <h5 class="menu-icon mr-2" style="float: right;"><i class="mdi mdi-account-circle"></i>{{
              readCount
            }}&nbsp; </h5>


<!--          <h4 class="card-title">Button Size</h4>-->

        </form>
      </div>
    </div>
  </div>
  <!---------디테일부분 끝---------->


  <!---------댓글부분---------->
  <div class="col-12 grid-margin stretch-card" v-bind:style={display:replyState}>
    <div class="card">
      <div class="card-body">

        <!--댓글 쓰기 Form-->
        <div class="form-group">
          <h6 class="card-title">Reply Insert</h6> <!--담아서 if문으로 할까? Reply or Reply Insert-->
          <div class="input-group mh-100">
            <div class="input-group-prepend">
              <button type="button" class="btn btn-outline-secondary" disabled style="width: 150px; height: 47px;">
                <i class="mdi mdi-account d-block mb-1"></i> {{ this.user.id }}
              </button>
            </div>
            <input type="text" class="form-control form-control-lg px-auto" placeholder="댓글을 입력해주세요"
                   aria-label="Username" v-model="replyContent">
            <span class="input-group-append">
              <button class="file-upload-browse btn btn-primary" type="button" @click="insertReply()">
                   <i class="mdi mdi-send d-block"></i>Submit</button>
            </span>
          </div>
        </div>
        <!--댓글 쓰기 Form 끝-->

        <!--댓글 목록-->
        <!-- <h6 class="card-title">Reply</h6>-->
        <h6 class="mt-5">Reply</h6>
        <hr>
        <h5 class="text-center mt-4"> {{ replyText }} </h5>
        <div v-for="infoReply in reply"> <!--for문-->
          <div class="preview-item-content d-flex flex-grow">
            <div class="flex-grow">
              <div class="d-flex d-md-block d-xl-flex justify-content-between">
                <h6 class="text-muted preview-subject mt-1">{{ infoReply.id }}</h6>
                <p class="text-muted mb-0"> {{ infoReply.regDate }} </p>
              </div>

              <div class="input-group">
                <textarea type="text" class="form-control input-group-prepend" id="replyBox" placeholder="Username"
                        v-model="infoReply.content" v-bind:disabled="infoReply.no!==this.check"></textarea>
                <button class="file-upload-browse btn btn-primary input-group-append" type="button"
                        @click="updateReply(infoReply)" v-show='infoReply.no===this.check'>Update
                </button>
              </div>
              <button type="submit" class="btn btn-outline-secondary my-2" @click="deleteReply(infoReply)"
                      style="float: right" v-show="infoReply.id===this.user.id">Delete
              </button>
              <button type="submit" class="btn btn-outline-secondary my-2 mr-2" @click="updateReplyState(infoReply)"
                      style="float: right"  v-show="infoReply.id===this.user.id">Update
              </button>

            </div>
          </div>
        </div>
        <!--댓글 목록 끝-->
      </div>
    </div>
  </div>
  <!---------댓글부분 끝---------->

</template>


<script>
import axios from "axios";
import router from "@/router";

export default {
  data() {
    return {
      //// 게시판
      boardType: history.state.boardType, //게시판타입
      no: history.state.no, //게시판번호
      title: "", //제목
      writer: "", //관리자
      content: "", //내용
      regDate: history.state.regDate, //날짜
      readCount: "", //조회수
      likeCount: "", //좋아요수
      rows: 15,

      //// 댓글
      reply: {},
      replyContent: "", //댓글내용
      replyState: "", //댓글상태 block or none
      replyUpdateBtn: "none", //update 눌렀을 때 block 으로 변경
      replyText: "", //댓글 0이면 없다고 알림
      // ButtonState: this.user.id === "Host" ? "block":"none", //admin인 경우에 block
      check: "", //댓글 no가 담긴다


      //user
      user: JSON.parse(sessionStorage.getItem("user")),

      //버튼상태
      boardBtn: false, //update, delete, 댓글 update, delete
      replyBtn: false,


    }
  },
  methods: {
    buttonState() {
      console.log(this.user.id);
      console.log(this.reply.id);


      //버튼상태
      if (this.user.id === "Host") {
        this.boardBtn = true;
        this.replyBtn = true;
       }

    },

    //////게시판 부분//////
    async selectNotification() {
      this.buttonState();
      let param = {
        no: this.no,
      }
      if (this.boardType === "공지게시판") {
        this.replyState = "none"; //댓글 none 상태
        await axios.post('/api/noticeBoardDetail', param)
            .then(res => {
              // console("공지게시판 data count", res.data[0].content);
              this.title = res.data[0].title;
              this.content = res.data[0].content;
              // this.regDate = res.data[0].regDate;
              // this.writer = res.data[0].writer;
              this.readCount = res.data[0].readCount;
              this.likeCount = res.data[0].likeCount;
            })
            .catch(function (error) {
              console.log(error);
            })
      } else if (this.boardType === "정보게시판") {
        this.replyState = "block"; //댓글 block 상태
        await axios.post('/api/infoBoardDetail', param)
            .then(res => {
              this.title = res.data[0].title;
              this.content = res.data[0].content;
              // this.regDate = res.data[0].regDate;
              // this.writer = res.data[0].writer;
              this.readCount = res.data[0].readCount;
              this.likeCount = res.data[0].likeCount;
              this.rows=30;

            })
            .catch(function (error) {
              console.log(error);
            })
      }
    },
    notification() {
      router.push({ //페이지 이동
        name: "notification",
        state: {
          /*dataObj : this.response,*/
        }
      })
    },
    updateBoard() { //게시글 수정
      router.push({ //페이지 이동
        name: "notificationWrite",
        state: {
          no: this.no,
          writeType: "Board Update",
          title: this.title,
          writer: this.writer,
          content: this.content,
          regDate: this.regDate,
          boardType: this.boardType,

        },
      })
    },
    async deleteBoard() { //게시글 삭제
      if (confirm("No : " + this.no + "을(를) 삭제하시겠습니까?")) {
        let param = {
          no: this.no,
        }
        if (this.boardType === "공지게시판") {
          await axios.post('/api/deleteNoticeBoard', param)
              .then(res => {
                alert("삭제했습니다.")
              })
              .catch(function (error) {
                console.log(error);
              })
        } else if (this.boardType === "정보게시판") {
          await axios.post('/api/deleteInfoBoard', param)
              .then(res => {
                alert("삭제했습니다.")
              })
              .catch(function (error) {
                console.log(error);
              })
        }
      }
      router.push({ //페이지 이동
        name: "notification",
        state: {},
      })
    },


    //////댓글 부분//////
    async replyList() { //댓글 리스트
      let param = {
        no: this.no,
      }
      await axios.post('/api/replyList', param)
          .then(res => {
            if(res.data.length===0){ //데이터가 없으면 댓글이 없습니다 출력
              this.replyText="댓글이 없습니다";
            }else{
              this.replyText="";
            }
            this.reply = res.data;
            this.dateChangeReply("reply");
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    async insertReply() { //댓글쓰기
      let param = {
        // no: this.no,
        infoNo: this.no, //board번호
        userNo: this.user.no, //user번호
        content: this.replyContent, //내용
      }
      await axios.post('/api/insertReply', param)
          .then(res => {
            // this.reply = res.data;
            this.replyContent = "";
            this.replyList();
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    updateReplyState(infoReply) {
      console.log(infoReply);

      this.check=infoReply.no;

      this.replyUpdateBtn = "block";
    },
    async updateReply(infoReply) { //댓글수정
      let param = {
        no: infoReply.no,
        content: infoReply.content, //내용
      }
      await axios.post('/api/updateReply', param)
          .then(res => {
            alert("댓글이 수정 되었습니다.");
            this.reply = res.data;
            this.replyList();

            this.replyUpdateBtn = "none";
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    async deleteReply(infoReply) { //댓글삭제 -> 여기에 클릭한 값 전달하면 되지!!!!
      if(confirm("댓글을 삭제하시겠습니까?")) {
        let param = {
          no: infoReply.no,
        }
        await axios.post('/api/deleteReply', param)
            .then(res => {
              alert("댓글이 삭제 되었습니다.");
              this.replyList();
            })
            .catch(function (error) {
              console.log(error);
            })
      }
    },
    dateChangeReply(type) { //Reply DATE
      // console.log(type);
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
    async readCountADD() { //조회수 +1
      let param = {
        no: this.no,
      }
      await axios.post('/api/noticeReadCount', param)
          .then(res => {
            this.selectNotification();
          })
          .catch(function (error) {
            console.log(error);
          })
      await axios.post('/api/infoReadCount', param)
          .then(res => {
            this.selectNotification();
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    async likeCountADD() { //좋아요 +1
      let param = {
        no: this.no,
      }
      await axios.post('/api/noticeLikeCount', param)
          .then(res => {
            let icon = document.getElementById('icon');
            icon.classList.replace('mdi-heart-outline', 'mdi-heart');
            this.selectNotification();
          })
          .catch(function (error) {
            console.log(error);
          })
      await axios.post('/api/infoLikeCount', param)
          .then(res => {
            let icon = document.getElementById('icon');
            icon.classList.replace('mdi-heart-outline', 'mdi-heart');
            this.selectNotification();
          })
          .catch(function (error) {
            console.log(error);
          })
    },




  },
  mounted() {
    this.readCountADD(); //조회수
    this.selectNotification();
    this.replyList();
    // this.buttonState(); //버튼상태 admin일 때, 해당하는 id일때 활성화 되는 것이 있음
  }
}


</script>


<style scoped>


</style>