<template>
  <div class="container">
    <div class="navigation">
      <el-image class="navigation-bg" :src="require('../../assets/home_images/bg.jpg')" />
    </div>
    <div class="second-part">
      <div class="search-topic">
        <div class="search-title">Search Topic</div>
        <div class="search-line">
          <el-input v-model="topicName" />
          <el-button type="primary">Search</el-button>
        </div>
        <div class="recommend-topics">
          <div v-for="(item, index) in recommendTopics" :key="index" class="recommend-item">{{item.name}}</div>
        </div>
      </div>
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
              <div>{{userInfo.collections}}</div>
            </div>
            <div class="comment">
              <el-image :src="require('../../assets/home_images/comment.png')" class="icon" />
              <div>{{userInfo.comments}}</div>
            </div>
            <div class="like">
              <el-image :src="require('../../assets/home_images/like.png')" class="icon" />
              <div>{{userInfo.like}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="third-part">
      <div class="topic-docker">
        <div class="top-btn" v-show="isLogin">
          <el-button type="primary" size="mini">Recommend</el-button>
          <el-button type="primary" size="mini">My Comments</el-button>
          <el-button type="primary" size="mini">My Star</el-button>
        </div>
        <div class="topic-item" v-for="(item, index) in topicInfo" :key="index">
          <div class="line1">
            <el-image :src="require(`../../assets/home_images/${item.avatar}.png`)" class="icon"  />
            <div class="topic-des">
              <div>{{item.userName}}</div>
              <div>{{item.time}}</div>
            </div>
          </div>
          <div @click="toDetails(item)">
            <div class="topic-title">{{item.title}}</div>
            <div class="topic-content">{{item.describe}}</div>
          </div>
          <div>
            <el-image :src="require('../../assets/home_images/collection.png')" class="comment-icon" />
            <span>
              <el-image :src="require('../../assets/home_images/comment.png')" class="comment-icon" />
              <span>80</span>
            </span>
            <span>
              <el-image :src="require('../../assets/home_images/like.png')" class="comment-icon" />
              <span>191</span>
            </span>
          </div>
        </div>
      </div>
      <div class="hot-topics">
        <div class="hot-topic-title">Hot topics</div>
        <div v-for="(item, index) in hotTopics" :key="index" class="hot-topic-item">
         {{index+1}}. {{item.title}}
        </div>
      </div>
    </div>
    <login-page v-model="showLogin" @loginSuccess="loginSuccess" />
  </div>
</template>

<script>
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
      recommendTopics: [
          { id: 1, name: 'Google Maps, AR wayfindingGoogle Maps, AR wayfindingGoogle Maps, AR wayfinding' },
          { id: 1, name: 'Google Maps, AR wayfinding' },
          { id: 1, name: 'Google Maps, AR wayfinding' },
          { id: 1, name: 'Google Maps, AR wayfinding' },
          { id: 1, name: 'Google Maps, AR wayfinding' },
          { id: 1, name: 'Google Maps, AR wayfinding' }
      ],
      userInfo:{
        avatar: 'man',
        userName: 'Alex',
        collections: 10,
        comments: 20,
        like: 3
      },
      topicInfo: [
        {
          userName: 'Alex',
          avatar: 'woman',
          title: 'Title Title Title Title Title Title Title Title',
          time: '2022-09-12',
          describe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.'
        },
        {
          userName: 'Bob',
          avatar: 'man',
          title: 'Title Title Title Title Title Title Title Title',
          time: '2022-09-12',
          describe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.'
        },
        {
          userName: 'Lisa',
          avatar: 'woman',
          title: 'Title Title Title Title Title Title Title Title',
          time: '2022-09-12',
          describe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.'
        },
        {
          userName: 'petter',
          avatar: 'man',
          title: 'Title Title Title Title Title Title Title Title',
          time: '2022-09-12',
          describe: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.'
        }
      ],

      hotTopics: [
        { id: 1, title: 'topic1' },
        { id: 2, title: 'topic2' },
        { id: 3, title: 'topic3' },
        { id: 4, title: 'topic4' },
        { id: 5, title: 'topic5' },
        { id: 6, title: 'topic6' },
        { id: 7, title: 'topic7' },
        { id: 8, title: 'topic8' },
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
    loginSuccess() {
      this.isLogin = true
      // get userInfo
      // todo
    },

    toDetails(item) {
      this.$router.push('details')
    }
  }
}
</script>

<style scoped>
.navigation-bg{
  width: 100%;
}
.second-part{
  display: flex;
  justify-content: space-between;
}
.search-topic{
  box-sizing: border-box;
  width: 69%;
  height: 280px;
  padding: 20px 40px;
  background-color: #FFF;
}
.search-title{
  font-size: 24px;
  font-weight: 600;
  text-align: left;
}
.search-line{
  display: flex;
}
.search-line>.el-input{
  width: 600px;
  margin-right: 20px;
}
.recommend-topics{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  width: 600px;
}
.recommend-item{
  width: 49%;
  margin-top: 20px;
  color: #409EFF;
  text-align: left;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
  font-size: 20px;
}
.user-info{
  width: 30%;
  height: 280px;
  background-color: #FFF;
  box-sizing: border-box;
  padding: 20px 20px 0 20px;
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
  width: 30%;
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
  width: 100px;
}
.topic-title{
  text-align: left;
  padding-left: 20px;
  font-weight: 700;
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
}
.top-btn {
  height: 50px;
  line-height: 50px;
}
.comment-icon{
  width: 25px;
  height: 25px;
}
</style>