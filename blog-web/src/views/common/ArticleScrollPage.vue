<template>
  <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
  </scroll-page>
</template>

<script>
import ArticleItem from '@/components/article/ArticleItem'
import ScrollPage from '@/components/scrollpage'
import {getArticles} from '@/api/article'

export default {
  name: "ArticleScrollPage",
  props: {
    offset: {
      type: Number,
      default: 100
    },
    page: {
      type: Object,
      default() {
        return {}
      }
    },
    query: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  watch: {
    'query': {
      handler() {
        this.noData = false
        this.articles = []
        this.innerPage.pageNumber = 1
        this.getArticles()
      },
      deep: true
    },
    'page': {
      handler() {
        this.noData = false
        this.articles = []
        this.innerPage = this.page
        this.getArticles()
      },
      deep: true
    }
  },
  created() {
    this.getArticles()
  },
  data() {
    return {
      loading: false,
      noData: false,
      innerPage: {
        pageSize: 10,
        page: 1,
      },
      articles: []
    }
  },
  methods: {
    load() {
      this.getArticles()
    },
    view(id) {
      this.$router.push({path: `/view/${id}`})
    },
    getArticles() {
      let that = this
      that.loading = true
      getArticles(this.innerPage).then(data => {
        console.log(data.data.items)
        let newArticles = data.data.items
        if (newArticles && newArticles.length > 0) {
          that.innerPage.pageNumber += 1
          that.articles = that.articles.concat(newArticles)
        } else {
          that.noData = true
        }

      }).catch(error => {
        if (error !== 'error') {
          that.$message({type: 'error', message: '文章加载失败!', showClose: true})
        }
      }).finally(() => {
        that.loading = false
      })

    }
  },
  components: {
    'article-item': ArticleItem,
    'scroll-page': ScrollPage
  }

}
</script>

<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 10px;

}
</style>
