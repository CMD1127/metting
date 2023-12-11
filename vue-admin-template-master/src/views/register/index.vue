<template>
  <div class="register-container">
    <article class="header">
      <header>
        <el-avatar icon="el-icon-user-solid" shape="circle" />
        <span class="login">
          <em class="bold">已有账号？</em>
          <a href="/login">
            <el-button type="primary" size="mini">登录</el-button>
          </a>
        </span>
      </header>
    </article>
    <section>
      <el-form
        ref="user"
        :model="user"
        :rules="rule"
        label-width="100px"
        autocomplete="off"
        :hide-required-asterisk="true"
        size="medium"
      >
        <div style="padding-top: 10px">
          <el-form-item label="账号" prop="loginName">
            <el-col :span="10">
              <el-input
                v-model="user.loginName"
                placeholder="输入账号"
              />
            </el-col>

            <span class="status">{{ statusMsg }}</span>
          </el-form-item>
          
          <el-form-item label="名称" prop="userName">
            <el-col :span="10">
              <el-input
                v-model="user.userName"
                maxlength="6"
                placeholder="请输入用户名称"
              />
            </el-col>
          </el-form-item>

          <el-form-item label="用户类型" prop="role">
            <el-col :span="10">
      <el-select v-model="user.role" placeholder="请选择用户类型" >
        <el-option label="普通用户" value="0"></el-option>
      </el-select>
    </el-col>
    </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-col :span="10">
              <el-input v-model="user.password" type="password" />
            </el-col>
          </el-form-item>
          <el-form-item label="确认密码" prop="cpassword">
            <el-col :span="10">
              <el-input v-model="user.cpassword" type="password" />
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              style="width: 40%"
              @click="registerInfo"
            >注册</el-button>
          </el-form-item>
        </div>
      </el-form>
    </section>

    <div class="error">{{ error }}</div>
  </div>
</template>

<script>
import register from '@/api/edu/register'
export default {
  name: 'Register',
  data() {
    return {
      statusMsg: '',
      error: '',
      isDisable: false,
      formLabelWidth: '500px',
      codeLoading: false,
      user: {
        loginName: '',
        userName: '',
        password: '',
        role: '',
      },
      rule: {
        loginName: [{
          required: true,
          message: '请输入账号',
          trigger: 'blur'
        }],
        userName: [{
          required: true,
          message: '请输入用户名称',
          trigger: 'blur'
        }],
        role: [{
          required: true,
          message: '请输入选择用户类型',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '创建密码',
          trigger: 'blur'
        }, { pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/, message: '密码必须同时包含数字与字母,且长度为 6-20位' }],
        cpassword: [{
          required: true,
          message: '确认密码',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请再次输入密码'))
            } else if (value !== this.user.password) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }]

      }
    }
  },
  methods: {
    // 用户注册
    registerInfo(){
      register.register(this.user)
            .then(response=>{
              this.$message({
              showClose: true,
              message: '注册成功，正在跳转到登录界面...',
              type: 'success'
            })
            setTimeout(() => {
              this.$router.push('/')
            }, 2000)
            }).catch(err => {
            console.log(err.response.data.message)
          })
       
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
  .el-select{
    display: inline-block;
    height: 47px;
    width: 100%;
  }
  .el-input {
    display: inline-block;
    height: 47px;
    width: 95%;

    input {
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      border: 1px solid rgba(255, 255, 255, 0.1);
      -webkit-appearance: none;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    label {
      font-style: normal;
      font-size: 12px;
      color: $light_gray;
    }
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.register-container {
  
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .header {
    border-bottom: 2px solid rgb(235, 232, 232);
    min-width: 980px;
    color: #666;

    header {
      margin: 0 auto;
      padding: 10px 0;
      width: 980px;
      height: 40px;
      .login {
        float: right;
      }

      .bold {
        font-style: normal;
        color: $light_gray;
      }
    }
  }

  > section {
    margin: 0 auto 30px;
    padding-top: 30px;
    width: 980px;
    min-height: 300px;
    padding-right: 100px;
    box-sizing: border-box;

    .status {
      font-size: 12px;
      margin-left: 20px;
      color: #e6a23c;
    }

    .error {
      color: red;
    }
  }

  .tips {
    float: right;
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
}
</style>

<style scoped>
/* 修改验证器样式 */
::v-deep .el-form-item.is-error .el-input__inner {
  border-color: #889aa4;
}
::v-deep .el-form-item.is-error .el-input__validateIcon {
  color: #889aa4;
}
::v-deep .el-form-item__error {
  color: #e6a23c;
}
</style>