<template>
  <div>
    <div style="margin-top: 10px;display:flex;justify-content: center">
      <el-input v-model="keyword" placeholder="请输入要查找的用户名..." prefix-icon="el-icon-search"
                style="width: 400px;margin-right: 10px;" @keydown.enter.native="doSearch"></el-input>
      <el-button type="primary" icon="el-icon-search" size="small" @click="doSearch">搜索</el-button>
    </div>
    <div class="hrContainer">
      <el-card class="hrCard" v-for="(hr,index) in hrs" :key="index">
        <div slot="header" class="clearfix">
          <span>{{ hr.name }}</span>
          <el-button style="float: right; padding: 3px 0;color: red" type="text" icon="el-icon-delete"
                     @click="delHr(hr)"></el-button>
        </div>

        <div>
          <div style="text-align:center;">
            <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userImg">
          </div>
          <div style="margin-top: 20px;font-size: 12px;color: #50e0df">
            <div>用户名：{{ hr.name }}</div>
            <div>手机号码：{{ hr.phone }}</div>
            <div>电话号码：{{ hr.telephone }}</div>
            <div>地址：{{ hr.address }}</div>
            <div>
              用户状态：
              <el-switch
                  v-model="hr.enabled"
                  @change="changeEnabled(hr)"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-text="启用"
                  inactive-text="禁用">
              </el-switch>
            </div>
            <div>
              用户角色：
              <el-tag type="success" v-for="(role,indexj) in hr.roles" :key="indexj" style="margin-right: 8px;"
                      size="small">{{ role.nameZh }}
              </el-tag>

              <el-popover
                  placement="right"
                  title="角色列表"
                  @show="showPop(hr)"
                  @hide="hidePop(hr)"
                  width="200"
                  trigger="click"
              >
                <div>
                  <el-select v-model="selectedRoles" placeholder="请选择" multiple>
                    <el-option
                        v-for="(r,indexk) in allRoles"
                        :key="indexk"
                        :label="r.nameZh"
                        :value="r.id">
                      <!--value是提交到服务器的数据-->
                    </el-option>
                  </el-select>
                </div>
                <el-button slot="reference" type="text" icon="el-icon-more"></el-button>
              </el-popover>
            </div>
            <p>备注：{{ hr.remark }}</p>

          </div>
        </div>

      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysHr",
  data() {
    return {
      keyword: '',
      hrs: [],
      allRoles: [],
      selectedRoles: [],


    };
  },
  methods: {
    initHrs() {
      this.getRequest('/system/hr/').then(data => {
        if (data) {
          this.hrs = data;
          //console.log(this.hrs);
        }
      })
    },
    changeEnabled(hr) {

      //console.log(hr);
      this.putRequest('/system/hr/', hr).then(data => {
        if (data) {
          this.initHrs();
        }
      })
    },
    initAllRoles() {
      this.getRequest('/system/hr/roles').then(data => {
        if (data) {
          this.allRoles = data;
        }
      })
    },
    showPop(hr) {
      let roles = hr.roles;
      this.selectedRoles = [];
      roles.forEach(r => {
        this.selectedRoles.push(r.id);
      })

      this.initAllRoles();
    },
    hidePop(hr) {
      let roles = [];
      Object.assign(roles, hr.roles);
      let flag = false;
      if (roles.length != this.selectedRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < roles.length; i++) {
          let role = roles[i];
          for (let j = 0; j < this.selectedRoles.length; j++) {
            if (role.id == this.selectedRoles[j].id) {
              //- 排除一个
              roles.splice(i, 1);
              //- roles删除了一个元素，后面的元素往前移动，索引应该不变
              i--;
              break;
            }
          }
        }
        if (roles.length != 0) {
          flag = true;
        }
      }
      //- 在后端进行修改
      if (flag) {
        let url = '/system/hr/role?hrId=' + hr.id;
        this.selectedRoles.forEach(rid => {
          url += '&rids=' + rid;
        });
        this.putRequest(url).then(data => {
          if (data) {
            this.initHrs();
          }
        })
      }
    },
    doSearch() {
      if (!this.keyword) {
        this.$message.warning('请输入要查找的用户名');
      }
      this.getRequest('/system/hr/' + this.keyword).then(data => {
        this.hrs = data;
      })
    },
    delHr(hr) {

      this.$confirm('此操作将永久删除【' + hr.name + '】角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/hr/' + hr.id).then(data => {
          if (data) {
            this.initHrs();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    }
  },
  mounted() {
    this.initHrs();
  },
  watch: {
    keyword(newVal) {
      if (!newVal) {
        this.initHrs();
      }
    }
  }
}
</script>

<style scoped>

.hrCard {
  width: 350px;
  margin-bottom: 20px;
}

.hrContainer {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.userImg {
  width: 72px;
  height: 72px;
  border-radius: 50%;
}

</style>