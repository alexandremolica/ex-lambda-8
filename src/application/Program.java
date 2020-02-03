package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		/*
		 * exemplo usando lambda, pipeline, stream
		 * ler arquivo, achar a media e listar os
		 * produto que estao abaixo da media
		 */
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			// carregando lista de produtos
			List<Product> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product (fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			// preco medio dos produtos
			double avg = list.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x,y) -> x +y) / list.size();
			
			System.out.println("Average price: "+ String.format("%.2f",avg));
			
			// nomes de produtos cujos precos sao abaixo da media
			//ordenacao decrecente
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
					
					
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)
					.map(p -> p.getName())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
					
			names.forEach(System.out::println);
		}
		catch (IOException e) {
			System.out.println("Error : "+ e.getMessage());
		}
		sc.close();
		
	}

}
