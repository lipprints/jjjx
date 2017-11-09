package com.jjjx.function.message.view;

import android.net.Uri;
import android.view.View;

import com.jjjx.R;
import com.jjjx.function.base.XBaseFragment;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * @author xz
 * @date 2017/10/31 0031
 * 聊天
 */

public class ChatListFragment extends XBaseFragment {

    @Override
    protected int getContentView() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected void initView(View view) {
        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager()
                .findFragmentById(R.id.conversationlist);

        Uri uri = Uri
                .parse("rong://"
                        + getActivity().getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(
                        Conversation.ConversationType.PRIVATE.getName(),
                        "false") // 设置私聊会话非聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.GROUP.getName(), "false")// 设置群组会话聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.DISCUSSION.getName(),
                        "false")// 设置讨论组会话非聚合显示
                .appendQueryParameter(
                        Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
    }

    @Override
    protected void xLoad() {

    }

    @Override
    protected void closeFragment() {

    }
}
