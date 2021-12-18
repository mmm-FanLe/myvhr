<template>
  <div>
    <!--添加model属性就可以给表单绑定数据:v-model="loginForm.username"-->
    <el-form ref="loginForm" :rules="rules" :model="loginForm"
             v-loading="loading"
             element-loading-text="正在登陆..."
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)"
             label-width="80px" class="loginContainer">
      <h3 class="loginTitle">系统登录</h3>
      <!--由于有表单验证，所以一定要加prop属性-->
      <!--auto-complete自动补全功能，类似缓存,比如：输入马，会提示马遥-->
      <el-form-item prop="username" label="用户名">
        <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名" auto-complete="off">

        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" auto-complete="off"
                  @keydown.enter.native="submitLogin"
        >

        </el-input>
      </el-form-item>
      <!--<el-checkbox v-model="checked" class="loginRemember"></el-checkbox>-->
      <el-button type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
    </el-form>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    return {
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
      loginForm: {/*表单默认值*/
        username: 'admin',
        password: '123'
      },
      checked: true,
      loading: false

    };
  },
  methods: {
    submitLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          //此时的data已经是以前的res.data,因为在axios的拦截器中处理过了
          this.postKeyValueRequest('/doLogin', this.loginForm).then(data => {
            this.loading=false;
            //由于在api.js中用了axios的拦截处理过一次异常，所以在这里如果请求失败不需要特殊处理
            if (data) {//如果data不为空则说明，后端没有错误
              // alert(JSON.stringify(res));
              console.log('login:', data);
              // console.log(res.obj);
              // alert(JSON.stringify(res.obj));
              window.sessionStorage.setItem('user', JSON.stringify(data.obj));
              let path = this.$route.query.redirect;
              console.log('path:', path);
              //this.$router.replace('/home');
              this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
            }
          })
          //alert('hello');
        } else {
          this.$message.error('请输入所有字段');
          return false;
        }
      })
    }
  }
}
</script>

<style scoped>
.loginContainer {
  border-radius: 15px;
  margin: 180px auto;
  width: 350px;
  padding: 20px 35px 15px 35px;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.loginTitle {
  margin: 0px auto 20px auto;
  text-align: center;
  color: #505458;
}

.loginRemember {
  text-align: left;
  margin: 0px 0px 15px 0px;
}
</style>