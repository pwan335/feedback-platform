<template>
  <div>
    <el-table
        :data="tableData"
        max-height="600"
        style="width: 100%"
        @row-click="toDetails"
    >
      <el-table-column prop="topicName" label="title" width="230" />
      <el-table-column prop="createTime" label="createTime" width="200" :formatter="formatTime" />
      <el-table-column prop="collectNum" label="stars" />
      <el-table-column prop="commentNum" label="comments"  />
      <el-table-column prop="likeNum" label="likes" />
      <el-table-column label="operation">
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
      if(column.label!=='operation') {
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
</style>