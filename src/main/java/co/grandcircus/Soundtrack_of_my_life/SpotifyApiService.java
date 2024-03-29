package co.grandcircus.Soundtrack_of_my_life;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import co.grandcircus.Soundtrack_of_my_life.model.spotify.AlbumtItems;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.ArtistItems;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.PlaylistItems;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.SpotifyResponse;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.TokenResponse;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.TrackItems;
import co.grandcircus.Soundtrack_of_my_life.model.spotify.Type;

@Component
public class SpotifyApiService {
	
	
	private final String CLIENT_ID = "d6a515f31243414b8a32f69ef1bcbac4";
	@Value("${spotify.CLIENT_SECRET}")
	private String CLIENT_SECRET;
	private RestTemplate restTemplate;
    
    {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.USER_AGENT, "Spring");
            return execution.execute(request, body);
        };
        restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();
    }
    
    
    public String getAccessToken() {
    	String url = "https://accounts.spotify.com/api/token";
    	
    	// request body
    	MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();
    	body.add("grant_type", "client_credentials");
    	
    	// headers
    	HttpHeaders headers = new HttpHeaders();
    	String unencoded = CLIENT_ID + ":" + CLIENT_SECRET; 
    	String encoded = Base64.getEncoder().encodeToString(unencoded.getBytes());
		headers.add("Authorization", "Basic " + encoded);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		TokenResponse response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), 
				TokenResponse.class).getBody();
    	
		String accessToken = response.getAccess_token(); 
		
		return accessToken;
    }

    
    public List<TrackItems> showTracks(String q, Type type){
    	String accessToken = getAccessToken();
    	String limit = "10";
    	String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
    			.queryParam("q", q)
				.queryParam("type", type)
				.queryParam("limit", limit)
				.toUriString();
    	
    	String bearerString = "Bearer " + accessToken;
    	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerString);
		SpotifyResponse response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), 
				SpotifyResponse.class).getBody();
		List<TrackItems> myTrack = response.getTrack().getItems();
		return myTrack;
    	
    }
    
    public List<PlaylistItems> showPlaylists(String q, Type type) {
    	String accessToken = getAccessToken();
    	String limit = "10";
    	String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
				.queryParam("q", q)
				.queryParam("type", type)
				.queryParam("limit", limit)
				.toUriString();
    	
    	String bearerString = "Bearer " + accessToken;
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerString);
		
		SpotifyResponse response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), 
    													SpotifyResponse.class).getBody();
		List<PlaylistItems> myList = response.getPlaylist().getItems();
		
    	return myList;  	
    }
    
    public List<ArtistItems> showArtists(String q, Type type){
    	String accessToken = getAccessToken();
    	String limit = "10";
    	String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
    			.queryParam("q", q)
				.queryParam("type", type)
				.queryParam("limit", limit)
				.toUriString();
    	String bearerString = "Bearer " + accessToken;
    	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerString);
		SpotifyResponse response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), 
				SpotifyResponse.class).getBody();
		List<ArtistItems> myArtist = response.getArtist().getItems();
		return myArtist;
   }
    
    public List<AlbumtItems> showAlbums(String q, Type type){
    	String accessToken = getAccessToken();
    	String limit = "10";
    	String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
    			.queryParam("q", q)
				.queryParam("type", type)
				.queryParam("limit", limit)
				.toUriString();
    	String bearerString = "Bearer " + accessToken;
    	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", bearerString);
		SpotifyResponse response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), 
				SpotifyResponse.class).getBody();
		List<AlbumtItems> myAlbum = response.getAlbum().getItems();
		return myAlbum;
     }
    
  

}//apiService class

