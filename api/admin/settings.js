import request from '@/utils/request'

/**
 * 获取系统设置
 * @returns {Promise<{
 *   siteName: string,
 *   siteDesc: string,
 *   logo: string,
 *   favicon: string,
 *   contactPhone: string,
 *   contactEmail: string,
 *   address: string,
 *   icp: string,
 *   copyright: string,
 *   theme: string,
 *   language: string,
 *   registrationEnabled: boolean,
 *   defaultUserRole: string,
 *   emailNotification: boolean,
 *   smsNotification: boolean,
 *   maintenance: boolean,
 *   maintenanceMessage: string,
 *   paymentSettings: Object,
 *   notificationTemplates: Array
 * }>}
 */
export function getSettings() {
  return request({
    url: '/admin/settings',
    method: 'get'
  })
}

/**
 * 更新系统设置
 * @param {Object} data - 设置数据
 */
export function updateSettings(data) {
  return request({
    url: '/admin/settings',
    method: 'put',
    data,
  })
}

/**
 * 上传文件
 * @param {FormData} data - 文件数据
 */
export function uploadFile(data) {
  return request({
    url: '/admin/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}

/**
 * 获取支付配置
 */
export function getPaymentSettings() {
  return request({
    url: '/admin/settings/payment',
    method: 'get'
  })
}

/**
 * 更新支付配置
 */
export function updatePaymentSettings(data) {
  return request({
    url: '/admin/settings/payment',
    method: 'put',
    data
  })
}

/**
 * 获取通知模板
 */
export function getNotificationTemplates() {
  return request({
    url: '/admin/settings/notification-templates',
    method: 'get'
  })
}

/**
 * 更新通知设置
 * @param {Object} data - 通知设置数据
 */
export function updateNotificationSettings(data) {
  return request({
    url: '/admin/settings/notifications',
    method: 'put',
    data
  })
}

/**
 * 测试邮件配置
 * @param {Object} config - 邮件配置
 */
export function testEmailSettings(config) {
  return request({
    url: '/admin/settings/test-email',
    method: 'post',
    data: config
  })
}

/**
 * 测试短信配置
 * @param {Object} config - 短信配置
 */
export function testSmsSettings(config) {
  return request({
    url: '/admin/settings/test-sms',
    method: 'post',
    data: config
  })
}

/**
 * 更新通知模板
 */
export function updateNotificationTemplate(id, data) {
  return request({
    url: `/admin/settings/notification-templates/${id}`,
    method: 'put',
    data
  })
}
