<template>
  <div class="container">
    <div class="go-back">
      <el-button @click="goBack">back</el-button>
    </div>
    <div class="line1">
      <el-image :src="require(`../../assets/home_images/${itemInfo.photo}.png`)" class="avatar" />
      <div>
        <div class="user-name">{{itemInfo.pmName}}</div>
        <div class="topic-time">{{itemInfo.createTime}}</div>
      </div>
    </div>
    <div class="topic-title">{{itemInfo.topicName}}</div>
    <div class="topic-content">{{itemInfo.content}}</div>
    <div class="operation">
      <div class="comment-input">
        <el-input v-model="comment" placeholder="write your opinion here" type="textarea" :rows="2" />
        <el-button type="primary" @click="addComment">submit</el-button>
      </div>
      <div class="icons">
        <el-image v-if="!itemInfo.collectState" :src="require('../../assets/home_images/collect0.png')" class="comment-icon" @click="postCollect" />
        <el-image v-else :src="require('../../assets/home_images/collection.png')" class="comment-icon" @click="deleteCollect" />
        <span class="count">{{itemInfo.collectNum}}</span>
        <el-image :src="require('../../assets/home_images/comment.png')" class="comment-icon" />
        <span class="count">{{itemInfo.commentNum}}</span>
        <el-image v-if="!itemInfo.likeState" :src="require('../../assets/home_images/like0.png')" class="comment-icon" @click="postLike" />
        <el-image v-else :src="require('../../assets/home_images/like.png')" class="comment-icon" @click="deleteLike" />
        <span class="count">{{itemInfo.likeNum}}</span>
      </div>
    </div>
    <el-divider />
    <div class="comment-box">
      <div>comments</div>
      <div v-for="(item, index) in commentsList" :key="index">
        <div class="line1">
          <el-image :src="require(`../../assets/home_images/${itemInfo.photo}.png`)" class="avatar" />
          <div>
            <div class="user-name">{{item.username}}</div>
          </div>
        </div>
        <div class="comment-content">{{item.content}}</div>
        <div class="comment-time">
          {{item.createTime}}
          <span class="reply-btn" @click="addReply(0, item)">reply</span>
        </div>
        <div class="reply-comment">
          <div class="reply-item" v-for="(reply, indicate) in item.replyList" :key="indicate">
            <div v-if="reply.toUname==item.username">{{reply.content}}</div>
            <div v-else>
              <span class="form-user">{{reply.fromUname}}</span>  Reply to <span class="to-user">@ {{reply.toUname}}: </span> {{reply.content}}
            </div>
            <div class="reply-time">
              {{reply.createTime}}
              <span v-if="reply.toUname==item.username" class="reply-btn" @click="addReply(1, reply)">reply</span>
            </div>
          </div>
        </div>
        <el-divider />
      </div>
    </div>

    <!--comment popup-->
    <el-dialog :visible.sync="showPop" title="comment">
      <div style="text-align: center">
        <el-input v-model="replyContent" class="reply-input" placeholder="write your opinion here" type="textarea" :rows="2" />
        <el-button type="primary" @click="addTreeComment">submit</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { collectTopic, deleteCollect, likeTopic, deleteLike, getComments, postComments, postTreeComments } from '@/api/TopicInfo'
export default {
  name: "TopicDetails",
  data() {
    return {
      showPop: false,
      replyContent: '',
      replyType: 0,
      replyTarget: null,
      comment: '',
      itemInfo: {
        photo: 'man',
        pmName: 'Alex',
        topicName: 'dvksv lk',
        createTime: '2022-10-01',
        content: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar sic tempor. Sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus pronin sapien nunc accuan eget.',
        collectNum: 1,
        collectState: false,
        commentNum: 3,
        likeNum: 1,
        likeState: false
      },
      commentsList: [
        {
          "id": 3,
          "topicId": 3,
          "role": "pm",
          "uid": 1,
          "username": "pm1",
          "content": "test comment1",
          "createTime": "2022-09-28T09:10:11.000+00:00",
          "replyList": [
            {
              "id": 1,
              "commentId": 3,
              "fromRole": "pm",
              "fromUid": 1,
              "fromUname": "pm1",
              "toRole": "user",
              "toUid": 1,
              "toUname": "user2",
              "replyType": "comment",
              "replyId": 1,
              "content": "reply test1",
              "createTime": "2022-01-19T14:22:22.000+00:00"
            },
            {
              "id": 3,
              "commentId": 3,
              "fromRole": "user",
              "fromUid": 1,
              "fromUname": "user2",
              "toRole": "pm",
              "toUid": 1,
              "toUname": "pm1",
              "replyType": "reply",
              "replyId": 1,
              "content": "reply test1",
              "createTime": "2022-01-19T14:22:22.000+00:00"
            }
          ]
        },
        {
          "id": 4,
          "topicId": 3,
          "role": "pm",
          "uid": 1,
          "username": "pm1",
          "content": "new api comment test111",
          "createTime": "2022-10-10T04:09:39.000+00:00",
          "replyList": [
            {
              "id": 2,
              "commentId": 4,
              "fromRole": "pm",
              "fromUid": 1,
              "fromUname": "pm1",
              "toRole": "user",
              "toUid": 1,
              "toUname": "user2",
              "replyType": "reply",
              "replyId": 3,
              "content": "new reply test111",
              "createTime": "2022-10-10T04:16:02.000+00:00"
            }
          ]
        }
      ],
      commentTarget: ''
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },

    async postCollect() {
      this.$set(this.itemInfo, 'collectState', true)
      this.$set(this.itemInfo, 'collectNum', this.itemInfo.collectNum+1)
      try {
        const res = await collectTopic({topicId: this.itemInfo.topicId})
        if(res.code=='20021') {
          this.$set(this.itemInfo, 'collectState', true)
          this.$set(this.itemInfo, 'collectNum', this.itemInfo.collectNum+1)
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async postLike() {
      this.$set(this.itemInfo, 'likeState', true)
      this.$set(this.itemInfo, 'likeNum', this.itemInfo.likeNum+1)
      try {
        const res = await likeTopic({topicId: this.itemInfo.topicId})
        if(res.code=='20021') {
          this.$set(this.itemInfo, 'likeState', true)
          this.$set(this.itemInfo, 'likeNum', this.itemInfo.likeNum+1)
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async deleteCollect() {
      this.$set(this.itemInfo, 'collectState', false)
      this.$set(this.itemInfo, 'collectNum', this.itemInfo.collectNum-1)
      try {
        const res = await deleteCollect({topicId: this.itemInfo.topicId})
        if(res.code=='20021') {
          this.$set(this.itemInfo, 'collectState', false)
          this.$set(this.itemInfo, 'collectNum', this.itemInfo.collectNum-1)
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },
    async deleteLike() {
      this.$set(this.itemInfo, 'likeState', false)
      this.$set(this.itemInfo, 'likeNum', this.itemInfo.likeNum-1)
      try {
        const res = await deleteLike({topicId: this.itemInfo.topicId})
        if(res.code=='20021') {
          this.$set(this.itemInfo, 'likeState', false)
          this.$set(this.itemInfo, 'likeNum', this.itemInfo.likeNum-1)
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    async getCommentsList() {
      try {
        const res = await getComments({id: this.itemInfo.topicId})
        if(res.code==='20011') {
          this.commentsList = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    async addComment() {
      if(!this.comment) {
        this.$message.warning('please enter your comment')
        return
      }
      const reqModel = { topicId: this.itemInfo.topicId, content: this.comment }
      try {
        const res = await postComments(reqModel)
        if(res.code=='20021') {
          await this.getCommentsList()
        } else {
          this.$message.error(res.msg)
        }
      } catch(err) {
        console.log(err)
      }
    },


    async addTreeComment() {
      if(!this.replyContent) {
        this.$message.warning('please enter your comment')
        return
      }
      let reqModel = {
        "commentId": this.replyTarget.id,
        "toRole": this.replyTarget.role,
        "toUid": this.replyTarget.uid,
        "replyType": "comment",
        "replyId": 3,
        "content": this.replyContent
      }
      if(this.replyType==0) {

      } else {
        reqModel = {
          "commentId": 4,
          "toRole": "user",
          "toUid": 1,
          "replyType": "reply",
          "replyId": 3,
          "content": this.replyContent
        }
      }
    },

    async addReply(type, target) {
      this.showPop = true
      this.replyType = type
      this.replyTarget = target
    },
    closePopup() {
      this.showPop = false
     }
  }
}
</script>

<style scoped>
div{
  text-align: left;
}
.container{
  font-size: 18px;
  background-color: #FFF;
  position: relative;
  padding: 150px;
}
.go-back{
  position: absolute;
  left:10px;
  top: 10px;
}
.line1{
  display: flex;
  align-items: center;
}
.avatar{
  height: 80px;
  width: 80px;
}
.user-name{
  font-weight: 700;
  margin-bottom: 4px;
}
.user-name, .topic-time{
  font-size: 18px;
}
.topic-title, .topic-content{
  text-align: left;
}
.topic-title {
  font-size: 20px;
  font-weight: 700;
  margin: 15px 0 10px 0;
}
.operation{
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.el-textarea{
  width: 400px;
  margin-right: 20px;
}
.comment-input{
  width: 60%;
}
.icons{
  width: 40%;
  display: flex;
  align-items: center;
}
.comment-icon{
  width: 25px;
  height: 25px;
}
.count{
  display: flex;
  margin-right: 20px;
  margin-left: 5px;
}
.comment-content{
  color: gray;
  font-size: 16px;
}
.reply-comment{
  padding-left: 70px;
  color: gray;
}
.form-user{
  display: inline-block;
  margin-right: 20px;
  color: #000;
}
.to-user{
  color: #409EFF;
  display: inline-block;
  margin-left: 10px;
  margin-right: 10px;
}
.reply-time{
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 14px;
}
.comment-time{
  color: gray;
  font-size: 16px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.reply-btn{
  display: inline-block;
  margin-left: 40px;
  color: #409EFF;
  cursor: pointer;
}
.el-divider--horizontal{
  height: 0.5px;
}
</style>