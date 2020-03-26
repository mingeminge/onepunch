<template>
  <div class="app-container">
    <!--<el-card style="margin-top: 10px">-->
    <el-form inline>
      <el-form-item>
        <el-button class="el-icon-refresh" type="success" @click="search">刷新</el-button>
        <el-button @click="uploadVisible=true" class="el-icon-upload" type="primary">上传
        </el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="20" v-loading="loading">
      <el-col v-for="item in images" :key="item.id" :span="4" :xs="12" class="image">
        <el-card :body-style="{ padding: '0px' }">
          <div slot="header" class="clearfix">
            <strong>{{item.name||'未命名'}}</strong>
          </div>
          <el-image fit="scale-down" style="height: 150px" :src="IMG_URL+item.imgUrl"
                    lazy></el-image>
          <!--<el-avatar shape="square" :size="100" :src="IMG_URL+item.imgUrl"></el-avatar>-->
          <div>
            <div class="bottom clearfix">
              <el-button type="text" class="el-icon-edit" @click="openImg(IMG_URL+item.imgUrl)">查看
              </el-button>
              <el-button type="text" class="el-icon-edit" @click="openUpdate(item)">修改
              </el-button>
              <el-button type="text" class="el-icon-delete" @click="deleteById(item.id)">
                删除
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- </el-card>-->
    <el-dialog :width="dialogWidth" title="头像上传" :visible.sync="uploadVisible">
      <el-form v-loading="dialogLoading" :model="image" ref="uploadForm" label-position="left" label-width="70px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="image.name"></el-input>
        </el-form-item>
        <el-form-item label="选择图片" prop="imgUrl">
          <el-upload
            class="upload-demo"
            ref="upload"
            :action="uploadAction"
            :headers="uploadHeader"
            :on-success="uploadSuccess"
            :before-upload="beforeAvatarUpload"
            :multiple="false"
            list-type="picture"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button class="el-icon-upload-success" type="success" @click="submitUpload">上传</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--<el-dialog :width="dialogWidth" title="头像编辑" :visible.sync="updateVisible">-->
    <el-dialog width="30%" title="修改" :visible.sync="updateVisible">
      <el-form style=";padding-right: 5%" v-loading="dialogLoading"
               :model="updateImg" ref="updateForm" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="updateImg.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="el-icon-success" @click="update">保存</el-button>
          <el-button class="el-icon-error" @click="updateVisible=false">取消</el-button>
        </el-form-item>
      </el-form>
      <!--</el-dialog>-->
    </el-dialog>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[12, 24, 48, 96]"
      :page-size="12"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-dialog center :top="dialogTop" :width="dialogWidth" :visible.sync="imgVisible">
      <el-image style="width: 100%;" :src="imgSrc"></el-image>
    </el-dialog>
  </div>
</template>

<script>
  import {deleteImg, findAll, save, updateImg} from "@/api/avatar";

  export default {
    name: "index",
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        uploadAction: process.env.BASE_API + '/avatar/upload',
        uploadHeader: {'Authorization': localStorage.Authorization},
        fileList: [],
        loading: true,
        dialogLoading: false,
        query: {current: 1, size: 12, type: ''},
        images: [],
        total: 0,
        uploadVisible: false,
        updateVisible: false,
        image: {},
        updateImg: {},
        imgSrc: '',
        imgVisible: false
      }
    },
    computed: {
      dialogWidth() {
        return '50%'
      },
      dialogTop() {
        return '1vh'
      }
    },
    mounted() {
      this.getImages(this.query);
    },
    methods: {
      search() {
        this.getImages(this.query);
      },
      update() {
        let _this = this;
        _this.dialogLoading = true;
        updateImg(_this.updateImg).then(resp => {
          if (resp.code === 200) {
            _this.dialogLoading = false;
            _this.updateVisible = false;
            _this.$refs['updateForm'].resetFields();
            _this.$message.success('修改成功!');
            _this.getImages(_this.query);
          }
        }).catch(() => {
          _this.dialogLoading = false;
          _this.updateVisible = false;
        })
      },
      openUpdate(item) {
        this.updateVisible = true;
        this.updateImg = item;
      },
      deleteById(id) {
        let _this = this;
        _this.$confirm('是否删除该头像?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
          deleteImg(id).then(resp => {
            if (resp.code === 200) {
              _this.loading = false;
              _this.$message.success('删除成功');
              _this.getImages(_this.query);
            }
          }).catch(() => {
            _this.loading = false;
          })
        });
      },
      getImages(params) {
        let _this = this;
        _this.loading = true;
        findAll(params).then(resp => {
          if (resp.code === 200) {
            _this.images = resp.data;
            _this.total = resp.count;
            _this.loading = false;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      submitUpload() {
        this.dialogLoading = true;
        this.$refs.upload.submit();
      },
      uploadSuccess(response, file, fileList) {
        let _this = this;
        if (response.code === 200) {
          _this.dialogLoading = true;
          _this.image.imgUrl = response.data;
          save(_this.image).then(resp => {
            if (resp.code === 200) {
              _this.fileList = [];
              _this.dialogLoading = false;
              _this.uploadVisible = false;
              _this.$refs['uploadForm'].resetFields();
              _this.$message.success('上传成功!')
              _this.getImages(_this.query);
            }
          }).catch(() => {
            _this.fileList = [];
            _this.dialogLoading = false;
            _this.uploadVisible = false;
            _this.$refs['uploadForm'].resetFields();
          })
        }
      },
      handleSizeChange(val) {
        this.query.size = val;
        this.getImages(this.query)
      },
      handleCurrentChange(val) {
        this.query.current = val;
        this.getImages(this.query)
      },
      openImg(imgSrc) {
        this.imgVisible = true;
        this.imgSrc = imgSrc;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
          this.dialogLoading = false;
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
          this.dialogLoading = false;
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>

<style scoped>
  .image {
    margin-bottom: 10px;
    text-align: center;
    font-size: small;
    color: rgba(0, 0, 0, .45);
  }
</style>
