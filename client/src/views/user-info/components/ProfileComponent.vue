<template>
  <div>
    <div class="edit_profile">
      <img class="profile_icon" src="../../../assets/userPage_images/profile_light.png">
      <div class="existed_font">
        <span>Update your profile here</span>
      </div>
    </div>
    <el-form ref="profile" :model="profile" label-width="130px">
      <el-form-item class="form_item" label="userName">
        <el-col :span="10">
          <el-input v-model="profile.userName" v-if="profile.role=='user'"></el-input>
          <el-input v-model="profile.pmName" v-if="profile.role=='pm'"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="address">
        <el-col :span="10">
          <el-input v-model="profile.address"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="phoneNumber">
        <el-col :span="10">
          <el-input v-model="profile.phoneNumber"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="hobby">
        <el-col :span="10">
          <el-input v-model="profile.hobby"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_item" label="company" v-if="profile.role!=='user'">
        <el-col :span="10">
          <el-input v-model="profile.company"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="form_btn">
        <el-button type="primary" @click="onSubmit">Update profile</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { updateUserInfo, updatePmInfo } from "@/api/user";

export default {
  name: 'ProfileComponent',
  data() {
    return {
      profile: {
        userName: '',
        address: '',
        phoneNumber: '',
        hobby: '',
        company: '',
      },
    }
  },
  created() {
    const user = localStorage.getItem('userInfo')
    if(user) {
      this.profile = JSON.parse(user);
    }
  },
  methods: {
    loadUser(){
      const user = localStorage.getItem('userInfo')
      if(user) {
        this.profile = JSON.parse(user);
      }
    },
    async onSubmit() {
      if (!this.profile.userName && !this.profile.pmName) {
        this.$message.error('Please enter userName');
        return;
      }
      if (!this.profile.address) {
        this.$message.error('Please enter address');
        return;
      }

      if (!this.profile.phoneNumber) {
        this.$message.error('Please enter phoneNumber');
        return;
      }else{
        var regEmail = /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/
        if(!regEmail.test(this.profile.phoneNumber)){
          this.$message({
            message: 'The phoneNumber format is incorrect',
            type: 'error'
          })
          return;
        }
      }
      if (!this.profile.hobby) {
        this.$message.error('Please enter hobby');
        return;
      }
      if(this.profile.role == 'user') {
        try {
          let reqModel = {
            "userName": this.profile.userName,
            "address": this.profile.address,
            "phoneNumber": this.profile.phoneNumber,
            "hobby": this.profile.hobby
          }
          const res = await updateUserInfo(reqModel)
          if(res.code===20041) {
            this.$message.success('update successfully')
            localStorage.setItem('userInfo', JSON.stringify(this.profile))
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.log(err)
        }
      } else {
        if (!this.profile.company) {
          this.$message.error('Please enter company');
          return;
        }
        try {
          let reqModel = {
            "pmName": this.profile.pmName,
            "address": this.profile.address,
            "phoneNumber": this.profile.phoneNumber,
            "hobby": this.profile.hobby,
            "company": this.profile.company
          }
          const res = await updatePmInfo(reqModel)
          if(res.code===20041) {
            this.$message.success('update successfully')
            localStorage.setItem('userInfo', JSON.stringify(this.profile))
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.log(err)
        }
      }
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