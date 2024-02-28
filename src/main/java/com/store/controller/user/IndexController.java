package com.store.controller.user;

import java.util.ArrayList;
import java.util.List;

import com.store.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.store.entity.Blog;
import com.store.entity.Manufacturer;
import com.store.entity.Product;
import com.store.model.BestSellerModel;
import com.store.model.ShowProduct;
import com.store.service.BlogService;
import com.store.service.CommentService;
import com.store.service.FavoriteService;
import com.store.service.ManufacturerService;
import com.store.service.OrderService;
import com.store.service.ParamService;
import com.store.service.ProductService;
import com.store.service.UserRoleService;

@Controller
public class IndexController {
	@Autowired
	UserRoleService userRoleService;

	@Autowired
	ProductService productService;

	@Autowired
	ManufacturerService manufacturerService;

	@Autowired
	CommentService commentService;

	@Autowired
	BlogService blogService;

	@Autowired
	ParamService paramService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	FavoriteService favoriteService;

	/**
	 * Hien thi trang chu cua giao dien nguoi dung
	 * 
	 * @return trang index.html
	 */
	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		return Constants.USER_DISPLAY_INDEX;
	}

	@ModelAttribute("manufacturer")
	public List<Manufacturer> manufacture(Model model) {
		List<Manufacturer> list = manufacturerService.findAll();
		return list;
	}

	@ModelAttribute("latestProduct")
	public List<List<ShowProduct>> getLatestProduct(Model model) {
		List<Product> list = productService.getListLatestProduct();

		List<ShowProduct> temp = new ArrayList<>();

		List<List<ShowProduct>> result = new ArrayList<List<ShowProduct>>();

		for (int i = 0; i < list.size(); i++) {
			int totalStar = commentService.getAllStarCommentByProductNameSearch(list.get(i).getNamesearch());

			ShowProduct showProduct = new ShowProduct();
			showProduct.setProduct(list.get(i));
			showProduct.setTotalStar(totalStar);
			temp.add(showProduct);
			if (i % 2 != 0) {
				result.add(temp);
				temp = new ArrayList<>();
			}
			if (i == (list.size() - 1)) {
				if (i % 2 == 0) {
					result.add(temp);
					temp = new ArrayList<>();
				}
			}
		}

		return result;
	}

	@ModelAttribute("viewsProduct")
	public List<ShowProduct> getViewsProduct(Model model) {
		List<Product> list = productService.getListViewsProduct();
		List<ShowProduct> listProduct = new ArrayList<ShowProduct>();

		for (Product product : list) {
			ShowProduct showProduct = new ShowProduct();
			int totalStar = commentService.getAllStarCommentByProductNameSearch(product.getNamesearch());
			showProduct.setProduct(product);
			showProduct.setTotalStar(totalStar);
			listProduct.add(showProduct);
		}

		return listProduct;
	}

	@ModelAttribute("listBlog")
	public List<Blog> getListBlog(Model model) {
		List<Blog> listBlog = blogService.getSixBlog();
		for (Blog blog : listBlog) {
			String uploadDay = paramService.convertDate(blog.getUploadday());
			blog.setUploadday(uploadDay);
		}
		return listBlog;
	}

	@ModelAttribute("listSale")
	public List<ShowProduct> getListProductSale(Model model) {
		List<Product> list = productService.getListProductSales();

		List<ShowProduct> listProduct = new ArrayList<ShowProduct>();

		for (Product product : list) {
			ShowProduct showProduct = new ShowProduct();
			int totalStar = commentService.getAllStarCommentByProductNameSearch(product.getNamesearch());
			showProduct.setProduct(product);
			showProduct.setTotalStar(totalStar);
			listProduct.add(showProduct);
		}

		return listProduct;
	}
	
	@ModelAttribute("listBestSeller")
	public List<ShowProduct> getListBestSeller(Model model){
		Pageable topFour = PageRequest.of(0, 4);
		
		List<BestSellerModel> list = orderService.getListBestSellerProduct(topFour);
		
		List<ShowProduct> listProduct = new ArrayList<ShowProduct>();
		
		for(BestSellerModel bestSeller: list) {
			ShowProduct showProduct = new ShowProduct();
			int totalStar = commentService.getAllStarCommentByProductNameSearch(bestSeller.getProduct().getNamesearch());
			showProduct.setProduct(bestSeller.getProduct());
			showProduct.setTotalStar(totalStar);
			listProduct.add(showProduct);
		}		
		return listProduct;
	}
	
	@ModelAttribute("listFavorite")
	public List<ShowProduct> demo(Model model) {
		Pageable topFour = PageRequest.of(0, 4);
		List<BestSellerModel> list = favoriteService.getListBestSellerProduct(topFour);
		
		List<ShowProduct> listProduct = new ArrayList<ShowProduct>();
		
		for(BestSellerModel bestSeller: list) {
			ShowProduct showProduct = new ShowProduct();
			int totalStar = commentService.getAllStarCommentByProductNameSearch(bestSeller.getProduct().getNamesearch());
			showProduct.setProduct(bestSeller.getProduct());
			showProduct.setTotalStar(totalStar);
			listProduct.add(showProduct);
		}		
		return listProduct;
	}

}
