const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave : false, //요놈 안해주면 eslint error
  devServer: {  //Proxy 설정. (백엔드랑 통신)
    proxy: {
      '/api': { // '/db' ~ 으로 요청이 오면 'http://localhost:8090/' 로 보내
        //target: 'http://192.168.219.76:8090',
        target: 'http://localhost:8090',
        changeOrigin: true,  //cors 사용 하겠다.
        withCredentials : true,
        pathRewrite : { // '/api' 로 시작하는 문자를 '' 로 변경
          '^/api' : ''
        }
      }
    }
  }

})
