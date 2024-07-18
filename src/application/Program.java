package application;

import java.util.Scanner;

import boardgame.Board;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		System.out.println();
		Description d = new Description();
		System.out.println(d.toString());
		System.out.println();

		Scanner sc = new Scanner(System.in);
		
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {

			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("---------------\n");
			System.out.print("Posição Origem: ");
			ChessPosition source = UI.readChessPosition(sc);
			
			System.out.println();
			System.out.print("Posição Destino: ");
			ChessPosition target = UI.readChessPosition(sc);
			System.out.print("---------------\n");
			
			System.out.println();
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
		
		
		
	}

}
