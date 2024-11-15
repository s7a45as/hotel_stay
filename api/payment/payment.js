import request from '@/utils/request'

/**
 * 获取支付二维码
 * @param {Object} data - 支付数据
 * @param {string} data.orderId - 订单ID
 * @param {number} data.amount - 支付金额
 * @param {string} data.method - 支付方式
 * @returns {Promise<{qrcode: string}>}
 */
export function generatePayQRCode(data) {
  return request({
    url: '/payment/qrcode',
    method: 'post',
    data,
  })
}

/**
 * 查询支付状态
 * @param {string} orderId - 订单ID
 * @returns {Promise<{status: string}>}
 */
export function getPaymentStatus(orderId) {
  return request({
    url: `/payment/status/${orderId}`,
    method: 'get',
  })
}
