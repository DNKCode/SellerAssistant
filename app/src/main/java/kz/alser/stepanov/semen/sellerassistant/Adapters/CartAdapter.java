package kz.alser.stepanov.semen.sellerassistant.Adapters;

import android.content.res.Resources;
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
import kz.alser.stepanov.semen.sellerassistant.Models.Cart;
import kz.alser.stepanov.semen.sellerassistant.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Cart> cartItems;
    private static CartAdapter.OnItemClickListener listener;

    public CartAdapter(List<Cart> cart){
        this.cartItems = cart;
    }

    public interface OnItemClickListener {
        void onItemClick(Cart cartItem);
        void onItemLongClick(Cart cartItem);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        CartAdapter.listener = clickListener;
    }

    @Override
    public CartViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_products_list, viewGroup, false);
        CartViewHolder pvh = new CartViewHolder(v);

        return pvh;
    }

    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItems.size());
    }

    public void addItem(Cart newItem) {
        cartItems.add(newItem);
        notifyDataSetChanged();
    }

    public void reloadCart(List<Cart> _cartItems)
    {
        cartItems.clear();
        cartItems.addAll(_cartItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder (CartViewHolder holder, int position)
    {
        try
        {
            holder.cart = cartItems.get(position);

            holder.itemName.setText(cartItems.get(position).getProductName() );
            holder.itemDescription.setText(cartItems.get(position).getProductDescription());
            holder.itemPhoto.setImageResource(R.mipmap.nofoto);
            holder.itemPrice.setText(String.format("Цена: %.2f", Double.valueOf(String.valueOf(cartItems.get(position).getBeginPrice()))));
            holder.itemQuantity.setText(String.format("Кол-во на складе: %s", String.valueOf(cartItems.get(position).getQuantity())));

                try
                {
                    String imagePath = cartItems.get(position).getProductImagePath();

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
        return cartItems.size();
    }

    public static class CartViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        CardView cv;
        TextView itemName;
        TextView itemDescription;
        ImageView itemPhoto;
        TextView itemPrice;
        TextView itemQuantity;

        Cart cart;

        CartViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemDescription = (TextView)itemView.findViewById(R.id.item_description);
            itemPrice = (TextView)itemView.findViewById(R.id.item_price);
            itemQuantity = (TextView)itemView.findViewById(R.id.item_quantity);
            itemPhoto = (ImageView)itemView.findViewById(R.id.item_photo);
            cart = new Cart();

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View v)
        {
            listener.onItemClick(cart);
        }

        @Override
        public boolean onLongClick (View v)
        {
            listener.onItemLongClick(cart);
            return false;
        }
    }
}