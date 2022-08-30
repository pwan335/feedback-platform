<template>
  <div class="checkOut">
    <div class="shopping_cart">
      Shopping Cart
    </div>
    <el-table :data="shoppingCart" @select="handleSum" @select-all="handleSum" ref="multipleTable" >
      <el-table-column type="selection" width="40" ></el-table-column>
      <el-table-column prop="image" label="Product" width="100">
        <template slot-scope="props">
          <div class="phone_img">
            <img :src="getImageUrl(props.row.image)"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="Title" width="200">
        <template slot-scope="titles">
          <span>{{ titles.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="brand" label="Brand" width="150">
        <template slot-scope="brands">
          <span>{{ brands.row.brand }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="Price" width="130">
        <template slot-scope="prices">
          <span>${{ prices.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="Quantity" width="200">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.quantity"
                           @change="handleSum(), handleQuantity(scope.$index), handleMax(scope.$index)" :min="0"
                           :max="scope.row.stock" size="small"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column label="Total Price" width="200">
        <template slot-scope="total">
          <span>${{ total.row.price * total.row.quantity }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Action" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="return_btn">
      <el-button type="primary" size="small" plain @click="returnPage">return</el-button>
    </div>
    <div class="checkOut_btn">
      <el-button type="primary" size="small" @click="ckOutCon">Check Out</el-button>
    </div>
    <div class="total_style">
      Total ({{ items }} items)：<span class="total_price">${{ sum }}</span>
    </div>
  </div>
</template>

<script>
import {decreaseStock} from "@/api/shop";
export default {
  name: "CheckOut",
  data() {
    return {
      sum: 0,
      items: 0,
      shoppingCart: [],
      checkOutCart: []
    }
  },
  created() {
    this.getCartFormSt()
  },
  mounted() {
    this.$refs['multipleTable'].toggleAllSelection()
  },
  methods: {
    getCartFormSt() {
      this.shoppingCart = [];
      this.sum = 0;
      this.items = 0;
      let storage = sessionStorage.getItem('storage')
      if (storage) {
        storage = JSON.parse(storage)
        for (let item in storage) {
          this.shoppingCart.push(storage[item])
        }
      }
    },
    getImageUrl(url) {
      // console.log(url)
      if (url && url !== 'imageurl') {
        return require(`../../assets/phone_default_images/${url}.jpeg`)
      }
      return ''
    },
    handleSum() {
      this.sum = 0;
      this.items = 0;
      this.$nextTick(() => {
        this.$refs.multipleTable.selection.forEach(item => {
          this.sum += (item.price * item.quantity);
          this.items += 1;
        });
      });
    },
    // Delete the product when the quantity is 0 and change the storage
    handleQuantity(index) {
      if (this.shoppingCart[index].quantity === 0) {
        this.$confirm('This will delete the product in shopping cart. Continue?', 'Warning', {
          confirmButtonText: 'OK',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: 'Delete completed'
          });
          let itemId = this.shoppingCart[index]._id
          this.changeStorage(itemId, -1)
          this.shoppingCart.splice(index, 1);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Delete canceled'
          });
          this.shoppingCart[index].quantity = 1;
          this.handleSum();
          // this.getCartFormSt();
        });
      } else {
        this.changeStorage(this.shoppingCart[index]._id, this.shoppingCart[index].quantity);
      }
    },
    handleMax(index) {
      if (this.shoppingCart[index].quantity === this.shoppingCart[index].stock) {
        this.$message.warning("You have purchased the maximum stock of this item！")
      }
    },
    changeStorage(id, quantity) {
      let storage = sessionStorage.getItem('storage')
      storage = JSON.parse(storage)
      if (quantity === -1) {
        delete storage[id]
      } else {
        let chaNum = parseInt(quantity);
        if (quantity <= 0) {
          delete storage[id]
        } else {
          storage[id].quantity = chaNum
        }
      }
      sessionStorage.setItem('storage', JSON.stringify(storage))
    },
    handleDelete(i) {
      let itemId = this.shoppingCart[i]._id
      this.changeStorage(itemId, -1)
      this.shoppingCart.splice(i, 1);
      this.handleSum();
    },
    returnPage() {
      this.$emit('switchTab', -1)
    },
    ckOutCon(){
      this.$confirm('Do you want to check out?', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.checkOut();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Check out canceled'
        });
      });
    },
    checkOut() {
      let promises = []
      let phoneId = []
      this.$refs.multipleTable.selection.forEach(item => {
        let phone = {
          "id": item._id,
          "number": item.quantity
        };
        let temp = decreaseStock(phone)
        promises.push(temp)
        phoneId.push(item._id)
      });
      Promise.all(promises).then(values=>{
        console.log(values)
        let checkOutState = true
        for(let i =0; i< values.length; i++) {
          let res = values[i]
          if(!res.success) {
            this.$message.error(res.message)
            checkOutState = false
            break;
          }
        }
        if (checkOutState) {
          phoneId.forEach(id=>{
            this.changeStorage(id, -1);
          })
          this.$message.success('Check out successful!');
          // return to Main page with 'Home' state
          sessionStorage.removeItem('querydata')
          this.$emit('switchTab', 0);
        }
      }, err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>
.shopping_cart {
  float: left;
  width: 90%;
  height: auto;
  margin-top: 1%;
  padding-top: 1%;
  padding-left: 1%;
  padding-bottom: 1%;
  text-align: left;
  color: #2e62b5;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  font-size: 18px;
  border: 1px solid #d9dde3;
}
.checkOut {
  margin-left: 3%;
}
.phone_img {
  box-sizing: border-box;
  float: left;
  width: 80px;
  height: 100px;
  padding: 5px;
  /*border: 1px solid greenyellow;*/
  /*background: url("../../../assets/phone_default_images/Apple.jpeg") no-repeat;*/
}
.phone_img > img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.total_style {
  float: right;
  margin-right: 2%;
  margin-top: 3%;
  font-size: 24px;
  font-weight: bold;
}
.total_price {
  color: red;
}
.checkOut_btn {
  float: right;
  margin-right: 15%;
  margin-top: 3%;
}
.return_btn {
  float: left;
  margin-top: 3%;
}
</style>
