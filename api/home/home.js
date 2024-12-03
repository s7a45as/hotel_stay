import request from '@/utils/request'

/**
 * 搜索房源
 * @param {Object} params - 搜索参数
 * @returns {Promise<Object>}
 */
export function searchHouses(params) {
  return request({
    url: '/home/search',
    method: 'get',
    params,
  })
}

/**
 * 获取城市房源列表
 * @param {string} cityCode - 城市代码
 * @returns {Promise<{
 *   data: {
 *     list: Array<{
 *       id: number,
 *       name: string,
 *       image: string,
 *       price: number,
 *       rating: number,
 *       tags: string[],
 *       location: string,
 *       cityCode: string
 *     }>,
 *     total: number
 *   }
 * }>}
 */
export function getCityHouses(cityCode) {
  return request({
    url: '/home/houses',
    method: 'get',
    params: { cityCode },
  })
}

/**
 * 获取热门目的地
 * @returns {Promise<Object>}
 */
export function getPopularDestinations() {
  return request({
    url: '/home/popular-destinations',
    method: 'get',
  })
}

/**
 * 获取城市列表
 * @returns {Promise<Object>}
 */
export function getCities() {
  return request({
    url: '/home/cities',
    method: 'get',
  })
}

/**
 * 获取优惠活动列表
 * @returns {Promise<Object>}
 */
export function getPromotions() {
  return request({
    url: '/home/promotions',
    method: 'get'
  })
}
