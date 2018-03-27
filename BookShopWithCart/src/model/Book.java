
//ORM mapping - ORM - Object Relational Mapping

package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //this means that this class will be saved as a table in database
//Optional we can give a name of the table different than the class
//@Table(name="BooksTable")
public class Book {
	@Id//to make it primary key
	@GeneratedValue
	private int id;
	@Column
	private String title;
	@Column
	private String author;
	@Column
	private String description;
	@Column
	private BigDecimal price;
	
	public Book () {};
	
	public Book(int id, String title, String author, String description, BigDecimal price) {
		
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
		this.price =  price;
	}

	public Book(String title, String author, String description, BigDecimal price) {
				
		this.title = title;
		this.author = author;
		this.description = description;
		this.price =  price;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\nBook id: " + id + "\n"+
				"Title: " + title + "\n"+
				"Author: " + author + "\n"+
				"Description: " + description + "\n"+
				"Price: " + price + "\n";
	}
	
}
