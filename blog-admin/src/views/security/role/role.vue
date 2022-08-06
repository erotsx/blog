<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openMenuModel(null)"
      >
        新增
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入角色名"
          style="width:200px"
          @keyup.enter.native="searchRoles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchRoles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      v-loading="loading"
      border
      :data="roleList"
      @selection-change="selectionChange"
    >
      <!-- 表格列 -->
      <el-table-column prop="id" label="id" align="center" />
      <el-table-column prop="name" label="角色名" align="center" />
      <el-table-column prop="description" label="描述" align="center" />
      <el-table-column prop="status" label="是否启用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="'1'"
            :inactive-value="'0'"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="创建时间"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createDate | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="220">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openMenuModel(scope.row)">
            <i class="el-icon-edit" /> 修改角色
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="openResourceModel(scope.row)"
          >
            <i class="el-icon-folder-checked" /> 修改权限
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteRoles(scope.row.id)"
          >
            <el-button slot="reference" size="mini" type="text">
              <i class="el-icon-delete" /> 删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChange"
      @current-change="currentChange"
    />
    <!-- 菜单对话框 -->
    <el-dialog :visible.sync="roleMenu" width="30%">
      <div slot="title" ref="roleTitle" class="dialog-title-container" />
      <el-form label-width="80px" size="medium" :model="roleForm">
        <el-form-item label="角色名">
          <el-input v-model="roleForm.name" style="width:250px" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="roleForm.description" style="width:250px" />
        </el-form-item>
        <el-form-item label="是否启用">
          <el-radio-group v-model="roleForm.status">
            <el-radio :label="'1'">启用</el-radio>
            <el-radio :label="'0'">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleMenu">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 资源对话框 -->
    <el-dialog :visible.sync="roleResource" width="30%" top="9vh">
      <div slot="title" class="dialog-title-container">修改接口权限</div>
      <el-form v-loading="permissionLoading" label-width="80px" size="medium">
        <el-form-item label="接口权限">
          <el-tree
            ref="resourceTree"
            :props="{
              label: 'name',
            }"
            :data="permissionList"
            :default-checked-keys="permissionIdList"
            show-checkbox
            node-key="id"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleResource = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleResource">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { createRole, deleteRole, listPermissions, searchRoles, updatePermissions, updateRole } from '@/api/role'
import { listCategoryPermissions } from '@/api/permission'

export default {
  data: function() {
    return {
      loading: true,
      isDelete: false,
      permissionLoading: false,
      roleList: [],
      roleIdList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      roleMenu: false,
      roleResource: false,
      permissionList: [],
      menuList: [],
      permissionIdList: [],
      roleForm: {
        name: '',
        description: '',
        status: '1'
      }
    }
  },
  created() {
    this.listRoles()
  },
  methods: {
    searchRoles() {
      this.current = 1
      this.listRoles()
    },
    sizeChange(size) {
      this.size = size
      this.listRoles()
    },
    currentChange(current) {
      this.current = current
      this.listRoles()
    },
    selectionChange(roleList) {
      this.roleIdList = []
      roleList.forEach(item => {
        this.roleIdList.push(item.id)
      })
    },
    listRoles() {
      const query = {
        page: this.current,
        pageSize: this.size,
        keyword: this.keywords
      }
      searchRoles(query).then(data => {
        this.roleList = data.data.items
        this.count = data.data.total
        this.loading = false
      })

      listCategoryPermissions().then(data => {
        this.permissionList = data.data
      })
      // this.axios.get('/api/admin/role/menus').then(({ data }) => {
      //   this.menuList = data.data
      // })
    },
    changeDisable(row) {
      this.roleForm = row
      this.loading = true
      updateRole(this.roleForm).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listRoles()
        this.roleMenu = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.roleMenu = false
      })
    },
    deleteRoles(id) {
      // var param = {}
      // if (id == null) {
      //   param = { data: this.roleIdList }
      // } else {
      //   param = { data: [id] }
      // }
      this.loading = true
      deleteRole(id).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listRoles()
        this.isDelete = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.isDelete = false
      })
      // this.axios.delete('/api/admin/roles', param).then(({ data }) => {
      //   if (data.flag) {
      //     this.$notify.success({
      //       title: '成功',
      //       message: data.message
      //     })
      //     this.listRoles()
      //   } else {
      //     this.$notify.error({
      //       title: '失败',
      //       message: data.message
      //     })
      //   }
      //   this.isDelete = false
      // })
    },
    openMenuModel(role) {
      // this.$nextTick(function() {
      //   this.$refs.menuTree.setCheckedKeys([])
      // })
      this.$refs.roleTitle.innerHTML = role ? '修改角色' : '新增角色'
      if (role != null) {
        this.roleForm = JSON.parse(JSON.stringify(role))
      } else {
        this.roleForm = {
          name: '',
          description: '',
          status: '1'
        }
        this.permissionIdList = []
      }
      this.roleMenu = true
    },
    openResourceModel(role) {
      this.$nextTick(function() {
        this.$refs.resourceTree.setCheckedKeys([])
      })
      this.permissionLoading = true
      this.permissionIdList = []
      this.roleForm = JSON.parse(JSON.stringify(role))
      listPermissions(role.id).then(data => {
        for (const permission of data.data) {
          this.permissionIdList.push(permission.id)
        }
        this.$refs.resourceTree.setCheckedKeys(this.permissionIdList)
        this.permissionLoading = false
      })
      this.roleResource = true
    },
    saveOrUpdateRoleResource() {
      this.permissionIdList = this.$refs.resourceTree.getCheckedKeys()
      updatePermissions(this.roleForm.id, this.permissionIdList).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.roleResource = false
        this.listRoles()
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.roleResource = false
      })
    },
    saveOrUpdateRoleMenu() {
      if (this.roleForm.name === '' || this.roleForm.name === undefined) {
        this.$message.error('角色名不能为空')
        return false
      }
      // this.roleForm.permissionIdList = null
      this.loading = true
      if (this.roleForm.id === undefined) {
        createRole(this.roleForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listRoles()
          this.roleMenu = false
        }).catch(error => {
          this.$notify.error({
            title: '失败',
            message: error.message
          })
          this.roleMenu = false
        })
      } else {
        updateRole(this.roleForm).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listRoles()
          this.roleMenu = false
        }).catch(error => {
          this.$notify.error({
            title: '失败',
            message: error.message
          })
          this.roleMenu = false
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
.pagination-container {
  float: right;
  margin-top: 1.25rem;
  margin-bottom: 1.25rem;
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
