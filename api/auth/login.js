import request from '@/utils/request'

/**
 * 用户登录
 * @param {Object} data - 登录数据
 * @param {string} data.username - 用户名
 * @param {string} data.password - 密码
 * @param {string} data.type - 登录类型(user/merchant)
 * @returns {Promise<{
 *   token: string,
 *   userInfo: {
 *     id: number,
 *     username: string,
 *     nickname: string,
 *     role: string,
 *     avatar: string
 *   }
 * }>}
 */
export function login(data) {
  const url = `/auth/login`
  return request({
    url,
    method: 'post',
    data,
  })
}

/**
 * 社交账号登录
 * @param {string} type - 社交账号类型
 * @returns {Promise<{
 *   token: string,
 *   userInfo: Object
 * }>}
 */
export function socialLogin(type) {
  return request({
    url: `/auth/social-login/${type}`,
    method: 'get',
  })
}
