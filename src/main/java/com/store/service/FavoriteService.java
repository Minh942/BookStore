package com.store.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.store.entity.Favorite;
import com.store.model.BestSellerModel;

public interface FavoriteService {

	Favorite create(int id);

	List<Favorite> getListFavoriteByEmail();

	void delete(int id);

	Favorite getOneFavorite(int id);

	List<BestSellerModel> getListBestSellerProduct(Pageable topFour);

}
