import request from '@/utils/request'

// 查询入库单列表
export function listWmsStockInEntry(query) {
  return request({
    url: '/wms/WmsStockInEntry/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getWmsStockInEntry(id) {
  return request({
    url: '/wms/WmsStockInEntry/' + id,
    method: 'get'
  })
}

// 新增入库单
export function addWmsStockInEntry(data) {
  return request({
    url: '/wms/WmsStockInEntry',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateWmsStockInEntry(data) {
  return request({
    url: '/wms/WmsStockInEntry',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delWmsStockInEntry(id) {
  return request({
    url: '/wms/WmsStockInEntry/' + id,
    method: 'delete'
  })
}
