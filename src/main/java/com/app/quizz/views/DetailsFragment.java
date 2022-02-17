package com.app.quizz.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.quizz.Model.QuizzListModel;
import com.app.quizz.R;
import com.app.quizz.viewmodel.QuizListViewModel;
import com.bumptech.glide.Glide;

import java.util.List;


public class DetailsFragment extends Fragment {

    private TextView title, difficulty, totalQuestions;
    private Button startQuizBtn;
    private NavController navController;
    private int position;
    private ProgressBar progressBar;
    private QuizListViewModel viewModel;
    private ImageView topicImg;
    private String quizId;
    private long totalQuesCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.detailsFragmenttitle);
        difficulty = view.findViewById(R.id.detailsFragmentDefficulty);
        totalQuestions = view.findViewById(R.id.detailsFragmentQues);
        startQuizBtn = view.findViewById(R.id.startQuizzBtn);
        progressBar = view.findViewById(R.id.detailsprogressBar);
        topicImg = view.findViewById(R.id.detailsFragmentImg);

        navController = Navigation.findNavController(view);

        position = DetailsFragmentArgs.fromBundle(getArguments()).getPosition();

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizzListModel>>() {
            @Override
            public void onChanged(List<QuizzListModel> quizzListModels) {
                QuizzListModel quiz = quizzListModels.get(position);
                difficulty.setText(quiz.getDifficulty());
                title.setText(quiz.getTitle());
                totalQuestions.setText(String.valueOf(quiz.getQuestions()));
                Glide.with(view).load(quiz.getImage()).into(topicImg);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                },2000);

                totalQuesCount = quiz.getQuestions();
                quizId = quiz.getQuizId();
            }
        });

        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DetailsFragmentDirections.ActionDetailsFragmentToQuizzFragment action=
                       DetailsFragmentDirections.actionDetailsFragmentToQuizzFragment();

               action.setQuizId(quizId);
               action.setTotalQuesCount(totalQuesCount);
               navController.navigate(action);
            }
        });


    }
}