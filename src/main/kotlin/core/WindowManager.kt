package tk.spmc.core

import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

class WindowManager(private var title: String, var width: Int, var height: Int, private var vSync: Boolean) {
    private var window: Long? = null
    var resize: Boolean = false

    private var matrixProjection: Matrix4f? = null

    init {
        matrixProjection = Matrix4f()
    }

    fun init() {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit()) throw IllegalArgumentException("Unable to initialize GLFW")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE)

        var maximized = false
        if (width == 0 && height == 0) {
            width = 100
            height = 100
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)
            maximized = true
        }

        window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create GLFW window")

        glfwSetFramebufferSizeCallback(window!!) { _: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            this.resize = true
        }

        glfwSetKeyCallback(window!!) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true)
        }

        if (maximized) glfwMaximizeWindow(window!!)
        else {
            val vidMode: GLFWVidMode? = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(window!!, (vidMode!!.width() - width) / 2, (vidMode.height() - height) / 2)
        }

        glfwMakeContextCurrent(window!!)

        if (this.vSync) glfwSwapInterval(1)

        glfwShowWindow(window!!)
        GL.createCapabilities()

        GL11.glClearColor(0f, 0f, 0f, 0f)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glCullFace(GL11.GL_BACK)
    }

    fun update() {
        glfwSwapBuffers(window!!)
        glfwPollEvents()
    }

    fun cleanup() {
        glfwDestroyWindow(window!!)
    }

    fun setClearColour(r: Float, g: Float, b: Float, a: Float) {
        GL11.glClearColor(r, g, b, a)
    }

    fun isKeyPressed(keycode: Int): Boolean {
        return glfwGetKey(window!!, keycode) == GLFW_PRESS
    }

    fun windowShouldClose(): Boolean {
        return glfwWindowShouldClose(window!!)
    }

    fun setTitle(title: String) {
        glfwSetWindowTitle(window!!, title)
    }

    fun updateProjectionMatrix(): Matrix4f {
        val aspectRatio: Float = width.toFloat() / height.toFloat()
        return matrixProjection!!.setPerspective(FOV.toFloat(), aspectRatio, Z_NEAR.toFloat(), Z_FAR.toFloat())
    }

    fun updateProjectionMatrix(matrix4f: Matrix4f, width: Int, height: Int): Matrix4f {
        val aspectRatio: Float = width.toFloat() / height.toFloat()
        return matrix4f.setPerspective(FOV.toFloat(), aspectRatio, Z_NEAR.toFloat(), Z_FAR.toFloat())
    }

    companion object {
        const val FOV = Math.PI/4
        const val Z_NEAR = 0.01
        const val Z_FAR = 1000.0
    }
}
