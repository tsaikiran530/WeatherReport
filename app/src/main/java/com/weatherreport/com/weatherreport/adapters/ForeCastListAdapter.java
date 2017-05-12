package com.weatherreport.com.weatherreport.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherreport.com.weatherreport.R;
import com.weatherreport.com.weatherreport.model.DayForecast;
import com.weatherreport.com.weatherreport.network.WeatherHttpClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ForeCastListAdapter.
 */
public class ForeCastListAdapter extends ArrayAdapter<DayForecast> {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");
    private List<DayForecast> dataSet;
    Context mContext;

    public ForeCastListAdapter(Context context,List<DayForecast> objects) {
        super(context, R.layout.list_item);
        this.dataSet = objects;
        this.mContext=context;
    }



    @Override
    public int getCount() {
        return dataSet.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayForecast dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.forecastDayName = (TextView) convertView.findViewById(R.id.forecast_day);
            viewHolder.forecastTemp = (TextView) convertView.findViewById(R.id.forecast_temp);
            viewHolder.forecastImg = (ImageView) convertView.findViewById(R.id.forecast_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Date d = new Date();
        Calendar gc =  new GregorianCalendar();
        gc.setTime(d);
        gc.add(GregorianCalendar.DAY_OF_MONTH, position);

        viewHolder.forecastDayName.setText(sdf.format(gc.getTime()));
        viewHolder.forecastTemp.setText( (int) (dataModel.forecastTemp.min - 273.15) + "-" + (int) (dataModel.forecastTemp.max - 273.15)+" \u2103" );

        LoadImageAsyncTask task = new LoadImageAsyncTask(viewHolder);
        task.execute(new String[]{dataModel.weather.currCondition.getIcon()});
        return convertView;
    }


    // View lookup cache
    private static class ViewHolder {
        TextView forecastDayName;
        TextView forecastTemp;
        ImageView forecastImg;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private class LoadImageAsyncTask extends AsyncTask<Object, Void, byte[]> {

        ViewHolder holder;

        public LoadImageAsyncTask(ViewHolder holder) {
            this.holder=holder;
        }

        @Override
        protected byte[] doInBackground(Object... params) {
            byte[] data = null;

            try {

                data = ( (new WeatherHttpClient()).getImage(params[0]+".png"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if (bytes != null) {
                Bitmap img = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
               this.holder.forecastImg.setImageBitmap(img);
            }
        }
    }


    @Override
    public DayForecast getItem(int position) {
        return dataSet.get(position);
    }
}
