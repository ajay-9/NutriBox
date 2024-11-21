package com.example.tiffincart.Screen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiffincart.GeminiResp;
import com.example.tiffincart.Interface.ResponseCallBack;
import com.example.tiffincart.R;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ConsultantFragment extends Fragment {

    private TextInputEditText queryEditText;
    private ImageView sendQuery;
    private ProgressBar progressBar;
    private LinearLayout chatResponse;
    private ChatFutures chatModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_consultant, container, false);

        // Initialize views
        queryEditText = view.findViewById(R.id.queryEditText);
        sendQuery = view.findViewById(R.id.sendMessage);
        progressBar = view.findViewById(R.id.progressBar);
        chatResponse = view.findViewById(R.id.chatResponse);

        chatModels = getChatModels();

        // Set send button click listener
        sendQuery.setOnClickListener(v -> {
            String query = Objects.requireNonNull(queryEditText.getText()).toString();
            queryEditText.setText("");  // Clear input field after sending
            chatBody("You", query, requireContext().getDrawable(R.drawable.user));  // User message
            progressBar.setVisibility(View.VISIBLE);

            // Get response from Gemini API
            GeminiResp.getResponse(chatModels, query, new ResponseCallBack() {
                @Override
                public void onResponse(String response) {
                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        chatBody("Tiffin Care", response, requireContext().getDrawable(R.drawable.logo));
                    });
                }

                @Override
                public void onError(Throwable throwable) {
                    requireActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                }
            });
        });

        return view;
    }

    private ChatFutures getChatModels() {
        GeminiResp model = new GeminiResp();
        return model.getModel().startChat();
    }

    private void chatBody(String userName, String messageText, Drawable image) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_message, null);

        TextView message = view.findViewById(R.id.agentMessage);
        ImageView logo = view.findViewById(R.id.bot_logo);

        message.setText(messageText);
        logo.setImageDrawable(image);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) message.getLayoutParams();

        if ("You".equals(userName)) {
            layoutParams.gravity = Gravity.END;
            message.setBackgroundResource(R.drawable.skin_border);
            logo.setVisibility(View.GONE);
        } else {
            layoutParams.gravity = Gravity.START;
            message.setBackgroundResource(R.drawable.purple_background);
            logo.setVisibility(View.VISIBLE);
        }

        message.setLayoutParams(layoutParams);
        chatResponse.addView(view);

        ScrollView scrollView = requireView().findViewById(R.id.scrollView2);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
