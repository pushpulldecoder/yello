import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import moe.tlaster.precompose.PreComposeWindow
import presentation.main.MainComposable
import theme.AppTheme
import java.awt.Window


val LocalWindowState = compositionLocalOf<WindowState> { error("") }
val LocalWindow = compositionLocalOf<Window> { error("") }

fun main() = application {
	val windowState = rememberWindowState(size = DpSize(width = 1440.dp, height = 960.dp))




	PreComposeWindow(
		onCloseRequest = { exitApplication() },
		state = windowState,
		onKeyEvent = {
//			println(it)
			true
		}
	) {
		CompositionLocalProvider(
			LocalWindow provides window,
			LocalWindowState provides windowState
		) {
			AppTheme(
				useDarkTheme = true
			) {
				Surface(
					color = MaterialTheme.colorScheme.background,
					contentColor = MaterialTheme.colorScheme.onBackground,
					modifier = Modifier.fillMaxSize()
				) {
					MainComposable()
				}
			}
		}
	}
}
