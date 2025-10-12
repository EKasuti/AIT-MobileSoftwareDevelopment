package com.example.minesweeper.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

// Game levels
enum class Level(val rows: Int, val cols: Int, val bombs: Int) {
    EASY(5, 5, 3),
    MEDIUM(8, 5, 6),
    HARD(12, 5, 10)
}


// cell state (referenced from wikipedia minesweeper's article)
enum class CellState {
    UNOPENED,
    FLAGGED,
    OPENED
}

data class Cell(
    var isBomb: Boolean = false,
    var adjacentBombs: Int = 0,
    var state: CellState = CellState.UNOPENED
)

class MinesweeperViewModel : ViewModel() {

    var board by mutableStateOf(
        Array(3) {
            Array(3) {
                Cell()
            }
        })
        private set

    var bombCount by mutableIntStateOf(0)
        private set

    var currentLevel by mutableStateOf<Level?>(null)
        private set

    fun setNewBoard(level: Level) {
        currentLevel = level
        bombCount = level.bombs

        val newBoard = Array(level.rows) {
            Array(level.cols) {
                Cell()
            }
        }

        // Place the bombs
        var bombsPlaced = 0
        while (bombsPlaced < level.bombs) {
            val row = Random.nextInt(level.rows)
            val col = Random.nextInt(level.cols)
            if (!newBoard[row][col].isBomb) {
                newBoard[row][col].isBomb = true
                bombsPlaced++
            }
        }
        // count adjacent bombs for each non-bomb cell
        for (row in 0 until level.rows) {
            for (col in 0 until level.cols) {
                if (!newBoard[row][col].isBomb) {
                    newBoard[row][col].adjacentBombs = countAdjacentBombs(newBoard, row, col, level.rows, level.cols)
                }
            }
        }

        board = newBoard
    }

    private fun countAdjacentBombs(
        board: Array<Array<Cell>>,
        row: Int,
        col: Int,
        rows: Int,
        cols: Int
    ): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val r = row + i
                val c = col + j
                if (r in 0 until rows && c in 0 until cols && board[r][c].isBomb) {
                    count++
                }
            }
        }
        return count
    }

    fun toggleFlag(row: Int, col: Int) {
        val cell = board[row][col]
        val newState = when (cell.state) {
            CellState.UNOPENED -> CellState.FLAGGED
            CellState.FLAGGED -> CellState.UNOPENED
            CellState.OPENED -> CellState.FLAGGED
        }
        board[row][col] = cell.copy(state = newState)
        board = board.copyOf()
    }

    fun revealCell(row: Int, col: Int) {
        val cell = board[row][col]
        board[row][col] = cell.copy(state = CellState.OPENED)
        board = board.copyOf()
    }
}
