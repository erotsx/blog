<template>
  <div class="reply-input-wrapper" style="display: none" ref="reply">
    <textarea
      class="comment-textarea"
      :placeholder="'回复 @' + nickname + '：'"
      auto-grow
      dense
      v-model="content"
    />
    <div class="emoji-container">
      <span
        :class="chooseEmoji ? 'emoji-btn-active' : 'emoji-btn'"
        @click="chooseEmoji = !chooseEmoji"
        style="margin-right: 20px"
      >
        <i class="iconfont iconbiaoqing" />
      </span>
      昵称
      <el-input
        class="input-comment"
        placeholder="昵称（必填*）"
        v-model="username"
        clearable>
      </el-input>
      电子邮箱
      <el-input
        class="input-comment"
        placeholder="email（必填*）"
        v-model="email"
        clearable>
      </el-input>
      <div style="margin-left:auto">
        <button @click="cancleReply" class="cancle-btn v-comment-btn">
          取消
        </button>
        <button @click="insertReply" class="upload-btn v-comment-btn">
          提交
        </button>
      </div>
    </div>
    <!-- 表情框 -->
    <emoji @addEmoji="addEmoji" :chooseEmoji="chooseEmoji"></emoji>
  </div>
</template>

<script>
import Emoji from "./Emoji";
import EmojiList from "../assets/js/emoji";
import {publishComment} from "@/api/comment";
export default {
  components: {
    Emoji
  },
  data: function() {
    return {
      index: 0,
      chooseEmoji: false,
      nickname: "",
      username: '',
      replyUserId: null,
      email: '',
      parentId: null,
      content: ""
    };
  },
  methods: {
    cancleReply() {
      this.$refs.reply.style.display = "none";
    },
    insertReply() {
      //判断登录
      if (this.content.trim() === "") {
        this.$toast({ type: "error", message: "回复不能为空" });
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
      this.content = this.content.replace(reg, function(str) {
        return (
          "<img src= '" +
          EmojiList[str] +
          "' width='22'height='20' style='padding: 0 1px'/>"
        );
      });
      const path = this.$route.path;
      const arr = path.split("/");
      const comment = {
        articleId: arr[2],
        replyUserId: this.replyUserId,
        nickname: this.username,
        email: this.email,
        replyNickname: this.nickname,
        parentId: this.parentId,
        content: this.content
      };
      this.content = "";
      publishComment(comment).then(data => {
        this.$emit("reloadReply", this.index);
        this.$toast({type: "success", message: data.message});
      }).catch(error=>{
        this.$toast({ type: "error", message: error.message });
      })
    },
    addEmoji(text) {
      this.content += text;
    }
  }
};
</script>

<style scoped>
.reply-input-wrapper {
  border: 1px solid #90939950;
  border-radius: 4px;
  padding: 10px;
  margin: 0 0 10px;
}
.input-comment {
  width: 200px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
