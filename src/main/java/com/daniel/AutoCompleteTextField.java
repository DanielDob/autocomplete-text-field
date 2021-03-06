package com.daniel;


import java.util.ArrayList;
import java.util.HashMap;

import com.daniel.date.Item;
import com.daniel.date.ItemsList;
import com.daniel.gui.Result;
import com.daniel.gui.ResultPane;
import com.daniel.logic.ListenerControl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

public class AutoCompleteTextField extends TextField implements Controls{
	
	/**
	 *  index of hovered item 
	 */
	private IntegerProperty hoverProperty = new SimpleIntegerProperty();
	private ResultPane resultPane;
	private ItemsList itemsList;
	private HashMap<Item<? extends Object>, Result> resultMap = new HashMap<>();
	
	public static int searchMode=SEARCH_FROM_BEGGINING;
	public static int upperCase=IGNORE_UPPER_CASES;
	
	public AutoCompleteTextField(ArrayList<? extends Object> items) {
		itemsList= new ItemsList(items);
		init();
	}
	public AutoCompleteTextField(Object[] items) {
		itemsList= new ItemsList(items);
		init();
	}
	private void init() {
		ListenerControl listenerControl = new ListenerControl();
		this.setOnKeyPressed(listenerControl.keyPressedAutocomplete(this));
		this.textProperty().addListener(listenerControl.onChangeTextListenerAutocomplete(this));
		this.resultPane= new ResultPane(this);
		hoverProperty.set(Integer.MIN_VALUE);
		hoverProperty.addListener(listenerControl.nrHoverValueObservable(resultPane));
	}
	
	public int getNrHover() {
		return hoverProperty.get();
	}
	public void setNrHover(int nrHover) {
		this.hoverProperty.set(nrHover);;
	}
	public ResultPane getResultPane() {
		return resultPane;
	}
	public void setResultPane(ResultPane resultPane) {
		this.resultPane = resultPane;
	}
	public ItemsList getItemsList() {
		return itemsList;
	}
	public void setItemsList(ItemsList itemsList) {
		this.itemsList = itemsList;
	}
	
	public HashMap<Item<? extends Object>, Result> getResultMap() {
		return resultMap;
	}
	public void setResultMap(HashMap<Item<? extends Object>, Result> resultMap) {
		this.resultMap = resultMap;
	}
	
	public static int getSearchMode() {
		return searchMode;
	}
	public static void setSearchMode(int searchMode) {
		AutoCompleteTextField.searchMode = searchMode;
	}
	public static int getUpperCase() {
		return upperCase;
	}
	public static void setUpperCase(int upperCase) {
		AutoCompleteTextField.upperCase = upperCase;
	}
	public void showResult() {
		ArrayList<Result> array = new ArrayList<>();
		for(Item<? extends Object> item : itemsList.getResultList())
			array.add(resultMap.get(item));
		resultPane.getResultList().removeAll(resultPane.getResultList());
		resultPane.setResultList(array);
		resultPane.show(this);
		
	}
}
