package org.abk;


import org.abk.dao.ProduitRepository;
import org.abk.entities.Produit;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CataMvc4Application {
	
	
	public static void main(String[] args) throws BeansException {
		
		//context pour les test 
		 ApplicationContext ctx = SpringApplication.run(CataMvc4Application.class, args);
		 System.out.println("debut");
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		/*produitRepository.save(new Produit ("ord HP l4352",670,90));
		produitRepository.save(new Produit ("azuz 432",670,90));
		produitRepository.save(new Produit ("drive disk compact",450,12));
		produitRepository.save(new Produit ("imp hp 5400",45,10));*/
		
		produitRepository.findAll().forEach(P-> System.out.println(P.getDesignation()));
		
	}

}
