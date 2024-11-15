import request from '@/utils/request'

// 获取预订日历数据
export function getBookingCalendar(params) {
  return request({
    url: '/merchant/bookings/calendar',
    method: 'get',
    params,
  })
}

// 更新预订状态
export function updateBookingStatus(id, data) {
  return request({
    url: `/merchant/bookings/${id}/status`,
    method: 'put',
    data,
  })
}
