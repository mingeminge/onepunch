<template>
  <div class="app-container">
    <!--<el-card>
      <div slot="header" class="clearfix">
        <strong>查询</strong>
      </div>-->
    <!-- </el-card>
     <el-card style="margin-top: 10px">-->
    <el-row :gutter="10">
      <el-col :xs="24" :sm="4">
        <!-- <el-card>
           <div slot="header" class="clearfix">
             <strong>组织机构</strong>
           </div>-->
        <el-form :model="queryForm" @submit.native.prevent inline class="query-form" ref="deptQueryForm">
          <el-form-item prop="deptName">
            <el-input prefix-icon="el-icon-search" v-model="deptName" placeholder="按组织名称搜索"
                      @input="getDeptDatas"></el-input>
          </el-form-item>
        </el-form>
        <el-tree
          highlight-current
          check-on-click-node
          :expand-on-click-node="false"
          v-loading="roleTreeLoading"
          :props="treeProps"
          :data="deptList"
          @node-click="treeClick"
          node-key="id"
          default-expand-all
          ref="permissionTree">
        </el-tree>
        <!--  </el-card>-->
      </el-col>
      <el-col :sm="20" :xs="24">
        <el-form v-if="searchVisible" :model="queryForm" inline class="query-form" ref="queryForm">
          <el-form-item label="用户名" prop="account">
            <el-input v-model="queryForm.username"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="queryForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="queryForm.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="query">查询</el-button>
            <el-button type="warning" @click="resetForm('queryForm')">重置</el-button>
          </el-form-item>
        </el-form>
        <!--   <el-card>-->
        <!--<div slot="header" class="clearfix">-->
        <!--<el-button class="el-icon-refresh" @click="loadTable">刷新</el-button>-->
        <div style="display: flex">
          <div>
            <el-button type="success" class="el-icon-plus" @click="openAdd">新增</el-button>
            <el-button type="warning" :disabled="currentRow.length===0||currentRow.length>1" class="el-icon-edit"
                       @click="openEdit(currentRow[0])">修改
            </el-button>
            <el-button type="danger" :disabled="currentRow.length===0||currentRow.length>1" class="el-icon-delete"
                       @click="deleteUser(currentRow[0])">删除
            </el-button>
            <el-button type="primary" class="el-icon-download" @click="exportExcel">导出</el-button>
          </div>
          <div style="margin-left: auto">
            <el-button-group>
              <el-button class="el-icon-search" @click="searchVisible=!searchVisible"></el-button>
              <el-button class="el-icon-refresh" @click="query"></el-button>
            </el-button-group>
          </div>
        </div>
        <!--</div>-->
        <el-table @selection-change="tableSelectionChange" v-loading="loading" :data="tableData"
                  ref="userTable">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column align="center" prop="username" label="用户名"></el-table-column>
          <!--<el-table-column align="center" prop="realName" label="真实姓名"></el-table-column>-->
          <el-table-column width="70" align="center" prop="sex" label="性别">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.sex===1">男</el-tag>
              <el-tag type="danger" v-else-if="scope.row.sex===0">女</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" :show-overflow-tooltip="true" prop="phone" label="手机号码"></el-table-column>
          <el-table-column align="center" :show-overflow-tooltip="true" prop="email" label="邮箱"></el-table-column>
          <el-table-column width="80" align="center" prop="status" label="状态">
            <template slot-scope="scope">
              <el-switch
                @change="updateStatus(scope.row)"
                v-model="scope.row.status"
                :active-value="0"
                :inactive-value="1"
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" :show-overflow-tooltip="true" prop="createTime"
                           label="创建日期"></el-table-column>
          <!--<el-table-column align="center" :show-overflow-tooltip="true" prop="updateTime"
                           label="修改日期"></el-table-column>-->
          <el-table-column align="center" label="操作" fixed="right">
            <template slot-scope="scope">
              <el-button type="success" class="el-icon-search" @click="openCheck(scope.row)" circle></el-button>
              <el-button type="primary" @click="openEdit(scope.row)"
                         class="el-icon-edit" circle></el-button>
              <el-button type="danger" @click="deleteUser(scope.row)"
                         class="el-icon-delete" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
          :current-page="queryForm.current"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
        <!--  </el-card>-->
      </el-col>
    </el-row>
    <!-- </el-card>-->
    <el-drawer
      :visible.sync="dialogVisible"
      size="40%"
      ref="drawer"
      direction="rtl">
      <!-- <el-dialog width="40%" :visible.sync="dialogVisible" :title="title" @closed="resetForm('openForm')">-->
      <el-form size="medium" style=";padding-right: 5%" v-loading="formLoading" ref="openForm"
               :model="openForm"
               label-width="120px">
        <el-form-item label="用户名" prop="username"
                      :rules="[{ required: true, message: '请输入用户名', trigger:  'blur' }]">
          <el-input :disabled="!isAdd||isCheck" v-model="openForm.username"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName"
                      :rules="[{ required: true, message: '请输入真实姓名', trigger:  'blur' }]">
          <el-input :disabled="isCheck" v-model="openForm.realName"></el-input>
        </el-form-item>
        <el-form-item v-if="isAdd" label="密码" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' },
    { min:6,max:12,message:'密码长度5-12位',trigger: 'blur' }]">
          <el-input type="password" v-model="openForm.password"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" :rules="[{ required: true, message: '请输入手机号码', trigger:  'blur' },
    {min:11,max:11,message:'请输入合法的手机号码',trigger: 'blur'}]">
          <el-input :disabled="isCheck" v-model="openForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" :rules="[
      { required: true, message: '请输入邮箱地址', trigger:  'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}]">
          <el-input :disabled="isCheck" v-model="openForm.email"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" :rules="[
      { required: true, message: '请选择性别', trigger:  'blur' }]">
          <el-radio-group :disabled="isCheck" v-model="openForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="!isAdd" label="状态" prop="status">
          <el-switch
            :disabled="isCheck"
            v-model="openForm.status"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select
            :disabled="isCheck"
            v-model="openForm.roles"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择角色">
            <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.description"
              :value="item.id">
            </el-option>
          </el-select>
          <!--<el-checkbox-group v-model="openForm.roles">
            <el-checkbox v-for="item in roles" :label="item.id" :key="item.id">{{item.description}}
            </el-checkbox>
          </el-checkbox-group>-->
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime" v-if="isCheck">
          <el-input disabled v-model="openForm.createTime"></el-input>
        </el-form-item>
        <el-form-item label="上次修改时间" prop="createTime" v-if="isCheck">
          <el-input disabled v-model="openForm.updateTime"></el-input>
        </el-form-item>
        <el-form-item label="组织机构">
          <!--<el-tree
            highlight-current
            check-on-click-node
            v-loading="deptTreeLoading"
            :props="treeProps"
            :data="deptList"
            node-key="id"
            :default-expanded-keys="deptDefaultKeys"
            show-checkbox
            check-strictly
            ref="deptTree">
          </el-tree>-->
          <el-cascader ref="deptSelect"
                       v-model="openForm.deptId"
                       :options="deptList"
                       :show-all-levels="false"
                       clearable
                       :props="treeProps"></el-cascader>
        </el-form-item>
        <el-form-item v-if="!isCheck">
          <el-button type="primary" @click="submit('openForm')">提交</el-button>
          <el-button @click="dialogVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
      <!--  </el-dialog>-->
    </el-drawer>
  </div>
</template>

<script>
  import {deleteUser, deptList, edit, findById, insert, list, roleList} from "@/api/user";

  import {exportFile} from "@/utils/download";

  export default {
    name: "index",
    computed: {
      queryParam() {
        return {
          'current': this.queryForm.current,
          'size': this.queryForm.size,
          'username': this.queryForm.username,
          'phone': this.queryForm.phone,
          'email': this.queryForm.email
        }
      }
    },
    data() {
      return {
        isAdd: false,
        isCheck: false,
        loading: false,
        formLoading: false,
        roleTreeLoading: false,
        searchVisible: true,
        tableData: [],
        total: 0,
        hide: true,
        queryForm: {
          current: 1, size: 10, username: '', phone: '', email: ''
        },
        dialogVisible: false,
        openForm: {},
        roles: [],
        deptList: [],
        title: '',
        treeProps: {
          children: 'children',
          label: 'name',
          id: 'id',
          disabled: 'status',

          value: 'id',
          expandTrigger: 'hover',
          checkStrictly: true
        },
        currentRow: {},
        deptName: '',
      }
    },
    mounted() {
      this.loadTable(this.queryParam);
      this.loadDeptTree();
    },
    methods: {
      getDeptDatas() {
        this.loadDeptTree({name: this.deptName})
      },
      exportExcel() {
        let data = this.queryForm;
        let params = '?username=' + data.username + '&phone=' + data.phone + '&email=' + data.email;
        let url = process.env.BASE_API + '/user/export';
        exportFile(url, params, "用户数据.xlsx");
      },
      tableSelectionChange(val) {
        //this.$refs.userTable.clearSelection();
        this.currentRow = val;
        console.log(this.currentRow)
      },
      loadDeptTree(params) {
        let _this = this;
        _this.roleTreeLoading = true;
        deptList(params).then(resp => {
          _this.deptList = resp.data.content;
          _this.roleTreeLoading = false;
        })
      },
      treeClick(data) {
        this.queryForm.deptId = data.id;
        this.loadTable(this.queryForm);
      },
      loadTable(params) {
        let _this = this;
        _this.loading = true;
        list(params).then(resp => {
          _this.tableData = resp.data;
          _this.loading = false;
          _this.total = resp.count;
        }).catch(() => {
          _this.loading = false;
        })
      },
      query() {
        this.queryForm.current = 1;
        this.loadTable(this.queryParam);
      },
      openEdit(data) {
        let _this = this;
        this.title = '编辑';
        _this.isAdd = false;
        _this.isCheck = false;
        _this.dialogVisible = true;
        _this.formLoading = true;
        roleList().then(resp => {
          _this.roles = resp.data;
          findById(data.id).then(resp => {
            _this.openForm = resp.data;
            _this.formLoading = false;
            /*_this.$refs.deptTree.setCheckedKeys([resp.data.deptId]);
            _this.deptDefaultKeys = [resp.data.deptId]*/
          }).catch(e => {
            _this.dialogVisible = false;
            _this.formLoading = false;
          })
        });

      },
      updateStatus(row) {
        let _this = this;
        edit(row).then(resp => {
          _this.dialogVisible = false;
          _this.$notify({
            title: '成功',
            message: '修改成功',
            type: 'success'
          });
        })
      },
      submit(formName) {
        let _this = this;
        let deptId = _this.$refs.deptSelect.getCheckedNodes(true)[0];
        if (deptId) {
          _this.openForm.deptId = deptId.value;
        }
        /*else {
                 _this.openForm.deptId = 0;
               }*/
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            if (!_this.isAdd) {
              edit(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '修改成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryParam)
              })
            } else {
              insert(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '新增用户成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryParam)
              })
            }
          }
        });
      },
      deleteUser(data) {
        let _this = this;
        _this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUser(data).then(resp => {
            _this.$notify({
              title: '成功',
              message: '删除用户成功',
              type: 'success'
            });
            _this.loadTable(_this.queryParam)
          })
        });
      },
      resetForm(formName) {
        this.openForm = {};
        if (this.$refs[formName]) {
          this.$refs[formName].resetFields();
        }
        this.dialogVisible = false;
      },
      handleSizeChange(val) {
        this.queryForm.size = val;
        this.loadTable(this.queryParam)
      },
      handleCurrentChange(val) {
        this.queryForm.current = val;
        this.loadTable(this.queryParam)
      },
      openCheck(row) {
        this.title = '查看';
        this.openForm = row;
        this.dialogVisible = true;
        this.isCheck = true;
      },
      openAdd() {
        this.title = '新增';
        this.dialogVisible = true;
        this.isAdd = true;
        this.isCheck = false;
        this.openForm = {roles: []};
        let _this = this;
        _this.formLoading = true;
        roleList().then(resp => {
          _this.roles = resp.data;
          _this.formLoading = false;
          /*  _this.$refs.deptTree.setCheckedKeys(['']);
            _this.deptDefaultKeys = [''];*/
        });
      },
    }
  }
</script>

<style scoped>

</style>
