package com.material.materialdesign2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.irshulx.Editor;
import com.material.materialdesign2.Diary.DiaryRender;
import com.material.materialdesign2.LocalDB.DBManagerDiary;
import com.material.materialdesign2.Models.Diary;

import java.util.ArrayList;

public class CardContentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


//        return inflater.inflate(R.layout.item_card, null);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter contentAdapter = new ContentAdapter(recyclerView.getContext());

        recyclerView.setAdapter(contentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        //        public TextView description;
        public Editor description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (Editor) itemView.findViewById(R.id.card_text);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, DiaryRender.class);
//                    intent.putExtra("id", getPosition());
//                    context.startActivity(intent);
//                }
//            });

        }
    }


    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {


        String mSerializedHtml = "";


        private int LENGTH;
        //        private final String[] mPlaces;
//        private final String[] mPlaceDesc;
        private final Drawable[] mPlacePictures;

        public ContentAdapter(Context context) {
            DBManagerDiary dbManagerDiary = new DBManagerDiary(context);
            ArrayList<Diary> diaries = dbManagerDiary.selectAll();

            Resources resources = context.getResources();
//            mPlaces = resources.getStringArray(R.array.places);
//            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);

            }
            a.recycle();
            LENGTH = diaries.size();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DBManagerDiary dbManagerDiary = new DBManagerDiary(holder.name.getContext());
            ArrayList<Diary> diaries = dbManagerDiary.selectAll();

            holder.name.setTag(diaries.get(position).getId());
            holder.name.setText(diaries.get(position).getTitle());
            holder.picture.setImageDrawable(mPlacePictures[0]);

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DiaryRender.class);
                    intent.putExtra("id", holder.name.getTag().toString());
                    context.startActivity(intent);
                }
            });

            holder.picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DiaryRender.class);
                    intent.putExtra("id", holder.name.getTag().toString());
                    context.startActivity(intent);
                }
            });


//            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
            Editor editor = new Editor(holder.name.getContext(), null);
            mSerializedHtml = editor.getContentAsHTML(diaries.get(position).getContent());


            holder.description.render(mSerializedHtml);

            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DiaryRender.class);
                    intent.putExtra("id", holder.name.getTag().toString());
                    context.startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
