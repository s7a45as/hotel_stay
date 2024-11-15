import request from '@/utils/request'

/**
 * 获取用户设置
 * @returns {Promise<{
 *   notifications: {
 *     email: boolean,
 *     sms: boolean,
 *     orderUpdates: boolean,
 *     promotions: boolean
 *   },
 *   preferences: {
 *     language: string,
 *     currency: string,
 *     timezone: string
 *   }
 * }>}
 */
export function getUserSettings() {
  return request({
    url: '/user/settings',
    method: 'get',
  })
}

/**
 * 更新用户设置
 * @param {Object} data - 设置数据
 */
export function updateUserSettings(data) {
  return request({
    url: '/user/settings',
    method: 'put',
    data,
  })
}
