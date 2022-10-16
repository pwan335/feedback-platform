const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  lintOnSave:false,
  transpileDependencies: true,
  publicPath: "/",
  devServer: {
    hot: true,
    proxy: {
      "/api": {
        target: "http://localhost:8080", // 需要代理访问的api地址
        changeOrigin: true, // 允许跨域请求
        pathRewrite: {
          // 重写路径，替换请求地址中的指定路径
          "^/api": "/", // 将请求地址中的/api替换为空，也就是请求地址中不会包含/api/
        },
      },
      "/public": {
        target: "http://pv.sohu.com/cityjson?ie=utf-8", // 需要代理访问的api地址
        changeOrigin: true, // 允许跨域请求
        pathRewrite: {
          // 重写路径，替换请求地址中的指定路径
          "^/public": "", // 将请求地址中的/api替换为空，也就是请求地址中不会包含/api/
        },
      },
      "/weather": {
        target: "http://apis.juhe.cn/simpleWeather/query", // 需要代理访问的api地址
        changeOrigin: true, // 允许跨域请求
        pathRewrite: {
          // 重写路径，替换请求地址中的指定路径
          "^/weather": "", // 将请求地址中的/api替换为空，也就是请求地址中不会包含/api/
        },
      },
    }
  },
  chainWebpack: config => {
    config.module
        .rule("css")
        .test(/\.css$/)
        .oneOf("vue")
        .resourceQuery(/\?vue/)
        .use("px2rem")
        .loader("px2rem-loader")
        .options({
          remUnit: 192 // 设计稿大小比例 / 10
        });
  }
})
