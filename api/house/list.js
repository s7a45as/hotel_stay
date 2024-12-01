import request from '@/utils/request'

/**
 * 获取房源列表
 * @param {Object} params - 查询参数
 * @param {number} params.page - 页码
 * @param {number} params.pageSize - 每页数量
 * @param {string} [params.city] - 城市
 * @param {string} [params.district] - 地区
 * @param {number} [params.guestCount] - 入住人数
 * @param {string} [params.checkInDate] - 入住日期
 * @param {string} [params.checkOutDate] - 退房日期
 * @param {number} [params.minPrice] - 最低价格
 * @param {number} [params.maxPrice] - 最高价格
 * @param {string} [params.roomTypes] - 房型，多个用逗号分隔
 * @param {string} [params.amenities] - 设施，多个用逗号分隔
 * @param {string} [params.tags] - 特色标签，多个用逗号分隔
 * @returns {Promise<{
 *   list: Array<{
 *     id: number,
 *     title: string,
 *     coverImage: string,
 *     price: number,
 *     city: string,
 *     district: string,
 *     type: string,
 *     rating: number,
 *     reviewCount: number,
 *     tags: Array<{
 *       type: string,
 *       text: string
 *     }>
 *   }>,
 *   total: number
 * }>}
 */
export function getHouseList(params) {
  const processedParams = { ...params }

  if (!processedParams.city || !processedParams.district) {
    delete processedParams.district
  }

  Object.keys(processedParams).forEach(key => {
    if (processedParams[key] === undefined ||
        processedParams[key] === null ||
        processedParams[key] === '') {
      delete processedParams[key]
    }
  })

  return request({
    url: '/house/list',
    method: 'get',
    params: processedParams,
  })
}

/**
 * 获取房源类型列表
 * @returns {Promise<Array<{
 *   id: string,
 *   name: string,
 *   icon: string
 * }>>}
 */
export function getHouseCategories() {
  return request({
    url: '/house/categories',
    method: 'get',
  })
}
