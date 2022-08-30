<template>
  <div>
    <div class="login" >
      <div class="login_form" v-if="showLogin">
        <div class="login-title">Log In</div>
        <el-input class="user icon"  placeholder="Email" v-model="email"></el-input>
        <el-input type="password"  class="password icon"  placeholder="Password" v-model="password"></el-input>
        <el-button class="login_btn" @click.native="login" type="primary" round :loading="isBtnLoading">Login</el-button>
        <div class="login_bottom">
          <span class="sign_up" @click="toSignup">Sign-up</span>
          <span class="forgot_password" @click="forgot">Forgot Password</span>
        </div>
      </div>
      <div class="login_form" v-if="showSignUp">
        <div class="signUp_form">
          <div class="login-title">Sign Up</div>
          <el-input class="input"  placeholder="First name" v-model="firstName"></el-input>
          <el-input class="input"  placeholder="Last name" v-model="lastName"></el-input>
          <el-input  class="input"  placeholder="Email" v-model="newEmail"></el-input>
          <el-input  class="input"  type="password" placeholder="Password" v-model="newPassword"></el-input>
          <div class="tips">Tips: Password must contain uppercase letters, lowercase letters and numbers, and be 6-12 digits in length</div>
          <el-button class="signup_btn" @click.native="signUp" type="primary" round :loading="isBtnLoading">Sign Up</el-button>
          <div class="signin_btn">
            <span @click="signIn">Sign In</span>
          </div>
        </div>
      </div>
<!--      <div class="login_form" v-if="showResetPas">-->
<!--        <div>-->
<!--          <div class="login-title">Reset password</div>-->
<!--          <div class="resetPass_form">-->
<!--            <el-input class="input"  placeholder="New Password" v-model="reNewPas"></el-input>-->
<!--            <el-input class="input"  placeholder="Confirm Password" v-model="reConPas"></el-input>-->
<!--            <el-button class="signup_btn" @click.native="resetPass" type="primary" round :loading="isBtnLoading">Reset</el-button>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
import {forgetPassword, signInAction, signUpAction} from "@/api/user";
import md5 from 'js-md5'
import {validPassword} from "@/utils";

export default {
  name: "LoginPage",
  data() {
      return {
        showLogin: true,
        showSignUp: false,
        showResetPas: false,
        email: '',
        password: '',
        isBtnLoading: false,

        firstName:'',
        lastName:'',
        newEmail:'',
        newPassword:'',

        reNewPas:'',
        reConPas:'',
        verEmail:''


      }
    },
    created () {
      // if(JSON.parse( localStorage.getItem('user')) && JSON.parse( localStorage.getItem('user')).email){
      //   this.email = JSON.parse( localStorage.getItem('user')).email;
      //   this.password = JSON.parse( localStorage.getItem('user')).password;
      // }
    },
    computed: {
      btnText() {
        if (this.isBtnLoading) return 'Login...';
        return 'Login';
      }
    },
    methods: {
      login() {
        if (!this.email) {
          this.$message.error('Please input your email');
          return;
        }else{
          var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
          if(!regEmail.test(this.email)){
            this.$message({
              message: 'The email format is incorrect',
              type: 'error'
            })
            this.email = '';
            return;
          }
        }
        if (!this.password) {
          this.$message.error('Please input your password');
          return;
        }
      //  Login request actions
        const userInfo = {
          "email": this.email,
          "password": md5(this.password)
        }
        signInAction(userInfo).then(res=>{
          if(res.success) {
            res.data.accessToken && sessionStorage.setItem('token', res.data.accessToken)
            res.data.user && sessionStorage.setItem('user', JSON.stringify(res.data.user))
            // After login successfully
            this.$message.success('log in successfuly')
            this.$emit('login', res.data.user)

            // go back to the last page
            let queryData = sessionStorage.getItem('querydata')
            if(queryData) {
              queryData = JSON.parse(queryData)
              let path = queryData.path
              if(path === 'details') {
                // Back to details page
                this.$emit('switchTab', 0)
              } else if(path === 'profile') {
                this.$emit('switchTab', 4)
                sessionStorage.removeItem('querydata')
              }
            }

          } else {
            this.$message.error(res.message)
          }
          }, err=>{
              console.log(err);
          }
        )
      },

      toSignup(){
        this.showLogin = false;
        this.showSignUp = true;
        this.showResetPas = false;
      },

      forgot(){
        // this.showLogin = false;
        // this.showSignUp = false;
        // this.showResetPas = true;
        this.$prompt('Please input your email to reset password', 'Email authentication', {
          confirmButtonText: 'OK',
          cancelButtonText: 'Cancel',
          inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
          inputErrorMessage: 'Invalid Email'
        }).then(({ value }) => {
          // this.$message({
          //   type: 'success',
          //   message: 'Your email is:' + value
          // });
          this.verEmail = value;
          this.verifyEmail(value);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Input canceled'
          });
        });
      },

      signIn(){
        this.showLogin = true;
        this.showSignUp = false;
        this.showResetPas = false;
      },
      signUp(){
        if (!this.firstName) {
          this.$message.error('Please input your first name');
          return;
        }
        if (!this.lastName) {
          this.$message.error('Please input your last name');
          return;
        }

        if (!this.newEmail) {
          this.$message.error('Please input your email');
          return;
        }else{
          var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
          if(!regEmail.test(this.newEmail)){
            this.$message({
              message: 'The email format is incorrect',
              type: 'error'
            })
            this.newEmail = '';
            return;
          }
        }

        if (!this.newPassword) {
          this.$message.error('Please input your password');
          return;
        }else{
          if(!validPassword(this.newPassword)) {
            this.$message.warning('Wrong password format')
            return;
          }
        }

        //  signup request actions
        const userInfo = {
          "firstname": this.firstName,
          "lastname": this.lastName,
          "email": this.newEmail,
          "password": md5(this.newPassword)
        }
        signUpAction(userInfo).then(res=>{
          if(res.success) {
            this.$message.success('Sign up successfully! Go to verify!');
            this.showLogin = true;
            this.showSignUp = false;
          }else{
            this.$message.error(res.message);
          }
        }, err=>{
          this.$message.error(err)
        })

        // After login successfully
        this.$emit('sign-up');
      },

      verifyEmail(val){
        let info ={
          "email" : val,
          "route": `http://localhost:8090/#/reset-password?email=${val}`
        };
        // Write an api to verify email and send reset password link in the past
        forgetPassword(info).then(res => {
          console.log(res)
          if(res.success){
            this.$message.success('Confirm your email successfully, go to verify!');
          }else{
            this.$message.error(res.message);
          }
          console.log(res);
        }, err => {
          console.log(err);
        });
      },

    }
}

</script>

<style scoped>

.icon {
  height: 40px;
  width: 50%;
  margin-bottom: 5px;
  padding-left: 8%;
  border: 0;
}

.login{
  width: 100%;
  height: auto;
  min-height: 560px;
  padding-top: 5%;
  padding-bottom: 5%;
  /*background: rgba(210, 225, 253, 0.98);*/
  background: linear-gradient(to right, rgba(153, 184, 248, 0.99), #cfdfff);
}
.login_form {
  margin-left: 45%;
  margin-right: 10%;
  /*float: left;*/
  /*width: 60%;*/
  padding-top: 5%;
  padding-bottom: 2%;
  background: white;
  border: 1px solid #d0d0d0;
}
.user {
  background: url("../../assets/loginPage_images/email.png") no-repeat;
  background-size: 32px 32px;
  background-position: 3%;
}
.password {
  background: url("../../assets/loginPage_images/password.png") no-repeat;
  background-size: 30px 30px;
  background-position: 3%;
  margin-top: 10px;
  margin-bottom: 20px;
}
.login-title{
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 3%;
  color: #2e62b5;
}
.login_logo {
  height: 100%;
}
.login_btn {
  margin-left: 5%;
  width: 50%;
  font-size: 16px;
  background: linear-gradient(to right, #000099 , #2154FA);
  filter: brightness(1.4);
}
.login_bottom{
  margin-top: 5%;
  margin-left: 5%;
}
.sign_up{
  cursor: pointer;
  margin-top: 5%;
  margin-right: 25%;
  font-size: 18px;
  font-weight: 500;
  color: #2e62b5;

}
.forgot_password{
  cursor: pointer;
  padding-top: 5%;
  font-size: 18px;
  font-weight: 500;
  color: #2e62b5;
}

.signUp_form{
  padding-left: 5%;
  padding-right: 5%;
  box-sizing: border-box;
}

.login-title{
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 3%;
  color: #2e62b5;
}
.input{
  margin-bottom: 3%;
}
.signin_btn{
  cursor: pointer;
  padding-top: 3%;
  width: 30%;
  font-size: 18px;
  font-weight: 500;
  color: #2e62b5;

}
.signup_btn{
  margin-top: 3%;
  margin-left: 5%;
  width: 50%;
  font-size: 16px;
  background: linear-gradient(to right, #000099 , #2154FA);
  filter: brightness(1.4);
}
.resetPass_form{
  padding-left: 10%;
  padding-right: 10%;
  height: 200px;
  box-sizing: border-box;
}
.tips{
  margin-bottom: 20px;
  color: coral;
  font-size: 10px;
  text-align: left;
}
</style>