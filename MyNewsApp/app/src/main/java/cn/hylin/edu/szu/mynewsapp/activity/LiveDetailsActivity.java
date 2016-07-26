package cn.hylin.edu.szu.mynewsapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hylin.edu.szu.mynewsapp.R;
import cn.hylin.edu.szu.mynewsapp.model.LiveResponse;

public class LiveDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTags)
    TextView tvTags;
    @BindView(R.id.tvImtro)
    TextView tvImtro;
    @BindView(R.id.tvIngredients)
    TextView tvIngredients;
    @BindView(R.id.tvBurden)
    TextView tvBurden;
    @BindView(R.id.tvSteps)
    TextView tvSteps;
    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_details);
        ButterKnife.bind(this);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("菜谱详情");
        ab.setDefaultDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");//标题
        String tags = intent.getStringExtra("tags");// 功能
        String imtro = intent.getStringExtra("imtro"); // 介绍
        String ingredients = intent.getStringExtra("ingredients");// 原料
        String burden = intent.getStringExtra("burden");//配料
        List<LiveResponse.Result.Data.Steps> steps = (List<LiveResponse.Result.Data.Steps>) intent.getSerializableExtra("steps");

        tvTitle.setText(title);
        tvTags.setText(tags);
        tvImtro.setText(imtro);
        tvIngredients.setText(ingredients);
        tvBurden.setText(burden);

        for (int i = 0; i < steps.size(); i ++) {
            TextView tvStep = new TextView(this);
            ImageView ivStep = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            ivStep.setLayoutParams(params);
            tvStep.setLayoutParams(params);
            tvStep.setTextSize(18);
            tvStep.setTextColor(Color.BLACK);
            tvStep.setBackgroundColor(getResources().getColor(R.color.background));

            tvStep.setText(steps.get(i).getStep());
            String url = steps.get(i).getImg().replace("\\","");
            Glide.with(this).load(url).asBitmap().into(ivStep);

            root.addView(ivStep);
            root.addView(tvStep);

        }


    }
}
