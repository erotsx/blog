<template>
  <el-card class="me-area" :body-style="{ padding: '16px' }" style="height: 200px">
    <el-row :gutter="20" style="height: 160px">
      <el-col :span="10" style="height: 160px">
        <router-link :to="'/article/' + id">
          <v-img
            class="on-hover"
            width="100%"
            height="100%"
            :src="cover"
          />
        </router-link>
      </el-col>
      <el-col :span="14">
        <div class="article-wrapper">
          <div style="line-height:1.4">
            <router-link :to="'/article/' + id">
              {{ title }}
            </router-link>
          </div>
          <div class="article-info">
            <!-- 是否置顶 -->
            <span v-if="isTop === 1">
                <span style="color:#ff7242">
                  <i class="iconfont iconzhiding"/> 置顶
                </span>
                <span class="separator">|</span>
              </span>
            <!-- 发表时间 -->
            <v-icon size="14">mdi-calendar-month-outline</v-icon>
            {{ createDate | date }}
            <span class="separator">|</span>
            <!-- 文章分类 -->
            <router-link :to="'/categories/' + categoryId">
              <v-icon size="14">mdi-inbox-full</v-icon>
              {{ categoryName }}
            </router-link>
            <span class="separator">|</span>
            <!-- 文章标签 -->
            <router-link
              style="display:inline-block"
              :to="'/tags/' + tag.id"
              class="mr-1"
              v-for="tag of tags"
              :key="tag.id"
            >
              <v-icon size="14">mdi-tag-multiple</v-icon>
              {{ tag.tagName }}
            </router-link>
          </div>
          <!-- 文章内容 -->
          <div class="article-content">
            {{ summary }}
          </div>
        </div>
      </el-col>
    </el-row>
    <!--    <div class="article-cover left-radius">-->
    <!--      <router-link :to="'/articles/' + id">-->
    <!--        <v-img-->
    <!--          class="on-hover"-->
    <!--          width="100%"-->
    <!--          height="100%"-->
    <!--          :src="cover"-->
    <!--        />-->
    <!--      </router-link>-->
    <!--    </div>-->
    <!--    <div class="me-article-header">-->

    <!--      <a @click="view(id)" class="me-article-title">{{ title }}</a>-->
    <!--      <el-button v-if="weight > 0" class="me-article-icon" type="text">置顶</el-button>-->
    <!--      <span class="me-pull-right me-article-count">-->
    <!--	    	<i class="me-icon-comment"></i>&nbsp;{{ commentCounts }}-->
    <!--	    </span>-->
    <!--      <span class="me-pull-right me-article-count">-->
    <!--	    	<i class="el-icon-view"></i>&nbsp;{{ viewCounts }}-->
    <!--	    </span>-->
    <!--    </div>-->

    <!--    <div class="me-article-description">-->
    <!--      {{ summary }}-->
    <!--    </div>-->
    <!--    <div class="me-article-footer">-->
    <!--	  	<span class="me-article-author">-->
    <!--	    	<i class="me-icon-author"></i>&nbsp;{{ author }}-->
    <!--	    </span>-->

    <!--      <el-tag v-for="t in tags" :key="t.id" size="mini" type="success">{{ t.tagName }}</el-tag>-->

    <!--      <span class="me-pull-right me-article-count">-->
    <!--	    	<i class="el-icon-time"></i>&nbsp;{{ createDate | format }}-->
    <!--	    </span>-->

    <!--    </div>-->
  </el-card>
</template>

<script>
import {formatTime} from "@/utils/time";

export default {
  name: 'ArticleItem',
  props: {
    id: String,
    weight: Number,
    categoryId: Number,
    categoryName: String,
    isTop: Number,
    title: String,
    commentCounts: Number,
    body: Object,
    viewCounts: Number,
    content: String,
    summary: String,
    author: String,
    tags: Array,
    createDate: String,
    cover: String
  },
  data() {
    return {}
  },
  methods: {
    view(id) {
      this.$router.push({path: `/article/${id}`})
    }
  }
}
</script>

<style scoped>

.me-article-header {
  /*padding: 10px 18px;*/
  /*margin-left: 45%;*/
  /*width: 55%;*/
  padding-bottom: 10px;
}

.me-article-title {
  font-weight: 600;
}

.me-article-icon {
  padding: 3px 8px;
}

.me-article-count {
  color: #a6a6a6;
  padding-left: 14px;
  font-size: 13px;
}

.me-pull-right {
  float: right;
}

.me-artile-description {
  font-size: 13px;
  line-height: 24px;
  margin-bottom: 10px;
}

.me-article-author {
  color: #a6a6a6;
  padding-right: 18px;
  font-size: 13px;
}

.el-tag {
  margin-left: 6px;
}

@media (min-width: 760px) {
  .blog-title {
    font-size: 2.5rem;
  }

  .blog-intro {
    font-size: 1.5rem;
  }

  .blog-contact {
    display: none;
  }

  .home-container {
    max-width: 1200px;
    margin: calc(100vh - 48px) auto 28px auto;
    padding: 0 5px;
  }

  .article-card {
    display: flex;
    align-items: center;
    height: 180px;
    width: 100%;
  }

  .article-cover {
    overflow: hidden;
    height: 100%;
    width: 45%;
  }

  .on-hover {
    /*transition: all 0.6s;*/
  }

  .article-card:hover .on-hover {
    transform: scale(1.1);
  }

  .article-wrapper {
    padding: 0 2.5rem;
    width: 100%;
  }

  .article-wrapper a {
    font-size: 1.5rem;
    transition: all 0.3s;
  }
}

@media (max-width: 759px) {
  .blog-title {
    font-size: 26px;
  }

  .blog-contact {
    font-size: 1.25rem;
    line-height: 2;
  }

  .home-container {
    width: 100%;
    margin: calc(100vh - 66px) auto 0 auto;
  }

  .article-card {
    margin-top: 1rem;
  }

  .article-cover {
    border-radius: 8px 8px 0 0 !important;
    height: 230px !important;
    width: 100%;
  }

  .article-cover div {
    border-radius: 8px 8px 0 0 !important;
  }

  .article-wrapper {
    padding: 1.25rem 1.25rem 1.875rem;
  }

  .article-wrapper a {
    font-size: 1.25rem;
    transition: all 0.3s;
  }
}

.article-info {
  font-size: 95%;
  color: #858585;
  line-height: 2;
  margin: 0.375rem 0;
}

.article-info a {
  font-size: 95%;
  color: #858585 !important;
}

.article-content {
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
</style>
