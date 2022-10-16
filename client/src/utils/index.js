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

export function getImageHost() {
    return 'http://localhost:8080'
}

export function utc2beijing(time) {
    let temp = new Date(time)
    let year = temp.getFullYear()
    let month = temp.getMonth() + 1
    let day = temp.getDate()
    let hour = temp.getHours()
    let min = temp.getMinutes()
    let second = temp.getSeconds()
    if(month.toString().length<2) {
        month = '0' + month
    }
    if(day.toString().length<2) {
        day = '0' + day
    }
    if(hour.toString().length<2) {
        hour = '0' + hour
    }
    if(min.toString().length<2) {
        min = '0' + min
    }
    if(second.toString().length<2) {
        second = '0' + second
    }
    return `${year}-${month}-${day} ${hour}:${min}:${second}`
}