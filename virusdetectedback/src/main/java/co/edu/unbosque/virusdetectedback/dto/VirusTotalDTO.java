package co.edu.unbosque.virusdetectedback.dto;

public class VirusTotalDTO {
	private Integer tId;
	private String id;
	private int malicious;
	private int suspicious;
	private String sha256;
	private String md5;
	private String sha1;

	public VirusTotalDTO() {
		// TODO Auto-generated constructor stub
	}

	public VirusTotalDTO(String id, int malicious, int suspicious, String sha256, String md5, String sha1) {
		super();
		this.id = id;
		this.malicious = malicious;
		this.suspicious = suspicious;
		this.sha256 = sha256;
		this.md5 = md5;
		this.sha1 = sha1;
	}

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMalicious() {
		return malicious;
	}

	public void setMalicious(int malicious) {
		this.malicious = malicious;
	}

	public int getSuspicious() {
		return suspicious;
	}

	public void setSuspicious(int suspicious) {
		this.suspicious = suspicious;
	}

	public String getSha256() {
		return sha256;
	}

	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	@Override
	public String toString() {
		return "VirusTotalDTO [tId=" + tId + ", id=" + id + ", malicious=" + malicious + ", suspicious=" + suspicious
				+ ", sha256=" + sha256 + ", md5=" + md5 + ", sha1=" + sha1 + "]";
	}

}
