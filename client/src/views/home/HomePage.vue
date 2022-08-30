<template>
  <div class="container">
    <div class="header">
      <div class="navigation">
        <div class="userinfo">
<!--          <span class="el-icon-s-custom avatar"></span>-->
          <span>{{userName}}</span>
        </div>
        <div class="menus">
          <span :class="['menu-item', activateIndex==0?'menu-item-activated':'']" @click="handleClick(0)">MainPage</span>
          <span :class="['menu-item', activateIndex==1?'menu-item-activated':'']" v-if="logged"  @click="handleClick(1)">Sign-out</span>
          <span :class="['menu-item', activateIndex==2?'menu-item-activated':'']" v-else  @click="handleClick(2)">Sign-in</span>
          <span :class="['menu-item', activateIndex==3?'menu-item-activated':'']"  @click="handleClick(3)">ChekOut</span>
          <span :class="['menu-item', activateIndex==4?'menu-item-activated':'']"  @click="handleClick(4)">Profile</span>
        </div>
      </div>
      <div class="sub-title" v-show="activateIndex!==0">SellPhone</div>
      <div class="filter-bar" v-show="activateIndex===0">
        <div class="title">SellPhone</div>
        <div class="search-field">
          <el-input
              placeholder="Search Phone"
              prefix-icon="el-icon-search"
              v-model="keyWords">
          </el-input>
          <el-button @click="startSearch" type="primary" class="search-btn">Search</el-button>
        </div>
      </div>
    </div>

    <div class="content">
      <main-page v-if="activateIndex===0" ref="mainpage" @switchTab="switchTabs" :key-word="keyWords" />
      <login-page v-else-if="activateIndex===2" @switchTab="switchTabs" @login="login" />
      <user-info v-else-if="activateIndex===4" @switchTab="switchTabs" @signOut="signOut" @updateUser="updateUser" />
      <check-out v-else-if="activateIndex===3" @switchTab="switchTabs" />
    </div>
  </div>
</template>

<script>
import MainPage from "@/views/main-page/MainPage";
import LoginPage from "@/views/login/LoginPage";
import UserInfo from "@/views/user-info/UserInfo";
import CheckOut from "@/views/check-out/Checkout";
export default {
  name: "HomePage",
  components: {MainPage, CheckOut, UserInfo, LoginPage},
  data() {
    return {
      userName: '',
      // activated menu
      activateIndex: 0,
      preactiveIndex: 0,
      // login status
      logged: false,
      keyWords: ''
    }
  },
  created() {
    const user = sessionStorage.getItem('user')
    if(user) {
      this.logged = true
      this.userName = JSON.parse(user).firstname
    }

    // jump to the login page after resetting password
    const queryInfo = this.$route.params
    if(queryInfo && queryInfo.resetPassword) {
      this.activateIndex = 2

    }
  },
  methods: {
    updateUser() {
      const user = sessionStorage.getItem('user')
      if(user) {
        this.userName = JSON.parse(user).firstname
      }
    },
    //
    handleClick(cmd) {
      if(cmd === 3) {
        // record the data to be returned to the details page
        if( this.$refs['mainpage'] && this.$refs['mainpage'].$data.status === 'details' ) {
          this.$refs['mainpage'].setBackInfo()
        }
      }
      if(cmd!==1) {
        this.preactiveIndex = this.activateIndex
        this.activateIndex = cmd
      } else if(cmd === 1) {
        // sign out
        this.signOut()
      }
      // initialization date
      this.keyWords = ''
    },

    // switch page
    switchTabs(index) {
      // go back to the last page
      if(index === -1) {
        this.activateIndex = this.preactiveIndex
      } else {
        this.activateIndex = index
      }
    },

    // filter data
    startSearch() {
        this.$refs['mainpage'].filterData()
    },

    // handle login
    login(user) {
      if(user) {
        this.logged = true
        this.userName = user.firstname
        this.switchTabs(0)
      } else {
        this.logged = false
      }
    },

    signOut() {
      this.$confirm('Do you want to log out?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.logged = false
        this.userName = ''
        this.activateIndex = 0;
        sessionStorage.clear()
        this.$message({
          type: 'success',
          message: 'Log out successfully'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Log out canceled'
        });
      });
    }
  }
}
</script>

<style scoped>
.header{
  padding: 20px;
  /*background: linear-gradient(to bottom, #6CC6CB, #EAE5C9);*/
  background: linear-gradient(to bottom, rgba(153, 184, 248, 0.99), #ffffff);
}
.navigation{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
}
.userinfo{
  float: right;
  height: 50px;
  line-height: 50px;
  font-size: 26px;
}
.avatar{
  margin-right: 10px;
  background-color: #FFF;
  border-radius: 50%;
}
.menu-item{
  display: inline-block;
  width: 100px;
  text-align: center;
  cursor: pointer;
}
.menu-item:hover{
  color: #1482f0;
}
.menu-item-activated{
  color: #1482f0;
}
.filter-bar{
  display: flex;
  align-items: center;
  justify-content: center;
}
.title{
  display: inline-block;
  font-size: 42px;
  font-weight: 600;
  color: #3e5df8;
  margin-right: 30px;
  width: 200px;
}

.sub-title{
  margin-left: 10%;
  font-size: 36px;
  font-weight: 600;
  color: #3e5df8;
  /*margin-right: 30px;*/
  width: 200px;
}

.search-field{
  width: 600px;
  display: flex;
}
.search-btn{
  margin-left: 20px;
}
.content{
  width: 1504px;
  margin: 0 auto;
}
</style>