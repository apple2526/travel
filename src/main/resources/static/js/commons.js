$.validator.addMethod("phoneFmt", function (value, element, param) {
    // console.log(value + "  " + element + "   " + param);
    if (param) {
        return new RegExp("^1[3456789]\\d{9}$").test(value);
    }
}, "手机号不合法！");