
<template>
  <div>
    <vHeader></vHeader>

    <el-table
      border
      size="medium"
      :data="
        tableData.filter(
          (data) =>
            !search || data.name.includes(search)
        )
      "
      style="width: 80%; margin-left: auto; margin-right: auto"
    >
      <el-table-column width="120" label="商品ID" prop="id" align="center">
      </el-table-column>
      <el-table-column label="商品名称" prop="name" align="center">
      </el-table-column>
      <el-table-column
        width="120"
        label="库存数量"
        prop="stock"
        align="center"
      >
      </el-table-column>
      <el-table-column label="商品图片" prop="img" align="center">
      </el-table-column>
      <el-table-column label="商品简介" prop="introduce" align="center">
      </el-table-column>
      <el-table-column width="120" label="商品单价" prop="price" align="center">
      </el-table-column>
      <el-table-column align="center">
        <template slot="header" slot-scope>
          <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="addToSeckill(scope.$index, scope.row)"
            >添加到秒杀列表</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
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

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editable" width="34%">
      <el-form
        :label-position="labelPosition"
        label-width="80px"
        :model="secgood"
      >
        <el-form-item label="商品ID">
          <el-input v-model="secgood.goodId"></el-input>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="secgood.name"></el-input>
        </el-form-item>
        <el-form-item label="原价">
          <el-input v-model="secgood.price"></el-input>
        </el-form-item>
        <el-form-item label="秒杀价">
          <el-input v-model="secgood.secPrice"></el-input>
        </el-form-item>
        <el-form-item label="秒杀数目">
          <el-input v-model="secgood.stock"></el-input>
        </el-form-item>
        <el-form-item label="秒杀时间">
          <el-date-picker
            v-model="time"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            size="small"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editable = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>
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
      time: [],
      pickerOptions: {},
      editable: false,
      secgood: {},
      tagform: {
        id: null,
        name: "",
      },
      labelPosition: "right",
      formLabelAlign: {
        name: "",
        region: "",
        type: "",
      },

      tableData: [
        // {
        //   id: 1,
        //   name: "外星人笔记本电脑",
        //   number: 100,
        //   img: undefined,
        //   introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
        //   price: 15999,
        // },
        // {
        //   id: 1,
        //   name: "外星人笔记本电脑",
        //   number: 100,
        //   img: undefined,
        //   introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
        //   price: 15999,
        // },
        // {
        //   id: 1,
        //   name: "外星人笔记本电脑",
        //   number: 100,
        //   img: undefined,
        //   introduce: "外星人笔记本巴拉巴拉巴拉巴拉巴拉巴拉巴拉",
        //   price: 15999,
        // },
      ],
      search: "",
    };
  },
  created() {
    this.selectList();
  },
  methods: {
    selectList(){
       const _this = this;
       this.$axios.get("/good/list?curpage=1&size=100").then((res) => {
        if (res.data.code == 200) {
          _this.tableData=res.data.data.list;
          
          // this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },

    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    addToSeckill() {
      this.editable = true;
    },

    saveEdit() {
      const _this = this;
      _this.secgood.startTime = _this.time[0];
      _this.secgood.endTime = _this.time[1];

      console.log(this.secgood);
      this.$axios.post("/secgood/add", _this.secgood).then((res) => {
        if (res.data.code == 200) {
          this.$message.success("添加成功");
          _this.editable = false;
          _this.secgood={};
          _this.time=[];
          // this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      });
      // .catch((err) => {
      //   this.$message.error("不要再试了哦，没有权限");
      // });
    },
  },
};
</script>

<style>
</style>