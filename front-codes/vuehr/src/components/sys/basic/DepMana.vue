<template>
  <div style="width: 50%;">
    <el-input
        prefix-icon="el-icon-search"
        placeholder="输入部门名称进行搜索..."
        v-model="filterText">
    </el-input>

    <el-tree
        class="filter-tree"
        :data="deps"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        ref="tree">
       <span class="custom-tree-node" style="display:flex;justify-content: space-between;width: 100%;"
             slot-scope="{ node, data }">
         <!--node.label和data.name是一样的，一个用的节点的信息，一个用的json对象的信息-->
        <span>{{ data.name }}</span>

        <span>
          <el-button
              class="depBtn"
              type="primary"
              size="mini"
              @click="() => showAddDepView(data)">
            添加部门
          </el-button>
          <el-button
              class="depBtn"
              type="danger"
              size="mini"
              @click="() => delDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <!--对话框-->
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag type="success">上级部门</el-tag>
            </td>
            <td><span>{{ depParentName }}</span></td>
          </tr>
          <tr>
            <td>
              <el-tag type="success">待添加部门的名称</el-tag>
            </td>
            <td>
              <el-input type="text" v-model="dep.name" placeholder="请输入部门的名称..."></el-input>
            </td>
          </tr>
        </table>

      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="AddDep">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DepMana",
  data() {
    return {
      filterText: '',
      deps: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      dialogVisible: false,
      dep: {
        name: '',

      },
      depParentName: '',
      depParentId: -1,


    };

  },
  methods: {
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
      this.getRequest('system/basic/department/').then(data => {
        if (data) {
          this.deps = data;
        }
      });
    },
    showAddDepView(data) {
      // console.log(data);
      this.depParentName = data.name;
      this.depParentId = data.id;
      this.dialogVisible = true;
    },

    delDep(data) {
      //console.log(data);
      this.$confirm('此操作将永久删除【' + data.name + '】部门, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.deleteRequest('/system/basic/department/' + data.parentId, data).then(data => {
          if (data) {
            this.removeDepFromDeps(this.deps, data.id);
            this.initDeps();
          }
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    AddDep() {
      this.postRequest('/system/basic/department/' + this.depParentId, this.dep).then(data => {
        if (data) {
          this.dialogVisible = false;
          this.restoreDep();
          //this.initDeps();//这样每添加一个就会把树形控件收起
          this.addNewDep2Deps(this.deps, data.obj);
        }
      })
    },
    //- 初始化变量
    restoreDep() {
      this.dep.name = '';
      this.depParentId = -1;
      this.depParentName = '';
    },
    addNewDep2Deps(deps, dep) {
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.parentId) {
          //把dep添加到d的children属性中
          //d.children.push(dep);
          d.children = d.children.concat(dep);
          return;
        } else {
          this.addNewDep2Deps(d.children, dep);
        }
      }
    },

    removeDepFromDeps(deps, id) {
      //console.log('222222');
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == id) {
          console.log('111111111111');
          deps.splice(i,1);
          console.log('deps:',deps);
          return;
        } else {
          this.removeDepFromDeps(d.children, id);
        }
      }
    }
  },
  watch: {
    filterText(newVal) {
      //- filter方法会调用filterNode(value, data)
      this.$refs.tree.filter(newVal);
    }
  },
  mounted() {
    this.initDeps();
  }
}
</script>

<style scoped>
.depBtn {
  padding: 2px;

}
</style>