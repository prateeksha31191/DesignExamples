package org.example;

import game.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Game ticTacToe= new Game();
        ticTacToe.initializeGame();
        System.out.println("game winner is: " + ticTacToe.startGame());
    }
}