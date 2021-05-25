
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
      <el-table-column width="120" label="商品ID" prop="id" align="center">
      </el-table-column>
      <el-table-column label="商品名称" prop="name" align="center">
      </el-table-column>
      <el-table-column
        width="120"
        label="库存数量"
        prop="number"
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
            @click="handleDelete(scope.$index, scope.row)"
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
        :model="formLabelAlign"
      >
        <el-form-item label="商品ID">
          <el-input v-model="formLabelAlign.name"></el-input>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="formLabelAlign.region"></el-input>
        </el-form-item>
        <el-form-item label="原价">
          <el-input v-model="formLabelAlign.type"></el-input>
        </el-form-item>
        <el-form-item label="秒杀价">
          <el-input v-model="formLabelAlign.type"></el-input>
        </el-form-item>
        <el-form-item label="秒杀数目">
          <el-input v-model="formLabelAlign.type"></el-input>
        </el-form-item>
        <el-form-item label="秒杀时间">
          <el-date-picker
            v-model="value2"
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
      editable: true,
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