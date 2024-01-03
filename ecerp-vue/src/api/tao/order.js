import request from '@/utils/request'

// 查询淘宝订单列表
export function listOrder(query) {
  return request({
    url: '/tao/order/list',
    method: 'get',
    params: query
  })
}

// 查询淘宝订单详细
export function getOrder(id) {
  return request({
    url: '/tao/order/' + id,
    method: 'get'
  })
}

// 新增淘宝订单
export function addOrder(data) {
  return request({
    url: '/tao/order',
    method: 'post',
    data: data
  })
}

// 修改淘宝订单
export function updateOrder(data) {
  return request({
    url: '/tao/order',
    method: 'put',
    data: data
  })
}

// 删除淘宝订单
export function delOrder(id) {
  return request({
    url: '/tao/order/' + id,
    method: 'delete'
  })
}
