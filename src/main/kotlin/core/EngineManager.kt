package tk.spmc.core

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import tk.spmc.SPMCCombatSystem
import tk.spmc.core.test.Game

class EngineManager {

    private var fps = 0
    private var frameTime = 1f/ FRAMERATE
    private var gameLogic: ILogic? = null

    private var isRunning = false

    private var windowManager: WindowManager? = null
    private var errorCallback: GLFWErrorCallback? = null

    private fun init() {
        errorCallback = GLFWErrorCallback.createPrint(System.err)
        glfwSetErrorCallback(errorCallback)
        gameLogic = SPMCCombatSystem.getGame()
        windowManager = SPMCCombatSystem.getWindowManager()
        windowManager!!.init()
        gameLogic!!.init()
    }

    fun start() {
        init()
        if (isRunning) return
        run()
    }

    private fun run() {
        this.isRunning = true
        var frames = 0
        var frameCounter = 0L
        var lastTime = System.nanoTime()
        var unprocessedTime = 0.0

        while (isRunning) {
            var render = false
            val startTime = System.nanoTime()
            val passedTime = startTime - lastTime
            lastTime = startTime

            unprocessedTime += (passedTime.toDouble() / NANOSECOND.toDouble())
            frameCounter += passedTime

            input()
            while (unprocessedTime > frameTime) {
                render = true
                unprocessedTime -= frameTime

                if (windowManager!!.windowShouldClose()) stop()
                if (frameCounter >= NANOSECOND) {
                    setFPS(frames)
                    print(fps.toString() + "\n")
                    frames = 0
                    frameCounter = 0
                }
            }

            if (render) {
                update()
                render()
                frames++
            }
        }
    }

    private fun setFPS(frames: Int) {
        this.fps = frames
    }

    private fun stop() {
        if (!isRunning) return
        isRunning = false
    }

    private fun input() {
        gameLogic!!.input()
    }

    private fun render() {
        gameLogic!!.render()
        windowManager!!.update()
    }

    private fun update() {
        gameLogic!!.update()
    }

    fun cleanup() {
        windowManager!!.cleanup()
        gameLogic!!.cleanup()
        errorCallback!!.free()
        glfwTerminate()
    }

    companion object {
        const val NANOSECOND = 1000000000L
        const val FRAMERATE = 1000f
    }
}