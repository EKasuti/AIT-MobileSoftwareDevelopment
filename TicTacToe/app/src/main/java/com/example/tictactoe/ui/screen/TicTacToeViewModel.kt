package hu.bme.ait.tictactoe.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Player { X, O }

data class BoardCell(val row: Int, val col: Int)

class TicTactToeViewModel : ViewModel() {

    var board by mutableStateOf(
        Array(3) { Array(3) { null as Player? } })
        private set

    var currentPlayer by mutableStateOf(Player.X)
        private set

    var winner by mutableStateOf<Player?>(null)
        private set

    init {
        //board[1][1] = Player.X
    }

    fun setNewBoard(size: Int) {
        board = Array(size){ Array(size) {null as Player?} }
        winner = null
        currentPlayer = Player.X
    }

    fun onCellClicked(cell: BoardCell) {
        // if the cell is empty and the game is not finished
        if (board[cell.row][cell.col] != null || winner != null) return

        board[cell.row][cell.col] = currentPlayer

        // after every move we check if there is a winner
        if (checkWinner(currentPlayer)) {
            winner = currentPlayer
            return
        }

        if (board.all { row -> row.all { it != null } }) {
            winner = null
            return
        }


        // we do not need this because currentPlayer is also a state and it is
        // used in TicTacToeScreen so it gets recomposed when the currentPlayer is changed
        // and it recomposes the TicTacToeBoard as well as it is part of TicTacToeScreen

        /*val newBoard = board.copyOf()
        newBoard[cell.row][cell.col] = currentPlayer
        board = newBoard*/

        currentPlayer =
            if (currentPlayer == Player.X) Player.O else Player.X
    }

    private fun checkWinner(player: Player): Boolean {
        val size = board.size

        // Rows
        for (row in 0 until size) {
            if ((0 until size).all { col -> board[row][col] == player }) return true
        }

        // Cols
        for (col in 0 until size) {
            if ((0 until size).all { row -> board[row][col] == player }) return true
        }

        // Diagonal
        if ((0 until size).all { i -> board[i][i] == player }) return true

        // Anti-diagonal
        if ((0 until size).all { i -> board[i][size - 1 - i] == player }) return true

        return false
    }
}