import request from '@/utils/request'

/**
 * 获取用户菜单配置
 * @returns {Promise<Array>}
 */
export function getUserMenus() {
  return request({
    url: '/user/menus',
    method: 'get',
  })
}

/**
 * 获取用户信息
 * @returns {Promise<{
 *   id: number,
 *   username: string,
 *   nickname: string,
 *   avatar: string,
 *   role: string
 * }>}
 */
export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get',
  })
}
