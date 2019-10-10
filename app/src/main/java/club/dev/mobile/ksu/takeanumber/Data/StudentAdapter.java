package club.dev.mobile.ksu.takeanumber.Data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.R;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context mContext;
    private List<Student> studentsList;

    public StudentAdapter (@NonNull Context context) {
        super(context, R.layout.student_item);
        mContext = context;
    }

    public void setStudentList(List<Student> students) {
        studentsList = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView (int position , @Nullable View listItem , @NonNull ViewGroup parent) {
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.student_item, parent , false);

        Student currentStudent = studentsList.get(position);

        TextView name = listItem.findViewById(R.id.student_name);
        name.setText(currentStudent.getName());

        TextView dateTime = listItem.findViewById(R.id.student_datetime);
        Date date = new Date(currentStudent.getDateTime());
        String dateString = DateFormat.getDateTimeInstance().format(date);
        dateTime.setText(dateString);

        TextView status = listItem.findViewById(R.id.student_status);
        String tempString;

        if (currentStudent.getStatus() == 0) {
            tempString = getContext().getString(R.string.student_status_waiting);
        }
        else if (currentStudent.getStatus() == 1) {
            tempString = getContext().getString(R.string.student_status_helping);
        }
        else {
            tempString = getContext().getString(R.string.student_status_unknown, currentStudent.getStatus());
        }
        status.setText(tempString);

        TextView estimatedTime = listItem.findViewById(R.id.student_estimated_time);
        estimatedTime.setText(getContext().getString(R.string.estimated_time, currentStudent.getTime()));

        return listItem;
    }

    @Override
    public int getCount() {
        return studentsList != null ? studentsList.size() : 0;
    }
}
