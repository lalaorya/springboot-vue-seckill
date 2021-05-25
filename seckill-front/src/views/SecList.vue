
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
      style="width: 80%; margin-left: auto; margin-right: auto"
    >
      <el-table-column label="商品ID" prop="id" align="center" width="120">
      </el-table-column>
      <el-table-column label="商品名称" prop="name" align="center">
      </el-table-column>
      <el-table-column
        label="库存数量"
        prop="number"
        align="center"
        width="120"
      >
      </el-table-column>
      <el-table-column label="原价" prop="price" align="center" width="120">
      </el-table-column>
      <el-table-column
        label="秒杀价"
        prop="sec_price"
        align="center"
        width="120"
      >
      </el-table-column>
      <el-table-column label="秒杀开始时间" prop="start_time" align="center">
      </el-table-column>
      <el-table-column label="秒杀结束时间" prop="end_time" align="center">
      </el-table-column>
      <el-table-column align="center">
        <template slot="header" slot-scope>
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="handletoDetail(scope.$index, scope.row)"
            >进去秒杀</el-button
          >
          <!-- <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >编辑</el-button
          > -->
          <!-- <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
          > -->
        </template>
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
      tableData: [
        {
          id: 1,
          name: "外星人笔记本电脑",
          number: 100,
          price: 15999,
          sec_price: 5999,
          start_time: "2021-05-24 18:00",
          end_time: "2021-05-24 23:00",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄",
        },
      ],
      search: "",
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handletoDetail(index, row) {
      this.$router.push({
        path: "/good/"+row.id,
      });
    },
  },
};
</script>

<style>
</style>