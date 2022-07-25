<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">分类</h1>
    </div>
    <!-- 分类列表 -->
    <v-card class="blog-container">
      <div class="category-title">分类 - {{ count }}</div>
      <ul class="category-list">
        <li
          class="category-list-item"
          v-for="item of categoryList"
          :key="item.id"
        >
          <router-link :to="'/categories/' + item.id">
            {{ item.categoryName }}
            <span class="category-count">({{ item.articleCount }})</span>
          </router-link>
        </li>
      </ul>
    </v-card>
  </div>
</template>

<script>
import {getAllCategories} from "@/api/category";

export default {
  name: 'category',
  created() {
    this.listCategories();
  },
  data: function () {
    return {
      categoryList: [],
      count: 0
    };
  },
  methods: {
    listCategories() {
      getAllCategories().then(data => {
        this.categoryList = data.data.items;
        this.count = data.data.total;
      }).catch(error => {

      })
    }
  },
  computed: {
    cover() {
      var cover = "https://www.notion.so/image/https%3A%2F%2Fwww.notion.so%2Fimages%2Fpage-cover%2Fgradients_11.jpg?table=collection&id=d0dcb5f1-9324-4c20-9a6f-2446aac9b4f9&spaceId=2f6f3d8b-da16-4d45-be45-4e1ecc295f74&width=2000&userId=2711b3bd-8fe1-40f8-b972-6c2370eb1135&cache=v2";
      // this.$store.state.blogInfo.pageList.forEach(item => {
      //   if (item.pageLabel == "category") {
      //     cover = item.pageCover;
      //   }
      // });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
.category-title {
  text-align: center;
  font-size: 36px;
  line-height: 2;
}

@media (max-width: 759px) {
  .category-title {
    font-size: 28px;
  }
}

.category-list {
  margin: 0 1.8rem;
  list-style: none;
}

.category-list-item {
  padding: 8px 1.8rem 8px 0;
}

.category-list-item:before {
  display: inline-block;
  position: relative;
  left: -0.75rem;
  width: 12px;
  height: 12px;
  border: 0.2rem solid #49b1f5;
  border-radius: 50%;
  background: #fff;
  content: "";
  transition-duration: 0.3s;
}

.category-list-item:hover:before {
  border: 0.2rem solid #ff7242;
}

.category-list-item a:hover {
  transition: all 0.3s;
  color: #8e8cd8;
}

.category-list-item a:not(:hover) {
  transition: all 0.3s;
}

.category-count {
  margin-left: 0.5rem;
  font-size: 0.75rem;
  color: #858585;
}

.blog-container {

  animation: main 1s;
  max-width: 970px;
  padding: 50px 40px;
  margin: 380px auto 40px auto;
}
</style>
