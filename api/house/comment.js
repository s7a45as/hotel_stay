import request from '@/utils/request'

/**
 * 获取房源评论列表
 * @param {Object} params
 * @param {number} params.houseId - 房源ID
 * @param {number} [params.page=1] - 页码
 * @param {number} [params.pageSize=10] - 每页数量
 * @param {number} [params.rating] - 评分筛选
 */
export function getComments(params) {
  return request({
    url: '/house/comments',
    method: 'get',
    params
  })
}

/**
 * 添加评论
 * @param {Object} data
 * @param {number} data.houseId - 房源ID
 * @param {number} data.orderId - 订单ID 
 * @param {number} data.rating - 评分(1-5)
 * @param {string} data.content - 评论内容
 * @param {string[]} [data.images] - 图片列表
 */
export function addComment(data) {
  return request({
    url: '/house/comment',
    method: 'post',
    data
  })
}

/**
 * 点赞/取消点赞评论
 * @param {number} commentId - 评论ID
 */
export function toggleCommentLike(commentId) {
  return request({
    url: `/house/comment/${commentId}/like`,
    method: 'post'
  })
}

/**
 * 举报评论
 * @param {Object} data
 * @param {number} data.commentId - 评论ID
 * @param {string} data.reason - 举报原因
 * @param {string} [data.description] - 详细说明
 */
export function reportComment(data) {
  return request({
    url: '/house/comment/report',
    method: 'post',
    data
  })
}

/**
 * 获取评分统计
 * @param {number} houseId - 房源ID
 */
export function getRatingStats(houseId) {
  return request({
    url: `/house/rating-stats/${houseId}`,
    method: 'get'
  })
}
