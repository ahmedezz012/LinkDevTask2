package com.ezz.linkdevtask.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezz.linkdevtask.app.activities.Activity_News;
import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.listeners.NewsClickListener;
import com.ezz.linkdevtask.app.MyApplication;
import com.ezz.linkdevtask.R;

/**
 * Created by A.Ezz on 12/6/2016.
 */

public class Navigation_Listview_Adapter extends RecyclerView.Adapter<Navigation_Listview_Adapter.Viewholder> {

    private Context context;
    private LayoutInflater layoutInflater;
    String Texts[];
    int Drawables[]={R.drawable.news_icon,R.drawable.map_icon,R.drawable.events_icon,R.drawable.leadership_icon,R.drawable.language};

    public Navigation_Listview_Adapter(Context context) {

        if(context!=null) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        Texts = context.getResources().getStringArray(R.array.Navigation_Items_Texts);
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.navigation_list_item,null);

        Viewholder viewholder = new Viewholder(v);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.txt_navigation_item.setText(Texts[position]);
        holder.img_navigation_item.setImageResource(Drawables[position]);

        NewsClickListener NewItemClickListener = new NewsClickListener() {
            @Override
            public void OnItemClick(int pos) {
                if(pos==4)
                {
                    SharedPreferences force_pref = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    String language = force_pref.getString(MyApplication.FORCE_LOCAL, "");
                    if(language.equals(Constants.arabic))
                    {
                        language="";
                    }
                    else
                        language=Constants.arabic;
                    MyApplication.updateLanguage(context,language);
                    Intent refresh = new Intent((context),Activity_News.class);
                    context.startActivity(refresh);
                    ((Activity_News)context).recreate();
                }
            }
        };

        holder.setItemClickListener(NewItemClickListener);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_navigation_item;
        ImageView img_navigation_item;
        NewsClickListener NewItemClickListener;
        public Viewholder(View itemView) {
            super(itemView);
             txt_navigation_item = (TextView) itemView.findViewById(R.id.txt_navigation_item);
             img_navigation_item = (ImageView) itemView.findViewById(R.id.img_navigation_item);
             itemView.setOnClickListener(this);
        }
        public void setItemClickListener(NewsClickListener itemClickListener) {
            this.NewItemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            this.NewItemClickListener.OnItemClick(this.getLayoutPosition());
        }
    }
}
