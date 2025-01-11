package com.store.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.store.dao.OrderDao;
import com.store.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.store.entity.Order;
import com.store.entity.Product;
import com.store.model.BestSellerModel;
import com.store.model.CartModel;
import com.store.model.DetailOrder;
import com.store.model.StatisticalOrder;
import com.store.model.StatisticalProductDay;
import com.store.model.StatisticalRevenue;
import com.store.model.StatisticalTotalOrder;
import com.store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
    OrderDao orderDao;

	@Autowired
    ProductDao productDao;

	@Override
	public List<Order> getOrderByName(String code) {
		return orderDao.getOrderByName(code);
	}

	@Override
	public void save(Order order) {
		orderDao.save(order);
	}


	@Override
	public List<Order> listOrderByCodeAndUsername(String id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		List<Order> list = new ArrayList<>();

		for (Order order : list) {
			String[] date = order.getDate().split("-");
			String result = date[2] + "/" + date[1] + "/" + date[0];
			order.setDate(result);
		}

		return list;
	}


	@Override
	public DetailOrder getDetailOrderByCode(String id) {
		DetailOrder detailOrder = new DetailOrder();
		List<Order> listOrder = orderDao.getOrderByName(id);

		detailOrder.setId(listOrder.get(0).getCode());
		detailOrder.setAddress(listOrder.get(0).getAddress().getDetail());
		detailOrder.setComment(listOrder.get(0).getComment());
		detailOrder.setDate(listOrder.get(0).getDate());

		detailOrder.setDistrict(listOrder.get(0).getAddress().getDistrict());
		detailOrder.setFullName(listOrder.get(0).getAddress().getUser().getFullname());
		String method = "";
		if (listOrder.get(0).getMethod().equals("1")) {
			method = "Thanh toán online khi nhận hàng";
		}
		if (listOrder.get(0).getMethod().equals("0")) {
			method = "Thanh toán trực tiếp khi nhận hàng";
		}
		detailOrder.setMethod(method);
		detailOrder.setPhone(listOrder.get(0).getAddress().getPhone());
		detailOrder.setProvince(listOrder.get(0).getAddress().getProvince());
		detailOrder.setWard(listOrder.get(0).getAddress().getWard());
		int subTotal = 0;
		int total = 0;

		List<CartModel> listCartModel = new ArrayList<CartModel>();
		for (Order list : listOrder) {
			CartModel cartModel = new CartModel();
			Product product = new Product();
			product = list.getProduct();
			cartModel.setProduct(product);
			cartModel.setQuality(list.getQuality());
			listCartModel.add(cartModel);

			subTotal = subTotal + list.getProduct().getPrice() * list.getQuality();
		}
		total = subTotal + 50000 - detailOrder.getDiscount();

		detailOrder.setSubTotal(subTotal);
		detailOrder.setTotal(total);
		detailOrder.setListOrder(listCartModel);

		return detailOrder;
	}

	@Override
	public void approveOrder(String id) {
		List<Order> listOrder = orderDao.getOrderByName(id);
		for (Order list : listOrder) {
			list.setStatus("1");
			orderDao.save(list);
		}
	}

	@Override
	public void cancelOrder(String id) {
		List<Order> listOrder = orderDao.getOrderByName(id);
		for (Order list : listOrder) {
			Product product = list.getProduct();
			product.setQuality(product.getQuality() + list.getQuality());
			list.setStatus("3");
			orderDao.save(list);
			productDao.save(product);
		}
	}

	@Override
	public void shippedOrder(String id) {
		List<Order> listOrder = orderDao.getOrderByName(id);
		for (Order list : listOrder) {
			list.setStatus("2");
			orderDao.save(list);
		}
	}
	@Override
	public void deleteOrder(String id) {
		List<Order> listOrder = orderDao.getOrderByName(id);
		for (Order list : listOrder) {
			orderDao.delete(list);
		}
	}

	@Override
	public List<StatisticalProductDay> listStatisticalProductDay() {
		return orderDao.listStatisticalProductDay();
	}

	@Override
	public List<StatisticalRevenue> listStatisticalRevenue(int month, int year) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MONTH, month - 1);

		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<StatisticalRevenue> listRevenue = new ArrayList<StatisticalRevenue>();

		for (int i = 1; i <= maxDay; i++) {
			long sum = 0;

			double total = (double) sum / 1000000;

			StatisticalRevenue statistical = new StatisticalRevenue();
			statistical.setPrice(total);
			statistical.setDate(i);
			listRevenue.add(statistical);
		}

		return listRevenue;
	}

	@Override
	public List<StatisticalRevenue> listStatisticalRevenueByMonth(int year) {
		List<StatisticalRevenue> listRevenue = new ArrayList<StatisticalRevenue>();
		for (int i = 1; i <= 12; i++) {
			long sum = 0;

			double total = (double) sum / 1000000;

			StatisticalRevenue statistical = new StatisticalRevenue();
			statistical.setPrice(total);
			statistical.setDate(i);
			listRevenue.add(statistical);

		}

		return listRevenue;
	}

	@Override
	public List<StatisticalRevenue> listStatisticalRevenueByYear(int year) {
		int minYear = year - 10;
		List<StatisticalRevenue> listRevenue = new ArrayList<StatisticalRevenue>();
		for (int i = 1; i <= 10; i++) {
			long sum = 0;

			double total = (double) sum / 1000000;

			StatisticalRevenue statistical = new StatisticalRevenue();
			statistical.setPrice(total);
			statistical.setDate(minYear + i);
			listRevenue.add(statistical);

		}

		return listRevenue;

	}

	@Override
	public StatisticalTotalOrder getStatisticalTotalOrderOnDay(int day, int month, int year) {
		int success = orderDao.getMaxOrderSuccessOnDay(day, month, year);
		int wait = orderDao.getMaxOrderWaitOnDay(day, month, year);
		int transport = orderDao.getMaxOrderTransportOnDay(day, month, year);
		int cancel = orderDao.getMaxOrderCancelOnDay(day, month, year);

		StatisticalTotalOrder totalOrder = new StatisticalTotalOrder(success, cancel, wait, transport);

		return totalOrder;
	}

	@Override
	public StatisticalTotalOrder getStatisticalTotalOrderOnMonth(int month, int year) {
		List<StatisticalOrder> orderSuccess = new ArrayList<>();
		List<StatisticalOrder> orderWait = new ArrayList<>();
		List<StatisticalOrder> orderTransport = new ArrayList<>();
		List<StatisticalOrder> orderCancel = new ArrayList<>();

		int success = orderSuccess.size();
		int wait = orderWait.size();
		int transport = orderTransport.size();
		int cancel = orderCancel.size();

		StatisticalTotalOrder totalOrder = new StatisticalTotalOrder(success, cancel, wait, transport);

		return totalOrder;
	}

	@Override
	public StatisticalTotalOrder getStatisticalTotalOrderOnYear(int year) {
		List<StatisticalOrder> orderSuccess = new ArrayList<>();
		List<StatisticalOrder> orderWait = new ArrayList<>();
		List<StatisticalOrder> orderTransport =new ArrayList<>();
		List<StatisticalOrder> orderCancel = new ArrayList<>();

		int success = orderSuccess.size();
		int wait = orderWait.size();
		int transport = orderTransport.size();
		int cancel = orderCancel.size();

		StatisticalTotalOrder totalOrder = new StatisticalTotalOrder(success, cancel, wait, transport);

		return totalOrder;
	}

	@Override
	public List<Integer> getListYearOrder() {

		List<Integer> listYear = new ArrayList<Integer>();

		return listYear;
	}

	@Override
	public StatisticalTotalOrder getStatisticalTotalOrderOnOption(int day, int month, int year) {
		StatisticalTotalOrder totalOrder = new StatisticalTotalOrder();

		if ((day == 0) && (month == 0)) {
			totalOrder = this.getStatisticalTotalOrderOnYear(year);
		} else if (day == 0) {
			totalOrder = this.getStatisticalTotalOrderOnMonth(month, year);
		} else {
			totalOrder = this.getStatisticalTotalOrderOnDay(day, month, year);
		}

		return totalOrder;
	}

	@Override
	public List<BestSellerModel> getListBestSellerProduct(Pageable topFour) {
		return orderDao.getListBestSellerProduct(topFour);
	}

	@Override
	public List<Product> listStatisticalProductWarehouse() {
		return productDao.listStatisticalProductWarehouse();
	}

}
