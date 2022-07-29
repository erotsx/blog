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
        新建相册
      </el-button>
      <div style="margin-left:auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入相册名"
          style="width:200px"
          @keyup.enter.native="searchAlbums"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchAlbums"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 相册列表 -->
    <el-row v-loading="loading" class="album-container" :gutter="12">
      <!-- 空状态 -->
      <el-empty v-if="albumList.length === 0" description="暂无相册" />
      <el-col v-for="item of albumList" :key="item.id" :md="6">
        <div class="album-item" @click="checkPhoto(item)">
          <!-- 相册操作 -->
          <div class="album-opreation">
            <el-dropdown @command="handleCommand">
              <i class="el-icon-more" style="color:#fff" />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="'update' + JSON.stringify(item)">
                  <i class="el-icon-edit" />编辑
                </el-dropdown-item>
                <el-dropdown-item :command="'delete' + item.id">
                  <i class="el-icon-delete" />删除
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <div class="album-photo-count">
            <div>{{ item.photoCount }}</div>
            <i v-if="item.status === 0" class="iconfont el-icon-mymima" />
          </div>
          <el-image fit="cover" class="album-cover" :src="item.cover" />
          <div class="album-name">{{ item.name }}</div>
        </div>
      </el-col>
    </el-row>
    <!-- 分页 -->
    <el-pagination
      :hide-on-single-page="true"
      class="pagination-container"
      :current-page="current"
      :page-size="size"
      :total="count"
      layout="prev, pager, next"
      @size-change="sizeChange"
      @current-change="currentChange"
    />
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addOrEdit" width="35%" top="10vh">
      <div slot="title" ref="albumTitle" class="dialog-title-container" />
      <el-form label-width="80px" size="medium" :model="albumForum">
        <el-form-item label="相册名称">
          <el-input v-model="albumForum.name" style="width:220px" />
        </el-form-item>
        <el-form-item label="相册描述">
          <el-input v-model="albumForum.description" style="width:220px" />
        </el-form-item>
        <el-form-item label="相册封面">
          <el-input v-model="albumForum.cover" placeholder="请输入内容" class="input-with-select">
            <el-button slot="append" icon="el-icon-picture-outline-round" @click="selectCover" />
          </el-input>
        </el-form-item>
        <el-form-item label="发布形式">
          <el-radio-group v-model="albumForum.status">
            <el-radio :label="1">公开</el-radio>
            <el-radio :label="0">私密</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditAlbum">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 删除对话框 -->
    <el-dialog :visible.sync="isdelete" width="30%">
      <div slot="title" class="dialog-title-container">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除该相册？</div>
      <div slot="footer">
        <el-button @click="isdelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteAlbum">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <SelectPhoto
      v-show="dialog_visible"
      :dialog_visible="dialog_visible"
      @dialogVisibleEvent="showDialog"
      @giveUrl="getUrl"
    />
  </el-card>
</template>

<script>
import * as imageConversion from 'image-conversion'
import { addAlbum, deleteAlbum, searchAlbum, updateAlbum } from '@/api/album'
import SelectPhoto from '@/components/Photo/selectPhoto'

export default {
  components: {
    SelectPhoto
  },
  data: function() {
    return {
      keywords: '',
      loading: true,
      isdelete: false,
      dialog_visible: false,
      addOrEdit: false,
      albumForum: {
        id: null,
        name: '',
        description: '',
        cover: '',
        status: 1
      },
      albumList: [],
      selectPhotoList: [],
      selectPhotoIdList: [],
      current: 1,
      page: 1,
      url: '',
      pageSize: 8,
      size: 8,
      count: 0,
      total: 0
    }
  },
  watch: {
    url() {
      this.albumForum.cover = this.url
    }
  },
  created() {
    this.listAlbums()
  },
  methods: {
    openModel(item) {
      if (item) {
        this.albumForum = JSON.parse(item)
        this.$refs.albumTitle.innerHTML = '修改相册'
      } else {
        this.albumForum = {
          id: null,
          name: '',
          description: '',
          cover: '',
          status: 1
        }
        this.$refs.albumTitle.innerHTML = '新建相册'
      }
      this.addOrEdit = true
    },
    checkPhoto(item) {
      this.$router.push({ path: '/album/' + item.id })
    },
    showDialog(visible) {
      this.dialog_visible = visible
    },
    selectCover() {
      this.showDialog(true)
    },
    getUrl(url) {
      this.url = url
    },
    listAlbums() {
      const params = {
        keyword: this.keywords,
        page: this.current,
        pageSize: this.size
      }
      searchAlbum(params).then(data => {
        this.albumList = data.data.items
        this.count = data.data.total
        this.loading = false
      })
    },
    addOrEditAlbum() {
      if (this.albumForum.name.trim() === '') {
        this.$message.error('相册名称不能为空')
        return false
      }
      if (this.albumForum.description.trim() === '') {
        this.$message.error('相册描述不能为空')
        return false
      }
      if (this.albumForum.id === null) {
        addAlbum(this.albumForum).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listAlbums()
        }).catch(error => {
          this.$notify.error({
            title: '失败',
            message: error.message
          })
        })
      } else {
        updateAlbum(this.albumForum).then(data => {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
          this.listAlbums()
        }).catch(error => {
          this.$notify.error({
            title: '失败',
            message: error.message
          })
        })
      }
      this.addOrEdit = false
    },
    uploadCover(response) {
      this.albumForum.albumCover = response.data
    },
    beforeUpload(file) {
      return new Promise(resolve => {
        if (file.size / 1024 < this.config.UPLOAD_SIZE) {
          resolve(file)
        }
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
          .compressAccurately(file, this.config.UPLOAD_SIZE)
          .then(res => {
            resolve(res)
          })
      })
    },
    handleCheckedPhotoChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.photoIdList.length
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.photoIdList.length
    },
    handleCommand(command) {
      const type = command.substring(0, 6)
      const data = command.substring(6)
      if (type === 'delete') {
        this.albumForum.id = data
        this.isdelete = true
      } else {
        // console.log(data)
        this.openModel(data)
      }
    },
    deleteAlbum() {
      deleteAlbum(this.albumForum.id).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listAlbums()
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
      })
      this.isdelete = false
    },
    searchAlbums() {
      this.current = 1
      this.listAlbums()
    },
    sizeChange(size) {
      this.size = size
      this.listAlbums()
    },
    currentChange(current) {
      this.current = current
      this.listAlbums()
    }
  }
}
</script>

<style scoped>
.album-cover {
  position: relative;
  border-radius: 4px;
  width: 100%;
  height: 170px;
}

.album-cover::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
}

.album-photo-count {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 1.5rem;
  z-index: 1000;
  position: absolute;
  left: 0;
  right: 0;
  padding: 0 0.5rem;
  bottom: 2.6rem;
  color: #fff;
}

.album-name {
  text-align: center;
  margin-top: 0.5rem;
}

.album-item {
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}

.album-opreation {
  position: absolute;
  z-index: 1000;
  top: 0.5rem;
  right: 0.8rem;
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

.operation-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}

.main-card {
  min-height: calc(100vh - 50px);
}
</style>
