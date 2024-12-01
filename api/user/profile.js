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
    url: '/user/profile/update',
    method: 'post',
    data,
  })
}

/**
 * 更新用户密码
 * @param {Object} params - 密码参数
 * @param {string} params.oldPassword - 旧密码
 * @param {string} params.newPassword - 新密码
 */
export function updateUserPassword(params) {
  return request({
    url: '/user/password/update',
    method: 'post',
    params: {
      oldPassword: params.oldPassword,
      newPassword: params.newPassword
    }
  })
}

/**
 * 上传用户头像
 * @param {FormData} data - 头像数据，字段名为 'file'
 */
export function uploadUserAvatar(data) {
  return request({
    url: '/user/avatar/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
