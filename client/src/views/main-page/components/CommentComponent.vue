<template>
  <div class="comment-container">
    <div class="comment-title">
      <span class="el-icon-chat-dot-square comment-icon"></span>
      <span>Reviews</span>
    </div>
    <div class="comment-item" v-for="(item, index) in currentReviews" :key="index">
      <div class="comment-left">
        <div>{{ item.reviewer.firstname + ' ' + item.reviewer.lastname }}</div>
        <div>
          <el-rate
              v-model="item.rating"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
          </el-rate>
        </div>
      </div>
      <div class="comment-right" v-if="item.comment.length>200 && !item.showAll">
        {{ getLimitReview(item.comment) }}...
        <span class="showmoredetail" @click="showMoreDetail(index)">show all</span>
      </div>
      <div class="comment-right" v-else>
        {{ item.comment }}
      </div>
    </div>
    <div class="no-data" v-if="reviews.length===0">
      There are no comments here yet
    </div>
    <div class="load-more" v-if="currentReviews.length<reviews.length" @click="loadMore">load more >></div>
    <div class="comment-add">
      <div class="comment-title">
        <span class="el-icon-edit-outline comment-icon"></span>
        <span>Add comment</span>
      </div>
      <div>
        <el-rate
            v-model="review.rating"
            text-color="#ff9900">
        </el-rate>
      </div>
      <div class="input-field">
        <el-input v-model="review.comment" class="comment-input" type="textarea" :rows="5"/>
        <el-button type="primary" @click="addComment">post</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { addReview } from "@/api/user";
import Vue from "vue";

export default {
  name: "CommentComponent",
  props: {
    reviews: {
      type: Array,
      default() {
        return []
      }
    },
    targetInfo:{
      type: Object,
      default() {
        return {};
      }
    },
    tartgetId: {
      type: String,
      default: ''
    }
  },
  model:{
    prop: 'reviews',
    event: 'changeReview'
  },
  data() {
    return {
      review: {
        rating: 0,
        comment: ''
      },
      currentReviews:[],
      pageIndex: 1,
    }
  },
  created() {
    this.reviewPager()
  },
  methods: {
    getLimitReview(review) {
      return review.substring(0, 200)
    },

    showMoreDetail(index) {
      let temp = Object.assign({}, this.currentReviews[index])
      temp['showAll'] = true
      Vue.set(this.currentReviews, index, temp)
      this.currentReviews[index] = temp
      console.log(this.currentReviews[index])
    },

    changeReview(review) {
      let temp = this.reviews.concat()
      temp.push(review)
      this.$emit('changeReview', temp)
      this.$nextTick(()=>{
        this.reviewPager()
      })
    },

    reviewPager() {
      let count = this.pageIndex * 3
      if(count>this.reviews.length) {
        count = this.reviews.length
      }
      this.currentReviews = this.reviews.slice(0, count)
    },

    loadMore() {
      this.pageIndex += 1
      this.reviewPager()
    },

    addComment() {
      // check if the user is logged in
      let user = sessionStorage.getItem('user')
      if(user) {
        user = JSON.parse(user)
        const reqModel = {
          id: this.tartgetId,
          review: this.review
        }
        addReview(reqModel).then(res=>{
          if(res.success) {
            this.$message.success(res.message)
            let review = {
              comment: this.review.comment,
              rating: this.review.rating,
              reviewer: user
            }
            this.changeReview(review)
            // initialization data
            this.review.rating = 0
            this.review.comment = ''
          } else {
            this.$message.error(res.message)
          }
        }, err =>{
          console.log(err)
          this.$message.error('Failed to comment')
        })
      } else {
        // go to login page
        this.$message.warning('please log in first!')
        let queryData = {
          path: 'details',
          data: this.targetInfo
        }
        sessionStorage.setItem('querydata', JSON.stringify(queryData))
        this.$emit('switchTab', 2)
      }
    }
  }
}
</script>

<style scoped>
.comment-container {
  text-align: left;
}

.comment-title > span {
  display: inline-block;
  margin-right: 10px;
}

.comment-title {
  display: flex;
  align-items: center;
  margin: 20px 0;
}

.comment-icon {
  color: #1482f0;
  font-size: 26px;
}

.comment-item {
  display: flex;
  margin-bottom: 30px;
  align-items: center;
}

.comment-left {
  width: 250px;
  line-height: 40px;
}

.comment-right {
  border: 1px solid #dbdbdb;
  padding: 20px;
  width: 500px;
  height: 100px;
  overflow: auto;
  text-align: left;
}
.comment-input{
  width: 500px;
}
.input-field{
  display: flex;
  align-items: center;
}
.input-field .el-button{
  margin-left: 20px;
}
.no-data{
  color: #7d8492;
  text-align: center;
}
.load-more{
  cursor: pointer;
  color: #1d68ff;
  text-align: center;
}
.showmoredetail{
  display: inline-block;
  color: #1d68ff;
  margin: 0 5px;
  cursor: pointer;
}
</style>