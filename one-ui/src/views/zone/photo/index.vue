<template>
  <div style="padding: 20px">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="相册" name="first">
        <el-form>
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
        </el-form>
        <el-button>创建相册</el-button>

        <div style="padding-top: 20px">
          <el-image
            style="width: 100px; height: 100px"
            :src="url"
            :fit="fit" @click="imgClick"></el-image>
        </div>
      </el-tab-pane>
      <el-tab-pane label="照片" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="视频" name="third">角色管理</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import {save} from "@/api/avatar";

  export default {
    name: "index",
    data() {
      return {
        uploadAction: process.env.BASE_API + '/avatar/upload',
        uploadHeader: {'Authorization': localStorage.Authorization},
        activeName: 'first',
        fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        image: {},
      }
    },
    methods: {
      handleClick() {

      },
      imgClick() {
        alert(1)
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

</style>
