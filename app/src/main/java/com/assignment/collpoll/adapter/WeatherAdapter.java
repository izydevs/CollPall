package com.assignment.collpoll.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.collpoll.R;
import com.assignment.collpoll.Utils.Utils;
import com.assignment.collpoll.WeatherDatabase;
import com.assignment.collpoll.activity.MainActivity;
import com.assignment.collpoll.activity.WeatherDetailsActivity;
import com.assignment.collpoll.model.WeatherDetails;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private Context context;
    private List<WeatherDetails> myList;
    private AlertDialog.Builder builder;

    public WeatherAdapter(Context context, List<WeatherDetails> myList) {
        this.context = context;
        this.myList = myList;
    }

    public void updateNewList(List<WeatherDetails> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.cityName.setText(String.format(Utils.changekelvinToCelcius(myList.get(i).getTemp())+" in "+ myList.get(i).getCityName()));
        myViewHolder.maxMinTemp.setText(String.format(Utils.changekelvinToCelcius(myList.get(i).getMaxTemp())+"/" +Utils.changekelvinToCelcius(myList.get(i).getMinTemp())));
        myViewHolder.weatherCondition.setText(myList.get(i).getWeatherCondition());
        myViewHolder.lastUpdate.setText(Utils.getDate(myList.get(i).getDate()*1000));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city_name)
        TextView cityName;
        @BindView(R.id.temp_diff)
        TextView maxMinTemp;
        @BindView(R.id.weather_desc)
        TextView weatherCondition;
        @BindView(R.id.date)
        TextView lastUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WeatherDetailsActivity.class);
                    intent.putExtra("weather_details",(Serializable)myList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
            ImageView deleteData = itemView.findViewById(R.id.delete);
            deleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogBox(getAdapterPosition());
                }
            });
        }
    }

    private void showDialogBox(final int i) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setMessage("Do you want to delete weather data")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteWeatherDataFromRoom(myList.get(i));
                        myList.remove(i);
                        notifyDataSetChanged();
                        if (myList.size()==0){
                            ((MainActivity)context).checkListSize();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(myList.get(i).getCityName()+" weather");
        alert.show();
    }

    private void deleteWeatherDataFromRoom(final WeatherDetails weatherDetails) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(context);
                weatherDatabase.weatherDao().deleteWeatherDetails(weatherDetails);

            }
        };
        thread.start();
    }
}
