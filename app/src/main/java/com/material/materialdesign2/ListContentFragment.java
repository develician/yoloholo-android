package com.material.materialdesign2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.materialdesign2.LocalDB.DBManager;
import com.material.materialdesign2.Models.Plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



//        return inflater.inflate(R.layout.item_list, null);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;


    }

    public void reloadData() {
//        Fragment currentFragment = getFragmentManager().findFragmentByTag("list_frg");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(this).attach(this).commit();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView avatar;
        public TextView title;
        public TextView description;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
//            avatar = (ImageView) itemView.findViewById(R.id.list_avatar);
            title = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

        }




    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {


        private int LENGTH;
//        private final String[] mPlaces;
        private ArrayList<String> titles = new ArrayList<>();
        private ArrayList<String> dates = new ArrayList<>();
//        private final String[] mPlaceDesc;
//        private final Drawable[] mPlaceAvators;
        public ContentAdapter(Context context) {
            DBManager manager = new DBManager(context);
            ArrayList<Plan> plans = manager.selectAll();

           for(int i = 0;i < plans.size();i++) {
                titles.add(i, plans.get(i).getTitle());
                dates.add(i, plans.get(i).getDateJSONArray());
           }

           LENGTH = plans.size();




//            Resources resources = context.getResources();
//            mPlaces = resources.getStringArray(R.array.places);
//            mPlaceDesc = resources.getStringArray(R.array.place_desc);
//            TypedArray a = resources.obtainTypedArray(R.array.place_avator);
//            mPlaceAvators = new Drawable[a.length()];
//            for (int i = 0;i < mPlaceAvators.length;i++){
//                mPlaceAvators[i] = a.getDrawable(i);

//            }
//            a.recycle();
        }





        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DBManager manager = new DBManager(holder.title.getContext());
            ArrayList<Plan> plans = manager.selectAll();



//            holder.avatar.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
//            holder.title.setText(mPlaces[position % mPlaces.length]);
            holder.title.setText(plans.get(position).getTitle());
//            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
            try {
                JSONObject json = new JSONObject(plans.get(position).getDateJSONArray());
                JSONArray jsonArray = json.optJSONArray("dateJSONArray");

                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                Calendar calendar = Calendar.getInstance();

                Date dateOfDepart = format.parse(jsonArray.get(0).toString());
                calendar.setTime(dateOfDepart);
                int departYear = calendar.get(Calendar.YEAR);
                int departMonth = calendar.get(Calendar.MONTH) + 1;
                int departDay = calendar.get(Calendar.DATE);

                Date dateOfArrive = format.parse(jsonArray.get(jsonArray.length() - 1).toString());
                calendar.setTime(dateOfArrive);
                int arriveYear = calendar.get(Calendar.YEAR);
                int arriveMonth = calendar.get(Calendar.MONTH) + 1;
                int arriveDay = calendar.get(Calendar.DATE);

                holder.description.setText(departYear + "/" + departMonth + "/" + departDay + "~" + arriveYear + "/" + arriveMonth + "/" + arriveDay);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }


    }



}
