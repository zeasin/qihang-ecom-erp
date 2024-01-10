import request from '@/utils/request'

// 查询出库单列表
export function listStockOutEntry(query) {
  return request({
    url: '/wms/stockOutEntry/list',
    method: 'get',
    params: query
  })
}

// 查询出库单详细
export function getStockOutEntry(id) {
  return request({
    url: '/wms/stockOutEntry/' + id,
    method: 'get'
  })
}

// 新增出库单
export function addStockOutEntry(data) {
  return request({
    url: '/wms/stockOutEntry',
    method: 'post',
    data: data
  })
}

// 修改出库单
export function updateStockOutEntry(data) {
  return request({
    url: '/wms/stockOutEntry',
    method: 'put',
    data: data
  })
}

// 删除出库单
export function delStockOutEntry(id) {
  return request({
    url: '/wms/stockOutEntry/' + id,
    method: 'delete'
  })
}
