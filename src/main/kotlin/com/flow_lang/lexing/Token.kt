package com.flow_lang.lexing

class Token(
    val value: String,
    val start: SourcePos,
    val end: SourcePos,
) {
    override fun toString(): String {
        return "$start: $value"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Token

        if (value != other.value) return false
        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }
}