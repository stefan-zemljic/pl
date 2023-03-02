package com.flow_lang.lexing

class SourcePos(
    private val source: Source,
    val index: Int,
    private val newLines: List<Int>,
) {
    init {
        if (index < 0) {
            throw IllegalArgumentException("index $index < 0")
        }
        if (index > content.length) {
            throw IllegalArgumentException("index $index > content.length")
        }
        newLines.forEach { newLine ->
            if (newLine > source.content.length) {
                throw IllegalArgumentException("newLine $newLine > content.length")
            }
        }
    }

    val content get() = source.content
    val path get() = source.path

    private val relativePosition: Pair<Int, Int> by lazy {
        var lineIndex = newLines.binarySearch(index)
        if (lineIndex < 0) {
            lineIndex = -lineIndex - 1
        }
        lineIndex + 1 to index - newLines[lineIndex]
    }

    /** >= 1 */
    val lineNo get() = relativePosition.first

    /** >= 1 */
    val charInLine get() = relativePosition.second

    fun offset(delta: Int) = source.at(index + delta)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SourcePos

        if (source != other.source) return false
        if (index != other.index) return false

        return true
    }

    override fun hashCode(): Int {
        var result = source.hashCode()
        result = 31 * result + index
        return result
    }

    override fun toString(): String {
        val lineNo = "$lineNo".padStart(4, '0')
        val charInLine = "$charInLine".padStart(3, '0')
        return "$source:$lineNo:$charInLine"
    }
}