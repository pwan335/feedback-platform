<template>
  <div>
    <div class="edit_profile">
      <img class="profile_icon" src="../../../assets/userPage_images/profile_light.png">
      <div class="existed_font">
        <span>Update your profile here</span>
      </div>
    </div>
    <el-form ref="profile" :model="profile" label-width="80px">
      <el-form-item class="form_item" label="First name">
        <el-col :span="10">
          <el-input v-model="profile.firstname"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="Last name">
        <el-col :span="10">
          <el-input v-model="profile.lastname"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="Email">
        <el-col :span="10">
          <el-input v-model="profile.email"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_btn">
        <el-button type="primary" @click="onSubmit">Update profile</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {updateProfile} from "@/api/user";
import md5 from "js-md5";

export default {
  name: 'ProfileComponent',
  data() {
    return {
      profile: {}
    }
  },
  created() {
    // this.loadUser();
    const user = sessionStorage.getItem('user')
    if(user) {
      console.log(JSON.parse(user));
      this.profile = JSON.parse(user);
    }
    console.log(user)
  },
  methods: {
    loadUser(){
      const user = sessionStorage.getItem('user')
      if(user) {
        console.log(JSON.parse(user));
        this.profile = JSON.parse(user);
      }
      console.log(user)
    },
    onSubmit() {
      if (!this.profile.firstname) {
        this.$message.error('Please input your first name');
        return;
      }
      if (!this.profile.lastname) {
        this.$message.error('Please input your last name');
        return;
      }

      if (!this.profile.email) {
        this.$message.error('Please input your email');
        return;
      }else{
        var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if(!regEmail.test(this.profile.email)){
          this.$message({
            message: 'The email format is incorrect',
            type: 'error'
          })
          this.loadUser();
          // let oldUser = sessionStorage.getItem('user');
          // oldUser = JSON.parse(oldUser);
          // this.profile.email = oldUser.email;
          return;
        }
      }

      this.$prompt('Please input your password', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        inputPattern: /\S/,
        inputErrorMessage: 'Error: can not be null',
        inputType: 'password'
      }).then(({ value }) => {
        const newProfile = {
          "firstname": this.profile.firstname,
          "lastname": this.profile.lastname,
          "email": this.profile.email,
          "password": md5(value)
        };
        console.log(newProfile)
        updateProfile(newProfile).then(res=>{
          console.log(res)
          if(res.success){
            let oldUser = sessionStorage.getItem('user');
            let newUser = res.data;
            oldUser = JSON.parse(oldUser);
            // console.log(oldUser);
            oldUser.firstname = newUser.firstname;
            oldUser.lastname = newUser.lastname;
            oldUser.email = newUser.email;
            sessionStorage.setItem('user', JSON.stringify(oldUser))
            this.$emit('updateUser')
            this.$message.success('Update your profile successfully!');
          }else{
            this.$message.error(res.message);
            this.loadUser();
          }
          // this.$emit('updateProfile', res.user);
        },err=>{
          console.log(err);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Input canceled'
        });
        // let oldUser = sessionStorage.getItem('user');
        // oldUser = JSON.parse(oldUser);
        this.loadUser();

      });
      // console.log("submit!");
    }
  }
}

</script>

<style scoped>

.edit_profile{
  display: flex;
  margin-bottom: 50px;
  font-size: 26px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.profile_icon{
  width: 40px;
  height: 40px;

}

.existed_font{
  padding-top: 10px;
  padding-left: 10px;
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

</style>