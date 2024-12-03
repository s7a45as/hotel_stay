import request from '@/utils/request'

/**
 * 获取系统优惠活动
 * @returns {Promise<Array>} 系统优惠活动列表
 */
export function getSystemPromotions() {
  return request({
    url: '/house/system/promotions',
    method: 'get'
  })
}

/**
 * 获取商家优惠活动
 * @param {number} merchantId - 商家ID
 * @returns {Promise<Array>} 商家优惠活动列表
 */
export function getMerchantPromotions(merchantId) {
  return request({
    url: `/house/merchant/${merchantId}/promotions`,
    method: 'get'
  })
}

/**
 * 计算最终价格
 * @param {Object} params
 * @param {number} params.originalPrice - 原始价格
 * @param {number} params.merchantId - 商家ID
 * @param {string} params.checkInTime - 入住时间
 * @param {string} params.checkOutTime - 退房时间
 * @returns {Promise<{
 *   originalPrice: number,
 *   finalPrice: number,
 *   discount: number,
 *   appliedPromotions: Array<{
 *     id: number,
 *     name: string,
 *     type: string,
 *     discount: number
 *   }>
 * }>}
 */
export function calculateFinalPrice(params) {
  return request({
    url: '/house/price/calculate',
    method: 'post',
    data: params
  })
}

/**
 * 计算价格和可用优惠
 * @param {Object} params - 计算参数
 * @returns {Object} 计算结果
 */
export async function calculatePrice(params) {
  try {
    // 获取系统优惠活动
    const systemPromotions = await getSystemPromotions()

    // 获取商家优惠活动
    const merchantPromotions = await getMerchantPromotions(params.merchantId)

    // 合并所有优惠活动并按优惠金额排序
    const allPromotions = [...systemPromotions, ...merchantPromotions].sort((a, b) => {
      return calculatePromotionDiscount(b, params.originalPrice) -
             calculatePromotionDiscount(a, params.originalPrice)
    })

    // 计算最终价格和应用的优惠
    const result = await calculateFinalPrice({
      ...params,
      promotions: allPromotions
    })

    return {
      ...result,
      availablePromotions: allPromotions
    }
  } catch (error) {
    console.error('计算价格失败:', error)
    throw error
  }
}

/**
 * 计算单个优惠活动的优惠金额
 * @param {Object} promotion - 优惠活动
 * @param {number} originalPrice - 原始价格
 * @returns {number} 优惠金额
 */
function calculatePromotionDiscount(promotion, originalPrice) {
  switch (promotion.type) {
    case 'FULL_REDUCTION':
      return originalPrice >= promotion.threshold ? promotion.discount : 0

    case 'DISCOUNT':
      return originalPrice * (1 - promotion.discount / 10)

    case 'COMPENSATION':
      return promotion.discount

    default:
      return 0
  }
}
