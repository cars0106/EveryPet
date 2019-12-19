package com.everypet.everypet;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.everypet.everypet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ToDoData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ToDoData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_todo, parent, false);
        }



        ImageView oImage = (ImageView)convertView.findViewById(R.id.kind);
        CheckBox oCheck = (CheckBox)convertView.findViewById(R.id.check);
        TextView oTextTime = (TextView) convertView.findViewById(R.id.time);
        TextView oTextWhat = (TextView) convertView.findViewById(R.id.what);
        TextView oTextDate = (TextView)convertView.findViewById(R.id.date);

        oTextTime.setText(m_oData.get(position).time);
        oTextWhat.setText(m_oData.get(position).what);
        oTextDate.setText(m_oData.get(position).date);

        return convertView;
    }
}
