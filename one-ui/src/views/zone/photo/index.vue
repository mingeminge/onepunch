<template>
  <div style="padding: 20px">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="相册" name="first">
        <el-button>上传照片/视频</el-button>
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
  export default {
    name: "index",
    data(){
      return{
        activeName:'first',
        fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
      }
    },
    methods:{
      handleClick(){

      },
      imgClick(){
        alert(1)
      }
    }
  }
</script>

<style scoped>

</style>
