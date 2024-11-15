import request from '@/utils/request'

/**
 * 获取隐私设置
 * @returns {Promise<{
 *   profileVisibility: string,
 *   showOnlineStatus: boolean,
 *   showLastSeen: boolean,
 *   allowFriendRequests: boolean,
 *   allowMessages: boolean,
 *   showEmail: boolean,
 *   showPhone: boolean
 * }>}
 */
export function getPrivacySettings() {
  return request({
    url: '/user/privacy-settings',
    method: 'get',
  })
}

/**
 * 更新隐私设置
 * @param {Object} data - 隐私设置数据
 */
export function updatePrivacySettings(data) {
  return request({
    url: '/user/privacy-settings',
    method: 'put',
    data,
  })
}
