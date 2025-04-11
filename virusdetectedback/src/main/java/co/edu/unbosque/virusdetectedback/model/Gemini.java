package co.edu.unbosque.virusdetectedback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gemini")
public class Gemini {
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer tId;
	@Column(columnDefinition = "TEXT")
	private String text;
	@Column(unique = true)
	private String name;

	public Gemini() {
		// TODO Auto-generated constructor stub
	}

	public Gemini(String text, String name) {
		super();
		this.text = text;
		this.name = name;
	}

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Gemini [tId=" + tId + ", text=" + text + ", name=" + name + "]";
	}

}
