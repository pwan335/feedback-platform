import request from "@/utils/request";

export function addTopic (data) {
    return request({
        url: 'TopicInfo/add',
        method: 'post',
        data
    })
}

export function getManageInfo (params) {
    return request({
        url: 'TopicInfo/manageInfo',
        method: 'get',
        params
    })
}

export function deleteTopic (data) {
    return request({
        url: 'TopicInfo/delete',
        method: 'post',
        data
    })
}