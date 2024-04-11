import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App);

//npm install --save axios
import axios from 'axios'
app.config.globalProperties.$axios = axios; //axios

//npm install vue-router
import router from './router'
app.use(router);

//npm install vue-chartjs chart.js
//차트 라이브러리

//npm install apexcharts
//촛대 차트

app.mount('#app');
