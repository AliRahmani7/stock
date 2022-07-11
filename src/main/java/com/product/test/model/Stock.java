package com.product.test.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Documen
public class Stock {
	 @Id
	 private String id;
	 private String producId;
	 private long quantity;
	public Stock(String id, String producId, long quantity) {
		super();
		this.id = id;
		this.producId = producId;
		this.quantity = quantity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProducId() {
		return producId;
	}
	public void setProducId(String producId) {
		this.producId = producId;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Stock [id=" + id + ", producId=" + producId + ", quantity=" + quantity + "]";
	}
}
