package club.dev.mobile.ksu.takeanumber.Data;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.R;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context mContext;
    private List<Student> studentsList;

    public StudentAdapter (@NonNull Context context, ArrayList<Student> list) {
        super(context, R.layout.student_view, list);
        mContext = context;
        studentsList = list;
    }

    @NonNull
    @Override
    public View getView (int position , @Nullable View listItem , @NonNull ViewGroup parent) {
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.student_view , parent , false);

        Student currentStudent = studentsList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.student_name);
        name.setText(currentStudent.getName());

        TextView dateTime = (TextView) listItem.findViewById(R.id.student_datetime);
        String tempTime = Long.toString(currentStudent.getDateTime());
        dateTime.setText(tempTime);

        TextView status = (TextView) listItem.findViewById(R.id.student_status);
        status.setText(Integer.toString(currentStudent.getStatus()));

        return listItem;
    }
}
