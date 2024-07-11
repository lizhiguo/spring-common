package org.apache.commons.helper

import cn.hutool.core.util.NumberUtil
import cn.hutool.core.util.StrUtil

object PhoneUtils {
    fun fmtMobile(str: String): String {
        if (StrUtil.isBlank(str)) {
            return ""
        }
        var tmpStr = str
        if (tmpStr.startsWith("11645")) {
            tmpStr = tmpStr.substring(6, tmpStr.length)
        }
        if (tmpStr.startsWith("+86")) {
            tmpStr = tmpStr.substring(3, tmpStr.length)
            if ((tmpStr.startsWith("1") || tmpStr.startsWith("2")) && tmpStr.length == 10) {
                return "0$tmpStr"
            }
            if (!tmpStr.startsWith("400") && !tmpStr.startsWith("1") && tmpStr.length > 8 && tmpStr.length < 12) {
                tmpStr = "0$tmpStr"
            }
        }
        if (str.startsWith("86")) {
            tmpStr = str.substring(2, str.length)
            if ((tmpStr.startsWith("1") || tmpStr.startsWith("2")) && tmpStr.length == 10) {
                return "0$tmpStr"
            }
            if (!tmpStr.startsWith("400") && !tmpStr.startsWith("1") && tmpStr.length > 8 && tmpStr.length < 12) {
                tmpStr = "0$tmpStr"
            }
        }
        if (str.startsWith("0086")) {
            tmpStr = str.substring(4, str.length)
            if ((tmpStr.startsWith("1") || tmpStr.startsWith("2")) && tmpStr.length == 10) {
                return "0$tmpStr"
            }
            if (!tmpStr.startsWith("400") && !tmpStr.startsWith("1") && tmpStr.length > 8 && tmpStr.length < 12) {
                tmpStr = "0$tmpStr"
            }
        }

        if (!NumberUtil.isNumber(tmpStr) && tmpStr.length >= 11) {
            tmpStr = tmpStr.substring(tmpStr.length - 11, tmpStr.length)
            if (tmpStr.startsWith("6")) {
                tmpStr = "0" + tmpStr.substring(1, tmpStr.length)
            } else if (!tmpStr.startsWith("1") && !tmpStr.startsWith("0")) {
                tmpStr = "0$tmpStr"
            }
        } else if (NumberUtil.isNumber(tmpStr) && tmpStr.length >= 11) {
            tmpStr = tmpStr.substring(tmpStr.length - 11, tmpStr.length)
            if (tmpStr.startsWith("6")) {
                tmpStr = "0" + tmpStr.substring(1, tmpStr.length)
            } else if (!tmpStr.startsWith("1") && !tmpStr.startsWith("0")) {
                tmpStr = "0$tmpStr"
            }
        }

        return tmpStr
    }


    /**
     * 计算漫游
     *
     * @param mobile
     * @return
     */
    fun calRoamFlag(mobile: String?): Int {
        var roamFlag = 1
        if (StrUtil.startWith(mobile, "+")) {
            roamFlag = if (StrUtil.startWith(mobile, "+86")) {
                0
            } else {
                1
            }
        } else if (StrUtil.startWith(mobile, "00")) {
            roamFlag = if (StrUtil.startWith(mobile, "0086")) {
                0
            } else {
                1
            }
        }
        return roamFlag
    }

    /**
     * 手机号处理
     *
     * @param mobile
     * @return
     */
    fun calNormPhone(mobile: String): String {
        var result = mobile

        val roamFlag = calRoamFlag(mobile)

        // 国际
        if (roamFlag == 1) {
            result = mobile
            return result
        }

        // 大于11位
        if (StrUtil.length(mobile) > 11) {
            result = StrUtil.subSufByLength(mobile, 11)
            if (StrUtil.startWith(result, "0") || StrUtil.startWith(result, "1")) {
                // no to do
            } else if (StrUtil.startWith(result, "6")) {
                result = "0" + result.substring(1)
            } else {
                result = "0$result"
            }
            return result
        }

        // 等于11位
        if (StrUtil.length(mobile) == 11) {
            result = mobile
            if (StrUtil.startWith(result, "0") || StrUtil.startWith(result, "1")) {
                // no to do
            } else if (StrUtil.startWith(result, "6")) {
                result = "0" + result.substring(1)
            } else {
                result = "0$result"
            }
            return result
        }

        // 大于9位，小于11位
        if (StrUtil.length(mobile) < 11 && StrUtil.length(mobile) >= 9) {
            result =
                if (StrUtil.startWith(result, "1") || StrUtil.startWith(result, "2")) {
                    "0$mobile"
                } else {
                    mobile
                }
            return result
        }
        result = mobile
        return result
    }
}