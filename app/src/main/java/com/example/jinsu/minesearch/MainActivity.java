package com.example.jinsu.minesearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recycler)
    RecyclerView mainRecycler;
    @BindView(R.id.btn_restart)
    Button btnRestart;

    int[][] mine;
    RequestManager glide;
    MineListAdapter adapter;
    @BindView(R.id.btn_answer)
    Button btnAnswer;

    public static Boolean IFDEF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        glide = Glide.with(this);
        init();

    }

    private void init() {
        mine = new int[10][10];
        IFDEF = false;
        adapter = new MineListAdapter(mine, glide, new MineListAdapter.DialogListener() {
            @Override
            public void setDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("실패")
                        .setMessage("지뢰를 밟았습니다. 다시 하시겠어요?")
                        .setPositiveButton("다시하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IFDEF = false;
                                setList();
                                return;
                            }
                        });
                builder.create();
                builder.show();
            }
        });
        mainRecycler.setHasFixedSize(true);
        mainRecycler.setLayoutManager(new GridLayoutManager(this, 10));
        mainRecycler.setAdapter(adapter);
        setList();

        btnRestart.setOnClickListener(v -> {
            IFDEF = false;
            setList();
        });

        btnAnswer.setOnClickListener(v -> {
            IFDEF = true;
            adapter.notifyDataSetChanged();
        });

    }


    /* 10개의 지뢰를 랜덤하게 배치 */
    private void setList() {
        Random rand = new Random();
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                mine[i][j]=0;
            }
        }

        int cnt = 0;
        int one = 0;
        int two = 0;
        while (cnt < 10) {
            one = rand.nextInt(10);
            two = rand.nextInt(10);
            if (mine[one][two] != 1) {
                mine[one][two] = 1;
                cnt++;
            }
        }
        adapter.notifyDataSetChanged();

    }



}
