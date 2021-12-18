<template>
  <el-container>
    <!--header-->
    <el-header class="headerContainer">
      <h3 class="headerTitle">微人事</h3>

      <el-dropdown trigger="click" class="userInfo" @command="commandHandler">
      <span class="el-dropdown-link user">
        {{user.name}}<i><img :src="user.userface" alt="" class="userImg"></i>
      </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
          <el-dropdown-item command="setting">设置</el-dropdown-item>
          <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <!--aside&main-->
    <el-container>
      <el-aside width="200px">
        <el-menu
            router
            unique-opened
            class="el-menu-vertical-demo"
            >
          <el-submenu :index="index+''" :key="index" v-for="(item,index) in routes" v-if="!item.hidden">

            <template slot="title">
              <i :class="item.iconCls"></i>
              <span style="margin-left: 8px;">{{item.name}}</span>
            </template>
            <el-menu-item v-for="(child,indexj) in item.children" :index="child.path" :key="indexj">
              <span>{{child.name}}</span>
            </el-menu-item>


          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path != '/home'">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
        </el-breadcrumb>
        <h3 v-else class="homeWelcome">
          <h3>欢迎 <span style="color:#e07cae">{{user.name}}</span> 来到微人事!</h3>
        </h3>

        <router-view class="homeRouterView"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      user:JSON.parse(window.sessionStorage.getItem('user')),

    };
  },
  methods:{
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest2('/logout');
          //清除登录信息
          window.sessionStorage.removeItem('user');
          //清除该用户的store内的数据
          this.$store.commit('initRoutes2',[]);
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

  },
  computed:{
    routes(){
      return this.$store.state.routes;
    }
  }
}
</script>

<style scoped>
.headerContainer{
  background-color: #359ae0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.headerTitle{
  font-family: "Xingkai SC";
  color: #fff;
  font-size: 30px;
}

.userInfo{
  cursor: pointer;
  color: #fff;
}

.homeWelcome{
  font-family: "Xingkai SC";
  font-size: 30px;
  color: #50e0df;
  margin: 100px auto;
  text-align: center;
}

.homeRouterView{
  margin-top: 15px;
}

.userImg{
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-left: 8px;
}

.user{
  display: flex;
  align-items: center;
}
</style>