package club.dev.mobile.ksu.takeanumber.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.R;

public class HelpSessionAdapter extends ArrayAdapter<HelpSession> {
    private Context context;
    private int listItemLayout;
    private List<HelpSession> helpSessionsList;

    public HelpSessionAdapter(@NonNull Context context, int resource){
        super(context, resource);
        this.context = context;
        this.listItemLayout = resource;
    }

    public void setHelpSessionsList(List<HelpSession> helpSessions) {
        helpSessionsList = helpSessions;
        notifyDataSetChanged();
    }

    @NonNull
    //@Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(listItemLayout, parent, false);
        }
        HelpSession session = helpSessionsList.get(position);
        TextView tv = convertView.findViewById(R.id.text1);
        tv.setText(session.getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return helpSessionsList != null ? helpSessionsList.size() : 0;
    }
}