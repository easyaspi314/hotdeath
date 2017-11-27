package com.smorgasbork.hotdeath;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class CardImageAdapter extends BaseAdapter
{
    private Context mContext;
    private int[]   m_cardIDs;
    private int[]   m_thumbIDs;

    public CardImageAdapter(Context c)
    {
        GameActivity ga = (GameActivity) c;

        // query the current card deck to see what cards are actually in use
        Game g = ga.getGame();
        CardDeck d = g.getDeck();
        Card[] cary = d.getCards();

        SparseBooleanArray usedIDs = new SparseBooleanArray();
        for (Card aCary : cary)
        {
            if (usedIDs.indexOfKey(aCary.getID()) >= 0)
            {
                continue;
            }

            usedIDs.put(aCary.getID(), true);
        }

        // go through all cards in order and add them to the array
        int[] cardids = ga.getCardIDs();

        Integer idx = 0;
        m_thumbIDs = new int[usedIDs.size()];
        m_cardIDs = new int[usedIDs.size()];
        for (Integer cardid : cardids)
        {
            if (usedIDs.indexOfKey(cardid) >= 0)
            {
                m_cardIDs[idx] = cardid;
                m_thumbIDs[idx] = ((GameActivity) c).getCardImageID(cardid);
                idx++;
            }
        }

        mContext = c;
    }

    public int[] getCardIDs()
    {
        return m_cardIDs;
    }

    @Override
    public int getCount()
    {
        return m_thumbIDs.length;
    }

    /* TODO: I don't think this is the intended usage of these... */
    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null)
        {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(m_thumbIDs[position]);
        return imageView;
    }

}