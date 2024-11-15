import request from '@/utils/request'

/**
 * 获取用户列表
 * @param {Object} params - 查询参数
 * @param {number} [params.currentPage=1] - 当前页码
 * @param {number} [params.pageSize=10] - 每页数量
 * @param {string} [params.username] - 用户名
 * @param {string} [params.phone] - 手机号
 * @param {number} [params.status] - 状态
 * @returns {Promise<{
 *   list: Array,
 *   total: number,
 *   currentPage: number,
 *   pageSize: number
 * }>}
 */
export function getUserList(params) {
  return request({
    url: '/admin/users/list',
    method: 'get',
    params
  })
}

/**
 * 更新用户信息
 * @param {number} id - 用户ID
 * @param {Object} data - 用户数据
 * @param {string} [data.username] - 用户名
 * @param {string} [data.nickname] - 昵称
 * @param {string} [data.email] - 邮箱
 * @param {string} [data.phone] - 手机号
 * @param {string} [data.department] - 部门
 * @param {string} [data.position] - 职位
 */
export function updateUser(id, data) {
  return request({
    url: `/admin/users/${id}/update`,
    method: 'put',
    data
  })
}

/**
 * 修改用户密码
 * @param {number} id - 用户ID
 * @param {Object} data - 密码数据
 * @param {string} data.newPassword - 新密码
 * @param {string} data.confirmPassword - 确认密码
 */
export function updateUserPassword(id, data) {
  return request({
    url: `/admin/users/${id}/password`,
    method: 'put',
    data
  })
}

/**
 * 更新用户状态
 * @param {number} id - 用户ID
 * @param {number} status - 状态值 (0: 禁用, 1: 正常, 2: 待审核)
 */
export function updateUserStatus(id, status) {
  return request({
    url: `/admin/users/${id}/status`,
    method: 'put',
    data: status
  })
}

/**
 * 审核管理员
 * @param {number} id - 管理员ID
 * @param {Object} data - 审核数据
 * @param {boolean} data.approved - 是否通过
 * @param {string} [data.remark] - 审核备注
 */
export function auditAdmin(id, data) {
  return request({
    url: `/admin/users/${id}/audit`,
    method: 'put',
    data
  })
}

/**
 * 获取待审核管理员列表
 * @returns {Promise<Array>} 待审核管理员列表
 */
export function getPendingAdmins() {
  return request({
    url: '/admin/users/pending',
    method: 'get'
  })
}

/**
 * 删除用户
 * @param {number} id - 用户ID
 */
export function deleteUser(id) {
  return request({
    url: `/admin/users/${id}`,
    method: 'delete'
  })
}
