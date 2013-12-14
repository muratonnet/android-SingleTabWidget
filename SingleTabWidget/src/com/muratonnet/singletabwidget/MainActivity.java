package com.muratonnet.singletabwidget;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.singletabwidget.R;
import com.muratonnet.widget.SingleTabWidget;
import com.muratonnet.widget.SingleTabWidget.OnTabChangedListener;

public class MainActivity extends FragmentActivity {

	private final String FRAGMENT_TAG_FORMAT = "Fragment_%s";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeTabs();
	}

	private void initializeTabs() {
		final SingleTabWidget tabWidget = (SingleTabWidget) findViewById(R.id.tabs);

		tabWidget.setLayout(R.layout.single_tab_indicator);
		tabWidget.addTab(R.drawable.single_tab_indicator_android_red_selector,
				getString(R.string.tab_1));
		tabWidget.addTab(R.drawable.single_tab_indicator_android_green_selector,
				getString(R.string.tab_2));
		tabWidget.addTab(R.drawable.single_tab_indicator_android_blue_selector,
				getString(R.string.tab_3));

		tabWidget.setOnTabChangedListener(new OnTabChangedListener() {
			@Override
			public void onTabChanged(int index) {
				addFragment(index);
			}
		});
		tabWidget.setCurrentTab(0);
		addFragment(0);
	}

	private void addFragment(int index) {

		Fragment fragment;
		String tag = String.format(FRAGMENT_TAG_FORMAT, index + 1);

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		List<Fragment> fragments = manager.getFragments();
		if (fragments != null) {
			for (Fragment f : fragments) {
				transaction.hide(f);
			}
		}

		fragment = manager.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = createFragment(index);
			transaction.add(R.id.fragment_container, fragment, tag);
		} else {
			transaction.show(fragment);
		}
		transaction.commit();

	}

	private Fragment createFragment(int index) {

		switch (index) {
		case 0:
			return new Tab1Fragment();
		case 1:
			return new Tab2Fragment();
		case 2:
			return new Tab3Fragment();
		default:
			return null;
		}
	}

}
