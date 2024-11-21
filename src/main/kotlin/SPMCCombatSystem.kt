package tk.spmc

import tk.spmc.common.util.Constants
import tk.spmc.core.EngineManager
import tk.spmc.core.WindowManager
import tk.spmc.core.test.Game

private var windowManager: WindowManager? = null
private var game: Game? = null

fun main() {
    windowManager = WindowManager(Constants.TITLE, 800, 600, false)
    game = Game()
    val engineManager = EngineManager()
    engineManager.start()
}

class SPMCCombatSystem {
    companion object {
        fun getWindowManager(): WindowManager? {
            return windowManager
        }

        fun getGame(): Game? {
            return game
        }
    }
}
