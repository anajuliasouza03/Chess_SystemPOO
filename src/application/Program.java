package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		/*
		System.out.println();
		Description d = new Description();
		System.out.println(d.toString());
		System.out.println();*/

		Scanner sc = new Scanner(System.in);
		
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			try {
				
				UI.clearScreen();
				
				System.out.println();
				Description d = new Description();
				System.out.println(d.toString());
				System.out.println();
				
				UI.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("---------------\n");
				System.out.print("Posição Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				System.out.println();
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Posição Destino: ");
				ChessPosition target = UI.readChessPosition(sc);
				System.out.print("---------------\n");
				
				System.out.println();
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				System.out.print("Pressione [ENTER] para continuar");
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				System.out.print("Pressione [ENTER] para continuar");
				sc.nextLine();
			}
		}
		
		
		
	}

}
