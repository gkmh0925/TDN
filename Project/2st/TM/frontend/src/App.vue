<template>
  <div id="appPage">
    <transition name="fade">
      <router-view/>
    </transition>
  </div>
</template>
<script>
import {reactive} from 'vue'
import axios from 'axios'

export default {
  setup() {
    const state = reactive({
      account: {
        mid: null,
        memberName: ''
      }
    })

    // http://localhost:3000/api/account로 하면 오류 발생
    axios.get('/api/account').then(res => {
      state.account = res.data
    })

    return {state}
  }
}
</script>

<style>
.fade-enter {
  opacity: 0;
}

.fade-enter-active{
  transition: opacity 1s ease-out;
}


.fade-leave-to {
  opacity: 0;
}

.slide-fade-enter {
  transform: translateX(10px);
  opacity: 0;
}

.slide-fade-enter-active{
  transition: all 0.2s ease;
}

/*.slide-fade-leave-to {
  transform: translateX(-10px);
  opacity: 0;
}*/

.slide-up-enter {
  transform: translateY(10px);
  opacity: 0;
}

.slide-up-enter-active {
  transition: all 0.2s ease;
}

.slide-up-move {
  transition: transform 0.2s ease-in;
}
</style>