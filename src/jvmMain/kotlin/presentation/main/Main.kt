package presentation.main

import LocalWindowState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import moe.tlaster.precompose.viewmodel.viewModel
import org.jaudiotagger.audio.AudioHeader
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jetbrains.skia.Image
import presentation.component.Hr
import presentation.component.Vc
import presentation.main.composable.bottomBar.YelloBottomBar
import presentation.main.composable.dirStructure.FileStructure
import presentation.main.composable.drawer.leftDrawer.LeftDrawer
import presentation.main.composable.drawer.rightDrawer.RightDrawer
import util.AudioUtil
import java.io.File


@Composable
fun MainComposable() {

	val mainViewModel = viewModel(MainViewModel::class) { MainViewModel() }
	val playerViewModel = viewModel(PlayerViewModel::class) { PlayerViewModel() }

	val currentFolder by mainViewModel.currentFolder.collectAsState()

	val currentQueue by playerViewModel.currentQueue.collectAsState()
	val currentTrackIndex by playerViewModel.currentTrackIndex.collectAsState()

	var audioFile by remember { mutableStateOf<File?>(null) }
	var audioTag by remember { mutableStateOf<Tag?>(null) }
	var audioHeader by remember { mutableStateOf<AudioHeader?>(null) }
	var albumImage by remember { mutableStateOf<ImageBitmap?>(null) }
	var audioTitle by remember { mutableStateOf<String?>(null) }
	var audioArtist by remember { mutableStateOf<String?>(null) }

	LaunchedEffect(key1 = currentTrackIndex, key2 = currentQueue) {
		try {
			audioFile = currentQueue.getOrNull(currentTrackIndex ?: -1)
			audioFile?.let {
				AudioUtil.audioFileIO.readFile(it).let {
					audioTag = it.tag
					audioHeader = it.audioHeader
				}
				audioTitle = audioTag?.getFirst(FieldKey.TITLE)?.let { it.ifEmpty { audioFile?.name } }
				audioArtist = audioTag?.getFirst(FieldKey.ARTIST)
			} ?: run {
				audioTag = null
				audioHeader = null
				albumImage = null
				audioTitle = audioFile?.name
				audioArtist = null
			}
		} catch (e: Exception) {
			audioTag = null
			audioHeader = null
			albumImage = null
			audioTitle = audioFile?.name
			audioArtist = null
		}
		try {
			audioTag?.firstArtwork?.binaryData?.let { albumImage = Image.makeFromEncoded(it).toComposeImageBitmap() }
		} catch (_: Exception) {
			albumImage = null
		}
	}

	val window = LocalWindowState.current
	LaunchedEffect(Unit) {
		snapshotFlow { window.size }
			.distinctUntilChanged()
			.collect {
				println(it)
			}
	}

	println("name : ${audioFile?.path}")

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
		) {
			LeftDrawer(
				albumImage = albumImage
			)
			Vc()
			FileStructure(
				mainViewModel = mainViewModel,
				playerViewModel = playerViewModel,
				modifier = Modifier.weight(1f)
			)
			AnimatedVisibility(
				visible = currentQueue.isNotEmpty()
			) {
				Row {
					Vc()
					RightDrawer(
						currentQueue = currentQueue,
						currentTrackIndex = currentTrackIndex,
						onClickFile = { playerViewModel.playFromQueue(it) }
					)
				}
			}
		}
		Hr()
		YelloBottomBar(
			playerViewModel = playerViewModel,
			title = audioTitle,
			artist = audioTag?.getFirst(FieldKey.ARTIST),
		)
	}
}
