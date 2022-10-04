<template>
  <div>
    <div class = "manage_topic">
      <div class="existed_font">
        <span>Manage your topic here</span>
      </div>
    </div>

<!--    prop: according to the query result from backend-->

    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          prop="Title"
          label="title"
          width="230">
      </el-table-column>
      <el-table-column
          prop="Status"
          label="status"
          width="100">
      </el-table-column>
      <el-table-column
          prop="pv"
          label="PV"
          width="100">
      </el-table-column>
      <el-table-column
          prop="stars"
          label="star"
          width="100">
      </el-table-column>
      <el-table-column
          prop="Comments"
          label="comments"
          width="100">
      </el-table-column>
      <el-table-column
          prop="Likes"
          label="likes"
          width="100">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="operation"
          width="80">
        <template slot-scope="scope">
          <el-button
              @click="delTopic(scope.$index, tableData)"
              type="text"
              size="small">
            delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getManageInfo} from "@/api/TopicInfo"
import {deleteTopic} from "@/api/TopicInfo"

export default {
  name: "ManageComponent",
  data() {
    return {
      tableData: [{
          Title: 'Title',
          Status: 'deleted',
          pv: '500',
          stars: '345',
          Likes: '22',
          Comments: 234

      }]
    }
  },
  created() {
    this.getInfo()
  },
  methods:{
    async getInfo() {
      let userID = sessionStorage.getItem("userID") //todo: Get current user ID
      let getManage = {
        "userID": userID
      }
      this.loading = true;
      getManageInfo(getManage).then(res => {
        console.log(res.data, 'manageInfo');
        this.tableData = res.data.data; //todo: not tested yet
        this.total = res.data.total;
        this.loading = false;
      })
      this.loading = false;
    },

    delTopic(index, rows) {
      this.$confirm('This operation will delete your topic, are you sure?', 'warning', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        let topicID = rows.at(index).data //todo: get topic ID
        let deltopic = {
          "TopicID": topicID
        }
        deleteTopic(deltopic).then(res => {
          if (res.success) {
            this.$message.success('Delete successfully');
          } else {
            this.$message.error(res.message);
          }
          console.log(res);
        }, err => {
          console.log(err);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Cancel delete'
        });
      });




    }
  }
}
</script>

<style scoped>

</style>