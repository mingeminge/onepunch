import Vue from 'vue'
import App from './App.vue'
import permission from "./utils/permission";
import store from "./store";
import router from "@/router";
import VCharts from 'v-charts'
import VueParticles from 'vue-particles'
import '@/styles/index.scss' // global css
 import '@/icons' // icon

import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/en'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(VueParticles)
Vue.use(ElementUI, {locale})
Vue.use(VCharts)
Vue.config.productionTip = false

Vue.prototype.$loading = ElementUI.Loading.service;
Vue.prototype.$msgbox = ElementUI.MessageBox;
Vue.prototype.$alert = ElementUI.MessageBox.alert;
Vue.prototype.$confirm = ElementUI.MessageBox.confirm;
Vue.prototype.$prompt = ElementUI.MessageBox.prompt;
Vue.prototype.$notify = ElementUI.Notification;
Vue.prototype.$message = ElementUI.Message;
let Base64 = require('js-base64').Base64;
new Vue({
    router,
    permission,
    store,
    render: h => h(App),
}).$mount('#app')
