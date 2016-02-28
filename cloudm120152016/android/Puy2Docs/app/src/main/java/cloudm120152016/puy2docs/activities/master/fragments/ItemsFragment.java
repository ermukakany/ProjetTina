package cloudm120152016.puy2docs.activities.master.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.master.DividerItemDecoration;
import cloudm120152016.puy2docs.activities.master.ItemAdapter;
import cloudm120152016.puy2docs.activities.master.MyLinearLayoutManager;
import cloudm120152016.puy2docs.models.Item;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.ACCOUNT_TYPE;


public class ItemsFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    ArrayList<Item> items = new ArrayList<>();
    AccountManager manager;
    Account[] accounts;

    String token;

    private static final String ARG_PARAMETER = "parameter";

    public static final String TAG = "RecyclerFragment";

    private String parameter;

    Bundle savedState;

    private static final String STATE_LIST = "State Adapter Data";

    /*@Bind(R.id.add)
    FloatingActionButton floatingActionButton;*/

    private OnFragmentInteractionListener mListener;

    public ItemsFragment() {

    }

    public static ItemsFragment newInstance(String param) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAMETER, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = AccountManager.get(getContext());
        accounts = manager.getAccountsByType(ACCOUNT_TYPE);
        token = manager.peekAuthToken(accounts[0], "global");

        Log.d("tetetet", "" + accounts.length);

        if (getArguments() != null) {
            parameter = getArguments().getString(ARG_PARAMETER);
        }

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        //return inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setMinimumHeight(3000);
        //doIt();
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getContext()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    void getData() {

        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ArrayList<Item>> data;

        if (TextUtils.isEmpty(parameter)) {
            data = userService.getRoot();
        }
        else {
            data = userService.getFolder(parameter);
        }

        getItems(data);
    }

    void getItems(Call<ArrayList<Item>> data) {
        data.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Response<ArrayList<Item>> response) {
                if (response.isSuccess()) {
                    filter(response.body());
                    Log.d("itemLLLLLLLLLLLL", "" + items.size());
                    adapter = new ItemAdapter(getActivity(), items);
                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                    recyclerView.setAdapter(alphaAdapter);
                } else {
                    // TODO: Rollback to previous fragment
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void filter(ArrayList<Item> body) {
        Predicate<Item> predicate = new Predicate<Item>() {
            @Override
            public boolean apply(Item item) {
                return item.getType() != null;
            }
        };
        items.clear();
        items.addAll(Collections2.filter(body, predicate));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST)) {
            items = savedInstanceState.getParcelableArrayList(STATE_LIST);
            adapter = new ItemAdapter(getActivity(), items);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            recyclerView.setAdapter(alphaAdapter);
        }
        else {
            getData();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_LIST, items);
    }

    public void createNewFolder(String folder) {
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ResponseBody> data;

        if (TextUtils.isEmpty(parameter)) {
            data = userService.putRoot(folder);

            data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccess()) {
                        //items.clear();
                        //items.addAll(response.body());
                        Log.d("Create folder on root", "OK");
                        getData();
                        //adapter = new ItemAdapter(getActivity(), items);
                        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                        //recyclerView.setAdapter(alphaAdapter);
                    }
                    else {
                        // TODO: Rollback to previous fragment
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }

        else {
            data = userService.putFolder(parameter, folder);

            data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccess()) {
                        Log.d("Create folder", "OK");

                    } else {
                        // TODO: Rollback to previous fragment
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

}
