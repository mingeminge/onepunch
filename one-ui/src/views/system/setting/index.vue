<template>
  <div class="app-container">

    <el-card>
      <el-button @click="uploadVisible=true" class="el-icon-upload" type="primary">上传</el-button>
      <el-tabs v-model="activeName" @tab-click="tabClick">
        <el-tab-pane name="0" label="登录背景"></el-tab-pane>
        <el-tab-pane name="1" label="登录动画"></el-tab-pane>
        <el-table :data="tableData" v-loading="tableLoading">
          <el-table-column label="类型" prop="type">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.type===0">登录背景</el-tag>
              <el-tag v-if="scope.row.type===1">登录gif</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="是否启用" prop="isOn">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.isOn" @change="updateStatus(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column v-if="activeName==='1'" label="时间" prop="time">
            <template slot-scope="scope">
              <el-select v-model="scope.row.time" @change="updateSettingTime(scope.row)">
                <el-option label="1秒" value="1000"></el-option>
                <el-option label="2秒" value="2000"></el-option>
                <el-option label="3秒" value="3000"></el-option>
                <el-option label="4秒" value="4000"></el-option>
                <el-option label="5秒" value="5000"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="图片/gif" prop="url">
            <template slot-scope="scope">
              <el-image :src="IMG_URL+scope.row.url" style="width: 100px" :preview-src-list="previewSrcList"></el-image>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="danger" @click="deleteById(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tabs>
    </el-card>
    <el-dialog width="30%" title="上传" :visible.sync="uploadVisible">
      <el-form v-loading="dialogLoading" :model="settingForm" ref="uploadForm" label-position="left" label-width="90px">
        <el-form-item label="类型" prop="type" :rules="[{required:true,message: '请选择类型'}]">
          <el-select v-model="settingForm.type">
            <el-option label="登录背景" value="0"></el-option>
            <el-option label="登录gif" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="播放时间" v-if="settingForm.type==='1'" :rules="[{required:true,message: '请选择播放时间'}]">
          <el-select v-model="settingForm.time">
            <el-option label="1秒" value="1000"></el-option>
            <el-option label="2秒" value="2000"></el-option>
            <el-option label="3秒" value="3000"></el-option>
            <el-option label="4秒" value="4000"></el-option>
            <el-option label="5秒" value="5000"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择图片" prop="url">
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="uploadAction"
            :headers="uploadHeader"
            :on-success="uploadSuccess"
            :multiple="false"
            list-type="picture"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button class="el-icon-upload-success" type="success" @click="submitUpload('uploadForm')">上传</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog center :visible.sync="imgVisible">
      <el-image style="width: 100%;" :src="imgSrc"></el-image>
    </el-dialog>
  </div>
</template>

<script>
  import {deleteSetting, findAll, save, update} from "@/api/setting";

  export default {
    name: "index",
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        uploadAction: process.env.BASE_API + '/setting/upload',
        uploadHeader: {'Authorization': localStorage.Authorization},
        tableData: [],
        previewSrcList: [],
        tableLoading: false,
        activeName: '',
        uploadVisible: false,
        dialogLoading: false,
        settingForm: {},
        fileList: [],
        imgVisible: false,
        imgSrc: ''
      }
    },
    mounted() {
      this.loadTable({type: '0'})
    },
    methods: {
      updateSettingTime(row) {
        this.updateStatus(row)
      },
      loadTable(params) {
        let _this = this;
        _this.tableLoading = true;
        _this.previewSrcList = [];
        findAll(params).then(resp => {
          _this.tableData = resp.data;
          _this.tableData.forEach(e => {
            _this.previewSrcList.push(_this.IMG_URL + e.url)
          })
          _this.tableLoading = false;
        })
      },
      openImg(imgSrc) {
        this.imgVisible = true;
        this.imgSrc = imgSrc;
      },
      tabClick(tab) {
        this.loadTable({type: this.activeName})
      },
      deleteById(row) {
        let _this = this;
        _this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteSetting(row.id).then(resp => {
            _this.$message.success('删除成功');
            _this.loadTable({type: this.activeName})
          })
        });
      },
      updateStatus(row) {
        let _this = this;
        update(row).then(resp => {
          _this.dialogVisible = false;
          _this.$notify({
            title: '成功',
            message: '修改成功',
            type: 'success'
          });
          _this.loadTable({type: _this.activeName})
        })
      },
      uploadSuccess(response, file, fileList) {
        let _this = this;
        if (response.code === 200) {
          _this.dialogLoading = true;
          _this.settingForm.url = response.data;
          save(_this.settingForm).then(resp => {
            if (resp.code === 200) {
              _this.fileList = [];
              _this.dialogLoading = false;
              _this.uploadVisible = false;
              _this.$refs['uploadForm'].resetFields();
              _this.$message.success('上传成功!')
              _this.loadTable({type: _this.activeName});
            }
          }).catch(() => {
            _this.fileList = [];
            _this.dialogLoading = false;
            _this.uploadVisible = false;
            _this.$refs['uploadForm'].resetFields();
          })
        }
      },
      submitUpload(formName) {
        let _this = this;
        _this.$refs[formName].validate((valid) => {
          if (valid) {
            _this.dialogLoading = true;
            _this.$refs.upload.submit();
          }
          return false;
        });

      },
    }
  }
</script>

<style scoped>

</style>
