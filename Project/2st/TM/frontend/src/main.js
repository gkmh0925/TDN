import { createApp } from 'vue'
import App from './App.vue'

/*라우터*/
import router from './router'

/*캘린더*/
import VCalendar from 'v-calendar'
import 'v-calendar/style.css';

/*axios*/
import axios from 'axios'


const app = createApp(App);
app.use(router);
app.use(VCalendar, {})
app.config.globalProperties.$axios = axios; //axios
app.mount('#app');

