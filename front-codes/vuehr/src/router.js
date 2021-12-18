import Vue from 'vue'
import Router from 'vue-router'
import Login from "./views/Login";
import Home from "./views/Home";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      name:'Login',
      component:Login,
      hidden:true
    },
    {
      path:'/home',
      name:'Home',
      component:Home,
      hidden:true
    },
    /*{
      path:'/home',
      name:'导航1',
      component:Home,
      children:[/!*当加了这个属性后，跳转页面时，只会在该组件的router-view内跳转*!/
        {
          path:'/test1',
          name:'选项1',
          component:Test1
        },
        {
          path:'/test2',
          name:'选项2',
          component:Test2
        }
      ]
    },*/


  ]
})
