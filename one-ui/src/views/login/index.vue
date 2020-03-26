<template>
  <div class="bg" v-loading="gifLoading"
       element-loading-text="正在加载超级炫酷的动画! ๑乛◡乛๑ ">
    <el-image @load="loadImgSuccess" v-show="showGif" style="height: 100%;width: 100%;position: absolute;z-index: 3333"
              :src="loginGif"></el-image>
    <el-image :fit="fit" :src="bgImg"></el-image>
    <div class="login-form animated flipInY" :title="'兴趣使然的Hero'">
      <div class="login-title">ONE PUNCH MAN</div>
      <h3></h3>
      <el-form size="medium" ref="loginForm" status-icon :model="form" :rules="rules">
        <el-form-item prop="username" :error="errorMsg">
          <el-input placeholder="账号" prefix-icon="el-icon-user"
                    v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" :error="errorMsg">
          <el-input placeholder="密码" prefix-icon="el-icon-lock" v-model="form.password" show-password
                    type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="form.rememberMe" @change="isRemember">记住我</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="star"
      :particleSize="3"
      linesColor="#fff"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="repulse">
    </vue-particles>
  </div>
</template>

<script>
  import {isMobile, GetCurrentBrowser, GetOs} from "@/utils/util";
  import {getSettings} from "@/api/setting";

  export default {
    name: "index",
    data() {
      return {
        form: {
          username: localStorage.username||'admin',
          password: localStorage.password||'123456',
          rememberMe: false
        },
        loading: false,
        rules: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 4, message: '用户名至少4位', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 12, message: '密码长度在6-12位之间', trigger: 'blur'}]
        },
        gifLoading: true,
        showGif: false,
        errorMsg: '',
        bgImg: '',
        loginGif: '',
        time: ''
      }
    },
    created() {
      //this.loadSystemSetting();
    },
    mounted() {
      let _this = this;
      document.onkeydown = function (e) {
        let key = window.event.keyCode;
        if (key === 13) {
          _this.login();
        }
      };
      this.loadSystemSetting();
    },
    computed: {
      redirect() {
        return this.$router.params.redirect;
      },
      fit() {
        if (isMobile()) {
          return 'scale-down;';
        } else {
          return 'cover';
        }
      },
    },
    methods: {
      loadSystemSetting() {
        let _this = this;
        getSettings().then(resp => {
          _this.bgImg = process.env.IMG_URL + resp.data.bg;
          _this.loginGif = process.env.IMG_URL + resp.data.gif;
          _this.time = resp.data.time
        })
      },
      isRemember(){
        if(this.form.rememberMe){
          localStorage.username=this.form.username;
          localStorage.password=this.form.password;
        }
      },
      loadImgSuccess(e) {
        this.gifLoading = false;
      },
      login() {
        let _this = this;
        _this.loading = true;
        _this.form.system = GetOs();
        _this.form.browser = GetCurrentBrowser();
        _this.$refs['loginForm'].validate(valid => {
          if (valid) {
            if (_this.form.rememberMe) {
              localStorage.username = _this.form.username;
              localStorage.password = _this.form.password;
            }
            _this.errorMsg = '';
            _this.loading = true;
            _this.$store.dispatch('user/login', _this.form).then(resp => {
              _this.showGif = true;
              setTimeout(function () {
                _this.showGif = false;
                setTimeout(function () {
                  //_this.loading = false;
                  _this.$router.push('/')
                }, 100)
              }, _this.time)
            }).catch(() => {
              _this.loading = false;
            });
          } else {
            _this.loading = false;
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped lang="scss">
  .bg {
    position: relative;
    height: 100%;
    width: 100%;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    #particles-js {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
    }
  }

  .login-form {
    width: 250px;
    position: absolute;
    border-radius: 5px;
    padding: 20px;
    z-index: 3;
    margin-left: 35%;
    margin-top: 5%;
    background: rgba(216, 220, 229, 0.7);
    /*background: rgba(255, 255, 255, .8);*/
    .el-form-item {
      margin-bottom: 25px !important;
    }
    .login-title {
      /*color: #707070;*/
      text-align: center;
      font-size: larger;
      font-weight: bold;
    }
    h3 {
      text-align: center;
      color: #ebedef;
      margin-top: 0px;
      margin-bottom: 5px;
      span {
        color: #20a0ff;
      }
    }
    form {
      margin-top: 25px;
      .el-form-item {
        margin-bottom: 15px;
      }
    }
    a {
      text-decoration: none;
      color: #1f2d3d;
    }
    button {
      width: 100%;
      font-weight: 600;
    }
  }
</style>
