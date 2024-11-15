import request from '@/utils/request'

// 获取图片列表
export function getGalleryList() {
  return request({
    url: '/merchant/gallery',
    method: 'get',
  })
}

// 上传图片
export function uploadImages(data) {
  return request({
    url: '/merchant/gallery/upload',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// 删除图片
export function deleteImage(id) {
  return request({
    url: `/merchant/gallery/${id}`,
    method: 'delete',
  })
}
