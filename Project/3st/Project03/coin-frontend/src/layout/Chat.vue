<template>
  <div class="row">
    <div class="col-xl-12 grid-margin stretch-card" :style="chatWidth">
      <div class="card" >
        <div class="card-body message message-drag">
          <i v-if="chatYN" class="mdi mdi-comment-check-outline icon-md chat-icon"></i>
          <i v-else class="mdi mdi-comment-remove-outline icon-md chat-icon"></i>
          <span v-if="connected" class="chat-title"> Server : On. <span v-if="chatYN">User : {{id}}.</span></span>
          <span v-else class="chat-title"> Server : Off.</span>
          <div class="chat-container" v-if="chatYN">
            <div class="chat-messages" ref="chatContainer">
              <div v-for="(msg, index) in chatMessages" :key="index"
                   :class="msg.owner === id.toString()? 'my-chat' : 'their-chat'">
                <div class="messages">{{msg.owner}} : {{ msg.message }}</div>
              </div>
            </div>
            <div id="the-basics">
              <div class="message-template">
                <div :class="{ shake: disabled }">
                  <input class="typeahead message-input" type="text" :placeholder="inputMessage" v-model="message"
                         @keydown.enter="sendMessage">
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
export default {
  data(){
    return{
      chatSize : this.chatWidth,
      yn :this.chatYN,
    }
  },
  props: {
    chatYN: {
      type: Boolean,
    },
    chatWidth:{
      type:String,
    },
    msgCnt:{
      type:Number
    }
  },
  mounted() {
  }
}
</script>

<script setup>
import {nextTick, ref, defineEmits} from "vue";
import router from "@/router";
import {onMounted} from "vue";
import {io} from "socket.io-client";

const disabled = ref(false);
//마우스 드래그 시작
let isDragging = null;
let originLeft = null;
let originTop = null;
let originX = null;
let originY = null;
let box = null;
let diffX = null;
let diffY = null;
let posision = {};
onMounted(async () => {
  await router.isReady();
  if (localStorage.getItem("posision") !== undefined && localStorage.getItem("posision") !== null && localStorage.getItem("posision") !== "") {
    posision = JSON.parse(localStorage.getItem("posision"))
    document.querySelector(".message-drag").style.left = `${posision.diffX}px`;
    document.querySelector(".message-drag").style.top = `${posision.diffY}px`;
  }else{
    document.querySelector(".message-drag").style.left = `${1000}px`;
    document.querySelector(".message-drag").style.top = `${470}px`;
  }
});


onMounted(async () => {
  await router.isReady();
  box = document.querySelector(".message-drag");

  box.addEventListener("mousedown", (e) => {
    isDragging = true;
    originX = e.clientX;
    originY = e.clientY;
    originLeft = box.offsetLeft;
    originTop = box.offsetTop;
  });

  document.addEventListener("mouseup", (e) => {
    isDragging = false;
  });

  document.addEventListener("mousemove", (e) => {
    if (isDragging) {
      diffX = originLeft + e.clientX - originX;
      diffY = originTop + e.clientY - originY;
      box.style.left = `${diffX}px`;
      box.style.top = `${diffY}px`;
      posision.diffX = diffX;
      posision.diffY = diffY;
      localStorage.setItem("posision", JSON.stringify(posision));
    }
  });
})
//마우스 드래그 끝


// 실시간 채팅 시작

// 브로드캐스팅 받은 데이터 구분을 위한 id
let user = JSON.parse(sessionStorage.getItem("user"));
const id = ref(user.id)
// 받은 데이터를 수집
const chatMessages = ref([])
// 연결 상태
const connected = ref(false)

//const socket = io('3.39.202.228:3000')
const socket = io('localhost:3000')
socket.on("connect", () => {
  connected.value = true;
});

socket.on("disconnect", () => {
  connected.value = false;
});


// 2022.08.15[프뚜]: 부모 함수 사용하기

// 메시지를 받으면 채팅 메시지 데이터에 푸시
// 자식 > 부모 데이터 보내기 후... defineEmits
let msgCnt = 0;
const emit = defineEmits(['readCnt'])
socket.on('chat', (data) => {
  msgCnt++;
  emit('readCnt', msgCnt);

  chatMessages.value.push(data)
  nextTick(() => {
    scrollChatToBottom();
  });
  return {
    msgCnt
  }
})


const message = ref('')
const chatContainer = ref(null);
let inputMessage = "Input message";

function sendMessage() {
  if (message.value === "") {
    inputMessage = "Please enter your message";
    disabled.value = true
    setTimeout(() => {
      inputMessage = "Input Message";
      disabled.value = false
    }, 2000)
    return;
  }
  const chat = {
    owner: id.value,
    message: message.value
  }
  chatMessages.value.push(chat)
  socket.timeout(5000).emit('chat', chat)

  message.value = "";
  // 스크롤을 새 메시지 아래로 이동시킵니다.
  nextTick(() => {
    scrollChatToBottom();
  });
}

function scrollChatToBottom() {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
}

//실시간 채팅 끝


</script>


<style scoped>

.message {
  width: 100%;
  padding: 10px;
  position: absolute;
  cursor: pointer;
  left: 1200px;
  top: -620px;
  background-color: #2b2c40;
  border: 1px solid #a3a4cc;
}

.message-template {
  width: 100%;
}

.message-input {
}

.chat-container {
  border: 1px solid #a3a4cc;
  display: flex;
  flex-direction: column;
  height: 300px;
  padding: 20px;
}

.chat-messages {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: auto;
}

.my-chat {
  align-self: flex-end;
  background-color: #8c8ecc;
  color: #232333;
  max-width: 70%;
  padding: 8px;
  margin: 8px 8px 8px 0;
  border-radius: 7px;
  border: 1px solid #232333;
  text-align: right;
  position: relative;
}

.their-chat {
  align-self: flex-start;
  background-color: #232333;
  max-width: 70%;
  padding: 8px;
  margin: 8px 0;
  border-radius: 7px;
  text-align: left;
  color: #a3a4cc;
  position: relative;
  border: 1px solid #a3a4cc;

}


input[type="text"] {
  flex: 1;
  padding: 8px;
  border: 1px solid #a3a4cc;
  border-radius: 5px;
  margin-right: 10px;
  color: #a3a4cc;
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

.chat-title {
  font-size: 1rem;
  color: #a3a4cc;
  position: relative;
  top: -6px;
}

.chat-icon {
  font-size: 1.275rem;
  color: #a3a4cc;
}

</style>