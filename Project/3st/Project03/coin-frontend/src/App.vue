<template>
  <div id="appPage">
    <router-view/>
  </div>
</template>
<script>

</script>

<style>

#appPage{
  width: 100%;
  height: 100%;
  position: relative;
}

/*버튼흔들*/
.shake {
  animation: shake 0.82s cubic-bezier(0.36, 0.07, 0.19, 0.97) both;
  transform: translate3d(0, 0, 0);
}

@keyframes shake {
  10%,
  90% {
    transform: translate3d(-1px, 0, 0);
  }

  20%,
  80% {
    transform: translate3d(2px, 0, 0);
  }

  30%,
  50%,
  70% {
    transform: translate3d(-4px, 0, 0);
  }

  40%,
  60% {
    transform: translate3d(4px, 0, 0);
  }
}


/* fade in out */
/* outer */
/*.v-enter-active,
.v-leave-active {
  transition: all 0.5s ease-in-out;
}

.v-enter-from,
.v-leave-to {
  transform: translateX(730px);
}

!* inner *!
.v-enter-active .inner,
.v-leave-active .inner {
  transition: all 3s ease-in-out;
}

.v-enter-from .inner,
.v-leave-to .inner {
  opacity: 0;
}*/
body{
  -webkit-user-select:none;
  -moz-user-select:none;
  -ms-user-select:none;
  user-select:none;
}
</style>