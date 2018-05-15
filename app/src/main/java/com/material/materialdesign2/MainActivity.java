package com.material.materialdesign2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.material.materialdesign2.Diary.DiaryEditor;
import com.material.materialdesign2.Diary.DiaryRender;
import com.material.materialdesign2.LocalDB.DBManager;
import com.material.materialdesign2.Models.Plan;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSelectDateListener{

    private DrawerLayout mDrawerLayout;
    public ViewPager viewPager;
    private Fragment currentFragment;
    private DBManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DBManager(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null) {
            VectorDrawableCompat indicator = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;

            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Hello SnackBar!", Snackbar.LENGTH_SHORT).show();
//            }
//        });

//        Image for menuCircleButton

        ImageView fabContent = new ImageView(this);
        fabContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_circle_black_24dp));

//        SubActionButtons

//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        ImageView subActionButtonContent = new ImageView(this);
//        subActionButtonContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
//        SubActionButton subButton1 = itemBuilder.setContentView(subActionButtonContent).build();
//

        Calendar calendar = Calendar.getInstance();

        DatePickerBuilder builder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.RANGE_PICKER)
                .date(calendar);

        DatePicker rangePicker = builder.build();


        Button addPlanButton = new Button(this);
        addPlanButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
        addPlanButton.setBackground(getResources().getDrawable(R.drawable.sub_button));
        addPlanButton.setText("일정 추가");
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
//                startActivity(intent);
                rangePicker.show();
            }
        });

        Button addDiaryButton = new Button(this);
        addDiaryButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
        addDiaryButton.setBackground(getResources().getDrawable(R.drawable.sub_button));
        addDiaryButton.setText("새 일기 쓰기");

        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        addPlanButton.setLayoutParams(tvParams);
        addDiaryButton.setLayoutParams(tvParams);

        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton menuCircleButton =
                new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(this)
                        .setTheme(FloatingActionButton.AUTOFILL_TYPE_NONE)
                        .setContentView(fabContent)
                        .setPosition(FloatingActionButton.SCROLLBAR_POSITION_DEFAULT)
                        .build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                                                                .addSubActionView(addPlanButton)
                                                                .addSubActionView(addDiaryButton)
                                                                .attachTo(menuCircleButton).build();
//        menuCircleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Hello SnackBar!", Snackbar.LENGTH_SHORT).show();
//            }
//        });

        addDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DBManager manager = new DBManager(MainActivity.this);
//
//                ArrayList<Plan> plans =  manager.selectAll();
//
//                for(int i = 0;i < plans.size();i++) {
////                    Toast.makeText(MainActivity.this, plans.get(i).getDateJSONArray(), Toast.LENGTH_LONG).show();
//                    try {
//                        JSONObject json = new JSONObject(plans.get(i).getDateJSONArray());
//                        JSONArray arrayList =  json.optJSONArray("dateJSONArray");
//                        Toast.makeText(MainActivity.this, arrayList.get(0) + "", Toast.LENGTH_SHORT).show();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

//                DBManager manager = new DBManager(MainActivity.this);
//                manager.removeAll();

                Intent intent = new Intent(MainActivity.this, DiaryEditor.class);
                startActivity(intent);






            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(new ListContentFragment(), "list_frg");
//        Adapter adapter = new Adapter(getSupportFragmentManager());
//        Adapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new ListContentFragment(), "최근 일정");
//        adapter.addFragment(new CardContentFragment(), "일기");
//        adapter.addFragment(new TileContentFragment(), "가계부");

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if(position < 0 || position >= 3) {
                return null;
            }

            switch (position) {
                case 0:
                    currentFragment = new ListContentFragment();
                    break;
                case 1:
                    currentFragment = new CardContentFragment();
                    break;
                case 2:
                    currentFragment = new TileContentFragment();
                    break;
            }
            return currentFragment;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "최근 일정";
                case 1:
                    return "일기";
                case 2:
                    return "가계부";
            }
            return super.getPageTitle(position);
        }
    }

    @Override
    public void onSelect(List<Calendar> calendars) {
//        Stream.of(calendars).forEach(calendar ->
//                Toast.makeText(getApplicationContext(),
//                        calendar.getTime().toString(),
//                        Toast.LENGTH_SHORT).show());


        ArrayList<String> stringCalendars = new ArrayList<>();
        for(int i =0;i<calendars.size();i++){
            stringCalendars.add(i, calendars.get(i).getTime().toString());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("dateJSONArray", new JSONArray(stringCalendars));
            String dateJSONArrayStr = json.toString();



//            DBManager manager = new DBManager(this);



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("여행의 제목을 입력해주세요!");

            final EditText inputTitle = new EditText(this);
            inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(inputTitle);

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Plan plan = new Plan();
                    plan.setTitle(inputTitle.getText().toString());
                    plan.setDateJSONArray(dateJSONArrayStr);
                    manager.insertData(plan);

                    viewPager.getAdapter().notifyDataSetChanged();
                    viewPager.setCurrentItem(0);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        } else if(id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewPager.getAdapter().notifyDataSetChanged();
    }


}
