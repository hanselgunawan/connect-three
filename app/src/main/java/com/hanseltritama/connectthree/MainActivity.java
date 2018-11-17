package com.hanseltritama.connectthree;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int turn = 0;
    private int player = 1;
    private int[][] board = new int[3][3];

    public void putRedToken(ImageButton imageButton) {
        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.red));
        imageButton.setAlpha(1f);
    }

    public void putYellowToken(ImageButton imageButton) {
        imageButton.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
        imageButton.setAlpha(1f);
    }

    public int getXCoord(int imgBtnId) {
        switch (imgBtnId) {
            case 0:
            case 1:
            case 2: return 0;
            case 3:
            case 4:
            case 5: return 1;
            case 6:
            case 7:
            case 8: return 2;
            default: return 0;
        }
    }

    public int getYCoord(int imgBtnId) {
        switch (imgBtnId) {
            case 0:
            case 3:
            case 6: return 0;
            case 1:
            case 4:
            case 7: return 1;
            case 2:
            case 5:
            case 8: return 2;
            default: return 0;
        }
    }

    public boolean checkCol(int[][] board, int x_coord) {
        return (board[0][x_coord] == board[1][x_coord]) && (board[1][x_coord] == board[2][x_coord]);
    }

    public boolean checkRow(int[][] board, int y_coord) {
        return (board[y_coord][0] == board[y_coord][1]) && (board[y_coord][1] == board[y_coord][2]);
    }

    public boolean checkDiagonalLeft(int[][] board, int x_coord, int y_coord) {
        return (x_coord == y_coord) && ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]));
    }

    public boolean checkDiagonalRight(int[][] board, int x_coord, int y_coord) {
        return (y_coord == 2 - x_coord) && ((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]));
    }

    public boolean checkWin(int[][] board, int x_coord, int y_coord) {
        return (checkCol(board, x_coord) || checkRow(board, y_coord) || checkDiagonalLeft(board, x_coord, y_coord) || checkDiagonalRight(board, x_coord, y_coord));
    }

    public void showAlertDialog(int player, int turn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        builder.setTitle(turn < 9 ? R.string.win_title : R.string.tie_title)
               .setMessage(turn < 9 ? String.format(getResources().getString(R.string.win_message), player) : getString(R.string.tie_message))
               .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                        recreate();
                   }
               })
               .show();
    }

    public void onBoardClick(View view) {
        int imgBtnId;
        int x_coord;
        int y_coord;
        ImageButton imageButton = (ImageButton) view;
        String resEntryName = imageButton.getResources().getResourceEntryName(imageButton.getId());

        imgBtnId = Integer.parseInt(resEntryName.substring(resEntryName.length() - 1));

        x_coord = getXCoord(imgBtnId);
        y_coord = getYCoord(imgBtnId);

        if(board[y_coord][x_coord] != 0) {
            Toast.makeText(this, "Try another place.", Toast.LENGTH_SHORT).show();
            return;
        }

        board[y_coord][x_coord] = player;

        if(player == 1) putRedToken(imageButton);
        else putYellowToken(imageButton);

        if(turn >= 4 && checkWin(board, x_coord, y_coord)) {
            showAlertDialog(player, turn);
            return;
        }

        turn++;
        player = turn % 2 + 1;

        if(turn == 9) showAlertDialog(player, turn);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
