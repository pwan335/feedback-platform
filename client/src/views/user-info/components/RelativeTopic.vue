<template>
  <div class="container">
    <el-tabs type="border-card" @tab-click="changeTab">
      <el-tab-pane label="Collect">
        <div class="no-data" v-if="topicInfo.length==0">No relevant data</div>
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
            <el-image v-if="!item.collectState" :src="require('../../../assets/home_images/collect0.png')" class="comment-icon" />
            <el-image v-else :src="require('../../../assets/home_images/collection.png')" class="comment-icon"  />
            <span class="count">{{item.collectNum}}</span>
            <el-image :src="require('../../../assets/home_images/comment.png')" class="comment-icon" />
            <span class="count">{{item.commentNum}}</span>
            <el-image v-if="!item.likeState" :src="require('../../../assets/home_images/like0.png')" class="comment-icon"/>
            <el-image v-else :src="require('../../../assets/home_images/like.png')" class="comment-icon" />
            <span class="count">{{item.likeNum}}</span>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Like">
        <div class="no-data" v-if="topicInfo.length==0">No relevant data</div>
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
            <el-image v-if="!item.collectState" :src="require('../../../assets/home_images/collect0.png')" class="comment-icon" />
            <el-image v-else :src="require('../../../assets/home_images/collection.png')" class="comment-icon"  />
            <span class="count">{{item.collectNum}}</span>
            <el-image :src="require('../../../assets/home_images/comment.png')" class="comment-icon" />
            <span class="count">{{item.commentNum}}</span>
            <el-image v-if="!item.likeState" :src="require('../../../assets/home_images/like0.png')" class="comment-icon"/>
            <el-image v-else :src="require('../../../assets/home_images/like.png')" class="comment-icon" />
            <span class="count">{{item.likeNum}}</span>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Comment">
        <div class="no-data" v-if="topicInfo.length==0">No relevant data</div>
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
            <el-image v-if="!item.collectState" :src="require('../../../assets/home_images/collect0.png')" class="comment-icon" />
            <el-image v-else :src="require('../../../assets/home_images/collection.png')" class="comment-icon"  />
            <span class="count">{{item.collectNum}}</span>
            <el-image :src="require('../../../assets/home_images/comment.png')" class="comment-icon" />
            <span class="count">{{item.commentNum}}</span>
            <el-image v-if="!item.likeState" :src="require('../../../assets/home_images/like0.png')" class="comment-icon"/>
            <el-image v-else :src="require('../../../assets/home_images/like.png')" class="comment-icon" />
            <span class="count">{{item.likeNum}}</span>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getTopicByCollected, getTopicByLike, getTopicByComment } from '@/api/TopicInfo'
import {getImageHost, utc2beijing} from "@/utils";

export default {
  name: "RelativeTopic",
  data() {
    return {
      topicInfo: []
    }
  },
  created() {
    this.getCollected()
  },
  methods: {
    formatUrl(url) {
      return getImageHost() + url
    },

    formatTime(time) {
      return utc2beijing(time)
    },

    changeTab(tab) {
      let index = tab.index
      if(index === '0') {
        this.getCollected()
      } else if(index === '1') {
        this.getLike()
      } else if(index === '2') {
        this.getComment()
      }
    },

    toDetails(item) {
      localStorage.setItem('target', JSON.stringify(item))
      this.$router.push('details')
    },

    async getCollected() {
      try {
        const res = await getTopicByCollected()
        if(res.code === 20011) {
          this.topicInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async getLike() {
      try {
        const res = await getTopicByLike()
        if(res.code === 20011) {
          this.topicInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async getComment() {
      try {
        const res = await getTopicByComment()
        if(res.code === 20011) {
          this.topicInfo = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    }
  }
}
</script>
<style>
.el-tab-pane{
  height: 600px;
  overflow: auto;
}
.el-tabs--border-card{
  margin: -30px;
}
</style>
<style scoped>
div{
  text-align: left;
}
.no-data{
  color: #8c939d;
  font-size: 18px;
  text-align: center;
  margin-top: 50px;
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
.avatar-icon{
  width: 45px;
  height: 45px;
  border-radius: 22.5px;
  border: 1px solid #e0e0e0;
  margin-right: 10px;
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