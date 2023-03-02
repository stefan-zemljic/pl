package com.flow_lang.lexing

import java.nio.file.Path
import kotlin.io.path.absolutePathString

class Source(
    val content: String,
    val path: Path?
) {
    companion object {
        val rLineStart = Regex("^|(?<=\r\n?|\n)")
    }

    val newLines by lazy { rLineStart.findAll(content).map { result -> result.range.first }.toList() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Source

        if (content != other.content) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content.hashCode()
        result = 31 * result + (path?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return path
            ?.normalize()
            ?.absolutePathString()
            ?.replace("\\", "/")
            ?.let { path -> "file:///$path" } ?: "source"
    }

    fun at(index: Int): SourcePos {
        return SourcePos(this, index, newLines)
    }
}