<template>
  <section id="wrap">
    <Header />
  <section id="container">
    <transition name="fade">
      <router-view/>
    </transition>
  </section>
    <Footer />
  </section>
</template>

<script>
  import Header from "@/layout/components/Header.vue";
  import Footer from "@/layout/components/Footer.vue";
  export default {
    components:{
      Header,
      Footer
    }
  };
</script>

<style>

</style>