package tk.spmc.common.util

data class Health(val max: Int) {
    private var currentHealth: Double = max.toDouble()

    fun getCurrentHealth(): Double {
        return currentHealth
    }

    fun getHealthPercentage(): Double {
        return currentHealth / max.toDouble()
    }

    fun changeHealth(health: Double) {
        if (health + currentHealth > max) currentHealth = max.toDouble()
        else currentHealth += health
    }
}
