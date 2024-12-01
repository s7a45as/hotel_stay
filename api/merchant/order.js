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

/**
 * 获取订单详情
 * @param {string} orderId - 订单ID
 * @returns {Promise<{
 *   id: string,
 *   orderNo: string,
 *   status: 'PENDING' | 'PAID' | 'CANCELLED' | 'COMPLETED' | 'REFUNDING' | 'REFUNDED',
 *   createTime: string,
 *   payTime: string,
 *   totalAmount: number,
 *   paymentMethod: string,
 *   // 房源信息
 *   house: {
 *     id: number,
 *     name: string,
 *     image: string,
 *     location: string,
 *     price: number
 *   },
 *   // 入住信息
 *   checkIn: string,
 *   checkOut: string,
 *   nights: number,
 *   guestCount: number,
 *   // 客户信息
 *   guest: {
 *     id: number,
 *     name: string,
 *     phone: string,
 *     email: string,
 *     avatar: string
 *   },
 *   // 退款信息（如果有）
 *   refund?: {
 *     id: number,
 *     amount: number,
 *     reason: string,
 *     status: string,
 *     createTime: string,
 *     description?: string,
 *     images?: string[]
 *   },
 *   remark?: string,
 *   updateTime: string
 * }>}
 */
export function getMerchantOrderDetail(orderId) {
  return request({
    url: `/merchant/orders/${orderId}/details`,
    method: 'get'
  })
}
