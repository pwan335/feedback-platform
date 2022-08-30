<template>
  <div class="change-password">
    <div class="change-content">
      <div class="reset_Pass">
        <img class="reset_icon" src="../../assets/userPage_images/resetPassword.png">
        <div class="existed_font">
          <span>Reset your password here</span>
        </div>
      </div>
      <div class="tips">Tips: Password must contain uppercase letters, lowercase letters<br> and numbers, and be 6-12 digits in length</div>
      <div class="resetPass_form">
        <el-input class="input"  type="password" placeholder="New Password" v-model="newPassword"></el-input>
        <el-input class="input"  type="password" placeholder="Confirm Password" v-model="confPassword"></el-input>
        <el-button class="reset_btn" @click.native="reset" type="primary" round>Reset</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {resetPassword} from "@/api/user";
import { validPassword } from "@/utils";
import md5 from 'js-md5'

export default {
  name: "ResetPassword",
  data() {
    return {
      newPassword: '',
      confPassword: ''
    }
  },
  methods:{
    reset(){
      if (!this.newPassword) {
        this.$message.warning('Please input your new password!');
        return;
      } else if (!this.confPassword) {
        this.$message.warning('Please input your confirm password!');
        return;
      } else if(this.confPassword !== this.newPassword){
        this.$message.warning('The two passwords entered do not match!');
        return;
      } else if(this.confPassword === this.newPassword){
        // validate password
        if(!validPassword(this.newPassword)) {
          this.$message.warning('Wrong password format')
          return;
        }

        // server side reset password
        const queryInfo = this.$route.query
        if(!queryInfo.secretkey) {
          return;
        }
        let setInfo = {
          "secretkey": queryInfo.secretkey,
          "password":md5(this.confPassword)
        }
        resetPassword(setInfo).then(res => {
          if(res.success){
            this.$message.success('Reset your password successfully! Go to login!');
            // back to login page
            this.$router.push({
              name: 'home',
              params: {
                resetPassword: true
              }
            })
          }else{
            this.$message.error(res.message);
          }
          console.log(res);
        }, err => {
          console.log(err);
        });
      }
    }
  }
}
</script>

<style scoped>
.change-password{
display: flex;
  justify-content: center;
}
.change-content{
  margin-top: 100px;
  background: linear-gradient(to bottom, rgba(154, 185, 250, 0.99), #e4e8f1);
  padding: 30px 80px;
  border-radius: 8px;
}

.reset_Pass{
  display: flex;
  margin-bottom: 50px;
  font-size: 26px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.reset_icon{
  width: 40px;
  height: 40px;

}

.existed_font{
  padding-top: 10px;
  padding-left: 10px;
}
.resetPass_form{
  padding-left: 10%;
  padding-right: 10%;
  height: 200px;
  box-sizing: border-box;
}
.reset_btn{
  margin-top: 3%;
  margin-left: 1%;
  width: 50%;
  font-size: 16px;
  background: linear-gradient(to right, #000099 , #2154FA);
  filter: brightness(1.4);
}

.input{
  margin-bottom: 3%;
}

.form_item{
  white-space: nowrap;
  box-sizing: border-box;
}

.form_btn{
  float: left;
  margin-left: 0%;
  box-sizing: border-box;
}
.el-input{
  width: 300px
}
.tips{
  margin-bottom: 20px;
  color: coral;
  font-size: 14px;
}
</style>