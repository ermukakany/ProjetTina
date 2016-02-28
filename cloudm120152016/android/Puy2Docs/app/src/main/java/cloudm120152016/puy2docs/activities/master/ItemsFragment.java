package cloudm120152016.puy2docs.activities.master;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cloudm120152016.puy2docs.R;
import cloudm120152016.puy2docs.models.Item;
import cloudm120152016.puy2docs.utils.retrofit.ServiceGenerator;
import cloudm120152016.puy2docs.utils.retrofit.UserService;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.ACCOUNT_TYPE;
import static cloudm120152016.puy2docs.utils.retrofit.AccountGeneral.serverQueries;


public class ItemsFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    ArrayList<Item> items = new ArrayList<>();
    AccountManager manager;
    Account[] accounts;
    AccountManagerFuture<Bundle> accountManagerFuture;
    Bundle authTokenBundle;

    String token;

    private static final String ARG_PARAMETER = "parameter";

    private String parameter;

    Bundle savedState;

    private static final String STATE_LIST = "State Adapter Data";

    private OnFragmentInteractionListener mListener;

    public ItemsFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static ItemsFragment newInstance(String param1) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAMETER, param1);
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

        if (savedInstanceState != null) {
            items = savedInstanceState.getParcelableArrayList(STATE_LIST);
        }
        else {
            getData();
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
        //getData();
        doIt();

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

            data.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Response<ArrayList<Item>> response) {
                    if (response.isSuccess()) {
                        items.clear();
                        items.addAll(response.body());
                        Log.d("itemLLLLLLLLLLLL", "" + items.size());
                        adapter = new ItemAdapter(getActivity(), items);
                        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                        recyclerView.setAdapter(alphaAdapter);
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
            data = userService.getFolder(parameter);

            data.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Response<ArrayList<Item>> response) {
                    if (response.isSuccess()) {
                        items.clear();
                        items.addAll(response.body());
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
        //Call<ArrayList<Item>> data = userService.getRoot();

        //items = serverQueries.getFolderContent(parameter);
    }

    void doIt() {
        if (!items.isEmpty()) {
            adapter = new ItemAdapter(getActivity(), items);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            recyclerView.setAdapter(alphaAdapter);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST, items);
    }


    ////////////////////
    // Don't Touch !!
    ////////////////////

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("internalSavedViewState8954201239547");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString("text"));
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString("text", tv1.getText().toString());
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }
}
