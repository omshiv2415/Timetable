package time.com.timetable.DB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import time.com.timetable.R;

/**
 * Created by VIRALKUMAR on 09/04/2016. omshiv
 */
public class ListDataAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ListDataAdapter(Context context, int resource) {

        super(context, resource);

    }


    static class LayoutHandler {

        TextView MCODE, STIME, FTIME, SDATE;

    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final LayoutHandler layoutHandler;
        if (row == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.MCODE = (TextView) row.findViewById(R.id.textModuleCode);
            layoutHandler.STIME = (TextView) row.findViewById(R.id.textStartTime);
            layoutHandler.FTIME = (TextView) row.findViewById(R.id.textFinishTime);
            layoutHandler.SDATE = (TextView) row.findViewById(R.id.textViewShiftDate);

            row.setTag(layoutHandler);

        } else {

            layoutHandler = (LayoutHandler) row.getTag();


        }
        final DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.MCODE.setText(dataProvider.getMcode());
        layoutHandler.STIME.setText(dataProvider.getMstartTime());
        layoutHandler.FTIME.setText(dataProvider.getMfinishTime());
        layoutHandler.SDATE.setText(dataProvider.getmDate());


        return row;
    }


}
