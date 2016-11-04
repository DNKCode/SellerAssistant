package kz.alser.stepanov.semen.sellerassistant.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Helpers.ApplicationContextSingleton;
import kz.alser.stepanov.semen.sellerassistant.Models.Orders;
import kz.alser.stepanov.semen.sellerassistant.R;

/**
 * Created by semen.stepanov on 24.10.2016.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private final List<Orders> orderItems;
    private static OrdersAdapter.OnItemClickListener listener;
    private Context context;

    public OrdersAdapter(List<Orders> orders){
        this.orderItems = orders;
        context = ApplicationContextSingleton.getInstance().getApplicationContext();
    }

    public interface OnItemClickListener {
        void onItemClick(Orders ordersItems);
        void onItemLongClick(Orders ordersItems);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        OrdersAdapter.listener = clickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_container, viewGroup, false);
        OrderViewHolder pvh = new OrderViewHolder(v);

        return pvh;
    }

    public Orders GetOrderItem(int position)
    {
        return orderItems.get(position);
    }

    public void removeItem(int position) {
        orderItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderItems.size());
    }

    public void addItem(Orders newItem)
    {
        orderItems.add(newItem);
        notifyDataSetChanged();
    }

    public void reloadOrders(List<Orders> _orderItems)
    {
        orderItems.clear();
        orderItems.addAll(_orderItems);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder (OrderViewHolder holder, int position)
    {
        try
        {
            holder.order = orderItems.get(position);
            holder.orderInvoice.setText(String.format(context.getString(R.string.orders_order_number), orderItems.get(position).getOrderInvoicePrefix()));
            holder.orderClientInfo.setText(String.format(context.getString(R.string.orders_client_info), String.valueOf(orderItems.get(position).getOrderLastName()), String.valueOf(orderItems.get(position).getOrderFirstName())));
            holder.orderTotal.setText(String.format(context.getString(R.string.orders_total_sum), Double.valueOf(String.valueOf(orderItems.get(position).getOrderTotal()))));
            holder.orderClientEmail.setText(orderItems.get(position).getOrderEmail());
            holder.orderClientPhone.setText(orderItems.get(position).getOrderPhone());
            holder.orderPaymentMethod.setText(orderItems.get(position).getOrderPaymentMethod());
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
        return orderItems.size();
    }

    public static class OrderViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        CardView cv;
        TextView orderInvoice;
        TextView orderClientInfo;
        TextView orderClientEmail;
        TextView orderClientPhone;
        TextView orderTotal;
        TextView orderPaymentMethod;

        Orders order;

        OrderViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            orderInvoice = (TextView)itemView.findViewById(R.id.order_invoice_number);
            orderClientInfo = (TextView)itemView.findViewById(R.id.order_client_info);
            orderClientEmail = (TextView)itemView.findViewById(R.id.order_client_email);
            orderClientPhone = (TextView)itemView.findViewById(R.id.order_client_phone);
            orderTotal = (TextView)itemView.findViewById(R.id.order_total);
            orderPaymentMethod = (TextView)itemView.findViewById(R.id.order_payment_method);
            order = new Orders();

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View v)
        {
            listener.onItemClick(order);
        }

        @Override
        public boolean onLongClick (View v)
        {
            listener.onItemLongClick(order);
            return false;
        }
    }
}