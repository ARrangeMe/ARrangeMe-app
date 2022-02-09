package com.google.ar.sceneform.samples.src.ui.ListUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.google.ar.sceneform.samples.src.R;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsPresenter;
import com.google.ar.sceneform.samples.src.ui.jobs.JobsPresenterImpl;

public class CustomListAdapter extends BaseAdapter implements ListAdapter {
    private List<CustomListItem> list = new ArrayList<>();
    private Context context;
    private JobsPresenter jobsPresenter;


    public CustomListAdapter(List<CustomListItem> list, Context context) {
        this.list = list;
        this.context = context;
        jobsPresenter = new JobsPresenterImpl();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.jobslist_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText1 = (TextView)view.findViewById(R.id.text1);
        listItemText1.setText(list.get(position).text1);
        TextView listItemText2 = (TextView)view.findViewById(R.id.text2);
        listItemText2.setText(list.get(position).text2);

        //Handle buttons and add onClickListeners
        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteJob(list.get(position).text2);
                list.remove(position); //or some other task
                notifyDataSetChanged();

            }
        });


        return view;
    }

    private void deleteJob(String jobId){

        new AsyncTask<String, String, Boolean>() {
            // potential for memory leak if this task lives longer than the main thread. Unlikely.
            @Override
            protected Boolean doInBackground(String... jobId) {
                jobsPresenter.deleteJob(jobId[0]);
                return true;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                //pipe the result to a new activity
                if(result.equals(false)) {
                    //there was a problem. TODO: print to logs or something
                    return;
                }
            }
        }.execute(jobId);
    }
}