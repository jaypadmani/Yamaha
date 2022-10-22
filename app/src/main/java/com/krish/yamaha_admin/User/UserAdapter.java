package com.krish.yamaha_admin.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krish.yamaha_admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewAdapter>{
    private List<UserData> list;
    private Context context;
    private String category;

    public UserAdapter(List<UserData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public UserViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new UserViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewAdapter holder, int position) {
        final UserData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.password.setText(item.getPassword());
        holder.bike.setText(item.getBikemodel());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Update_User.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("password",item.getPassword());
                intent.putExtra("bike",item.getBikemodel());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,password,bike;
        private Button update;
        private ImageView imageView;
        public UserViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            password = itemView.findViewById(R.id.userPassword);
            bike = itemView.findViewById(R.id.userBike);
            imageView = itemView.findViewById(R.id.userImage);
            update = itemView.findViewById(R.id.userUpdate);
        }
    }
}