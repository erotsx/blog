<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新增目录
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入接口名"
          style="width:200px"
          @keyup.enter.native="listResources"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="listResources"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 权限列表 -->
    <el-table
      v-loading="loading"
      :data="resourceList"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="id" label="编号" align="center" />
      <el-table-column prop="name" label="名称" width="220" />
      <el-table-column prop="url" label="接口路径" width="300" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createDate | date }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.children"
            type="text"
            size="mini"
            @click="openAddResourceModel(scope.row)"
          >
            <i class="el-icon-plus" /> 新增
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="openEditResourceModel(scope.row)"
          >
            <i class="el-icon-edit" /> 修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteResource(scope.row)"
          >
            <el-button slot="reference" size="mini" type="text">
              <i class="el-icon-delete" /> 删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addModule" width="30%">
      <div slot="title" ref="moduleTitle" class="dialog-title-container" />
      <el-form label-width="80px" size="medium" :model="resourceForm">
        <el-form-item label="目录名">
          <el-input v-model="resourceForm.name" style="width:220px" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="resourceForm.description" style="width:220px" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addModule = false">取 消</el-button>
        <el-button type="primary" @click="addPermissionCategory">
          确 定
        </el-button>
      </span>
    </el-dialog>
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addResource" width="30%">
      <div slot="title" ref="resourceTitle" class="dialog-title-container" />
      <el-form label-width="80px" size="medium" :model="resourceForm">
        <el-form-item label="接口名">
          <el-input v-model="resourceForm.name" style="width:220px" />
        </el-form-item>
        <el-form-item label="接口路径">
          <el-input v-model="resourceForm.url" style="width:220px" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="resourceForm.description"
            type="textarea"
            style="width:220px"
            :rows="3"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addResource = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditResource">
          确 定
        </el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  createCategoryPermissions,
  createPermission, deleteCategoryPermissions, deletePermission,
  searchCategoryPermissions,
  updateCategoryPermissions, updatePermission
} from '@/api/permission'

export default {
  data() {
    return {
      loading: true,
      keywords: '',
      resourceList: [],
      addModule: false,
      addResource: false,
      resourceForm: {}
    }
  },
  computed: {
  },
  created() {
    this.listResources()
  },
  methods: {
    listResources() {
      searchCategoryPermissions(this.keywords).then(data => {
        this.resourceList = data.data
        this.loading = false
      })
    },
    openModel(resource) {
      if (resource != null) {
        this.resourceForm = JSON.parse(JSON.stringify(resource))
        this.$refs.moduleTitle.innerHTML = '修改目录'
      } else {
        this.resourceForm = {}
        this.$refs.moduleTitle.innerHTML = '添加目录'
      }
      this.addModule = true
    },
    openEditResourceModel(resource) {
      if (resource.url == null) {
        this.openModel(resource)
        return false
      }
      this.resourceForm = JSON.parse(JSON.stringify(resource))
      this.$refs.resourceTitle.innerHTML = '修改接口'
      this.addResource = true
    },
    openAddResourceModel(resource) {
      this.resourceForm = {}
      this.resourceForm.categoryId = resource.id
      this.$refs.resourceTitle.innerHTML = '添加接口'
      this.addResource = true
    },
    deleteResource(row) {
      console.log(row)
      if (row.url === undefined) {
        deleteCategoryPermissions(row.id).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listResources()
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
        })
      } else {
        deletePermission(row.id).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listResources()
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
        })
      }
    },
    addPermissionCategory() {
      if (this.resourceForm.name.trim() === '') {
        this.$message.error('资源名不能为空')
        return false
      }
      this.loading = true
      if (this.resourceForm.id === undefined) {
        createCategoryPermissions(this.resourceForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.addModule = false
          this.listResources()
          this.resourceForm = {}
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
          this.resourceForm = {}
        })
      } else {
        updateCategoryPermissions(this.resourceForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.addModule = false
          this.listResources()
          this.resourceForm = {}
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
          this.resourceForm = {}
        })
      }
      this.addModule = false
    },
    addOrEditResource() {
      if (this.resourceForm.name === '' || this.resourceForm.name === undefined) {
        this.$message.error('接口名不能为空')
        return false
      }
      if (this.resourceForm.url === '' || this.resourceForm.url === undefined) {
        this.$message.error('接口路径不能为空')
        return false
      }
      this.loading = true
      if (this.resourceForm.id === undefined) {
        createPermission(this.resourceForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.addResource = false
          this.listResources()
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
          this.addResource = false
        })
      } else {
        updatePermission(this.resourceForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.addResource = false
          this.listResources()
        }).catch(err => {
          this.$notify.error({
            title: '失败',
            message: err.message
          })
          this.addResource = false
        })
      }
    }
  }
}
</script>
<style scoped>
.main-card {
  min-height: calc(100vh - 50px);
}
.operation-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}
.title {
  position: absolute;
  left: 0;
  font-size: 16px;
  font-weight: bold;
  color: #202a34;
}
.title::before {
  content: '';
  width: 24px;
  height: 16px;
  border-left: 3px solid #0081ff;
  margin-right: 20px;
}
.dialog-title-container {
  display: flex;
  align-items: center;
  font-weight: bold;
}
.dialog-title-container i {
  font-size: 1.5rem;
  margin-right: 0.5rem;
}
</style>
