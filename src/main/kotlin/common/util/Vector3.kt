package tk.spmc.common.util

data class Vector3(val x: Double, val y: Double, val z: Double) {
    fun add(a: Vector3): Vector3 {
        return Vector3(x + a.x, y + a.y, z + a.z)
    }

    fun multiply(a: Vector3): Vector3 {
        return Vector3(x * a.x, y * a.y, z * a.z)
    }
}
