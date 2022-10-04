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
        <el-col :span="10">
          <el-input type="title" v-model="yourTitle"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item class="text_item" label="Body">
        <el-col :span="20">
          <el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" v-model="yourBody"></el-input>
        </el-col>
      </el-form-item>

      <el-upload
          action="#"
          v-model="yourPictures"
          list-type="picture-card"
          :auto-upload="false">
        <i slot="default" class="el-icon-plus"></i>
        <div slot="file" slot-scope="{file}">
          <img
              class="el-upload-list__item-thumbnail"
              :src="file.url" alt=""
          >
          <span class="el-upload-list__item-actions">
        <span
            class="el-upload-list__item-preview"
            @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleDownload(file)"
        >
          <i class="el-icon-download"></i>
        </span>
        <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
        </div>
      </el-upload>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>

      <el-form-item class="form_btn">
        <el-button type="primary" @click="postTopic">Submit</el-button>
      </el-form-item>

    </el-form>
  </div>

</template>

<script>
import {addTopic} from "@/api/TopicInfo";

export default {
  name: "TopicComponent",
  data() {
    return{
      yourTitle: 'Title',
      yourBody: '',
      yourPictures: '',
      dialogVisible: false,
      disabled: false,
    };
  },
  methods:{
    postTopic() {
      if (!this.yourTitle) {
        this.$message.error('Please input your title!')
        return;
      }
      if (!this.yourBody) {
        this.$message.error('Please input your topic!')
        return;
      }

      const topic = {
        "topicTitle": this.yourTitle,
        "topicBody": this.yourBody,
        "topicPicture": this.yourPictures
      }
      addTopic(topic).then(res => {
        if (res.success) {
          this.$message.success('Submit successfully');
          this.yourTitle = '';
          this.yourBody = '';
          this.yourPictures = '';
        } else {
          this.$message.error(res.message);
        }
        console.log(res);
      }, err => {
        console.log(err);
      });
    },

    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
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
  float: left;
  margin-left: 0;
  box-sizing: border-box;
}

</style>