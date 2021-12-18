<template>
  <div>
    <el-container>
      <!--el-header-->
      <el-header class="homeHeader">
        <div class="title">微人事</div>
        <!--用户中心-->
        <el-dropdown trigger="click" class="userInfo" @command="commandHandler">
          <span class="el-dropdown-link userImg">
            {{ user.name }}<i><img :src="user.userface" alt=""></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <!--command的值会作为参数传递给函数-->
            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <!--
              1.启用router模式会在激活导航时以 index 作为 path 进行路由跳转
              2.unique-opened:每次只展开一个下拉列表
          -->
          <el-menu router unique-opened>
            <!--
                1.this.$router.options.routes会得到路由配置的所有对象
                2.index都等于1(或同一个值)时，点击一个会把列表渲染的所有都展开，因为它会认为这是一个列表，所以需要动态指定一个值
                  但是此时还会有一个问题，console会报错：想要一个String但是给了一个value
                  所以要把他变成字符串

            -->
            <el-submenu :index="index+''" v-for="(item,index) in routes/*this.$router.options.routes*/" :key="index" v-if="!item.hidden">
              <!--<template v-if="!item.hidden">-->
              <template slot="title">
                <i style="color: #359ae0;margin-right: 10px;" :class="item.iconCls"></i>
                <span>{{ item.name }}</span>
              </template>
              <!--el-menu-item的父亲不能是template-->
              <el-menu-item v-for="(child,indexj) in item.children" :index="child.path" :key="indexj">
                {{ child.name }}
              </el-menu-item>

            </el-submenu>
          </el-menu>
        </el-aside>


        <el-main>
          <!--面包屑导航-->
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <h3 v-else class="homeWelcome">
              欢迎来到微人事!
          </h3>

          <!--
              该组件的跳转页面的内容，会显示在这个地方
              直接在这绑定class，到时候每个页面内容都会应用上
          -->
          <router-view class="homeRouteView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem('user')),

    };
  },
  methods: {
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest('/logout');
          //清除登录信息
          window.sessionStorage.removeItem('user');
          //清除该用户的store内的数据
          this.$store.commit('initRoutes',[]);
          //回到登录页
          this.$router.replace('/');

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });
        });
      }
    },
    menuClick(index) {
      this.$router.push(index);
    }
  },
  computed:{
    //这里面的方法当做变量使用
    routes(){
      //console.log('路由中的routes:',this.$router.options.routes);
      //console.log('Vuex中的routes:',this.$store.state.routes);
      return this.$store.state.routes;
    }
  }
}
</script>

<style scoped>
.homeHeader {
  background-color: #359ae0;
  display: flex;
  justify-content: space-between; /*x轴分成两边*/
  align-items: center; /*y轴居中*/
  padding: 0px 15px;
  box-sizing: border-box; /*默认值，传统的盒子模型*/
}

.homeHeader .title {
  font-size: 30px;
  font-family: "Xingkai TC";
  color: #fff;
}

.homeHeader .userInfo {
  cursor: pointer;
  color: #fff;
}

.userImg {
  display: flex;
  align-items: center;
}

.userImg img {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-left: 10px;
}

.homeWelcome{
  text-align: center;
  font-size: 30px;
  font-family: "Xingkai SC";
  color: #359ae0;
  margin-top: 50px;

}

.homeRouteView{
  margin-top: 15px;
}


</style>