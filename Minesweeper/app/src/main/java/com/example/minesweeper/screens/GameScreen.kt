package com.example.minesweeper.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minesweeper.R
import kotlin.math.roundToInt
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    minesweeperViewModel: MinesweeperViewModel = viewModel(),
    selectedLevel: Level,
) {

    var flagMode by rememberSaveable { mutableStateOf(false)}
    var elapsedTime by remember { mutableIntStateOf(0) }

    // create a new board when level changes
    LaunchedEffect(selectedLevel) {
        minesweeperViewModel.setNewBoard(selectedLevel)
    }

    // start timer when board changes / game restarts
    LaunchedEffect(minesweeperViewModel.board) {
        while (true) {
            delay(1000)
            elapsedTime++
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top bar (had to replace top bar cause of height issue)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(colorResource(R.color.dark_green)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            IconButton( onClick = { }, modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Title
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            // Refresh button
            IconButton(
                onClick = {
                    minesweeperViewModel.setNewBoard(selectedLevel)
                    elapsedTime = 0
                    flagMode = false
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.restart_game),
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // bomb count and timer
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(stringResource(R.string.bombs_count, minesweeperViewModel.bombCount))
            Text(stringResource(R.string.time_counter, elapsedTime))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Game over message
        if (minesweeperViewModel.isGameOver && !minesweeperViewModel.isGameWon) {
            Spacer(modifier = Modifier.height(8.dp))
            if (minesweeperViewModel.isRevealedManually) {
                Text(
                    text = stringResource(R.string.hard_reveal_message),
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Text(
                    text = stringResource(R.string.game_over_message),
                    color = Color.Red,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Game won message
        if (minesweeperViewModel.isGameWon) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.game_won_message),
                color = Color.Green,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // the board
        MineSweeperBoard(
            board = minesweeperViewModel.board,
            flagMode = flagMode,
            onCellClick = { row, col ->
                if (flagMode) minesweeperViewModel.toggleFlag(row, col)
                else minesweeperViewModel.revealCell(row, col)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))


        // Reveal and flag buttons
        if (!minesweeperViewModel.isGameOver && !minesweeperViewModel.isGameWon) {
            // Reveal and flag buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { flagMode = !flagMode }) {
                    Text(if (flagMode) stringResource(R.string.flag_mode_on) else stringResource(R.string.flag_mode_off))
                }

                Button(onClick = { minesweeperViewModel.revealAll() }) {
                    Text(stringResource(R.string.reveal_all))
                }
            }
        } else {
            // Restart button when game ends
            Button(
                onClick = {
                    minesweeperViewModel.setNewBoard(selectedLevel)
                    elapsedTime = 0
                    flagMode = false
                }
            ) {
                Text(stringResource(R.string.restart_game))
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MineSweeperBoard(
    board: Array<Array<Cell>>,
    flagMode: Boolean,
    onCellClick: (Int, Int) -> Unit
) {
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    val bombImage = ImageBitmap.imageResource(id = R.drawable.bomb)
    val flagImage = ImageBitmap.imageResource(id = R.drawable.flag)

    val rows = board.size
    val cols = if (rows > 0) board[0].size else 0

    val cellSizeDp = 60.dp
    val density = LocalDensity.current
    val cellSizePx = with(density) { cellSizeDp.toPx() }

    // The board size scales with rows and columns
    val boardWidth = cellSizeDp * cols
    val boardHeight = cellSizeDp * rows

    Canvas(
        modifier = Modifier
            .size(width = boardWidth, height = boardHeight)
            .pointerInput(board, flagMode) {
                detectTapGestures { offset ->
                    val col = (offset.x / cellSizePx).toInt()
                    val row = (offset.y / cellSizePx).toInt()
                    if (row in board.indices && col in board[row].indices) {
                        onCellClick(row, col)
                    }
                }
            }
    ) {
        canvasSize = this.size
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        // Draw cells
        board.forEachIndexed { row, cols ->
            cols.forEachIndexed { col, cell ->
                val startX = col * cellWidth
                val startY = row * cellHeight
                val cellOffset = Offset(startX, startY)
                val cellSize = Size(cellWidth, cellHeight)

                drawRect(
                    color = when (cell.state) {
                        CellState.OPENED -> {
                            if (cell.isBomb) Color.Red else Color.White // opened cell color
                        }
                        CellState.FLAGGED -> Color.Yellow
                        CellState.UNOPENED -> Color.LightGray // unopened cell color
                    },
                    topLeft = cellOffset,
                    size = cellSize
                )

                drawRect(
                    color = Color.Black,
                    topLeft = cellOffset,
                    size = cellSize,
                    style = Stroke(width = 2f)
                )


                if (cell.state == CellState.OPENED) {
                    if (cell.isBomb) {
                        drawBomb(this, bombImage, startX, startY, cellWidth, cellHeight)
                    } else if (cell.adjacentBombs > 0) {
                        drawNumber(this, startX, startY, cellWidth, cellHeight, cell.adjacentBombs)
                    }
                }

                // Draw flag on top if flagged
                if (cell.state == CellState.FLAGGED) {
                    // drawFlag(this, startX, startY, cellWidth, cellHeight)
                    drawFlagImage(this, flagImage, startX, startY, cellWidth, cellHeight)
                }
            }
        }

        // border around the board
        drawRect(
            color = Color.Black,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, size.height),
            style = Stroke(width = 12f)
        )
    }
}


// Draw bomb
fun drawBomb(
    drawScope: DrawScope,
    bombImage: ImageBitmap,
    startX: Float,
    startY: Float,
    cellWidth: Float,
    cellHeight: Float
) {
    val imageSize = minOf(cellWidth, cellHeight) * 0.6f
    drawScope.drawImage(
        image = bombImage,
        dstOffset = IntOffset(
            (startX + (cellWidth - imageSize) / 2f).roundToInt(),
            (startY + (cellHeight - imageSize) / 2f).roundToInt()
        ),
        dstSize = IntSize(imageSize.roundToInt(), imageSize.roundToInt())
    )
}

// draw flag 
fun drawFlagImage(
    drawScope: DrawScope,
    flagImage: ImageBitmap,
    startX: Float,
    startY: Float,
    cellWidth: Float,
    cellHeight: Float
) {
    val imageSize = minOf(cellWidth, cellHeight) * 0.6f
    drawScope.drawImage(
        image = flagImage,
        dstOffset = IntOffset(
            (startX + (cellWidth - imageSize) / 2f).roundToInt(),
            (startY + (cellHeight - imageSize) / 2f).roundToInt()
        ),
        dstSize = IntSize(imageSize.roundToInt(), imageSize.roundToInt())
    )
}


// adjacent numbers
fun drawNumber(
    drawScope: DrawScope,
    startX: Float,
    startY: Float,
    cellWidth: Float,
    cellHeight: Float,
    number: Int
) {
    drawScope.drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            textSize = minOf(cellWidth, cellHeight) / 2.5f
            color = when (number) {
                1 -> android.graphics.Color.BLUE
                2 -> android.graphics.Color.GREEN
                3 -> android.graphics.Color.RED
                4 -> android.graphics.Color.MAGENTA
                else -> android.graphics.Color.BLACK
            }
            textAlign = Paint.Align.CENTER
        }
        canvas.nativeCanvas.drawText(
            number.toString(),
            startX + cellWidth / 2f,
            startY + cellHeight * 0.7f,
            paint
        )
    }
}