<template>
  <div>

    <div class="roleInfo">
      <el-input placeholder="请输入角色英文名" v-model="role.name">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input placeholder="请输入角色中文名" v-model="role.nameZh" @keydown.enter.native="addRole"></el-input>
      <el-button type="primary" size="small" icon="el-icon-plus" @click="addRole">添加角色</el-button>
    </div>
    <!--下半部分-->
    <div class="permissionMain">
      <el-collapse accordion @change="changeMenu" v-model="activeName">

        <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">

          <el-card class="box-card" shadow="always">

            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color: #ff0000" type="text"
                         icon="el-icon-delete" @click="delRoleById(r.id,r.nameZh)"></el-button>
            </div>

            <div>
              <!--node-key，其值为节点数据中的一个字段名，该字段在整棵树中是唯一的-->
              <el-tree
                  :key="index"
                  :data="allMenus" :props="defaultProps"
                  node-key="id"
                  ref="tree"
                  :default-checked-keys="selectedMenus"
                  show-checkbox></el-tree>

              <div style="display:flex;justify-content: flex-end">
                <el-button size="mini" @click="activeName=-1">取消修改</el-button>
                <el-button size="mini" type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
              </div>

            </div>
          </el-card>

        </el-collapse-item>

      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissMana",
  data() {
    return {
      role: {
        name: '',
        nameZh: '',
      },
      roles: [],
      allMenus: [],
      selectedMenus: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      activeName:-1,


    };
  },
  methods: {
    initRoles() {
      this.getRequest('/system/basic/permiss/').then(data => {
        if (data) {
          this.roles = data;
        }
      })
    },
    initAllMenus() {
      this.getRequest('/system/basic/permiss/menus').then(data => {
        if (data) {
          this.allMenus = data;
        }
      })
    },
    //- 得到这个角色具有权限的所有id
    initSelectedMenus(rid){
      this.getRequest('/system/basic/permiss/mids/'+rid).then(data => {
        if (data) {
          this.selectedMenus = data;
        }
      })
    },
    //- rid是回调参数，返回的是el-collapse标签上的activeName值,而这个值又绑定了el-collapse-item上的name值，name值绑定了rid
    changeMenu(rid) {
      console.log(rid);
      if (rid) {
        this.initAllMenus();
        this.initSelectedMenus(rid);
      }
    },
    doUpdate(rid,index){
      //console.log(rid);
      let tree = this.$refs.tree[index];
      //- getCheckedKeys参数为true代表只返回叶子节点的key(这里绑定的是menu的id)
      let checkedKeys = tree.getCheckedKeys(true);
      //console.log(checkedKeys);
      let url = '/system/basic/permiss/?rid='+rid;
      checkedKeys.forEach(key=>{
        url += '&mids='+key;
      });

      this.putRequest(url).then(data=>{
          if (data){
            // this.initRoles();
            this.activeName=-1;
          }
      })
    },
    addRole(){
      if (this.role.name && this.role.nameZh){
        this.postRequest('/system/basic/permiss/role',this.role).then(data=>{
          if (data){
            this.initRoles();
            this.role={};
          }
        })
      }else{
        this.$message.warning('请输入角色的中文名和英文名！！');
      }
    },
    delRoleById(rid,rnameZh){
      this.$confirm('此操作将永久删除【'+rnameZh+'】角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.deleteRequest('/system/basic/permiss/role/'+rid).then(data=>{
          if (data){
            this.initRoles();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  },
  mounted() {
    this.initRoles();
  }
}
</script>

<style scoped>
.roleInfo {
  display: flex;
  justify-content: flex-start;
}

.roleInfo .el-input {
  width: 300px;
  margin-right: 6px;
}

.permissionMain {
  margin-top: 10px;
  width: 800px;
}

</style>