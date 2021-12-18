<template>
  <div>
    <div>
      <el-input
          class="addJob"
          size="small"
          placeholder="添加职位.."
          prefix-icon="el-icon-plus"
          v-model="job.name"
      ></el-input>
      <el-select v-model="job.titleLevel" placeholder="请选择职称等级" size="small" style="margin-right: 8px;">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>

      <el-button icon="el-icon-plus" size="small" type="primary" @click="addJobLevel">添加</el-button>
    </div>

    <div>
      <el-table
          max-height="300"
          :data="jobs"
          size="small"
          border
          style="width: 100%;margin-top: 10px;"
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
            width="150">
        </el-table-column>

        <el-table-column
            prop="titleLevel"
            label="职称级别">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间">
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
            <el-button size="small" @click="showEditView(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteJob(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button size="small" type="danger" style="margin-top: 8px" :disabled="multipleSelection.length==0" @click="deleteJobByIds">批量删除</el-button>
      <div>
        <!--对话框-->
        <el-dialog
            title="修改职称"
            :visible.sync="dialogVisible"
            width="30%">
          <div>
            <table>
              <tr>
                <td>
                  <el-tag size="small">职称名</el-tag>
                </td>
                <td>
                  <el-input v-model="jobTemp.name" size="small"></el-input>
                </td>
              </tr>

              <tr>
                <td>
                  <el-tag size="small">职称等级</el-tag>
                </td>
                <td>
                  <el-select v-model="jobTemp.titleLevel">
                    <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                    </el-option>
                  </el-select>
                </td>
              </tr>

              <tr>
                <td>
                  <el-tag size="small">是否启用</el-tag>
                </td>
                <td>
                  <el-switch

                      v-model="jobTemp.enabled"
                      active-text="启用"
                      inactive-text="禁用"
                      active-color="#13ce66"
                      inactive-color="#ff4949">
                  </el-switch>
                </td>
              </tr>
            </table>
          </div>
          <span slot="footer" class="dialog-footer">
              <el-button size="small" @click="dialogVisible=false">取 消</el-button>
              <el-button size="small" type="primary" @click="updateJob">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "JobLevelMana",
  data() {
    return {
      job: {
        name: '',
        titleLevel: '',

      },
      jobTemp: {
        name: '',
        titleLevel: '',
        enabled: false
      },
      titleLevels: [
        '正高级',
        '副高级',
        '中级',
        '初级',
        '员级'
      ],
      jobs: [],
      dialogVisible: false,
      multipleSelection:[],

    };
  },
  methods: {
    addJobLevel() {
      //如果这两个值不为空
      if (this.job.name && this.job.titleLevel) {
        this.postRequest('/system/basic/joblevel/', this.job).then(data => {
          if (data) {
            this.initJobLevel();
            this.job = {};
          }
        })
      } else {
        this.$message.warning('请填写添加的职称并选择职称等级');
      }
    },
    initJobLevel() {
      this.getRequest('/system/basic/joblevel/').then(data => {
        if (data) {
          this.jobs = data;
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteJob(row) {
      this.$confirm('此操作将永久删除【' + row.name + '】职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/joblevel/' + row.id).then(data => {
          if (data) {

            this.initJobLevel();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    showEditView(row){
      //- 对象合并，直接将属性值合并，不是引用传递
      Object.assign(this.jobTemp,row);
      this.dialogVisible=true;

    },
    updateJob(){
      this.putRequest('/system/basic/joblevel/',this.jobTemp).then(data=>{
        if (data){
          this.dialogVisible=false;
          this.jobTemp={};
          this.initJobLevel();
        }
      })
    },
    deleteJobByIds(){

      this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item=>{
          ids += 'ids='+item.id+'&';
        })
        this.deleteRequest('/system/basic/joblevel/'+ids).then(data=>{
          if (data){
            this.initJobLevel();
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
    this.initJobLevel();
  }
}
</script>

<style scoped>
.addJob {
  width: 200px;
  margin-right: 8px;
}
</style>