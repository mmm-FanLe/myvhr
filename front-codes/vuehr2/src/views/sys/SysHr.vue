<template>
  <div>
    <!--上半部分-->
    <div style="display:flex;justify-content: center;margin-bottom: 10px;">
      <el-input v-model="keyword" placeholder="请输入要查找的用户名..."
                prefix-icon="el-icon-search" size="small" style="width: 40%;margin-right: 10px;" @keydown.enter.native="findHrByName"></el-input>
      <el-button type="primary" icon="el-icon-search"
                 size="small" @click="findHrByName">搜索</el-button>
    </div>

    <!--下半部分-->
    <div class="hrContainer">
      <el-card class="box-card hrCard" shadow="always" v-for="(hr,index) in hrs" :key="index">
        <!--上半部分-->
        <div slot="header" class="clearfix cardTitle">
          <span>{{hr.name}}</span>
          <el-button style="color: red" icon="el-icon-delete" type="text" @click="deleteHrByHrid(hr)"></el-button>
        </div>
        <!--下半部分-->
        <div>
          <div style="text-align:center;">
            <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userImg">
          </div>

          <div style="font-size: 12px;color: #50e0df">
            <div>用户名：{{hr.name}}</div>
            <div>手机号码：{{hr.phone}}</div>
            <div>电话号码：{{hr.telephone}}</div>
            <div>住址：{{hr.address}}</div>
            <div>用户状态：
              <el-switch
                  @change="changeEnabled(hr)"
                  v-model="hr.enabled"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-text="启用"
                  inactive-text="禁用">
              </el-switch>
            </div>
            <div style="margin-top: 10px;">
              用户角色：
              <el-tag type="success" size="small"
                      v-for="(role,indexj) in hr.roles" :key="indexj"
                      style="margin-right: 5px;"
              >{{role.nameZh}}</el-tag>

              <el-popover
                  placement="right"
                  title="角色列表"
                  width="200"
                  trigger="click"
                  @show="showPop(hr)"
                  @hide="hidePop(hr)"
                  >
                <el-select v-model="selectedRoles" placeholder="请选择"
                           @change="handlerSelectedChange"
                           multiple>
                  <el-option
                      v-for="(r,indexk) in allRoles"
                      :key="indexk"
                      :label="r.nameZh"
                      :value="r.id">
                  </el-option>
                </el-select>
                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
              </el-popover>

            </div>
            <div>
              备注：{{hr.remark}}
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysHr",
  data(){
    return{
      hr:{
        name:'',
      },
      hrs:[],
      allRoles:[],
      keyword:'',
      selectedRoles: [],
      flag:false,


    };
  },
  methods:{
    findHrByName(){
      if (!this.keyword){
        this.$message.warning('请输入要查找的用户名');
      }
      this.getRequest2('/system/hr/'+this.keyword).then(data=>{
        if (data){
          this.hrs = data;
        }
      })
    },
    initHrs(){
      this.getRequest2('/system/hr/').then(data=>{
        if (data){
          this.hrs = data;
        }
      });
    },
    getAllRoles(){
      this.getRequest2('/system/hr/roles').then(data=>{
        if (data){
          this.allRoles=data;
        }
      })
    },
    deleteHrByHrid(hr){
      this.$confirm('此操作将永久删除【'+hr.name+'】操作员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest2("/system/hr/"+this.hr.id).then(data=>{
          if (data){
            this.initHrs();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    showPop(hr){
      //console.log('showPop');
      let roles = hr.roles;
      this.selectedRoles = [];
      roles.forEach(r=>{
        this.selectedRoles.push(r.id);
      })
    },
    hidePop(hr){
      //- 首先判断用户到底有没有修改(有可能只是点击了一下又关闭了)
      if (this.flag){// - 如果选中值发生变化
        let url = '/system/hr/role?hrId=' + hr.id;
        this.selectedRoles.forEach(rid=>{
          url += '&rids='+rid;
        })
        this.putRequest2(url).then(data=>{
          if (data){
            this.flag=false;
            this.initHrs();
          }
        });
      }

    },
    changeEnabled(hr){
      this.putRequest2('/system/hr/',hr).then(data=>{
        if (data){
          this.initHrs();
        }
      });
    },
    // - 下拉框的选中值发生变化时触发
    handlerSelectedChange(arr){
      //console.log(arr);
      //- 表示选中值发生了变化
      this.flag = true;
    }
  },
  mounted() {
    this.getAllRoles();
    this.initHrs();

  },
  watch:{
    keyword(newVal){
      //- 如果没有输入关键字则默认查找全部操作员
      if (!newVal){
        this.initHrs();
      }
    }
  }
}
</script>

<style scoped>
.cardTitle{
  display: flex;
  justify-content: space-between;
}

.userImg{
  width: 72px;
  height: 72px;
  border-radius: 50%;
}

.hrContainer{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.hrCard{
  width: 350px;
  margin-bottom: 20px;
}
</style>