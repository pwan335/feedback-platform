<template>
  <div class="phoneListing-container">
    <div class="view_comment">
      <img class="comment_icon" src="../../../assets/userPage_images/list_review.png">
      <div class="existed_font">
        <span>View Comments</span>
      </div>
    </div>
    <div class="no-data" v-if="associatedPhones.length===0">
      No relevant comments yet!
    </div>
    <div class="phone_listing" v-for="(item, index) in associatedPhones" :key="index">
      <div class="phone_detail">
        <div class="phone_img">
          <img :src="getImageUrl(item.image)" />
        </div>
        <div class="phone_title">{{item.title}}</div>
      </div>
      <div class="phone_comments">
        <el-table :data="item.reviews">
          <el-table-column prop="comment" type="expand">
            <template slot-scope="props">
              <div class="comment"><p class="comment_font">Comment: </p>{{props.row.comment}}</div>
            </template>
          </el-table-column>
          <el-table-column prop="reviewer" label="Reviewer" width="200">
            <template slot-scope="props">
              <span>{{props.row.reviewer.firstname + " " + props.row.reviewer.lastname}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="rating" label="Rating" width="200">
            <template slot-scope="props">
              <div>
                <el-rate
                    v-model="props.row.rating"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value}">
                </el-rate>
              </div>
            </template>
          </el-table-column>
<!--          <el-table-column prop="comment" label="Comment" width="300">-->
<!--            <template slot-scope="props">-->
<!--              <div class="comment" >{{props.row.comment}}</div>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import {findAssociatedPhone} from "@/api/user";

export default {
  name: "CommentComponent",
  props:{
    existedListings: {
      type: Array,
      default() {
        return [
          {
            title: 'Galaxy s III mini SM-G730V Verizon Cell Phone BLUE',
            price: 999,
            brand: 'SamSumg',
            stock: '1'
          },
          {
            title: 'Galaxy s III mini',
            price: 999,
            brand: 'SamSumg',
            stock: '1'
          }
        ]
      }
    }
  },
  data() {
    return {
      associatedPhones: {},

      reviews:[{
        reviewer: 'Jimmy Sagedahl',
        rating: '2.0',
        comment: 'I had purchased this as a replacement for a 3gs phone that '+
                'I apparently used as a basketball too often. I had to return it because several ' +
                'features did not work. I subsequently ordered a 4s, an upgrade at a similar price which works fine.'
      },{
        reviewer: 'Jimmy Sagedahl',
        rating: '3.0',
        comment: 'Got phone yesterday all …. pleased now!'
      },{
        reviewer: 'Jimmy Sagedahl',
        rating: '4.0',
        comment: 'Got phone yesterday all …. pleased now!'
      }]
    }
  },
  created() {
    findAssociatedPhone().then(res=>{
      // console.log(res);
      if(res.success){
        this.associatedPhones = res.data;
        console.log(this.associatedPhones)
      }
      // else{
      //   console.log()
      // }
    },err=>{
      console.log(err);
    });

  },
  methods:{
    getImageUrl(url) {
      if(url && url !== 'imageurl') {
        return require(`../../../assets/phone_default_images/${url}.jpeg`)
      }
      return ''
    }
  }
}
</script>

<style scoped>
.view_comment{
  display: flex;
  font-size: 26px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}
.comment_icon{
  width: 50px;
  height: 50px;

}

.existed_font{
  padding-top: 10px;
  padding-left: 10px;
}

.phone_listing{
  display: flex;
  float: left;
  margin-top: 3%;
  margin-bottom: 30px;
  align-items: center;
}

.phone_detail{
}

.phone_img{
  box-sizing: border-box;
  float: left;
  width: 100px;
  height: 120px;
  padding: 5px;
  /*border: 1px solid greenyellow;*/
  /*background: url("../../../assets/phone_default_images/Apple.jpeg") no-repeat;*/
}

.phone_img>img{
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.phone_title{
  float: left;
  width: 200px;
  text-align: center;
  margin-top: 20px;

}

.phone_comments{
  float: right;
}

.comment{
  height: auto;
  max-height: 100px;
  overflow:auto;
}

.comment_font{
  font-weight: bold;
}

.no-data{
  color: #7d8492;
  text-align: center;
  margin: 30px;
  padding: 10px;
  width: 90%;
  font-size: 20px;
  border: 1px solid lightskyblue;
}

</style>