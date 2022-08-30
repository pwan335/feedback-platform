<template>
  <div>
    <div class="exited_listing">
      <img class="phone_icon" src="../../../assets/userPage_images/phone.png" >
      <span class="existed_font"> Existed phone listings</span>
    </div>
    <!--Existed phone listings-->
    <div class="listing_table">
      <el-tabs v-model="activeName">
        <el-tab-pane label="Existed phone listings" name="first" :key="'first'">
<!--          @select="handleAll" @select-all="handleAll"-->
          <el-table :data="associatedPhones" ref="multipleTable">
            <el-table-column prop="reviews" type="expand">
              <template slot-scope="props">
                <div class="phone_comments">
                  <el-table :data="props.row.reviews">
                    <el-table-column prop="reviewer" label="Reviewer" width="160">
                      <template slot-scope="props">
                        <span>{{props.row.reviewer.firstname + " " + props.row.reviewer.lastname}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="rating" label="Rating" width="180">
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
                    <el-table-column prop="comment" label="Comment" width="300">
                      <template slot-scope="props">
                        <div class="comment" >{{props.row.comment}}</div>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="image" label="Product" width="100" >
              <template slot-scope="props">
                <div class="phone_img">
                  <img :src="getImageUrl(props.row.image)" />
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="title" label="Title" width="200">
              <template slot-scope="titles">
                <span>{{titles.row.title}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="brand" label="Brand" width="120">
              <template slot-scope="brands">
                <span>{{brands.row.brand}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="Price" width="100">
              <template slot-scope="prices">
                <span>${{prices.row.price}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="stock" label="Stock" width="100">
              <template slot-scope="stocks">
                <span>{{stocks.row.stock}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="_id" label="Display" width="100" >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="handleEnable(scope.row._id)" v-if="scope.row.hasOwnProperty('disabled')">Enable</el-button>
                <el-button size="mini" type="text" @click="handleDisable(scope.row._id)" v-else-if="!scope.row.hasOwnProperty('disabled')">Disable</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="_id" label="Action" width="200">
<!--              <template slot-scope="scope">-->
<!--                <el-button size="mini" type="primary" @click="handleComment" plain>View comments</el-button>-->
<!--              </template>-->
              <template slot-scope="ids">
<!--                <el-button size="mini" type="primary" @click="handleComments(ids.row._id)" plain>View comments</el-button>-->
                <el-button size="mini" type="danger" @click="handleDelete(ids.row._id)">Delete</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="Add listing" name="second" :key="'second'">
          <el-table :data="listings">
            <el-table-column label="Product" width="100">
              <template slot-scope="scope">
                <div class="addPhone_img">
                  <img :src="getImageUrl(image)" v-if="brandState==true" v-show="image!==''"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="Title" width="200">
              <template slot-scope="scope">
                <el-input v-model="title" size="small">Input title</el-input>
              </template>
            </el-table-column>
            <el-table-column prop="brand" label="Brand" width="120">
              <template slot-scope="scope">
                <el-dropdown @command="handleCommand">
                  <el-button type="primary" size="small" plain v-if="brand=='Brand'">
                    Brand<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='Samsung'">
                    Samsung<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='Apple'">
                    Apple<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='HTC'">
                    HTC<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='Huawei'">
                    Huawei<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='Nokia'">
                    Nokia<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='LG'">
                    LG<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='Motorola'">
                    Motorola<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-button type="primary" size="small" plain v-else-if="brand=='BlackBerry'">
                    BlackBerry<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="Brand">Brand</el-dropdown-item>
                    <el-dropdown-item command="Samsung">Samsung</el-dropdown-item>
                    <el-dropdown-item command="Apple">Apple</el-dropdown-item>
                    <el-dropdown-item command="HTC">HTC</el-dropdown-item>
                    <el-dropdown-item command="Huawei">Huawei</el-dropdown-item>
                    <el-dropdown-item command="Nokia">Nokia</el-dropdown-item>
                    <el-dropdown-item command="LG">LG</el-dropdown-item>
                    <el-dropdown-item command="Motorola">Motorola</el-dropdown-item>
                    <el-dropdown-item command="Sony">Sony</el-dropdown-item>
                    <el-dropdown-item command="BlackBerry">BlackBerry</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="Price" width="120">
              <template slot-scope="scope">
                <el-input v-model="price" size="small">Input price</el-input>
              </template>
            </el-table-column>
            <el-table-column label="Stock" width="160">
              <template slot-scope="scope">
                <el-input-number v-model="addStock" :min="0" size="small"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="Action" width="120">
              <template slot-scope="scope">
                <el-button size="small" type="primary" @click="handleAdd()">Add</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {addNewPhone, deletePhone, findAssociatedPhone, setEnabled, setDisabled} from "@/api/user";
import Vue from "vue";
import {validNum, validStock} from "@/utils";

export default {
  name: "ListingComponent",
  data() {
    return {
      associatedPhones:[],

      activeName: 'first',
      existedListings:[{
        title: 'Galaxy s III mini SM-G730V Verizon Cell Phone BLUE',
        price: 888,
        brand: 'SamSumg',
        stock: '8'
      }],
      listings:[
      {
        // title: 'Galaxy s III mini SM-G730V Verizon Cell Phone BLUE',
        // price: 888,
        // brand: 'SamSumg',
        // stock: '8'
        title: '',
        price: '',
        brand: '',
        stock: ''
      }
      ],
      addStock: 1,
      title: '',
      brand:'Brand',
      price:'',
      brands: ['Samsung','Apple','HTC','Huawei','Nokia','LG','Motorola','Sony','BlackBerry'],
      image: '',
      brandState : false,
      multipleSelection: [],
      checkAll: false,
      isIndeterminate: true,
      selected: true,

    }
  },
  created() {
    findAssociatedPhone().then(res=>{
      // console.log(res);
      if(res.success){
        this.associatedPhones = res.data;
      }else{
        console.log(res.message);
      }
    },err=>{
      console.log(err);
    });
  },
  methods:{
    getImageUrl(url) {
      // console.log(url)
      if(url && url !== 'imageurl') {
        return require(`../../../assets/phone_default_images/${url}.jpeg`)
      }
      return ''
    },


    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },

    handleSelect(){

    },

    handleDelete(id){
      let data = {
        "id": id
      };
      console.log(data);
      deletePhone(data).then(res=>{
        console.log(res);
        if(res.success){
          this.$message.success('Delete phone successfully!');
          this.updatePhoneList();
        }else{
          this.$message.error(res.message);
        }
      },err=>{
        console.log(err);
      });
    },

    handleAdd(){
      if(!this.title){
        this.$message.warning('Please input the title!');
        return;
      }
      if(this.brand==='Brand'){
        this.$message.warning('Please select your brand');
        return;
      }
      if(!this.price){
        this.$message.warning('Please input the price!');
        return;
      }else{
        if(!validNum(this.price)){
          this.$message.warning('Please input the correct price!');
          return;
        }
        if(this.price <= 0){
          this.$message.warning('Please input the correct price!');
          return;
        }
      }

      if(!this.addStock){
        this.$message.warning('Please input the correct stock!');
        return;
      }else{
        if(!validStock(this.addStock)){
          this.$message.warning('Please input the correct stock!');
          return;
        }
      }
      let newPhone = {
        "title": this.title,
        "brand": this.brand,
        "stock": this.addStock,
        "price": this.price
      };
      addNewPhone(newPhone).then(res=>{
        console.log(res);
        if(res.success){
          this.$message.success('Add new phone successfully!');
          this.updatePhoneList();
          this.addStock = 1;
          this.title = '';
          this.brand = 'Brand';
          this.price = '';
          this.image = '';
        }else{
          this.$message.error(res.message);
        }
      },err=>{
        console.log(err);
      }
      );
      // console.log("title: " + this.title + " brand: " + this.brand + " price: " + this.price + " stock: " + this.addStock )
    },

    updatePhoneList(){
      findAssociatedPhone().then(res=>{
        if(res.success){
          this.associatedPhones = res.data;
        }else{
          console.log(res.message);
        }
        // this.associatedPhones = res.data;
        // console.log(this.associatedPhones);
      },err=>{
        console.log(err);
      });
    },
    handleCommand(command) {
      if(command!="Brand"){
        this.brand = command;
        this.image = command;
        this.brandState = true;
        // console.log(this.image)
      }else{
        this.brand = command;
        this.brandState = false;
      }
      // this.$message('click on item ' + command);
    },

    handleEnable(id){
      let data = {
        "id": id
      };
      setEnabled(data).then(res=>{
        if(res.success){
          this.updatePhoneList();
          this.$message.success("This phone is enabled successfully");
        }else{
          this.$message.error("This phone is enabled unsuccessfully");
        }
      },err=>{
        console.log(err);
      });
    },
    handleDisable(id){
      let data = {
        "id": id
      };
      setDisabled(data).then(res=>{
        console.log(res)
        if(res.success){
          this.updatePhoneList();
          this.$message.success("This phone is disabled successfully");
        }else{
          this.$message.error("This phone is disabled unsuccessfully");
        }
      },err=>{
        console.log(err);
      });
    },
  }
}
</script>

<style scoped>

.add_list{
  float: left;
  width: 100%;
}

.addList_btn{
  float: left;
}

.exited_listing{
  float: left;
  /*margin-top: 5%;*/
  font-size: 32px;
  color: #537ec4;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.phone_icon{
  width: 45px;
  height: 45px;
}

.listing_table{
  float: left;
  margin-top: 2%;
  box-sizing: border-box;
}

.phone_img{
  box-sizing: border-box;
  float: left;
  width: 80px;
  height: 100px;
  padding: 5px;
  /*border: 1px solid greenyellow;*/
  /*background: url("../../../assets/phone_default_images/Apple.jpeg") no-repeat;*/
}
.addPhone_img{
  box-sizing: border-box;
  float: left;
  width: 80px;
  height: 100px;
  padding: 5px;
  border: 1px solid #e3e2e2;
  /*background: url("../../../assets/phone_default_images/Apple.jpeg") no-repeat;*/
}

.phone_img>img{
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.addPhone_img>img{
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.product{
  height: 80px;
  /*border: 1px solid greenyellow;*/
  background: url("../../../assets/phone_default_images/Samsung.jpeg") no-repeat;
  background-size: contain;
}
.product_img{
  height: 100px;
  border: 1px solid greenyellow;
  object-fit: fill;
  background: url("../../../assets/userPage_images/phone.png") no-repeat;
}

.phone_comments{
  margin-left: 70px;
  width: 80%;
  border: 1px solid lightgrey;
}

.comment{
  height: auto;
  max-height: 100px;
  overflow:auto;
}

.comment_font{
  font-weight: bold;
}

</style>