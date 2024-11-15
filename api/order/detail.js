import request from '@/utils/request'

/**
 * 获取订单详情
 * @param {string} orderId - 订单ID
 * @returns {Promise<Object>}
 */
export function getOrderDetail(orderId) {
  return request({
    url: `/order/detail/${orderId}`,
    method: 'get',
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
 * 申请退款
 * @param {string} orderId - 订单ID
 * @param {Object} data - 退款数据
 */
export function applyRefund(orderId, data) {
  return request({
    url: `/order/${orderId}/refund`,
    method: 'post',
    data,
  })
}
