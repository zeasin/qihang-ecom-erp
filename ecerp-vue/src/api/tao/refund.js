import request from '@/utils/request'

// 查询淘宝退款订单列表
export function listRefund(query) {
  return request({
    url: '/tao/refund/list',
    method: 'get',
    params: query
  })
}

// 查询淘宝退款订单详细
export function getRefund(id) {
  return request({
    url: '/tao/refund/' + id,
    method: 'get'
  })
}

// 新增淘宝退款订单
export function addRefund(data) {
  return request({
    url: '/tao/refund',
    method: 'post',
    data: data
  })
}

// 修改淘宝退款订单
export function updateRefund(data) {
  return request({
    url: '/tao/refund',
    method: 'put',
    data: data
  })
}

// 删除淘宝退款订单
export function delRefund(id) {
  return request({
    url: '/tao/refund/' + id,
    method: 'delete'
  })
}
