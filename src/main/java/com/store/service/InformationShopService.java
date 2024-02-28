package com.store.service;

import java.util.List;

import com.store.entity.InformationShop;
import com.store.model.ShopModel;

public interface InformationShopService {

	ShopModel createInformationShop(ShopModel shopModel);

	List<InformationShop> findAll();

	void delete(Integer id);

	ShopModel updateActive(ShopModel shopModel);

	ShopModel getOneShopById(Integer id);

	ShopModel updateInformation(ShopModel shopModel);

	InformationShop getOneInformationShop();

}
