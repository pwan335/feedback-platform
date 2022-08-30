import request from "@/utils/request";

export function signInAction(data) {
    return request({
        url: 'users/login',
        method: 'post',
        data
    })
}

export function signUpAction(data) {
    return request({
        url: 'users/signup',
        method: 'post',
        data
    })
}

export function addReview(data) {
    return request({
        url: 'phone/addReview',
        method: 'post',
        data
    })
}

export function updateProfile(data) {
    return request({
        url: 'users/profile/update',
        method: 'post',
        data
    })
}

export function changePasswprd(data) {

    return request({
        url: 'users/profile/changepassword',
        method: 'post',
        data
    })
}

export function findAssociatedPhone(data) {

    return request({
        url: 'phone/findAssociatedPhone',
        method: 'get',
        data
    })
}

export function addNewPhone(data) {
    return request({
        url: 'phone/addNewPhone',
        method: 'post',
        data
    })
}

export function deletePhone(data) {
    return request({
        url: 'phone/deletePhone',
        method: 'post',
        data
    })
}

export function setEnabled(data) {
    return request({
        url: 'phone/setEnabled',
        method: 'post',
        data
    })
}

export function setDisabled(data) {
    return request({
        url: 'phone/setDisabled',
        method: 'post',
        data
    })
}

export function forgetPassword(data) {
    return request({
        url: 'users/forgetpassword',
        method: 'post',
        data
    })
}

export function resetPassword(data) {
    return request({
        url: 'users/resetpassword',
        method: 'post',
        data
    })
}






