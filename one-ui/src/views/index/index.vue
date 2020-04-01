<template>
  <div v-loading="loading" style="padding: 10px">
    <el-card>
      <el-row>
        <el-col :md="12" :xs="24" :sm="24">
          <div class="head-avatar">
            <el-image fit="scale-down" style="width: 80px;height: 80px;border-radius: 5px"
                      :src="IMG_URL+avatar"></el-image>
          </div>
          <div class="head-info">
            <div class="head-welcome">
              <span>{{title}}</span>
              <span style="color: #2db7f5">{{info.username}}，</span>
              <span>{{message}}</span>
            </div>
            <div class="head-desc">
              {{info.roles}} | 上次登陆IP:{{info.ip}}
            </div>
            <div class="head-time">
              <span>上次登录时间：</span>{{info.lastLoginTime}}
            </div>
          </div>
        </el-col>
        <el-col :md="6" :xs="24" :sm="12">
          <el-col :xs="12" :sm="12" style="padding: 10px">
            <div class="other-info">
              <span>上次登录地点</span>
            </div>
            <div class="other-info">
              <a>{{info.address}}</a>
            </div>
          </el-col>
          <el-col :xs="12" :sm="8" style="padding: 10px">
            <div class="other-info">
              <span>网络类型</span>
            </div>
            <div class="other-info">
              <a>{{info.isp}}</a>
            </div>
          </el-col>
        </el-col>
        <el-col :md="6" :xs="24" :sm="12">
          <el-col :xs="12" :sm="12" style="padding: 10px">
            <div class="other-info">
              <span>总访问量</span>
            </div>
            <div class="other-info">
              <a>{{loginCount}}</a>
            </div>
          </el-col>
          <el-col :xs="12" :sm="8" style="padding: 10px">
            <div class="other-info">
              <span>IP量</span>
            </div>
            <div class="other-info">
              <a>{{ipCount}}</a>
            </div>
          </el-col>
        </el-col>
      </el-row>
    </el-card>

    <el-row :gutter="10" style="margin-top: 10px">
      <el-col :sm="8" :xs="24">
        <el-card>
          <div id="pie" :style="{height: barHeight,width: '100%'}"></div>
        </el-card>
      </el-col>
      <el-col :sm="16" :xs="24">
        <el-card>
          <div id="bar" :style="{height: barHeight,width: '100%'}"></div>
        </el-card>
      </el-col>
    </el-row>


  </div>


</template>
<script>
  import {getBarChart, getInfo, getLoginInfo, getRegionalDistribution} from "@/api";
  import {mapGetters} from 'vuex'

  export default {
    name: "index",//主页
    data() {
      return {
        IMG_URL: process.env.IMG_URL,
        loading: false,
        info: {},
        myChart: null,
        pieChart: null,
        loginCount: null, ipCount: null
      }
    },
    watch: {
      isReSize(val) {
        if (this.myChart) {
          //这里有个bug 直接resize无效，定时就行
          setTimeout(() => {
            this.myChart.resize();
          }, 100)
        }
      }
    },
    mounted() {
      this.getInformation();
      this.getVisitInfo();
      this.getChart();
      this.showLineChart()
      this.regionalDistribution();
    },
    methods: {
      regionalDistribution() {
        let _this = this;
        getRegionalDistribution().then(resp => {
          _this.showLineChart(resp.data)
        })
      },
      getVisitInfo() {
        let _this = this;
        getLoginInfo().then(resp => {
          _this.loginCount = resp.data.loginCount;
          _this.ipCount = resp.data.ipCount;
        })
      },
      getInformation() {
        let _this = this;
        _this.loading = true;
        getInfo().then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.info = resp.data;
          }
        })
      },
      getChart() {
        let _this = this;
        getBarChart(_this.name).then(resp => {
          if (resp.code === 200 && resp.data) {
            let timeArr = [];
            let dataArr1 = [];
            let dataArr2 = [];
            resp.data.forEach(item => {
              timeArr.push(item.time);
              dataArr1.push(item.count);
              dataArr2.push(item.user);
            })
            _this.showChart(timeArr, dataArr1, dataArr2)
          }
        })
      },
      showChart(timeArr, data1, data2) {
        this.myChart = this.$echarts.init(document.getElementById('bar'));
        let option = {
          title: {
            text: '近30日系统访问记录'
          },
          color: ['#3398DB'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: timeArr,
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '您的访问',
              type: 'bar',
              data: data1,
              itemStyle: {
                color: '#67C23A',
                barBorderRadius: [30, 30, 0, 0]
              }
            },
            {
              name: 'IP量',
              type: 'bar',
              data: data2,
              itemStyle: {
                color: '#409EFF',
                barBorderRadius: [30, 30, 0, 0]
              }
            }
          ]
        };
        this.myChart.setOption(option);
        let _this = this;
        window.addEventListener('resize', () => {
          _this.myChart.resize();
        })
      },
      showLineChart(data) {
        this.pieChart = this.$echarts.init(document.getElementById('pie'));
        let colorList = ['#afa3f5', '#fa5a5a', '#f0d264', '#3bafff', '#f1bb4c', "#82c8a0","#7fccde","#6698cb","#cb99c5"];
        let option = {
          tooltip: {
            trigger: 'item',
            formatter: "{b} : {d}% <br/>"
          },
          title: {
            subtext: '地域占比',
            x: 'center',
            y: '45%',
            textStyle: {
              fontSize: 18,
              fontWeight: 'normal',
              color: ['#333']
            },
            subtextStyle: {
              color: '#f1bb4c',
              fontSize: 16
            },
          },
          grid: {
            bottom: 150,
            left: 0,
            right: '10%'
          },
          legend: {
            show: false,
            orient: 'vertical',
            top: "middle",
            right: "5%",
            textStyle: {
              color: '#f2f2f2',
              fontSize: 25,

            },
            icon: 'roundRect'
          },
          series: [
            // 主要展示层的
            {
              radius: ['29%', '59%'],
              center: ['50%', '50%'],
              type: 'pie',
              itemStyle: {
                normal: {
                  formatter: function (params) {
                    return params.name + ":" + params.value
                  },
                  color: function (params) {
                    return colorList[params.dataIndex]
                  }
                }
              },
              labelLine: {
                normal: {
                  show: true,
                  length: 25,
                  length2: 60,
                  lineStyle: {
                    color: '#d3d3d3'
                  },
                  align: 'right'
                },
                color: "#000",
                emphasis: {
                  show: true
                }
              },
              label: {
                normal: {
                  padding: [0, -90],
                  height: 35,
                  rich: {
                    a: {
                      width: 38,
                      height: 38,
                      lineHeight: 50,

                      align: 'left'
                    },
                    b: {
                      width: 29,
                      height: 45,
                      lineHeight: 50,
                      align: 'left'
                    },
                    c: {
                      width: 34,
                      height: 33,
                      lineHeight: 50,

                      align: 'left'
                    },
                    d: {
                      width: 34,
                      height: 44,
                      lineHeight: 50,
                      align: 'left'
                    },
                    e: {
                      width: 38,
                      height: 30,
                      lineHeight: 50,
                      align: 'left'
                    },
                    nameStyle: {
                      fontSize: 16,
                      color: "#555",
                      align: 'left'
                    },
                    rate: {
                      fontSize: 20,
                      color: "#1ab4b8",
                      align: 'left'
                    }
                  }
                }
              },
              data: data
            },
            // 边框的设置
            {
              radius: ['54%', '59%'],
              center: ['50%', '50%'],
              type: 'pie',
              label: {
                normal: {
                  show: false
                },
                emphasis: {
                  show: false
                }
              },
              labelLine: {
                normal: {
                  show: false
                },
                emphasis: {
                  show: false
                }
              },
              animation: false,
              tooltip: {
                show: false
              },
              itemStyle: {
                normal: {
                  color: 'rgba(250,250,250,0.5)'
                }
              },
              data: [{
                value: 1,
              }],
            }
          ]
        };
        this.pieChart.setOption(option);
        let _this = this;
        window.addEventListener('resize', () => {
          _this.pieChart.resize();
        })
      }
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'avatar',
        'name'
      ]),
      barHeight() {
        return window.outerHeight * 0.52 + 'px';
      },
      title() {
        let now = new Date(), hour = now.getHours()
        if (hour < 6) {
          return "凌晨好！"
        }
        else if (hour < 9) {
          return "早上好！"
        }
        else if (hour < 12) {
          return "上午好！"
        }
        else if (hour < 14) {
          return "中午好！"
        }
        else if (hour < 17) {
          return "下午好！"
        }
        else if (hour < 19) {
          return "傍晚好！"
        }
        else if (hour < 22) {
          return "晚上好！"
        }
        else {
          return "夜里好！"
        }
      },
      message() {
        let now = new Date(), hour = now.getHours()
        if (hour < 3) {
          return "睡你麻痹起来嗨啊！"
        }
        else if (hour < 5) {
          return "哟！修仙呢？"
        }
        else if (hour < 9) {
          return "今天又是充满希望的一天！菜鸡"
        }
        else if (hour < 12) {
          return "工作之余来杯枸杞茶吧！"
        }
        else if (hour < 14) {
          return "祝你有个元气满满的一下午鸭！"
        }
        else if (hour < 17) {
          return "一杯茶一包烟，一个bug改一天！"
        }
        else if (hour < 19) {
          return "bug写完了吗？"
        }
        else if (hour < 24) {
          return "来撸一把？"
        }
      },
      isReSize() {
        return this.sidebar.opened;
      }
    }
  }
</script>

<style scoped>
</style>
