<template>
  <div>
    <div class="comment-title"><i class="iconfont iconpinglunzu"/>评论</div>
    <!-- 评论框 -->
    <div class="comment-input-wrapper">
      <div style="display:flex">
        <v-avatar size="40">
          <img
            v-if="this.$store.state.avatar"
            :src="this.$store.state.avatar"
          />
          <img
            v-else
            src="https://s3.bmp.ovh/imgs/2022/08/09/dc5018f0e1382db2.png"
          />
        </v-avatar>
        <div style="width:100%" class="ml-3">
          <div class="comment-input">
            <textarea
              class="comment-textarea"
              v-model="content"
              placeholder="留下点什么吧..."
              auto-grow
              dense
            />
          </div>
          <!-- 操作按钮 -->
          <div class="emoji-container">
            <span
              :class="chooseEmoji ? 'emoji-btn-active' : 'emoji-btn'"
              @click="chooseEmoji = !chooseEmoji"
              style="margin-right: 20px"
            >
              <i class="iconfont iconbiaoqing"/>
            </span>
            昵称
            <el-input
              class="input-comment"
              placeholder="昵称（必填*）"
              v-model="nickname"
              clearable>
            </el-input>
            电子邮箱
            <el-input
              class="input-comment"
              placeholder="email（必填*）"
              v-model="email"
              clearable>
            </el-input>
            <button
              @click="insertComment"
              class="upload-btn v-comment-btn"
              style="margin-left:auto"
            >
              提交
            </button>
          </div>
          <!-- 表情框 -->
          <emoji @addEmoji="addEmoji" :chooseEmoji="chooseEmoji"/>
        </div>
      </div>
    </div>
    <!-- 评论详情 -->
    <div v-if="count > 0 && reFresh">
      <!-- 评论数量 -->
      <div class="count">{{ count }} 评论</div>
      <!-- 评论列表 -->
      <div
        style="display:flex"
        class="pt-5"
        v-for="(item, index) of commentList"
        :key="item.id"
      >
        <!-- 头像 -->
        <v-avatar size="40" class="comment-avatar">
          <img :src="item.avatar"/>
        </v-avatar>
        <div class="comment-meta">
          <!-- 用户名 -->
          <div class="comment-user">
            <span v-if="!item.webSite">{{ item.nickname }}</span>
            <a v-else :href="item.webSite" target="_blank">
              {{ item.nickname }}
            </a>
            <span class="blogger-tag" v-if="item.userId === 1">博主</span>
          </div>
          <!-- 信息 -->
          <div class="comment-info">
            <!-- 楼层 -->
            <span style="margin-right:10px">{{ count - index }}楼</span>
            <!-- 发表时间 -->
            <span style="margin-right:10px">{{ item.createDate | time }}</span>
            <!-- 点赞 -->
            <span
              :class="isLike(item.id) + ' iconfont icondianzan'"
              @click="like(item)"
            />
            <span v-show="item.likeCounts > 0"> {{ item.likeCounts }}</span>
            <!-- 回复 -->
            <span class="reply-btn" @click="replyComment(index, item)">
              回复
            </span>
          </div>
          <!-- 评论内容 -->
          <p v-html="item.content" class="comment-content"></p>
          <!-- 回复人 -->
          <div
            style="display:flex"
            v-for="reply of item.replyVoList"
            :key="reply.id"
          >
            <!-- 头像 -->
            <v-avatar size="36" class="comment-avatar">
              <img :src="reply.avatar"/>
            </v-avatar>
            <div class="reply-meta">
              <!-- 用户名 -->
              <div class="comment-user">
                <span>{{ reply.nickname }}</span>
                <span class="blogger-tag" v-if="reply.userId === 1">博主</span>
              </div>
              <!-- 信息 -->
              <div class="comment-info">
                <!-- 发表时间 -->
                <span style="margin-right:10px">
                  {{ reply.createDate | time }}
                </span>
                <!-- 点赞 -->
                <span
                  :class="isLike(reply.id) + ' iconfont icondianzan'"
                  @click="like(reply)"
                />
                <span v-show="reply.likeCounts > 0"> {{ reply.likeCounts }}</span>
                <!-- 回复 -->
                <span class="reply-btn" @click="replyComment(index, reply)">
                  回复
                </span>
              </div>
              <!-- 回复内容 -->
              <p class="comment-content">
                <!-- 回复用户名 -->
                <template v-if="reply.replyUserId !== item.userId">
                  <span class="ml-1"> @{{ reply.replyNickname + "，" }}</span>
                </template>
                <span v-html="reply.content"/>
              </p>
            </div>
          </div>
          <!-- 回复数量 -->
          <div
            class="mb-3"
            style="font-size:0.75rem;color:#6d757a"
            v-show="item.replyCounts > 3"
            ref="check"
          >
            共
            <b>{{ item.replyCounts }}</b>
            条回复，
            <span
              style="color:#00a1d6;cursor:pointer"
              @click="checkReplies(index, item)"
            >
              点击查看
            </span>
          </div>
          <!-- 回复分页 -->
          <div
            class="mb-3"
            style="font-size:0.75rem;color:#222;display:none"
            ref="paging"
          >
            <span style="padding-right:10px">
              共{{ Math.ceil(item.replyCounts / 5) }}页
            </span>
            <paging
              ref="page"
              :totalPage="Math.ceil(item.replyCounts / 5)"
              :index="index"
              :commentId="item.id"
              @changeReplyCurrent="changeReplyCurrent"
            />
          </div>
          <!-- 回复框 -->
          <Reply :type="type" ref="reply" @reloadReply="reloadReply"/>
        </div>
      </div>
      <!-- 加载按钮 -->
      <div class="load-wrapper">
        <v-btn outlined v-if="count > commentList.length" @click="listComments">
          加载更多...
        </v-btn>
      </div>
    </div>
    <!-- 没有评论提示 -->
    <div v-else style="padding:1.25rem;text-align:center">
      来发评论吧~
    </div>
  </div>
</template>

<script>
import Reply from "./Reply";
import Paging from "./Paging";
import Emoji from "./Emoji";
import EmojiList from "../assets/js/emoji";
import {getCommentsByArticle, getReplyByParentId, likeComment, publishComment} from "@/api/comment";

export default {
  components: {
    Reply,
    Emoji,
    Paging
  },
  props: {
    type: {
      type: Number
    }
  },
  created() {
    this.listComments();
  },
  data: function () {
    return {
      reFresh: true,
      nickname: '',
      email: '',
      content: "",
      chooseEmoji: false,
      current: 1,
      commentList: [],
      count: 0
    };
  },
  methods: {
    replyComment(index, item) {
      this.$refs.reply.forEach(item => {
        item.$el.style.display = "none";
      });
      console.log(item)
      //传值给回复框
      this.$refs.reply[index].content = "";
      this.$refs.reply[index].nickname = item.nickname;
      this.$refs.reply[index].replyUserId = item.userId;
      this.$refs.reply[index].parentId = this.commentList[index].id;
      this.$refs.reply[index].chooseEmoji = false;
      this.$refs.reply[index].index = index;
      this.$refs.reply[index].$el.style.display = "block";
    },
    addEmoji(key) {
      this.content += key;
    },
    checkReplies(index, item) {
      const param = {
        id: item.id,
        page: 1,
        pageSize: 5
      }
      getReplyByParentId(param).then(data => {
        this.$refs.check[index].style.display = "none";
        item.replyVoList = data.data.items;
        //超过1页才显示分页
        if (Math.ceil(item.replyCounts / 5) > 1) {
          this.$refs.paging[index].style.display = "flex";
        }
      })
    },
    changeReplyCurrent(current, index, commentId) {
      const param = {
        id: commentId,
        page: current,
        pageSize: 5
      }
      getReplyByParentId(param).then(data => {
        this.commentList[index].replyVoList = data.data.items;
      })
    },
    listComments() {
      //查看评论
      const path = this.$route.path;
      const arr = path.split("/");
      const param = {
        page: this.current,
        id: arr[2]
      };
      getCommentsByArticle(param).then(data => {
        if (this.current === 1) {
          this.commentList = data.data.items;
        } else {
          this.commentList.push(...data.data.items);
        }
        this.current++;
        this.count = data.data.total;
      })
    },
    insertComment() {
      //判断登录
      // if (!this.$store.state.userId) {
      //   this.$store.state.loginFlag = true;
      //   return false;
      // }
      //判空
      if (this.content.trim() === "") {
        this.$toast({type: "error", message: "评论不能为空"});
        return false;
      }
      if (this.email.trim() === "") {
        this.$toast({type: "error", message: "邮箱不能为空"});
        return false;
      }
      if (this.nickname.trim() === "") {
        this.$toast({type: "error", message: "昵称不能为空"});
        return false;
      }
      //解析表情
      var reg = /\[.+?\]/g;
      this.content = this.content.replace(reg, function (str) {
        return (
          "<img src= '" +
          EmojiList[str] +
          "' width='24'height='24' style='margin: 0 1px;vertical-align: text-bottom'/>"
        );
      });
      //发送请求
      const path = this.$route.path;
      const arr = path.split("/");
      const comment = {
        content: this.content,
        nickname: this.nickname,
        email: this.email,
        parentId: 0,
        articleId: arr[2]
      };
      this.content = "";
      publishComment(comment).then(data => {
        this.current = 1;
        this.listComments();
        this.$toast({type: "success", message: data.message});
      }).catch(error => {
        this.$toast({type: "error", message: error.message});
      })
    },
    like(comment) {
      console.log(comment)
      likeComment(comment.id).then(data => {
        this.$set(comment, "likeCounts", comment.likeCounts + 1);
      })
      // 发送请求
      // this.axios
      //   .post("/api/comments/" + comment.id + "/like")
      //   .then(({ data }) => {
      //     if (data.flag) {
      //       // 判断是否点赞
      //       if (this.$store.state.commentLikeSet.indexOf(comment.id) !== -1) {
      //         this.$set(comment, "likeCount", comment.likeCount - 1);
      //       } else {
      //         this.$set(comment, "likeCount", comment.likeCount + 1);
      //       }
      //       this.$store.commit("commentLike", comment.id);
      //     }
      //   });
    },
    reloadReply(index) {
      this.current = 1
      this.listComments()
      // const param = {
      //   id:this.commentList[index].id,
      //   page:this.$refs.page[index].current,
      //   pageSize:5
      // }
      // getReplyByParentId(param).then(data=>{
      //   this.commentList[index].replyCounts++;
      //   //回复大于5条展示分页
      //   if (this.commentList[index].replyCounts > 5) {
      //     this.$refs.paging[index].style.display = "flex";
      //   }
      //   this.$refs.check[index].style.display = "none";
      //   this.$refs.reply[index].$el.style.display = "none";
      //   this.commentList[index].replyVoList = data.data;
      // })
    }
  },
  computed: {
    isLike() {
      return function (commentId) {
        // var commentLikeSet = this.$store.state.commentLikeSet;
        // return commentLikeSet.indexOf(commentId) != -1 ? "like-active" : "like";
      };
    }
  },
  watch: {
    commentList() {
      this.reFresh = false;
      this.$nextTick(() => {
        this.reFresh = true;
      });
    }
  }
};
</script>

<style scoped>
.blogger-tag {
  background: #ffa51e;
  font-size: 12px;
  display: inline-block;
  border-radius: 2px;
  color: #fff;
  padding: 0 5px;
  margin-left: 6px;
}

.comment-title {
  display: flex;
  align-items: center;
  font-size: 1.25rem;
  font-weight: bold;
  line-height: 40px;
  margin-bottom: 10px;
}

.comment-title i {
  font-size: 1.5rem;
  margin-right: 5px;
}

.comment-input-wrapper {
  border: 1px solid #90939950;
  border-radius: 4px;
  padding: 10px;
  margin: 0 0 10px;
}

.count {
  padding: 5px;
  line-height: 1.75;
  font-size: 1.25rem;
  font-weight: bold;
}

.comment-meta {
  margin-left: 0.8rem;
  width: 100%;
  border-bottom: 1px dashed #f5f5f5;
}

.reply-meta {
  margin-left: 0.8rem;
  width: 100%;
}

.comment-user {
  font-size: 14px;
  line-height: 1.75;
}

.comment-user a {
  color: #1abc9c !important;
  font-weight: 500;
  transition: 0.3s all;
}

.comment-nickname {
  text-decoration: none;
  color: #1abc9c !important;
  font-weight: 500;
}

.comment-info {
  line-height: 1.75;
  font-size: 0.75rem;
  color: #b3b3b3;
}

.reply-btn {
  cursor: pointer;
  float: right;
  color: #ef2f11;
}

.comment-content {
  font-size: 0.875rem;
  line-height: 1.75;
  padding-top: 0.625rem;
  white-space: pre-line;
  word-wrap: break-word;
  word-break: break-all;
}

.comment-avatar {
  transition: all 0.5s;
}

.comment-avatar:hover {
  transform: rotate(360deg);
}

.like {
  cursor: pointer;
  font-size: 0.875rem;
}

.like:hover {
  color: #eb5055;
}

.like-active {
  cursor: pointer;
  font-size: 0.875rem;
  color: #eb5055;
}

.load-wrapper {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.load-wrapper button {
  background-color: #49b1f5;
  color: #fff;
}

.input-comment {
  width: 230px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
