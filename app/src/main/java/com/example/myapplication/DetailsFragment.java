//package com.example.myapplication;
//
//import android.os.Bundle;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.IntRange;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
///**
// * Display details for a given kitten
// *
// * @author bherbst
// */
//public class DetailsFragment extends Fragment {
//    private static final String ARG_KITTEN_NUMBER = "argKittenNumber";
//
//    /**
//     * Create a new DetailsFragment
//     * @param kittenNumber The number (between 1 and 6) of the kitten to display
//     */
//    public static DetailsFragment newInstance(@IntRange(from = 1, to = 6) int kittenNumber) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);
//   DetailsFragment fragment = new DetailsFragment();
//        fragment.setArguments(args);
//
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.players_details, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        ImageView image = (ImageView) view.findViewById(R.id.image);
//
//        Bundle args = getArguments();
//        int kittenNumber = args.containsKey(ARG_KITTEN_NUMBER) ? args.getInt(ARG_KITTEN_NUMBER) : 1;
//        image.setImageResource(R.drawable.add_team);
//
//    }
//}
