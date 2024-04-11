const { defineConfig } = require('@vue/cli-service')
const path = require("path"); //1번 빌드경로 위치변경

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave : false,
  outputDir: path.resolve(__dirname, "../backend/dist"),
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:3000"
      }
    }
  }

})
