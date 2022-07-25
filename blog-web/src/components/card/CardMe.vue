<template>
  <el-card class="box-card">
    <div class="avatar-container">
      <v-avatar size="110" style="margin-bottom:0.5rem">
        <img :src="avatar" alt=""/>
      </v-avatar>
    </div>
    <h1 class="me-author-name" id="nickname"></h1>
    <div class="me-author-description">
      <span><i class="me-introduction" id="introduction"></i></span>
    </div>
    <div class="me-author-tool">
      <a v-if="show.show_qq" :title="qq" class="iconfont iconqq"
         :href="
         'http://wpa.qq.com/msgrd?v=3&uin=' +
                    qq +
                    '&site=qq&menu=yes'
                "></a>
      <a
        v-if="show.show_github"
        target="_blank"
        :href="github"
        class="mr-5 iconfont icongithub"
      />
    </div>
  </el-card>

</template>

<script>
import {getBloggerInfo} from "@/api/user";

export default {
  computed: {
    drawer: {
      set(value) {
        this.$store.state.drawer = value;
      },
      get() {
        return this.$store.state.drawer;
      }
    }
  },
  name: 'CardMe',
  created() {
    this.getBloggerInfo()
  },
  data() {
    return {
      nickname: '',
      avatar: '',
      introduction: '',
      show: {
        show_qq: false,
        show_github: false
      },
      qq: '',
      github: ''
    }
  },
  methods: {
    getBloggerInfo() {
      getBloggerInfo().then(data => {
        if (data.data.qq != null) {
          this.qq = data.data.qq
          this.show.show_qq = true
        }
        if (data.data.github != null) {
          this.github = data.data.github
          this.show.show_github = true
        }

        this.avatar = data.data.avatar
        this.nickname = data.data.nickname
        this.introduction = data.data.introduction
        document.getElementById("nickname").innerText = data.data.nickname
        document.getElementById("introduction").innerText = data.data.introduction
      }).catch(error => {
        if (error !== 'error') {
          this.$message({type: 'error', message: '博主信息加载失败!', showClose: true})
        }
      })
    },
    showTool(tool) {
      this.$message({
        duration: 0,
        showClose: true,
        dangerouslyUseHTMLString: true,
        message: '<strong>' + tool.message + '</strong>'
      });
    }
  }
}
</script>

<style scoped>
.avatar-container {
  display: flex;
  justify-content: center;
}

.me-author-name {
  text-align: center;
  font-size: 30px;
  border-bottom: 1px solid #5FB878;
}

.me-author-description {
  padding-top: 10px;
  text-align: center;
}

.me-introduction {
  text-align: center;
}

.me-author-tool {
  text-align: center;
  padding-top: 10px;
}

.me-author-tool i {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
}

.me-author-tool a {
  cursor: pointer;
  padding: 4px 10px;
  font-size: 30px;
  color: #0d1117;
}


</style>
