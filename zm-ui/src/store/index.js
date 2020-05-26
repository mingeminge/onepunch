import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import getters from "./getter";
import tagsView from './modules/tagsView'
import settings from './modules/settings'
import permission from "@/store/modules/permission";
Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        app,
        tagsView,
        settings,
        permission
    },
    getters
})

export default store
