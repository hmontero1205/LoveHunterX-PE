package com.lovehunterx.screens.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

public class TicTacToe extends Group {
    private int mode;
    private BoardPiece[][] pieces;
    private boolean turn;

    public TicTacToe(String start) {
        Image back = new Image(new Texture("tttboard.png"));

        if (start.equals("true")) {
            mode = 1;
            LoveHunterX.getCurrentScreen().displayNotification("Make your move!");
            turn = true;
        } else {
            mode = 2;
            LoveHunterX.getCurrentScreen().displayNotification("Waiting for opponent to make a move...");
        }

        Stage stage = LoveHunterX.getCurrentScreen().getStage();
        back.setPosition(stage.getWidth() / 2 - back.getWidth() / 2, stage.getHeight() / 2 - back.getHeight() / 2);

        addActor(back);

        pieces = new BoardPiece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoardPiece piece = new BoardPiece(i, j);
                piece.setX(back.getX() + 90 + i * (Assets.TTT_PIECE_1.getWidth() + 23));
                piece.setY(back.getY() + 40 + j * (Assets.TTT_PIECE_1.getHeight() + 20));
                addActor(piece);

                pieces[i][j] = piece;
            }
        }
    }

    public void requestTurn() {
        turn = true;
    }

    public void place(int mode, int row, int col) {
        pieces[row][col].setMode(mode);
    }

    class BoardPiece extends Actor {
        private int mode;
        private int i;
        private int j;

        public BoardPiece(int i, int j) {
            setBounds(0, 0, Assets.TTT_PIECE_1.getWidth(), Assets.TTT_PIECE_1.getHeight());
            this.i = i;
            this.j = j;

            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (mode != 0 || !turn) {
                        return;
                    }

                    Packet packet = new Packet("choose_move");
                    packet.addData("mode", String.valueOf(TicTacToe.this.mode));
                    packet.addData("row", String.valueOf(BoardPiece.this.i));
                    packet.addData("col", String.valueOf(BoardPiece.this.j));
                    LoveHunterX.getConnection().send(packet);

                    mode = TicTacToe.this.mode;

                    turn = false;
                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            if (mode == 1) {
                batch.draw(Assets.TTT_PIECE_1, getX(), getY());
            } else if (mode == 2) {
                batch.draw(Assets.TTT_PIECE_2, getX(), getY());
            }
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

    }

}
