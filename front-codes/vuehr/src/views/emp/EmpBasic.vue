<template>
  <div>
    <!--上半部分-->
    <div>
      <div style="display:flex;justify-content: space-between">
        <div>
          <el-input
              size="small" v-model="keyword" placeholder="请输入员工名进行搜索..."
              prefix-icon="el-icon-search" style="width: 300px;margin-right: 10px;"
              @keydown.enter.native="initEmps" clearable @clear="initEmps" :disabled="showAdanceSearchView"
          ></el-input>
          <el-button type="primary" icon="el-icon-search" size="small" @click="initEmps"
                     :disabled="showAdanceSearchView">搜索
          </el-button>
          <el-button type="primary" size="small" @click="showAdanceSearchView=!showAdanceSearchView">
            <i :class="showAdanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'" aria-hidden="true"></i>
            高级搜索
          </el-button>
        </div>

        <div>
          <el-upload
              :show-file-list="false"
              style="display: inline-flex;margin-right: 8px;"
              action="/employee/basic/import"
              :before-upload="beforeUpload"
              :on-success="onSuccess"
              :on-error="onError"
              :disabled="importDataDisabled"
          >
            <el-button type="success" size="small" :icon="importDataIcon">
              {{importDataText}}
            </el-button>
          </el-upload>

          <el-button type="success" size="small" @click="exportData" icon="el-icon-upload2">
            导出数据
          </el-button>
          <el-button type="primary" size="small" icon="el-icon-plus" @click="showAddEmpView">
            添加用户
          </el-button>
        </div>
      </div>

      <transition name="slide-fade">
        <div
            style="border: 1px solid #50e0df;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0;font-size: 12px;"
            v-show="showAdanceSearchView">
          <el-row>
            <el-col :span="5">
              政治面貌：
              <el-select v-model="advanceSearchValue.politicId" placeholder="政治面貌" size="mini"
                         style="width: 130px;margin-left: 5px;">
                <el-option
                    v-for="item in politics"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              民族：
              <el-select v-model="advanceSearchValue.nationId" placeholder="民族" size="mini" style="width: 130px;">
                <el-option
                    v-for="item in nations"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              职位：
              <el-select v-model="advanceSearchValue.posId" placeholder="职位" size="mini" style="width: 130px;">
                <el-option
                    v-for="item in positions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              职称：
              <el-select v-model="advanceSearchValue.jobLevelId" placeholder="职称" size="mini" style="width: 130px;">
                <el-option
                    v-for="item in jobLevels"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="7" style="padding-top: 6px;">
              聘用形式：
              <el-radio-group v-model="advanceSearchValue.engageForm">
                <el-radio label="劳动合同">劳动合同</el-radio>
                <el-radio label="劳务合同">劳务合同</el-radio>
              </el-radio-group>
            </el-col>
          </el-row>

          <el-row style="margin-top: 10px;">
            <el-col :span="5">
              所属部门：
              <el-popover
                  placement="right"
                  title="请选择部门"
                  width="200"
                  trigger="manual"
                  v-model="popVisible">
                <el-tree :data="deps" default-expand-all :props="defaultProps"
                         @node-click="advanceSearchDep"></el-tree>

                <div slot="reference"
                     style="width: 130px;display: inline-flex;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 5px;align-items: center"
                     @click="showDepView">{{ inputDepName }}
                </div>
              </el-popover>
            </el-col>

            <el-col :span="10">
              入职日期：
              <el-date-picker
                  v-model="beginDateScope"
                  type="daterange"
                  size="mini"
                  unlink-panels
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-col>
            <el-col :offset="4" :span="5">
              <el-button size="mini" @click="showAdanceSearchView=!showAdanceSearchView">取消</el-button>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="advanceSearch">搜索</el-button>
            </el-col>
          </el-row>
        </div>
      </transition>
    </div>
    <!--下半部分-->
    <div style="margin-top: 10px;margin-bottom: 100px;">
      <el-table
          v-loading="loading"
          element-loading-text="正在加载..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          max-height="500"
          :data="emps"
          stripe
          border
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            fixed
            label="姓名"
            width="90">
        </el-table-column>
        <el-table-column
            prop="workID"
            label="工号"
            width="90">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="85">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            width="110">
        </el-table-column>
        <el-table-column
            prop="idCard"
            label="身份证号码"
            width="160">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况"
            width="85">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            width="50">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯"
            width="80">
        </el-table-column>
        <el-table-column
            prop="politicsstatus.name"
            label="政治面貌"
            width="80">
        </el-table-column>
        <el-table-column
            prop="email"
            label="电子邮件"
            width="150">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            width="110">
        </el-table-column>
        <el-table-column
            prop="address"
            label="联系地址"
            width="220">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="所属部门"
            width="80">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位"
            width="100">
        </el-table-column>
        <el-table-column
            prop="joblevel.name"
            label="职称"
            width="120">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            width="80">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            width="110">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            label="转正日期"
            width="110">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            width="110">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同截止日期"
            width="110">
        </el-table-column>
        <el-table-column
            label="合同期限"
            width="90">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.contractTerm }}</el-tag>
            年
          </template>
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="最高学历"
            width="80">
        </el-table-column>
        <el-table-column
            prop="specialty"
            label="专业"
            width="80">
        </el-table-column>
        <el-table-column
            prop="school"
            label="毕业院校"
            width="80">
        </el-table-column>

        <el-table-column label="操作" fixed="right" width="200">
          <template slot-scope="scope">
            <el-button style="padding: 3px;" size="mini" @click="showUpdateEmpView(scope.row)">编辑</el-button>
            <el-button style="padding: 3px;" size="mini" type="primary">查看高级资料</el-button>
            <el-button style="padding: 3px;" size="mini" type="danger" @click="delEmpById(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页处理-->
      <div style="display:flex;justify-content: flex-end">
        <el-pagination
            background
            layout="prev, pager, next,sizes,jumper,->,total,slot"
            :page-sizes="[10, 20, 30]"
            :page-size="10"
            @current-change="currentChange"
            @size-change="sizeChange"
            :total="total">
        </el-pagination>
      </div>
      <!--添加员工的对话框-->
      <div>
        <el-dialog
            v-loading="loading2"
            element-loading-text="正在加载..."
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :title="title"
            :visible.sync="dialogVisible"
            width="90%">
          <div>
            <el-form ref="empForm" :model="emp" :rules="rules">
              <!--第一行-->
              <el-row>
                <el-col :span="6">
                  <el-form-item label="姓名：" label-width="auto" prop="name">
                    <el-input v-model="emp.name" size="mini" prefix-icon="el-icon-edit" placeholder="请输入员工姓名"
                              style="width: 120px;"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="5">
                  <el-form-item label="性别：" label-width="auto" prop="gender">
                    <el-radio-group v-model="emp.gender">
                      <el-radio label="男">男</el-radio>
                      <el-radio label="女">女</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="出生日期：" label-width="auto" prop="birthday">
                    <el-date-picker
                        v-model="emp.birthday"
                        type="date"
                        size="mini"
                        style="width: 150px;"
                        value-format="yyyy-MM-dd"
                        placeholder="选择出生日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item label="政治面貌：" label-width="auto" prop="politicId">
                    <el-select v-model="emp.politicId" placeholder="政治面貌" size="mini" style="width: 200px;">
                      <el-option
                          v-for="item in politics"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--第二行-->
              <el-row>
                <el-col :span="6">
                  <el-form-item label="民族：" label-width="auto" prop="nationId">
                    <el-select v-model="emp.nationId" placeholder="民族" size="mini" style="width: 150px;">
                      <el-option
                          v-for="item in nations"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="5">
                  <el-form-item label="籍贯:" label-width="auto" prop="nativePlace">
                    <el-input size="mini" style="width: 120px" prefix-icon="el-icon-edit"
                              v-model="emp.nativePlace" placeholder="请输入籍贯"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电子邮箱:" label-width="auto" prop="email">
                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-message"
                              v-model="emp.email" placeholder="请输入电子邮箱"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="联系地址:" label-width="auto" prop="address">
                    <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                              v-model="emp.address" placeholder="请输入联系地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--第三行-->
              <el-row>
                <el-col :span="6">
                  <el-form-item label="职位：" label-width="auto" prop="posId">
                    <el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 150px;">
                      <el-option
                          v-for="item in positions"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="5">
                  <el-form-item label="职称：" label-width="auto" prop="jobLevelId">
                    <el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 150px;">
                      <el-option
                          v-for="item in jobLevels"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="所属部门:" label-width="auto" prop="departmentId">
                    <el-popover
                        placement="right"
                        title="请选择部门"
                        width="200"
                        trigger="manual"
                        v-model="popVisible">
                      <el-tree :data="deps" default-expand-all :props="defaultProps"
                               @node-click="handleNodeClick"></el-tree>

                      <div slot="reference"
                           style="width: 150px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                           @click="showDepView">{{ inputDepName }}
                      </div>
                    </el-popover>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item label="电话号码:" label-width="auto" prop="phone">
                    <el-input size="mini" style="width: 200px" prefix-icon="el-icon-phone"
                              v-model="emp.phone" placeholder="电话号码"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--第4行-->
              <el-row>
                <el-col :span="6">
                  <el-form-item label="工号:" label-width="auto" prop="workID">
                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                              v-model="emp.workID" placeholder="工号" disabled></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="5">
                  <el-form-item label="学历:" prop="tiptopDegree">
                    <el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini"
                               style="width: 150px;">
                      <el-option
                          v-for="item in tiptopDegrees"
                          :key="item"
                          :label="item"
                          :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="毕业院校:" label-width="auto" prop="school">
                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                              v-model="emp.school" placeholder="毕业院校名称"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item label="专业名称:" label-width="auto" prop="specialty">
                    <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                              v-model="emp.specialty" placeholder="请输入专业名称"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--第4行-->
              <el-row>
                <el-col :span="6">
                  <el-form-item label="入职日期:" label-width="auto" prop="beginDate">
                    <el-date-picker
                        v-model="emp.beginDate"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        style="width: 130px;"
                        placeholder="入职日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="转正日期:" label-width="auto" prop="conversionTime">
                    <el-date-picker
                        v-model="emp.conversionTime"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        style="width: 130px;"
                        placeholder="转正日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="合同起始日期:" label-width="auto" prop="beginContract">
                    <el-date-picker
                        v-model="emp.beginContract"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        style="width: 130px;"
                        placeholder="合同起始日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="合同终止日期:" label-width="auto" prop="endContract">
                    <el-date-picker
                        v-model="emp.endContract"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        style="width: 150px;"
                        placeholder="合同终止日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <!--第5行-->
              <el-row>
                <el-col :span="8">
                  <el-form-item label="身份证号码:" label-width="auto" prop="idCard">
                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                              v-model="emp.idCard" placeholder="请输入身份证号码"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="聘用形式:" label-width="auto" prop="engageForm">
                    <el-radio-group v-model="emp.engageForm">
                      <el-radio label="劳动合同">劳动合同</el-radio>
                      <el-radio label="劳务合同">劳务合同</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="婚姻状况:" label-width="auto" prop="wedlock">
                    <el-radio-group v-model="emp.wedlock">
                      <el-radio label="已婚">已婚</el-radio>
                      <el-radio label="未婚">未婚</el-radio>
                      <el-radio label="离异">离异</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>


            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addEmp">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      emps: [],
      total: 0,
      loading: false,
      loading2: false,
      pageNum: 1,
      pageSize: 10,
      keyword: '',
      emp: {
        name: "",
        gender: "",
        birthday: "",
        idCard: "610122199001011256",
        wedlock: "",
        nationId: null,
        nativePlace: "",
        politicId: null,
        email: "",
        phone: "",
        address: "",
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workState: "",
        workID: "",
        contractTerm: 2,
        conversionTime: "",
        notWorkDate: null,
        beginContract: "",
        endContract: "",
        workAge: null
      },
      dialogVisible: false,
      jobLevels: [],
      politics: [],
      nations: [],
      positions: [],
      tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
      popVisible: false,
      deps: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      inputDepName: '',
      rules: {
        name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
        birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
        idCard: [{
          required: true,
          message: '请输入身份证号码',
          trigger: 'blur'
        }, {
          pattern: '^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$',
          message: '身份证号码格式不正确',
          trigger: 'blur'
        }],
        wedlock: [{required: true, message: '请选择婚姻状况', trigger: 'blur'}],
        nationId: [{required: true, message: '请选择民族', trigger: 'blur'}],
        nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'}, {
          type: 'email',
          message: '邮箱格式不正确',
          trigger: 'blur'
        }],
        phone: [{required: true, message: '请输入手机号码', trigger: 'blur'}],
        address: [{required: true, message: '请输入联系地址', trigger: 'blur'}],
        departmentId: [{required: true, message: '请选择所属部门', trigger: 'blur'}],
        jobLevelId: [{required: true, message: '请选择职称', trigger: 'blur'}],
        posId: [{required: true, message: '请选择职位', trigger: 'blur'}],
        politicId: [{required: true, message: '请选择政治面貌', trigger: 'blur'}],
        engageForm: [{required: true, message: '请选择聘用形式', trigger: 'blur'}],
        tiptopDegree: [{required: true, message: '请选择学历', trigger: 'blur'}],
        specialty: [{required: true, message: '请输入专业名称', trigger: 'blur'}],
        school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
        beginDate: [{required: true, message: '请选择入职如期', trigger: 'blur'}],
        workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
        workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
        contractTerm: [{required: true, message: '请选择合同期限', trigger: 'blur'}],
        conversionTime: [{required: true, message: '请选择转正日期', trigger: 'blur'}],
        notWorkDate: [{required: true, message: '请选择出生日期', trigger: 'blur'}],
        beginContract: [{required: true, message: '请选择合同起始日期', trigger: 'blur'}],
        endContract: [{required: true, message: '请选择合同终止日期', trigger: 'blur'}],
        workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
      },
      //用title的值判断是添加操作还是修改操作
      title: '添加员工',
      showAdanceSearchView: false,
      advanceSearchValue: {
        politicId: null,
        nationId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        departmentId: null,
        beginContract: null,
        endContract: null
      },
      beginDateScope: null,
      importDataText:'导入数据',
      importDataIcon:'el-icon-download',
      importDataDisabled:false,


    };
  },
  methods: {
    initEmps() {
      this.loading = true;
      this.getRequest('/employee/basic/?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyword=' + this.keyword).then(data => {
        this.loading = false;
        if (data) {
          this.total = data.total;
          this.emps = data.data;
        }
      })
    },
    currentChange(currentPageNum) {
      this.pageNum = currentPageNum;
      this.initEmps();
    },
    sizeChange(currentPageSize) {
      this.pageSize = currentPageSize;
      this.initEmps();
    },
    showAddEmpView() {
      this.title = '添加员工';
      this.emp = {};
      this.loading2 = true;
      this.dialogVisible = true;
      this.loading2 = false;
      this.getMaxWorkID();

    },
    initData() {
      this.getRequest('/employee/basic/nations').then(data => {
        this.nations = data;
      })

      this.getRequest('/employee/basic/joblevels').then(data => {
        this.jobLevels = data;
      })

      this.getRequest('/employee/basic/politics').then(data => {
        this.politics = data;
      })

      this.getRequest('/employee/basic/positions').then(data => {
        this.positions = data;
      })
    },
    getMaxWorkID() {
      this.getRequest('/employee/basic/maxWorkID').then(data => {
        this.emp.workID = data.obj;
      })
    },
    showDepView() {
      this.popVisible = !this.popVisible
    },
    initDeps() {
      this.getRequest('system/basic/department/').then(data => {
        if (data) {
          this.deps = data;
        }
      });
    },
    handleNodeClick(data) {
      this.inputDepName = data.name;
      this.emp.departmentId = data.id;
      this.popVisible = !this.popVisible
    },
    addEmp() {
      this.$refs.empForm.validate(valid => {
        if (valid) {
          if (this.title === '添加员工') {
            this.postRequest('/employee/basic/', this.emp).then(data => {
              if (data) {
                this.dialogVisible = false;
                this.initEmps();
              }
            })
          } else {
            this.putRequest('/employee/basic/', this.emp).then(data => {
              if (data) {
                this.dialogVisible = false;
                this.initEmps();
              }
            })
          }
        }
      })
    },
    delEmpById(row) {
      this.$confirm('此操作将永久删除【' + row.name + '】员工, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/employee/basic/' + row.id).then(data => {
          if (data) {
            this.initEmps();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    showUpdateEmpView(row) {
      this.title = '编辑员工信息';
      this.emp = row;
      this.dialogVisible = true;
      this.initDeps();
    },
    exportData() {
      window.open('/employee/basic/export', '_parent');
    },
    advanceSearch() {
      //this.loading = true;
      console.log('1111');
      if (this.beginDateScope.length==2) {
        this.advanceSearchValue.beginContract = this.beginDateScope[0];
        this.advanceSearchValue.endContract = this.beginDateScope[1];
      }
      this.getRequest('/employee/basic/advance', this.advanceSearchValue).then(data => {
        if (data) {
          console.log(data);
          console.log('222')
          this.emps = data;
        }
      })
    },
    advanceSearchDep(data) {
      this.inputDepName = data.name;
      this.advanceSearchValue.departmentId = data.id;
      this.popVisible = !this.popVisible;
    },
    beforeUpload(){
      this.importDataText = '正在导入';
      this.importDataIcon = 'el-icon-loading';
      this.importDataDisabled = true;
    },
    onSuccess(resp,file,filelist){
      this.importDataText = '导入数据';
      this.importDataIcon = 'el-icon-download';
      this.importDataDisabled = false;
    },
    onError(resp,file,filelist){
      this.importDataText = '导入数据';
      this.importDataIcon = 'el-icon-download';
      this.importDataDisabled = false;

    },

  },
  mounted() {
    this.initEmps();
    this.initData();
    this.initDeps();
  },

}
</script>

<style>
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .8s ease;
}

.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */
{
  transform: translateX(10px);
  opacity: 0;
}
</style>