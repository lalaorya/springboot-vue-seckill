<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">用户登录</div>
      <el-form
        :model="loginForm"
        :rules="rules"
        ref="login"
        label-width="0px"
        class="ms-content"
      >
        <el-form-item prop="nick">
          <el-input v-model="loginForm.nick" placeholder="用户名">
            <!-- <el-button slot="prepend" icon="el-icon-lx-people">昵称</el-button> -->
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="密码"
            v-model="loginForm.password"
            @keyup.enter.native="login"
          >
            <!-- <el-button slot="prepend" icon="el-icon-lx-lock">密码</el-button>、 -->
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="login">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
// import md5 from "js-md5"
export default {
  data: function () {
    return {
      loginForm: {
        nick: "admin",
        password: "admin",
      },
      rules: {
        nick: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    login() {
      const _this = this;
      // _this.

      // _this.$router.push('/goodlist');
      // 这是element ui的验证表单格式的固定写法
      this.$refs.login.validate((valid) => {
        if (valid) {
          // 发送请求
          // 前端第一次md5 对密码加密
          _this.loginForm.password = this.$md5(_this.loginForm.password);
          console.log(_this.loginForm.password);

          this.$axios.post("/login", this.loginForm).then((res) => {
            // 请求后端,如果返回为400,会被axios自动拦截,那里有拦截方法
            _this.$message.success("登录成功");
            // 从响应头拿到token
            const token = res.headers["authorization"];
            _this.$store.commit("SET_TOKEN", token);
            _this.$store.commit("SET_USERINFO", res.data.data);
            // _this.$store.commit("SET_USERINFO", res.data.data);
             _this.$router.push('/goodlist');
          });
        } else {
          this.$message({
            showClose: true,
            message: "请输入用户名和密码",
            type: "error",
          });
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/b-login.jpg);
  background-size: 100%;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 22px;
  color: rgb(31, 27, 27);
  border-bottom: 1px solid rgb(10, 245, 30);
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.5);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>