<template>
    <div>
        <div class="animate__animated animate__flipInX loginForm">
            <div class="login-title">A</div>
            <h3></h3>
            <el-form label-position="top" label-width="60px" ref="loginForm" status-icon :model="loginForm"
                     size="medium" :rules="rules">
                <el-form-item label="用户" prop="username">
                    <el-input prefix-icon="el-icon-user" v-model="loginForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input prefix-icon="el-icon-lock" type="password" show-password
                              v-model="loginForm.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitLogin('loginForm')" :loading="loginLoading">登录</el-button>
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

    export default {
        name: "index",
        data() {
            return {
                loginLoading: false,
                gifLoading: false,
                bgGif: '',
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 4, message: '用户名至少4位', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 6, max: 12, message: '密码长度在6-12位之间', trigger: 'blur'}]
                },
                loginForm: {
                    username: localStorage.username || 'yizuomin',
                    password: localStorage.password || '123456',
                }
            }
        },
        mounted() {
            let _this = this;
            document.onkeydown = function (e) {
                if (window.event.keyCode === 13) {
                    _this.submitLogin('loginForm');
                }
            };
            _this.getSysSetting();
        },
        methods: {
            getSysSetting() {
                let _this = this;
                /*getSetting({'type': 2}).then(resp => {
                    _this.bgGif = resp.data.bgUrl;
                })*/
            },
            submitLogin(formName) {
                let _this = this;
                _this.loginLoading = true;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        _this.loginLoading = true;
                        _this.$store.dispatch('app/login', _this.loginForm).then(resp => {
                            _this.$router.push('/')
                        }).catch(() => {
                            _this.loginLoading = false;
                        });
                    } else {
                        _this.loginLoading = false;
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .loginForm {
        width: 250px;
        /*margin: 0 auto;*/
        background: rgba(216, 220, 229, 0.7);
        border-radius: 5px;
        position: absolute;
        padding: 20px;
        z-index: 3;
        margin-left: 39%;
        margin-top: 10%;
    }

    .login-title {
        text-align: center;
        font-size: larger;
        font-weight: bold;
    }
</style>