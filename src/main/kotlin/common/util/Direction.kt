package tk.spmc.common.util

import kotlin.math.cos
import kotlin.math.sin

data class Direction(var angle: Double) {
    fun toVector3(): Vector3 {
        return Vector3(cos(angle), 0.0, sin(angle))
    }
}
