import request from '@/utils/request'

/**
 * 获取商家信息
 * @returns {Promise<{
 *   id: number,
 *   name: string,
 *   avatar: string,
 *   role: string
 * }>}
 */
export function getMerchantInfo() {
  return request({
    url: '/merchant/info',
    method: 'get',
  })
}

/**
 * 获取商家菜单配置
 * @returns {Promise<Array>}
 */
export function getMerchantMenus() {
  return request({
    url: '/merchant/menus',
    method: 'get',
  })
}
