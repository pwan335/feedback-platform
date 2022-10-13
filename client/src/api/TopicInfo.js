import request from "@/utils/request";

// search topic by name
export function getTopicByName (data) {
    return request({
        url: `/topics/name`,
        method: 'post',
        data
    })
}

export function getLatestTopic (params) {
    return request({
        url: `/topics/latest`,
        method: 'get',
        params
    })
}

export function getHotTopic (params) {
    return request({
        url: `/topics/hot`,
        method: 'get',
        params
    })
}

export function getTopicByCollected () {
    return request({
        url: `/users/data/collect`,
        method: 'get'
    })
}

export function getTopicByLike () {
    return request({
        url: `/users/data/like`,
        method: 'get'
    })
}

export function getTopicByComment () {
    return request({
        url: `/users/data/comment`,
        method: 'get'
    })
}

export function collectTopic (data) {
    return request({
        url: `/topics/collect`,
        method: 'post',
        data
    })
}

export function deleteCollect (data) {
    return request({
        url: `/topics/collect`,
        method: 'delete',
        data
    })
}

export function likeTopic (data) {
    return request({
        url: `/topics/like`,
        method: 'post',
        data
    })
}

export function deleteLike (data) {
    return request({
        url: `/topics/like`,
        method: 'delete',
        data
    })
}

// get comments
export function getComments (params) {
    return request({
        url: `/comments/topicId`,
        method: 'get',
        params
    })
}

// post comment
export function postComments (data) {
    return request({
        url: `/comments/comment`,
        method: 'post',
        data
    })
}

// comment the comment of others
export function postTreeComments (data) {
    return request({
        url: `/comments/reply`,
        method: 'post',
        data
    })
}
