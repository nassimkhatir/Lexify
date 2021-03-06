package parisnanterre.fr.lexify.application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import org.w3c.dom.Text;

import parisnanterre.fr.lexify.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AboutGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    boolean rules_actif=false;

    public AboutGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutGameFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static AboutGameFragment newInstance(String param1, String param2) {
        AboutGameFragment fragment = new AboutGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_game, container, false);
        final Button btn_regles = (Button) view.findViewById(R.id.fragment_about_regles_btn);
        final Button btn_about_application= (Button) view.findViewById(R.id.fragment_about_application_btn);
        final Button btn_menu= (Button) view.findViewById(R.id.fragment_about_menu_btn);
        final TextView txt_about_application = (TextView) view.findViewById(R.id.fragment_about_affiche_txt);

        btn_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
            }
        });

        btn_regles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    txt_about_application.setText(
                            "\nRules\n\n4 rounds\n2 players\n4 words per game\n" +
                                    "1 word per round\n\n" +
                                    "A player guesses a word to another with 4 indices related to the maximum word.\n\n" +
                                    "Each round, the players change their role.\n\n" +
                                    "Correct proposition : +1 point\n" +
                                    "False proposition : -1 point\n" +
                                    "Pass : -5 points\n\n" +
                                    "The game starts with a score of 10 points.");
                }
        });

        btn_about_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    txt_about_application.setText(
                            "\nLexify, the mobile game that lexify your vocabulary.\n\n" +
                                    "Inspired by the game Mot de Passe on France 2, " +
                                    "Lexify's goal is either to guess a word or to guess a word." +
                                    "\n\nMail : lexifyapp@gmail.com\n\n" +
                                    "Autors: \n\n Nassin Khatir\nLucas Nayet\nNicolas Piot");
            }
        });

        return view;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
