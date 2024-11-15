import request from '@/utils/request'

// 获取设置
export function getSettings() {
  return request({
    url: '/merchant/settings',
    method: 'get',
  })
}

// 更新设置
export function updateSettings(data) {
  return request({
    url: '/merchant/settings',
    method: 'put',
    data,
  })
}
