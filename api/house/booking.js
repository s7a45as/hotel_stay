import request from '@/utils/request'

/**
 * 获取我的预订列表
 * @param {Object} params - 查询参数
 * @param {number} [params.page=1] - 页码
 * @param {number} [params.pageSize=10] - 每页数量
 * @param {string} [params.status] - 订单状态(pending/confirmed/completed/cancelled)
 * @returns {Promise<{
 *   list: Array<{
 *     id: number,
 *     orderNo: string,
 *     houseName: string,
 *     checkInDate: string,
 *     checkOutDate: string,
 *     amount: number,
 *     status: string
 *   }>,
 *   total: number
 * }>}
 */
export function getMyBookings(params) {
  return request({
    url: '/house/bookings/my',
    method: 'get',
    params
  })
}

/**
 * 取消预订
 * @param {string|number} bookingId - 预订ID
 * @returns {Promise<void>}
 */
export function cancelBooking(bookingId) {
  return request({
    url: `/house/bookings/${bookingId}/cancel`,
    method: 'post'
  })
}

/**
 * 创建预订
 * @param {Object} data - 预订数据
 * @param {number} data.houseId - 房源ID
 * @param {string} data.checkInTime - 入住时间(格式：YYYY-MM-DDTHH:mm:ss)
 * @param {string} data.checkOutTime - 退房时间(格式：YYYY-MM-DDTHH:mm:ss)
 * @param {string} data.amount - 订单金额(BigDecimal格式的字符串)
 * @param {number} data.guestCount - 入住人数
 * @param {string} data.contactName - 联系人姓名
 * @param {string} data.contactPhone - 联系人电话
 * @param {string} [data.specialRequests] - 特殊要求(可选)
 * @returns {Promise<{
 *   orderId: string,
 *   orderNo: string,
 *   amount: string,
 *   paymentUrl: string
 * }>}
 */
export function createBooking(data) {
  // 确保金额是字符串格式
  const bookingData = {
    ...data,
    amount: data.amount.toString()
  }

  return request({
    url: '/house/bookings/create',
    method: 'post',
    data: bookingData
  })
}
