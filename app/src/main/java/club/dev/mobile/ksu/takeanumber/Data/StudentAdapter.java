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
    private List<Student> studentsList = new ArrayList<>();

    public StudentAdapter (@NonNull Context context, ArrayList<Student> list) {
        super(context, 0, list);
        mContext = context;
        studentsList = list;
    }

    @NonNull
    @Override
    public View getView (int position , @Nullable View converView , @NonNull ViewGroup parent) {
        View listItem = converView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.student_view , parent , false);

        Student currentStudent = studentsList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.Name);
        name.setText(currentStudent.getName());

        TextView dateTime = (TextView) listItem.findViewById(R.id.dateTime);
        String tempTime = Long.toString(currentStudent.getDateTime());
        name.setText(tempTime);

        TextView status = (TextView) listItem.findViewById(R.id.currentStatus);
        name.setText(currentStudent.getStatus());

        return listItem;
    }
}
