<template>
  <div class="app-container">
    <!--<el-card>
      <div slot="header" class="clearfix">
        <strong>查询</strong>
      </div>-->
    <el-form :model="queryForm" inline class="query-form" ref="queryForm">
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="queryForm.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" class="el-icon-search" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
    <!--</el-card>
    <el-card style="margin-top: 10px">-->
    <!--<div slot="header" class="clearfix">
      <strong>菜单列表</strong>
    </div>-->
    <div style="padding-bottom: 10px">
      <el-button class="el-icon-refresh" @click="query">刷新</el-button>
      <el-button type="success" class="el-icon-plus" @click="openAdd">新增</el-button>
      <el-button type="primary" class="el-icon-arrow-down" @click="toggleRowExpansion(true)">展开</el-button>
      <el-button type="warning" class="el-icon-arrow-up" @click="toggleRowExpansion(false)">收起</el-button>
      <!--<el-button type="warning" class="el-icon-upload">导入</el-button>
      <el-button type="primary" class="el-icon-download">导出</el-button>-->
    </div>
    <!--<el-table stripe v-loading="loading" :data="tableData" max-height="450"
              :header-cell-style="{backgroundColor:'#eff3f9'}"
              row-key="id"
              default-expand-all
              :tree-props="{children: 'children', hasChildren: 'children'}">-->
    <el-table :data="tableData" v-loading="loading" ref="menuTable" row-key="id"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column align="center" label="菜单名称" prop="name"></el-table-column>
      <el-table-column align="center" label="图标" prop="icon" width="80">
        <template slot-scope="scope">
          <svg-icon v-if="scope.row.icon" :icon-class="scope.row.icon"/>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip align="center" label="请求地址" prop="path"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" label="组件" prop="component"></el-table-column>
      <el-table-column align="center" label="类型" prop="type" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type===0" type="info">目录</el-tag>
          <el-tag v-if="scope.row.type===1" type="primary">菜单</el-tag>
          <el-tag v-if="scope.row.type===2" type="danger">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status===1" type="danger">禁用</el-tag>
          <el-tag v-else type="success">正常</el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column align="center" label="外链" prop="iFrame" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.iFrame" type="danger">是</el-tag>
          <el-tag v-else type="success">否</el-tag>
        </template>
      </el-table-column>-->
      <el-table-column align="center" label="可见" prop="hidden" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hidden" type="danger">否</el-tag>
          <el-tag v-else type="success">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="权限标识" prop="permission"></el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="success" circle class="el-icon-circle-plus" @click="openAdd(scope.row)"></el-button>
          <el-button type="warning" circle class="el-icon-edit" @click="openEdit(scope.row)"></el-button>
          <el-button type="danger" circle class="el-icon-delete" @click="deleteMenu(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- </el-card>-->
    <el-drawer
      size="40%"
      :visible.sync="dialogVisible"
      ref="drawer"
      direction="rtl">
      <!--<el-dialog width="40%" :visible.sync="dialogVisible" :title="isAdd?'新增':'编辑'">-->
      <el-form size="small" style=";padding-right: 5%" v-loading="formLoading" ref="openForm" :model="openForm"
               label-width="120px">
        <el-form-item label="上级菜单" prop="pid">
          <!--<el-tree ref="parentTree" :data="parentData" :props="treeProps" @node-click="treeClick"></el-tree>-->
          <el-cascader ref="parentMenu"
                       v-model="openForm.pid"
                       :options="parentData"
                       :show-all-levels="false"
                       clearable
                       :props="treeProps"></el-cascader>
        </el-form-item>
        <el-form-item label="类型" prop="type" :rules="[{ required: true, message: '请选择类型', trigger:  'blur' }]">
          <el-select v-model="openForm.type" placeholder="请选择">
            <el-option :value="0" label="目录"></el-option>
            <el-option :value="1" label="菜单"></el-option>
            <el-option :value="2" label="按钮"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name"
                      :rules="[{ required: true, message: '请输入名称', trigger:  'blur' }]">
          <el-input v-model="openForm.name"></el-input>
        </el-form-item>
        <el-form-item v-if="openForm.type===1" label="组件路径" prop="component"
                      :rules="[{ required: true, message: '请输入组件路径', trigger:  'blur' }]">
          <el-input v-model="openForm.component"></el-input>
        </el-form-item>
        <el-form-item v-if="openForm.type===1" label="组件名称" prop="componentName"
                      :rules="[{ required: true, message: '请输入组件名称', trigger:  'blur' }]">
          <el-input v-model="openForm.componentName"></el-input>
        </el-form-item>
        <el-form-item v-if="openForm.type===1" label="路径" prop="path">
          <el-input v-model="openForm.path"></el-input>
        </el-form-item>
        <el-form-item v-if="openForm.type===1" label="图标" prop="icon">
          <el-input v-model="openForm.icon"></el-input>
        </el-form-item>
        <el-form-item label="权限标识" prop="permission">
          <el-input v-model="openForm.permission"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort" :rules="[{ type: 'number', message: '请输入数字', trigger:  'blur' }]">
          <el-input v-model.number="openForm.sort"></el-input>
        </el-form-item>
        <el-form-item v-if="openForm.type===1" label="隐藏" prop="hidden">
          <el-switch
            v-model="openForm.hidden"
            :active-value="true"
            :inactive-value="false">
          </el-switch>
        </el-form-item>
        <!--<el-form-item v-if="openForm.type===1" label="是否外链" prop="iFrame">
          <el-switch
            v-model="openForm.iFrame"
            :active-value="true"
            :inactive-value="false">
          </el-switch>
        </el-form-item>-->
        <el-form-item>
          <el-button type="success" @click="submit('openForm')">提交</el-button>
          <el-button @click="resetForm('openForm')">取消</el-button>
        </el-form-item>

      </el-form>
      <!--</el-dialog>-->
    </el-drawer>
  </div>
</template>

<script>
  import {deletePermissionById, edit, findById, insert, list} from "@/api/menu";
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
        openForm: {},
        treeProps: {
          children: 'children',
          label: 'name',
          value: 'id',
          id: 'id',
          /* disabled: 'status',*/
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
      toggleRowExpansion(isExpan) {
        if (isExpan) {
          if (this.tableData) {
            this.tableData.forEach(e => {
              this.$refs.menuTable.toggleRowExpansion(e, true);
            })
          }
        } else {
          if (this.tableData) {
            this.tableData.forEach(e => {
              this.$refs.menuTable.toggleRowExpansion(e, false);
            })
          }
        }
      },
      openEdit(row) {
        let _this = this;
        _this.isAdd = false;
        _this.dialogVisible = true;
        _this.formLoading = true;
        list().then(resp => {
          _this.parentData = setDisabled(resp.data, row);
          findById(row.id).then(resp => {
            _this.openForm = resp.data;
            _this.formLoading = false;
          });
        })
      },
      openAdd(row) {
        let _this = this;
        _this.isAdd = true;
        _this.dialogVisible = true;
        _this.formLoading = true;
        _this.openForm = {};
        list().then(resp => {
          _this.parentData = resp.data;
          _this.openForm.pid = row.id
          _this.formLoading = false;
        })
      },
      deleteMenu(data) {
        let _this = this;
        _this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deletePermissionById(data).then(resp => {
            _this.$notify({
              title: '成功',
              message: '删除菜单成功',
              type: 'success'
            });
            _this.loadTable(_this.queryParam)
          })
        });
      },
      submit(formName) {
        let _this = this;
        let pid = _this.$refs.parentMenu.getCheckedNodes(true)[0];
        debugger
        if (pid) {
          _this.openForm.pid = pid.value;
        } else {
          _this.openForm.pid = 0;
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
          let data = resp.data;
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
