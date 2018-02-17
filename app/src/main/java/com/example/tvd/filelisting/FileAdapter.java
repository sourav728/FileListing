package com.example.tvd.filelisting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TVD on 2/15/2018.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {
    private ArrayList<GetSetValues>arrayList = new ArrayList<>();
    private Context context;

    public FileAdapter(Context context, ArrayList<GetSetValues>arrayList)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public FileAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileAdapter.FileHolder holder, int position) {
        final GetSetValues getSetValues = arrayList.get(position);
        holder.filename.setText(getSetValues.getFilename());
        holder.filecount.setText(String.valueOf(getSetValues.getFilecount()));

        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setSelected(getSetValues.isSelected());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    getSetValues.setSelected(true);
                }
                else getSetValues.setSelected(false);
            }
        });
        //holder.checkbox.setChecked(getSetValues.isSelected());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FileHolder extends RecyclerView.ViewHolder {
        TextView filename,filecount;
        CheckBox checkbox;
        public FileHolder(View itemView) {
            super(itemView);
            filename = (TextView) itemView.findViewById(R.id.txt_filename);
            filecount = (TextView) itemView.findViewById(R.id.txt_file_count);
            checkbox = (CheckBox) itemView.findViewById(R.id.file_check);
        }
    }
}
