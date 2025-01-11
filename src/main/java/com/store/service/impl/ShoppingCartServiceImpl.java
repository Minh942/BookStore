package com.store.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.store.model.CartModel;
import com.store.service.ShoppingCartService;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	public static Map<Integer, CartModel> map = new HashMap<>();

	@Override
	public void add(Integer id, CartModel entity) {
		if (map.get(id) != null) {
			this.update(id, entity.getQuality() + 1);
		} else {
			map.put(id, entity);
		}

	}

	@Override
	public double getAmount() {
		double amount = 0;
		Set<Integer> set = map.keySet();
		for (Integer i : set) {
			amount += map.get(i).getQuality() * map.get(i).getProduct().getPrice();
		}

		return amount;
	}

	@Override
	public void clearDiscount() {

	}

	@Override
	public void remove(Integer id) {
		map.remove(id);
	}

	@Override
	public void update(Integer id, int qty) {
		map.get(id).setQuality(qty);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Collection<CartModel> getItems() {
		return map.values();
	}

	@Override
	public int getCount() {
		int count = 0;
		Set<Integer> set = map.keySet();
		for (Integer i : set) {
			count++;
		}
		return count;
	}

	@Override
	public int getCountAllProduct() {
		return 10;
	}

}
