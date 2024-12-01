import request from '@/utils/request'

/**
 * 获取消息列表
 * @param {Object} params - 查询参数
 * @param {string} [params.type] - 消息类型
 * @param {string} [params.keyword] - 搜索关键词
 * @param {string} [params.startDate] - 开始日期
 * @param {string} [params.endDate] - 结束日期
 * @param {string} [params.readStatus] - 阅读状态
 * @param {number} [params.currentPage] - 当前页码
 * @param {number} [params.pageSize] - 每页条数
 * @returns {Promise<{
 *   list: Array<{
 *     id: number,
 *     title: string,
 *     content: string,
 *     type: string,
 *     isRead: boolean,
 *     createTime: string,
 *     sender: string,
 *     priority: string
 *   }>,
 *   total: number,
 *   unreadCount: number
 * }>}
 */
export function getMessageList(params) {
  return request({
    url: '/merchant/messages',
    method: 'get',
    params
  })
}

/**
 * 获取消息统计数据
 * @returns {Promise<{
 *   total: number,
 *   unread: number,
 *   system: number,
 *   order: number,
 *   comment: number,
 *   security: number
 * }>}
 */
export function getMessageStats() {
  return request({
    url: '/merchant/messages/stats',
    method: 'get'
  })
}

/**
 * 标记消息为已读
 * @param {number|number[]} messageIds - 消息ID或ID数组
 */
export function markMessageRead(messageIds) {
  return request({
    url: '/merchant/messages/read',
    method: 'post',
    data: {
      messageIds: Array.isArray(messageIds) ? messageIds : [messageIds]
    }
  })
}

/**
 * 标记所有消息为已读
 */
export function markAllMessagesRead() {
  return request({
    url: '/merchant/messages/read-all',
    method: 'post'
  })
}

/**
 * 删除消息
 * @param {number|number[]} messageIds - 消息ID或ID数组
 */
export function deleteMessages(messageIds) {
  return request({
    url: '/merchant/messages',
    method: 'delete',
    data: {
      messageIds: Array.isArray(messageIds) ? messageIds : [messageIds]
    }
  })
}

/**
 * 清空所有消息
 */
export function clearAllMessages() {
  return request({
    url: '/merchant/messages/clear',
    method: 'delete'
  })
}
