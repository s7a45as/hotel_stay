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
 * 收藏/取消收藏房源
 * @param {string|number} id - 房源ID
 * @returns {Promise<Object>}
 */
export function toggleFavorite(id) {
  return request({
    url: `/house/favorite/${id}`,
    method: 'post',
  })
}

/**
 * 创建预订
 * @param {Object} data - 预订数据
 * @returns {Promise<Object>}
 */
export function createBooking(data) {
  return request({
    url: '/house/booking',
    method: 'post',
    data,
  })
}
