import request from '@/utils/request'

/**
 * 获取商家房源列表
 * @param {Object} params - 查询参数
 * @param {number} params.currentPage - 当前页码
 * @param {number} params.pageSize - 每页数量
 * @param {string} [params.name] - 房源名称
 * @param {string} [params.type] - 房源类型
 * @param {number} [params.status] - 房源状态
 * @returns {Promise<{
 *   records: Array,
 *   total: number,
 *   size: number,
 *   current: number
 * }>}
 */
export function getMerchantHouseList(params) {
  return request({
    url: '/merchant/houses',
    method: 'get',
    params: {
      currentPage: params.current || 1,
      pageSize: params.size || 10,
      name: params.name || undefined,
      type: params.type || undefined,
      status: params.status || undefined
    }
  })
}

/**
 * 创建房源
 * @param {Object} data - 房源数据
 * @param {string} data.title - 房源标题
 * @param {string} data.name - 房源名称
 * @param {string} data.type - 房源类型
 * @param {string} data.address - 地址
 * @param {string} data.city - 城市 (必需)
 * @param {string} data.district - 区域 (必需)
 * @param {string} data.location - 具体位置
 * @param {number} data.price - 价格
 * @param {number} data.maxGuests - 最大入住人数
 * @param {string} data.description - 描述
 * @param {string} data.category - 分类
 * @param {number} data.status - 状态
 * @param {Array<string>} data.facilities - 设施
 * @param {Array<string>} data.images - 图片列表
 * @param {Array<string>} data.features - 特色标签
 * @param {string} data.image - 主图
 * @returns {Promise<void>}
 */
export function createHouse(data) {
  return request({
    url: '/merchant/houses',
    method: 'post',
    data
  })
}

/**
 * 更新房源
 * @param {number|string} id - 房源ID
 * @param {Object} data - 房源更新数据
 * @returns {Promise<void>}
 */
export function updateHouse(id, data) {
  return request({
    url: `/merchant/houses/${id}`,
    method: 'put',
    data
  })
}

/**
 * 删除房源
 * @param {number|string} id - 房源ID
 * @returns {Promise<void>}
 */
export function deleteHouse(id) {
  return request({
    url: `/merchant/houses/${id}`,
    method: 'delete'
  })
}

/**
 * 上传房源图片
 * @param {FormData} formData - 包含文件的表单数据
 * @returns {Promise<{
 *   data: {
 *     url: string
 *   }
 * }>}
 */
export function uploadHouseImage(formData) {
  return request({
    url: '/merchant/houses/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: formData
  })
}


