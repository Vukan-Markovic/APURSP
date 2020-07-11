package vukan.com.apursp.ui.user_messages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import vukan.com.apursp.R;
import vukan.com.apursp.adapters.ConversationAdapter;
import vukan.com.apursp.ui.my_ads.MyAdsViewModel;
import vukan.com.apursp.ui.product.ProductViewModel;

public class UserMessagesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ConversationAdapter adapter;
    private List<String> productNames;
    private List<String> userNames;
    private List<String> list;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(getString(R.string.app_name));
        productNames = new ArrayList<>();
        userNames = new ArrayList<>();
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ConversationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        UserMessagesViewModel userMessagesViewModel = new ViewModelProvider(this).get(UserMessagesViewModel.class);
        MyAdsViewModel myAdsViewModel = new ViewModelProvider(this).get(MyAdsViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        FirebaseUser fire_user = FirebaseAuth.getInstance().getCurrentUser();

        //umesto ovog id treba da ide trenutni user id, al morala sam da je zakucam jer nemam u bazi torke da testiram obrnuto
        userMessagesViewModel.getAllUserMessages(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(getViewLifecycleOwner(), conv -> {

            for (int i = 0; i < conv.size(); i++) {
                int finalI = i;
                Log.i("AAAAAAA", String.valueOf(conv.get(finalI).getLista().size()));
                productViewModel.getProductDetails(conv.get(finalI).getLista().get(0).getProductID()).observe(getViewLifecycleOwner(), product -> {
                    myAdsViewModel.getUser(conv.get(finalI).getLista().get(0).getSenderID()).observe(getViewLifecycleOwner(), user -> {
                        list.add(user.getUsername() + ':' + product.getName());
                        Set<String> set = new LinkedHashSet<>(list);
                        list.clear();
                        list.addAll(set);

                        if (list.size() == conv.size()) {
                            for (String s : list) {
                                userNames.add(s.split(":")[0]);
                                productNames.add(s.split(":")[1]);
                            }

                            adapter.setConversations(conv, productNames, userNames);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                });
            }
        });
    }
}