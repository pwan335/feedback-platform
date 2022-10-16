<template>
  <div>
    <div class="topic_Edit">
<!--      <img class="topic_icon" src=""-->
      <div class="existed_font"></div>
      <span>Write your topic here</span>
    </div>
    <div class="tips">
      Tips: You need write the title and the body of your topic
    </div>
    <el-form ref="Topic" label-width="140px">
      <el-form-item class="form_item" label="Title">
        <el-col :span="20">
          <el-input type="title" v-model="yourTitle" placeholder="topic title"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="text_item" label="Body">
        <el-col :span="20">
          <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" v-model="yourBody"></el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <el-upload
        action=""
        list-type="picture-card"
        :file-list="fileList"
        :limit="8"
        :on-change="changeFile"
        :on-remove="handleRemove"
        :on-preview="handlePictureCardPreview"
        :auto-upload="false">
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <div class="form_btn">
      <el-button type="primary" @click="postTopic">Submit</el-button>
    </div>
  </div>

</template>

<script>
import { createTopic, createTextTopic } from "@/api/user";

export default {
  name: "TopicComponent",
  data() {
    return{
      yourTitle: '',
      yourBody: '',
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: []
    };
  },
  methods:{
    changeFile(file, fileList) {
      this.fileList = fileList
    },

    handleRemove(file, fileList) {
      this.fileList = fileList
    },

    postTopic() {
      if (!this.yourTitle) {
        this.$message.error('Please input your title!')
        return;
      }
      if (!this.yourBody) {
        this.$message.error('Please input your topic!')
        return;
      }

      if(this.fileList.length===0) {
        createTextTopic({ topicName: this.yourTitle, content: this.yourBody }).then(res => {
          if (res.code===20021) {
            this.$message.success('Submit successfully');
            this.yourTitle = '';
            this.yourBody = '';
            this.fileList = []
          } else {
            this.$message.error(res.msg);
          }
          console.log(res);
        }, err => {
          console.log(err);
        });
      } else {
        const formData = new FormData()
        formData.append('topicName', this.yourTitle)
        formData.append('content', this.yourBody)
        this.fileList.forEach(item=>{
          formData.append('file', item.raw)
        })
        createTopic(formData).then(res => {
          if (res.code===20021) {
            this.$message.success('Submit successfully');
            this.yourTitle = '';
            this.yourBody = '';
            this.fileList = []
          } else {
            this.$message.error(res.msg);
          }
          console.log(res);
        }, err => {
          console.log(err);
        });
      }
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }

 }

}
</script>

<style scoped>
.topic_Edit {
  display: flex;
  margin-bottom: 30px;
  font-size: 26px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.tips{
  text-align: left;
  margin-bottom: 20px;
  color: coral;
  font-size: 14px;
}

.existed_font {
  padding-top: 10px;
  padding-left: 10px;
}

.form_item {
  white-space: nowrap;
  box-sizing: border-box;
}

.text_item {
  white-space: nowrap;
  box-sizing: unset;
}

.form_btn {
  margin-top: 20px;
  text-align: center;
  box-sizing: border-box;
}

</style>