package cloudm120152016.puy2docs.activities.master;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.List;
import java.util.Objects;

import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    private static final String TYPE_FOLDER = "folder";
    private static final String TYPE_FILE = "documents";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public Button infoView;
        public Item item;

        public CardView card;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.type);
            infoView = (Button) view.findViewById(R.id.info);

            card = (CardView) view.findViewById(R.id.card_detail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_value, parent, false);

        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.item = items.get(position);

        holder.textView.setText(holder.item.getName());

        if (Objects.equals(holder.item.getType(), TYPE_FOLDER)) {
            holder.imageView.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_folder).sizeDp(48).color(Color.parseColor("#2196F3")));
        }

        else if (Objects.equals(holder.item.getType(), TYPE_FILE)){
            //**TODO : Handle different file type
            IIcon icon = new FileIcon(holder.item.getName()).getExt();
            int color = new ColorIcon(icon).getColor();
            holder.imageView.setImageDrawable(new IconicsDrawable(context, icon).sizeDp(48).color(color));
        }

        else {
            holder.imageView.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_times).sizeDp(48).color(Color.parseColor("#aaFF0000")));
        }

        //holder.infoView.setImageDrawable(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_more_vert).sizeDp(18));

        holder.infoView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, ItemInfoFragment.newInstance(holder.item.getId())).addToBackStack(null).commit();
            }
        }));

        holder.card.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(holder.item.getType(), TYPE_FILE)) {
                    Snackbar.make(v, holder.item.getName(), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, ItemsFragment.newInstance(holder.item.getId())).addToBackStack(null).commit();
                }

            }
        }));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
