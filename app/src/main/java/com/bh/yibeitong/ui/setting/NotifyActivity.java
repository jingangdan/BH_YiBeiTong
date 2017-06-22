package com.bh.yibeitong.ui.setting;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bh.yibeitong.R;
import com.bh.yibeitong.base.BaseActivity;

/**
 * Created by jingang on 2016/11/10.
 * 通知消息
 */

public class NotifyActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_startTime, tv_endTime;
    private ImageView iv_notify_back;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        initData();

    }

    /**
     * 组件 初始化
     */
    public void initData() {
        tv_startTime = (TextView) findViewById(R.id.tv_startTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);

        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);

        iv_notify_back = (ImageView) findViewById(R.id.iv_notify_back);
        iv_notify_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_notify_back:
                finish();
                break;

            case R.id.tv_startTime:
                //开始时间
                TimePickerDialog time = new TimePickerDialog(NotifyActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (minute >= 0 && minute <= 9) {
                                    str = String.valueOf(minute);
                                    str = "0" + minute;
                                    tv_startTime.setText(hourOfDay + ":" + str);
                                } else {
                                    tv_startTime.setText(hourOfDay + ":" + minute);
                                }

                            }
                        }, 12, 00, true);
                time.show();

                break;

            case R.id.tv_endTime:
                //结束时间
                TimePickerDialog endTime = new TimePickerDialog(NotifyActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (minute >= 0 && minute <= 9) {
                                    str = String.valueOf(minute);
                                    str = "0" + minute;
                                    tv_endTime.setText(hourOfDay + ":" + str);
                                } else {
                                    tv_endTime.setText(hourOfDay + ":" + minute);
                                }

                            }
                        }, 12, 00, true);
                endTime.show();

                break;

            default:
                break;
        }
    }
}
