import request from '@/utils/request'

/**
 * 获取房源详情
 * @param {string|number} id - 房源ID
 * @returns {Promise<Object>}
 */
export function getHouseDetail(id) {
  return request({
    url: `/house/detail/${id}`,
    method: 'get',
  })
}

/**
 * 创建订单
 * @param {Object} data - 订单数据
 * @returns {Promise<{
 *   orderId: string,
 *   amount: number
 * }>}
 */
export function createOrder(data) {
  return request({
    url: '/order/create',
    method: 'post',
    data,
  })
}
