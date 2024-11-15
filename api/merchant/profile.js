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
    method: 'put',
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
    method: 'put',
    data,
  })
}

/**
 * 上传商家头像
 * @param {FormData} data - 头像数据
 */
export function uploadMerchantAvatar(data) {
  return request({
    url: '/merchant/avatar',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
