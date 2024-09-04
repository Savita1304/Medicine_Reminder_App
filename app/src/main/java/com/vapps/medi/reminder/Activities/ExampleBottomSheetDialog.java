package com.vapps.medi.reminder.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vapps.medi.reminder.Fragments.ProfileFragment;
import com.vapps.medi.reminder.R;


public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener listener;
    ProfileFragment profileFragment;


    public ExampleBottomSheetDialog(BottomSheetListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottomsheet, container, false);
        //listener = (BottomSheetListener) this;

        TextView button1 = v.findViewById(R.id.button1);
        //TextView button2 = v.findViewById(R.id.button2);





        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listener.onButtonClicked("Button 1 clicked");

                dismiss();
            }
        });
      /*  button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  listener.onButtonClicked("Button 2 clicked");
                dismiss();
            }
        });
*/
        return v;
    }


    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }


}





