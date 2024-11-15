import request from '@/utils/request'

/**
 * 更新密码
 * @param {Object} data - 密码数据
 * @param {string} data.oldPassword - 旧密码
 * @param {string} data.newPassword - 新密码
 */
export function updatePassword(data) {
  return request({
    url: '/user/security/password',
    method: 'put',
    data,
  })
}

/**
 * 更新手机号
 * @param {Object} data - 手机号数据
 * @param {string} data.phone - 新手机号
 * @param {string} data.code - 验证码
 */
export function updatePhone(data) {
  return request({
    url: '/user/security/phone',
    method: 'put',
    data,
  })
}

/**
 * 发送验证码
 * @param {Object} data - 请求数据
 * @param {string} data.phone - 手机号
 */
export function sendVerifyCode(data) {
  return request({
    url: '/user/security/verify-code',
    method: 'post',
    data,
  })
}
