package com.app.quizz.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quizz.Model.QuizzListModel;
import com.app.quizz.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder> {

    private List<QuizzListModel> quizzListModels;
    private OnItemClickedListner onItemClickedListner;

    public void setQuizzListModels(List<QuizzListModel> quizzListModels) {
        this.quizzListModels = quizzListModels;
    }

    public QuizListAdapter(OnItemClickedListner onItemClickedListner){
        this.onItemClickedListner = onItemClickedListner;
    }

    @NonNull
    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_quizz, parent , false);
        return new QuizListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListViewHolder holder, int position) {
        QuizzListModel model = quizzListModels.get(position);
        holder.title.setText(model.getTitle());
        Glide.with(holder.itemView).load(model.getImage()).into(holder.quizImage);
    }

    @Override
    public int getItemCount() {
        if (quizzListModels == null){
            return 0;
        }else
        {        return quizzListModels.size();
        }
    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView quizImage;

        private ConstraintLayout constraintLayout;

        public QuizListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.quizzTitleList);
            quizImage = itemView.findViewById(R.id.quizzImgList);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            constraintLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            onItemClickedListner.onItemClick(getAdapterPosition());

        }
    }

    public interface OnItemClickedListner{
        void onItemClick(int position);

    }
}
