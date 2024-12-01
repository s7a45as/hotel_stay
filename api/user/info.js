import request from '@/utils/request'

/**
 * 获取用户信息
 * @returns {Promise<{
 *   id: number,
 *   username: string,
 *   nickname: string,
 *   avatar: string,
 *   role: string,
 *   permissions: string[],
 *   email: string,
 *   phone: string,
 *   status: number,
 *   createdAt: string
 * }>}
 */
export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

/**
 * 更新用户信息
 * @param {Object} data - 用户信息
 */
export function updateUserInfo(data) {
  return request({
    url: '/user/info',
    method: 'put',
    data
  })
}

/**
 * 更新用户头像
 * @param {FormData} data - 头像文件
 */
export function updateUserAvatar(data) {
  return request({
    url: '/user/avatar',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  })
}
