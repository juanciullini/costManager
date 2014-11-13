package com.june.costmanager;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;
import com.june.costmanager.fragments.IncomeFragment;

public class IncomePagerActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private ArrayList<Incoming> mIncomes;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mIncomes = IncomList.get(this).getIncoms();
		
		FragmentManager fm = getSupportFragmentManager();
		
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return mIncomes.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				Incoming i = mIncomes.get(arg0);
				return IncomeFragment.newInstance(i.getId());
			}
		});
		// obtiene el intent que envia ListFragment cuando una de las filas es tapeada
		UUID incomeId = (UUID)getIntent().getSerializableExtra(IncomeFragment.EXTRA_INCOME_ID);
		
		// ver de hacerlo con alguna estructura de acceso directo por UUID, asi me evito la busqueda
		for (int i = 0; i < mIncomes.size(); i++) {
			if (mIncomes.get(i).getId().equals(incomeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				Incoming income = mIncomes.get(pos);
				if (income.getTitle() != null) {
					setTitle(income.getTitle()); 
				}

				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}
