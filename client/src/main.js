import Vue from 'vue'
import App from './App.vue'
import router from "@/router/router";

import '@/element.js'
import './utils/flexible'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
