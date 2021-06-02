<template>
  <div id="head">
    <div>
      <el-tabs v-model="activeName" @tab-click="toGoodList">
        <el-tab-pane label="商品列表" name="goodlist"></el-tab-pane>
        <el-tab-pane label="秒杀列表" name="seclist"></el-tab-pane>
        <el-tab-pane
          label="商品详情"
          name="good"
          disabled="fasle"
        ></el-tab-pane>
        <el-tab-pane label="秒杀订单" name="secorder"></el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: "first",
    };
  },
  methods: {
    toGoodList(tab) {
      this.$router.push({
        path: tab.name,
      });
    },
  },
};
</script>

<style>
#head {
  display: flex;
  justify-content: center;
}
</style>