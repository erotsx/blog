<template>
  <div>
    <el-dialog :visible.sync="dialog_visible" :before-close="beforeClose" width="1280">
      <div slot="title" class="dialog-title-container">
        选择图片
      </div>
      <div class="select-photo-container">
        <el-upload
          class="upload-demo"
          action="https://www.imgurl.org/api/v2/upload"
          :data="imgURL"
          :before-upload="beforeUpload"
          :on-success="upload"
          multiple
          :limit="3"
          :on-error="errorUpload"
        >
          <el-button size="small" type="primary" icon="el-icon-plus">点击上传</el-button>
        </el-upload>
        <div style="margin-left:auto">
          <el-input
            v-model="keyword"
            prefix-icon="el-icon-search"
            size="small"
            placeholder="请输入图片关键词"
            style="width:200px"
            @keyup.enter.native="searchPhotos"
          />
          <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="searchPhotos"
          >
            搜索
          </el-button>
        </div>
      </div>
      <el-divider />
      <el-row v-loading="selectLoading" class="photo-container" :gutter="25">
        <el-empty v-if="selectPhotoList.length === 0" description="暂无照片" />
        <el-radio-group
          v-model="selectPhotoUrlList"
          :max="1"
          class="photo-radio-group"
          @change="handleCheckedPhotoChange"
        >
          <el-col v-for="item of selectPhotoList" :key="item.id" :span="4">
            <el-radio class="photo-radio" :label="item.url">
              <div class="photo-item">
                <!-- 照片操作 -->
                <div class="photo-operation">
                  <el-dropdown @command="handleCommand">
                    <i class="el-icon-more" style="color:#000000" />
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item :command="'update' + JSON.stringify(item)">
                        <i class="el-icon-edit" />编辑
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>
                <el-image
                  fit="cover"
                  class="photo-img"
                  :src="item.url"
                  :preview-photo-src-list="selectPhotoList"
                />
                <div class="photo-name">{{ item.name }}</div>
              </div>
            </el-radio>
          </el-col>
        </el-radio-group>
      </el-row>
      <el-pagination
        :hide-on-single-page="true"
        class="pagination-container"
        :current-page="page"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @size-change="sizeChange"
        @current-change="currentChange"
      />
      <el-divider />
      <div class="confirm-photo-container">
        <div style="margin-left:auto">
          <el-button
            type="primary"
            size="small"
            style="margin-left:1rem"
            @click="giveUrl"
          >
            确定
          </el-button>
        </div>
      </div>
    </el-dialog>
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
  </div>
</template>

<script>
import { insertPhoto, searchSysPhotos, updatePhoto } from '@/api/photo'

export default {
  name: 'SelectPhoto',
  props: {
    // eslint-disable-next-line vue/prop-name-casing
    dialog_visible: Boolean
  },
  data() {
    return {
      selectLoading: false,
      selectPhotoList: [],
      selectPhotoUrlList: [],
      url: '',
      imgURL: {
        uid: 'dd8f57fc3b53b197beb27912d0002345',
        token: '615e392c6e4b8dbb2c70f2eebb18e595'
      },
      keyword: null,
      photoForm: {
        id: null,
        name: '',
        description: ''
      },
      page: 1,
      editPhoto: false,
      pageSize: 12,
      total: null
    }
  },
  watch: {
    dialog_visible() {
      if (this.dialog_visible) {
        this.selectLoading = true
        this.listSelectPhotos()
      }
    }
  },
  created() {
  },
  methods: {
    searchPhotos() {
      const query = {
        page: this.page,
        pageSize: this.pageSize,
        keyword: this.keyword
      }
      searchSysPhotos(query).then(data => {
        this.selectPhotoList = data.data.items
        this.total = data.data.total
        this.selectLoading = false
      })
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
        this.listSelectPhotos()
        this.editPhoto = false
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.editPhoto = false
      })
    },
    beforeUpload(file) {
      this.selectLoading = true
    },
    upload(response) {
      const photo = {
        albumId: '1552620789100072963',
        name: response.data.relative_path,
        url: response.data.url,
        deleteUrl: response.data.delete
      }
      insertPhoto(photo).then(data => {
        this.$notify.success({
          title: '成功',
          message: data.message
        })
        this.listSelectPhotos()
      }).catch(error => {
        this.$notify.error({
          title: '失败',
          message: error.message
        })
        this.listSelectPhotos()
      })
    },
    errorUpload(err) {
      console.log(err)
    },
    giveUrl() {
      this.$emit('giveUrl', this.url)
      this.$emit('dialogVisibleEvent', false)
    },
    beforeClose() {
      this.$emit('dialogVisibleEvent', false)
    },
    sizeChange(pageSize) {
      this.pageSize = pageSize
      this.listSelectPhotos()
    },
    currentChange(page) {
      this.page = page
      this.listSelectPhotos()
    },
    listSelectPhotos() {
      const query = {
        page: this.page,
        pageSize: this.pageSize,
        keyword: this.keyword
      }
      searchSysPhotos(query).then(data => {
        this.selectPhotoList = data.data.items
        this.total = data.data.total
        this.selectLoading = false
      })
    },
    handleCheckedPhotoChange(value) {
      this.url = value
      // const checkedCount = value.length
      // this.checkAll = checkedCount === this.photoIdList.length
      // this.isIndeterminate =
      //   checkedCount > 0 && checkedCount < this.photoIdList.length
    },
    handleCommand(command) {
      console.log(command)
      const type = command.substring(0, 6)
      const data = command.substring(6)
      if (type === 'delete') {
        this.albumForum.id = data
        this.isdelete = true
      } else {
        // console.log(data)
        this.photoForm = JSON.parse(data)
        this.editPhoto = true
      }
    }
  }
}
</script>
<style>
.dialog-title-container {
  display: flex;
  align-items: center;
  font-weight: bold;
}
.select-photo-container{
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
}
.confirm-photo-container{
  height: 0;
  display: flex;
  align-items: center;
}

.photo-radio{
  position: relative;
  width: 100%;
}

.photo-radio-group{
  position: relative;
  height: 133px;
  width: 100%;
}

.photo-item {
  width: 100%;
  height: 100%;
  position: relative;
  cursor: pointer;
  margin-bottom: 1rem;
}
.photo-operation {
  position: absolute;
  z-index: 1000;
  top: 0.3rem;
  right: 0.5rem;
}

.photo-img {
  width: 100%;
  height: 7rem;
  border-radius: 4px;
}

.photo-name{
  white-space:nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
  width: 100%;
}
</style>
