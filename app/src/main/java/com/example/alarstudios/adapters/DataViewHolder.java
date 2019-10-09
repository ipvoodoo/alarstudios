package com.example.alarstudios.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.alarstudios.R;
import com.example.alarstudios.model.Datum;
import com.squareup.picasso.Picasso;

public class DataViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.item_image)
  ImageView mItemImage;
  @BindView(R.id.item_name)
  TextView mItemName;
  @BindView(R.id.item_id)
  TextView mItemId;
  @BindView(R.id.item_country)
  TextView mItemCountry;
  @BindView(R.id.item_lat)
  TextView mItemLat;
  @BindView(R.id.item_lon)
  TextView mItemLon;
  @BindView(R.id.item_card)
  CardView mItemCard;

  public DataViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(Datum item, DataAdapter.ItemClickListener listener) {
    Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(mItemImage);
    mItemName.setText(item.getName());
    mItemId.setText(item.getId());
    mItemCountry.setText(item.getCountry());
    mItemLat.setText(String.valueOf(item.getLat()));
    mItemLon.setText(String.valueOf(item.getLon()));
    if (listener != null) {
      mItemCard.setOnClickListener(view -> listener.onItemClick(item));
    }
  }
}
