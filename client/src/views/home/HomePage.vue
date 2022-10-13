<template>
  <div class="container">
    <div class="navigation">
      <div class="weather">
        <div>City: {{city}}</div>
        <div>Weather: {{weather.temperature}}℃, {{weather.info}},  {{weather.direct}}</div>
      </div>
      <div class="web-title">Easy feedback</div>
      <el-image class="navigation-bg" :src="require('../../assets/home_images/bg.jpg')" />
    </div>

    <div class="third-part">
      <div class="topic-docker">
        <div class="search-line">
          <el-input v-model="topicName" clearable />
          <el-button type="primary" @click="searchTopic">Search</el-button>
        </div>
        <div class="top-btn" v-show="isLogin">
          <el-button type="primary" size="mini" @click="getLatest">Latest</el-button>
          <el-button type="primary" size="mini" @click="getRecommend">Recommend</el-button>
        </div>
        <div class="topic-item" v-for="(item, index) in topicInfo" :key="index">
          <div class="line1">
            <el-image :src="formatUrl(item.pmAvatar)" class="avatar-icon"  />
            <div class="topic-des">
              <div class="pm-name">{{item.pmName}}</div>
              <div>{{formatTime(item.createTime)}}</div>
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
        <div class="load-more" v-if="total>topicInfo.length">
          <span @click="loadMore">Load more >></span>
        </div>
      </div>
      <div style="width: 30%">
        <div class="user-info">
          <div v-if="!isLogin" class="un-login">
            <div class="login-tips">Login to find more...</div>
            <el-button @click="handleLogin">click here</el-button>
          </div>
          <div v-else style="position:relative;">
            <el-button class="edit-btn" type="text" @click="userCenter">user center</el-button>
            <div style="text-align: center">
              <el-image :src="formatUrl(userInfo.avatar)" class="avatar" />
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
          <div v-for="(item, index) in hotTopics" :key="index" class="hot-topic-item" @click="toDetails(item)">
            {{index+1}}. {{item.topicName}}
          </div>
        </div>
      </div>
    </div>
    <login-page v-model="showLogin" @loginSuccess="loginSuccess" />
  </div>
</template>

<script>
import { getImageHost, utc2beijing } from "@/utils";
import { getUserInfo, getUserData } from "@/api/user";
import { getLatestTopic, getHotTopic, getTopicByName } from '@/api/TopicInfo'
import LoginPage from "./components/LoginPage";

import { getCityByIP } from "@/api/publicApi";
import { weatherInfo } from "@/api/weather";

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
      // 0 search 1 latest 2 recomment
      getMode: 0,
      total: 0,
      userInfo:{},
      userData: {
        collectNum: 0,
        commentNum: 0,
        likeNum: 0
      },
      topicInfo: [],

      hotTopics: [],

      city: '北京',

      weather: {
        aqi: "130",
        direct: "北风",
        humidity: "77",
        info: "阴",
        power: "2级",
        temperature: "12",
        wid: "02",
      }
    }
  },
  created() {
    let userInfo = localStorage.getItem('userInfo')
    if(userInfo) {
      this.isLogin = true
      this.userInfo = JSON.parse(userInfo)
      this.getData()
    } else {
      this.isLogin = false
    }
    this.searchTopic()
    this.getHot()
    // this.getWeather()
  },
  methods: {
    async getWeather() {
     try {
       const res = await getCityByIP()
       if(res.status == 200) {
         console.log(res.data)
         let result = res.data.split(' ')
         const reg = /\"(.*?)\"/
         result = reg.exec (result[result.length-1]);
         let city = result[1].split('市')[0]
         this.city = city
         weatherInfo({city, key: '7b7a794432d3fa3c89947d7d05b87f4c'}).then(res=>{
           this.weather = res.result.realtime
         })
       }
     } catch (err) {
       console.log(err)
     }

    },

    formatUrl(url) {
      return getImageHost() + url
    },

    formatTime(time) {
      return utc2beijing(time)
    },

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
          localStorage.setItem('userInfo', JSON.stringify(res.data))
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
      await this.getData()
    },

    async getData() {
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

    async searchTopic(loadMore) {
      if(loadMore!==true) {
        this.pageNum = 1
      }
      const reqModel = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        topicName: this.topicName,
      }
      try {
        const res = await getTopicByName(reqModel)
        if(res.code === 20011) {
          this.total = res.data.total
          if(loadMore===true) {
            this.topicInfo = this.topicInfo.concat(res.data.list)
          } else {
            this.topicInfo = res.data.list
          }
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    loadMore() {
      this.pageNum += 1
      if(this.getMode===0) {
        this.searchTopic(true)
      } else if(this.getMode===1) {
        this.getLatest(true)
      } else if(this.getMode===2) {
        this.getRecommend(true)
      }
    },

    async getLatest(loadMore) {
      if(loadMore!==true) {
        this.pageNum = 1
      }
      try {
        const res = await getLatestTopic({pageNum: this.pageNum, pageSize: this.pageSize})
        if(res.code === 20011) {
          if(loadMore!==true) {
            this.topicInfo = res.data.list
          } else {
            this.topicInfo = this.topicInfo.concat(res.data.list)
          }
          this.total = res.data.total
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    async getRecommend(loadMore) {
      if(loadMore!==true) {
        this.pageNum = 1
      }
      try {
        const res = await getHotTopic({pageNum: this.pageNum, pageSize: this.pageSize})
        if(res.code === 20011) {
          this.total = res.data.total
          if(loadMore!==true) {
            this.topicInfo = res.data.list
          } else {
            this.topicInfo = this.topicInfo.concat(res.data.list)
          }
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    async getHot() {
      const reqModel = {
        pageNum: 1,
        pageSize: 10
      }
      try {
        const res = await getHotTopic(reqModel)
        if(res.code === 20011) {
          this.hotTopics = res.data.list
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    toDetails(item) {
      localStorage.setItem('target', JSON.stringify(item))
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
.navigation{
  position: relative;
}
.weather {
  position: absolute;
  left: 10px;
  top: 10px;
  z-index: 99;
  color: #937e22;
}
.web-title {
  position: absolute;
  top: 100px;
  left: 200px;
  font-size: 56px;
  color: #bcbb7d;
  z-index: 99;
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
  border-radius: 50px;
  border: 1px solid #e0e0e0;
}
.user-name{
  text-align: center;
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

.avatar-icon{
  width: 45px;
  height: 45px;
  border-radius: 22.5px;
  border: 1px solid #e0e0e0;
  margin-right: 10px;
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
  background-color: #f6faff;
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
.load-more{
  cursor: pointer;
  color: #409EFF;
  text-align: center;
  margin-top: 10px;
}
.un-login{
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  height: 100%;
}
.un-login>div{
  width: 100%;
  text-align: center;
}
</style>