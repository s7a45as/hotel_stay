import request from '@/utils/request'

/**
 * 获取管理员信息
 * @returns {Promise<{
 *   id: number,
 *   username: string,
 *   nickname: string,
 *   avatar: string,
 *   role: string,
 *   permissions: string[],
 *   menus: Array,
 *   lastLoginTime: string,
 *   lastLoginIp: string
 * }>}
 */
export function getAdminInfo() {
  return request({
    url: '/admin/info',
    method: 'get'
  })
}

/**
 * 更新管理员信息
 * @param {Object} data - 管理员信息
 */
export function updateAdminInfo(data) {
  return request({
    url: '/admin/info',
    method: 'put',
    data
  })
}

/**
 * 获取管理员操作日志
 * @param {Object} params - 查询参数
 */
export function getAdminLogs(params) {
  return request({
    url: '/admin/logs',
    method: 'get',
    params
  })
}

/**
 * 获取管理员菜单
 */
export function getAdminMenus() {
  return request({
    url: '/admin/menus',
    method: 'get'
  })
}
