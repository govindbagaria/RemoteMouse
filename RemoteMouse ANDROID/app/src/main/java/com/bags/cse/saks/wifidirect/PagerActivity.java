package com.bags.cse.saks.wifidirect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PagerActivity extends Activity{

    ViewPager mViewPager;
    Button button;
    LinearLayout linearLayout,parent;
    CustomPagerAdapter mCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);

        linearLayout=(LinearLayout)findViewById(R.id.linearlayout);
        parent=(LinearLayout)findViewById(R.id.parent);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(PagerActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });


        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mCustomPagerAdapter);
    }

    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container,int position) {

            if(position==0){
                View itemView = mLayoutInflater.inflate(R.layout.first_frag, container, false);
                LinearLayout linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout_background);
                linearLayout.setBackgroundResource(R.drawable.pager1);
                container.addView(itemView);
                return itemView;
            }

            if(position==1){
                View itemView = mLayoutInflater.inflate(R.layout.second_frag, container, false);
                LinearLayout linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout_background);
                linearLayout.setBackgroundResource(R.drawable.pageraa);
                container.addView(itemView);
                return itemView;
            }
            if(position==2){
                View itemView = mLayoutInflater.inflate(R.layout.third_frag, container, false);
                LinearLayout linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout_background);
                container.addView(itemView);
                linearLayout.setBackgroundResource(R.drawable.pagerbb);
                return itemView;
            }
            if(position==3){
                View itemView = mLayoutInflater.inflate(R.layout.fourth_frag, container, false);
                LinearLayout linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout_background);
                linearLayout.setBackgroundResource(R.drawable.pagerc);
                container.addView(itemView);

                return itemView;
            }
            if(position==4){
                View itemView = mLayoutInflater.inflate(R.layout.fourth_frag, container, false);
                LinearLayout linearLayout=(LinearLayout)itemView.findViewById(R.id.linearlayout_background);
                linearLayout.setBackgroundResource(R.drawable.pagerd);
                container.addView(itemView);

                return itemView;
            }
            return  true;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
