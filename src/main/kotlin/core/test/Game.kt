package tk.spmc.core.test

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import tk.spmc.SPMCCombatSystem
import tk.spmc.core.ILogic
import tk.spmc.core.ObjectLoader
import tk.spmc.core.RenderManager
import tk.spmc.core.WindowManager
import tk.spmc.core.entity.Model

class Game : ILogic {

    private var direction = 0
    private var colour = 0f

    private var renderManager: RenderManager? = null
    private var objectLoader: ObjectLoader? = null
    private var windowManager: WindowManager? = null
    private var model: Model? = null

    init {
        renderManager = RenderManager()
        windowManager = SPMCCombatSystem.getWindowManager()
        objectLoader = ObjectLoader()
    }

    override fun init() {
        renderManager!!.init()

        val vertices = floatArrayOf(
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
        )

        model = objectLoader!!.loadObjects(vertices)
    }

    override fun input() {
        if (windowManager!!.isKeyPressed(GLFW.GLFW_KEY_W)) direction = 1
        else if (windowManager!!.isKeyPressed(GLFW.GLFW_KEY_S)) direction = -1
        else direction = 0
    }

    override fun update() {
        colour += direction * .001f
        if (colour > 1f) colour = 1f
        else if (colour < 0f) colour = 0f
    }

    override fun render() {
        if (windowManager!!.resize) GL11.glViewport(0, 0, windowManager!!.width, windowManager!!.height)
        windowManager!!.setClearColour(colour, colour, colour, 0f)
        //if (model == null) renderManager!!.clear()
        renderManager!!.render(model!!)
    }

    override fun cleanup() {
        renderManager!!.cleanup()
        objectLoader!!.cleanup()
    }

}