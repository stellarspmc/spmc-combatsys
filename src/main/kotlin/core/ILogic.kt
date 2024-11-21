package tk.spmc.core

interface ILogic {
    fun init()
    fun input()
    fun update()
    fun render()
    fun cleanup()
}