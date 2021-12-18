<template>
  <div>
    <!--上半部分-->
    <div>
      <div style="display:flex;justify-content: space-between">
        <div>
          <el-input v-model="keyword" placeholder="请输入员工名进行搜索.." prefix-icon="el-icon-search" size="small"
                    :disabled="showAdvanceView"
                    style="width: 300px;margin-right: 8px;" @keydown.enter.native="searchEmpByName"></el-input>
          <el-button type="primary" icon="el-icon-search" size="small" @click="searchEmpByName"
                     :disabled="showAdvanceView">搜索
          </el-button>
          <el-button type="primary" :icon="showAdvanceView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                     @click="showAdvanceView=!showAdvanceView"
                     size="small">
            高级搜索
          </el-button>
        </div>

        <div>
          <el-upload
              style="display:inline-block;margin-right: 10px;"
              :show-file-list="false"
              :before-upload="handlerBeforeUpload"
              :on-success="handlerUploadSuccess"
              :on-error="handlerUploadError"
              :disabled="importEmpDisabled"
              action="/employee/basic/import"
          >
            <el-button type="success" :icon="importEmpIcon" size="small">{{ importEmpText }}</el-button>
          </el-upload>
          <el-button type="success" icon="el-icon-upload2" size="small" @click="exportEmps">导出数据</el-button>
          <el-button type="primary" icon="el-icon-plus" size="small" @click="showDialog">添加用户</el-button>
        </div>
      </div>

      <transition name="slide-fade">
        <!--高级搜索-->
        <div v-show="showAdvanceView"
             style="border: 1px solid #359ae0;border-radius: 5px;box-sizing: border-box;padding: 5px;margin-top: 5px;">
          <el-row>
            <el-col :span="5">
              政治面貌：
              <el-select v-model="advancedSearchEmp.politicId" placeholder="请选择政治面貌" size="mini" style="width: 130px;">
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
              <el-select v-model="advancedSearchEmp.nationId" placeholder="请选择民族" size="mini" style="width: 120px;">
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
              <el-select v-model="advancedSearchEmp.posId" placeholder="请选择职位" size="mini" style="width: 120px;">
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
              <el-select v-model="advancedSearchEmp.jobLevelId" placeholder="请选择职称" size="mini" style="width: 120px;">
                <el-option
                    v-for="item in jobs"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
              </el-select>
            </el-col>

            <el-col :span="7">
              聘用形式：
              <el-radio-group v-model="advancedSearchEmp.engageForm" size="mini">
                <el-radio label="劳务合同">劳务合同</el-radio>
                <el-radio label="劳动合同">劳动合同</el-radio>
              </el-radio-group>
            </el-col>
          </el-row>

          <el-row style="margin-top: 10px;">
            <el-col :span="6">
              所属部门：
              <el-popover
                  placement="right"
                  title="请选择部门"
                  width="200"
                  v-model="poverVisible"
                  trigger="manual"><!--这里是手动激活，需要绑定v-model-->
                <el-tree :data="deps" default-expand-all :props="defaultProps"
                         @node-click="handleNodeClickAdvanced"></el-tree>

                <div slot="reference" class="depTree"
                     @click="showDepView">
                  {{ selectedDepName }}
                </div>
              </el-popover>
            </el-col>
            <el-col :span="10">
              入职日期：
              <el-date-picker
                  v-model="advancedSearchEmp.beginDateScope"
                  type="daterange"
                  size="mini"
                  unlink-panels
                  format="yyyy年MM月dd日"
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-col>
            <el-col :offset="4" :span="4">
              <el-button size="mini" @click="showAdvanceView=!showAdvanceView">取消</el-button>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="initEmps('advanced')">搜索</el-button>
            </el-col>
          </el-row>
        </div>
      </transition>


    </div>

    <!--下半部分-->
    <div style="margin-top: 10px;">
      <el-table
          v-loading="loading"
          element-loading-text="正在加载..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          max-height="500"
          :data="emps"
          border
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            fixed
            prop="name"
            label="姓名"
            width="100"
        >
        </el-table-column>
        <el-table-column
            width="50"
            prop="gender"
            label="性别">
        </el-table-column>
        <el-table-column
            width="110"
            prop="birthday"
            label="出生日期">
        </el-table-column>
        <el-table-column
            width="170"
            prop="idCard"
            label="身份证号码">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯">
        </el-table-column>
        <el-table-column
            prop="politicsstatus.name"
            label="政治面貌">
        </el-table-column>
        <el-table-column
            width="120"
            prop="phone"
            label="电话号码">
        </el-table-column>
        <el-table-column
            width="200"
            prop="address"
            label="联系地址">
        </el-table-column>
        <el-table-column
            width="150"
            prop="department.name"
            label="所属部门">
        </el-table-column>
        <el-table-column
            width="120"
            prop="position.name"
            label="职位">
        </el-table-column>
        <el-table-column
            width="120"
            prop="joblevel.name"
            label="职称">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式">
        </el-table-column>
        <el-table-column
            label="工龄">
          <template slot-scope="scope">
            <el-tag type="success">{{ scope.row.workAge }}</el-tag>
            年
          </template>
        </el-table-column>
        <el-table-column
            width="120"
            prop="beginDate"
            label="入职日期">
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
            width="90"
        >
          <template slot-scope="scope">
            <el-tag type="success">{{ scope.row.contractTerm }}</el-tag>
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
            width="150">
        </el-table-column>
        <el-table-column
            prop="school"
            label="毕业院校"
            width="150">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="220">
          <template slot-scope="scope">
            <el-button size="mini" style="padding: 3px;" @click="updateEmp(scope.row)">编辑</el-button>
            <el-button type="primary" size="mini" style="padding: 3px;">查看高级资料</el-button>
            <el-button type="danger" size="mini" style="padding: 3px;" @click="delEmp(scope.row)">删除员工</el-button>
          </template>
        </el-table-column>
      </el-table>


      <!--添加员工和修改员工的对话框-->
      <el-dialog
          :title="title"
          width="90%"
          :visible.sync="dialogVisible">

        <div>
          <el-form ref="empForm" :model="emp" :rules="rules">
            <el-row>
              <el-col :span="6">
                <el-form-item label="姓名：" prop="name">
                  <el-input size="mini" v-model="emp.name" placeholder="请输入员工姓名" prefix-icon="el-icon-edit"
                            style="width: 135px;" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="5">
                <el-form-item label="性别：" prop="gender">
                  <el-radio-group v-model="emp.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="出生日期：" prop="birthday">
                  <el-date-picker
                      v-model="emp.birthday"
                      type="date"
                      size="mini"
                      style="width: 150px;"
                      placeholder="选择出生日期"
                      format="yyyy年MM月dd日"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="政治面貌：" prop="politicId">
                  <el-select v-model="emp.politicId" placeholder="请选择" size="mini">
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

            <el-row>
              <el-col :span="6">
                <el-form-item label="民族：" prop="nationId">
                  <el-select v-model="emp.nationId" placeholder="请选择民族" size="mini" style="width: 120px;">
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
                <el-form-item label="籍贯：" prop="nativePlace">
                  <el-input v-model="emp.nativePlace" placeholder="请输入籍贯" size="mini" style="width: 120px;"
                            prefix-icon="el-icon-edit"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="电子邮箱：" prop="email">
                  <el-input v-model="emp.email" placeholder="请输入邮箱" prefix-icon="el-icon-message" size="mini"
                            style="width: 150px;"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="联系地址：" prop="address">
                  <el-input v-model="emp.address" placeholder="请输入联系地址" prefix-icon="el-icon-s-home" size="mini"
                            style="width: 150px;"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6">
                <el-form-item label="职位：" prop="posId">
                  <el-select v-model="emp.posId" placeholder="请选择职位" size="mini" style="width: 120px;">
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
                <el-form-item label="职称：" prop="jobLevelId">
                  <el-select v-model="emp.jobLevelId" placeholder="请选择职称" size="mini" style="width: 120px;">
                    <el-option
                        v-for="item in jobs"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="所属部门：" prop="departmentId">
                  <el-popover
                      placement="right"
                      title="请选择部门"
                      width="200"
                      v-model="poverVisible"
                      trigger="manual"><!--这里是手动激活，需要绑定v-model-->
                    <el-tree :data="deps" default-expand-all :props="defaultProps"
                             @node-click="handleNodeClick"></el-tree>

                    <div slot="reference" class="depTree"
                         @click="showDepView">
                      {{ selectedDepName }}
                    </div>
                  </el-popover>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="电话号码：" prop="phone">
                  <el-input v-model="emp.phone" placeholder="请输入电话号码" prefix-icon="el-icon-phone" size="mini"
                            style="width: 150px;"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6">
                <el-form-item label="工号：" prop="workID">
                  <el-input v-model="emp.workID" size="mini" prefix-icon="el-icon-loading" disabled
                            style="width: 120px;"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="5">
                <el-form-item label="学历：" size="mini" prop="tiptopDegree">
                  <el-select v-model="emp.tiptopDegree" placeholder="请选择学历" style="width: 120px;">
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
                <el-form-item label="毕业院校：" prop="school">
                  <el-input v-model="emp.school" placeholder="请输入毕业院校名称" style="width: 130px;"
                            prefix-icon="el-icon-edit" size="mini"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="专业名称：" prop="specialty">
                  <el-input v-model="emp.specialty" placeholder="请输入专业名称" style="width: 130px;"
                            prefix-icon="el-icon-edit" size="mini"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6">
                <el-form-item label="入职日期：" prop="beginDate">
                  <el-date-picker
                      v-model="emp.beginDate"
                      type="date"
                      size="mini"
                      style="width: 120px;"
                      placeholder="选择入职日期"
                      format="yyyy年MM月dd日"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="转正日期：" prop="conversionTime">
                  <el-date-picker
                      v-model="emp.conversionTime"
                      type="date"
                      size="mini"
                      style="width: 120px;"
                      placeholder="选择转正日期"
                      format="yyyy年MM月dd日"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="合同起始日期：" prop="beginContract">
                  <el-date-picker
                      v-model="emp.beginContract"
                      type="date"
                      size="mini"
                      style="width: 120px;"
                      placeholder="选择合同起始日期"
                      format="yyyy年MM月dd日"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="合同终止日期：" prop="endContract">
                  <el-date-picker
                      v-model="emp.endContract"
                      type="date"
                      size="mini"
                      style="width: 125px;"
                      placeholder="选择合同终止日期"
                      format="yyyy年MM月dd日"
                      value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="身份证号码：" prop="idCard">
                  <el-input v-model="emp.idCard" placeholder="请输入身份证号码" prefix-icon="el-icon-edit" size="mini"
                            style="width: 180px;"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="7">
                <el-form-item label="聘用形式：" prop="engageForm">
                  <el-radio-group v-model="emp.engageForm" size="mini">
                    <el-radio label="劳动合同">劳动合同</el-radio>
                    <el-radio label="劳务合同">劳务合同</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col :span="6" :offset="3">
                <el-form-item label="婚姻状况：" prop="wedlock">
                  <el-radio-group v-model="emp.wedlock">
                    <el-radio label="已婚">已婚</el-radio>
                    <el-radio label="未婚">未婚</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
    <el-button type="primary" @click="addEmp" size="mini">确 定</el-button>
  </span>
      </el-dialog>

      <div style="margin-top: 10px;">
        <el-pagination
            background
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="10"
            layout="prev, pager, next, jumper,sizes,total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      keyword: '',
      emps: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      loading: false,
      dialogVisible: false,
      title: '添加员工',
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
      politics: [],
      nations: [],
      jobs: [],
      positions: [],
      tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
      maxWorkId: -1,
      deps: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      poverVisible: false,
      selectedDepName: '',
      rules: {
        name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
        birthday: [{required: true, message: '请选择出生日期', trigger: 'blur'}],
        politicId: [{required: true, message: '请选择政治面貌', trigger: 'blur'}],
        nationId: [{required: true, message: '请选择民族', trigger: 'blur'}],
        nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '邮箱格式不正确', trigger: 'blur'}
        ],
        address: [{required: true, message: '请输入联系地址', trigger: 'blur'}],
        posId: [{required: true, message: '请选择职位', trigger: 'blur'}],
        jobLevelId: [{required: true, message: '请选择职称', trigger: 'blur'}],
        departmentId: [{required: true, message: '请选择部门', trigger: 'blur'}],
        phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
        tiptopDegree: [{required: true, message: '请选择学历', trigger: 'blur'}],
        school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
        specialty: [{required: true, message: '请输入专业名称', trigger: 'blur'}],
        beginDate: [{required: true, message: '请选择入职日期', trigger: 'blur'}],
        conversionTime: [{required: true, message: '请选择转正日期', trigger: 'blur'}],
        beginContract: [{required: true, message: '请选择合同起始日期', trigger: 'blur'}],
        endContract: [{required: true, message: '请选择合同结束日期', trigger: 'blur'}],
        idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}],
        engageForm: [{required: true, message: '请选择聘用形式', trigger: 'blur'}],
        wedlock: [{required: true, message: '请选择婚姻状况', trigger: 'blur'}]
      },
      importEmpText: '导入数据',
      importEmpIcon: 'el-icon-download',
      importEmpDisabled: false,//- 正在上传的时候再点击不能让用户继续上传
      showAdvanceView: false,

      //- 高级搜索的条件
      advancedSearchEmp: {
        politicId: null,
        nationId: null,
        jobLevelId: null,
        posId: null,
        engageForm:null,
        departmentId: null,
        beginDateScope: null
      },


    };
  },
  methods: {
    initEmps(type) {
      this.loading = true;
      var url = '/employee/basic/?'+'?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyword=' + this.keyword;;
      if (type && type === 'advanced') {

        url = '/employee/basic/advanced?';
        /*
        * - 若这里不做判断，传到后端封装成Employee对象的时候会出现异常
        * - 比如高级搜索中没有选择politicId,则politicId=null,后端会把他当成字符串，在封装给Employee对象的时候会出现数字转换异常:NumberFormatException,相当于Integer politicId = "null"
        * - 故如果前端没有选择字符串则不添加到url中，后端会接受不到，从而politicId等于null，相当于Integer politicId = null
        * */
        if (this.advancedSearchEmp.politicId!= null){
          url += 'politicId='+this.advancedSearchEmp.politicId+'&';
        }
        if (this.advancedSearchEmp.jobLevelId!= null){
          url += 'jobLevelId='+this.advancedSearchEmp.jobLevelId+'&';
        }
        if (this.advancedSearchEmp.posId!= null){
          url += 'posId='+this.advancedSearchEmp.posId+'&';
        }
        if (this.advancedSearchEmp.engageForm!= null){
          url += 'engageForm='+this.advancedSearchEmp.engageForm+'&';
        }
        if (this.advancedSearchEmp.departmentId!= null){
          url += 'departmentId='+this.advancedSearchEmp.departmentId+'&';
        }
        if (this.advancedSearchEmp.beginDateScope!= null){
          url += 'beginDateScope='+this.advancedSearchEmp.beginDateScope+'&';
        }


        this.getRequest2(url).then(data => {
          if (data) {
            this.loading = false;
            this.total = data.total;
            this.emps = data.data;
          }
        })
      } else {
        this.getRequest2(url).then(data => {
          if (data) {
            this.loading = false;
            this.total = data.total;
            this.emps = data.data;
          }
        })
      }

    },
    handleSizeChange(pageSize) {
      //console.log('pageSize',pageSize);
      this.loading = true;
      this.pageSize = pageSize;
      this.getRequest2('/employee/basic/?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyword=' + this.keyword).then(data => {
        if (data) {
          this.loading = false;
          console.log(data)
          this.emps = data.data;
        }
      })
    },
    handleCurrentChange(pageNum) {
      //console.log('pageNum',pageNum);
      this.loading = true;
      this.pageNum = pageNum;
      this.getRequest2('/employee/basic/?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyword=' + this.keyword).then(data => {
        if (data) {
          this.loading = false;
          console.log(data)
          this.emps = data.data;
        }
      })
    },
    updateEmp(row) {
      //console.log(row);
      this.title = '修改员工信息';
      this.emp = row;
      console.log(this.emp);
      this.dialogVisible = true;
    },
    delEmp(row) {
      //console.log(row);

      this.$confirm('此操作将永久删除【' + row.name + '】用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest2('/employee/basic/' + row.id).then(data => {
          if (data) {
            this.initEmps();
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    searchEmpByName() {
      if (!this.keyword) {
        this.$message.warning('请先输入员工名再进行搜索..');
      }
      this.getRequest2('/employee/basic/?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyword=' + this.keyword).then(data => {
        if (data) {
          this.total = data.total;
          this.emps = data.data;
        }
      })
    },
    getWorkID() {
      this.getRequest2('/employee/basic/maxWorkId').then(data => {
        if (data) {
          this.emp.workID = data.obj;
        }
      });
    },
    showDialog() {
      this.title = '添加员工';
      this.emp = {workID: -1};
      this.getWorkID();
      this.dialogVisible = true;
    },
    initInfo() {
      this.getRequest2('/employee/basic/nation').then(data => {
        if (data) {
          this.nations = data;
        }
      });
      this.getRequest2('/employee/basic/politic').then(data => {
        if (data) {
          this.politics = data;
        }
      });
      this.getRequest2('/employee/basic/joblevel').then(data => {
        if (data) {
          this.jobs = data;
        }
      });
      this.getRequest2('/employee/basic/position').then(data => {
        if (data) {
          this.positions = data;
        }
      });
    },

    handleNodeClick(data) {
      this.selectedDepName = data.name;
      this.emp.departmentId = data.id;
      this.poverVisible = !this.poverVisible;
    },
    initDeps() {
      this.getRequest2('/system/basic/dep/').then(data => {
        if (data) {
          this.deps = data;
        }
      })
    },
    showDepView() {
      this.poverVisible = !this.poverVisible;
    },
    addEmp() {

      //- 表单验证
      this.$refs.empForm.validate(valid => {
        if (valid) {
          if (this.title === '添加员工') {
            this.postRequest2('/employee/basic/', this.emp).then(data => {
              if (data) {
                this.dialogVisible = false;
                this.initEmps();
              }
            });
          } else {//- 修改员工
            this.putRequest2('/employee/basic/', this.emp).then(data => {
              if (data) {
                this.dialogVisible = false;
                this.initEmps();
              }
            });
          }
        }
      });
    },
    exportEmps() {
      window.open('/employee/basic/export', '_parent');
    },
    handlerBeforeUpload() {
      this.importEmpText = '正在导入';
      this.importEmpIcon = 'el-icon-loading';
      this.importEmpDisabled = true;
    },
    handlerUploadSuccess() {
      this.importEmpText = '导入数据';
      this.importEmpIcon = 'el-icon-download';
      this.importEmpDisabled = false;
      this.initEmps();
    },
    handlerUploadError() {
      this.importEmpText = '导入数据';
      this.importEmpIcon = 'el-icon-download';
      this.importEmpDisabled = false;
    },
    handleNodeClickAdvanced(data) {
      this.selectedDepName = data.name;
      this.advancedSearchEmp.departmentId = data.id;
      this.poverVisible = !this.poverVisible;
    }

  },
  mounted() {
    this.initEmps();
    this.initInfo();
    this.initDeps();

  },
  watch: {
    keyword(newVal) {
      if (!newVal) {
        this.initEmps();
      }
    }
  }
}
</script>

<style scoped>
.depTree {
  display: inline-block;
  height: 26px;
  border: 1px solid #dedede;
  border-radius: 5px;
  padding-left: 8px;
  align-items: center;
  cursor: pointer;
  width: 130px;
}

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