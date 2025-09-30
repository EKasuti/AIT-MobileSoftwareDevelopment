package hu.bme.ait.tictactoe.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wajahatkarim.flippable.rememberFlipController
import com.wajahatkarim.flippable.Flippable
import com.example.tictactoe.R

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    ticTactToeViewModel: TicTactToeViewModel = viewModel()
) {
    val context = LocalContext.current


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(context.getString(R.string.text_welcome), fontSize = 30.sp)

        ScoreBoard(
            xWins = ticTactToeViewModel.xWins,
            oWins = ticTactToeViewModel.oWins
        )

        WinnerCard(winner = ticTactToeViewModel.winner)

        if (ticTactToeViewModel.winner == null){
            Card(
                modifier = modifier.shadow(8.dp, shape = MaterialTheme.shapes.medium),
                border = BorderStroke(2.dp, Color(context.getColor(R.color.borderStrokeColor))),
                colors = CardDefaults.cardColors(
                    containerColor = if (ticTactToeViewModel.currentPlayer == Player.X)
                        Color(0xFFE3F2FD) else Color(0xFFFFEBEE)
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    text = context.getString(R.string.next_player_text, ticTactToeViewModel.currentPlayer),
                    fontSize = 28.sp
                )
            }
        }

        TicTacToeBoard(ticTactToeViewModel.board) {
            ticTactToeViewModel.onCellClicked(it)
        }

        Button(onClick = {
            ticTactToeViewModel.setNewBoard(3)
        }) {
            Text(context.getString(R.string.btn_reset))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TicTacToeBoard(
    board: Array<Array<Player?>>,
    onCellClicked: (BoardCell) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()

    val monkeyImage: ImageBitmap =
        ImageBitmap.imageResource(R.drawable.monkey)

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    Canvas(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .aspectRatio(1.0f)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    val cellX = (it.x / (canvasSize.width / 3)).toInt()
                    val cellY = (it.y / (canvasSize.height / 3)).toInt()

                    onCellClicked(
                        BoardCell(cellY, cellX)
                    )
                }
            }
    ) {
        canvasSize = this.size
        val canvasWidth = size.width.toInt()
        val canvasHeight = size.height.toInt()

        // Draw the grid
        val gridSize = size.minDimension
        val thirdSize = gridSize / 3

        for (i in 1..2) {
            drawLine(
                color = Color.Black,
                strokeWidth = 8f,
                pathEffect = PathEffect.cornerPathEffect(4f),
                start = androidx.compose.ui.geometry.Offset(thirdSize * i, 0f),
                end = androidx.compose.ui.geometry.Offset(thirdSize * i, gridSize)
            )
            drawLine(
                color = Color.Black,
                strokeWidth = 8f,

                start = androidx.compose.ui.geometry.Offset(0f, thirdSize * i),
                end = androidx.compose.ui.geometry.Offset(gridSize, thirdSize * i),
            )
        }

        // Draw players.. X and O
        for (row in 0..2) {
            for (col in 0..2) {
                val player = board[row][col]
                if (player != null) {
                    val centerX = col * thirdSize + thirdSize / 2
                    val centerY = row * thirdSize + thirdSize / 2
                    if (player == Player.X) {
                        drawLine(
                            color = Color.Blue,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(
                                centerX - thirdSize / 4,
                                centerY - thirdSize / 4
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                centerX + thirdSize / 4,
                                centerY + thirdSize / 4
                            ),
                        )
                        drawLine(
                            color = Color.Blue,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(
                                centerX + thirdSize / 4,
                                centerY - thirdSize / 4
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                centerX - thirdSize / 4,
                                centerY + thirdSize / 4
                            ),
                        )
                    } else {
                        drawCircle(
                            color = Color.Red,
                            style = Stroke(width = 8f),
                            center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                            radius = thirdSize / 4,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WinnerCard(
    winner: Player?,
    modifier: Modifier = Modifier
) {
    if (winner == null) return

    val flipController = rememberFlipController()
    val context = LocalContext.current

    Flippable(
        frontSide = {
            Card(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(Color(0xFFFFF9C4)),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = context.getString(R.string.confetti_icon),
                            fontSize = 40.sp
                        )
                        Text(
                            text = context.getString(R.string.winner_text, winner.name),
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = context.getString(R.string.tap_for_hint),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        },
        backSide = {
            Card(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(Color(0xFF549DDC)),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = context.getString(R.string.bulb_icon),
                            fontSize = 32.sp
                        )
                        Text(
                            text = context.getString(R.string.tip_text),
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        flipController = flipController
    )
}

@Composable
fun ScoreBoard(
    xWins: Int,
    oWins: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(6.dp),
        border = BorderStroke(2.dp, Color(context.getColor(R.color.borderStrokeColor)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = context.getString(R.string.player1, xWins),
                fontSize = 24.sp,
                color = Color(0xFF1976D2),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = context.getString(R.string.player2, oWins),
                fontSize = 24.sp,
                color = Color(0xFFD32F2F),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
