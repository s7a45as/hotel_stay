import request from '@/utils/request'

/**
 * 获取订单列表
 * @param {Object} params - 查询参数
 * @returns {Promise<{
 *   list: Array,
 *   total: number
 * }>}
 */
export function getOrderList(params) {
  return request({
    url: '/admin/orders',
    method: 'get',
    params,
  })
}

/**
 * 更新订单状态
 * @param {string} orderId - 订单ID
 * @param {Object} data - 更新数据
 */
export function updateOrderStatus(orderId, data) {
  return request({
    url: `/admin/orders/${orderId}/status`,
    method: 'put',
    data,
  })
}

/**
 * 导出订单数据
 * @param {Object} params - 查询参数
 */
export function exportOrders(params) {
  return request({
    url: '/admin/orders/export',
    method: 'get',
    params,
    responseType: 'blob',
  })
}
