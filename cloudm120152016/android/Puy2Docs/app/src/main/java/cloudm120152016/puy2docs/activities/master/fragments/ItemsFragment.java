package cloudm120152016.puy2docs.activities.master.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.activities.master.DividerItemDecoration;
import cloudm120152016.puy2docs.activities.master.ItemAdapter;
import cloudm120152016.puy2docs.models.Item;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
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

    private static final String STATE_LIST = "State Adapter Data";

    public String item_id;
    public String fileName;

    /*@Bind(R.id.add)
    FloatingActionButton floatingActionButton;*/

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //recyclerView.setMinimumHeight(3000);
        //doIt();
        //recyclerView.setLayoutManager(new MyLinearLayoutManager(getContext()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);


    }

    void getData(Boolean init) {

        startAnim();

        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ArrayList<Item>> data;

        if (TextUtils.isEmpty(parameter)) {
            data = userService.getRoot();
        }
        else {
            data = userService.getFolder(parameter);
        }

        getItems(data, init);
    }

    void getItems(Call<ArrayList<Item>> data, final Boolean init) {
        data.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Response<ArrayList<Item>> response) {
                if (response.isSuccess()) {
                    filter(response.body());
                    Log.d("itemLLLLLLLLLLLL", "" + items.size());
                    if (init) {
                        initAdapter();
                    } else {
                        refreshAdapter();
                    }
                } else {
                    // TODO: Rollback to previous fragment
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void initAdapter() {
        adapter = new ItemAdapter(getActivity(), items);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        recyclerView.setAdapter(alphaAdapter);
        stopAnim();
    }

    void refreshAdapter() {
        adapter.notifyDataSetChanged();
        stopAnim();
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
            initAdapter();
        }
        else {
            getData(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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
                        Log.d("Create folder on root", "OK");
                        getData(false);
                    } else {
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
                        getData(false);
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

    public void uploadNewFile(File file) {
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ResponseBody> data;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        HashMap<String,RequestBody> map=new HashMap<>();
        map.put("file\"; filename=\"" + file.getName(), requestBody);

        if (TextUtils.isEmpty(parameter)) {
            data = userService.putDocument(map);

            data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccess()) {
                        Log.v("Upload", "success");
                        getData(false);
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
            data = userService.putDocument(parameter, map);

            data.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccess()) {
                        Log.v("Upload", "success");
                        getData(false);
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

    public void deleteFolder() {
        Log.d("DELETE CODE", "BEGIN");
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ResponseBody> data = userService.deleteFolder(item_id);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                Log.d("DELETE CODE", ""+response.code());
                if (response.isSuccess()) {
                    getData(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("DELETE CODE", "FAIL");
                Log.d("DELETE CODE", t.toString());
            }
        });
    }

    public void deleteFile() {
        UserService userService = ServiceGenerator.createService(UserService.class, token);
        Call<ResponseBody> data = userService.deleteDocument(item_id);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                Log.d("DELETE CODE", ""+response.code());
                if (response.isSuccess()) {
                    getData(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void refreshData() {
        getData(false);
    }

    void startAnim(){
        getActivity().findViewById(R.id.recycler_view).setVisibility(View.GONE);
        getActivity().findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        getActivity().findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
        getActivity().findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
    }

}
