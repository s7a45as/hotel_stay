import request from '@/utils/request'

// 上传聊天图片
export function uploadChatImage(data) {
  return request({
    url: '/chat/upload',
    method: 'post',
    data
  })
}

// 获取未读消息数
export function getUnreadCount() {
  return request({
    url: '/chat/unread',
    method: 'get'
  })
}
