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
    HARD(10, 6, 10)
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

    var isGameOver by mutableStateOf(false)
        private set

    var isGameWon by mutableStateOf(false)
        private set

    var isRevealedManually by mutableStateOf(false)
        private set

    fun setNewBoard(level: Level) {
        currentLevel = level
        bombCount = level.bombs
        isGameOver = false
        isGameWon = false
        isRevealedManually = false

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
        if (isGameOver) return
        val cell = board[row][col]
        if (cell.state == CellState.OPENED) return

        val newState = when (cell.state) {
            CellState.UNOPENED -> CellState.FLAGGED
            CellState.FLAGGED -> CellState.UNOPENED
            else -> cell.state
        }
        board[row][col] = cell.copy(state = newState)
        board = board.copyOf()
    }

    fun revealCell(row: Int, col: Int) {
        if (isGameOver || isGameWon) return
        val cell = board[row][col]

        // don't reveal flagged cells
        if (cell.state == CellState.FLAGGED || cell.state == CellState.OPENED) return

        if(cell.isBomb) {
            isGameOver = true
            isRevealedManually = false
            revealAllCells()
            return
        }
        board[row][col] = cell.copy(state = CellState.OPENED)

        // if cell is empty (has no adj bombs) we reveal neighbors recursively
        if (cell.adjacentBombs == 0) {
            revealNearbyCells(row, col)
        }

        // also check if all safe cells are open
        if (checkWin()) {
            isGameWon = true
            revealAllCells()
        }

        board = board.copyOf()
    }

    private fun revealAllCells() {
        board = board.map { row ->
            row.map { it.copy(state = CellState.OPENED) }.toTypedArray()
        }.toTypedArray()
    }

    fun revealAll() {
        if (isGameOver || isGameWon) return
        isRevealedManually = true
        isGameOver = true
        revealAllCells()
        board = board.copyOf()
    }

    private fun checkWin(): Boolean {
        for (r in board) {
            for (cell in r) {
                if (!cell.isBomb && cell.state != CellState.OPENED) {
                    return false
                }
            }
        }
        return true
    }

    private fun revealNearbyCells(row: Int, col: Int) {
        val rows = board.size
        val cols = board[0].size

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val newRow = row + i
                val newCol = col + j

                if (newRow in 0 until rows && newCol in 0 until cols) {
                    val neighbor = board[newRow][newCol]
                    if (!neighbor.isBomb && neighbor.state == CellState.UNOPENED) {
                        board[newRow][newCol] = neighbor.copy(state = CellState.OPENED)
                        // recursively reveal if also empty
                        if (neighbor.adjacentBombs == 0) {
                            revealNearbyCells(newRow, newCol)
                        }
                    }
                }
            }
        }
    }
}
