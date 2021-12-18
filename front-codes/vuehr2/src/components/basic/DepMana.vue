<template>
  <div>
    <div style="width: 50%;">
      <!--上半部分-->
      <div style="margin-bottom: 10px;">
        <el-input v-model="filterText" placeholder="输入部门名称进行搜索..." prefix-icon="el-icon-search" size="small"
                  ></el-input>
      </div>

      <!--下半部分-->
      <div>
        <el-tree
            class="filter-tree"
            :data="deps"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree">

          <span class="custom-tree-node" slot-scope="{ node, data }"
                style="display:flex;justify-content: space-between;width: 100%;">
            <span>{{ node.label }}</span>
            <span>
              <el-button
                  class="depBtn"
                  type="primary"
                  size="mini"
                  @click="() => showDialogView(data)">
                添加部门
              </el-button>
              <el-button
                  class="depBtn"
                  type="danger"
                  size="mini"
                  @click="() => remove(node, data)">
                删除部门
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>

    <!--添加部门时的对话框-->
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="50%">
      <div style="width: 100%;">
        <el-row style="display:flex;justify-content: space-around;align-items: center;margin-bottom: 10px;">
         <el-col :span="6"><el-tag type="success">上级部门</el-tag></el-col>
         <el-col :span="6">
           {{parentDep.name}}
         </el-col>
        </el-row>
        <el-row style="display:flex;justify-content: space-around;align-items: center">
         <el-col :span="6"><el-tag type="success">待添加部门名称</el-tag></el-col>
         <el-col :span="6">
           <el-input v-model="dep.name" size="small" style="width: 150px;" placeholder="请输入部门名称..."></el-input>
         </el-col>
        </el-row>

      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelAddDep" size="small">取 消</el-button>
    <el-button type="primary" @click="addDep" size="small">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "DepMana",
  data() {
    return {
      dep: {
        name: '',

      },
      deps: [],
      filterText: '',
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      dialogVisible:false,
      parentDep:{
        id:-1,
        name:'',
      }

    };
  },
  methods: {
    showDialogView(data) {
      console.log(data);
      this.parentDep.id = data.id;
      this.parentDep.name = data.name;
      this.dialogVisible = true;
    },
    remove(node, data) {
      //console.log(data);
      this.parentDep.id = data.parentId;

      this.$confirm('此操作将永久删除【'+data.name+'】部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.deleteRequest2('/system/basic/dep/'+this.parentDep.id,data).then(data=>{
          if (data){
            //this.initDeps();
            this.deleteDepFromDeps(this.deps,data);
            console.log(this.deps);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    /*
    * 对树节点进行筛选时执行的方法，
    * 返回true表示这个节点可以显示，返回false则表示这个节点会被隐藏
    * */
    filterNode(value, data) {
      //- 没有传value则返回true，都可以显示
      if (!value) return true;
      //- 如果传了value则看对象中包不包含value
      return data.name.indexOf(value) !== -1;
    },
    initDeps() {
      this.getRequest2('/system/basic/dep/').then(data => {
        if (data) {
          this.deps = data;
          console.log(this.deps);
        }
      })
    },
    addDep(){
      this.postRequest2('/system/basic/dep/'+this.parentDep.id,this.dep).then(data=>{
        if (data){
          this.dialogVisible=false;
          this.restoreInfo();
          //this.initDeps();
          this.addNewDepToDeps(this.deps,data.obj);
        }
      });
    },
    restoreInfo(){
      this.dep.name='';
      this.parentDep.id=-1;
      this.parentDep.name='';
    },
    cancelAddDep(){
      this.dialogVisible=false;
      this.restoreInfo();
    },
    /*动态添加dep*/
    addNewDepToDeps(deps,dep){
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.parentId){
          d.children = d.children.concat(dep);
        }else{
          this.addNewDepToDeps(d.children,dep);
        }
      }
    },
    /*动态删除*/
    deleteDepFromDeps(deps,dep){
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.id){
          deps.splice(i,1);
          return;
        }else{
          this.deleteDepFromDeps(d.children,dep);
        }
      }
    }
  },
  mounted() {
    this.initDeps();
  },
  watch: {
    filterText(newVal) {
      //- filter方法会调用filterNode(value, data)
      this.$refs.tree.filter(newVal);
    }
  }
}
</script>

<style scoped>
.depBtn {
  padding: 2px;
}
</style>