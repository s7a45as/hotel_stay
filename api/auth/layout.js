import request from '@/utils/request'

/**
 * 获取网站配置
 * @returns {Promise<{
 *   siteName: string,
 *   logo: string,
 *   userAgreement: string,
 *   privacyPolicy: string,
 *   helpCenter: string
 * }>}
 */
export function getSiteConfig() {
  return request({
    url: '/auth/site-config',
    method: 'get',
  })
}
