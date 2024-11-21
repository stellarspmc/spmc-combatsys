package tk.spmc.common.util

import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer

class Utilities {
    companion object {
        fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
            val buffer = MemoryUtil.memAllocFloat(data.size)
            buffer.put(data).flip()
            return buffer
        }
    }
}