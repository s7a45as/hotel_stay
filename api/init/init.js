import request from '@/utils/request'

/**
 * 初始化系统配置和用户信息
 * @returns {Promise<{
 *   settings: Object,
 *   userInfo: Object|null,
 *   permissions: string[]
 * }>}
 */
export function initSystem() {
  return request({
    url: '/init',
    method: 'get'
  })
}

/**
 * 获取系统基础配置
 * @returns {Promise<{
 *   siteName: string,
 *   logo: string,
 *   theme: string,
 *   language: string
 * }>}
 */
export function getBaseConfig() {
  return request({
    url: '/init/config',
    method: 'get'
  })
}
