import request from '@/utils/request'

// 获取优惠活动列表
export function getPromotionList() {
  return request({
    url: '/merchant/promotions',
    method: 'get',
  })
}

// 创建优惠活动
export function createPromotion(data) {
  return request({
    url: '/merchant/promotions',
    method: 'post',
    data,
  })
}

// 更新优惠活动
export function updatePromotion(id, data) {
  return request({
    url: `/merchant/promotions/${id}`,
    method: 'put',
    data,
  })
}

// 删除优惠活动
export function deletePromotion(id) {
  return request({
    url: `/merchant/promotions/${id}`,
    method: 'delete',
  })
}
