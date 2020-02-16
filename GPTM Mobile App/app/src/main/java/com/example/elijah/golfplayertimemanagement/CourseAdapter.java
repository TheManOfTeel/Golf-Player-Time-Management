package com.example.elijah.golfplayertimemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CourseAdapter extends ArrayAdapter<GolfCourse> {

    public CourseAdapter(@NonNull Context context, ArrayList<GolfCourse> golfCourses) {
        super(context, 0, golfCourses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View converView, ViewGroup parent){
        if(converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(
                    R.layout.course_spinner, parent, false
            );
        }
        TextView textView = converView.findViewById(R.id.course);

        GolfCourse currentCourse = getItem(position);

        if(currentCourse != null) {
            textView.setText(currentCourse.getName());
        }

        return converView;

    }
}
