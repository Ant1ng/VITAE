package com.example.vitae;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.collection.ArraySet;

public class Adapter extends BaseAdapter implements ListAdapter {
    private ArraySet<String> list;
    private Entries entries;
    private String id;
    private Context context;
    private boolean answer = true;

    public Adapter(String id, ArraySet<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.id = id;
    }

    public Adapter(String id, Entries entries, Context context) {
        this.list = entries.getEntryList();
        this.entries = entries;
        this.context = context;
        this.id = id;
    }

    public void removeAnswer() {
        answer = false;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.valueAt(pos);
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
            view = inflater.inflate(R.layout.question_entry, null);
            if (!answer) {
                view = inflater.inflate(R.layout.answer_entry, null);
            }
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.valueAt(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.removeAt(position);
                entries.writeFile();
                notifyDataSetChanged();
            }
        });
        if (answer) {
            Button answerBtn = (Button)view.findViewById(R.id.answer_btn);
            answerBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //do something
                    Intent intent = new Intent(v.getContext(), Answers.class);
                    intent.putExtra("question", list.valueAt(position));
                    intent.putExtra("id", id);
                    v.getContext().startActivity(intent);
                }
            });
        }

        return view;
    }
}
