<template>
  <div>
    <vHeader></vHeader>

    <el-table
      border
      size="medium"
      max-height=620px
      :data="
        tableData.filter(
          (data) =>
            !search || data.name.toLowerCase().includes(search.toLowerCase())
        )
      "
      style="width: 80%; margin-left: auto; margin-right: auto"
    >
      <el-table-column label="秒杀流水号" prop="id" align="center" width="120">
      </el-table-column>
      <el-table-column label="商品ID" prop="goodId" align="center" width="120">
      </el-table-column>
      <el-table-column label="商品名称" prop="name" align="center">
      </el-table-column>
      <el-table-column label="库存数量" prop="stock" align="center" width="120">
      </el-table-column>
      <el-table-column label="原价" prop="price" align="center" width="120">
      </el-table-column>
      <el-table-column
        label="秒杀价"
        prop="secPrice"
        align="center"
        width="120"
      >
      </el-table-column>
      <el-table-column label="秒杀开始时间" prop="startTime" align="center">
      </el-table-column>
      <el-table-column label="秒杀结束时间" prop="endTime" align="center">
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
          <el-button
            size="mini"
            type="success"
            @click="handlePrepare(scope.$index, scope.row)"
            >库存预热</el-button
          >
          <!-- <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >Delete</el-button
          > -->
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
    background=true
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[100, 200, 300, 400]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalNum"
      style="float: right; padding: 20px 90px 20px 0px"
    >
    </el-pagination>
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
      pagesize: 100,
      totalNum: 100,
      screenHeight: "100px",
      currentPage: 1,
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

  created() {
    this.selectList(this.currentPage,this.pagesize);
  },

  methods: {
    selectList(val,pagesize) {
      const _this = this;
      this.$axios.get("/secgood/list?curpage="+val+"&size="+pagesize).then((res) => {
        if (res.data.code == 200) {
          _this.tableData = res.data.data.list;
          _this.totalNum = res.data.data.total
          // this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },

    handlePrepare(index, row) {
      this.$axios.get("/secgood/prepare?id="+row.id).then((res) => {
        if (res.data.code == 200) {
          this.$message.success(res.data.msg);
          // this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
    handletoDetail(index, row) {
      this.$router.push({
        path: "/good/" + row.id,
      });
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pagesize = val;
      this.handleCurrentChange(1);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.selectList(val, this.pagesize);
    },
  },
};
</script>

<style>
</style>