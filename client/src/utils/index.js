// Password must contain uppercase letters, lowercase letters, and numbers and be 6-12 digits in length
export function validPassword(password) {
    const regx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{6,12}$/g
    return regx.test(password)
}

export function validNum(num) {
    const regx = /^\d+(?=\.{0,1}\d+$|$)/g
    return regx.test(num)
}

export function validStock(stock) {
    const regx = /^\+?[1-9][0-9]*$/g
    return regx.test(stock)
}