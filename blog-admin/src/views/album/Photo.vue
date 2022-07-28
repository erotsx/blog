<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <!-- 相册信息 -->
    <div class="album-info">
      <el-image fit="cover" class="album-cover" :src="albumInfo.cover" />
      <div class="album-detail">
        <div style="margin-bottom:0.6rem">
          <span class="album-name">{{ albumInfo.name }}</span>
          <span class="photo-count">{{ albumInfo.photoCount }}张</span>
        </div>
        <div>
          <span v-if="albumInfo.description" class="album-desc">
            {{ albumInfo.description }}
          </span>
          <el-button
            icon="el-icon-picture"
            type="primary"
            size="small"
            @click="uploadPhoto = true"
          >
            上传照片
          </el-button>
        </div>
      </div>
      <!-- 相册操作 -->
      <div class="operation">
        <div class="all-check">
          <el-checkbox
            v-model="checkAll"
            :indeterminate="isIndeterminate"
            @change="handleCheckAllChange"
          >
            全选
          </el-checkbox>
          <div class="check-count">已选择{{ selectPhotoIdList.length }}张</div>
        </div>
        <el-button
          type="success"
          :disabled="selectPhotoIdList.length === 0"
          size="small"
          icon="el-icon-deleteItem"
          @click="movePhoto = true"
        >
          移动到
        </el-button>
        <el-button
          type="danger"
          :disabled="selectPhotoIdList.length === 0"
          size="small"
          icon="el-icon-deleteItem"
          @click="batchDeletePhoto = true"
        >
          批量删除
        </el-button>
      </div>
    </div>
    <!-- 照片列表 -->
    <el-row v-loading="loading" class="photo-container" :gutter="10">
      <!-- 空状态 -->
      <el-empty v-if="photoList.length === 0" description="暂无照片" />
      <el-checkbox-group
        v-model="selectPhotoIdList"
        @change="handleCheckedPhotoChange"
      >
        <el-col v-for="item of photoList" :key="item.id" :md="4">
          <el-checkbox :label="item.id">
            <div class="photo-item">
              <!-- 照片操作 -->
              <div class="photo-operation">
                <el-dropdown @command="handleCommand">
                  <i class="el-icon-more" style="color:#000000" />
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item :command="JSON.stringify(item)">
                      <i class="el-icon-edit" />编辑
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <el-image
                fit="contain"
                class="photo-img"
                :src="item.url"
                :preview-photo-src-list="photoList"
              />
              <div class="photo-name">{{ item.name }}</div>
            </div>
          </el-checkbox>
        </el-col>
      </el-checkbox-group>
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
    <!-- 上传模态框 -->
    <el-dialog :visible.sync="uploadPhoto" width="70%" top="10vh">
      <div slot="title" class="dialog-title-container">
        上传照片
      </div>
      <!-- 上传 -->
      <div v-loading="uploadLoading" class="uploadImg-container">
        <el-upload
          v-show="uploadList.length > 0"
          action="https://www.imgurl.org/api/v2/upload"
          list-type="picture-card"
          :data="imgURL"
          :file-list="uploadList"
          multiple
          :before-upload="beforeUpload"
          :on-success="upload"
          :on-remove="handleRemove"
          :on-error="errorUpload"
        >
          <i class="el-icon-plus" />
        </el-upload>
        <div class="upload">
          <el-upload
            v-show="uploadList.length === 0"
            drag
            action="https://www.imgurl.org/api/v2/upload"
            :data="imgURL"
            multiple
            :before-upload="beforeUpload"
            :on-success="upload"
            :show-file-list="false"
            :on-error="errorUpload"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div slot="tip" class="el-upload__tip">
              支持上传jpg/png文件
            </div>
          </el-upload>
        </div>
      </div>
      <div slot="footer">
        <div class="upload-footer">
          <div class="upload-count">共上传{{ uploadList.length }}张照片</div>
        </div>
      </div>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog :visible.sync="editPhoto" width="30%">
      <div slot="title" class="dialog-title-container">
        修改信息
      </div>
      <el-form label-width="80px" size="medium" :model="photoForm">
        <el-form-item label="照片名称">
          <el-input v-model="photoForm.name" style="width:220px" />
        </el-form-item>
        <el-form-item label="照片描述">
          <el-input v-model="photoForm.description" style="width:220px" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="editPhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhoto">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="batchDeletePhoto" width="30%">
      <div slot="title" class="dialog-title-container">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中照片？</div>
      <div slot="footer">
        <el-button @click="batchDeletePhoto = false">取 消</el-button>
        <el-button type="primary" @click="updatePhotoDelete(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 移动对话框 -->
    <el-dialog :visible.sync="movePhoto" width="30%">
      <div slot="title" class="dialog-title-container">
        移动照片
      </div>
      <el-empty v-if="albumList.length < 2" description="暂无其他相册" />
      <el-form v-else label-width="80px" size="medium" :model="photoForm">
        <el-radio-group v-model="albumId">
          <div class="album-check-item">
            <template v-for="item of albumList">
              <el-radio
                v-if="item.id !== albumInfo.id"
                :key="item.id"
                :label="item.id"
                style="margin-bottom:1rem"
              >
                <div class="album-check">
                  <el-image
                    fit="cover"
                    class="album-check-cover"
                    :src="item.cover"
                  />
                  <div style="margin-left:0.5rem">{{ item.name }}</div>
                </div>
              </el-radio>
            </template>
          </div>
        </el-radio-group>
      </el-form>
      <div slot="footer">
        <el-button @click="movePhoto = false">取 消</el-button>
        <el-button
          :disabled="albumId == null"
          type="primary"
          @click="updatePhotoAlbum"
        >
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { getAlbumInfo, listAlbums } from '@/api/album'
import { deletePhotos, insertPhoto, movePhotos, searchPhotos, updatePhoto } from '@/api/photo'

export default {
  data: function() {
    return {
      loading: true,
      uploadLoading: false,
      checkAll: false,
      isIndeterminate: false,
      uploadPhoto: false,
      editPhoto: false,
      movePhoto: false,
      batchDeletePhoto: false,
      imgURL: {
        uid: 'dd8f57fc3b53b197beb27912d0002345',
        token: '615e392c6e4b8dbb2c70f2eebb18e595'
      },
      uploadList: [],
      photoList: [],
      photoIdList: [],
      selectPhotoIdList: [],
      albumList: [],
      albumInfo: {
        id: null,
        name: '',
        description: '',
        cover: '',
        photoCount: 0
      },
      photoForm: {
        id: null,
        name: '',
        description: ''
      },
      albumId: null,
      current: 1,
      size: 18,
      count: 0
    }
  },
  watch: {
    photoList() {
      this.photoIdList = []
      this.photoList.forEach(item => {
        this.photoIdList.push(item.id)
      })
    },
    uploadPhoto() {
      if (!this.uploadPhoto) {
        console.log(111)
        this.listPhotos()
      }
    }
  },
  created() {
    this.getAlbumInfo()
    this.listAlbums()
    this.listPhotos()
  },
  methods: {
    getAlbumInfo() {
      getAlbumInfo(this.$route.params.albumId).then(data => {
        this.albumInfo = data.data
      })
    },
    listAlbums() {
      listAlbums().then(data => {
        this.albumList = data.data
      })
    },
    listPhotos() {
      const query = {
        page: this.current,
        pageSize: this.size,
        albumId: this.$route.params.albumId
      }
      searchPhotos(query).then(data => {
        this.photoList = data.data.items
        this.count = data.data.total
        this.loading = false
      })
    },
    sizeChange(size) {
      this.size = size
      this.listPhotos()
    },
    currentChange(current) {
      this.current = current
      this.listPhotos()
    },
    savePhotos() {
      const photoUrlList = []
      this.uploadList.forEach(item => {
        photoUrlList.push(item.url)
      })
      this.axios
        .post('/api/admin/photos', {
          albumId: this.$route.params.albumId,
          photoUrlList: photoUrlList
        })
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: '成功',
              message: data.message
            })
            this.uploadList = []
            this.listPhotos()
          } else {
            this.$notify.error({
              title: '失败',
              message: data.message
            })
          }
        })
      this.uploadPhoto = false
    },
    updatePhoto() {
      if (this.photoForm.name.trim() === '') {
        this.$message.error('照片名称不能为空')
        return false
      }
      updatePhoto(this.photoForm).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listPhotos()
        this.editPhoto = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.editPhoto = false
      })
    },
    updatePhotoAlbum() {
      const params = {
        albumId: this.albumId,
        photoIdList: this.selectPhotoIdList
      }
      console.log(params)
      movePhotos(this.selectPhotoIdList, this.albumId).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.getAlbumInfo()
        this.listPhotos()
        this.movePhoto = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.movePhoto = false
      })
    },
    handleRemove(file) {
      this.uploadList.forEach((item, index) => {
        if (item.url === file.url) {
          this.uploadList.splice(index, 1)
        }
      })
    },
    upload(response) {
      console.log(response)
      const photo = {
        albumId: this.$route.params.albumId,
        name: response.data.relative_path,
        url: response.data.url,
        deleteUrl: response.data.delete
      }
      insertPhoto(photo).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.uploadLoading = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.uploadLoading = false
      })
      this.uploadList.push({ url: response.data.url })
    },
    beforeUpload(file) {
      this.uploadLoading = true
      // return new Promise(resolve => {
      //   if (file.size / 1024 < this.config.UPLOAD_SIZE) {
      //     resolve(file)
      //   }
      //   // 压缩到200KB,这里的200就是要压缩的大小,可自定义
      //   imageConversion
      //     .compressAccurately(file, this.config.UPLOAD_SIZE)
      //     .then(res => {
      //       resolve(res)
      //     })
      // })
    },
    handleCheckAllChange(val) {
      this.selectPhotoIdList = val ? this.photoIdList : []
      this.isIndeterminate = false
    },
    handleCheckedPhotoChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.photoIdList.length
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.photoIdList.length
    },
    handleCommand(command) {
      this.photoForm = JSON.parse(command)
      this.editPhoto = true
    },
    errorUpload(err) {
      console.log(err)
    },
    updatePhotoDelete(id) {
      let data = {}
      if (id == null) {
        data = this.selectPhotoIdList
      } else {
        data = { idList: [id], isDelete: 1 }
      }
      deletePhotos(data).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listPhotos()
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
      })
      this.batchDeletePhoto = false
    }
  }
}
</script>

<style scoped>
.album-info {
  display: flex;
  margin-top: 2.25rem;
  margin-bottom: 2rem;
}

.album-cover {
  border-radius: 4px;
  width: 5rem;
  height: 5rem;
}

.album-check-cover {
  border-radius: 4px;
  width: 4rem;
  height: 4rem;
}

.album-detail {
  padding-top: 0.4rem;
  margin-left: 0.8rem;
}

.album-desc {
  font-size: 14px;
  margin-right: 0.8rem;
}

.operation {
  padding-top: 1.5rem;
  margin-left: auto;
}

.all-check {
  display: inline-flex;
  align-items: center;
  margin-right: 1rem;
}

.check-count {
  margin-left: 1rem;
  font-size: 12px;
}

.album-name {
  font-size: 1.25rem;
}

.photo-count {
  font-size: 12px;
  margin-left: 0.5rem;
}

.photo-item {
  width: 100%;
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}

.photo-img {
  width: 100%;
  height: 7rem;
  border-radius: 4px;
}

.photo-name {
  font-size: 14px;
  margin-top: 0.3rem;
  text-align: center;
}

.uploadImg-container {
  height: 400px;
}

.upload {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.upload-footer {
  display: flex;
  align-items: center;
}

.photo-operation {
  position: absolute;
  z-index: 1000;
  top: 0.3rem;
  right: 0.5rem;
}

.album-check {
  display: flex;
  align-items: center;
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

.main-card {
  min-height: calc(100vh - 50px);
}

.dialog-title-container {
  display: flex;
  align-items: center;
  font-weight: bold;
}
</style>
