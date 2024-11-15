import request from '@/utils/request'

/**
 * 获取商家统计数据
 * @returns {Promise<{
 *   totalHouses: number,
 *   totalOrders: number,
 *   totalIncome: number,
 *   pendingOrders: number
 * }>}
 */
export function getMerchantStatistics() {
  return request({
    url: '/merchant/dashboard/statistics',
    method: 'get',
  })
}

/**
 * 获取订单趋势数据
 * @returns {Promise<{
 *   months: string[],
 *   orders: number[]
 * }>}
 */
export function getOrderTrend() {
  return request({
    url: '/merchant/dashboard/order-trend',
    method: 'get',
  })
}

/**
 * 获取收入趋势数据
 * @returns {Promise<{
 *   months: string[],
 *   income: number[]
 * }>}
 */
export function getIncomeTrend() {
  return request({
    url: '/merchant/dashboard/income-trend',
    method: 'get',
  })
}
