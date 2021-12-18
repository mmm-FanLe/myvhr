<template>
  <div>

    <!--上半部分-->
    <div class="addPos">
      <el-input prefix-icon="el-icon-search" placeholder="添加职位..." size="small" v-model="position.name"></el-input>
      <el-button type="primary" icon="el-icon-plus" size="small" style="margin-left: 10px;" @click="addPosition">添加</el-button>
    </div>
    <!--表格-->
    <el-table
        ref="multipleTable"
        :data="tableData"
        border
        tooltip-effect="dark"
        style="width: 100%"
        max-height="350"
        @selection-change="handleSelectionChange"
        >
      <el-table-column
          type="selection"
          width="55">
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
          width="200">
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
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="danger" size="small" style="margin-top: 10px;" :disabled="selections.length==0" @click="deletePositionByIds">批量删除</el-button>

    <el-dialog
        title="修改职位名称"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <div style="margin-bottom: 10px;">
          <el-tag type="success">职位名称:</el-tag>
          <el-input v-model="updatePosition.name" size="small" style="width: 150px;margin-left: 10px;"></el-input>
        </div>

        <div>
          <el-tag type="success" style="margin-right: 10px;">是否启用</el-tag>
          <el-switch
              v-model="updatePosition.enabled"
              active-text="启用"
              inactive-text="禁止">
          </el-switch>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
    <el-button type="primary" @click="doUpdatePosition" size="mini">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import {Message} from "element-ui";

export default {
  name: "PosMana",
  data(){
    return{
      position:{
        name:''
      },
      tableData:[],
      selections:[],
      dialogVisible:false,
      updatePosition:{
        name:'',
        enabled:true
      }
    };
  },
  methods:{
    initTable(){
      this.getRequest2('/system/basic/pos/').then(data=>{
        if (data){
          this.tableData = data;
        }
      });
    },
    handleEdit(index,row){

      Object.assign(this.updatePosition,row);
      this.dialogVisible=true;

    },
    handleDelete(index,row){
      console.log(index);
      console.log(row);
      this.$confirm('此操作将永久删除【'+row.name+'】职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest2('/system/basic/pos/'+row.id).then(data=>{
          if (data){
           this.initTable();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    addPosition(){
      if (this.position.name == ''){
        Message.warning('请输入职位的名称！');
      }
      this.postRequest2('/system/basic/pos/',this.position).then(data=>{
        if (data){
          this.position.name='';
          this.initTable();
        }
      })
    },
    handleSelectionChange(selections){
      //console.log(val);//数组对象
      this.selections = selections;
    },
    deletePositionByIds(){

      this.$confirm('此操作将永久删除【'+this.selections.length+'个】职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.selections.forEach(selection=>{
          ids += 'ids='+selection.id+'&';
        })
        //console.log(ids);
        this.deleteRequest2('/system/basic/pos/'+ids).then(data=>{
          if (data){
            this.selections=[];
            this.initTable();
          }
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    doUpdatePosition(){
      this.putRequest2('/system/basic/pos/',this.updatePosition).then(data=>{
        if (data){
          this.updatePosition={};
          this.dialogVisible = false;
          this.initTable();
        }
      })
    }

  },
  mounted() {
    this.initTable();
  }
}
</script>

<style scoped>
.addPos{
  width: 50%;
  display: flex;
  margin-bottom: 15px;
}
</style>