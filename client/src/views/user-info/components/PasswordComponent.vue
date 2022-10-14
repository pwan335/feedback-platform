<template>
  <div>
    <div class="reset_Pass">
      <img class="reset_icon" src="../../../assets/userPage_images/resetPassword.png">
      <div class="existed_font">
        <span>Reset your password here</span>
      </div>
    </div>
    <div class="tips">Tips: Password must contain uppercase letters, lowercase letters and numbers,<br>
      and be 6-12 digits in length
    </div>
    <el-form ref="password" label-width="120px">
      <el-form-item class="form_item" label="Current Password">
        <el-col :span="10">
          <el-input type="password" v-model="oldPassword"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="New Password">
        <el-col :span="10">
          <el-input type="password" v-model="newPassword"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_btn">
        <el-button type="primary" @click="onChange">Save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { updatePassword, updatePmPassword } from "@/api/user";
import {validPassword} from "@/utils";
import md5 from 'js-md5'

export default {
  name: "PasswordComponent",
  data() {
    return {
      oldPassword: '',
      newPassword: '',
      role: '',
    }
  },
  created() {
    let info = localStorage.getItem('userInfo')
    if(info) {
      this.role = JSON.parse(info).role
    }
  },
  methods: {
    onChange() {
      if (!this.oldPassword) {
        this.$message.error('Please input your current password!');
        return;
      }
      if (!this.newPassword) {
        this.$message.error('Please input your new password!');
        return;
      }
      // validate password
      if (!validPassword(this.newPassword)) {
        this.$message.warning('Wrong password format')
        return
      }

      const password = {
        "password": md5(this.oldPassword),
        "newPwd": md5(this.newPassword)
      }
      if(this.role === 'user') {
        updatePassword(password).then(res => {
          if (res.code==20041) {
            this.$message.success('Change your password successfully!');
            this.oldPassword = '';
            this.newPassword = '';
            this.$emit('logOut')
          } else {
            this.$message.error(res.msg);
          }
        }, err => {
          console.log(err);
        });
      } else {
        updatePmPassword(password).then(res => {
          if (res.code==20041) {
            this.$message.success('Change your password successfully!');
            this.oldPassword = '';
            this.newPassword = '';
            this.$emit('logOut')
          } else {
            this.$message.error(res.msg);
          }
        }, err => {
          console.log(err);
        });
      }
    }
  }
}
</script>

<style scoped>
.reset_Pass {
  display: flex;
  margin-bottom: 30px;
  font-size: 26px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.reset_icon {
  width: 40px;
  height: 40px;

}

.existed_font {
  padding-top: 10px;
  padding-left: 10px;
}

.form_item {
  white-space: nowrap;
  box-sizing: border-box;
}

.form_btn {
  float: left;
  margin-left: 0%;
  box-sizing: border-box;
}
.tips{
  text-align: left;
  margin-bottom: 20px;
  color: coral;
  font-size: 14px;
}
</style>