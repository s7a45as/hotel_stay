import request from '@/utils/request'

/**
 * 获取仪表盘统计数据
 * @returns {Promise<{
 *   totalUsers: number,
 *   totalOrders: number,
 *   totalHouses: number,
 *   revenue: number
 * }>}
 */
export function getDashboardStatistics() {
  return request({
    url: '/admin/dashboard/statistics',
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
    url: '/admin/dashboard/orderTrend',
    method: 'get',
  })
}

/**
 * 获取用户增长趋势数据
 * @returns {Promise<{
 *   months: string[],
 *   users: number[]
 * }>}
 */
export function getUserTrend() {
  return request({
    url: '/admin/dashboard/userTrend',
    method: 'get',
  })
}
