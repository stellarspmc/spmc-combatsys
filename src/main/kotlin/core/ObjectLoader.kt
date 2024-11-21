package tk.spmc.core

import org.lwjgl.opengl.*
import tk.spmc.common.util.Utilities
import tk.spmc.core.entity.Model

class ObjectLoader {

    private var vertexArrayObject: ArrayList<Int> = ArrayList()
    private var vertexBufferObject: ArrayList<Int> = ArrayList()

    fun loadObjects(vertices: FloatArray): Model {
        val id = createVertexArrayObject()
        storeDataAttribList(0, 3, vertices)
        unbind()
        return Model(id, vertices.size / 3)
    }

    private fun createVertexArrayObject(): Int {
        val id = GL30.glGenVertexArrays()
        vertexArrayObject.add(id)
        GL30.glBindVertexArray(id)
        return id
    }

    private fun storeDataAttribList(attribNumber: Int, vertexCount: Int, data: FloatArray) {
        val vertexBuffer = GL15.glGenBuffers()
        vertexBufferObject.add(vertexBuffer)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer)

        val floatBuffer = Utilities.storeDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW)

        GL20.glVertexAttribPointer(attribNumber, vertexCount, GL11.GL_FLOAT, false, 0, 0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    private fun unbind() {
        GL30.glBindVertexArray(0)
    }

    fun cleanup() {
        for (vertexArray in vertexArrayObject) GL30.glDeleteVertexArrays(vertexArray)
        for (vertexBuffer in vertexBufferObject) GL30.glDeleteBuffers(vertexBuffer)
    }
}