
<template>
  <div>
    <vHeader></vHeader>

    <el-table
      border
      size="medium"
      max-height="650px"
      :data="
        tableData.filter(
          (data) =>
            !search || data.name.toLowerCase().includes(search.toLowerCase())
        )
      "
      style="width: 70%; margin-left: auto; margin-right: auto"
    >
      <el-table-column label="订单流水号" prop="id" align="center">
      </el-table-column>
      <el-table-column label="秒杀商品ID" prop="secId" align="center">
      </el-table-column>
      <el-table-column width="120" label="用户ID" prop="userId" align="center">
      </el-table-column>
      <el-table-column label="订单状态" prop="status" align="center">
        <el-tag
          :item="status"
          :key="items.label"
          :type="items.type"
          effect="dark"
        >
          {{ items.label }}
        </el-tag>
      </el-table-column>
      <el-table-column
        width="180"
        label="订单创建时间"
        prop="createTime"
        align="center"
      >
      </el-table-column>
      <el-table-column label="付款时间" prop="payTime" align="center">
      </el-table-column>

      <el-table-column align="center">
        <template slot="header" slot-scope>
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="toPay(scope.$index, scope.row)"
            >去付款</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      background="true"
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

      items: { type: "success", label: "2321" },
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
  created() {
    this.selectList(this.currentPage, this.pagesize);
  },
  methods: {
    selectList(val, pagesize) {
      const _this = this;
      this.$axios
        .get("/secorder/list?curpage=" + val + "&size=" + pagesize)
        .then((res) => {
          if (res.data.code == 200) {
            _this.tableData = res.data.data.list;
            _this.totalNum = res.data.data.total;
            // this.reload();
          } else {
            this.$message.error(res.data.msg);
          }
        });
    },

    toPay(index, row) {
      const _this = this;
      console.log(row);
      this.$axios
        .put("/secorder/payById?id=" + row.id)
        .then((res) => {
          if (res.data.code == 200) {
            // this.reload();
            _this.$message.success(res.data.msg);
            row.payTime=res.data.data;
          } else {
            _this.$message.error(res.data.msg);
          }
        });
    },
    handleDelete(index, row) {
      console.log(index, row);
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