import request from '@/utils/request'

/**
 * 获取图片列表
 * @param {Object} params - 查询参数
 * @param {number} [params.page=1] - 页码
 * @param {number} [params.pageSize=20] - 每页数量
 * @param {string} [params.category] - 图片分类
 * @returns {Promise<{
 *   list: Array<{
 *     id: number,
 *     url: string,
 *     name: string,
 *     description: string,
 *     category: string
 *   }>,
 *   total: number
 * }>}
 */
export function getGalleryImages(params) {
  return request({
    url: '/house/gallery',
    method: 'get',
    params
  })
}

/**
 * 获取图片分类
 * @returns {Promise<Array<{
 *   id: string,
 *   name: string
 * }>>}
 */
export function getImageCategories() {
  return request({
    url: '/house/gallery/categories',
    method: 'get'
  })
}
