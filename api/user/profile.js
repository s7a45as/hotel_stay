import request from '@/utils/request'

/**
 * 获取用户个人信息
 * @returns {Promise<Object>}
 */
export function getUserProfile() {
  return request({
    url: '/user/profile',
    method: 'get',
  })
}

/**
 * 更新用户个人信息
 * @param {Object} data - 用户信息
 */
export function updateUserProfile(data) {
  return request({
    url: '/user/profile',
    method: 'put',
    data,
  })
}

/**
 * 更新用户密码
 * @param {Object} data - 密码数据
 */
export function updateUserPassword(data) {
  return request({
    url: '/user/password',
    method: 'put',
    data,
  })
}

/**
 * 上传用户头像
 * @param {FormData} data - 头像数据
 */
export function uploadUserAvatar(data) {
  return request({
    url: '/user/avatar',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
