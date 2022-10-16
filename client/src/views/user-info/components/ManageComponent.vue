<template>
  <div>
    <el-table
        :data="tableData"
        max-height="600"
        style="width: 100%; column-rule-color: #bbc5d3"
        :header-cell-style="{ color: '#409EFF' }"
        @row-click="toDetails"
    >
      <el-table-column prop="topicName" label="Title" width="230"/>
      <el-table-column prop="createTime" label="CreateTime" width="200" :formatter="formatTime"/>
      <el-table-column prop="collectNum" label="Collects" />
      <el-table-column prop="commentNum" label="Comments"  />
      <el-table-column prop="likeNum" label="Likes" />
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <span class="el-icon-delete del-btn" @click="delTopic(scope.row)"></span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

import { getTopicDataByPm, deleteTopic } from '@/api/user'
import { utc2beijing } from "@/utils";

export default {
  name: "ManageComponent",
  data() {
    return {
      tableData: [{}]
    }
  },
  created() {
    this.getInfo()
  },
  methods:{
    formatTime(row, column, value) {
      if(value) {
        return utc2beijing(value)
      }
    },

    toDetails(row, column) {
      if(column.label!=='Operation') {
        localStorage.setItem('target', JSON.stringify(row))
        this.$router.push('details')
      }
    },

    async getInfo() {
      try {
        const res = await getTopicDataByPm()
        if(res.code === 20011) {
          this.tableData = res.data
        } else {
          this.$message.error(res.msg)
        }
      } catch (err) {
        console.log(err)
      }
    },

    delTopic(item) {
      this.$confirm('This operation will delete your topic, are you sure?', 'warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        let topicID = item.topicId
        deleteTopic({ id: topicID }).then(res => {
          if (res.code === 20031) {
            this.$message.success('Delete successfully');
            this.getInfo()
          } else {
            this.$message.error(res.msg);
          }
        }, err => {
          console.log(err);
        });
      }).catch(() => {});
    }
  }
}
</script>

<style scoped>
.del-btn{
  color: #f5614c;
}
.pro-style{
  color: #1482f0;
}
</style>