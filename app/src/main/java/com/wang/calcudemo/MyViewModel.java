package com.wang.calcudemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

/**
 * Created by wang on 2021/2/25 0025.
 **/
public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private final static String KEY_HIGH_SCOPE="KEY_HIGH_SCOPE";
    private final static String KEY_LEFT_SCOPE="KEY_LEFT_SCOPE";
    private final static String KEY_RIGHT_SCOPE="KEY_RIGHT_SCOPE";
    private final static String KEY_OPERATOR="KEY_OPERATOR";
    private final static String KEY_ANSWER="KEY_ANSWER";
    private final static String KEY_CUREENT_SCOPE="KEY_CUREENT_SCOPE";
    private final static String SAVE_CALCU_DATA="SAVE_CALCU_DATA";
    public boolean isWin=false;

    public MyViewModel(@NonNull Application application,SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCOPE)){
            SharedPreferences shr=getApplication().getSharedPreferences(SAVE_CALCU_DATA, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCOPE,shr.getInt(KEY_HIGH_SCOPE,0));
            handle.set(KEY_LEFT_SCOPE,0);
            handle.set(KEY_RIGHT_SCOPE,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CUREENT_SCOPE,0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getHighScope(){
        return  handle.getLiveData(KEY_HIGH_SCOPE);
    }
    public MutableLiveData<Integer> getLeftScope(){
        return  handle.getLiveData(KEY_LEFT_SCOPE);
    }
    public MutableLiveData<Integer> getRightScope(){
        return  handle.getLiveData(KEY_RIGHT_SCOPE);
    }
    public MutableLiveData<String> getOperator(){
        return  handle.getLiveData(KEY_OPERATOR);
    }
    public MutableLiveData<Integer> getAnswer(){
        return  handle.getLiveData(KEY_ANSWER);
    }
    public MutableLiveData<Integer> getCurrentScope(){
        return  handle.getLiveData(KEY_CUREENT_SCOPE);
    }

   public void generatorQuestion(){
        int Level=20;
        Random random=new Random();
        int x= random.nextInt(20)+1;
        int y= random.nextInt(20)+1;
        //加法 和 减法各一半
        if (x%2 == 0){
            getOperator().setValue("+");
            if (x > y){
                getAnswer().setValue(x);
                getLeftScope().setValue(y);
                getRightScope().setValue(x-y);
            }else{
                getAnswer().setValue(y);
                getLeftScope().setValue(x);
                getRightScope().setValue(y-x);
            }
        }else{
            getOperator().setValue("-");
            if (x>y){
                getAnswer().setValue(x-y);
                getLeftScope().setValue(x);
                getRightScope().setValue(y);
            }else{
                getAnswer().setValue(y-x);
                getLeftScope().setValue(y);
                getRightScope().setValue(x);
            }
        }

    }

    public void  correct(){
        getCurrentScope().setValue(getCurrentScope().getValue()+1);
        if (getCurrentScope().getValue() > getHighScope().getValue()){
            getHighScope().setValue(getCurrentScope().getValue());
            isWin=true;
        }
    }

    public void save(){
        SharedPreferences shr=getApplication().getSharedPreferences(SAVE_CALCU_DATA,Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= shr.edit();
        editor.putInt(KEY_HIGH_SCOPE,getHighScope().getValue());
        editor.apply();

    }


}
