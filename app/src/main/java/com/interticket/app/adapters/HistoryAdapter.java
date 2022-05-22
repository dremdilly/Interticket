package com.interticket.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.interticket.app.MainActivity;
import com.interticket.app.R;
import com.interticket.app.fragments.TicketFragment;
import com.interticket.app.models.History;

import java.io.File;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txtTitle.setText("A ticket was purchased for " + historyList.get(position).getAmount());
        holder.txtBalance.setText("Balance: " + historyList.get(position).getBalance());

        final String currentDate = historyList.get(position).getCurrentDate();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        File file = new File( ((MainActivity) context).getFilesDir() + File.separator + "ticket_" + currentDate + ".txt");

        if(!file.exists()) {
            holder.itemView.setVisibility(View.GONE);
        }

        holder.ticketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TicketFragment();

                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.flFragments,fragment).addToBackStack(null).commit();
            }
        });

        holder.ticketDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query delQuery = ref.child("History").child(auth.getCurrentUser().getUid()).orderByChild("currentDate").equalTo(currentDate);

                delQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                        historyList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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
        private ImageButton ticketDel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBalance = itemView.findViewById(R.id.txtBalance);
            ticketCard = itemView.findViewById(R.id.ticket_card);
            ticketDel = itemView.findViewById(R.id.imageView5);
        }
    }
}
