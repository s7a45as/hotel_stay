import request from '@/utils/request'

/**
 * 获取系统设置
 * @returns {Promise<Object>}
 */
export function getSettings() {
  return request({
    url: '/admin/settings',
    method: 'get',
  })
}

/**
 * 更新系统设置
 * @param {Object} data - 设置数据
 */
export function updateSettings(data) {
  return request({
    url: '/admin/settings',
    method: 'put',
    data,
  })
}

/**
 * 上传文件
 * @param {FormData} data - 文件数据
 */
export function uploadFile(data) {
  return request({
    url: '/admin/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
