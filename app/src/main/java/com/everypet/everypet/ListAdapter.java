package com.everypet.everypet;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.everypet.everypet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private List<ToDoData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(Context context, List<ToDoData> _oData)
    {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_oData = _oData;
        nListCnt = m_oData.size();
    }
    public ListAdapter(Context context)
    {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_oData = new ArrayList<>();
        nListCnt = m_oData.size();
    }
    public void setData(List<ToDoData> _oData){
        m_oData=_oData;
        notifyDataSetChanged();
    }

    public void addData(ToDoData data){
        m_oData.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return m_oData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return m_oData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
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
