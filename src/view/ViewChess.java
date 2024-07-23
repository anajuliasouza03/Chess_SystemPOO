package view;

import javax.swing.*;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewChess extends JFrame {

    private static final long serialVersionUID = 1L;
    private ChessMatch chessMatch;
    private JButton[][] buttons = new JButton[8][8];
    private ChessPiece selectedPiece;
    private Position selectedPosition;
    private JTextArea capturedPiecesTextArea;
    private JLabel turnLabel;
    private JLabel waitingLabel;
    private JLabel statusLabel;
    
    private Map<String, ImageIcon> pieceImages;

    public ViewChess() {
    	setResizable(false);
        setTitle("Chess");
        setSize(1000, 800);  // Increased width to accommodate side panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        chessMatch = new ChessMatch();

        // Create and add the main board panel
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        getContentPane().add(boardPanel, BorderLayout.CENTER);

        // Create and add the side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        getContentPane().add(sidePanel, BorderLayout.EAST);

        initializeButtons(boardPanel);
        initializeSidePanel(sidePanel);
        
        initializePieceImages();

        updateBoard();
        updateSidePanel();
    }

    private void initializeButtons(JPanel boardPanel) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(70, 70));  
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }
    }


    private void initializeSidePanel(JPanel sidePanel) {
        capturedPiecesTextArea = new JTextArea(10, 20);
        capturedPiecesTextArea.setEditable(false);
        capturedPiecesTextArea.setFont(new Font("Monospaced", Font.BOLD, 20));
        sidePanel.add(new JScrollPane(capturedPiecesTextArea));

        turnLabel = new JLabel("JOGADA: 1");
        sidePanel.add(turnLabel);

        waitingLabel = new JLabel("ESPERANDO JOGADA DAS [BRANCAS]");
        sidePanel.add(waitingLabel);
        
        statusLabel = new JLabel("");  // For displaying check/checkmate status
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sidePanel.add(statusLabel);
    }

    private void updateBoard() {
        ChessPiece[][] pieces = chessMatch.getPieces();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setText("");  // Clear any existing text
                if (pieces[i][j] != null) {
                    String key = pieces[i][j].getColor().toString().toLowerCase() + "_" + pieces[i][j].getClass().getSimpleName().toLowerCase();
                    buttons[i][j].setIcon(pieceImages.get(key));
                } else {
                    buttons[i][j].setIcon(null);  // No image for empty squares
                }
                buttons[i][j].setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY  : Color.DARK_GRAY); 
            }
        }
    }

    private void updateSidePanel() {
        List<String> whiteCaptured = chessMatch.getCapturedPieces(chess.Color.WHITE)
                .stream()
                .map(p -> p.getClass().getSimpleName().charAt(0) + "")
                .collect(Collectors.toList());
        List<String> blackCaptured = chessMatch.getCapturedPieces(chess.Color.BLACK)
                .stream()
                .map(p -> p.getClass().getSimpleName().charAt(0) + "")
                .collect(Collectors.toList());

        capturedPiecesTextArea.setText("PEÇAS CAPTURADAS:\n");
        capturedPiecesTextArea.append("BRANCAS: " + whiteCaptured + "\n");
        capturedPiecesTextArea.append("PRETAS: " + blackCaptured + "\n");

        turnLabel.setText("JOGADA: " + chessMatch.getTurn());
        waitingLabel.setText("ESPERANDO JOGADA DAS [" + (chessMatch.getCurrentPlayer() == chess.Color.WHITE ? "BRANCAS" : "PRETAS") + "]");

        if (chessMatch.getCheckMate()) {
            statusLabel.setText("CHECKMATE!");
            statusLabel.setForeground(Color.RED);
            disableBoard();
        } else if (chessMatch.getCheck()) {
            statusLabel.setText("CHECK!");
            statusLabel.setForeground(Color.ORANGE);
        } else {
            statusLabel.setText("");
        }
    }
    
    private void disableBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void initializePieceImages() {
        pieceImages = new HashMap<>();
        pieceImages.put("black_king", loadImage("black_king.png"));
        pieceImages.put("white_king", loadImage("white_king.png"));
        pieceImages.put("black_queen", loadImage("black_queen.png"));
        pieceImages.put("white_queen", loadImage("white_queen.png"));
        pieceImages.put("black_rook", loadImage("black_rook.png"));
        pieceImages.put("white_rook", loadImage("white_rook.png"));
        pieceImages.put("black_bishop", loadImage("black_bishop.png"));
        pieceImages.put("white_bishop", loadImage("white_bishop.png"));
        pieceImages.put("black_knight", loadImage("black_knight.png"));
        pieceImages.put("white_knight", loadImage("white_knight.png"));
        pieceImages.put("black_pawn", loadImage("black_pawn.png"));
        pieceImages.put("white_pawn", loadImage("white_pawn.png"));
    }

    private ImageIcon loadImage(String fileName) {
        java.net.URL imgURL = getClass().getResource("/resources/" + fileName);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image originalImage = originalIcon.getImage();
            
            // Redimensionar a imagem para metade do tamanho original
            Image scaledImage = originalImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Couldn't find file: " + fileName);
            return null;
        }
    }


    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Position position = new Position(row, col);
            if (selectedPiece == null) {
                if (chessMatch.getBoard().piece(position) != null) {
                    selectedPiece = (ChessPiece) chessMatch.getBoard().piece(position);
                    selectedPosition = position;
                }
            } else {
                try {
                    ChessPosition sourcePosition = ChessPosition.fromPosition(selectedPosition);
                    ChessPosition targetPosition = ChessPosition.fromPosition(position);
                    chessMatch.performChessMove(sourcePosition, targetPosition);
                    
                    if (chessMatch.getPromoted() != null) {
                        String type = askUserForPieceType();
                        chessMatch.replacePromotedPiece(type);
                    }
                    
                    updateBoard();
                    updateSidePanel();
                } catch (ChessException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                selectedPiece = null;
                selectedPosition = null;
            }
        }
        private String askUserForPieceType() {
            String[] options = {"B", "N", "R", "Q"};
            int response = JOptionPane.showOptionDialog(null, "Escolha a peça para promoção", "Promoção de Peão",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[3]);
            return options[response];
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewChess view = new ViewChess();
            view.setVisible(true);
        });
    }
}
