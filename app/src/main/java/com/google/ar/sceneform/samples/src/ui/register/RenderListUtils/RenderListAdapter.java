package com.google.ar.sceneform.samples.src.ui.register.RenderListUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.google.ar.sceneform.samples.src.R;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;

import java.util.ArrayList;
import java.util.List;

public class RenderListAdapter extends BaseAdapter implements ListAdapter {
    private List<RenderListItem> list = new ArrayList<>();
    private Context context;


    public RenderListAdapter(List<RenderListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.renderlist_item, null);
        }

        //Handle TextView and display string from your list
        String itemName = list.get(position).getText1();
        String itemId = list.get(position).getText2();
        RGBColor color = list.get(position).color;

//        view.setBackgroundColor(color.getRGB());

        TextView listItemText1 = (TextView)view.findViewById(R.id.text1);
        listItemText1.setText(itemName);
        listItemText1.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));

        TextView listItemText2 = (TextView)view.findViewById(R.id.text2);
        listItemText2.setText(itemId);
        listItemText2.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));


        //Handle buttons and add onClickListeners
        Switch renderSwitch = (Switch) view.findViewById(R.id.renderSwitch);
        renderSwitch.setChecked(true);// eveything visible by default
        renderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   list.get(position).getObject().setVisibility(Object3D.OBJ_VISIBLE);
               } else{
                   list.get(position).getObject().setVisibility(Object3D.OBJ_INVISIBLE);
               }
            }
        });

        return view;
    }



}