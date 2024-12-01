
import request from '@/utils/request'
/**
 * 获取城市列表
 * @returns {Promise<Array<{
 *   value: string,
 *   label: string
 * }>>}
 */
export function getCityList() {
  return request({
    url: '/common/cities',
    method: 'get'
  })
}

/**
 * 获取指定城市的地区列表
 * @param {string} cityCode - 城市编码或城市名称
 * @returns {Promise<Array<{
 *   value: string,
 *   label: string
 * }>>}
 */
export function getDistrictList(cityCode) {
  return request({
    url: '/common/districts',
    method: 'get',
    params: {
      cityCode:cityCode
    }
  })
}

