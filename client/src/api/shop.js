import request from "@/utils/request";

export function getBestSellers(data) {
    return request({
        url: 'phone/findBestSellers',
        method: 'get',
        data
    })
}

export function findLowStockPhones(data) {
    return request({
        url: 'phone/findLowStockPhones',
        method: 'get',
        data
    })
}

export function findByTitle(params) {
    return request({
        url: 'phone/findByTitle',
        method: 'get',
        params
    })
}

export function decreaseStock(data) {
    return request({
        url: 'phone/decreaseStock',
        method: 'post',
        data
    })
}

