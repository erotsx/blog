<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form v-show="showSearch" ref="form" :inline="true" :model="params" label-width="68px">
        <el-form-item label="标签名称">
          <el-input v-model="params.tagName" style="width: 150px" size="small" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :key="tableKey"
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
        @sort-change="sortChange"
      >
        <el-table-column prop="id" align="center" label="ID" width="220" />
        <el-table-column align="center" width="125" label="标签名称">
          <template v-slot:default="slotProps">
            <el-tag
              style="margin-left: 3px"
              align="center"
              type="warning"
            >{{ slotProps.row.tagName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createDate" width="250px" sortable align="center" />
        <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
          <template v-slot:default="slotProps">
            <el-button type="primary" size="mini" @click="handleUpdate(slotProps.row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(slotProps.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" :page.sync="params.page" :limit.sync="params.pageSize" @pagination="getList" />

    </div>
  </div>
</template>

<script>

import { remove, searchTags } from '@/api/tags'
import Pagination from '@/components/Pagination'

export default {

  components: { Pagination },
  data() {
    return {
      showSearch: true,
      tableKey: 0,
      listLoading: true,
      list: null,
      total: 0,
      params: {
        tagName: null,
        page: 1,
        pageSize: 10
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      searchTags(this.params).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listLoading = false
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    handleFind: function() {
      this.params.page = 1
      this.queryList()
    },
    resetQuery: function() {
      this.params.tagName = null
      this.params.page = 1
      this.queryList()
    },
    queryList: function() {
      this.listLoading = true
      searchTags(this.params).then(res => {
        this.list = res.data.items
        this.total = res.data.total
        this.listLoading = false
      }).catch(err => {
        console.log(err)
      })
      this.loading.close()
    },
    handleUpdate(row) {

    },
    handleDelete(row) {
      const id = row.id
      this.$confirm('此操作将把标签删除, 是否继续?', '提示', {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(id).then(res => {
          this.$message.success('删除标签成功')
          this.queryList()
        }).catch(err => {
          console.log(err)
        })
      }).catch(() => {
        this.$message.info('取消删除')
      })
    }
  }
}
</script>

