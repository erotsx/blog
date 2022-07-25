<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">标签</h1>
    </div>
    <!-- 标签列表 -->
    <v-card class="blog-container">
      <div class="tag-cloud-title">标签 - {{ count }}</div>
      <div class="tag-cloud">
        <router-link
          :style="{ 'font-size': Math.floor(Math.random() * 10) + 18 + 'px' }"
          v-for="item of tagList"
          :key="item.id"
          :to="'/tags/' + item.id"
        >
          {{ item.tagName }}
        </router-link>
      </div>
    </v-card>
  </div>
</template>

<script>
import {getAllTags} from "@/api/tag";

export default {
  name: 'tag',
  created() {
    this.listTags();
  },
  data: function () {
    return {
      tagList: [],
      count: 0
    };
  },
  methods: {
    listTags() {
      getAllTags().then(data => {
        this.tagList = data.data.items;
        this.count = data.data.total;
      }).catch(error => {

      })
    }
  },
  computed: {
    cover() {
      var cover = "https://www.notion.so/image/https%3A%2F%2Fwww.notion.so%2Fimages%2Fpage-cover%2Fgradients_11.jpg?table=collection&id=d0dcb5f1-9324-4c20-9a6f-2446aac9b4f9&spaceId=2f6f3d8b-da16-4d45-be45-4e1ecc295f74&width=2000&userId=2711b3bd-8fe1-40f8-b972-6c2370eb1135&cache=v2";
      // this.$store.state.blogInfo.pageList.forEach(item => {
      //   if (item.pageLabel == "tag") {
      //     cover = item.pageCover;
      //   }
      // });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
.tag-cloud-title {
  line-height: 2;
  font-size: 36px;
  text-align: center;
}

@media (max-width: 759px) {
  .tag-cloud-title {
    font-size: 25px;
  }
}

.tag-cloud {
  text-align: center;
}

.tag-cloud a {
  color: #616161;
  display: inline-block;
  text-decoration: none;
  padding: 0 8px;
  line-height: 2;
  transition: all 0.3s;
}

.tag-cloud a:hover {
  color: #03a9f4 !important;
  transform: scale(1.1);
}

.blog-container {

  animation: main 1s;
  max-width: 970px;
  padding: 50px 40px;
  margin: 380px auto 40px auto;
}
</style>
