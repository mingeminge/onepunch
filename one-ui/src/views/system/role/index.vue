<template>
  <div class="app-container">
    <el-form :model="queryForm" inline class="query-form" ref="queryForm">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="queryForm.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" class="el-icon-search" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
    <div style="padding-bottom: 10px">
      <el-button class="el-icon-refresh" @click="query">刷新</el-button>
      <el-button type="success" class="el-icon-plus" @click="openAdd">新增</el-button>
      <!--<el-button type="warning" class="el-icon-upload">导入</el-button>
      <el-button type="primary" class="el-icon-download">导出</el-button>-->
    </div>
    <el-row :gutter="20">
      <el-col :span="18">
        <el-card shadow="never">
          <div slot="header" class="clearfix">
            <strong>角色列表</strong>
          </div>
          <el-table v-loading="loading" :data="tableData" ref="roleTable" @row-click="rowClick" highlight-current-row>
            <el-table-column width="100" align="center" prop="name" label="角色名"></el-table-column>
            <el-table-column width="140" align="center" prop="description" label="描述"></el-table-column>
            <el-table-column width="100" align="center" prop="status" label="状态">
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
            <el-table-column width="140" align="center" :show-overflow-tooltip="true" prop="createTime"
                             label="创建日期"></el-table-column>
            <el-table-column width="140" align="center" :show-overflow-tooltip="true" prop="updateTime"
                             label="修改日期"></el-table-column>
            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="primary" class="el-icon-search" circle @click="check(scope.row)"></el-button>
                <!-- <el-button type="info" @click="authentication(scope.row)">授权</el-button>-->
                <el-button type="warning" class="el-icon-edit" circle @click="openEdit(scope.row)"></el-button>
                <el-button type="danger" class="el-icon-delete" circle @click="deleteRole(scope.row)"></el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="5"
            :current-page="queryForm.current"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
          <div slot="header" class="clearfix">
            <strong>授权</strong>
          </div>
          <el-form v-loading="roleTreeLoading">
            <el-form-item>
              <el-tree
                highlight-current
                check-on-click-node
                :default-expanded-keys="permDefaultKeys"
                :props="treeProps"
                :data="permissions"
                node-key="id"
                ref="permissionTree"
                check-strictly
                show-checkbox>
              </el-tree>
            </el-form-item>
            <el-form-item>
              <el-button size="small" class="el-icon-success" type="primary" @click="updateRolePermission">保存
              </el-button>
              <!--<el-button @click="drawerVisible=false">取消</el-button>-->
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog width="40%" :visible.sync="dialogVisible" :title="formTitle">
      <el-form style="padding-left: 15%;padding-right: 20%" v-loading="formLoading" ref="openForm" :model="openForm"
               label-width="80px">
        <el-form-item label="角色名称" prop="name" :rules="[{ required: true, message: '请输入角色名', trigger:  'blur' }]">
          <el-input :disabled="isCheck" v-model="openForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input :disabled="isCheck" v-model="openForm.description"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch :disabled="isCheck"
                     v-model="openForm.status"
                     :active-value="0"
                     :inactive-value="1"
                     active-color="#13ce66"
                     inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
        <!--<el-form-item label="组织机构" prop="depts">
          <el-tree
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
          </el-tree>
        </el-form-item>-->
        <el-form-item v-if="isCheck" label="创建时间" prop="createTime">
          <el-input :disabled="isCheck" v-model="openForm.createTime"></el-input>
        </el-form-item>
        <el-form-item v-if="isCheck" label="修改时间" prop="updateTime">
          <el-input :disabled="isCheck" v-model="openForm.updateTime"></el-input>
        </el-form-item>
        <el-form-item v-if="!isCheck">
          <el-button type="success" @click="submit('openForm')">保存</el-button>
          <el-button @click="resetForm('openForm')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--<el-drawer
      :visible.sync="drawerVisible"
      ref="drawer"
      size="35%"
      direction="rtl">
      &lt;!&ndash;<div slot="header" class="clearfix">
        <strong>编辑权限</strong>
      </div>&ndash;&gt;

    </el-drawer>-->
  </div>
</template>

<script>
  import {deleteRole, edit, findById, insert, list, menuList} from "@/api/role";

  export default {
    name: "role",
    data() {
      return {
        queryForm: {current: 1, size: 5, name: ''},
        tableData: [],
        loading: false,
        roleTreeLoading: false,
        deptTreeLoading: false,
        dialogVisible: false,
        drawerVisible: false,
        formLoading: false,
        isAdd: false,
        isCheck: true,
        currentRow: null,
        openForm: {},
        deptList: [],
        total: 0,
        permissions: [],
        deptDefaultKeys: [],
        permDefaultKeys: [],
        formTitle: '查看',
        treeProps: {
          children: 'children',
          label: 'name',
          id: 'id',
          disabled: 'status'
        }
      }
    },
    computed: {
      queryParam() {
        return {
          'current': this.queryForm.current,
          'size': this.queryForm.size,
          'name': this.queryForm.name
        }
      }
    },
    mounted() {
      this.loadTable(this.queryParam)
    },
    methods: {
      query() {
        this.queryForm.current = 1;
        this.loadTable(this.queryParam)
      },
      updateRolePermission() {
        let _this = this;
        if (_this.currentRow) {
          _this.roleTreeLoading = true;
          _this.currentRow.permissions = _this.$refs.permissionTree.getCheckedKeys();
          edit(_this.currentRow).then(resp => {
            _this.roleTreeLoading = false;
            _this.drawerVisible = false;
            _this.$notify({
              title: '成功',
              message: '修改权限成功',
              type: 'success'
            });
          })
        } else {
          _this.$message.warning('请选择一行用户数据');
        }
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
        //_this.openForm.depts = _this.$refs.deptTree.getCheckedKeys();
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            if (_this.isAdd) {
              insert(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '新增角色成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryParam)
              }).catch(() => {
                _this.dialogVisible = false;
              })
            } else {
              edit(_this.openForm).then(resp => {
                _this.dialogVisible = false;
                _this.$notify({
                  title: '成功',
                  message: '修改成功',
                  type: 'success'
                });
                _this.loadTable(_this.queryParam)
              }).catch(() => {
                _this.dialogVisible = false;
              })
            }

          }
        });
      },
      rowClick(row) {
        let _this = this;
        let activePermissions = [];
        _this.currentRow = row;
        findById(row.id).then(res => {
          res.data.permissions.forEach(p => {
            activePermissions.push(p)
          });
          _this.$refs.permissionTree.setCheckedKeys(activePermissions);
          _this.permDefaultKeys = activePermissions;
        })
      },
      openEdit(row) {
        let _this = this;
        _this.dialogVisible = true;
        _this.formLoading = true;
        _this.formTitle = '编辑';
        _this.isCheck = false;
        _this.deptTreeLoading = true;
        _this.permDefaultKeys = [];
        //_this.deptDefaultKeys = [];
        _this.isAdd = false;
        let activePermissions = [];
        let activeDept = [];
        findById(row.id).then(res => {
          _this.openForm = res.data;
          res.data.permissions.forEach(p => {
            activePermissions.push(p)
          });
          res.data.depts.forEach(d => {
            activeDept.push(d);
          })
          _this.$refs.permissionTree.setCheckedKeys(activePermissions);
          _this.permDefaultKeys = activePermissions;
          /* deptList().then(resp => {
             _this.deptList = resp.data;
             _this.deptTreeLoading = false;
             _this.$refs.deptTree.setCheckedKeys(activeDept);
             _this.deptDefaultKeys = activeDept;
           });*/
          _this.roleTreeLoading = false;
          _this.formLoading = false;
        }).catch(() => {
          _this.roleTreeLoading = false;
          _this.formLoading = false;
        });
      },
      deleteRole(data) {
        let _this = this;
        _this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            deleteRole(data).then(resp => {
              _this.$notify({
                title: '成功',
                message: '删除角色成功',
                type: 'success'
              });
              _this.loadTable(_this.queryParam)
            })
          }
        );
      },
      openAdd() {
        this.formTitle = '新增'
        this.dialogVisible = true;
        this.isAdd = true;
        this.isCheck = false;
        //this.roleTreeLoading=false
        this.deptDefaultKeys = '';
        this.openForm = {};
      },
      check(row) {
        this.dialogVisible = true;
        this.openForm = row;
        this.formTitle = '查看';
        this.openEdit(row);
        this.isCheck = true;
      },
      authentication(row, column) {
        let _this = this;
        _this.currentRow = row;
        /*if (column.label === '状态') {
          return false;
        }*/
        let activePermissions = [];
        _this.roleTreeLoading = true;
        _this.drawerVisible = true;
        findById(row.id).then(res => {
          res.data.permissions.forEach(p => {
            activePermissions.push(p)
          });
          _this.$refs.permissionTree.setCheckedKeys(activePermissions);
          _this.permDefaultKeys = activePermissions;
          _this.roleTreeLoading = false;
        }).catch(() => {
          _this.roleTreeLoading = false;
        });
      },
      loadTable(params) {
        let _this = this;
        _this.loading = true;
        _this.roleTreeLoading = true;
        menuList().then(res => {
          _this.permissions = res.data;
          _this.roleTreeLoading = false;
          list(params).then(resp => {
            _this.tableData = resp.data;
            _this.loading = false;
            _this.total = resp.count;
          }).catch(() => {
            _this.loading = false;
          })
        });
      },
      handleSizeChange(val) {
        this.queryForm.size = val;
        this.loadTable(this.queryParam)
      },
      handleCurrentChange(val) {
        this.queryForm.current = val;
        this.loadTable(this.queryParam)
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
