import request from '@/utils/request'

/**
 * 获取民宿位置列表
 * @param {Object} params - 查询参数
 * @param {number} [params.cityCode] - 城市代码
 * @param {number[]} [params.bounds] - 地图边界范围
 * @returns {Promise<Array<{
 *   id: number,
 *   name: string,
 *   address: string,
 *   lat: number,
 *   lng: number,
 *   price: number,
 *   rating: number,
 *   distance: number
 * }>>}
 */
export function getHouseLocations(params) {
  return request({
    url: '/house/locations',
    method: 'get',
    params
  })
}

/**
 * 获取城市列表
 * @returns {Promise<Array<{
 *   code: string,
 *   name: string,
 *   lat: number,
 *   lng: number
 * }>>}
 */
export function getCityList() {
  return request({
    url: '/house/cities',
    method: 'get'
  })
}

/**
 * 获取地点建议
 * @param {string} keyword - 搜索关键词
 * @returns {Promise<Array<{
 *   name: string,
 *   address: string,
 *   lat: number,
 *   lng: number
 * }>>}
 */
export function getLocationSuggestions(keyword) {
  return request({
    url: '/house/location/suggest',
    method: 'get',
    params: { keyword }
  })
}
