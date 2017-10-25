package com.jjjx.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.adapter.CommentAdapter;
import com.jjjx.data.response.AddCommentResponse;
import com.jjjx.data.response.CommentListResponse;
import com.jjjx.data.response.FindDataResponse;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.jjjx.widget.commentdialog.BottomDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import static com.jjjx.widget.commentdialog.RightDialog.create;

/**
 * Created by AMing on 17/8/8.
 * Company RongCloud
 */
public class FindImageActivity extends BaseActivity {

    private static final int ADD_COMMENT = 211;
    private static final int GET_COMMENT_LIST = 212;

    ImageView imageView;
    TextView commentButton;

    private EditText mEditText;
    private TextView mButton;
    private BottomDialog dialog;
    private FindDataResponse.ParaEntity.DiscoverInfoEntity entity;
    private SmartRefreshLayout mSmartRefreshLayout;
    private SmartRefreshUtil mSmartRefreshUtil;
    private int mPageIndex = 0;//页码
    private boolean isRefresh = false;
    private List<CommentListResponse.ParaEntity.DiscoverInfoEntity> data = new ArrayList<>();
    private ListView commentList;
    private CommentAdapter adapter;
    private View headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);
        setTitle("发现详情");
        entity = getIntent().getParcelableExtra("FindImageEntity");

        commentButton = (TextView) findViewById(R.id.comment_button);
        commentButton.setVisibility(CacheTask.getInstance().isLogin() ? View.VISIBLE : View.GONE);
        commentList = (ListView) findViewById(R.id.comment_list);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentDialog();
            }
        });
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.comment_sfl);
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                mPageIndex++;
                request(GET_COMMENT_LIST);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新后，要设置可以触发加载功能
                refreshlayout.setLoadmoreFinished(false);
                isRefresh = true;
                mPageIndex = 0;
                request(GET_COMMENT_LIST);

            }
        });
        mSmartRefreshUtil = new SmartRefreshUtil(mSmartRefreshLayout);
        headView = LayoutInflater.from(this).inflate(R.layout.find_image_head_view, commentList, false);
        imageView = (ImageView) headView.findViewById(R.id.find_image);
        Glide.with(this).load(entity.getPicture()).into(imageView);
        adapter = new CommentAdapter(data, mContext);
        commentList.addHeaderView(headView);
        commentList.setAdapter(adapter);
        request(GET_COMMENT_LIST);
    }

    /**
     * 弹出评论dialog
     */
    private void showCommentDialog() {
        dialog = BottomDialog.create(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_comment_view)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        initView(v);
                    }
                })
                .setDimAmount(0.6f)
                .setCancelOutside(false)
                .setTag("comment")
                .show();
    }


    private void initView(View v) {
        mEditText = (EditText) v.findViewById(R.id.edit_text);
        mButton = (TextView) v.findViewById(R.id.comment_btn);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn);
                    mButton.setEnabled(false);
                } else {
                    mButton.setBackgroundResource(R.drawable.dialog_send_btn_pressed);
                    mButton.setEnabled(true);
                }
            }
        });
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, 0);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(ADD_COMMENT);
                dialog.dismiss();
            }
        });

    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == ADD_COMMENT) {
            return action.addComment(String.valueOf(entity.getPid()), mEditText.getText().toString());
        } else if (requestCode == GET_COMMENT_LIST) {
            return action.getCommentList(String.valueOf(entity.getPid()), String.valueOf(mPageIndex));
        }
        return null;
    }

    boolean isMoveList = false;

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (requestCode == ADD_COMMENT) {
            AddCommentResponse response = (AddCommentResponse) result;
            if ("S0009".equals(response.getHead().getCode())) {
                NToast.shortToast(mContext, "评论成功");
                isRefresh = true;
                mPageIndex = 0;
                request(GET_COMMENT_LIST);
                isMoveList = true;
            }
        } else if (requestCode == GET_COMMENT_LIST) {
            CommentListResponse response = (CommentListResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
                if (response.getPara().getDiscoverInfo().size() > 0) {
                    if (isRefresh) {//是下拉刷新
                        adapter.refreshAdapter(response.getPara().getDiscoverInfo());
                        if (isMoveList) {
                            new android.os.Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    commentList.smoothScrollToPosition(1);
                                }
                            }, 300);
                        }
                    } else {//是上拉加载
                        adapter.addDataAdapter(response.getPara().getDiscoverInfo());
                    }
                    mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_SUCCESS);
                } else {
                    mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_NO);
                }
            }
        }
    }
}
