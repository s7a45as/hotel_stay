import request from '@/utils/request'

/**
 * 获取房源列表
 * @param {Object} params - 查询参数
 * @param {number} params.page - 页码
 * @param {number} params.pageSize - 每页数量
 * @param {string} [params.categoryId] - 房型ID
 * @param {string} [params.location] - 位置
 * @param {number[]} [params.priceRange] - 价格范围
 * @param {string[]} [params.facilities] - 设施
 * @param {string} [params.sortBy] - 排序方式
 * @returns {Promise<Object>}
 */
export function getHouseList(params) {
  return request({
    url: '/house/list',
    method: 'get',
    params,
  })
}

/**
 * 获取房源类型列表
 * @returns {Promise<Array>}
 */
export function getHouseCategories() {
  return request({
    url: '/house/categories',
    method: 'get',
  })
}
