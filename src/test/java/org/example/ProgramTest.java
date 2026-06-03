package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ProgramTest {

    private char[] board(String s) {
        char[] b = new char[9];
        for (int i = 0; i < 9; i++) {
            char c = s.charAt(i);
            b[i] = (c == '.') ? ' ' : c;
        }
        return b;
    }

    @Test
    void gameInitsEmptyBoard() {
        Game g = new Game();
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', g.board[i]);
        }
    }

    @Test
    void gamePlayersHaveSymbols() {
        Game g = new Game();
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertEquals(State.PLAYING, g.state);
    }

    @Test
    void checkStateXWinRow() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("XXX......")));
    }

    @Test
    void checkStateXWinColumn() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X..X..X..")));
    }

    @Test
    void checkStateXWinDiagonal() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X...X...X")));
    }

    @Test
    void checkStateXWinAntiDiagonal() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("..X.X.X..")));
    }

    @Test
    void checkStateOWin() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(board("OOO......")));
    }

    @Test
    void checkStatePlaying() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(board(".........")));
    }

    @Test
    void checkStateDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(board("XOXXOOOXX")));
    }

    @Test
    void checkStateMiddleRow() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("...XXX...")));
    }

    @Test
    void checkStateBottomRow() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(board("......OOO")));
    }

    @Test
    void generateMovesEmptyBoard() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("........."), moves);
        assertEquals(9, moves.size());
    }

    @Test
    void generateMovesPartialBoard() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("XOX......"), moves);
        assertEquals(6, moves.size());
    }

    @Test
    void generateMovesFullBoard() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("XOXXOOOXX"), moves);
        assertEquals(0, moves.size());
    }

    @Test
    void evaluateXWinForXPlayer() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(board("XXX......"), g.player1));
    }

    @Test
    void evaluateXWinForOPlayer() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-Game.INF, g.evaluatePosition(board("XXX......"), g.player2));
    }

    @Test
    void evaluateDrawIsZero() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(0, g.evaluatePosition(board("XOXXOOOXX"), g.player1));
    }

    @Test
    void evaluatePlayingIsMinusOne() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(board("........."), g.player1));
    }

    @Test
    void evaluateOWinForOPlayer() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(Game.INF, g.evaluatePosition(board("OOO......"), g.player2));
    }

    @Test
    void miniMaxReturnsValidMoveNearFull() {
        Game g = new Game();
        int move = g.MiniMax(board("XOXXO.OXX"), g.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void miniMaxFromTwoInARowRuns() {
        Game g = new Game();
        int move = g.MiniMax(board("OO......."), g.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void miniMaxOnEmptyBoardRuns() {
        Game g = new Game();
        int move = g.MiniMax(board("........."), g.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void playerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 4;
        p.selected = true;
        p.win = false;
        assertEquals('X', p.symbol);
        assertEquals(4, p.move);
        assertTrue(p.selected);
        assertFalse(p.win);
    }

    @Test
    void stateValues() {
        assertEquals(4, State.values().length);
        assertEquals(State.XWIN, State.valueOf("XWIN"));
    }

    @Test
    void cellConstructorAndGetters() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void cellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void utilityPrintsDoNotThrow() {
        Utility.print(new char[]{'X','O','X','.','.','.','.','.','.'});
        Utility.print(new int[]{1,2,3,4,5,6,7,8,9});
        ArrayList<Integer> m = new ArrayList<>();
        m.add(1); m.add(2);
        Utility.print(m);
        assertNotNull(m);
    }

    @Test
    void panelConstructorBuildsGrid() {
        TicTacToePanel panel =
            new TicTacToePanel(new java.awt.GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }
}