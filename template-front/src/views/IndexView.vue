<script setup>
import {Delete, EditPen, Plus, User} from "@element-plus/icons-vue";
import {apiStudentList, apiStudentSave} from "@/net";
import {reactive, watchEffect} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {get, post, logout} from "@/net";
import router from "@/router";

const editor = reactive({
  id: 0,
  display: false,
  temp: {},
  loading: false,
  isAdd: false
})

const userTable = reactive({
  page: 1,
  size: 10,
  total: 0,
  data: []
})

watchEffect(() => apiStudentList(userTable.page, userTable.size, data => {
  userTable.total = data.total
  userTable.data = data.list
  console.log(data)
}))

function openUserEditor(user) {
  editor.isAdd = false
  editor.id = user.id
  editor.display = true
  editor.loading = true
  editor.temp = { ...user }
  editor.loading = false
  console.log(editor.temp)
}

function saveUserDetail() {
  editor.display = false
  if(editor.isAdd) {
    // 添加学生
    post('/api/student/add', editor.temp, () => {
      // 刷新列表
      apiStudentList(userTable.page, userTable.size, data => {
        userTable.total = data.total
        userTable.data = data.list
      })
      ElMessage.success('添加学生成功')
    })
  } else {
    // 更新学生
    apiStudentSave(editor.temp, () => {
      const user = userTable.data.find(user => user.id === editor.id)
      Object.assign(user, editor.temp)
      ElMessage.success('数据保存成功')
    })
  }
}

function openAddUserEditor() {
  editor.isAdd = true
  editor.display = true
  editor.temp = {
    name: '',
    className: '',
    major: '',
    studentId: ''
  }
}

function deleteUser(id) {
  ElMessageBox.confirm('确定要删除该学生吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    post(`/api/student/delete/${id}`, {}, () => {
      // 刷新列表
      apiStudentList(userTable.page, userTable.size, data => {
        userTable.total = data.total
        userTable.data = data.list
      })
      ElMessage.success('删除学生成功')
    })
  }).catch(() => {
    // 用户取消删除操作，不做任何处理
  })
}

function handleLogout() {
  logout(() => {
    router.push('/')
  })
}
</script>

<template>
  <div class="user-admin">
    <div style="display: flex; justify-content: space-between; align-items: center">
      <div>
        <div class="title">
          <el-icon><User/></el-icon>
          学生列表
        </div>
        <div class="desc">
          在这里管理学生的信息
        </div>
      </div>
      <div>
        <el-button type="primary" :icon="Plus" @click="openAddUserEditor">添加学生</el-button>
        <el-button type="danger" @click="handleLogout">退出登录</el-button>
      </div>
    </div>
    <el-table :data="userTable.data" height="320">
      <el-table-column prop="id" label="编号" width="80"/>
      <el-table-column label="用户名" width="180">
        <template #default="{ row }">
          <div class="table-username">
            <div>{{ row.name }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="className" label="班级">
        <template #default="{ row }">
          <div class="table-className">
            <div>{{ row.className }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="major" label="专业">
        <template #default="{ row }">
          <div class="table-major">
            <div>{{ row.major }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="studentId" label="学号">
        <template #default="{ row }">
          <div class="table-studentId">
            <div>{{ row.studentId }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" :icon="EditPen"
                     @click="openUserEditor(row)"
                     >编辑</el-button>
          <el-button type="danger" size="small" :icon="Delete"
                     @click="deleteUser(row.id)"
                     >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination :total="userTable.total"
                     v-model:current-page="userTable.page"
                     v-model:page-size="userTable.size"
                     layout="total, sizes, prev, pager, next, jumper"/>
    </div>
    <el-drawer v-model="editor.display">
      <template #header>
        <div>
          <div style="font-weight: bold">
            <el-icon><EditPen/></el-icon> {{ editor.isAdd ? '添加学生' : '编辑学生信息' }}
          </div>
          <div style="font-size: 13px">{{ editor.isAdd ? '添加完成后请点击下方保存按钮' : '编辑完成后请点击下方保存按钮' }}</div>
        </div>
      </template>
      <el-form label-position="top">
        <el-form-item label="学生姓名">
          <el-input v-model="editor.temp.name"/>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="editor.temp.className"/>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="editor.temp.major"/>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="editor.temp.studentId"/>
        </el-form-item>
        <el-divider/>
      </el-form>
      <template #footer>
        <div style="text-align: center">
          <el-button type="success" @click="saveUserDetail">保存</el-button>
          <el-button type="info" @click="editor.display = false">取消</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<style lang="less" scoped>
.user-admin {
  .title {
    font-weight: bold;
  }

  .desc {
    color: #bababa;
    font-size: 13px;
    margin-bottom: 20px;
  }

  .table-username {
    height: 30px;
    display: flex;
    align-items: center;
    gap: 15px;
  }

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }
  :deep(.el-drawer__header) {
    margin-bottom: 0;
  }
}
</style>