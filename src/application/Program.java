package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
				
				UI.clearScreen();
				
				/*
				System.out.println();
				Description d = new Description();
				System.out.println(d.toString());
				System.out.println();
				*/
				
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print(">>>>Posicao Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				System.out.println();
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print(">>>>Posicao Destino: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				System.out.println();
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if (chessMatch.getPromoted() != null) {
					System.out.print("Entre com a peca para promover! (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
				
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
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		
		
		
	}

}
