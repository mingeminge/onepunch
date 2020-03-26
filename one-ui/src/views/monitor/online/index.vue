<template>
  <div class="app-container" v-loading="loading">
    <el-card>
      <el-table :data="tableData" ref="aliveUserTable" stripe style="width: 100%">
        <el-table-column prop="username" label="用户名"></el-table-column>
        <!--<el-table-column prop="country" label="登录国家"></el-table-column>
        <el-table-column prop="region" label="登录省份"></el-table-column>
        <el-table-column prop="city" label="登录城市"></el-table-column>-->
        <el-table-column prop="ip" label="IP地址"></el-table-column>
        <el-table-column prop="address" label="登陆地点"></el-table-column>
        <el-table-column prop="system" label="操作系统"></el-table-column>
        <el-table-column prop="browser" label="浏览器"></el-table-column>
        <el-table-column prop="isp" label="网络类型"></el-table-column>
        <el-table-column prop="lastLoginTime" label="登录时间"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="danger" @click="kickoutUser(scope.row)">强制下线</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import {getOnlineUserList, killOnlineUser} from "@/api/online";

  export default {
    name: "AliveUser",
    data() {
      return {
        loading: false,
        tableData: []
      }
    },
    mounted() {
      this.getAliveUsers();
    },
    methods: {
      getAliveUsers() {
        let _this = this;
        _this.loading = true;
        getOnlineUserList().then(resp => {
          if (resp.code === 200) {
            _this.tableData = resp.data;
            _this.loading = false;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      kickoutUser(row) {
        let _this = this;
        _this.$confirm('强制下线后用户将不可继续操作, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
         /* if (row.token === _this.$store.getters.token) {
            _this.$confirm('自己踢自己?是个狼人!', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              killOnlineUser(row).then(resp => {
                if (resp.code === 200) {
                  _this.$store.dispatch('logout').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                    _this.$router.push('/login')
                  })
                }
              }).catch(() => {
                _this.$message.error("强制下线失败");
                _this.loading = false;
              })
            });
          } else {*/
            killOnlineUser(row).then(resp => {
              if (resp.code === 200) {
                _this.$message.success("强制下线成功");
                _this.loading = false;
                _this.getAliveUsers();
              }
            }).catch(() => {
              _this.$message.error("强制下线失败");
              _this.loading = false;
            })
         /* }*/
        }).catch(() => {
          _this.loading = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
