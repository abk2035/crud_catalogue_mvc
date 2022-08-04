package org.abk.web;

import java.util.Optional;

import org.abk.dao.ProduitRepository;
import org.abk.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(value="/user/index")
	public String index(Model model ,
			@RequestParam(name="page",defaultValue="0")int p,
			@RequestParam(name="size",defaultValue="5")int s,
			@RequestParam(name="motCle",defaultValue="")String mc) {
		//page des produits
		Page<Produit> pageProduits = produitRepository.chercher("%"+mc+"%",PageRequest.of(p,s)) ;
		
		model.addAttribute("listProduits",pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages",pages); 
		model.addAttribute("size", s);
		model.addAttribute("pageCourante",p);
		model.addAttribute("motCle",mc);
		
		return "produits" ;
	}
	
	//action de suppression
	@RequestMapping(value="/admin/delete" , method=RequestMethod.GET)
	public String delete(Long id,int page,int size,String motCle) {
		produitRepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&size="+size+"&motCle="+motCle;
	}
	
	//action pour ajouter les produits
	@RequestMapping(value="/admin/form" , method=RequestMethod.GET)
	public String formProduit(Model model) {
		model.addAttribute("produit",new Produit());
		return "FormProduit";
	}
	
	//operation pour ajouter un produit
	@RequestMapping(value="/admin/save" , method=RequestMethod.POST)
	public String save(Model model, @Validated Produit produit ,BindingResult bindingResult ) {
		if(bindingResult.hasErrors()) {
			return "FormProduit";
		}
		 produitRepository.save(produit);
		 return "Confirmation";
	 }
  
	 
	//editer un produit
		@RequestMapping(value="/admin/edit" , method=RequestMethod.GET)
		public String edit(Model model ,Long id) {
			// presente un bugg ********************************************************************************
		        Produit p= produitRepository.getById(id);
		        //Produit p = createProduit.get();
				model.addAttribute("produit",p);
				return "EditProduit";
		}
		
		
  //action par defaut
	@RequestMapping(value="/")
	public String home() {
		return "redirect:/user/index";
	}
	
	// action interdite 
	@RequestMapping(value="/403")
	public String accessDenied() {
		return "403";
	}
	
	// login page
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
 
}
