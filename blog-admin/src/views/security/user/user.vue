<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <!-- 登录方式 -->
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入昵称"
          style="width:200px"
          @keyup.enter.native="searchUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchUsers"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table v-loading="loading" border :data="userList">
      <!-- 表格列 -->
      <el-table-column
        prop="linkAvatar"
        label="头像"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <img :src="scope.row.avatar" width="40" height="40">
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        align="center"
        width="140"
      />
      <el-table-column prop="roleList" label="用户角色" align="center">
        <template slot-scope="scope">
          <el-tag
            v-for="(item, index) of scope.row.roleList"
            :key="index"
            style="margin-right:4px;margin-top:4px"
          >
            {{ item.name }}
          </el-tag>
        </template>
      </el-table-column>
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
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createDate | date }}
        </template>
      </el-table-column>
      <el-table-column
        prop="lastLogin"
        label="上次登录时间"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.lastLogin | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            @click="openEditModel(scope.row)"
          >
            <i class="el-icon-folder-checked" /> 修改角色
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="openEditUserModel(scope.row)"
          >
            <i class="el-icon-edit" /> 修改信息
          </el-button>
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
    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isEdit" width="30%">
      <div slot="title" class="dialog-title-container">
        分配角色
      </div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="角色">
          <el-checkbox-group v-model="roleIdList">
            <el-checkbox
              v-for="item of userRoleList"
              :key="item.id"
              :label="item.id"
            >
              {{ item.name }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserRole">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="isUserEdit" width="30%">
      <div slot="title" class="dialog-title-container">
        修改信息
      </div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="昵称">
          <el-input v-model="userForm.nickname" style="width:220px" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isUserEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUser">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { searchUser, updateUser, updateUserInfo, updateUserRole } from '@/api/user'
import { listRoles } from '@/api/role'

export default {
  data: function() {
    return {
      loading: true,
      isEdit: false,
      isUserEdit: false,
      userForm: {
      },
      userRoleList: [],
      roleIdList: [],
      userList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    }
  },
  watch: {
  },
  created() {
    this.listUsers()
    this.listRoles()
  },
  methods: {
    searchUsers() {
      this.current = 1
      this.listUsers()
    },
    sizeChange(size) {
      this.size = size
      this.listUsers()
    },
    currentChange(current) {
      this.current = current
      this.listUsers()
    },
    changeDisable(user) {
      this.loading = true
      const body = {
        id: user.id,
        status: user.status
      }
      updateUser(body).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listUsers()
      })
    },
    openEditModel(user) {
      this.roleIdList = []
      this.userForm = JSON.parse(JSON.stringify(user))
      this.userForm.roleList.forEach(item => {
        this.roleIdList.push(item.id)
      })
      this.isEdit = true
    },
    openEditUserModel(user) {
      this.userForm = JSON.parse(JSON.stringify(user))
      console.log(this.userForm)
      this.isUserEdit = true
    },
    editUserRole() {
      this.userForm.roleIdList = this.roleIdList
      this.loading = true
      updateUserRole(this.userForm.id, this.roleIdList).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listUsers()
        this.isEdit = false
      }).catch(err => {
        this.$notify.error({
          title: '失败',
          message: err.message
        })
      })
    },
    editUser() {
      this.loading = true
      const body = {
        nickname: this.userForm.nickname
      }
      console.log(this.userForm.id)
      updateUserInfo(this.userForm.id, body).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listUsers()
        this.isUserEdit = false
      }).catch(err => {
        this.$notify.error({
          title: '失败',
          message: err.message
        })
      })
    },
    listUsers() {
      const query = {
        keyword: this.keywords,
        page: this.current,
        pageSize: this.size
      }
      searchUser(query).then(data => {
        this.userList = data.data.items
        this.count = data.data.total
        this.loading = false
      })
    },
    listRoles() {
      listRoles().then(data => {
        this.userRoleList = data.data
      })
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
