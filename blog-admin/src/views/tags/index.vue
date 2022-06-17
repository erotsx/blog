<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form v-show="showSearch" ref="form" :inline="true" :model="params" label-width="68px">
        <el-form-item label="标题">
          <el-input v-model="params.title" style="width: 150px" size="small" placeholder="请输入文章名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :key="tableKey"
        v-loading="listLoading"
        :data="list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
        @sort-change="sortChange"
      >
        <el-table-column prop="title" align="center" label="标题" width="220">
          <template v-slot:default="slotProps">
            <el-link :underline="false" @click="onClick(slotProps.row)">{{ slotProps.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" width="125" label="分类">
          <template v-slot:default="slotProps">
            <el-tag
              style="margin-left: 3px"
              align="center"
              type="warning"
            >{{ slotProps.row.categoryName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          width="220"
          label="标签"
        >
          <template v-slot:default="slotProps">
            <el-tag
              v-for="(item) in slotProps.row.tags"
              style="margin-left: 3px"
              align="center"
              type="primary"
            >{{ item }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" align="center" class-name="status-col" width="100" />
        <el-table-column label="评论次数" prop="commentCounts" align="center" width="95" />
        <el-table-column label="访问次数" prop="viewCounts" align="center" width="95" />
        <!--      <el-table-column label="作者" prop="author" width="110px" align="center"></el-table-column>-->
        <el-table-column v-if="showReviewer" label="Reviewer" width="110px" align="center" />
        <el-table-column label="创建时间" prop="createDate" width="150px" sortable align="center" />
        <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
          <template v-slot:default="slotProps">
            <el-button type="primary" size="mini" @click="handleUpdate(slotProps.row)">
              编辑
            </el-button>
            <el-button v-if="slotProps.row.status!='draft'" size="mini" @click="handleModifyStatus(slotProps.row,'draft')">
              设置
            </el-button>
            <el-button v-if="slotProps.row.status!='deleted'" size="mini" type="danger" @click="handleDelete(slotProps.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" :page.sync="params.page" :limit.sync="params.pageSize" @pagination="getList" />

      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
          <el-form-item label="Type" prop="type">
            <el-select v-model="temp.type" class="filter-item" placeholder="Please select">
              <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
            </el-select>
          </el-form-item>
          <el-form-item label="Date" prop="timestamp">
            <el-date-picker v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
          </el-form-item>
          <el-form-item label="Title" prop="title">
            <el-input v-model="temp.title" />
          </el-form-item>
          <el-form-item label="Status">
            <el-select v-model="temp.status" class="filter-item" placeholder="Please select">
              <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="Imp">
            <el-rate v-model="temp.importance" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" :max="3" style="margin-top:8px;" />
          </el-form-item>
          <el-form-item label="Remark">
            <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">
            Cancel
          </el-button>
          <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
            Confirm
          </el-button>
        </div>
      </el-dialog>

      <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
        <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
          <el-table-column prop="key" label="Channel" />
          <el-table-column prop="pv" label="Pv" />
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {

  data() {
    return {
      showSearch: true,
      tableKey: 0,
      listLoading: true,
      list: null,
      params: {
        tagName: null,
        page: 1,
        pageSize: 10
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    }
  }
}
</script>

