package com.example.afeka.tryinghisshit.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.example.afeka.tryinghisshit.GameEngine;
import com.example.afeka.tryinghisshit.R;


public class Cell extends BaseCell implements View.OnClickListener , View.OnLongClickListener{

    public Cell( Context context , int x , int y ){
        super(context);

        setPosition(x,y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().click( getXPos(), getYPos() );
    }

    @Override
    public boolean onLongClick(View v) {
        GameEngine.getInstance().flag( getXPos() , getYPos() );

        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Minesweeper" , "Cell::onDraw");
        drawButton(canvas);

        if( isFlagged() ){
            drawFlag(canvas);
        }else if( isRevealed() && isBomb() && !isClicked() ){
            drawNormalBomb(canvas);
        }else {
            if( isClicked() ){
                if( getValue() == -1 ){
                    drawBombExploded(canvas);
                }else {
                    drawNumber(canvas);
                }
            }else{
                drawButton(canvas);
            }
        }
    }

    private void drawBombExploded(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_explosion);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawFlag( Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_flag);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawButton(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_normal_button);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNormalBomb(Canvas canvas ){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_mine);
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }

    private void drawNumber( Canvas canvas ){
        Drawable drawable = null;

        switch (getValue() ){
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_empty_cell);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.small_8);
                break;
        }

        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);
    }


}