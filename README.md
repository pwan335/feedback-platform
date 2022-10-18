# EasyFeedback
Object Oriented Application Framework Project



## Project Functionalities

- Topic

  1. Show the recommended(hot) topic

  2. Show the latest topic

  3. Show details of the topic (including topic name, content, photos and comments)

  4. Search topic by topic name

  5. Using paging to display the topic list and comments (two levels)

     

- Client (user): 

  1. Register
  2. Login
  3. Forget password
  4. View details of the topic
  5. Actions with the topic
     1. Add comment to the topic
     2. Collect the topic
     3. Like the topic
  6. Show the use's profile
  7. Chang profile (including the user's avatar)
  8. Change password
  9. View topics that users have liked, collected and commented on



- Business(pm)
  1. Register
  2. Login
  3. Forget password
  4. Chang profile (including the pm's avatar)
  5. Change password
  6. Create topics
  7. Manage topic
  8. View details of the topic
  9. Communicate with users using comments



## Servers

### 1 Libraries

```
java 1.8
springboot 2.7.3
mybatis 2.2.2
druid 1.1.16
lombok 1.18.12
slf4j 1.7.20
logback 1.2.3
java-jwt 3.9.0
json 20190722
mysql 8.0.27
httpclient 4.4
httpmime 4.4
pagehelper 1.4.1
```



### 2 A quick guide to run

- run the main function in EasyfeedbackApplication.java

  ```
  package com.elec5619;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class EasyfeedbackApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(EasyfeedbackApplication.class, args);
      }
  }
  ```

  





## Client

### 1 Libraries

```
{
		"axios": "^0.26.1",
    "core-js": "^3.8.3",
    "element-ui": "^2.15.6",
    "install": "^0.13.0",
    "js-md5": "^0.7.3",
    "lib-flexible": "^0.3.2",
    "vue": "^2.6.14",
    "vue-router": "^3.2.0",
    "vuex": "^3.2.0"
}
```



### 2 A quick guide to run

#### Project setup

```
npm install
```

#### Compiles and hot-reloads for development

```
npm run serve
```

### 
