import request from '@/utils/request'

/**
 * 发送验证码
 * @param {Object} data - 请求数据
 * @param {string} data.email - 邮箱
 * @param {string} data.type - 验证码类型(user/merchant)
 */
export function sendVerifyCode(data) {
  const url = '/auth/register/verify-code'
  return request({
    url,
    method: 'post',
    data,
  })
}

/**
 * 用户注册
 * @param {Object} data - 注册数据
 * @param {string} data.type - 注册类型(user/merchant/admin)
 */
export function register(data) {
  const url = `/auth/register/${data.type}`
  return request({
    url,
    method: 'post',
    data,
  })
}

/**
 * 上传营业执照
 * @param {FormData} data - 包含营业执照图片的表单数据
 */
export function uploadBusinessLicense(data) {
  return request({
    url: '/auth/upload/business-license',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
