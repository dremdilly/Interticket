package com.interticket.app.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.interticket.app.R;

public class TicketFragment extends Fragment {
    private ConstraintLayout holdScreen;
    private ConstraintLayout ticketView;
    private Button backBtn;
    private int flagForButton = 0;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        holdScreen = view.findViewById(R.id.hold_screen);
        ticketView = view.findViewById(R.id.ticket_view);
        backBtn = view.findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        holdScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                switch(action) {
                    case MotionEvent.ACTION_DOWN: {

                        ticketView.setVisibility(View.INVISIBLE);
                        flagForButton = 0;

                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        ticketView.setVisibility(View.VISIBLE);
                        flagForButton = 0;


                        return true;
                    }
                    case MotionEvent.ACTION_CANCEL: {


                        ticketView.setVisibility(View.VISIBLE);
                        flagForButton = 1;

                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }
}
