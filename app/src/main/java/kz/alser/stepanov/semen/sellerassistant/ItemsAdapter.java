package kz.alser.stepanov.semen.sellerassistant;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Helpers.Helpers;
import kz.alser.stepanov.semen.sellerassistant.Models.Items4Selection;

/**
 * Created by semen.stepanov on 09.09.2016.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private final List<Items4Selection> items;
    private static ItemsAdapter.OnItemClickListener listener;

    public ItemsAdapter(List<Items4Selection> _items/*, OnItemClickListener listener*/){
        this.items = _items;
       // this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Items4Selection item);
        void onItemLongClick(Items4Selection item);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        ItemsAdapter.listener = clickListener;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_products_list, viewGroup, false);
        ItemsViewHolder pvh = new ItemsViewHolder(v);

        return pvh;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    public void reloadCategories(List<Items4Selection> _categories)
    {
        items.clear();
        items.addAll(_categories);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder (ItemsViewHolder holder, int position)
    {
        try
        {
            holder.items4Selection = items.get(position);

            switch (items.get(position).getItemType())
            {
                case CATEGORY:
                    holder.cv.setCardBackgroundColor(Color.rgb(220, 237, 242));
                    break;

                case PRODUCT:
                    holder.cv.setCardBackgroundColor(Color.rgb(196, 234, 245));
                    break;

                case BACK_MENU:
                    holder.cv.setCardBackgroundColor(Color.rgb(226, 242, 220));
                    break;

                default:
                    holder.cv.setCardBackgroundColor(Color.WHITE);
                    break;
            }

            holder.itemName.setText(items.get(position).getItemName());

            if (items.get(position).getItemType() != Items4Selection.Item_Type.BACK_MENU)
            {
                holder.itemDescription.setText(items.get(position).getItemDescription ());
                holder.itemPhoto.setImageResource(R.mipmap.nofoto);

                try
                {
                    String imagePath = items.get(position).getItemImagePath();

                    if (Helpers.isNullOrEmpty(imagePath))
                    {
                        holder.itemPhoto.setImageResource(R.mipmap.nofoto);
                    }
                    else
                    {
                        Resources res = holder.cv.getContext().getResources();
                        String urlImg = String.format("%s/%s/%s", res.getString(R.string.base_url), res.getString(R.string.image_url), imagePath);
                        Picasso.with(holder.cv.getContext()).load(urlImg).resize(200, 200).into(holder.itemPhoto);
                    }
                }
                catch (Exception ex)
                {
                    holder.itemPhoto.setImageResource(R.mipmap.nofoto);
                }
            }
            else
            {
                holder.itemDescription.setText("");
                holder.itemPhoto.setImageResource(R.mipmap.back);
            }

            if (items.get(position).getItemType() == Items4Selection.Item_Type.PRODUCT)
            {
                holder.itemPrice.setText(String.format("Цена: %.2f", Double.valueOf(String.valueOf(items.get(position).getItemPrice()))));
                holder.itemQuantity.setText(String.format("Кол-во на складе: %s", String.valueOf(items.get(position).getItemQuantity ())));
            }
            else
            {
                holder.itemPrice.setText("");
                holder.itemQuantity.setText("");
            }

            /*holder.cv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v)
                {

                }
            });*/

            //holder.bind(items.get(position), listener);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount ()
    {
        return items.size();
    }

    public static class ItemsViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        CardView cv;
        TextView itemName;
        TextView itemDescription;
        ImageView itemPhoto;
        TextView itemPrice;
        TextView itemQuantity;

        Items4Selection items4Selection;

        ItemsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemDescription = (TextView)itemView.findViewById(R.id.item_description);
            itemPrice = (TextView)itemView.findViewById(R.id.item_price);
            itemQuantity = (TextView)itemView.findViewById(R.id.item_quantity);
            itemPhoto = (ImageView)itemView.findViewById(R.id.item_photo);
            items4Selection = new Items4Selection();

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View v)
        {
            listener.onItemClick(items4Selection);
        }

        @Override
        public boolean onLongClick (View v)
        {
            listener.onItemLongClick(items4Selection);
            return false;
        }

        /*public void bind(final Items4Selection item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(item);
                }
            });
        }*/
    }
}