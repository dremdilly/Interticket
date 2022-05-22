package com.interticket.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.interticket.app.MainActivity;
import com.interticket.app.R;
import com.interticket.app.fragments.TicketFragment;
import com.interticket.app.models.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historyList;
    private Context context;

    public HistoryAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_history,parent,false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtTitle.setText("A ticket was purchased for " + historyList.get(position).getAmount());
        holder.txtBalance.setText("Balance: " + historyList.get(position).getBalance());
        holder.ticketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TicketFragment();

                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.flFragments,fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtBalance;
        private CardView ticketCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBalance = itemView.findViewById(R.id.txtBalance);
            ticketCard = itemView.findViewById(R.id.ticket_card);
        }
    }
}
