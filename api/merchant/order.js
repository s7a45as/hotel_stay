import request from '@/utils/request'

/**
 * 获取商家订单列表
 * @param {Object} params - 查询参数
 * @returns {Promise<{
 *   list: Array,
 *   total: number
 * }>}
 */
export function getMerchantOrderList(params) {
  return request({
    url: '/merchant/orders',
    method: 'get',
    params,
  })
}

/**
 * 处理退款申请
 * @param {string} orderId - 订单ID
 * @param {Object} data - 退款数据
 */
export function handleRefund(orderId, data) {
  return request({
    url: `/merchant/orders/${orderId}/refund`,
    method: 'post',
    data,
  })
}
