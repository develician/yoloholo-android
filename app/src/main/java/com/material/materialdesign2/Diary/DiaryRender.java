package com.material.materialdesign2.Diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.material.materialdesign2.LocalDB.DBManagerDiary;
import com.material.materialdesign2.Models.Diary;
import com.material.materialdesign2.R;

import org.w3c.dom.Text;

public class DiaryRender extends AppCompatActivity {

    private Editor editor;
    private String mSerialized;
    private String mSerializedHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_render);


        DBManagerDiary dbManagerDiary = new DBManagerDiary(DiaryRender.this);
        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("id"), Toast.LENGTH_SHORT).show();
        Diary diary = dbManagerDiary.selectById(Integer.parseInt(getIntent().getStringExtra("id")));

        Editor editor = (Editor) findViewById(R.id.diaryRenderer);
        mSerializedHtml = editor.getContentAsHTML(diary.getContent());
        editor.render(mSerializedHtml);


//        TextView textView = (TextView) findViewById(R.id.lblHtmlRendered);
//        textView.setText(mSerializedHtml);
    }
}
