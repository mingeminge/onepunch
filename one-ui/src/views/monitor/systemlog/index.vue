<template>
  <div class="app-container">
    <el-form v-model="query" inline>
      <el-form-item label="用户名">
        <el-input v-model="query.username"></el-input>
      </el-form-item>
      <el-form-item label="日期">
        <el-date-picker
          v-model="query.createTime"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否异常">
        <el-select v-model="query.errorFlag">
          <el-option value="" label="全部"></el-option>
          <el-option value="true" label="是"></el-option>
          <el-option value="false" label="否"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 100%" v-loading="loading">
      <el-table-column label="操作人" prop="username"></el-table-column>
      <el-table-column label="模块" prop="moduleName"></el-table-column>
      <el-table-column label="方法" prop="methodName">
        <!--<template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.methodName==='查看'">查看</el-tag>
          <el-tag type="success" v-if="scope.row.methodName==='新增'">新增</el-tag>
          <el-tag type="warning" v-if="scope.row.methodName==='修改'">修改</el-tag>
          <el-tag type="danger" v-if="scope.row.methodName==='删除'">删除</el-tag>
        </template>-->
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" width="140" label="请求URL" prop="uri"></el-table-column>
      <el-table-column label="IP地址" prop="ip"></el-table-column>
      <el-table-column label="耗时(毫秒)" prop="executionTime"></el-table-column>
      <el-table-column width="160" label="操作时间" prop="createTime"></el-table-column>
      <el-table-column label="是否发生异常" prop="errorFlag">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.errorFlag===true">是</el-tag>
          <el-tag type="success" v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="danger" @click="deleteById(scope.row)">删除</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
  import {deleteLog, findAll} from "@/api/systemLog";

  export default {
    name: "SystemLog",
    data() {
      return {
        tableData: [],
        total: 0,
        loading: false,
        query: {current: 1, size: 10, username: '', createTime: '', errorFlag: ''}
      }
    },
    mounted() {
      this.loadTableData(this.queryParam);
    },
    computed: {
      queryParam() {
        return {
          current: this.query.current,
          size: this.query.size,
          username: this.query.username,
          startTime: this.query.createTime[0],
          endTime: this.query.createTime[1],
          errorFlag: this.query.errorFlag
        }
      }
    },
    methods: {
      loadTableData(params) {
        let _this = this;
        _this.loading = true;
        findAll(params).then(resp => {
          if (resp.code === 200) {
            _this.tableData = resp.data;
            _this.total = resp.count;
            _this.loading = false;
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      deleteById(row) {
        let _this = this;
        _this.loading = true;
        deleteLog(row).then(resp => {
          if (resp.code === 200) {
            _this.loading = false;
            _this.$message({type: 'success', message: '删除成功'})
            _this.loadTableData();
          }
        }).catch(() => {
          _this.loading = false;
        })
      },
      handleSizeChange(val) {
        this.query.size = val;
        this.loadTableData(this.queryParam)
      },
      handleCurrentChange(val) {
        this.query.current = val;
        this.loadTableData(this.queryParam)
      },
      search() {
        this.loadTableData(this.queryParam);
      }
    }
  }
</script>

<style scoped>

</style>
