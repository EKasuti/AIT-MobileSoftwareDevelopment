import androidx.navigation3.runtime.NavKey
import com.example.minesweeper.screens.Level
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute : NavKey

data class GameScreenRoute(val level: Level): NavKey