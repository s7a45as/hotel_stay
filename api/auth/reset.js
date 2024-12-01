import request from '@/utils/request'

/**
 * 重置密码
 * @param {Object} data - 请求数据
 * @param {string} data.token - 重置令牌
 * @param {string} data.password - 新密码
 * @param {string} data.type - 验证类型(email/phone)
 * @param {string} data.target - 目标(邮箱/手机号)
 */
export function resetPassword(data) {
  return request({
    url: '/auth/reset-password',
    method: 'post',
    data,
  })
}

/**
 * 验证重置令牌
 * @param {Object} params - 查询参数
 * @param {string} params.token - 重置令牌
 * @param {string} params.type - 验证类型
 */
export function verifyResetToken(params) {
  return request({
    url: '/auth/verify-reset-token',
    method: 'get',
    params,
  })
}
export function sendVerifyCode(params) {
  return request({})
}

export function verifyCode(params) {
  return request({})
}
