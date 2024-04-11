<template>

  <div class="col-12 grid-margin stretch-card">
    <div class="card">
      <div class="card-body">
        <h4 class="card-title">{{ state.writeType }}</h4>
        <form class="forms-sample">
          <div class="form-group">
            <label for="exampleSelectGender">BoardType</label>
            <select class="form-control" id="boardType" v-model="boardType" v-bind:disabled=state.disabledState>
              <!--v-model 에는 옵션값에 따른 값이 담긴다 //글쓰기면 false가 업데이트면 true가 전달됨-->
              <option>Notice</option>
              <option>Info</option>
            </select>
          </div>
          <div class="form-group">
            <label for="exampleInputName1">Title</label>
            <input type="text" class="form-control" id="inputTitle" placeholder="Title" v-model="title">
          </div>
          <div class="form-group">
            <label for="exampleTextarea1">Content</label>
            <textarea class="form-control" id="inputContent" rows="15" placeholder="Content"
                      v-model="content"></textarea>
          </div>
          <button type="button" class="btn btn-outline-secondary mr-2" @click="clickSubmit">{{ Submit }}</button>
          <button class="btn btn-secondary" @click="notification()">Cancel</button>
        </form>
      </div>
    </div>
  </div>
</template>


<script>

import axios from "axios";
import router from "@/router";

export default {
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("user")), //관리자 데이터 : sessionStorage에 user의 값이 담겨있는 것을 JSON형태의 데이터로 값을 주겠다
      title: "", //제목
      content: "", //내용
      //writer: "", //관리자
      // ReadCount: "", //조회수
      // LikeCount: "", //좋아요수
      boardType: "Notice",  //셀렉트박스 선택 옵션
      url: "domain.com",
      state: history.state, //boardDetail의 값을 넘겨받음
      Submit: "Insert",
    }
  },
  methods: {
    notification() {
      router.push({ //페이지 이동
        name: "notification",
        state: {
          /*dataObj : this.response,*/
        }
      })
    },
    clickSubmit() {  //글쓰기 or 수정하기
      if (this.state.writeType === "Board Insert") {
        this.insertBoard();
      } else if (this.state.writeType === "Board Update") {
        this.updateBoard();
      }
    },
    async insertBoard() {
      let postUrl = ""; //통신 url 받아서 주기
      if (this.boardType === "Notice") {
        this.boardType = "공지게시판";
        postUrl = "insertNoticeBoard";
      } else if (this.boardType === "Info") {
        this.boardType = "정보게시판";
        postUrl = "insertInfoBoard";
      }

      let param = {
        title: this.title, //제목
        content: this.content, //내용
        writer: this.user.id, //관리자
        boardType: this.boardType, //셀렉트박스 선택 옵션 notice or info
        url: this.url,
      }

      await axios.post('/api/' + postUrl, param)
          .then(res => {
            alert(this.state.boardType + " 게시물이 등록 되었습니다.")
            router.push({ //페이지 이동
              name: "notification",
              state: {
                /*dataObj : this.response,*/
              }
            })
          })
    },
    async updateBoard() { //게시글 수정
      if (confirm("수정하시겠습니까?")) {
        let param = {
          no: this.state.no,
          title: this.title, //제목
          content: this.content, //내용
          writer: this.user.id, //관리자
          boardType: this.boardType === "Notice" ? "공지게시판" : "정보게시판", //셀렉트박스 선택 옵션
          url: this.url,
        }
        console.log(param);

        let postUrl = ""; //통신 url 받아서 주기
        if (param.boardType === "공지게시판") {
          postUrl = "updateNoticeBoard";
        } else if (param.boardType === "정보게시판") {
          postUrl = "updateInfoBoard";
        }
        await axios.post('/api/' + postUrl, param)
            .then(res => {
              alert(this.state.boardType + " 업데이트 하였습니다.")
              router.push({ //페이지 이동
                name: "notificationDetail",
                state: { // notification 을 통해서 가지 않으니까 값을 줘야함
                  no: this.state.no,
                  boardType: this.boardType === "Notice" ? "공지게시판" : "정보게시판",
                }
              })
            })
            .catch(function (error) {
              console.log(error);
            })
      }

    },
    updateForm() {
      if (this.state.title !== undefined) {
        this.Submit = "Update";
        this.title = this.state.title; //제목
        this.content = this.state.content; //내용
        this.boardType = this.state.boardType === "정보게시판" ? "Info" : "Notice";  //셀렉트박스 선택 옵션
      }
    },
  },
  mounted() {
    console.log(this.state);
    console.log(this.disabledState);
    this.updateForm();
  }
}


</script>


<style scoped>


</style>