<template>
  <div class="app-container">
    <!--<el-card>
      <div slot="header" class="clearfix">
        <strong>查询</strong>
      </div>-->
    <el-form :model="queryForm" inline class="query-form" ref="queryForm">
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="queryForm.name"></el-input>
      </el-form-item>
      <!--<el-form-item label="部门状态" prop="status">
        <el-select v-model="queryForm.status">
          <el-option value="" label="所有"></el-option>
          <el-option :value="0" label="正常"></el-option>
          <el-option :value="1" label="停用"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item>
        <el-button type="success" class="el-icon-search" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- </el-card>
     <el-card style="margin-top: 10px">-->
    <!--<div slot="header" class="clearfix">
      <strong>菜单列表</strong>
    </div>-->
    <div style="padding-bottom: 10px">
      <el-button class="el-icon-refresh" @click="query">刷新</el-button>
      <el-button type="success" class="el-icon-plus" @click="openAdd">新增</el-button>
      <el-button type="primary" class="el-icon-arrow-down" @click="toggleRowExpansion(true)">展开</el-button>
      <el-button type="warning" class="el-icon-arrow-up" @click="toggleRowExpansion(false)">收起</el-button>
    </div>
    <!--<el-table stripe v-loading="loading" :data="tableData" max-height="450"
              :header-cell-style="{backgroundColor:'#eff3f9'}"
              row-key="id"
              default-expand-all
              :tree-props="{children: 'children', hasChildren: 'children'}">-->
    <el-table :data="tableData" v-loading="loading" ref="authorityTable" style="width: 100%" row-key="id"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column align="center" label="部门名称" prop="name"></el-table-column>
      <el-table-column align="center" label="排序" prop="orderNum"></el-table-column>
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status===1" type="danger">停用</el-tag>
          <el-tag v-else type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime"></el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="success" circle class="el-icon-circle-plus"
                     @click="openAdd(scope.row.id)"></el-button>
          <el-button type="warning" circle class="el-icon-edit" @click="openEdit(scope.row)"></el-button>
          <el-button type="danger" circle class="el-icon-delete" @click="deleteDept(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  </el-card>-->
    <el-drawer
      :visible.sync="dialogVisible"
      ref="drawer"
      size="35%"
      direction="rtl">
      <!--<el-dialog width="40%" :visible.sync="dialogVisible" :title="isAdd?'新增':'编辑'">-->
      <el-form size="medium" style="padding-right: 5%" v-loading="formLoading" ref="openForm" :model="openForm"
               label-width="120px">
        <el-form-item label="上级部门" prop="parentId">
          <el-cascader ref="parentMenu"
                       v-model="openForm.parentId"
                       :options="parentData"
                       :show-all-levels="false"
                       clearable
                       :props="treeProps"></el-cascader>
        </el-form-item>
        <el-form-item label="名称" prop="name"
                      :rules="[{ required: true, message: '请输入名称', trigger:  'blur' }]">
          <el-input v-model="openForm.name"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="manager" :rules="[{ required: true, message: '请输入负责人', trigger:  'blur' }]">
          <el-input v-model="openForm.manager"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone" :rules="[{ required: true, message: '请输入手机号码', trigger:  'blur' },
    {min:11,max:11,message:'请输入合法的手机号码',trigger: 'blur'}]">
          <el-input v-model="openForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="联系邮箱" prop="email" :rules="[
      { required: true, message: '请输入邮箱地址', trigger:  'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}]">
          <el-input v-model="openForm.email"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum" :rules="[{ type: 'number', message: '请输入数字', trigger:  'blur' }]">
          <el-input v-model.number="openForm.orderNum"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="openForm.status"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="submit('openForm')">提交</el-button>
          <el-button @click="resetForm('openForm')">取消</el-button>
        </el-form-item>

      </el-form>
      <!-- </el-dialog>-->
    </el-drawer>
  </div>
</template>

<script>
  import {deleteDeptById, edit, findById, insert, list} from "@/api/dept";
  import {setDisabled} from "@/utils/cascader";

  export default {
    name: "index",
    data() {
      return {
        queryForm: {},
        loading: false,
        isAdd: false,
        roleTreeLoading: false,
        dialogVisible: false,
        formLoading: false,
        tableData: [],
        parentData: [],
        openForm: {status: '', manager: '', orderNum: ''},
        treeProps: {
          children: 'children',
          label: 'name',
          value: 'id',
          id: 'id',
          expandTrigger: 'hover',
          checkStrictly: true
        }
      }
    },
    mounted() {
      this.loadTable(this.queryForm)
    },
    methods: {
      query() {
        this.queryForm.current = 1;
        this.loadTable(this.queryForm)
      },
      openEdit(row) {
        let _this = this;
        _this.isAdd = false;
        _this.dialogVisible = true;
        _this.formLoading = true;
        list().then(resp => {
          let data = setDisabled(resp.data.content, row);
          _this.parentData = data;
          findById(row.id).then(resp => {
            _this.openForm = resp.data.content;
            _this.formLoading = false;
          });
        })
      },
      toggleRowExpansion(isExpan) {
        if (isExpan) {
          if (this.tableData) {
            this.tableData.forEach(e => {
              this.$refs.authorityTable.toggleRowExpansion(e, true);
            })
          }
        } else {
          if (this.tableData) {
            this.tableData.forEach(e => {
              this.$refs.authorityTable.toggleRowExpansion(e, false);
            })
          }
        }
      },
      openAdd(parentId) {
        let _this = this;
        _this.isAdd = true;
        _this.dialogVisible = true;
        _this.openForm = {};
        if (parentId) {
          _this.openForm.parentId = parentId;
        }
        list().then(resp => {
          _this.parentData = resp.data.content;
        })
      },
      deleteDept(data) {
        let _this = this;
        _this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteDeptById(data).then(resp => {
            _this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success'
            });
            _this.loadTable(_this.queryParam)
          })
        });
      },
      submit(formName) {
        let _this = this;
        let parentId = _this.$refs.parentMenu.getCheckedNodes(true)[0];
        if (parentId) {
          _this.openForm.parentId = parentId.value;
        } else {
          _this.openForm.parentId = 0;
        }
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            if (_this.isAdd) {
              insert(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '新增成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryForm)
              })
            } else {
              edit(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '修改成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryForm)
              })
            }
          }
        });
      },
      loadTable(params) {
        let _this = this;
        _this.loading = true;
        list(params).then(resp => {
          let data = resp.data.content;
          _this.tableData = data;
          _this.loading = false;
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.dialogVisible = false;
      },
    }
  }
</script>

<style scoped>

</style>
