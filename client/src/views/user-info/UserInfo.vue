<template>
  <div class="container">
    <div class="userInfo-container">
      <div class="item-list">
        <span :class="['item', activetedIndex==1?'item-activated':'']" @click="showForm(1)">Edit Profile</span>
        <span :class="['item', activetedIndex==2?'item-activated':'']" @click="showForm(2)">Change Password</span>
        <span :class="['item', activetedIndex==3?'item-activated':'']" @click="showForm(3)">Manage Listings</span>
        <span :class="['item', activetedIndex==4?'item-activated':'']" @click="showForm(4)">View Comments</span>
        <div class="signOut_btn">
          <el-button type="danger" size="small" plain @click="signOut">Sign-out</el-button>
        </div>
      </div>
      <div class="detail" v-if="showProfile">
        <profile-component @updateUser="updateUser"/>
      </div>
      <div class="detail" v-if="showPassword">
        <password-component />
      </div>
      <div class="detail" v-if="showList">
        <listing-component />
      </div>
      <div class="detail" v-if="showComments">
        <comment-component />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileComponent from "@/views/user-info/components/ProfileComponent";
import PasswordComponent from "@/views/user-info/components/PasswordComponent";
import ListingComponent from "@/views/user-info/components/ListingComponent";
import CommentComponent from "@/views/user-info/components/CommentComponent";

export default {
  name: "UserInfo",
  components: {
    PasswordComponent,
    ProfileComponent,
    ListingComponent,
    CommentComponent
  },
  data() {
    return {
      showProfile: true,
      showPassword: false,
      showList: false,
      showComments: false,
      activetedIndex: 1
    }
  },
  created() {
    const user = sessionStorage.getItem('user')
    if(!user) {
      this.$message.warning('Please log in first')
      // record the page to return after successful login
      let queryData = {
        path: 'profile',
      }
      sessionStorage.setItem('querydata', JSON.stringify(queryData))
      this.$emit('switchTab',2);
    }
  },
  methods:{
    showForm(item){
      this.activetedIndex = item;
      if(item == 1){
        this.showProfile = true;
        this.showPassword= false;
        this.showList = false;
        this.showComments = false;
      }else if(item == 2){
        this.showProfile = false;
        this.showPassword= true;
        this.showList = false;
        this.showComments = false;
      }else if(item == 3){
        this.showProfile = false;
        this.showPassword= false;
        this.showList = true;
        this.showComments = false;
      }else if(item == 4){
        this.showProfile = false;
        this.showPassword= false;
        this.showList = false;
        this.showComments = true;
      }
    },
    signOut(){
      this.$emit('signOut')
    },

    updateUser(){
      this.$emit('updateUser')
    }
  }
}
</script>

<style scoped>

.userInfo-container {
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

.detail{
  float: right;
  width: 80%;
  /*height: 90%;*/
  height: auto;
  min-height: 680px;
  padding-top: 5%;
  padding-left: 8%;
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

</style>