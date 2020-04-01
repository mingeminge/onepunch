<template>
  <div v-loading="loading">
    <el-card style="text-align: center">
      <div v-show="showTitle" class="el-icon-edit-outline" style="color: #279fcf;line-height: 30px"
           @click="showInput = true;showTitle=false">
        写留言
      </div>
      <div v-show="showInput">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入内容"
          v-model="textarea">
        </el-input>
        <div style="margin: 20px">
          <el-button class="el-icon-check" type="primary" @click="submit">确定</el-button>
          <el-button class="el-icon-close" @click="showInput=false;showTitle=true">取消</el-button>
        </div>

      </div>

    </el-card>
    <div v-for="i in messages" :key="i.id" style="border-bottom: 1px solid beige;margin: 20px">
      <div>
        <el-avatar style="float: left" :src="IMG_URL+i.avatar" size="medium"></el-avatar>
        <div style="line-height: 36px;padding-left: 50px;font-size: large">{{i.username}}</div>
      </div>
      <div style="font-size: x-small;color: darkgrey;margin-left: 48px;margin-top: -5px">{{i.time}}</div>
      <div
        style="padding: 10px 20px 10px 48px;word-break: break-word;line-height: 25px">
        {{i.value}}
      </div>
    </div>

  </div>
</template>

<script>
  import {findAll, save} from "@/api/message";

  export default {
    name: "index",
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        showInput: false,
        showTitle: true,
        loading: false,
        textarea: '',
        messages: []
      }
    },
    mounted() {
      this.list();
    },
    methods: {
      submit() {
        let _this = this;
        save({value: this.textarea}).then(resp => {
          _this.$message.success("留言成功");
          _this.showInput = false;
          _this.showTitle = true;
          _this.textarea = '';
          _this.list();
        }).catch(() => {
          _this.$message.error("留言失败了！")
        })
      },
      list() {
        let _this = this;
        _this.loading = true;
        findAll().then(resp => {
          this.messages = resp.data;
          _this.loading = false;
        }).catch(() => {
          _this.loading = false;
        })
      }
    }
  }
</script>

<style scoped>

</style>
