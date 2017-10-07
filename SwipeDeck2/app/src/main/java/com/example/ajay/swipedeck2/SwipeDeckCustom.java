package com.example.ajay.swipedeck2;

import com.example.ajay.swipedeck.SwipeDeck;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class was included in the SwipeDeck2 Github repo (https://github.com/aaronbond/SwipeDeck2).
 * It can be used to customize the animation of cards when they are dragged. I have not edited this
 * class in any way.
 */
public class SwipeDeckCustom extends SwipeDeck {

    public SwipeDeckCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void animateCardPosition(View card, int position) {
        int offset = position * 15;
        float scale = (card.getMeasuredWidth() - offset) / (float) card.getMeasuredWidth();
        card.animate()
                .setDuration(ANIMATION_DURATION)
                .y(getPaddingTop() + offset)
                .scaleX(scale)
                .scaleY(scale)
                .alpha(1.0f);
    }
}
