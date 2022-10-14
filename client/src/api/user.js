import request from "@/utils/request";

export function userLogin(data) {
    return request({
        url: 'users/login',
        method: 'post',
        data
    })
}

// get the information of users
export function getUserInfo() {
    return request({
        url: `/users/profile`,
        method: 'get',
        headers: {'Content-Type': 'application/json ;charset=utf-8'}
    })
}

export function getUserData() {
    return request({
        url: `/topics/user/data`,
        method: 'get',
    })
}

export function updateUserInfo(data) {
    return request({
        url: `/users/profile`,
        method: 'put',
        data
    })
}

export function updatePassword(data) {
    return request({
        url: `/users/password/change`,
        method: 'put',
        data
    })
}

export function verifyOldPw(data) {
    return request({
        url: `/users/password/verify`,
        method: 'post',
        data
    })
}

export function updateAvatar(data) {
    return request({
        url: `/users/image/save`,
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function getTopicByCollected() {
    return request({
        url: `users/data/collect`,
        method: 'get',
    })
}

export function getTopicByLike() {
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

export function resetPassword(data) {
    return request({
        url: `/users/password/forget`,
        method: 'post',
        data
    })
}








// pm login
export function pmLogin(data) {
    return request({
        url: 'pm/login',
        method: 'post',
        data
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

export function updatePmInfo(data) {
    return request({
        url: `/pm/profile`,
        method: 'put',
        data
    })
}

export function updatePmAvatar(data) {
    return request({
        url: `/pm/image/save`,
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function updatePmPassword(data) {
    return request({
        url: `/pm/password/change`,
        method: 'put',
        data
    })
}

export function resetPmPassword(data) {
    return request({
        url: `/pm/password/forget`,
        method: 'post',
        data
    })
}

export function createTopic(data) {
    return request({
        url: 'pm/img-topic',
        method: 'post',
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
export function createTextTopic(data) {
    return request({
        url: 'pm/topic',
        method: 'post',
        data,
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