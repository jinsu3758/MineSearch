package com.example.jinsu.minesearch;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineListAdapter extends RecyclerView.Adapter<MineListAdapter.ViewHolder> {

    private RequestManager glide;
    private int one,two;
    private int[][] list;
    private DialogListener dialogListener;

    public interface DialogListener
    {
        void setDialog();
    }

    public MineListAdapter(int[][] list, RequestManager glide, DialogListener dialogListener) {
        this.list = list;
        this.glide = glide;
        this.dialogListener = dialogListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine, parent, false);
        MineListAdapter.ViewHolder viewHolder = new MineListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        one = position/10;
        two = position%10;
        if(MainActivity.IFDEF) {
            if (list[one][two] != 1) {
                holder.mine_btn.setText(String.valueOf(getCount(one, two)));
            } else {
                glide.load(R.drawable.bomb).into(holder.mine_im);
            }
        }
        else{
            holder.mine_btn.setOnClickListener(v -> {
                one = position/10;
                two = position%10;
                if (list[one][two] != 1) {
                    holder.mine_btn.setText(String.valueOf(getCount(one, two)));
                    holder.mine_btn.setEnabled(false);
                    holder.mine_btn.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.disabled_mine));
                } else {
                    glide.load(R.drawable.bomb).into(holder.mine_im);
                    holder.mine_btn.setEnabled(false);
                    holder.mine_btn.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.disabled_mine));
                    dialogListener.setDialog();
                }
            });
        }
    }

    /* 현재 위치의 주변 지뢰의 갯수를 찾아주는 함수 */
    private int getCount(int row, int col)
    {
        int cnt=0;
        cnt += isMine(row-1,col-1);
        cnt += isMine(row-1,col);
        cnt += isMine(row-1,col+1);
        cnt += isMine(row,col-1);
        cnt += isMine(row,col+1);
        cnt += isMine(row+1,col-1);
        cnt += isMine(row+1,col);
        cnt += isMine(row+1,col+1);

        return cnt;
    }

    /* 지뢰 유무 구별 함수 */
    private int isMine(int row, int col)
    {
        if(row <0 || col <0 || row > 9 || col > 9)
            return 0;
        return list[row][col];
    }

    @Override
    public int getItemCount() {
        return list.length * list[1].length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.mine_btn)
        Button mine_btn;
        @BindView(R.id.mine_im)
        ImageView mine_im;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
