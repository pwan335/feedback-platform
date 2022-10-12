import request from "@/utils/request";

export function userLogin(params) {
    return request({
        url: 'users/login',
        method: 'get',
        params
    })
}

// get the information of users
export function getUserInfo() {
    return request({
        url: `/users/profile`,
        method: 'get',
    })
}

export function getUserData() {
    return request({
        url: `/topics/user/data`,
        method: 'get',
    })
}

export function getTopicByCollected() {
    return request({
        url: `users/data/collect`,
        method: 'get',
    })
}

export function getTopicByLiked() {
    return request({
        url: `users/data/like`,
        method: 'get',
    })
}

export function getTopicByComment() {
    return request({
        url: `users/data/comment`,
        method: 'get',
    })
}

export function userRegister(data) {
    return request({
        url: `users/register`,
        method: 'post',
        data
    })
}


// pm login
export function pmLogin(params) {
    return request({
        url: 'pm/login',
        method: 'get',
        params
    })
}

export function pmRegister(data) {
    return request({
        url: `pm/register`,
        method: 'post',
        data
    })
}

export function getPmInfo() {
    return request({
        url: `/pm/profile`,
        method: 'get',
    })
}

export function createTopic(data) {
    return request({
        url: 'pm/topic',
        method: 'post',
        data
    })
}

export function deleteTopic(data) {
    return request({
        url: `pm/topic/${data.id}`,
        method: 'delete',
    })
}

export function getTopicDataByPm() {
    return request({
        url: `pm/topic/data`,
        method: 'get',
    })
}