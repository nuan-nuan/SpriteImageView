package com.biztrology.lokobee.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.biztrology.lokobee.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    ArrayList<Bitmap> mBmps = new ArrayList<Bitmap>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageview);

        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.mipmap.image10);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.mipmap.image11);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.mipmap.image12);
        Bitmap avatar4 = BitmapFactory.decodeResource(getResources(), R.mipmap.image13);
        Bitmap avatar5 = BitmapFactory.decodeResource(getResources(), R.mipmap.image14);


        mBmps.add(avatar1);
        mBmps.add(avatar2);
        mBmps.add(avatar3);
        mBmps.add(avatar4);
        mBmps.add(avatar5);

        //填充bitmap到需要显示的view上去
        mImageView.setImageBitmap(GroupFaceUtil.createGroupFace(GroupFaceUtil.FACE_TYPE_NOW, this, mBmps.toArray(new Bitmap[mBmps.size()])));
    }
}
