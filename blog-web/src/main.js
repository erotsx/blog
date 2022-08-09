import Vue from 'vue'
import App from './App'

import Toast from "./components/toast/index";
import router from './router'
import store from './store'
import dayjs from "dayjs";
import animated from "animate.css";
import InfiniteLoading from "vue-infinite-loading";
import "./assets/css/iconfont.css";
import "./assets/css/markdown.css";
import "./assets/css/vue-social-share/client.css";
import "./assets/css/index.css"
// import "highlight.js/styles/atom-one-dark.css";
import Share from "vue-social-share";


import vuetify from "./plugins/vuetify";
import lodash from 'lodash'

// import ElementUI from 'element-ui'
import '@/assets/theme/index.css'


import {formatTime} from "./utils/time";


Vue.config.productionTip = false
Vue.use(Share);
Vue.use(Toast);
Vue.use(animated)
Vue.use(InfiniteLoading)
Vue.config.devtools = true
// Vue.use(ElementUI)

Vue.filter("date", function (value) {
  return dayjs(value).format("YYYY-MM-DD");
});

Vue.filter("year", function (value) {
  return dayjs(value).format("YYYY");
});

Vue.filter("hour", function (value) {
  return dayjs(value).format("HH:mm:ss");
});

Vue.filter("time", function (value) {
  return dayjs(value).format("YYYY-MM-DD HH:mm:ss");
});

Vue.filter("num", function (value) {
  if (value >= 1000) {
    return (value / 1000).toFixed(1) + "k";
  }
  return value;
});

Object.defineProperty(Vue.prototype, '$_', {value: lodash})


Vue.directive('title', function (el, binding) {
  document.title = el.dataset.title
})
// 格式话时间
Vue.filter('format', formatTime)

new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  template: '<App/>',
  components: {App}
})
