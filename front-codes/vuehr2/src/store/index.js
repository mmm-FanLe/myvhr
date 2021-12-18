import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        routes:[],//传递路由数组
    },
    mutations:{
        initRoutes2(state,data){
            state.routes=data;
        }
    },
    actions:{

    }
})
