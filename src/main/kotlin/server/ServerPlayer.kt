package tk.spmc.server

import tk.spmc.common.util.Direction
import tk.spmc.common.util.Health
import tk.spmc.common.util.Vector3

data class ServerPlayer(var position: Vector3, var health: Health) {
    fun move(a: Direction) {
        position.add(a.toVector3())
    }

    fun show() {
        TODO("cuz idk how lol")
    }
}
