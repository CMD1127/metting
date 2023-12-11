import request from '@/utils/request'

export default{
      
        // 添加小节
        addVideo(eduVideo){
          return request({
              url: `/eduservice/eduvideo/addVideo`,
              method: 'post',
              data:eduVideo
            })
      },
      // 根据id获取章节
      getVideo(id){
        return request({
            url: `/eduservice/eduvideo/getVideo/${id}`,
            method: 'get'
            
          })
    },
    // 修改小节
    updateVideo(eduVideo){
      return request({
          url: `/eduservice/eduvideo/updateVideo`,
          method: 'post',
          data:eduVideo
        })    
  },
  // 删除小节
  deleteVideo(id){
    return request({
        url: `/eduservice/eduvideo/${id}`,
        method: 'delete'
        
      })
},
// 删除阿里云视频
removeAlyVideo(videoId){
  return request({
      url: `/eduvod/video/removeAlyVideo/${videoId}`,
      method: 'delete'
      
    })
}
}