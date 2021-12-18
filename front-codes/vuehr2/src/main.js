import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store/index'
import 'font-awesome/css/font-awesome.min.css'

import axios from "axios";
import {postKeyValueRequest2} from "./utils/axiosConfig";
import {postRequest2} from "./utils/axiosConfig";
import {putRequest2} from "./utils/axiosConfig";
import {deleteRequest2} from "./utils/axiosConfig";
import {getRequest2} from "./utils/axiosConfig";
import {initMenu2} from "./utils/menu";

Vue.prototype.axios = axios;
Vue.prototype.deleteRequest2 = deleteRequest2;
Vue.prototype.getRequest2 = getRequest2;
Vue.prototype.putRequest2 = putRequest2;
Vue.prototype.postRequest2 = postRequest2;
Vue.prototype.postKeyValueRequest2 = postKeyValueRequest2;

Vue.config.productionTip = false
Vue.use(ElementUI);

//路由前置守卫,每次跳转页面时进行检查
router.beforeEach((to,from,next)=>{

  if (to.path == '/'){
    next();
  }else{
    //如果已经登录过则加载menus
    if (window.sessionStorage.getItem('user')){
      initMenu2(router,store);
      next();
    }else{
      //如果没有登录还想跳转到其他页面则设置重定向，登录后直接跳转到目标页面
      next('/?redirect='+to.path);
    }
  }
})

new Vue({
  router,
  store,//引入store！！！！！！
  render: h => h(App)
}).$mount('#app')
