package com.june.costmanager;

import java.util.UUID;

import android.support.v4.app.Fragment;

import com.june.costmanager.fragments.IncomeFragment;

public class IncomeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		UUID incomeId = (UUID)getIntent().getSerializableExtra(IncomeFragment.EXTRA_INCOME_ID);
		
		return IncomeFragment.newInstance(incomeId);
	}

}
