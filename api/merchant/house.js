import request from '@/utils/request'

/**
 * 获取商家房源列表
 * @param {Object} params - 查询参数
 * @returns {Promise<{
 *   list: Array,
 *   total: number
 * }>}
 */
export function getMerchantHouseList(params) {
  return request({
    url: '/merchant/houses',
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
    url: '/merchant/houses',
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
    url: `/merchant/houses/${id}`,
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
    url: `/merchant/houses/${id}`,
    method: 'delete',
  })
}

/**
 * 上传房源图片
 * @param {FormData} data - 图片数据
 */
export function uploadHouseImage(data) {
  return request({
    url: '/merchant/houses/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data',
    },
    data,
  })
}
