package Chess;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller 
{
	private Board board = new Board();
	private Team team = new Team();
	private boolean whiteTurn;
	private int totalTurns = 0;
	private int pieceAddCount = 0;
	private int pieceEndResult = 32;
	private String white = "White";
	private String black = "Black";
	private int maxWidth = 8;
	private int maxHeight = 8;
	private Piece dummyWhiteKing;
	private Piece dummyBlackKing;
	private boolean checkMate = false;
	
	public void runGame()
	{		
		String otherTurnColor = whitePlayerTurn() ? black : white;
		Scanner userInput = new Scanner(System.in);
		
		while(!checkMate)
		{			
			Position whiteKingPosition = team.getKingFromWhiteTeam();
			Position blackKingPosition = team.getKingFromBlackTeam();
			ArrayList<Piece> whiteTeam = new ArrayList<Piece>();
			ArrayList<Piece> blackTeam = new ArrayList<Piece>();
			
			String player = whitePlayerTurn() ? "White" : "Black";
			
			if(whitePlayerTurn())
			{
				System.out.println("\nIt is Player " + player + "'s turn");
				System.out.println("Here are the pieces that can move from your team!  Choose one!");
				whiteTeam = getWhiteTeamPieces();
				
				for(int i = 0; i < whiteTeam.size(); i++)
				{
					Piece currentPiece = whiteTeam.get(i);
					Position piecePosition = team.getPiecePositionFromWhiteTeam(currentPiece);
					int x = piecePosition.getPositionX();
					int y = piecePosition.getPositionY();
					String coordinate = coordinateToPosition(x, y);
					
					System.out.print(i + ")  " + currentPiece.getPieceType() + " " + "[" + coordinate + "]\t\t");
					if(i % 3 == 0)
					{
						System.out.println();
					}
				}
				
				int pieceOption = userInput.nextInt();
				Piece pieceChosen = whiteTeam.get(pieceOption);
				Position pieceChosenPosition = team.getPiecePositionFromWhiteTeam(pieceChosen);
				int chosenX = pieceChosenPosition.getPositionX();
				int chosenY = pieceChosenPosition.getPositionY();
				String coordinate = coordinateToPosition(chosenX, chosenY);
				possibleMovesForPiece(pieceChosen, pieceChosenPosition);
				
				System.out.println("You have choosen option: " + pieceOption + "\nWhich is " + pieceChosen.getPieceType() + " " + "[" + coordinate + "]");
				
				System.out.println("Here are its Possible Moves!");
				
				for(int j = 0; j < pieceChosen.getPossibleMoves().size(); j++)
				{
					Position possibleMoveForPiece = pieceChosen.getPossibleMoves().get(j);
					int possibleX = possibleMoveForPiece.getPositionX();
					int possibleY = possibleMoveForPiece.getPositionY();
					String possibleCoordinate = coordinateToPosition(possibleX, possibleY);
					System.out.println(j + ")  " + possibleCoordinate);
				}
				
				System.out.println("Now where would you like to move the piece to?");
				
				int possiblePieceMove = userInput.nextInt();
				Position chosenPossibleMove = pieceChosen.getPossibleMoves().get(possiblePieceMove);
				int chosenPossibleX = chosenPossibleMove.getPositionX();
				int chosenPossibleY = chosenPossibleMove.getPositionY();
				String chosenCoordinate = coordinateToPosition(chosenPossibleX, chosenPossibleY);
				
				String startingSpot = coordinateToPosition(chosenX, chosenY);
				String endSpot = coordinateToPosition(chosenPossibleX, chosenPossibleY);
				String command = startingSpot + " " + endSpot;
				
				movePieceOnBoard(command, pieceChosenPosition, chosenPossibleMove);
				
				if(pieceChosen.getPieceType().equals("p") && chosenPossibleMove.getPositionY() == 7)
				{
					System.out.println("What would you like to upgrade your Pawn at " + chosenCoordinate + " to?");
					
					System.out.println("1)  Queen \n2)  Rook \n3)  Bishop \n4)  Knight");
					int pawnPromoteOption = userInput.nextInt();					
					
					team.removePieceFromWhiteTeam(pieceChosen);
					
					if(pawnPromoteOption == 1)
					{						
						Piece queen = new Queen("q");
						queen.setPieceColor(white);
						team.addWhitePieceToTeam(queen, chosenPossibleMove);
						board.setChessBoardSquare(new Square(queen, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 2)
					{
						Piece rook = new Rook("r");
						rook.setPieceColor(white);
						team.addWhitePieceToTeam(rook, chosenPossibleMove);
						board.setChessBoardSquare(new Square(rook, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 3)
					{
						Piece bishop = new Bishop("b");
						bishop.setPieceColor(white);
						team.addWhitePieceToTeam(bishop, chosenPossibleMove);
						board.setChessBoardSquare(new Square(bishop, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 4)
					{
						Piece knight = new Knight("k");
						knight.setPieceColor(white);
						team.addWhitePieceToTeam(knight, chosenPossibleMove);
						board.setChessBoardSquare(new Square(knight, chosenPossibleMove), chosenPossibleMove);
					}
				}
				board.printBoard();
			}
			else
			{
				System.out.println("\nIt is Player " + player + "'s turn");
				System.out.println("Here are the pieces that can move from your team!  Choose one!");
				blackTeam = getBlackTeamPieces();
				
				for(int i = 0; i < blackTeam.size(); i++)
				{
					Piece currentPiece = blackTeam.get(i);
					Position piecePosition = team.getPiecePositionFromBlackTeam(currentPiece);
					int x = piecePosition.getPositionX();
					int y = piecePosition.getPositionY();
					String coordinate = coordinateToPosition(x, y);
					
					System.out.print(i + ")  " + currentPiece.getPieceType() + " " + "[" + coordinate + "]\t\t");
					if(i % 3 == 0)
					{
						System.out.println();
					}
				}
				
				int pieceOption = userInput.nextInt();
				Piece pieceChosen = blackTeam.get(pieceOption);
				Position pieceChosenPosition = team.getPiecePositionFromBlackTeam(pieceChosen);
				int chosenX = pieceChosenPosition.getPositionX();
				int chosenY = pieceChosenPosition.getPositionY();
				String coordinate = coordinateToPosition(chosenX, chosenY);
				possibleMovesForPiece(pieceChosen, pieceChosenPosition);
				
				System.out.println("You have choosen option: " + pieceOption + "\nWhich is " + pieceChosen.getPieceType() + " " + "[" + coordinate + "]");
				
				System.out.println("Here are its Possible Moves!");
				
				for(int j = 0; j < pieceChosen.getPossibleMoves().size(); j++)
				{
					Position possibleMoveForPiece = pieceChosen.getPossibleMoves().get(j);
					int possibleX = possibleMoveForPiece.getPositionX();
					int possibleY = possibleMoveForPiece.getPositionY();
					String possibleCoordinate = coordinateToPosition(possibleX, possibleY);
					System.out.println(j + ")  " + possibleCoordinate);
				}
				
				System.out.println("Now where would you like to move the piece to?");
				
				int possiblePieceMove = userInput.nextInt();
				Position chosenPossibleMove = pieceChosen.getPossibleMoves().get(possiblePieceMove);
				int chosenPossibleX = chosenPossibleMove.getPositionX();
				int chosenPossibleY = chosenPossibleMove.getPositionY();
				String chosenCoordinate = coordinateToPosition(chosenPossibleX, chosenPossibleY);
				
				String startingSpot = coordinateToPosition(chosenX, chosenY);
				String endSpot = coordinateToPosition(chosenPossibleX, chosenPossibleY);
				String command = startingSpot + " " + endSpot;
				
				movePieceOnBoard(command, pieceChosenPosition, chosenPossibleMove);
				
				if(pieceChosen.getPieceType().equals("P") && chosenPossibleMove.getPositionY() == 0)
				{
					System.out.println("What would you like to upgrade your Pawn at " + chosenCoordinate + " to?");
					
					System.out.println("1)  Queen \n2)  Rook \n3)  Bishop \n4)  Knight");
					int pawnPromoteOption = userInput.nextInt();					
					
					team.removePieceFromBlackTeam(pieceChosen);
					
					if(pawnPromoteOption == 1)
					{						
						Piece queen = new Queen("Q");
						queen.setPieceColor(black);
						team.addBlackPieceToTeam(queen, chosenPossibleMove);
						board.setChessBoardSquare(new Square(queen, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 2)
					{
						Piece rook = new Rook("R");
						rook.setPieceColor(black);
						team.addBlackPieceToTeam(rook, chosenPossibleMove);
						board.setChessBoardSquare(new Square(rook, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 3)
					{
						Piece bishop = new Bishop("B");
						bishop.setPieceColor(black);
						team.addBlackPieceToTeam(bishop, chosenPossibleMove);
						board.setChessBoardSquare(new Square(bishop, chosenPossibleMove), chosenPossibleMove);
					}
					else if(pawnPromoteOption == 4)
					{
						Piece knight = new Knight("K");
						knight.setPieceColor(black);
						team.addBlackPieceToTeam(knight, chosenPossibleMove);
						board.setChessBoardSquare(new Square(knight, chosenPossibleMove), chosenPossibleMove);
					}
				}
				board.printBoard();
			}
			
			// detects if and when check and/or check mate happens
			System.out.println("\nabout to get black king");
			team.getKingFromBlackTeam();
			System.out.println("suppose to of gotten it");
			
			if(whitePlayerTurn())
			{
				if(isKingInCheck(whiteKingPosition))
				{		
					System.out.println();
					System.out.println("\n\nPlayer " + otherTurnColor + "'s King is in Check!");
					
					if(isKingInCheckMate(whiteKingPosition))
					{
						String winner = whitePlayerTurn() ? black : white;
						
						System.out.println("\nTeam " + otherTurnColor + " has been Checkmated!"
								+ "\nThe Winner is " + winner + " team!!"
								+ " CONGRATULATIONS!");
						checkMate = true;
					}
				}
			}
			else
			{
				if(isKingInCheck(blackKingPosition))
				{
					System.out.println("\n\nPlayer " + otherTurnColor + "'s King is in Check!");
					
					if(isKingInCheckMate(blackKingPosition))
					{
						String winner = whitePlayerTurn() ? black : white;
						
						System.out.println("\nTeam " + otherTurnColor + " has been Checkmated!"
								+ "\nThe Winner is " + winner + " team!!"
								+ " CONGRATULATIONS!");
						checkMate = true;
					}
				}
			}
		}
	}

	private ArrayList<Piece> getWhiteTeamPieces()
	{
		ArrayList<Piece> whiteTeam = new ArrayList<>();
		
		// adds all pieces, except kings, to their appropriate teams in an ArrayList
		for(int y = 0; y < maxHeight; y++)
		{
			for(int x = 0; x < maxWidth; x++)
			{
				Piece currentPiece = board.getChessBoardSquare(x, y).getPiece();
				Position currentPosition = new Position(x, y);
				
				possibleMovesForPiece(currentPiece, currentPosition);
				
				if(currentPiece.getPieceColor().equals(white))
				{
					if(currentPiece.getPossibleMoves().size() > 0)
					{
						whiteTeam.add(currentPiece);
					}
				}
			}
		}
		
		return whiteTeam;
	}
	
	private ArrayList<Piece> getBlackTeamPieces()
	{
		ArrayList<Piece> blackTeam = new ArrayList<>();
		
		// adds all pieces, except kings, to their appropriate teams in an ArrayList
		for(int y = 0; y < maxHeight; y++)
		{
			for(int x = 0; x < maxWidth; x++)
			{
				Piece currentPiece = board.getChessBoardSquare(x, y).getPiece();
				Position currentPosition = new Position(x, y);
				
				possibleMovesForPiece(currentPiece, currentPosition);
				
				if(currentPiece.getPieceColor().equals(black))
				{
					if(currentPiece.getPossibleMoves().size() > 0)
					{
						blackTeam.add(currentPiece);
					}
				}
			}
		}
		
		return blackTeam;
	}
	
	private boolean whitePlayerTurn()
	{		
		whiteTurn = (totalTurns % 2 == 0) ? true : false;
		return whiteTurn;
	}
	
	public void addPieceToBoard(Piece piece, int x, int y)
	{
		Position position = new Position(x, y);
		Square newSquare = new Square(piece, position);
		board.setChessBoardSquare(newSquare, position);
		
		if(piece.getPieceColor().equals(white))
		{
			team.addWhitePieceToTeam(piece, position);
		}
		else
		{
			team.addBlackPieceToTeam(piece, position);
		}
		
		if(piece.getPieceType().equals("k"))
		{
			team.setWhiteKing(piece);
			dummyWhiteKing = piece;
		}
		if(piece.getPieceType().equals("K"))
		{
			team.setBlackKing(piece);
			dummyBlackKing = piece;
		}
		
		pieceAddCount++;
		if(pieceAddCount == pieceEndResult)
		{
			board.printBoard();
			System.out.println();
		}
	}

	public void movePieceOnBoard(String command, Position pieceStart, Position pieceEnd)
	{
		int x1 = pieceStart.getPositionX();
		int y1 = pieceStart.getPositionY();
		int x2 = pieceEnd.getPositionX();
		int y2 = pieceEnd.getPositionY();
		String startSpot = command.substring(0,2);
		String endSpot = command.substring(3,5);
		Piece piece = board.getChessBoardSquare(x1, y1).getPiece();
		Square newSquare = new Square(board.getChessBoardSquare(x2, y2).getPiece(), pieceEnd);
		Pawn dummyPawn = new Pawn("Pawn");

		if(piece.getClass() == dummyPawn.getClass())
		{
			if(piece.getPieceColor() == white)
			{
				if(dummyPawn.validWhiteMovement(x1, y1, x2, y2, newSquare))
				{
//					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x1, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else if(dummyPawn.pawnCapturing(x1, y1, x2, y2, piece.getPieceColor(), newSquare))
				{
//					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else
				{
					System.out.print(command + " is an invalid move command! Please revise it!");
					System.out.println();
				}
			}
			else if(piece.getPieceColor() == black)
			{
				if(dummyPawn.validBlackMovement(x1, y1, x2, y2, newSquare))
				{
//					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x1, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else if(dummyPawn.pawnCapturing(x1, y1, x2, y2, piece.getPieceColor(), newSquare))
				{
//					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else
				{
					System.out.print(command + " is an invalid move command! Please revise it!");
					System.out.println();
				}
			}
		}
		else
		{
			if(piece.validMovement(x1, y1, x2, y2))
			{
//				getPiecePath(x1, y1, x2, y2);
				movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
			}
			else
			{
				System.out.println();
				System.out.print(command + " is an invalid move command! Please revise it!");
				System.out.println();
			}
		}
	}

	private void movePieceCheck(Piece piece, int x1, int y1, int x2, int y2, Position start, Position end, String command, String startSpot, String endSpot)
	{		 
		String currentTurnColor = whitePlayerTurn() ? white : black;
		
		if(board.getChessBoardSquare(x2, y2).getPiece().getPieceColor() != (piece.getPieceColor()))
		{
			if(otherPieceExistsInPath(x1, y1, x2, y2))
			{
				if(piece.getPieceColor() == currentTurnColor)
				{					
					movePiece(piece, start, end);
					
					totalTurns++;
					
					System.out.println();
					System.out.print("Successfully moved piece " + coordinateToPosition(x1, y1) + " to " + coordinateToPosition(x2, y2));
					board.printBoard();
					System.out.println();
				}
				else
				{
					System.out.println();
					System.out.println(command);
					System.out.println("Piece at " + startSpot + " is not your piece!  Choose from your own color!");
				}
			}
			else
			{
				System.out.println();
				System.out.println(command + " \nYou can't do that!  Your path is being blocked!");
			}
		}
		else
		{
			System.out.println();
			System.out.print("There is a an ally piece on the spot you are trying to move to! " + endSpot + " \nMove is not valid!");
		}
	}
	
	private void movePiece(Piece piece, Position startPosition, Position endPosition)
	{		
		int x1 = startPosition.getPositionX();
		int y1 = startPosition.getPositionY();
		int x2 = endPosition.getPositionX();
		int y2 = endPosition.getPositionY();
		
		board.setChessBoardSquare(new Square(piece, endPosition), endPosition);

		board.setChessBoardSquare(new Square(new Piece("-"), startPosition), startPosition);
		board.getChessBoardSquare(x1, y1).getPiece().setPieceColor("-");
	
		if(piece.getPieceColor() == white)
		{
			team.setPiecePositionOnWhiteTeam(piece, endPosition);
		}
		else
		{
			team.setPiecePositionOnBlackTeam(piece, endPosition);
		}
		
//		if(piece.getPieceType().equals("k"))
//		{
//			Position newPosition = new Position(x2, y2);
//			
//			team.setWhiteKingPosition(newPosition);
//		}
//		if(piece.getPieceType().equals("K"))
//		{
//			Position newPosition = new Position(x2, y2);
//			team.setBlackKingPosition(newPosition);
//		}
	}
	
	private boolean isKingInCheck(Position kingPosition)
	{
		boolean inCheck = false;
		String colorCheck = whitePlayerTurn() ? "Black" : "White";
		
//		System.out.println("\nPieces that can move for " + colorCheck + "'s Team");
		
		for(int y = 0; y < maxWidth; y++)
		{
			for(int x = 0; x < maxHeight; x++)
			{
				Piece currentPiece = board.getChessBoardSquare(x, y).getPiece();
				
				if(currentPiece != board.getBlankPiece() && currentPiece.getPieceColor().equals(colorCheck))
				{
					Position positionCheck = new Position(x, y);
					possibleMovesForPiece(currentPiece, positionCheck);
					
					ArrayList<Position> pieceMoves = currentPiece.getPossibleMoves();
					
					for(int i = 0; !inCheck && i < pieceMoves.size(); i++)
					{
						Position indexPosition = pieceMoves.get(i);
						
						if(indexPosition.equals(kingPosition))
						{
							inCheck = true;
						}
					}
				}
			}
		}		
		return inCheck;
	}
	
	private boolean isKingInCheckMate(Position kingPosition)
	{
		boolean checkMate = true;
		boolean kingHasMoves = true;
		
		Position whiteKingPosition = team.getKingFromWhiteTeam();
		Position blackKingPosition = team.getKingFromBlackTeam();
		
		String enemyColor = whitePlayerTurn() ? "Black" : "White";
		String allyColor = whitePlayerTurn() ? "White" : "Black";
		ArrayList<Position> kingPossibleMoves = new ArrayList<Position>();
		ArrayList<Position> outOfCheckMovesForKing = new ArrayList<Position>();
		ArrayList<Position> outOfCheckAllyMoves = new ArrayList<Position>();
		ArrayList<Piece> allyPiecesThatSaveKing = new ArrayList<Piece>();
		ArrayList<Piece> allyTeam = new ArrayList<Piece>();
		ArrayList<Piece> enemyTeam = new ArrayList<Piece>();
		
		// adds all pieces, except kings, to their appropriate teams in an ArrayList
		for(int y = 0; y < maxHeight; y++)
		{
			for(int x = 0; x < maxWidth; x++)
			{
				Piece currentPiece = board.getChessBoardSquare(x, y).getPiece();
				Position currentPosition = new Position(x, y);
				
				if(!(currentPiece.getPieceType().equals("k")) && !(currentPiece.getPieceType().equals("K")))
				{
					if(currentPiece.getPieceColor().equals(allyColor))
					{
						possibleMovesForPiece(currentPiece, currentPosition);
						allyTeam.add(currentPiece);
					}
					else if(currentPiece.getPieceColor().equals(enemyColor))
					{
						possibleMovesForPiece(currentPiece, currentPosition);
						enemyTeam.add(currentPiece);
					}
				}
			}
		}
		
		// gets white or black kings possible movements
		// depending on who's turn it is!
		// if white turn then whiteKing moves
		// else blackKing moves
		if(whitePlayerTurn())
		{
			possibleMovesForPiece(dummyWhiteKing, whiteKingPosition);			
			kingPossibleMoves = dummyWhiteKing.getPossibleMoves();
		}		
		else
		{
			possibleMovesForPiece(dummyBlackKing, blackKingPosition);
			kingPossibleMoves = dummyBlackKing.getPossibleMoves();
		}
		
		// iterates through king valid moves
		for(Position validKingLocation : kingPossibleMoves)
		{			
			if(whitePlayerTurn())
			{
				Position originalKingPosition = whiteKingPosition;
				Position ghostMovePosition = validKingLocation;
				int ghostX = ghostMovePosition.getPositionX();
				int ghostY = ghostMovePosition.getPositionY();
				
				Piece testPieceSpot = board.getChessBoardSquare(ghostX, ghostY).getPiece();
				Square testSquare = board.getChessBoardSquare(ghostX, ghostY);
				
				movePiece(dummyWhiteKing, originalKingPosition, ghostMovePosition);
				
				if(isKingInCheck(ghostMovePosition))
				{
					checkMate = true;
				}
				else
				{
					outOfCheckMovesForKing.add(validKingLocation);
					checkMate = false;
				}
				
				if(outOfCheckMovesForKing.size() > 0)
				{
					kingHasMoves = true;
					checkMate = false;
				}
				else
				{
					kingHasMoves = false;
				}
				
				movePiece(dummyWhiteKing, ghostMovePosition, originalKingPosition);
				
				testSquare.setPiece(testPieceSpot);
				board.setChessBoardSquare(testSquare, ghostMovePosition);
			}
			else
			{
				Position originalKingPosition = blackKingPosition;
				Position ghostMovePosition = validKingLocation;
				int ghostX = ghostMovePosition.getPositionX();
				int ghostY = ghostMovePosition.getPositionY();
				
				Piece testPieceSpot = board.getChessBoardSquare(ghostX, ghostY).getPiece();
				Square testSquare = board.getChessBoardSquare(ghostX, ghostY);
				
				movePiece(dummyBlackKing, originalKingPosition, ghostMovePosition);
				
				if(isKingInCheck(ghostMovePosition))
				{
					checkMate = true;
				}
				else
				{
					outOfCheckMovesForKing.add(validKingLocation);
					checkMate = false;
				}
				
				if(outOfCheckMovesForKing.size() > 0)
				{
					kingHasMoves = true;
					checkMate = false;
				}
				else
				{
					kingHasMoves = false;
				}
				
				movePiece(dummyBlackKing, ghostMovePosition, originalKingPosition);
				
				testSquare.setPiece(testPieceSpot);
				board.setChessBoardSquare(testSquare, ghostMovePosition);
			}
		}
		
		// goes in here if the king has no valid moves of its own to get out of check
		if(!kingHasMoves)
		{
			for(int i = 0; i < allyTeam.size(); i++)
			{
				Piece allyPiece = allyTeam.get(i);
				
				if(whitePlayerTurn())
				{
					possibleMovesForPiece(allyPiece, team.getPiecePositionFromWhiteTeam(allyPiece));
				}
				else
				{
					possibleMovesForPiece(allyPiece, team.getPiecePositionFromBlackTeam(allyPiece));
				}
				
				for(int j = 0; j < allyPiece.getPossibleMoves().size() && checkMate; j++)
				{
					if(whitePlayerTurn())
					{
						Position originalPiecePosition = team.getPiecePositionFromWhiteTeam(allyPiece);
						possibleMovesForPiece(allyPiece, originalPiecePosition);
						
						Position ghostMovePosition = allyPiece.getPossibleMoves().get(j);
						int ghostX = ghostMovePosition.getPositionX();
						int ghostY = ghostMovePosition.getPositionY();
						
						Piece testPieceSpot = board.getChessBoardSquare(ghostX, ghostY).getPiece();
						Square testSquare = board.getChessBoardSquare(ghostX, ghostY);
						
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 1 WHITE");
//						board.printBoard();
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 2 WHITE");
						
						movePiece(allyPiece, originalPiecePosition, ghostMovePosition);
//						System.out.println(allyPiece.getPieceType() + " PIECE TYPE");
//						System.out.println(originalPiecePosition + " POSITION OF PIECE");
//						System.out.println(ghostMovePosition + " GHOST POSITION");
//						System.out.println(allyPiece.getPossibleMoves());
//						System.out.println(isKingInCheck(whiteKingPosition) + " WHITE STATUS");
//						
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 3 WHITE");
//						board.printBoard();
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 4 WHITE");
						
						if(isKingInCheck(whiteKingPosition))
						{
							checkMate = true;
						}
						else
						{
							checkMate = false;
						}
						
						movePiece(allyPiece, ghostMovePosition, originalPiecePosition);
						
						testSquare.setPiece(testPieceSpot);
						board.setChessBoardSquare(testSquare, ghostMovePosition);
					}
					else
					{
//						System.out.println("BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM BLACK TEAM ");
//						System.out.println(team.getBlackTeamSize() + " size of black team!");
//						System.out.println(allyPiece.getPieceType() + " I AM THIS TYPE");
//						System.out.println(allyPiece.getPieceColor() + " I SHOULD BE BLACK");
//						System.out.println(team.getPiecePositionFromBlackTeam(allyPiece) + " THIS IS MY POSITION");
//						System.out.println(allyPiece.getPossibleMoves() + " THESE ARE MY POSSIBLE MOVES");
						
						Position originalPiecePosition = team.getPiecePositionFromBlackTeam(allyPiece);
						possibleMovesForPiece(allyPiece, originalPiecePosition);
						
						Position ghostMovePosition = allyPiece.getPossibleMoves().get(j);
						
						int ghostX = ghostMovePosition.getPositionX();
						int ghostY = ghostMovePosition.getPositionY();
						
						Piece testPieceSpot = board.getChessBoardSquare(ghostX, ghostY).getPiece();
						Square testSquare = board.getChessBoardSquare(ghostX, ghostY);
						
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 1 BLACK");
//						board.printBoard();
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 2 BLACK");
						
						movePiece(allyPiece, originalPiecePosition, ghostMovePosition);
//						System.out.println(allyPiece.getPieceType() + " PIECE TYPE");
//						System.out.println(originalPiecePosition + " POSITION OF PIECE");
//						System.out.println(ghostMovePosition + " GHOST POSITION");
//						System.out.println(allyPiece.getPossibleMoves());
//						System.out.println(isKingInCheck(blackKingPosition) + " BLACK STATUS!??!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!??!-----------------------?!?!?!?!??!?!?!?!?!??!?!?!?");
//						
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 3 BLACK");
//						board.printBoard();
//						System.out.println("\n------------------------------------------------------------------------------------------- CHECK 4 BLACK");
						
						if(isKingInCheck(blackKingPosition))
						{
							checkMate = true;
						}
						else
						{
							checkMate = false;
						}
						
						movePiece(allyPiece, ghostMovePosition, originalPiecePosition);
						
						testSquare.setPiece(testPieceSpot);
						board.setChessBoardSquare(testSquare, ghostMovePosition);
					}
				}
			}
		}
		else
		{
			System.out.println("\nSuggested moves to get the " + allyColor + " king out of Check move the king to:");
			
			for(int i = 0; i < outOfCheckMovesForKing.size(); i++)
			{
				System.out.print(" " + coordinateToPosition(outOfCheckMovesForKing.get(i).getPositionX(), outOfCheckMovesForKing.get(i).getPositionY()));
			}
			System.out.println();
		}
		
		if(allyPiecesThatSaveKing.size() >= 1)
		{
			System.out.println("Pieces that can save the " + allyColor + " king:");
			for(int i = 0; i < allyPiecesThatSaveKing.size(); i++)
			{
				System.out.println(" " + allyPiecesThatSaveKing.get(i).getClass());
			}
			System.out.println();
		}
		if(outOfCheckAllyMoves.size() >= 1)
		{
			System.out.println("Spots that your ally pieces can move to in order to save your king: ");
			
			for(int i = 0; i < outOfCheckAllyMoves.size(); i++)
			{
				System.out.print(" " + coordinateToPosition(outOfCheckAllyMoves.get(i).getPositionX(), outOfCheckAllyMoves.get(i).getPositionY()));
			}
		}
		
		return checkMate;
	}

	private void possibleMovesForPiece(Piece piece, Position positionCheck)
	{	
		piece.resetPossibleMoves();
		int x1 = positionCheck.getPositionX();
		int y1 = positionCheck.getPositionY();
		String pieceType = piece.getPieceType().toLowerCase();
		char column = (char) (x1 + 65);
		String row = ""+(y1+1);
		String position = column + row;		
		
		// ROOK
		if(pieceType.equals("r"))
		{
//			System.out.print("\nPossible moves for Rook at " + position + ": ");
			// origin to top
			topPossibleMoves(piece, positionCheck);
			
			// origin to bottom
			bottomPossibleMoves(piece, positionCheck);
			
			// origin to right
			rightPossibleMoves(piece, positionCheck);
			
			// origin to left
			leftPossibleMoves(piece, positionCheck);
		}		
		// BISHOP
		else if(pieceType.equals("b"))
		{
//			System.out.print("\nPossible moves for Bishop at " + position + ": ");
			// origin to top left
			topLeftPossibleMoves(piece, positionCheck);
			
			// origin to top right
			topRightPossibleMoves(piece, positionCheck);
			
			// origin to bottom left
			bottomLeftPossibleMoves(piece, positionCheck);
			
			// origin to bottom right
			bottomRightPossibleMoves(piece, positionCheck);			
		}
		
		// QUEEN
		else if(pieceType.equals("q"))
		{
//			System.out.print("\nPossible moves for Queen at " + position + ": ");
			// origin to top left
			topLeftPossibleMoves(piece, positionCheck);
			
			// origin to top
			topPossibleMoves(piece, positionCheck);
			
			// origin to top right
			topRightPossibleMoves(piece, positionCheck);
			
			// origin to the right
			rightPossibleMoves(piece, positionCheck);
			
			// origin to bottom right
			bottomRightPossibleMoves(piece, positionCheck);
			
			// origin to bottom
			bottomPossibleMoves(piece, positionCheck);
			
			// origin to bottom left
			bottomLeftPossibleMoves(piece, positionCheck);
			
			// origin to the left
			leftPossibleMoves(piece, positionCheck);
		}
		
		// KNIGHT
		else if(pieceType.equals("n"))
		{
//			System.out.print("\nPossible moves for Knight at " + position + ": ");
			
			// top left
			knightTopLeftPossibleMove(piece, positionCheck);
			
			// top top left
			knightTopTopLeftPossibleMove(piece, positionCheck);
			
			// top top right
			knightTopTopRightPossibleMove(piece, positionCheck);
			
			// top right
			knightTopRightPossibleMove(piece, positionCheck);
			
			// bottom right
			knightBottomRightPossibleMove(piece, positionCheck);
			
			// bottom bottom right
			knightBottomBottomRightPossibleMove(piece, positionCheck);
			
			// bottom bottom left
			knightBottomBottomLeftPossibleMove(piece, positionCheck);
			
			// bottom left
			knightBottomLeftPossibleMove(piece, positionCheck);
		}
		
		// KING
		else if(pieceType.equals("k"))
		{
//			System.out.print("\nPossible moves for King at " + position + ": ");
			
			// top left
			kingTopLeftPossibleMove(piece, positionCheck);
			
			// top
			kingTopPossibleMove(piece, positionCheck);
			
			// top right
			kingTopRightPossibleMove(piece, positionCheck);
			
			// right
			kingRightPossibleMove(piece, positionCheck);
			
			// bottom right
			kingBottomRightPossibleMove(piece, positionCheck);
			
			// bottom
			kingBottomPossibleMove(piece, positionCheck);
			
			// bottom left
			kingBottomLeftPossibleMove(piece, positionCheck);
			
			// left
			kingLeftPossibleMove(piece, positionCheck);
		}
		
		// PAWN
		else if(pieceType.equals("p"))
		{
			if(piece.getPieceColor().equals(white))
			{
				whitePawnPossibleMove(piece, positionCheck);
			}
			else
			{
				blackPawnPossibleMove(piece, positionCheck);
			}
		}
	}
	
	private String coordinateToPosition(int x1, int y1)
	{	
		char column = (char) (x1 + 65);
		String row = ""+(y1+1);
		String position = column + row;
		return position;
	}

	private void getPiecePath(int x1, int y1, int x2, int y2)
	{
		int verticleMovement = y2 - y1;
		int horizontalMovement = x2 - x1;
		int upRightMovement = verticleMovement - horizontalMovement;                
		int diagonalMovement = verticleMovement + horizontalMovement;
		int mathCheck = 0;

		if(verticleMovement != mathCheck && (horizontalMovement == mathCheck))
		{                        
			if(y2 > y1)
			{
				for(int i = y1+1; i <= y2; i++)
				{			
					System.out.print(board.getChessBoardSquare(x1, i).getPiece().getPieceType() + " ");					
				}
			}
			else
			{
				for(int i = y1-1; i >= y2; i--)
				{			
					System.out.print(board.getChessBoardSquare(x1, i).getPiece().getPieceType()	+ " ");
				}
			}
		}

		if (horizontalMovement != mathCheck && (verticleMovement == mathCheck)) 
		{
			if (x2 > x1) 
			{
				for (int i = x1 + 1; i <= x2; i++) 
				{
					System.out.print(board.getChessBoardSquare(i, y1).getPiece().getPieceType()	+ " ");
				}
			} 
			else 
			{
				for (int i = x1 - 1; i >= x2; i--) 
				{
					System.out.print(board.getChessBoardSquare(i, y1).getPiece().getPieceType() + " ");
				}
			}
		}

		if (diagonalMovement == mathCheck) 
		{
			if ((x2 < x1) && (y2 > y1)) 
			{
				int y = y1 + 1;
				for (int x = x1 - 1; x >= x2; x--, y++) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			} 
			else 
			{
				int y = y1 - 1;
				for (int x = x1 + 1; x <= x2; x++, y--) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			}
		}

		if (upRightMovement == mathCheck || (verticleMovement == horizontalMovement)) 
		{
			if ((x2 > x1) && (y2 > y1)) 
			{
				int y = y1 + 1;
				for (int x = x1 + 1; x <= x2; x++, y++) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			} 
			else
			{
				int y = y1 - 1;
				for (int x = x1 - 1; x >= x2; x--, y--) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			}
		}
	}
	
	private boolean otherPieceExistsInPath(int x1, int y1, int x2, int y2) 
	{
		boolean moveCompletable = true;

		int verticleMovement = y2 - y1;
		int horizontalMovement = x2 - x1;
		int upRightMovement = verticleMovement - horizontalMovement;
		int diagonalMovement = verticleMovement + horizontalMovement;
		int mathCheck = 0;

		if (verticleMovement != mathCheck && (horizontalMovement == mathCheck)) 
		{
			moveCompletable = (y2 > y1) ? verticleMovementPathCheck(x1, y1, y2) : verticleMovementPathCheck(x1, y2, y1);
		}

		if (horizontalMovement != mathCheck && (verticleMovement == mathCheck)) 
		{
			moveCompletable = (x2 > x1) ? horizontalMovementPathCheck(y1, x1, x2) : horizontalMovementPathCheck(y1, x2, x1);
		}

		if (diagonalMovement == mathCheck) 
		{
			moveCompletable = (x2 < x1) && (y2 > y1) ? diagonalMovementPathCheckBottomRightToTopLeft(y1, x1, x2) : diagonalMovementPathCheckBottomRightToTopLeft(y2, x2, x1);
		}

		if (upRightMovement == mathCheck || (verticleMovement == horizontalMovement)) 
		{
			moveCompletable = (x2 > x1) && (y2 > y1) ? diagonalMovementPathCheckBottomLeftToTopRight(y1, x1, x2) : diagonalMovementPathCheckBottomLeftToTopRight(y2, x2, x1);
		}

		return moveCompletable;
	}	
	
	private boolean verticleMovementPathCheck(int x1, int y1, int y2) 
	{
		boolean moveCompletable = true;

		for (int i = y1 + 1; moveCompletable && (i < y2); i++) 
		{
			if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}
	
	private boolean horizontalMovementPathCheck(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;

		for (int i = x1 + 1; moveCompletable && (i < x2); i++) 
		{
			if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}
	
	private boolean diagonalMovementPathCheckBottomRightToTopLeft(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;
		int y = y1 + 1;
		for (int x = x1 - 1; moveCompletable && (x > x2); x--, y++) 
		{
			if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}

		return moveCompletable;
	}
	
	private boolean diagonalMovementPathCheckBottomLeftToTopRight(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;

		int y = y1 + 1;
		for (int x = x1 + 1; moveCompletable && (x < x2); x++, y++) 
		{
			if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}	

	private void topPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(y1 >= 0 && y1 < 7)
		{				
			boolean pieceNotFound = true;

			for (int i = y1 + 1; pieceNotFound && (i < maxHeight) && (board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor()); i++)
			{
//				System.out.print(" " + coordinateToPosition(x1, i));
				Position newMove = new Position(x1, i);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}			
	}
	
	private void rightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(x1 >= 0 && x1 < 7)
		{
			boolean pieceNotFound = true;

			for (int i = x1 + 1; pieceNotFound && (i < maxWidth) && (board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor()); i++)
			{
//				System.out.print(" " + coordinateToPosition(i, y1));
				Position newMove = new Position(i, y1);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}
	
	private void bottomPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(y1 >= 1 && y1 <= 7)
		{				
			boolean pieceNotFound = true;
			for (int i = y1 - 1; pieceNotFound && (i >= 0) && (board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor()); i--)
			{
//				System.out.print(" " + coordinateToPosition(x1, i));
				Position newMove = new Position(x1, i);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					}
				}
			}
		}
	}
	
	private void leftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(x1 >= 1 && x1 <= 7)
		{
			boolean pieceNotFound = true;

			for (int i = x1 - 1; pieceNotFound && (i >= 0) && (board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor()); i--)
			{
//				System.out.print(" " + coordinateToPosition(i, y1));
				Position newMove = new Position(i, y1);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}
	
	private void topLeftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			boolean pieceNotFound = true;

			int y = y1 + 1;
			for (int x = x1 - 1; pieceNotFound && (x >= 0) && (y < maxHeight) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x--, y++) 
			{
//				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}	
	
	private void topRightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 <= 6))
		{
			boolean pieceNotFound = true;
			
			int y = y1 + 1;
			for (int x = x1 + 1; pieceNotFound && (x < maxWidth) && (y < maxHeight) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x++, y++) 
			{
//				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}	
	
	private void bottomRightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 6 && y1 <= maxHeight))
		{
			boolean pieceNotFound = true;
			
			int y = y1 - 1;
			for (int x = x1 + 1; pieceNotFound && (x < maxWidth) && (y >= 0) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x++, y--) 
			{
//				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}
	
	private void bottomLeftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			boolean pieceNotFound = true;
			
			int y = y1 - 1;
			for (int x = x1 - 1; pieceNotFound && (x >= 0) && (y >= 0) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x--, y--) 
			{
//				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
//						System.out.print("*");
					} 
				}
			}
		}
	}
	
	private void knightTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 2 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1-2, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-2, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-2, y1+1)+"*");
					Position newMove = new Position(x1-2, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-2, y1+1));
				Position newMove = new Position(x1-2, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightTopTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 5))
		{
			if(board.getChessBoardSquare(x1-1, y1+2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1+2).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-1, y1+2)+"*");
					Position newMove = new Position(x1-1, y1+2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-1, y1+2));
				Position newMove = new Position(x1-1, y1+2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightTopTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 < 7 && y1 <= 5))
		{
			if(board.getChessBoardSquare(x1+1, y1+2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1+2).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+1, y1+2)+"*");
					Position newMove = new Position(x1+1, y1+2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+1, y1+2));
				Position newMove = new Position(x1+1, y1+2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 5 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1+2, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+2, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+2, y1+1)+"*");
					Position newMove = new Position(x1+2, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+2, y1+1));
				Position newMove = new Position(x1+2, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 5 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+2, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+2, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+2, y1-1)+"*");
					Position newMove = new Position(x1+2, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+2, y1-1));
				Position newMove = new Position(x1+2, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightBottomBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 2) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1-2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1-2).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+1, y1-2)+"*");
					Position newMove = new Position(x1+1, y1-2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+1, y1-2));
				Position newMove = new Position(x1+1, y1-2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightBottomBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 2) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1-2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1-2).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-1, y1-2)+"*");
					Position newMove = new Position(x1-1, y1-2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-1, y1-2));
				Position newMove = new Position(x1-1, y1-2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void knightBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 2 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-2, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-2, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-2, y1-1)+"*");
					Position newMove = new Position(x1-2, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-2, y1-1));
				Position newMove = new Position(x1-2, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-1, y1+1)+"*");
					Position newMove = new Position(x1-1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-1, y1+1));
				Position newMove = new Position(x1-1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingTopPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1, y1+1)+"*");
					Position newMove = new Position(x1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1, y1+1));
				Position newMove = new Position(x1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+1, y1+1)+"*");
					Position newMove = new Position(x1+1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+1, y1+1));
				Position newMove = new Position(x1+1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+1, y1)+"*");
					Position newMove = new Position(x1+1, y1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+1, y1));
				Position newMove = new Position(x1+1, y1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1+1, y1-1)+"*");
					Position newMove = new Position(x1+1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1+1, y1-1));
				Position newMove = new Position(x1+1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingBottomPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1, y1-1)+"*");
					Position newMove = new Position(x1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1, y1-1));
				Position newMove = new Position(x1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-1, y1-1)+"*");
					Position newMove = new Position(x1-1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-1, y1-1));
				Position newMove = new Position(x1-1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void kingLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1).getPiece().getPieceColor() != piece.getPieceColor())
				{
//					System.out.print(" " + coordinateToPosition(x1-1, y1)+"*");
					Position newMove = new Position(x1-1, y1);
					piece.setPossibleMoves(newMove);
				}
			}
			else
			{
//				System.out.print(" " + coordinateToPosition(x1-1, y1));
				Position newMove = new Position(x1-1, y1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void whitePawnPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		boolean pieceFound = false;
		
		// checks the 2 spots above it because it hasn't moved yet
		if(y1 == 1)
		{
			for (int i = y1+1; !pieceFound && (i <= 3); i++)
			{
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceFound = true;
				}
				else
				{
					Position newMove = new Position(x1, i);
					piece.setPossibleMoves(newMove);
					pieceFound = false;
				}
			}
		}
		// checks one spot ahead because it has moved
		else if(y1 <= 6)
		{
			int x = x1;
			int y = y1+1;
			if(board.getChessBoardSquare(x, y).getPiece().getPieceType().equals(board.getBlankPiece().getPieceType()))
			{
				Position newMove = new Position(x1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
		
		// top left check
		if((x1 >= 1 && x1 < maxWidth) && (y1 >= 0 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()
				&& board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
			{
				Position newMove = new Position(x1-1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
		// top right check
		if((x1 >= 0 && x1 <= 6) && (y1 >= 0 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()
				&& board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
			{
				Position newMove = new Position(x1+1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	
	private void blackPawnPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		boolean pieceFound = false;
		
		// checks the 2 spots above it because it hasn't moved yet
		if(y1 == 6)
		{
			for (int i = y1-1; !pieceFound && (i >= 4); i--)
			{
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceFound = true;
				}
				else
				{
					Position newMove = new Position(x1, i);
					piece.setPossibleMoves(newMove);
					pieceFound = false;
				}
			}
		}
		// checks one spot ahead because it has moved
		else if(y1 >= 1)
		{
			if(board.getChessBoardSquare(x1, y1-1).getPiece().getPieceType().equals(board.getBlankPiece().getPieceType()))
			{
				Position newMove = new Position(x1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
		
		// bottom left check
		if((x1 >= 1 && x1 < maxWidth) && (y1 >= 1 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()
				&& board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
			{
				Position newMove = new Position(x1-1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
		// bottom right check
		if((x1 >= 0 && x1 <= 6) && (y1 >= 1 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()
				&& board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
			{
				Position newMove = new Position(x1+1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
}