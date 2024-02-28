package com.store.service;

import java.util.List;

import com.store.entity.Address;
import com.store.entity.District;
import com.store.entity.Province;
import com.store.entity.Ward;
import com.store.model.AddressModel;

public interface AddressService {	
	List<Address> findListAddressByEmail(String username);
	List<Province> findAllProvince();
	List<District> findDistrictByIdProvince(Integer id);
	List<Ward> findWardByIdProvinceAndIdDistrict(Integer idProvince, Integer idDistrict);
	AddressModel createAddress(AddressModel addressModel);
	Address getAddressById(int parseInt);
	void delete(Address address);
	Address findAddressById(String username, int id);
	AddressModel getOneAddressById(int id);
	List<District> getListDistrictByAdressId(Integer id);
	List<Ward> getListWardByAdressId(Integer id);
	AddressModel updateAddress(AddressModel addressModel, Integer id);
}
