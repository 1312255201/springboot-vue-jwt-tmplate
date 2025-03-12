<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden; display: flex">
    <div style="flex: 1; background-color: black">
      <el-image src="bg.png" fit="cover" style="width: 100%;height:100%"></el-image>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">这是一个前后端分离开发的模板</div>
      <div style="margin-top: 10px">只有一些基础功能，没有业务功能qwq</div>
      <div style="margin-top: 5px">Enjoy It!</div>
    </div>
    <div class="right-card">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>
.welcome-title{
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
.right-card{
  width: 400px;
  z-index: 1;
  background-color: white;
}
</style>