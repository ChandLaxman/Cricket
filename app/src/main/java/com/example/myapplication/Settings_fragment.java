package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Settings_fragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.teams, container, false);

        return rootview;
    }
}
//    public void filterPOPUP(final Intent context) {
//        TextView submit;
//        TextView from,to;
//
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.filter_bottom_sheet);
//
//        submit = (TextView) dialog.findViewById(R.id.submit);
//        from = (TextView) dialog.findViewById(R.id.from);
//        to = (TextView) dialog.findViewById(R.id.to);
//        final Calendar c = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//        String date = dateFormat.format(c.getTime());
//        from.setText(date);
//        to.setText(date);
//
//
//        from.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final Calendar c = Calendar.getInstance();
//                fYear = c.get(Calendar.YEAR);
//                fMonth = c.get(Calendar.MONTH);
//                fDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                from.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                globalValue.putString("from_receipt",from.getText().toString());
//
//                            }
//                        }, fYear, fMonth, fDay);
//                datePickerDialog.show();
//
//            }
//        });
//        to.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final Calendar c = Calendar.getInstance();
//                toYear = c.get(Calendar.YEAR);
//                toMonth = c.get(Calendar.MONTH);
//                toDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                to.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                globalValue.putString("to_receipt",to.getText().toString());
//
//                            }
//                        }, toYear, toMonth, toDay);
//                datePickerDialog.show();
//
//            }
//        });
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                dialog.dismiss();
//                filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));
//
//            }
//        });
//        ImageView close1 = (ImageView) dialog.findViewById(R.id.closebtn);
//
//
//        try
//        {
//            if(!(dialog.isShowing()))
//            {
//                dialog.show();
//            }
//            else
//            {
//                dialog.dismiss();
//                //   filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));
//            }
//        }
//        catch (Exception e)
//        {
//
//        }
//        close1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                //  filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));
//
//            }
//        });
//
//    }


