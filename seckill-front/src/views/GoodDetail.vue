<template>
  <div>
    <vHeader></vHeader>
    <el-card class="box-card">
      <div :goodDetail="goodDetail" class="text item">
        <div class="text item">商品名称：{{ goodDetail.name }}</div>
        <div class="text item">商品详情：{{ goodDetail.name }}</div>
        <div class="text item">商品单价：{{ goodDetail.name }}</div>
        <div class="text item seckill">秒杀价 ：{{ goodDetail.name }}</div>
         <el-divider></el-divider>
        <div class="text item seckill2">秒杀倒计时{ }}</div>
        <el-button type="success" >开始秒杀</el-button>
        <div class="text item seckill2">秒杀结束</div>
      </div>
    </el-card>
  </div>
</template>

<script>
import vHeader from "../components/Header.vue";
export default {
  components: {
    vHeader,
  },
  data() {
    return {
      goodDetail: {
        name: "xiaomi",
      },
    };
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
}

.seckill{
    color: red;
}

.seckill2{
    text-align: center;
    font-size: 28px;
    color: red;
}
</style>
