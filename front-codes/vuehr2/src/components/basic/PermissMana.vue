<template>
  <div>
    <!--上半部分-->
    <div style="display:flex;justify-content: space-around">
      <el-input placeholder="请输入角色英文名" v-model="role.name" style="width: 500px;margin-right: 10px;">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input v-model="role.nameZh" placeholder="请输入角色中文名" style="width: 500px;margin-right: 10px;"></el-input>
      <el-button type="primary" icon="el-icon-plus" size="small" @click="addRole">添加角色</el-button>
    </div>

    <!--下半部分-->
    <div style="margin-top: 10px;">

      <el-collapse v-model="activeName" @change="handlerChange" accordion>

        <el-collapse-item :title="role.nameZh" :name="role.id" v-for="(role,index) in roles">

          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color: red" type="text" icon="el-icon-delete" @click="deleteRoleById(role)"></el-button>
            </div>

            <el-tree :data="menus" :props="defaultProps" :key="index"
                     show-checkbox node-key="id" ref="tree"
                     :default-checked-keys="selectedMenusId"
            ></el-tree>

            <div style="display:flex;justify-content: flex-end">
              <el-button size="mini" @click="activeName=-1">取消修改</el-button>
              <el-button type="primary" size="mini" @click="updateMidsByRid(role.id,index)">确认修改</el-button>
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
        nameZh: ''
      },
      roles: [],
      menus: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      activeName: -1,
      selectedMenusId: [],

    }
  },
  methods: {
    initRoles() {
      this.getRequest2('/system/basic/permission/').then(data => {
        if (data) {
          this.roles = data;
        }
      });
    },
    initMenus() {
      this.getRequest2('/system/basic/permission/menus').then(data => {
        if (data) {
          this.menus = data;
        }
      });
    },
    handlerChange(rid){
      //console.log(rid);
      if (rid){
        //- 加载权限的树形结构
        this.initMenus();
        //- 加载角色对应的权限
        this.initSelectedMenusId(rid);
      }
    },
    initSelectedMenusId(rid){
      this.getRequest2('/system/basic/permission/mid/'+rid).then(data=>{
        if (data){
          this.selectedMenusId=data;
        }
      })
    },
    updateMidsByRid(rid,index){
      let node = this.$refs.tree[index];
      //- 只返回叶子节点
      //let leafNodes = node.getCheckedNodes(true);
      let mids = node.getCheckedKeys(true);
      //console.log(mids);
      let url = '/system/basic/permission/?rid='+rid;
      mids.forEach(mid=>{
        url += '&mids=' + mid;
      })
      //console.log(url);
      this.putRequest2(url).then(data=>{
        if (data){
          this.activeName=-1;
        }
      });
    },
    addRole(){
      if (this.role.name && this.role.nameZh){
        this.postRequest2('/system/basic/permission/',this.role).then(data=>{
          if (data){
            this.role={};
            this.initRoles();
          }
        })
      }else{
        this.$message.warning('请输入角色的英文名和中文名！');
      }
    },
    deleteRoleById(role){
      this.$confirm('此操作将永久删除【'+role.nameZh+'】角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest2('/system/basic/permission/role/'+role.id).then(data=>{
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
    //this.initMenus();
    this.initRoles();
  }
}
</script>

<style scoped>

</style>