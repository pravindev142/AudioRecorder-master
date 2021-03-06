

package com.thinkdev.audiorecorder.app.lostrecords;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkdev.audiorecorder.ARApplication;
import com.thinkdev.audiorecorder.Mapper;
import com.thinkdev.audiorecorder.R;
import com.thinkdev.audiorecorder.app.browser.FileBrowserActivity;
import com.thinkdev.audiorecorder.app.info.ActivityInformation;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

public class LostRecordsActivity extends Activity implements LostRecordsContract.View {

	public static final String EXTRAS_RECORDS_LIST = "records_list";

	private LostRecordsContract.UserActionsListener presenter;
	private TextView txtEmpty;

	private LostRecordsAdapter adapter;

	public static Intent getStartIntent(Context context, ArrayList<RecordItem> data) {
		Intent intent = new Intent(context, LostRecordsActivity.class);
		intent.putParcelableArrayListExtra(EXTRAS_RECORDS_LIST, data);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		setTheme(ARApplication.getInjector().provideColorMap().getAppThemeResource());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_records);

		ImageButton btnBack = findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ARApplication.getInjector().releaseLostRecordsPresenter();
				finish();
			}
		});
		findViewById(R.id.btn_file_browser).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(FileBrowserActivity.getStartIntent(getApplicationContext()));
			}
		});

		txtEmpty = findViewById(R.id.txtEmpty);
		Button btnDeleteAll = findViewById(R.id.btn_delete_all);
		btnDeleteAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AndroidUtils.showSimpleDialog(
						LostRecordsActivity.this,
						R.drawable.ic_delete_forever,
						R.string.warning,
						R.string.delete_all_records,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								presenter.deleteRecords(adapter.getData());
							}
						}
				);
			}
		});

		RecyclerView recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		adapter = new LostRecordsAdapter();
		adapter.setOnItemClickListener(new LostRecordsAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(RecordItem record) {
				presenter.onRecordInfo(Mapper.toRecordInfo(record));
			}

			@Override
			public void onRemoveItemClick(final RecordItem record) {
				AndroidUtils.showSimpleDialog(
						LostRecordsActivity.this,
						R.drawable.ic_delete_forever,
						R.string.warning,
						getApplicationContext().getString(R.string.delete_record, record.getName()),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								presenter.deleteRecord(record);
							}
						}
				);
			}
		});
		recyclerView.setAdapter(adapter);
		if (getIntent() != null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null && extras.containsKey(EXTRAS_RECORDS_LIST)) {
				adapter.setData(extras.<RecordItem>getParcelableArrayList(EXTRAS_RECORDS_LIST));
			}
		}

		presenter = ARApplication.getInjector().provideLostRecordsPresenter();
	}

	@Override
	protected void onStart() {
		super.onStart();
		presenter.bindView(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (presenter != null) {
			presenter.unbindView();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		ARApplication.getInjector().releaseLostRecordsPresenter();
	}

	@Override
	public void showLostRecords(List<RecordItem> items) {
		adapter.setData(items);
	}

	@Override
	public void showRecordInfo(RecordInfo info) {
		startActivity(ActivityInformation.getStartIntent(getApplicationContext(), info));
	}

	@Override
	public void onDeletedRecord(int id) {
		adapter.removeItem(id);
		if (adapter.getItemCount() == 0) {
			showEmpty();
		}
	}

	@Override
	public void showEmpty() {
		adapter.clearData();
		txtEmpty.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideEmpty() {
		txtEmpty.setVisibility(View.GONE);
	}

	@Override
	public void showProgress() {

	}

	@Override
	public void hideProgress() {

	}

	@Override
	public void showError(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showError(int resId) {
		Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showMessage(int resId) {
		Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG).show();
	}
}
