package application;

import boardgame.Board;
import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());
		System.out.println();
		System.out.println();
		System.out.println("--------------DESCRIÇÃO: -----------");
		System.out.println("peças 'brancas' -> cor VERMELHO");
		System.out.println("peças 'pretas' -> cor AMARELO");
		System.out.println("------------------------------------");	
		System.out.println();
		System.out.println();
	}

}
