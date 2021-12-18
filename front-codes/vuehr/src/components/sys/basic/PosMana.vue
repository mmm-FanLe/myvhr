<template>
  <div>

    <div>
      <el-input
          class="addPosInput"
          size="small"
          placeholder="添加职位.."
          prefix-icon="el-icon-plus"
          @keydown.enter.native="addPosition"
          v-model="pos.name"
      ></el-input>
      <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
    </div>

    <div class="posManaMain">
      <el-table
          max-height="500"
          @selection-change="handleSelectionChange"
          :data="position"
          border
          stripe
          size="small"
          style="width: 100%">

        <el-table-column
            type="selection"
            width="55"
        >
        </el-table-column>

        <el-table-column
            prop="id"
            label="编号"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位名称"
            width="120">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="150">
        </el-table-column>

        <el-table-column
            label="是否启用">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
            <el-tag type="warning" v-else>未启用</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="showEditView(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>

      </el-table>
      <!--批量删除-->
      <el-button size="small" type="danger" style="margin-top: 8px;" :disabled="multipleSelection.length==0" @click="deleteByIds">批量删除</el-button>
    </div>
      <!--修改信息时的对话框-->
      <el-dialog
          title="修改职位信息"
          :visible.sync="dialogVisible"
          width="250px">
        <div>
          <div>
            <el-tag>职位名称</el-tag>
            <el-input class="updatePosInfo" size="small" v-model="updatePos.name"></el-input>
          </div>
          <div>
            <el-tag style="margin-right: 8px;">是否启用</el-tag>
            <el-switch
                v-model="updatePos.enabled"
                active-text="启用"
                inactive-text="禁用"
                active-color="#13ce66"
                inactive-color="#ff4949"
            >
            </el-switch>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="dialogVisible=false">取 消</el-button>
          <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
        </span>
      </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosMana",
  data() {
    return {
      pos: {
        name: '',
      },
      updatePos: {
        name: '',
        enabled:true
      },
      position: [],
      dialogVisible: false,
      multipleSelection:[],

    };
  },
  methods: {
    addPosition() {
      if (this.pos.name == '') {
        this.$message.warning('请输入添加的职位');
      } else {
        this.postRequest('/system/basic/pos/', this.pos).then(data => {
          if (data) {
            this.initPosition();
            this.pos.name = '';
          }
        })
      }
    },
    initPosition() {
      this.getRequest('/system/basic/pos/').then(data => {
        if (data) {
          this.position = data;
        }
      })
    },
    showEditView(index, data) {

      /*
        ! 如果这样写:this.updatePos = data; 相当于引用传递
          - 这样的话就是把data的索引给了this.updatePos,对话框的输入框又双向绑定了this.updatePos
          - 所以输入框变=updatePos变=data变=表格中的数据变
        ! this.updatePos.name = data.name; 相当于值传递
          - updatePos里面的属性变不影响data的属性变
       */
      this.updatePos.id = data.id;//修改时需要根据id修改
      this.updatePos.name = data.name;
      //Object.assign(this.updatePos,data);
      this.dialogVisible = true;

    },
    doUpdate() {
      this.putRequest('/system/basic/pos/', this.updatePos).then(data => {
        if (data) {
          this.initPosition();
          this.updatePos = {};
          this.dialogVisible = false;
        }
      })
    },
    handleDelete(index, data) {
      this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/pos/' + data.id).then(data => {
          if (data) {
            this.initPosition();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val){
      //console.log(val);
      this.multipleSelection=val;
    },
    deleteByIds(){
      this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        let ids = '?';
        this.multipleSelection.forEach(item=>{
          ids += 'ids='+item.id + '&';
        })
        this.deleteRequest('/system/basic/pos/'+ids).then(data => {
          if (data) {
            this.initPosition();
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
    this.initPosition();
  }

}
</script>

<style scoped>

.addPosInput {
  width: 300px;
  margin-right: 8px;
}

.posManaMain {
  margin-top: 10px;
}

.updatePosInfo {
  width: 100px;
  margin-left: 8px;
}

</style>