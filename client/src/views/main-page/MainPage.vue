<template>
  <div class="container">
    <!--default-->
    <div v-if="status==='default'" class="default-container">
      <div class="sell-title">Sold out soon</div>
      <div class="phone-item-container">
        <div class="phone-item" v-for="(item, index) in soldOutSoon" :key="index" @click="viewPhoneDetails(item)">
          <img :src="getImageUrl(item.image)" />
          <div class="item-des">
            <span class="price">${{item.price}}</span>
            <span class="stock">stock:{{formatStock(item.stock)}}</span>
          </div>
        </div>
      </div>
      <div class="sell-title">Best Sellers</div>
      <div class="phone-item-container">
        <div class="phone-item" v-for="(item, index) in bestSellers" :key="index" @click="viewPhoneDetails(item)">
          <img :src="getImageUrl(item.image)" />
          <div class="item-des">
            <span class="rating">Rating：{{item.rating.toFixed(2)}}</span>
          </div>
        </div>
      </div>
    </div>

    <!--search-->
    <div v-else-if="status==='search'" class="search-container">
      <div class="filter-condition">
        <div class="filter-price">
          <span>price:</span>
          <div class="block">
            <el-slider
                v-model="price"
                :max="maxPrice"
                range>
            </el-slider>
          </div>
        </div>
        <div class="filter-brand">
          <span>Brand:</span>
          <el-select v-model="brand" clearable>
            <el-option v-for="(item, index) in brands" :key="index" :label="item" :value="item" />
          </el-select>
        </div>
        <div class="confirm-btn">
          <el-button type="primary" @click="filterByPriceBrand">confirm</el-button>
        </div>
      </div>
      <div class="filter-content">
        <div class="phone-item-container">
          <div class="phone-item" v-for="(item, index) in reFilterResult" :key="index" @click="viewPhoneDetails(item)">
            <img :src="getImageUrl(item.image)" />
            <div class="item-des">
              <div class="phone-item-title">{{item.title}}</div>
              <span class="price">${{item.price}}</span>
              <span class="stock">stock:{{formatStock(item.stock)}}</span>
            </div>
          </div>
          <div class="no-data" v-if="reFilterResult.length===0">No relevant data matched</div>
        </div>
      </div>
    </div>

    <!--details-->
    <div v-else-if="status==='details'" class="details-container">
      <span class="el-icon-arrow-left go-back" @click="goBack">go back</span>
      <div class="details-content">
        <div class="details-docker">
          <div class="details-left-part">
            <img :src="getImageUrl(targetInfo.image)" />
          </div>
          <div class="details-right-part">
            <div class="item-details-title">{{targetInfo.title}}</div>
            <div>Price:<span class="item-details-value">${{targetInfo.price || 0}}</span></div>
            <div>Brand:<span class="item-details-value">{{targetInfo.brand}}</span></div>
            <div>Stock:<span class="item-details-value">{{targetInfo.stock}}</span></div>
            <div>Seller:<span class="item-details-value">{{targetInfo.seller.firstname + ' ' + targetInfo.seller.lastname}}</span></div>
<!--            <div>Quantity:-->
<!--              <el-input-number v-model="quantityToBuy" :min="1" :max="10" size="small">-->
<!--              </el-input-number>-->
<!--            </div>-->
            <div>
              In Cart:<span class="item-details-value">{{getNum()}}</span>
            </div>
            <div class="add-to-cart">
              <el-button type="plain" icon="el-icon-shopping-cart-2" @click="addToCart">add to cart</el-button>
            </div>
          </div>
        </div>
        <el-divider></el-divider>
        <div class="comment">
          <comment-component v-model="targetInfo.reviews" :target-info="targetInfo" :tartget-id="targetInfo['_id']" @switchTab="switchTabs" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getBestSellers, findLowStockPhones, findByTitle } from '@/api/shop'
import CommentComponent from "@/views/main-page/components/CommentComponent";
export default {
  name: "MainPage",
  components: {
    CommentComponent
  },
  props: {
    keyWord: {
      type: String,
      default: ''
    }
  },
  watch: {
    // 搜索关键字为空并且不是查看具体元素，则设置为default状态
      keyWord(value) {
        if(value==='' || value.trim()==='') {
          if(this.targetInfo) {
            this.historyRoute = this.status
            this.status = 'details'
          } else {
            this.historyRoute = this.status
            this.status = 'default'
          }
        }
      }
  },
  data() {
    return {
      // home page status(default、search、details)
      status: 'default',
      historyRoute: '',
      // item to view details
      targetInfo: '',
      brand: '',
      price: [0, 999],
      maxPrice: 0,
      soldOutSoon: [],
      bestSellers: [],

      filterResult: [],
      reFilterResult: [],

      // the brands of phone
      brands: ['Samsung','Apple','HTC','Huawei','Nokia','LG','Motorola','Sony','BlackBerry'],

      // details
      quantityToBuy: 1,
      itemNuminCart: 0
    }
  },
  created() {
    this.getDefaultData()
    // Whether to enter the details page
    let queryData = sessionStorage.getItem('querydata')
    if(queryData) {
      queryData = JSON.parse(queryData)
      let path = queryData.path
      if(path === 'details') {
        this.targetInfo = queryData.data
        this.status = 'details'
        this.historyRoute = 'default'
        this.getNumFormCt(this.targetInfo._id);
        // clear cache
        sessionStorage.removeItem('querydata')
      }
    }
  },
  methods: {
    getImageUrl(url) {
      if(url && url !== 'imageurl') {
        return require(`../../assets/phone_default_images/${url}.jpeg`)
      }
      return ''
    },

    // 跳转路由
    switchTabs(index) {
      this.$emit('switchTab', index)
    },

    getNumFormCt(id){
      let storage = sessionStorage.getItem('storage')
      if(storage) {
        storage = JSON.parse(storage)
        console.log(storage)
        if(storage[id]){
          this.itemNuminCart = storage[id].quantity;
          this.getNum();
        }else
          this.itemNuminCart = 0;
      }else{
        this.itemNuminCart = 0;
      }
    },
    getNum(){
      return this.itemNuminCart
    },
    // filter data
    filterData() {
      if(this.keyWord) {
        this.historyRoute = this.status
        this.status = 'search'
        this.getFilterData()
      }
    },

    // format stock
    formatStock(stock) {
      let res = ''
      if(stock>999) {
        res = '999+'
      } else {
        res = stock
      }
      return res
    },

    // Get data in default mode
    getDefaultData() {
      findLowStockPhones({}).then(res=>{
        if(res.success) {
          this.soldOutSoon = res.data
        }
      }, err=>{
        console.log(err)
      })
      getBestSellers({}).then(res=>{
        if(res.success) {
          this.bestSellers = res.data
        }
      }, err=>{
        console.log(err)
      })
    },

    // Get the data after keyword search
    getFilterData() {
      findByTitle({title: this.keyWord}).then(res=>{
        console.log(res)
        if(res.success) {
          this.filterResult = res.data.phones
          this.price = [0, res.data.maxPrice]
          this.maxPrice = res.data.maxPrice
          this.filterByPriceBrand()
        }
      },
      err=>{
        console.log(err)
      })
    },

    // Secondary filter data by price and phone brand
    filterByPriceBrand() {
      this.reFilterResult = this.filterResult.filter(item=>{
        let validPrice = false
        let validBrand = false
        if(item.price>=this.price[0] && item.price<=this.price[1]) {
          validPrice = true
        }
        if(!this.brand || item.brand === this.brand) {
          validBrand = true
        }
        return validPrice && validBrand
      })
    },

    // view phone details
    viewPhoneDetails(item) {
      this.targetInfo = item
      this.getNumFormCt(this.targetInfo._id);
      this.historyRoute = this.status
      this.status = 'details'
    },

    // goBack mode
    goBack(){
      this.status = this.historyRoute
      this.targetInfo = ''
    },

    // set return back inforamtion
    setBackInfo() {
      if(this.status==='details') {
        let queryData = {
          path: 'details',
          data: this.targetInfo
        }
        sessionStorage.setItem('querydata', JSON.stringify(queryData))
      }
    },

    // add to cart
    addToCart() {
      // check user
      let user = sessionStorage.getItem('user')
      if(!user) {
        this.$message.warning('please log in first!')
        this.setBackInfo()
        this.$emit('switchTab', 2)
        return
      }
      this.$prompt('Please input the quantity', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        inputPattern: /^\+?[1-9][0-9]*$/,
        inputErrorMessage: 'Invalid Number'
      }).then(({ value }) => {
        if(value > this.targetInfo.stock - this.getNum()){
          this.$message.warning("Out of stock！")
        }else{
          user = JSON.parse(user)
          if(this.targetInfo) {
            const storage = sessionStorage.getItem('storage')
            if(!storage) {
              const temp = {}
              temp[this.targetInfo._id] = Object.assign({}, this.targetInfo)
              temp[this.targetInfo._id].quantity = value
              sessionStorage.setItem('storage', JSON.stringify(temp))
            } else {
              const st = JSON.parse(storage)
              let id = this.targetInfo._id
              if(Object.hasOwnProperty.call(st,id)) {
                st[id].quantity = parseInt(st[id].quantity) + parseInt(value)
                sessionStorage.setItem('storage', JSON.stringify(st))
              } else {
                st[id] = Object.assign({}, this.targetInfo)
                st[id].quantity = value
                sessionStorage.setItem('storage', JSON.stringify(st))
              }
            }
          }
          this.$message({
            type: 'success',
            message: 'Add to cart successfully!'
          });
        }
        this.getNumFormCt(this.targetInfo._id);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Input canceled'
        });
      });
    }

  }
}
</script>

<style scoped>
.default-container{
  margin-top: 15px;
}
.phone-item-container{
  border: 1px solid #73b9b0;
  display: flex;
  flex-wrap: wrap;
}
.sell-title{
  font-size: 24px;
  text-align: left;
  font-weight: 700;
  color: #2e62b5;
  margin: 10px 0;
  padding-left: 10px;
}
.phone-item{
  box-sizing: border-box;
  width: 260px;
  height: 350px;
  border: 1px solid #d0d0d0;
  margin: 10px 20px;
}
.phone-item:hover{
  border: 1px solid #f66622;
}
.phone-item img{
  width: 250px;
  height: 240px;
}
.item-des{
  height: 110px;
}
.details-content{
  width: 750px;
  padding: 20px;
  margin: 0 auto;
}
.default-container .item-des{
  line-height: 90px;
}
.phone-item .price, .phone-item .rating{
  display: inline-block;
  font-size: 24px;
  font-weight: 700;
  color: #ef6102;
  margin: 10px 20px 0 0;
}
.phone-item .stock{
  font-size: 24px;
  font-weight: 700;
  color: #252525;
}

.filter-condition{
  display: flex;
  align-items: center;
  margin: 20px 0;
}
.phone-item-title{
  overflow:hidden;
  text-overflow:ellipsis;
  display:-webkit-box;
  -webkit-box-orient:vertical;
  -webkit-line-clamp:2;
}
.filter-price{
  width: 300px;
  margin-right: 50px;
  display: flex;
  align-items: center;
}
.filter-price>span, .filter-brand>span{
  font-size: 24px;
  color: #5e2ca5;
  margin-right: 20px;
}
.block{
  width: 200px;
}
.confirm-btn{
  margin-left: 20px;
}
.details-container{
  position: relative;
}
.go-back{
  position: absolute;
  left: 40px;
  top: 30px;
  font-size: 24px;
  cursor: pointer;
}
.go-back:hover{
  color: #1482f0;
}
.details-docker{
  display: flex;
  justify-content: center;
  padding-top: 80px;
}
.details-left-part img{
  width: 300px;
  height: 330px;
  border: 1px solid gainsboro;
}
.details-right-part{
  width: 400px;
  margin-left: 20px;
  text-align: left;
  font-size: 24px;
  line-height: 40px;
}
.item-details-title{
  font-weight: 700;
}
.item-details-value{
  display: inline-block;
  margin-left: 30px;
}
.el-input-number{
  margin-left: 30px;
}
.add-to-cart .el-button{
  margin-top: 20px;
  color: #ce4500;
}
.no-data{
  color: #7d8492;
  text-align: center;
  margin: 30px 0;
  width: 100%;
  font-size: 20px;
}
</style>