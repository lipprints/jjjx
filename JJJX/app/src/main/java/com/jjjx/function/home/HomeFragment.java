package com.jjjx.function.home;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.jjjx.app.App;
import com.jjjx.network.OnBDLocationListener;
import com.jjjx.R;
import com.jjjx.function.main.IndexItemDetailsActivity;
import com.jjjx.function.add.view.PublishActivity;
import com.jjjx.function.home.view.SearchActivity;
import com.jjjx.function.base.XBaseFragment;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.data.response.IndexDataResponse.ParaEntity.ComplaintsEntity;
import com.jjjx.function.home.adapter.HomeAdapter;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.NToast;
import com.jjjx.utils.refreshload.SmartRefreshUtil;
import com.jjjx.widget.banner.GlideImageLoader;
import com.jjjx.widget.dialog.AppProgressDialog;
import com.jjjx.widget.popwinpicker.PopupAdapter;
import com.jjjx.widget.popwinpicker.PopupButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author AMing
 * @date 17/5/8
 * Company RongCloud
 * 首页
 */
public class HomeFragment extends XBaseFragment implements OnBDLocationListener, AdapterView.OnItemClickListener, PublishActivity.RefreshDataListener, IndexItemDetailsActivity.RefreshListener, View.OnClickListener {
    private static final int GET_INDEX = 2;

    private List<String> images;
    private HomeAdapter mAdapter;
    private List<ComplaintsEntity> data = new ArrayList<>();
    private BDLocation bdLocation;

    private SmartRefreshLayout mSmartRefreshLayout;
    private SmartRefreshUtil mSmartRefreshUtil;
    private LinearLayout mSearchLayout;
    /**
     * 页码
     */
    private int mPageIndex = 0;
    private boolean isRefresh = false;
    private PopupButton mPopupButton1;
    private PopupButton mPopupButton2;
    private PopupButton mPopupButton3;
    private Banner mBanner;
    private String[] mArray1;
    private String[] mArray2;
    private String[] mArray3;
    private ListView mLv1;
    private ListView mLv2;
    private ListView mLv3;
    private View mView1;
    private View mView2;
    private View mView3;

    @Override
    public void onLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
        //TODO 切换线程
        CacheTask.getInstance().cacheCity(bdLocation.getCity());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView(View view) {
        mPopupButton1 = find(R.id.popup_button1);
        mPopupButton2 = find(R.id.popup_button2);
        mPopupButton3 = find(R.id.popup_button3);
        LinearLayout layout1 = find(R.id.fi_layout_1);
        LinearLayout layout2 = find(R.id.fi_layout_2);
        LinearLayout layout3 = find(R.id.fi_layout_3);

        ListView indexListView = find(R.id.index_list);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.index_head_view, indexListView, false);
        mBanner = headView.findViewById(R.id.banner);
        indexListView.addHeaderView(headView);

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        mView1 = inflater.inflate(R.layout.popup, null);
        mView2 = inflater.inflate(R.layout.popup2, null);
        mView3 = inflater.inflate(R.layout.popup3, null);

        mLv1 = mView1.findViewById(R.id.lv);
        mLv2 = mView2.findViewById(R.id.lv);
        mLv3 = mView3.findViewById(R.id.lv);

        mSmartRefreshLayout = find(R.id.index_srl);
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //刷新后，要设置可以触发加载功能
                refreshlayout.setLoadmoreFinished(false);
                isRefresh = true;
                refresh();
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                mPageIndex++;
                request(GET_INDEX);
            }
        });


        mAdapter = new HomeAdapter(getActivity(), data);
        indexListView.setAdapter(mAdapter);
        indexListView.setOnItemClickListener(this);

        mSearchLayout = find(R.id.fi_search_layout);
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });


        mSmartRefreshUtil = new SmartRefreshUtil(mSmartRefreshLayout);

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        AppProgressDialog.show(getContext());
    }

    @Override
    protected void xLoad() {
        //先处理选项
        mArray1 = new String[]{"不限", "舞蹈", "声乐", "美术", "乐器"};
        mArray2 = new String[]{"不限", "教龄/建校时间最长", "订单最多", "课次单价最低", "课次单价最高"};
        mArray3 = new String[]{"不限", "私教", "机构/学校", "大学生", "离我最近"};

        final PopupAdapter adapter1 = new PopupAdapter(getActivity(), R.layout.popup_item, mArray1, R.drawable.normal, R.drawable.press);
        final PopupAdapter adapter2 = new PopupAdapter(getActivity(), R.layout.popup_item, mArray2, R.drawable.normal, R.drawable.press);
        final PopupAdapter adapter3 = new PopupAdapter(getActivity(), R.layout.popup_item, mArray3, R.drawable.normal, R.drawable.press);
        mLv1.setAdapter(adapter1);
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter1.setPressPostion(i);
                adapter1.notifyDataSetChanged();
                mPopupButton1.setText(mArray1[i]);
                mPopupButton2.setText("排序");
                mPopupButton3.setText("筛选");
                mPopupButton1.hidePopup();
            }
        });
        mPopupButton1.setPopupView(mView1);

        mLv2.setAdapter(adapter2);
        mLv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter2.setPressPostion(i);
                adapter2.notifyDataSetChanged();
                mPopupButton2.setText(mArray2[i]);
                mPopupButton1.setText("分类");
                mPopupButton3.setText("筛选");
                mPopupButton2.hidePopup();
            }
        });
        mPopupButton2.setPopupView(mView2);

        mLv3.setAdapter(adapter3);
        mLv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter3.setPressPostion(i);
                adapter3.notifyDataSetChanged();
                mPopupButton3.setText(mArray3[i]);
                mPopupButton1.setText("分类");
                mPopupButton2.setText("排序");
                mPopupButton3.hidePopup();
            }
        });
        mPopupButton3.setPopupView(mView3);

        //轮播图
        images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136189&di=cbbd537c7eb6ed7f907d005b19031b98&imgtype=jpg&er=1&src=http%3A%2F%2Fpic2.ooopic.com%2F13%2F58%2F87%2F63bOOOPICb3_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136210&di=c0d88cb26a88a6b0cf78a8f7beb49653&imgtype=jpg&er=1&src=http%3A%2F%2Fpic74.nipic.com%2Ffile%2F20150805%2F12033066_235333091000_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541508985&di=decefe3014ec2eba9a1df114460b04c4&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F72%2F21%2F20bOOOPIC2c_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541537797&di=f03bc68d54916ed39d027a7567181661&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018f1b56f2057232f875a9448ab29f.jpg%40900w_1l_2o_100sh.jpg");
        mBanner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        mBanner.setDelayTime(6000);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position) {
                    case 0:
                        NToast.shortToast(getActivity(), "点击了第 1 个轮播图");
                        break;
                    case 1:
                        NToast.shortToast(getActivity(), "点击了第 2 个轮播图");
                        break;
                    case 2:
                        NToast.shortToast(getActivity(), "点击了第 3 个轮播图");
                        break;
                    case 3:
                        NToast.shortToast(getActivity(), "点击了第 4 个轮播图");
                        break;
                    default:
                        break;
                }
            }
        });

        //数据
        App.getInstance().addOnBDLocationObserver(this);
        PublishActivity.setRefreshDataListener(this);
        IndexItemDetailsActivity.setRefreshListener(this);
        request(GET_INDEX);

    }

    @Override
    protected void closeFragment() {
        App.getInstance().removeOnBDLocationObserver(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ComplaintsEntity entity = (ComplaintsEntity) mAdapter.getItem(position - 1);// headview 计算一个 position
        Intent intent = new Intent(getActivity(), IndexItemDetailsActivity.class);
        intent.putExtra("indexItemData", entity);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fi_layout_1:
                //手心第三方斯蒂芬
                mPopupButton1.performClick();
                break;
            case R.id.fi_layout_2:
                mPopupButton2.performClick();
                break;
            case R.id.fi_layout_3:
                mPopupButton3.performClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void refresh() {
        mPageIndex = 0;
        request(GET_INDEX);
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case GET_INDEX:
                return action.requestIndexData(mPageIndex);
            default:
                break;
        }
        return super.doInBackground(requestCode);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        switch (requestCode) {
            case GET_INDEX:
                AppProgressDialog.onDismiss();
                mSmartRefreshUtil.stopRefrshLoad();
                IndexDataResponse response = (IndexDataResponse) result;
                if ("10000".equals(response.getHead().getCode())) {
                    //  NToast.shortToast(getActivity(), "刷新成功");
                    List<ComplaintsEntity> complaintsEntities = response.getPara().getComplaints();
                    if (complaintsEntities.size() > 0) {
                        if (isRefresh) { //是下拉刷新
                            mAdapter.refreshAdapter(complaintsEntities);
                        } else {
                            //增量刷新
                            mAdapter.addDataAdapter(complaintsEntities);
                        }
                        mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_SUCCESS);
                    } else { //没有更多数据
                        mSmartRefreshUtil.stopRefrshLoad(SmartRefreshUtil.LOAD_NO);
                    }
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }


}
