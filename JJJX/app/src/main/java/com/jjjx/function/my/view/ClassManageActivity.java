package com.jjjx.function.my.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.utils.CacheTask;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author AMing
 * @date 17/9/5
 * Company RongCloud
 * 课程管理
 */
public class ClassManageActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final int GET_CLASS_MANAGE_LIST = 206;
    private static final int DELETE_CLASS = 207;
    private List<IndexDataResponse.ParaEntity.ComplaintsEntity> data = new ArrayList<>();
    private ListView mListView;
    private ClassManageAdapter adapter;
    private int tempInt;
    private int tempId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manage);
        setTitle("我的课程管理");
        setBtnRight("设置");
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempInt % 2 == 0) {
                    adapter.changeDeleteMode(true);
                    setBtnRight("完成");
                } else {
                    adapter.changeDeleteMode(false);
                    setBtnRight("设置");
                }
                tempInt++;
            }
        });
        request(GET_CLASS_MANAGE_LIST);
        mListView = (ListView) findViewById(R.id.class_manage_list);
        adapter = new ClassManageAdapter(mContext, data);
        adapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete(int id) {
                tempId = id;
                request(DELETE_CLASS);
            }
        });
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == GET_CLASS_MANAGE_LIST) {
            return action.getClassManageList(CacheTask.getInstance().getUserId());
        } else if (requestCode == DELETE_CLASS) {
            return action.deleteCourseById(String.valueOf(tempId));
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (requestCode == GET_CLASS_MANAGE_LIST) {
            IndexDataResponse response = (IndexDataResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
                if (response.getPara().getComplaints().size() > 0) {
                    adapter.refreshAdapter(response.getPara().getComplaints());
                } else {
                    //TODO  无数据
                }
            }
        } else if (requestCode == DELETE_CLASS) {
            request(GET_CLASS_MANAGE_LIST);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IndexDataResponse.ParaEntity.ComplaintsEntity entity = (IndexDataResponse.ParaEntity.ComplaintsEntity) adapter.getItem(i);

    }

    class ClassManageAdapter extends BaseAdapter {
        private Context context;
        private List<IndexDataResponse.ParaEntity.ComplaintsEntity> data;
        private boolean isShowDelete;

        public ClassManageAdapter(Context context, List<IndexDataResponse.ParaEntity.ComplaintsEntity> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_class_manage, parent, false);
                holder.classAge = (TextView) convertView.findViewById(R.id.index_class_age);
                holder.classTime = (TextView) convertView.findViewById(R.id.index_class_time);
                holder.className = (TextView) convertView.findViewById(R.id.index_class_name);
                holder.learnYear = (TextView) convertView.findViewById(R.id.index_learn_year);
                holder.className = (TextView) convertView.findViewById(R.id.index_class_name);
                holder.classMode = (TextView) convertView.findViewById(R.id.index_class_mode);
                holder.classFirstImage = (SimpleDraweeView) convertView.findViewById(R.id.index_class_image);
                holder.deleteClassImage = (ImageView) convertView.findViewById(R.id.class_manage_badge_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final IndexDataResponse.ParaEntity.ComplaintsEntity positionData = data.get(position);
            if (TextUtils.isEmpty(positionData.getVideo())) {
            }
            holder.className.setText(positionData.getCourseName());
            holder.classMode.setText(positionData.getTeachingNumber());
            holder.classFirstImage.setImageURI(TextUtils.isEmpty(positionData.getFirstFrame()) ? positionData.getPicture() : getFirstPicture(positionData.getFirstFrame()));
            holder.classAge.setText(positionData.getSeniority());
            holder.learnYear.setText(positionData.getRightAge());
            holder.classTime.setText(positionData.getTeachingDate());
            holder.deleteClassImage.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
            holder.deleteClassImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(mContext).setTitle("课程管理").setMessage("是否删除此课程?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (mOnDeleteListener != null) {
                                mOnDeleteListener.onDelete(positionData.getId());
                            }
                        }
                    }).show();
                }
            });
            return convertView;
        }


        public void refreshAdapter(List<IndexDataResponse.ParaEntity.ComplaintsEntity> data) {
            this.data = data;
            notifyDataSetChanged();
        }


        public void changeDeleteMode(boolean isShowDelete) {
            this.isShowDelete = isShowDelete;
            notifyDataSetChanged();
        }

        public void addDataAdapter(List<IndexDataResponse.ParaEntity.ComplaintsEntity> data) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            /**
             * 授课时间
             */
            TextView classTime;
            /**
             * 适学年龄
             */
            TextView classAge;
            /**
             * 课程名
             */
            TextView className;
            /**
             * 教龄
             */
            TextView learnYear;
            /**
             * 授课方式
             */
            TextView classMode;

            /**
             * 课程图片第一张
             */
            SimpleDraweeView classFirstImage;

            ImageView deleteClassImage;


        }


        private String getFirstPicture(String pictures) {
            if (pictures.contains(",")) {
                String[] arrays = pictures.split(",");
                return arrays[0];
            }
            return pictures;
        }

        public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
            mOnDeleteListener = onDeleteListener;
        }

        OnDeleteListener mOnDeleteListener;

    }

    interface OnDeleteListener {
        void onDelete(int id);
    }
}
