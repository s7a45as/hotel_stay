import request from '@/utils/request'

/**
 * 获取精选民宿列表
 * @param {Object} params - 查询参数
 * @param {number} [params.page=1] - 页码
 * @param {number} [params.pageSize=10] - 每页数量
 * @param {string} [params.sortBy] - 排序方式(price/rating)
 * @returns {Promise<{
 *   list: Array<{
 *     id: number,
 *     name: string,
 *     coverImage: string,
 *     price: number,
 *     rating: number,
 *     tags: string[],
 *     location: string
 *   }>,
 *   total: number
 * }>}
 */
export function getFeaturedHouses(params) {
  return request({
    url: '/house/featured',
    method: 'get',
    params
  })
}

/**
 * 获取民宿标签列表
 * @returns {Promise<string[]>}
 */
export function getHouseTags() {
  return request({
    url: '/house/tags',
    method: 'get'
  })
}
