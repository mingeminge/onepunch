<template>
  <div class="app-container">
    <!--<el-col :span="12">
      <el-card shadow="false">
        <div slot="header" class="clearfix">
          <strong>系统信息</strong>
        </div>
        <el-form v-loading="loading" v-model="formData" label-width="150px">
          <el-form-item label="系统名称" prop="osName">
            <label>{{formData.osName}}</label>
          </el-form-item>
          <el-form-item label="CPU使用" prop="systemCpuLoad">
            <label>{{formData.systemCpuLoad}}%</label>
          </el-form-item>
          <el-form-item label="jvm线程负载" prop="getProcessCpuLoad">
            <label>{{formData.getProcessCpuLoad}}%</label>
          </el-form-item>
          <el-form-item label="总的物理内存" prop="totalMemorySize">
            <label>{{formData.totalMemorySize}}GB</label>
          </el-form-item>
          <el-form-item label="剩余的物理内存" prop="freePhysicalMemorySize">
            <label>{{formData.freePhysicalMemorySize}}GB</label>
          </el-form-item>
          <el-form-item label="已使用的物理内存" prop="usedMemory">
            <label>{{formData.usedMemory}}GB</label>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>-->
    <el-row>
      <el-col :span="12">
        <div id="memory" :style="{height:barHeight,width:'100%'}"></div>
      </el-col>
      <el-col :span="12">
        <div id="cpu" :style="{height:barHeight,width:'100%'}"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="redis" :style="{height:barHeight,width:'100%'}"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {getServerInfo} from "@/api/monitor";

  export default {
    name: "index",
    data() {
      return {
        diskInfo: {
          total: 0,
          free: 0
        },
        path: process.env.WS_URL,
        socket: "",
        loading: false,
        formData: {},
        myChart: null,
        lineChart: null,
        memoryLineData: {time: [], value: []},
        cpuLineData: {time: [], value: []},
        redisLineData: {time: [], value: []},
        interval: null
      }
    },
    computed: {
      barHeight() {
        return window.outerHeight * 0.3 + 'px';
      },
    },
    mounted() {
      let _this = this;
      this.getServerInfomation();
      this.interval = setInterval(function () {
        _this.getServerInfomation();
      }, 5000);
    },
    watch: {
      cpuLineData: {
        handler: function (val, oldval) {
          this.showLineChart('cpu', 'CPU使用情况', val)
        },
        deep: true//对象内部的属性监听，也叫深度监听
      },
      redisLineData: {
        handler: function (val, old) {
          this.showLineChart('redis', 'Redis实时Key数量', val)
        },
        deep: true
      },
      memoryLineData: {
        handler: function (val, oldval) {
          let total = (this.formData.totalMemorySize / 1024).toFixed(2) + 'GB';
          let used = (this.formData.usedMemory / 1024).toFixed(2) + 'GB';
          let free = (this.formData.freePhysicalMemorySize / 1024).toFixed(2) + 'GB';
          this.showLineChart('memory', '内存使用情况', val, '总共:' + total + '     已用:' + used + '        可用:' + free)
        },
        deep: true
      },
    },
    methods: {
      getServerInfomation() {
        let _this = this;
        _this.loading = true;
        getServerInfo().then(resp => {
          let data = resp.data;
          if (this.memoryLineData.time.length > 50) {
            this.memoryLineData.time.splice(this.memoryLineData.time[0], 1)
            this.memoryLineData.value.splice(this.memoryLineData.value[0], 1)
            this.cpuLineData.time.splice(this.memoryLineData.time[0], 1)
            this.cpuLineData.value.splice(this.memoryLineData.value[0], 1)
            this.redisLineData.value.splice(_this.cpuLineData.value[0], 1)
          }
          this.memoryLineData.time.push(data.time)
          this.memoryLineData.value.push(((data.usedMemory / data.totalMemorySize) * 100).toFixed(2))
          this.cpuLineData.time.push(data.time)
          this.cpuLineData.value.push(data.systemCpuLoad);
          this.redisLineData.time.push(data.time);
          this.redisLineData.value.push(data.redisKeyCount);
          _this.formData = data;
          _this.loading = false;
        })
      },
      showLineChart(id, name, data, sub) {
        this.myChart = this.$echarts.init(document.getElementById(id));
        let option = {
          title: {
            text: name,
            subtext: sub,
            color: 'rgb(200,100,100)'
          },
          color: ['#3398DB'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '5%',
            bottom: '3%',
            /*top: '3%',*/
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: data.time,
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
              name: '已使用',
              type: 'line',
              data: data.value,
              itemStyle: {
                color: '#67C23A',
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
      }
    },
    destroyed() {
      this.interval = null;
    }
  }
</script>

<style scoped>

</style>
