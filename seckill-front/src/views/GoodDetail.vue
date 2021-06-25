<template>
  <div>
    <vHeader></vHeader>
    <el-card class="box-card">
      <div :goodDetail="goodDetail" class="text item">
        <div class="text item">商品名称：{{ goodDetail.name }}</div>
        <div class="text item">商品详情：{{ goodDetail.good.introduce }}</div>
        <div class="text item">商品单价：{{ goodDetail.price }}</div>
        <div class="text item seckill">秒杀价 ：{{ goodDetail.secPrice }}</div>
        <el-divider></el-divider>
        <div id="secbutton">
          <el-button
            type="danger"
            v-if="status == 0"
            size="medium"
            icon="el-icon-time"
            disabled
          >
            <span style="font-size: 25px">
              秒杀倒计时： {{ hour }}:{{ minutes }}:{{ seconds }}</span
            >
          </el-button>
          <el-button type="success" v-if="status == 1" @click="seckill()">
            <span style="font-size: 25px">立即秒杀 </span>
          </el-button>
          <el-button type="success" v-if="status == 2" disabled>
            <span style="font-size: 25px">秒杀活动已结束</span>
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import vHeader from "../components/Header.vue";
// import { MessageBox } from "element-ui";
export default {
  components: {
    vHeader,
  },
  data() {
    return {
      status: 0,

      seckillForm: {},

      totalTime: 0,
      lastTime: 0,
      hour: 0,
      minutes: 0,
      seconds: 0,
      goodDetail: {},
    };
  },

  created() {
    this.getGoodDetail();
    if (this.$store.getters.getUser.id) {
      this.seckillForm.userId = this.$store.getters.getUser.id;
    }

    // this.caculTotalTime();
    // this.countDown();
  },

  methods: {
    getGoodDetail() {
      const _this = this;
      const temp = this.$route.params.id;
      this.$axios.get(`/secgood/${temp}`).then((res) => {
        if (res.data.code == 200) {
          _this.goodDetail = res.data.data;
          console.log(2222);
          console.log(_this.goodDetail);

          _this.caculTotalTime();

          // this.reload();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },

    caculTotalTime() {
      const _this = this;
      this.totalTime = new Date(_this.goodDetail.startTime) - new Date();
      this.lastTime = new Date(_this.goodDetail.endTime) - new Date();

      if (_this.lastTime < 0) {
        _this.status = 2;
        return;
      }
      _this.totalTime = Math.floor(_this.totalTime / 1000);
      _this.lastTime = Math.floor(_this.lastTime / 1000);
      _this.countDown();

      console.log(this.totalTime);
      console.log(this.lastTime);
    },

    countDown() {
      const _this = this;
      let clock = window.setInterval(() => {
        if (this.totalTime < 0) {
          //当倒计时小于0时清除定时器
          _this.status = 1;
          window.clearInterval(clock);
          // 再开启一个计时器判断是否结束
          let clock2 = window.setInterval(() => {
            if (this.lastTime < 0) {
              _this.status = 2;
              window.clearInterval(clock2);
            } else {
              _this.lastTime--;
            }
          }, 1000);
        } else {
          _this.calTime(_this.totalTime);
          _this.totalTime--;
        }
      }, 1000);
    },

    calTime(time) {
      const _this = this;
      // var day = Math.floor(time / 60 / 60 / 24);
      var hour = Math.floor(time / 60 / 60); //小时
      var minutes = Math.floor((time / 60) % 60); //分钟
      var seconds = Math.floor(time % 60); //秒
      // _this.day = day;
      _this.hour = _this.formatTime(hour);
      _this.minutes = _this.formatTime(minutes);
      _this.seconds = _this.formatTime(seconds);
    },

    formatTime(t) {
      if (t < 10) {
        t = "0" + t;
      }
      return t;
    },

    seckill() {
      console.log(this.goodDetail);
      const _this = this;
      const secId = _this.goodDetail.id;
      this.$axios.get(`/exposer/${secId}`).then((res) => {
        if (res.data.code == 400) {
          // 调整时间
          _this.totalTime = res.data.start - res.data.now;
          _this.lastTime = res.data.end - res.data.now;
        } else {
          const md5 = res.data.data.md5;
          _this.seckillForm.secId = secId;
          _this.seckillForm.md5 = md5;
          this.$axios.post("/seckill", _this.seckillForm).then((res) => {
            if (res.data.code == 200) {
              const loading = this.$loading({
                lock: true,
                text: "排队中",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.6)",
              });
<<<<<<< HEAD
              const interval1 =setInterval(() => {
                // 轮询查看订单是否完成
                setTimeout(() => {
                  clearInterval(interval1)
                }, 10000);
                _this.$axios
                  .post("/secorder/selectEntry", _this.seckillForm)
                  .then((res) => {
                    if (res.data.code == 200) {
                      clearInterval(interval1)
                      loading.close();
                      this.$confirm("秒杀成功", "提示", {
                        confirmButtonText: "查看订单",
                        cancelButtonText: "取消",
                        type: "success",
                      })
                        .then(() => {
                          this.$router.push({
                            path: "/secorder/",
                          });
                        })
                        .catch(() => {
                          this.$message({
                            type: "info",
                            message: "已取消",
                          });
                        });
                    }
                  });
                
=======
              setTimeout(() => {
                loading.close();
                this.$confirm("秒杀成功", "提示", {
                  confirmButtonText: "查看订单",
                  cancelButtonText: "取消",
                  type: "success",
                })
                  .then(() => {
                    this.$router.push({
                      path: "/secorder/",
                    });
                  })
                  .catch(() => {
                    this.$message({
                      type: "info",
                      message: "已取消",
                    });
                  });
>>>>>>> 48dce43b932f0e08bb5254a07700159f3fbae7e0
              }, 2000);
            } else {
              this.$message.error(res.data.msg);
            }
          });
        }
      });
    },
  },
};
</script>

<style>
.text {
  font-size: 16px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
}

.seckill {
  color: red;
}

.seckill2 {
  text-align: center;
  font-size: 38px;
  color: red;
}

#secbutton {
  display: flex;
  justify-content: center;
  /* margin: 0% auto;/ */
}
</style>