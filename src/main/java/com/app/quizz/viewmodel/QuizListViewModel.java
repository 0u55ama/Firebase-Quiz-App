package com.app.quizz.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.quizz.Model.QuizzListModel;
import com.app.quizz.repository.QuizListRepository;

import java.util.List;

public class QuizListViewModel extends ViewModel implements QuizListRepository.onFirestoreTaskComplete {

    private MutableLiveData<List<QuizzListModel>> quizListLiveData = new MutableLiveData<>();

    private QuizListRepository repository = new QuizListRepository(this);

    public MutableLiveData<List<QuizzListModel>> getQuizListLiveData() {
        return quizListLiveData;
    }

    public QuizListViewModel(){
        repository.getQuizData();
    }

    @Override
    public void quizDataLoaded(List<QuizzListModel> quizzListModels) {
        quizListLiveData.setValue(quizzListModels);

    }

    @Override
    public void onError(Exception e) {

        Log.d("QuizERROR","onError: " + e.getMessage());

    }
}
