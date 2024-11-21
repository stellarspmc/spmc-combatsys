package tk.spmc.core

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import tk.spmc.SPMCCombatSystem
import tk.spmc.core.entity.Model

class RenderManager {
    private var windowManager: WindowManager? = null

    init {
        windowManager = SPMCCombatSystem.getWindowManager()
    }

    fun init() {

    }

    fun render(model: Model) {
        clear()
        GL30.glBindVertexArray(model.id)
        GL20.glEnableVertexAttribArray(0)
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }

    fun clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun cleanup() {

    }
}