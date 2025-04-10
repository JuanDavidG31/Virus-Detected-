package co.edu.unbosque.virusdetectedback.dto;

public class GeminiDTO {
	private Integer id;
	private String text;
	private String name;

	public GeminiDTO() {
		// TODO Auto-generated constructor stub
	}

	public GeminiDTO(String text, String name) {
		super();
		this.text = text;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "GeminiDTO [id=" + id + ", text=" + text + ", name=" + name + "]";
	}

}
