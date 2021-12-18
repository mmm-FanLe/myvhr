import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import axios from "axios";
import {postRequest} from "./utils/api";
import {postKeyValueRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {initMenu} from "./utils/menus";
import 'font-awesome/css/font-awesome.min.css'

//给Vue类的实例(Vue对象)添加方法
Vue.prototype.postRequest = postRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.axios = axios;

Vue.config.productionTip = false
Vue.use(ElementUI);


//路由前置守卫,相当于后端的过滤器
router.beforeEach((to, from, next) => {
    console.log('to:',to);
    //console.log('from:',from);
    //如果要跳转到登录页则直接放行
    if (to.path == '/') {
        next();
    } else {
        //里面有信息代表已经登录了
        if (window.sessionStorage.getItem('user')) {
            //初始化Vuex中的routes
            initMenu(router, store);
            next();
        } else {
            //若没有登录则跳转至登录页
            next('/?redirect=' + to.path);
        }
        //console.log('1111');
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
