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
    url: '/order/list',
    method: 'get',
    params,
  })
}

/**
 * 取消订单
 * @param {string} orderId - 订单ID
 */
export function cancelOrder(orderId) {
  return request({
    url: `/order/${orderId}/cancel`,
    method: 'post',
  })
}

/**
 * 导出订单列表
 * @param {Object} params - 查询参数
 */
export function exportOrderList(params) {
  return request({
    url: '/order/export',
    method: 'get',
    params,
    responseType: 'blob',
  })
}
