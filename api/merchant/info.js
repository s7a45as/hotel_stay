import request from '@/utils/request'

/**
 * 获取商家信息
 * @returns {Promise<{
 *   id: number,
 *   username: string,
 *   nickname: string,
 *   avatar: string,
 *   role: string,
 *   permissions: string[],
 *   businessName: string,
 *   businessLicense: string,
 *   businessAddress: string,
 *   contactPerson: string,
 *   contactPhone: string,
 *   contactEmail: string,
 *   status: string,
 *   houses: Array,
 *   createdAt: string
 * }>}
 */
export function getMerchantInfo() {
  return request({
    url: '/merchant/profile',
    method: 'get'
  })
}

/**
 * 更新商家基本信息
 * @param {Object} data - 商家信息
 */
export function updateMerchantInfo(data) {
  return request({
    url: '/merchant/profile',
    method: 'post',
    data
  })
}

/**
 * 更新商家营业信息
 * @param {Object} data - 营业信息
 */
export function updateBusinessInfo(data) {
  return request({
    url: '/merchant/profile',
    method: 'put',
    data
  })
}

/**
 * 获取商家统计数据
 * @returns {Promise<{
 *   totalHouses: number,
 *   totalOrders: number,
 *   totalIncome: number,
 *   todayOrders: number,
 *   monthlyIncome: number
 * }>}
 */
export function getMerchantStats() {
  return request({
    url: '/merchant/stats',
    method: 'get'
  })
}
