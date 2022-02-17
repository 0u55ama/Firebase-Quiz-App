package com.app.quizz.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.app.quizz.Adapter.QuizListAdapter;
import com.app.quizz.Model.QuizzListModel;
import com.app.quizz.R;
import com.app.quizz.viewmodel.AuthViewModel;
import com.app.quizz.viewmodel.QuizListViewModel;

import java.util.List;


public class ListFragment extends Fragment implements QuizListAdapter.OnItemClickedListner {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private QuizListViewModel viewModel;
    private QuizListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
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

        recyclerView = view.findViewById(R.id.listQuizz);
        progressBar = view.findViewById(R.id.quizListprogressBar);
        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizListAdapter(this);

        recyclerView.setAdapter(adapter);

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizzListModel>>() {
            @Override
            public void onChanged(List<QuizzListModel> quizzListModels) {

                progressBar.setVisibility(View.GONE);
                adapter.setQuizzListModels(quizzListModels);
                adapter.notifyDataSetChanged();

            }
        });




    }

    @Override
    public void onItemClick(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action =
                ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        navController.navigate(action);

    }
}