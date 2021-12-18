<template>
  <div>
    <!--上半部分-->
    <div>
      <el-input v-model="addLevel.name" placeholder="添加职位..."
                size="small" style="width: 200px;margin-right: 10px;" prefix-icon="el-icon-plus"></el-input>
      <el-select v-model="addLevel.titleLevel" placeholder="请选择职称等级" size="small">
        <el-option
            v-for="item in levels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-plus"
                 size="small" style="margin-left: 10px;"
                 @click="addJobLevel"
      >添加</el-button>
    </div>

    <!--下半部分-->
    <div style="margin-top: 15px;">
      <el-table
          border
          ref="jobs"
          :data="jobs"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
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
            label="职称名称"
            width="120">
        </el-table-column>

        <el-table-column
            prop="titleLevel"
            label="职称级别"
            width="80">
        </el-table-column>

        <el-table-column
            prop="createDate"
            label="创建时间"
            width="180">
        </el-table-column>

        <el-table-column
            label="是否启用"
            width="120">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
            <el-tag type="warning" v-else>未启用</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index,scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index,scope.row)">删除</el-button>

          </template>
        </el-table-column>


      </el-table>
      <el-button type="danger" size="small"
                 :disabled="selectedJobs.length==0" style="margin-top: 10px;"
                 @click="deleteJobLevelByIds"
      >批量删除</el-button>
    </div>

    <!--编辑对话框-->
    <el-dialog
        title="修改职称信息"
        :visible.sync="dialogVisible"
        width="30%"
        >
      <div>
        <el-row
            type="flex" justify="space-around" style="margin-bottom: 10px;"
        >
         <el-col :span="6">
           <el-tag size="small">职称名</el-tag>
         </el-col>
         <el-col :span="6">
           <el-input v-model="updateJob.name" size="mini" style="width: 100px;"></el-input>
         </el-col>
        </el-row>
        <el-row type="flex" justify="space-around" style="margin-bottom: 10px;">
         <el-col :span="6">
           <el-tag size="small">职称等级</el-tag>
         </el-col>
         <el-col :span="6">
           <el-select v-model="updateJob.titleLevel" size="mini" style="width: 100px;">
             <el-option
                 v-for="item in levels"
                 :key="item"
                 :label="item"
                 :value="item">
             </el-option>
           </el-select>
         </el-col>
        </el-row>
        <el-row type="flex" justify="space-around">
         <el-col :span="6">
           <el-tag size="small">是否启用</el-tag>
         </el-col>
         <el-col :span="6">
           <el-switch
               width="60"
               v-model="updateJob.enabled"
               active-color="#13ce66"
               inactive-color="#ff4949"
               active-text="启用"
               inactive-text="禁用">
           </el-switch>
         </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
    <el-button type="primary" @click="updateJobLevelById" size="mini">确 定</el-button>
  </span>
    </el-dialog>
  </div>


</template>

<script>
export default {
  name: "JobLevelMana",
  data() {
    return {
      addLevel: {
        name: '',
        titleLevel: ''
      },
      levels: [
        '正高级',
        '副高级',
        '中级',
        '初级',
        '员级',
        '元老级'
      ],
      jobs: [],
      selectedJobs:[],
      dialogVisible:false,
      updateJob:{
        name: '',
        titleLevel: '',
        enabled:true
      },


    };
  },
  methods: {
    handleSelectionChange(arr) {
      //console.log(arr);
      this.selectedJobs=arr;
    },
    handleEdit(index, row) {
      //this.updateJob=row;
      //- updateJob必须先赋值上空属性(name,titleLevel,enabled)
      Object.assign(this.updateJob,row);
      this.dialogVisible=true;
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除【'+row.name+'】职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest2('/system/basic/joblevel/'+row.id).then(data=>{
          if (data){
            this.initjobs();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    initjobs() {
      this.getRequest2('/system/basic/joblevel/').then(data => {
        if (data) {
          this.jobs = data;

        }
      })
    },
    addJobLevel(){
      if (this.addLevel.name=='' || this.addLevel.titleLevel==''){
        this.$message.warning('请输入添加的职称名称并且选择职称等级！');
      }
      this.postRequest2('/system/basic/joblevel/',this.addLevel).then(data=>{
        if (data){
          this.addLevel={};
          this.initjobs();
        }
      })
    },
    deleteJobLevelByIds(){

      this.$confirm('此操作将永久删除这【'+this.selectedJobs.length+'】个职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.selectedJobs.forEach(job=>{
          ids += 'ids='+job.id+'&';
        })
        //console.log(ids);
        this.deleteRequest2('/system/basic/joblevel/'+ids).then(data=>{
          if (data){
            this.initjobs();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    updateJobLevelById(){
      this.putRequest2('/system/basic/joblevel/',this.updateJob).then(data=>{
        if (data){
          this.dialogVisible=false;
          this.initjobs();
        }
      })
    }

  },
  mounted() {
    this.initjobs();
  }
}
</script>

<style scoped>

</style>