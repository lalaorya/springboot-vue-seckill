import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import ElementUI from 'element-ui';
import md5 from "js-md5"

import 'element-ui/lib/theme-chalk/index.css'; // 默认主题

Vue.config.productionTip = false
Vue.use(ElementUI, {
    size: 'small'
});

import axios from '@/network/axios'
Vue.prototype.$axios = axios //
Vue.prototype.$md5=md5

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
