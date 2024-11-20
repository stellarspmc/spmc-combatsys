package tk.spmc

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import tk.spmc.client.game.GameLoop

private var window: Long = 0

fun main() {
    init()
    loop()

    glfwFreeCallbacks(window)
    glfwDestroyWindow(window)

    glfwTerminate()
    glfwSetErrorCallback(null)?.free()
}

fun init() {
    GLFWErrorCallback.createPrint(System.err).set()

    check(glfwInit()) { "Unable to initialize GLFW" }
    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    window = glfwCreateWindow(300, 300, "Hello World!", 0, 0)
    glfwSetKeyCallback(window) { window: Long, key: Int, _: Int, action: Int, _: Int ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true)
    }

    stackPush().use { stack ->
        val pWidth = stack.mallocInt(1)
        val pHeight = stack.mallocInt(1)
        glfwGetWindowSize(window, pWidth, pHeight)
        val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(
            window,
            (vidMode!!.width() - pWidth[0]) / 2,
            (vidMode.height() - pHeight[0]) / 2
        )
    }

    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)
}

fun loop() {
    GL.createCapabilities()
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    while ( !glfwWindowShouldClose(window) ) {
        glClear(GL_COLOR_BUFFER_BIT)
        glClear(GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    GameLoop.loop()
}