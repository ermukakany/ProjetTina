package cloudm120152016.puy2docs.activities.master;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.MasterActivity;
import cloudm120152016.puy2docs.activities.master.fragments.ItemsFragment;
import cloudm120152016.puy2docs.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;

    }

    private static final String TYPE_FOLDER = "folder";
    private static final String TYPE_FILE = "documents";

    @Override
    public Filter getFilter() {
        return new UserFilter(this, items);
    }

    private static class UserFilter extends Filter {

        private final ItemAdapter adapter;

        private final List<Item> originalList;

        private final ArrayList<Item> filteredList;

        private UserFilter(ItemAdapter adapter, List<Item> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase();

                for (final Item item : originalList) {
                    if (item.getName().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }



        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.items.clear();
            adapter.items.addAll((ArrayList<Item>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewDate;
        public ImageView imageView;
        public Button infoView;
        public Item item;

        public CardView card;

        public ViewHolder(View view) {
            super(view);

            textViewName = (TextView) view.findViewById(R.id.name);
            textViewDate = (TextView) view.findViewById(R.id.modified);
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.item = items.get(position);

        if (holder.item.getType() != null) {
            createCard(holder);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void createCard(final ViewHolder holder) {
        holder.textViewName.setText(holder.item.getName());

        if (TextUtils.equals(holder.item.getType(), TYPE_FOLDER)) {
            holder.imageView.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_folder).sizeDp(48).color(Color.parseColor("#2196F3")));
            holder.textViewDate.setVisibility(View.GONE);
        }

        else if (TextUtils.equals(holder.item.getType(), TYPE_FILE)){

            IIcon icon = new FileIcon(holder.item.getName()).getExt();
            int color = new ColorIcon(icon).getColor();
            holder.imageView.setImageDrawable(new IconicsDrawable(context, icon).sizeDp(48).color(color));

            holder.textViewName.setPadding(0, 0, 0, 10);
            holder.textViewDate.setText(beautifulDate(holder.item.getLast_modify()));
            holder.textViewDate.setPadding(0, 10, 0, 0);
            holder.textViewDate.setVisibility(View.VISIBLE);

        }

        else {
            holder.imageView.setImageDrawable(new IconicsDrawable(context, FontAwesome.Icon.faw_times).sizeDp(48).color(Color.parseColor("#aaFF0000")));
        }

        holder.infoView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(holder.item.getType(), TYPE_FOLDER)) {
                    ((MasterActivity) context).currentFragment.item_id = holder.item.getId();
                    ((MasterActivity) context).sheetEditFolder();
                } else {
                    ((MasterActivity) context).currentFragment.item_id = holder.item.getId();
                    ((MasterActivity) context).currentFragment.fileName = holder.item.getName();
                    ((MasterActivity) context).sheetEditFile();
                }

                //FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                //transaction.replace(R.id.frameLayout, ItemInfoFragment.newInstance(holder.item.getId())).addToBackStack(null).commit();
            }
        }));

        holder.card.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(holder.item.getType(), TYPE_FILE)) {
                    // TODO DLL File
                    Snackbar.make(v, holder.item.getName(), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    ((MasterActivity) context).currentFragment = ItemsFragment.newInstance(holder.item.getId());
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    //transaction.replace(R.id.frameLayout, ItemsFragment.newInstance(holder.item.getId())).addToBackStack(null).commit();
                    transaction.replace(R.id.frameLayout, ((MasterActivity) context).currentFragment).addToBackStack(null).commit();
                }

            }
        }));
    }

    String beautifulDate(String date) {
        String split[]= date.split("\\s+");
        if (split.length == 2)
        return "Modifié le " + split[0] + " à " + split[1];
        return null;
    }

    public void animateTo(List<Item> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Item> newModels) {
        for (int i = items.size() - 1; i >= 0; i--) {
            final Item model = items.get(i);
            if (!newModels.contains(model)) {
                Log.d("FILTER", "REMOVE");
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Item> newModels) {
        for (int i = 0; i < newModels.size(); i++) {
            final Item model = newModels.get(i);
            if (!items.contains(model)) {
                Log.d("FILTER", "ADD");
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Item> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Item model = newModels.get(toPosition);
            final int fromPosition = items.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Item removeItem(int position) {
        final Item model = items.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Item model) {
        items.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Item model = items.remove(fromPosition);
        items.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
