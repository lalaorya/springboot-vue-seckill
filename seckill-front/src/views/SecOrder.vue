
<template>
  <div>
    <vHeader></vHeader>

    <el-table
      border
      size="medium"
      :data="
        tableData.filter(
          (data) =>
            !search || data.name.toLowerCase().includes(search.toLowerCase())
        )
      "
      style="width: 50%; margin-left: auto; margin-right: auto"
    >
      <el-table-column label="订单流水号" prop="id" align="center">
      </el-table-column>
      <el-table-column label="秒杀商品ID" prop="good_id" align="center">
      </el-table-column>
      <el-table-column label="用户ID" prop="user_id" align="center">
      </el-table-column>
      <el-table-column label="订单状态" prop="status" align="center">
        <el-tag :item="items" :key="items.label" :type="items.type" effect="dark">
          {{ items.label }}
        </el-tag>
      </el-table-column>
      <el-table-column label="订单创建时间" prop="create_time" align="center">
      </el-table-column>
            <el-table-column label="付款时间" prop="pay_time" align="center">
      </el-table-column>
    </el-table>
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
      items: { type: "success", label: "标签一" },
      tableData: [
        {
          id: 1,
          name: "外星人笔记本电脑",
          number: 100,
          img: undefined,
          introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
          price: 15999,
        },
        {
          id: 1,
          name: "外星人笔记本电脑",
          number: 100,
          img: undefined,
          introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
          price: 15999,
        },
        {
          id: 1,
          name: "外星人笔记本电脑",
          number: 100,
          img: undefined,
          introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
          price: 15999,
        },
      ],
      search: "",
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    // addToSeckill(index,row){

    // }
  },
};
</script>

<style>
</style>