import request from '@/utils/request'

/**
 * 获取房源列表
 * @param {Object} params - 查询参数
 * @returns {Promise<{
 *   list: Array,
 *   total: number
 * }>}
 */
export function getHouseList(params) {
  return request({
    url: '/admin/houses',
    method: 'get',
    params,
  })
}

/**
 * 创建房源
 * @param {Object} data - 房源数据
 */
export function createHouse(data) {
  return request({
    url: '/admin/houses',
    method: 'post',
    data,
  })
}

/**
 * 更新房源
 * @param {number} id - 房源ID
 * @param {Object} data - 房源数据
 */
export function updateHouse(id, data) {
  return request({
    url: `/admin/houses/${id}`,
    method: 'put',
    data,
  })
}

/**
 * 删除房源
 * @param {number} id - 房源ID
 */
export function deleteHouse(id) {
  return request({
    url: `/admin/houses/${id}`,
    method: 'delete',
  })
}

/**
 * 导出房源数据
 * @param {Object} params - 导出参数
 * @returns {Promise<Blob>} - 返回文件流
 */
export function exportHouses(params) {
  return request({
    url: '/admin/houses/export',
    method: 'get',
    params,
    responseType: 'blob' // 指定响应类型为 blob
  })
}
