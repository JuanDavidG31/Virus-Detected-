
package co.edu.unbosque.virusdetectedback.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.edu.unbosque.virusdetectedback.dto.GeminiDTO;
import co.edu.unbosque.virusdetectedback.dto.VirusTotalDTO;

public class ExternalHTTPRequestHandler {

	private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public static String doGetAndParse(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-type", "application/json").build();

		HttpResponse<String> response = null;

		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("status code -> " + response.statusCode());
		String uglyJson = response.body();
		return prettyPrintUsingGson(uglyJson);
	}

	public static String prettyPrintUsingGson(String uglyJson) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		String prettyJsonString = gson.toJson(jsonElement);
		return prettyJsonString;

	}

	public static String toPostFileAndConvertToDTOVirus(String url, String apiKey, File file) {
		String boundary = "----JavaMultipartBoundary" + System.currentTimeMillis();
		HttpResponse<String> response = null;

		try {
			var byteStream = new ByteArrayOutputStream();
			var writer = new PrintWriter(new OutputStreamWriter(byteStream, StandardCharsets.UTF_8), true);

			writer.append("--").append(boundary).append("\r\n");
			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName())
					.append("\"\r\n");
			writer.append("Content-Type: application/octet-stream\r\n\r\n");
			writer.flush();

			Files.copy(file.toPath(), byteStream);
			byteStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
			writer.append("--").append(boundary).append("--\r\n");
			writer.flush();

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("x-apikey", apiKey)
					.header("Content-Type", "multipart/form-data; boundary=" + boundary)
					.POST(HttpRequest.BodyPublishers.ofByteArray(byteStream.toByteArray())).build();

			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
		return json.getAsJsonObject("data").get("id").getAsString();
	}

	public static VirusTotalDTO toGetAndConvertToDTOVirus(String url, String apiKey) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("x-apikey", apiKey).build();

		HttpResponse<String> response = null;

		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}

		String json = response.body();
		ObjectMapper mapper = new ObjectMapper();

		VirusTotalDTO dto = new VirusTotalDTO();

		try {
			JsonNode root = mapper.readTree(json);
			dto.setId(root.at("/data/id").asText());
			dto.setMalicious(root.at("/data/attributes/last_analysis_stats/malicious").asInt());
			dto.setSuspicious(root.at("/data/attributes/last_analysis_stats/suspicious").asInt());
			dto.setSha256(root.at("/meta/file_info/sha256").asText());
			dto.setMd5(root.at("/meta/file_info/md5").asText());
			dto.setSha1(root.at("/meta/file_info/sha1").asText());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dto;
	}

	public static GeminiDTO postAndConvertToDTOGemini(String urlWithApiKey, String jsonRequestBody) {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlWithApiKey))
				.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
				.build();

		HttpResponse<String> response;

		try {
			response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}

		String jsonResponse = response.body();
		ObjectMapper mapper = new ObjectMapper();
		GeminiDTO dto = new GeminiDTO();

		try {
			JsonNode root = mapper.readTree(jsonResponse);
			String rawText = root.at("/candidates/0/content/parts/0/text").asText();
			System.out.println("Texto recibido de Gemini:\n" + rawText);

			String cleanedText = rawText.replaceAll("(?i)^```json\\s*", "") // quita encabezado ```json
					.replaceAll("(?i)^```\\s*", "") // o solo ```
					.replaceAll("(?i)\\s*```$", "") // quita cierre ```
					.trim();

			JsonNode textJson = mapper.readTree(cleanedText);
			String name = textJson.get("name").asText();
			String description = textJson.get("text").asText();

			dto.setText(description);
			dto.setName(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dto;
	}

}
