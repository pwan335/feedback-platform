<template>
  <div class="container">
    <div class="userInfo-container">
      <div class="home" @click="backHome" v-if="userInfo.role=='user'">
        <span class="el-icon-arrow-left"></span>
        <span class="back-text">home</span>
      </div>
      <div class="item-list">
        <el-image class="avatar" :src="formatUrl(userInfo.avatar)" @click="showPop=true"></el-image>
        <span :class="['item', activetedIndex==1?'item-activated':'']" @click="showForm(1)">Edit Profile</span>
        <span :class="['item', activetedIndex==2?'item-activated':'']" @click="showForm(2)">Change Password</span>
        <span v-if="userInfo.role!=='user'" :class="['item', activetedIndex==3?'item-activated':'']" @click="showForm(3)">Manage Page</span>
        <span v-if="userInfo.role!=='user'" :class="['item', activetedIndex==4?'item-activated':'']" @click="showForm(4)">Topic Edition</span>
        <span v-if="userInfo.role=='user'" :class="['item', activetedIndex==5?'item-activated':'']" @click="showForm(5)">Relative Topic</span>
        <div class="signOut_btn">
          <el-button type="danger" size="small" plain @click="signOut">Sign-out</el-button>
        </div>
      </div>
      <div class="detail" v-if="showProfile">
        <profile-component @updateUser="updateUser"/>
      </div>
      <div class="detail" v-if="showPassword">
        <password-component @logOut="signOut" />
      </div>
      <div class="detail" v-if="showManage">
        <manage-component />
      </div>
      <div class="detail" v-if="showTopic">
        <topic-component />
      </div>
      <div class="detail" v-if="showRelative">
        <relative-topic />
      </div>
    </div>
    <el-dialog width="350px" :visible.sync="showPop" title="Update Avatar" :close-on-click-modal="false" :before-close="closeDialog">
      <el-upload
          class="avatar-uploader"
          action=""
          :show-file-list="false"
          :auto-upload="false"
          :on-change="handleChange"
          :on-remove="handleRemove">
        <img v-if="imageUrl" :src="imageUrl" class="new-avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-button type="primary" @click="changeAvatar" class="confirm-btn">confirm</el-button>
    </el-dialog>
  </div>
</template>

<script>
import ProfileComponent from "@/views/user-info/components/ProfileComponent";
import PasswordComponent from "@/views/user-info/components/PasswordComponent";
import ManageComponent from "@/views/user-info/components/ManageComponent";
import TopicComponent from "@/views/user-info/components/TopicComponent";
import RelativeTopic from "@/views/user-info/components/RelativeTopic";
import {getImageHost} from "@/utils";
import { updateAvatar, updatePmAvatar } from "@/api/user";

export default {
  name: "UserInfo",
  components: {
    PasswordComponent,
    ProfileComponent,
    ManageComponent,
    TopicComponent,
    RelativeTopic
  },
  data() {
    return {
      showProfile: true,
      showPassword: false,
      showManage: false,
      showTopic: false,
      showRelative: false,
      activetedIndex: 1,
      userInfo: {},
      showPop: false,
      imageUrl: '',
      file: '',
    }
  },
  created() {
    let userInfo = localStorage.getItem('userInfo')
    if(userInfo) {
      this.userInfo = JSON.parse(userInfo)
    }
  },
  methods:{
    async changeAvatar() {
      if(!this.file) {
        this.$message.warning('please choose your avatar first')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      if(this.userInfo.role=='user') {
        try {
          const res = await updateAvatar(formData)
          if(res.code === 20021) {
            this.$message.success('update successfully')
            this.userInfo.avatar = res.data
            localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
            this.closeDialog()
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.log(err)
        }
      } else if(this.userInfo.role == 'pm') {
        try {
          const res = await updatePmAvatar(formData)
          if(res.code === 20021) {
            this.$message.success('update successfully')
            this.userInfo.avatar = res.data
            localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
            this.closeDialog()
          } else {
            this.$message.error(res.msg)
          }
        } catch (err) {
          console.log(err)
        }
      }
    },

    closeDialog() {
      this.showPop = false
      this.file = ''
      this.imageUrl = ''
    },

    handleRemove(file) {
      console.log(file)
    },

    handleChange(file) {
      if(file) {
        this.file = file
      }
      this.imageUrl = URL.createObjectURL(file.raw)
    },

    formatUrl(url) {
      return getImageHost() + url
    },

    showForm(item){
      this.activetedIndex = item;
      if(item == 1){
        this.showProfile = true;
        this.showPassword= false;
        this.showManage = false;
        this.showTopic = false;
        this.showRelative = false
      }else if(item == 2){
        this.showProfile = false;
        this.showPassword= true;
        this.showManage = false;
        this.showTopic = false;
        this.showRelative = false
      }else if(item == 3){
        this.showProfile = false;
        this.showPassword= false;
        this.showManage = true;
        this.showTopic = false;
        this.showRelative = false
      }else if(item == 4){
        this.showProfile = false;
        this.showPassword= false;
        this.showManage = false;
        this.showRelative = false
        this.showTopic = true;
      }else if(item == 5){
        this.showProfile = false;
        this.showPassword= false;
        this.showManage = false;
        this.showTopic = false;
        this.showRelative = true
      }
    },
    signOut(){
      // 退出登录，返回首页
      localStorage.clear()
      this.$router.go(-1)
    },

    backHome(){
      // 返回首页
      this.$router.back()
    },

    updateUser(){
      // 更新用户信息
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #dcdcdc;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
</style>
<style scoped>
.new-avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.userInfo-container {
  position: relative;
  width: 100%;
  /*height: 680px;*/
  height: auto;
  padding-top: 3%;
  padding-bottom: 3%;
  background: #f6f6f6;
  overflow:hidden;
  box-sizing: border-box;
}

.item-list{
  float: left;
  margin-left: 3%;
  /*margin-right: 1%;*/
  padding-top: 5%;
  width: 15%;
  /*border: 2px solid #00B7FF ;*/
  box-sizing: border-box;
}

.signOut_btn{
  float: left;
}

.backHome_btn{
  float: left;
}

.detail{
  float: right;
  width: 80%;
  /*height: 90%;*/
  height: auto;
  min-height: 680px;
  padding: 30px;
  /*margin-bottom: 3%;*/
  background: white;
  box-sizing: border-box;
}

.item{
  cursor: pointer;
  margin-bottom: 18%;
  display: flex;
  justify-content: left;
  font-size: 22px;
}
.item-activated{
  color: #1482f0;
}
.avatar{
  height: 120px;
  width: 120px;
  border: 1px solid #e0e0e0;
  border-radius: 60px;
  margin-bottom: 20px;
}
.confirm-btn{
  margin-top: 20px;
}
.home{
  font-size: 18px;
  position: absolute;
  left: 20px;
  top: 20px;
  cursor: pointer;
}
.back-text{
  color: #409EFF;
}
</style>