<template>
    <div class="app-container">
      人员添加
      <br>
      <br>
      <el-form label-width="120px">
        <el-form-item label="人员名称">
          <el-input v-model="employee.name" style="width: 400px;" />
        </el-form-item>
        
        <el-form-item label="年龄">
          <el-input v-model="employee.age" style="width: 400px;"/>
        </el-form-item>
     
        <el-form-item label="性别">
          <el-select v-model="employee.sex" placeholder="请选择性别">
            <el-option v-for="item in sexList" :key="item.id" :label="item.name" :value="parseInt(item.id)"></el-option>
    </el-select>
        </el-form-item>

        <el-form-item
        label="头像上传"
        :rules="[
          { required: true, message: '头像不能为空'}
        ]"
      >
        <div class="touxiang">
          <div class="pic">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </div>
          <el-upload
            :show-file-list="false"
            :on-success="imgHandleSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/upload/avatar'"
          >
            <el-button size="small" type="primary" class="mybuttom">头像上传</el-button>
          </el-upload>
        </div>
      </el-form-item>
 
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
   import employee from '@/api/edu/employee'
   import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
  export default {
    name: 'AvatarUploadDemo',
  components: { ImageCropper, PanThumb },
    data() {
      return {
        sexList: [
        { id: 1, name: '男' },
        { id: 0, name: '女' },
      ],
        BASE_API: process.env.BASE_API, // 接口API地址
        imageUrl: '',
fileListAvater: [],
imagecropperShow: false,
imagecropperKey: 0,
image: 'https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191',
        employee: {
          sex:''
        }
      }
    },
    watch: {
    $route(to, from) {
      this.init()
    }
  },
    created() {
      this.init();
    },
    methods: {
       // 头像上传
    imgHandleSuccess(response) {
      if (response.code !== 20000) {
        this.$message.error('头像上传失败')
        return
      } else {
        this.$message.success('头像上传成功')
      }
      this.imageUrl = response.data.imagePath
      this.employee.avatar = response.data.imagePath[0]
      console.log(this.imageUrl)
  
      // // 得到一个图片信息对象 临时路径
      // const picInfo = { pic: response.data }
      // // 将图片信息对象，push到fileList数组中
      // this.imgUpload.fileList.push(picInfo)
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
      init(){
//页面渲染之前执行
      //获取路由id值 调用接口得到医院设置信息
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getemployee(id)
      }else{
            this.employee={}
      }
      },
      //根据id查询
      getemployee(id) {
        employee.getById(id)
          .then(response => {
            this.employee = response.data.item
            this.imageUrl = response.data.item.avatar
          })
      },
      // 修改
      update() {
        employee.update(this.employee)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/employee/list' })
          })
      },
      // 添加
      save() {
        employee.save(this.employee)
          .then(reponse => {
            //提示
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            //跳转列表页面，使用路由跳转方式实现
            this.$router.push({ path: '/employee/list' })
          })
      },
      //添加
      saveOrUpdate() {
        //判断添加还是修改
        if (!this.employee.id) { //没有id，做添加
          this.save();
        } else {//修改
          this.update()
        }
      }
    }
  }
  </script>
 <style>
.touxiang {
  margin: 30px auto 30px 100px;
  display: flex;
.avatar-uploader {
::v-deep .el-upload {
  margin-top: 5px;
  height: 45px;
  display: flex;
  flex-direction: column;
  align-content: space-between;
}
::v-deep .el-button {
  width: 90px;
  height: 35px;
  font-size: 15px;
}
}
.pic {
  margin-right: 20px;
  border-radius: 50%;
  border: 1px dashed gray;
  height: 80px;
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  border-radius: 50%;
  width: 80px;
  height: 80px;
  display: block;
}
}
}
</style>

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>