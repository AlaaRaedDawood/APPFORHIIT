package com.example.start;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class imagetargetAdapter  extends RecyclerView.Adapter<imagetargetAdapter.ImageViewHolder> {

    private OnItemClickListener listener;
        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<imagetarget> productList;

        //getting the context and product list with constructor
        public imagetargetAdapter(Context mCtx, List<imagetarget> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflating and returning our view holder
           // LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_print_markers, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            //getting the product of the specified position
            imagetarget product = productList.get(position);

            //binding the data with the viewholder views
            holder.textViewTitle.setText(product.getTitle());
            holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

        }


        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ImageViewHolder extends RecyclerView.ViewHolder {

            TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
            ImageView imageView;
            Button button_print ;
            Button button_view;
            public ImageViewHolder(View itemView) {
                super(itemView);

                textViewTitle = itemView.findViewById(R.id.textViewTitle);
                imageView = itemView.findViewById(R.id.imageView);
                button_print = itemView.findViewById(R.id.buttonprint);
                button_view = itemView.findViewById(R.id.buttonviewimage);

                button_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onViewClick(productList.get(position) ,v);
                            //notifyDataSetChanged();
                        }
                    }
                });
                button_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onPrintClick(productList.get(position) ,v);

                        //notifyDataSetChanged();
                    }
                }
            });

        }}
    public interface OnItemClickListener {
            void onPrintClick(imagetarget imagetarget, View v);
            void onViewClick (imagetarget imagetarget, View v);
    }

    public void setOnItemClickListener(imagetargetAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    }

