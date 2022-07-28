<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form v-show="showSearch" ref="form" :inline="true" :model="params" label-width="68px">
        <el-form-item label="标题">
          <el-input v-model="params.title" style="width: 150px" size="small" placeholder="请输入文章名称" />
        </el-form-item>
        <el-form-item label="标签">
          <el-select
            v-model="params.tagId"
            style="width: 130px"
            size="small"
            filterable
            clearable
            reserve-keyword
            placeholder="请选择标签"
            @change="handleFind"
          >
            <el-option v-for="item in tags" :key="item.id" :label="item.tagName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="分类">
          <el-select
            v-model="params.categoryId"
            style="width: 130px"
            size="small"
            filterable
            clearable
            reserve-keyword
            placeholder="请选择分类"
            @change="handleFind"
          >
            <el-option v-for="item in category" :key="item.id" :label="item.categoryName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="params.status"
            style="width: 130px"
            size="small"
            filterable
            clearable
            reserve-keyword
            placeholder="请选择状态"
            @change="handleFind"
          >
            <el-option v-for="(item,index) in publishList" :key="index" :label="item" :value="item" />
          </el-select>
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
        <el-table-column
          prop="cover"
          label="文章封面"
          width="180"
          align="center"
        >
          <template slot-scope="scope">
            <el-image
              class="article-cover"
              :src="
                scope.row.cover
                  ? scope.row.cover
                  : 'https://static.talkxj.com/articles/c5cc2b2561bd0e3060a500198a4ad37d.png'
              "
            />
          </template>
        </el-table-column>
        <el-table-column prop="title" align="center" label="标题" width="220">
          <template v-slot:default="slotProps">
            <el-link :underline="false" @click="onClick(slotProps.row)">{{ slotProps.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" width="125" label="分类">
          <template v-slot:default="slotProps">
            <el-tag
              v-if="slotProps.row.categoryName!==null"
              style="margin-left: 3px"
              align="center"
              type="warning"
            >{{ slotProps.row.categoryName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="标签"
        >
          <template v-slot:default="slotProps">
            <el-tag
              v-for="(item) in slotProps.row.tags"
              :key="item.id"
              style="margin-left: 3px"
              align="center"
              type="primary"
            >{{ item.tagName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" align="center" class-name="status-col" width="100" />
        <el-table-column label="评论次数" prop="commentCounts" align="center" />
        <el-table-column label="访问次数" prop="viewCounts" align="center" />
        <!--      <el-table-column label="作者" prop="author" width="110px" align="center"></el-table-column>-->
        <el-table-column v-if="showReviewer" label="Reviewer" width="110px" align="center" />
        <el-table-column label="创建时间" prop="createDate" sortable align="center" />
        <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
          <template v-slot:default="slotProps">
            <el-button type="primary" size="mini" @click="handleUpdate(slotProps.row)">
              编辑
            </el-button>
            <el-button
              v-if="slotProps.row.status!=='draft'"
              size="mini"
              @click="handleModifyStatus(slotProps.row,'draft')"
            >
              设置
            </el-button>
            <el-button
              v-if="slotProps.row.status!=='deleted'"
              size="mini"
              type="danger"
              @click="handleDelete(slotProps.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="params.page"
        :limit.sync="params.pageSize"
        @pagination="getList"
      />

      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form
          ref="dataForm"
          :rules="rules"
          :model="temp"
          label-position="left"
          label-width="70px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="Type" prop="type">
            <el-select v-model="temp.type" class="filter-item" placeholder="Please select">
              <el-option
                v-for="item in calendarTypeOptions"
                :key="item.key"
                :label="item.display_name"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Date" prop="timestamp">
            <el-date-picker v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
          </el-form-item>
          <el-form-item label="Title" prop="title">
            <el-input v-model="temp.title" />
          </el-form-item>
          <el-form-item label="Status">
            <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
              <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="Imp">
            <el-rate
              v-model="temp.importance"
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              :max="3"
              style="margin-top:8px;"
            />
          </el-form-item>
          <el-form-item label="Remark">
            <el-input
              v-model="temp.remark"
              :autosize="{ minRows: 2, maxRows: 4}"
              type="textarea"
              placeholder="Please input"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">
            Cancel
          </el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            Confirm
          </el-button>
        </div>
      </el-dialog>

      <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
        <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
          <el-table-column prop="key" label="Channel" />
          <el-table-column prop="pv" label="Pv" />
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { fetchList, fetchPv, createArticle, updateArticle, removeArticle } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'
import { fetchTags } from '@/api/tags'
import { fetchCategory } from '@/api/category' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      showSearch: true,
      tags: [],
      category: [],
      publishList: ['已发布', '草稿'],
      params: {
        title: null,
        tagId: null,
        status: null,
        page: 1,
        categoryId: null,
        pageSize: 10
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    fetchTags().then(res => {
      this.tags = res.data.items
    })
    fetchCategory().then(res => {
      this.category = res.data.items
    })
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.params).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.params.page = 1
      this.getList()
    },
    handleFind: function() {
      this.params.page = 1
      this.queryList()
    },
    queryList: function() {
      this.listLoading = true
      fetchList(this.params).then(res => {
        this.list = res.data.items
        this.total = res.data.total
        this.listLoading = false
      }).catch(err => {
        console.log(err)
      })
      this.loading.close()
    },
    resetQuery: function() {
      this.params.title = null
      this.params.tagId = null
      this.params.status = null
      this.params.categoryId = null
      this.queryList()
    },
    onClick: function(row) {
      if (row.status === '草稿') {
        this.$message.error('文章暂未发布，无法进行浏览')
        return false
      }
      // window.open(this.BLOG_WEB_URL + 'articles/' + row.id)
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          this.temp.author = 'vue-element-admin'
          createArticle(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.$router.push({ path: '/articles/write/' + row.id })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateArticle(tempData).then(() => {
            const index = this.list.findIndex(v => v.id === this.temp.id)
            this.list.splice(index, 1, this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      const id = row.id
      this.$confirm('此操作将把文章删除, 是否继续?', '提示', {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(id.type)
        removeArticle(id).then(res => {
          this.$message.success('删除文章成功')
          this.queryList()
        }).catch(err => {
          console.log(err)
        })
      }).catch(() => {
        this.$message.info('取消删除')
      })
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
<style scoped>
.article-cover {
  position: relative;
  width: 100%;
  height: 90px;
  border-radius: 4px;
}
.article-cover::after {
  content: "";
  background: rgba(0, 0, 0, 0.3);
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>
