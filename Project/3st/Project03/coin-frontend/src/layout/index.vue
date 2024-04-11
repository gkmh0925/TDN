<template>
  <section id="wrap">
    <div class="container-scroller">
      <Sider/>
      <div class="container-fluid page-body-wrapper" ref="container">
        <Header />
        <div class="main-panel">
          <div class="content-wrapper">
            <Transition>
              <router-view/>
            </Transition>
          </div>
          <Footer />
        </div>
      </div>
    </div>
  </section>
</template>
<script setup>
  import Sider from "@/layout/Sider.vue";
  import Header from "@/layout/Header.vue";
  import Footer from "@/layout/Footer.vue";
</script>

<style>

</style>


