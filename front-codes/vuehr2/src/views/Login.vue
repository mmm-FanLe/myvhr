<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules"
             v-loading="loading"
             element-loading-text="拼命加载中"
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)"
             label-width="80px" class="loginForm">
      <h3 class="loginTitle">用户登录</h3>
      <el-form-item label="用户名:" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密 码:" prop="password">
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%;" @click="userLogin">立即登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return {
      form:{
        username:'admin',
        password:'123'
      },
      rules:{
        username: [{require:true,message:'请输入用户名',trigger:'blur'}],
        password: [{require:true,message:'请输入密码',trigger:'blur'}]
      },
      loading:false,

    }
  },
  methods:{
    userLogin(){
      this.loading = true;
      this.$refs.loginForm.validate((valid)=>{
        if (valid){
          this.postRequest2('/jsonLogin',this.form).then(data=>{
            if (data){
              this.loading = true;
              window.sessionStorage.setItem('user',JSON.stringify(data.obj));
              //这个功能实现的是：用户在登录页的地址栏输入其他页面的地址，在登录后直接跳转到其他页面的地址
              //              而不是登录后在一个个点击
              let path = this.$route.query.redirect;
              console.log('path ====>',path);
              this.$router.replace((path == '/' || path == undefined)?'/home':path);
            }
          })

        }else{
          this.$message.warning('请输入用户名和密码');
        }
      })
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.loginForm{
  width: 350px;
  border: 1px solid #eaeaea;
  margin: 150px auto;
  padding: 20px 20px 10px 15px;
  box-shadow: 0 0 10px #cac6c6;
  border-radius: 8px;
}

.loginTitle{
  text-align: center;
  color: #505458;
  margin-bottom: 20px;
}
</style>