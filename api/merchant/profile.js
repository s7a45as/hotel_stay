import request from '@/utils/request'

/**
 * 获取商家信息
 * @returns {Promise<Object>}
 */
export function getMerchantProfile() {
  return request({
    url: '/merchant/profile',
    method: 'get',
  })
}

/**
 * 更新商家信息
 * @param {Object} data - 商家信息
 */
export function updateMerchantProfile(data) {
  return request({
    url: '/merchant/profile',
    method: 'post',
    data,
  })
}

/**
 * 更新商家密码
 * @param {Object} data - 密码数据
 */
export function updateMerchantPassword(data) {
  return request({
    url: '/merchant/password',
    method: 'post',
    data,
  })
}

/**
 * 上传商家头像
 * @param {FormData} data 包含文件的 FormData 对象，文件参数名为 'file'
 * @returns {Promise<{url: string}>} 返回上传后的头像URL
 */
export function uploadMerchantAvatar(data) {
  return request({
    url: '/merchant/avatar',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
