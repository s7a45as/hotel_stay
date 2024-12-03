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
    url: '/house/comment/list',
    method: 'get',
    params: {
      houseId: params.houseId,
      page: params.page || 1,
      pageSize: params.pageSize || 10,
      rating: params.rating
    }
  })
}

/**
 * 添加评论
 * @param {Object} data
 * @param {number} data.houseId - 房源ID
 * @param {number} data.rating - 评分(1-5)
 * @param {string} data.content - 评论内容
 * @param {string[]} [data.images] - 图片列表
 */
export function addComment(data) {
  const { houseId, ...commentData } = data
  return request({
    url: `/house/comment/${houseId}`,
    method: 'post',
    data: {
      ...commentData,
      user_id: null, // 由后端通过 @AuthenticationPrincipal 注入
      house_id: houseId
    }
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
    url: `/house/comment/rating-stats/${houseId}`,
    method: 'get'
  })
}

/**
 * 删除评论
 * @param {number} commentId - 评论ID
 */
export function deleteComment(commentId) {
  return request({
    url: `/house/comment/${commentId}`,
    method: 'delete'
  })
}

/**
 * 修改评论
 * @param {number} comment_id - 评论ID
 * @param {Object} data
 * @param {number} data.rating - 评分(1-5)
 * @param {string} data.content - 评论内容
 * @param {string[]} [data.images] - 图片列表
 */
export function updateComment(comment_id, data) {
  return request({
    url: `/house/comment/${comment_id}`,
    method: 'put',
    data: {
      ...data,
      id: comment_id // 确保包含评论ID
    }
  })
}

// 上传评论图片
export function uploadCommentImage(data) {
  return request({
    url: '/house/comment/upload-image',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data
  })
}
