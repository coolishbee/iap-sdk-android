package io.github.coolishbee

enum class ApiErrorCode(val code: Int) {
    NOT_DEFINED(-1),
    CANCEL(7001),
    PURCHASE_ERROR(7003),
    INTERNAL_ERROR(7004);

    companion object {
        fun fromInt(value: Int) = values().first{
            it.code == value
        }
    }
}