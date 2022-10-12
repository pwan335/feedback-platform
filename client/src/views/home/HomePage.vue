<template>
  <div class="container">
    <div class="navigation">
      <el-image class="navigation-bg" :src="require('../../assets/home_images/bg.jpg')" />
    </div>

    <div class="third-part">
      <div class="topic-docker">
        <div class="search-line">
          <el-input v-model="topicName" />
          <el-button type="primary">Search</el-button>
        </div>
        <div class="top-btn" v-show="isLogin">
          <el-button type="primary" size="mini" @click="getLatest">Latest</el-button>
          <el-button type="primary" size="mini" @click="getRecommend">Recommend</el-button>
        </div>
        <div class="topic-item" v-for="(item, index) in topicInfo" :key="index">
          <div class="line1">
            <el-image :src="require(`../../assets/home_images/${item.avatar}.png`)" class="icon"  />
            <div class="topic-des">
              <div class="pm-name">{{item.pmName}}</div>
              <div>{{item.createTime}}</div>
            </div>
          </div>
          <div @click="toDetails(item)">
            <div class="topic-title">{{item.topicName}}</div>
            <div class="topic-content">{{item.content}}</div>
          </div>
          <div class="icons">
            <el-image v-if="!item.collectState" :src="require('../../assets/home_images/collect0.png')" class="comment-icon" />
            <el-image v-else :src="require('../../assets/home_images/collection.png')" class="comment-icon"  />
            <span class="count">{{item.collectNum}}</span>
            <el-image :src="require('../../assets/home_images/comment.png')" class="comment-icon" />
            <span class="count">{{item.commentNum}}</span>
            <el-image v-if="!item.likeState" :src="require('../../assets/home_images/like0.png')" class="comment-icon"/>
            <el-image v-else :src="require('../../assets/home_images/like.png')" class="comment-icon" />
            <span class="count">{{item.likeNum}}</span>
          </div>
        </div>
      </div>
      <div style="width: 30%">
        <div class="user-info">
          <div v-if="!isLogin">
            <div class="login-tips">Login to find more...</div>
            <el-button @click="handleLogin">click here</el-button>
          </div>
          <div v-else style="position:relative;">
            <el-button class="edit-btn" type="text" @click="userCenter">user center</el-button>
            <div>
              <el-image :src="require(`../../assets/home_images/${userInfo.avatar}.png`)" class="avatar" />
            </div>
            <div class="user-name">{{userInfo.userName}}</div>
            <div class="statistic">
              <div class="collection">
                <el-image :src="require('../../assets/home_images/collection.png')" class="icon" />
                <div>{{userData.collectNum}}</div>
              </div>
              <div class="comment">
                <el-image :src="require('../../assets/home_images/comment.png')" class="icon" />
                <div>{{userData.commentNum}}</div>
              </div>
              <div class="like">
                <el-image :src="require('../../assets/home_images/like.png')" class="icon" />
                <div>{{userData.likeNum}}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="hot-topics">
          <div class="hot-topic-title">Hot topics</div>
          <div v-for="(item, index) in hotTopics" :key="index" class="hot-topic-item">
            {{index+1}}. {{item.topicName}}
          </div>
        </div>
      </div>
    </div>
    <login-page v-model="showLogin" @loginSuccess="loginSuccess" />
  </div>
</template>

<script>
import { getUserInfo, getUserData } from "@/api/user";
import { getTopicByCollected, getTopicByLike, getTopicByComment, getLatestTopic, getHotTopic, getTopicByName } from '@/api/TopicInfo'
import LoginPage from "./components/LoginPage";
export default {
  name: "HomePage",
  components: {
    LoginPage
  },
  data() {
    return {
      isLogin: false,
      showLogin: false,
      topicName: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      userInfo:{
        "role": "user",
        "uid": 1,
        "userName": "apitestUser",
        "password": "abc123",
        "email": "948936249@qq.com",
        "address": "Auatralia",
        "phoneNumber": "110",
        "hobby": "唱跳，rap，篮球",
        "avatar": "man",
      },
      userData: {
        collectNum: 2,
        commentNum: 3,
        likeNum: 2
      },
      topicInfo: [
        {
          avatar: 'man',
          "topicId": 23,
          "topicName": "save topic api test",
          "content": "test 123",
          "createTime": "2022-10-12T05:18:47.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "apitestpm",
          "collectNum": 0,
          "collectState": false,
          "commentNum": 0,
          "likeNum": 0,
          "likeState": false,
          "images": [
            "/pic1.png",
            "/pic2.png"
          ]
        },
        {
          avatar: 'man',
          "topicId": 22,
          "topicName": "save topic api test",
          "content": "test 123",
          "createTime": "2022-10-12T05:17:31.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "apitestpm",
          "collectNum": 0,
          "collectState": false,
          "commentNum": 0,
          "likeNum": 1,
          "likeState": true,
          "images": []
        }
      ],

      hotTopics: [
        {
          "topicId": 2,
          "topicName": "The best way to study2",
          "content": "read mroe, listen more and speak more!",
          "createTime": "2022-01-20T03:11:11.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "",
          "collectNum": 1,
          "collectState": false,
          "commentNum": 3,
          "likeNum": 1,
          "likeState": false,
          "images": [
            "/pic1.png",
            "/pic2.png"
          ]
        },
        {
          "topicId": 1,
          "topicName": "The best way to study",
          "content": "read mroe, listen more and speak more!",
          "createTime": "2022-01-19T02:15:00.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "",
          "collectNum": 1,
          "collectState": false,
          "commentNum": 0,
          "likeNum": 0,
          "likeState": false,
          "images": []
        },
        {
          "topicId": 8,
          "topicName": "save topic test",
          "content": "test is successful",
          "createTime": "2022-10-11T01:22:01.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "",
          "collectNum": 0,
          "collectState": false,
          "commentNum": 0,
          "likeNum": 0,
          "likeState": false,
          "images": []
        },
        {
          "topicId": 13,
          "topicName": "save topic api test",
          "content": "test 123",
          "createTime": "2022-10-11T01:40:03.000+00:00",
          "updateTime": "",
          "pmId": 1,
          "pmName": "",
          "collectNum": 0,
          "collectState": false,
          "commentNum": 0,
          "likeNum": 0,
          "likeState": false,
          "images": []
        }
      ]
    }
  },
  created() {

  },
  methods: {
    handleLogin() {
      this.showLogin = true
    },
    userCenter() {
      this.$router.push('user-info')
    },
    async loginSuccess() {
      const role = localStorage.getItem('role')
      if(role=='pm') {
       await this.$router.push('user-info')
        return
      }
      this.isLogin = true
      // get userInfo
      try {
        const res = await getUserInfo()
        if(res.code==20011) {
          this.userInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
      try {
        const res = await getUserData()
        if(res.code==20011) {
          this.userData = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    async searchTopic() {
      this.pageNum = 1
    },

    async getLatest() {

    },

    async getRecommend() {

    },

    toDetails(item) {
      this.$router.push('details')
    }
  }
}
</script>

<style scoped>
div{
  text-align: left;
}
.container{
  font-size: 16px;
}
.navigation-bg{
  width: 100%;
}
.search-line{
  display: flex;
}
.search-line>.el-input{
  width: 600px;
  margin-right: 20px;
}
.user-info{
  height: 280px;
  background-color: #FFF;
  box-sizing: border-box;
  padding: 20px 20px 0 20px;
  margin-bottom: 20px;
}
.edit-btn{
  position: absolute;
  right: 0px;
  top: 0px;
}
.login-tips{
  height: 40px;
  line-height: 40px;
  font-size: 24px;
}
.avatar{
  height: 100px;
  width: 100px;
}
.user-name{
  font-size: 20px;
  font-weight: 600;
  margin: 10px 0;
}
.statistic{
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}
.collection, .like, .comment{
  display: flex;
  flex-direction: column;
  width: 80px;
  align-items: center;
}
.icon{
  width: 45px;
  height: 45px;
}

.third-part{
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
.topic-docker{
  width: 69%;
  min-height: 600px;
  background-color: #FFFFFF;
  padding: 20px;
  box-sizing: border-box;
}
.hot-topics{
  height: 600px;
  box-sizing: border-box;
  background-color: #FFFFFF;
}
.topic-item{
  cursor: pointer;
  border-bottom: 1px solid #d0cfd0;
  padding: 20px;
}
.topic-item:hover{
  background-color: #e8e8e8;
}
.line1{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 10px;
}
.topic-des{
  display: inline-block;
  width: 500px;
}
.pm-name{
  font-weight: 700;
  margin-bottom: 10px;
}
.topic-title{
  font-size: 20px;
  text-align: left;
  padding-left: 20px;
  font-weight: 700;
  margin-bottom: 10px;
}
.topic-content{
  text-align: left;
  padding-left: 20px;
}
.hot-topics{
  padding: 20px;
}
.hot-topic-title {
  text-align: left;
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 20px;
}
.hot-topic-item {
  color: #409EFF;
  font-size: 20px;
  height: 40px;
  line-height: 40px;
  text-align: left;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}
.top-btn {
  height: 50px;
  line-height: 50px;
}
.icons{
  margin-top: 10px;
  display: flex;
  align-items: center;
}
.comment-icon{
  width: 25px;
  height: 25px;
}
.count{
  margin-right: 20px;
  margin-left: 5px;
}
</style>