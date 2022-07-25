<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">归档</h1>
    </div>
    <!-- 归档列表 -->
    <v-card class="blog-container">
      <!--      <timeline>-->
      <!--        <timeline-title> 目前共计{{ count }}篇文章，继续加油</timeline-title>-->
      <!--        <timeline-item v-for="item of archiveList" :key="item.id">-->
      <!--          &lt;!&ndash; 日期 &ndash;&gt;-->
      <!--          <span class="time">{{ item.createTime | date }}</span>-->
      <!--          &lt;!&ndash; 文章标题 &ndash;&gt;-->
      <!--          <router-link-->
      <!--            :to="'/articles/' + item.id"-->
      <!--            style="color:#666;text-decoration: none"-->
      <!--          >-->
      <!--            {{ item.articleTitle }}-->
      <!--          </router-link>-->
      <!--        </timeline-item>-->
      <!--      </timeline>-->
      <!-- 分页按钮 -->
      <el-timeline :reverse="reverse">
        <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          :timestamp="activity.createDate">
          {{ activity.title }}
        </el-timeline-item>
      </el-timeline>
      <v-pagination
        color="#00C4B6"
        v-model="current"
        :length="Math.ceil(count / 10)"
        total-visible="7"
      />
    </v-card>
  </div>
</template>

<script>

import {listArchives} from "@/api/article";

export default {
  name: 'archive',
  components: {},
  created() {
    this.listArchives();
  },
  data: function () {
    return {
      current: 1,
      count: 0,
      archiveList: [],
      reverse: true,
      activities: []
    };
  },
  methods: {
    listArchives() {
      listArchives().then(data => {
        this.activities = data.data.items
      }).catch(error => {
      })
    }
  },
  computed: {
    cover() {
      var cover = "https://www.notion.so/image/https%3A%2F%2Fwww.notion.so%2Fimages%2Fpage-cover%2Fgradients_11.jpg?table=collection&id=d0dcb5f1-9324-4c20-9a6f-2446aac9b4f9&spaceId=2f6f3d8b-da16-4d45-be45-4e1ecc295f74&width=2000&userId=2711b3bd-8fe1-40f8-b972-6c2370eb1135&cache=v2";
      // this.$store.state.blogInfo.pageList.forEach(item => {
      //   if (item.pageLabel == "archive") {
      //     cover = item.pageCover;
      //   }
      // });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  },
  watch: {
    current(value) {
      // this.axios
      //   .get("/api/articles/archives", {
      //     params: {current: value}
      //   })
      //   .then(({data}) => {
      //     this.archiveList = data.data.recordList;
      //     this.count = data.data.count;
      //   });
      // listArchives().then(data => {
      //   this.activities = data.data.items
      // }).catch(error => {
      // })
    }
  }
};
</script>

<style scoped>
.time {
  font-size: 0.75rem;
  color: #555;
  margin-right: 1rem;
}

.blog-container {

  animation: main 1s;
  max-width: 970px;
  padding: 50px 40px;
  margin: 380px auto 40px auto;
}
</style>
