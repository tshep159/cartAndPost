package org.kamogelofoundations.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.kamogelofoundations.dto.Category;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.repository.CategoryRepository;
import org.kamogelofoundations.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	@GetMapping("/all")
	public String index(Model model) {
		List<Product> products = productRepo.findAll();
		List<Category> categories = categoryRepo.findAll();
		HashMap<Integer, String> categoriesMap = new HashMap<>();
		categories.forEach(c -> categoriesMap.put(c.getId(), c.getName()));
		model.addAttribute("products", products);
		model.addAttribute("categoriesMap", categoriesMap);
		//long count = productRepo.count();
		return "/admin/products/index";
	}

	@GetMapping("/add")
	public String add(Product product, Model model) {
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);

		return "/admin/products/add";
	}

	@PostMapping("/add")
	public String add(@Valid Product product, BindingResult bindingResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {
		List<Category> categories = categoryRepo.findAll();

		/*if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categories);
			return "/admin/products/add";
		}
*/
		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + filename);

		if (filename.endsWith("jpg") || filename.endsWith("png"))
			fileOK = true;

		redirectAttributes.addFlashAttribute("message", "Product added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		Product productExists = productRepo.findBySlug(slug);

		if (!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else {
			product.setSlug(slug);
			product.setImage(filename);
			productRepo.save(product);

			Files.write(path, bytes);
		}
			System.out.print(product.getImage()+ product.getName());
		return "redirect:/admin/products/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {

		Product product = productRepo.getOne(id);
		List<Category> categories = categoryRepo.findAll();

		model.addAttribute("product", product);
		model.addAttribute("categories", categories);

		return "/admin/products/edit";
	}

	@PostMapping("/edit")
	public String edit(@Valid Product product, BindingResult bindingResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		// get the current product so i can show the appropriate name if there's errors
		Product currentProduct = productRepo.getOne(product.getId());
		List<Category> categories = categoryRepo.findAll();

		if (bindingResult.hasErrors()) {
			model.addAttribute("productName", currentProduct.getName());
			model.addAttribute("categories", categories);
			return "/admin/products/edit";
		}

		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String filename = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + filename);

		if (!file.isEmpty()) {
			if (filename.endsWith("jpg") || filename.endsWith("png"))
				fileOK = true;
		} else
			fileOK = true;

		redirectAttributes.addFlashAttribute("message", "Product edited");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		Product productExists = productRepo.findBySlugAndIdNot(slug, product.getId());

		if (!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", product);
		} else {
			product.setSlug(slug);
			if (!file.isEmpty()) {
				Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(path2);
				product.setImage(filename);
				Files.write(path, bytes);
			} else {
				product.setImage(currentProduct.getImage());
			}
			productRepo.save(product);
		}

		return "redirect:/admin/products/edit/" + product.getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException {

			Product product = productRepo.getOne(id);
		Path path = Paths.get("src/main/resources/static/media/" + product.getImage());
		Files.delete(path);
		//productRepo.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Product deleted");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/products";
	}
	
	
	
	//adding category
	
	@GetMapping(value="/categories")
	public ModelAndView addCat() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("/admin/categories/index");
		return mv;
		
		
		
	}
	
}
