package com.wang.calcudemo;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.calcudemo.databinding.FragmentQuestionBinding;
import com.wang.calcudemo.databinding.FragmentTitleBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final MyViewModel myViewModel=  new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.generatorQuestion();
        myViewModel.isWin=false;
        final FragmentQuestionBinding binding;
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder answerStr=new StringBuilder();
        View.OnClickListener onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("QuestionFragment","ID:"+view.getId());
                switch (view.getId()){
                        case R.id.button12:
                            answerStr.append(0);
                            break;
                        case R.id.button2:
                            answerStr.append(1);
                            break;
                        case R.id.button3:
                            answerStr.append(2);
                            break;
                        case R.id.button4:
                            answerStr.append(3);
                            break;
                        case R.id.button5:
                            answerStr.append(4);
                            break;
                        case R.id.button6:
                            answerStr.append(5);
                            break;
                        case R.id.button7:
                            answerStr.append(6);
                            break;
                        case R.id.button8:
                            answerStr.append(7);
                            break;
                        case R.id.button9:
                            answerStr.append(8);
                            break;
                        case R.id.button10:
                            answerStr.append(9);
                            break;
                        case R.id.button11:
                            answerStr.setLength(0);
                            break;
                    }
                    if (answerStr.length()==0){
                        binding.textView9.setText(getString(R.string.user_answer));
                    }else{
                        binding.textView9.setText(answerStr.toString());
                    }
            }
        };
        binding.button12.setOnClickListener(onClickListener);//0
        binding.button2.setOnClickListener(onClickListener);//1
        binding.button3.setOnClickListener(onClickListener);//2
        binding.button4.setOnClickListener(onClickListener);//3
        binding.button5.setOnClickListener(onClickListener);//4
        binding.button6.setOnClickListener(onClickListener);//5
        binding.button7.setOnClickListener(onClickListener);//6
        binding.button8.setOnClickListener(onClickListener);//7
        binding.button9.setOnClickListener(onClickListener);//8
        binding.button10.setOnClickListener(onClickListener);//9
        binding.button11.setOnClickListener(onClickListener);//CLAER

        binding.button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerStr.length()>0&&Integer.parseInt(answerStr.toString()) == myViewModel.getAnswer().getValue().intValue()){
                    myViewModel.correct();
                    answerStr.setLength(0);
                    binding.textView9.setText(getString(R.string.user_answer));
                    myViewModel.generatorQuestion();
                }else{
                    NavController controller= Navigation.findNavController(view);
                    if (myViewModel.isWin){
                        myViewModel.save();
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                    }else{
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }

                }
            }
        });

        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }
}