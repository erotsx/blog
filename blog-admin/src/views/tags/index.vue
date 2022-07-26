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

      <el-row :gutter="10" class="mb8" style="margin-bottom: 10px">
        <el-col :span="1.5">
          <el-button
            size="small"
            class="filter-item"
            type="primary"
            icon="el-icon-plus"
            @click="handleCreate"
          >添加
          </el-button>
        </el-col>
      </el-row>

      <el-table
        :key="tableKey"
        v-loading="listLoading"
        :data="list"
        border
        fit="true"
        highlight-current-row
        style="width: 100%;"
        @sort-change="sortChange"
      >
        <el-table-column prop="id" align="center" label="ID" width="220" />
        <el-table-column align="center" label="标签名称">
          <template v-slot:default="slotProps">
            <el-tag
              style="margin-left: 3px"
              align="center"
              type="warning"
            >{{ slotProps.row.tagName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="文章数量" prop="articleCount" sortable align="center" />
        <el-table-column label="创建时间" prop="createDate" sortable align="center" />
        <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
          <template v-slot:default="slotProps">
            <el-button type="primary" size="mini" @click="handleEdit(slotProps)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(slotProps.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" :page.sync="params.page" :limit.sync="params.pageSize" @pagination="getList" />

      <el-dialog center :title="title" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="tag">
          <el-form-item label="标签名" prop="tagName" :label-width="formLabelWidth">
            <el-input v-model="tag.tagName" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeDialogForm">取 消</el-button>
          <el-button type="primary" @click="submit">确认</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>

import { add, info, remove, searchTags, update } from '@/api/tags'
import Pagination from '@/components/Pagination'

export default {

  components: { Pagination },
  data() {
    return {
      showSearch: true,
      formLabelWidth: '120px',
      tableKey: 0,
      listLoading: true,
      list: null,
      isEditForm: 0,
      dialogFormVisible: false,
      total: 0,
      title: null,
      tag: {},
      params: {
        tagName: null,
        page: 1,
        pageSize: 10
      },
      rules:
        {
          'tagName': [
            { required: true, message: '必填字段', trigger: 'change' },
            { min: 1, max: 20, message: '长度在1到20个字符' }
          ]
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
    handleEdit: function(scope) {
      info(scope.row.id).then(res => {
        this.tag = res.data
        this.isEditForm = 1
        this.title = '修改标签'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      })
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
    handleCreate: function() {
      this.isEditForm = 0
      this.tag = this.getFormObject()
      this.title = '新增标签'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    getFormObject: function() {
      return {
        tagName: ''
      }
    },
    closeDialogForm: function() {
      this.tag = {}
      this.dialogFormVisible = false
    },
    submit: function() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            update(this.tag).then(res => {
              this.$message.success('修改标签成功')
              this.getList()
              this.closeDialogForm()
            }).catch(err => {
              console.log(err)
            })
          } else {
            add(this.tag).then(res => {
              this.$message.success('添加标签成功')
              this.tag = res.data
              this.getList()
              this.closeDialogForm()
            }).catch(err => {
              console.log(err)
            })
          }
        } else {
          console.error('no submit')
        }
      })
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

