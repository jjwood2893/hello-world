package com.techelevator.dao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.Collection;
import com.techelevator.model.Comic;
import com.techelevator.model.User;

@Service
public class CollectionSqlDAO implements CollectionDAO {
	private JdbcTemplate jdbcTemplate;
	private UserDAO userDAO;


	public CollectionSqlDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
		this.userDAO = userDAO;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createCollection(Principal principal, String collectionName, boolean isPublic) {
		String username = principal.getName();
		User user = userDAO.findByUsername(username);
		Long userId = user.getId();
		String sql = "INSERT INTO collections (user_id, name, isPublic) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, userId, collectionName, isPublic);
	}

	@Override
	public Collection getCollection(Long collectionId) {
		String sql = "SELECT * FROM collections WHERE collection_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collectionId);
		if (rs.next()) {
			return mapRowToCollection(rs);
		} else {
			return null;
		}
	}
	
	private Comic mapRowToComic(SqlRowSet rs) {
		Comic comic = new Comic();
		comic.setComicId(rs.getLong("comic_id"));
		comic.setTitle(rs.getString("title"));
		comic.setIssue(rs.getLong("issue"));
		comic.setAuthor(rs.getString("author"));
		comic.setArtist(rs.getString("artist"));
		comic.setPublisher(rs.getString("publisher"));
		comic.setCoverUrl(rs.getString("coverUrl"));
		return comic;
	}
	
	private Collection mapRowToCollection(SqlRowSet rs) {
		Collection collection = new Collection();
		collection.setCollectionId(rs.getLong("collection_id"));
		collection.setName(rs.getString("name"));
		collection.setUserId(rs.getLong("user_id"));
		collection.setPublicCollection(rs.getBoolean("isPublic"));
		return collection;

	}

	@Override
	public List<Comic> viewCollection(Principal principal, Long collectionId) {
		String sql = "SELECT comics.comic_id, comics.title, comics.issue, comics.author, comics.artist, comics.publisher, comics.coverUrl FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.collection_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collectionId);
		List<Comic> listOfComics = new ArrayList<Comic>();
		while (rs.next()) {
			listOfComics.add(mapRowToComic(rs));
		}

		Long collectionUserId = getCollection(collectionId).getUserId();
		Long userId = userDAO.findByUsername(principal.getName()).getId();

		if (userId == collectionUserId || getCollection(collectionId).isPublicCollection()) {
			return listOfComics;
		} else {
			return null;
		}

	}

	@Override
	public List<Comic> viewCollection(Long collectionId) {
		String sql = "SELECT comics.comic_id, comics.title, comics.issue, comics.author, comics.artist, comics.publisher, comics.coverUrl FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.collection_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collectionId);
		List<Comic> listOfComics = new ArrayList<Comic>();
		while (rs.next()) {
			listOfComics.add(mapRowToComic(rs));
		}

		if (getCollection(collectionId).isPublicCollection()) {
			return listOfComics;
		} else {
			return null;
		}

	}

	@Override
	public List<Collection> viewCollections(Principal principal) {
		String sql = "";
//		if (principal!=null) 
		sql = "SELECT collections.* FROM collections JOIN users ON users.user_id = collections.user_id "
				+ "WHERE collections.user_id = ? OR collections.isPublic = TRUE";

//		else {
//			sql = "SELECT * FROM collections WHERE collections.isPublic = TRUE";
//		}
		Long userId = userDAO.findByUsername(principal.getName()).getId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
		List<Collection> listOfCollections = new ArrayList<Collection>();
		while (rs.next()) {
			Collection currentCollection = mapRowToCollection(rs);
			listOfCollections.add(currentCollection);
		}

		return listOfCollections;

	}

	@Override
	public List<Collection> viewMyCollections(Principal principal, String username) {
		String sql = "SELECT * FROM collections WHERE collections.user_id = ?";
		Long userId = userDAO.findByUsername(username).getId();
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
		List<Collection> listOfCollections = new ArrayList<Collection>();
		while (rs.next()) {
			Collection currentCollection = mapRowToCollection(rs);
			listOfCollections.add(currentCollection);
		}

		return listOfCollections;
	}

	@Override
	public List<Collection> viewCollections() {
		String sql = "SELECT * FROM collections WHERE collections.isPublic = TRUE";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		List<Collection> listOfCollections = new ArrayList<Collection>();
		while (rs.next()) {
			Collection currentCollection = mapRowToCollection(rs);
			listOfCollections.add(currentCollection);
		}

		return listOfCollections;

	}
	
//	@Override
//	public int getNumberOfIssuesForCharacterInCollection(Principal principal, int collection_id,
//			String maincharacter) {
//		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
//				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.collection_id = ? AND comics.maincharacter = ?";
//		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {collection_id, maincharacter}, Integer.class);
//		return numberOfIssues;
//	}
	
	@Override
	public int getNumberOfIssuesForAuthorInCollection(Principal principal, int collection_id, String author) {
		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.collection_id = ? AND comics.author = ?";
		author = author.replace("_", " ");
		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {collection_id, author}, Integer.class);
		return numberOfIssues;
	}

	@Override
	public int getNumberOfIssuesForArtistInCollection(Principal principal, int collection_id, String artist) {
		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.collection_id = ? AND comics.artist = ?";
		artist = artist.replace("_", " ");
		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {collection_id, artist}, Integer.class);
		return numberOfIssues;
	}

//	@Override
//	public int getNumberOfIssuesForCharacterInCollections(Principal principal, String maincharacter) {
//		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
//				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.isPublic AND comics.maincharacter = ?";
//		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {maincharacter}, Integer.class);
//		return numberOfIssues;
//	}

	@Override
	public int getNumberOfIssuesForAuthorInCollections(Principal principal, String author) {
		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.isPublic AND comics.author = ?";
		author = author.replace("_", " ");
		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {author}, Integer.class);
		return numberOfIssues;
	}

	@Override
	public int getNumberOfIssuesForArtistInCollections(Principal principal, String artist) {
		String sql = "SELECT COUNT(ALL comics.title) FROM collections INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id"
				+ " INNER JOIN comics ON comics.comic_id = collection_comic.comic_id WHERE collections.isPublic AND comics.artist = ?";
		artist = artist.replace("_", " ");
		int numberOfIssues = jdbcTemplate.queryForObject(sql, new Object[] {artist}, Integer.class);
		return numberOfIssues;
	}

	@Override
	public void removeComicFromCollection(int collection_id, int comic_id) {
		String sql = "DELETE FROM collection_comic WHERE collection_id = ? AND comic_id = ?";
		jdbcTemplate.update(sql, collection_id, comic_id);
	}

	@Override
	public String getMostPopularCharacterInCollections() {
		String sql = "SELECT COUNT(comic_character.character_id), characters.charactername\r\n" + 
				"FROM collections \r\n" + 
				"INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id\r\n" + 
				"INNER JOIN comics ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"INNER JOIN comic_character ON comic_character.comic_id = comics.comic_id\r\n" + 
				"INNER JOIN characters ON comic_character.character_id = characters.character_id \r\n" + 
				"WHERE collections.isPublic\r\n" + 
				"GROUP BY characters.character_id";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		int currentCountLeader = 0;
		String finalPopularCharacter = null;
		while (rs.next() ) {
			if (rs.getInt("count") > currentCountLeader) {
				currentCountLeader = rs.getInt("count");
				finalPopularCharacter = rs.getString("charactername");
			}
		}
		
		return finalPopularCharacter;
	}

	@Override
	public String getMostPopularAuthorInCollections() {
		String sql = "SELECT COUNT(comics.author), comics.author\r\n" + 
				"FROM collections \r\n" + 
				"INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id \r\n" + 
				"INNER JOIN comics ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"WHERE collections.isPublic \r\n" + 
				"GROUP BY comics.comic_id";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		int currentCountLeader = 0;
		String finalPopularAuthor = null;
		while (rs.next() ) {
			if (rs.getInt("count") > currentCountLeader) {
				currentCountLeader = rs.getInt("count");
				finalPopularAuthor = rs.getString("author");
			}
		}
		
		return finalPopularAuthor;
	}

	@Override
	public String getMostPopularArtistInCollections() {
		String sql = "SELECT COUNT(comics.artist), comics.artist\r\n" + 
				"FROM collections \r\n" + 
				"INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id \r\n" + 
				"INNER JOIN comics ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"WHERE collections.isPublic \r\n" + 
				"GROUP BY comics.comic_id";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		int currentCountLeader = 0;
		String finalPopularArtist = null;
		while (rs.next() ) {
			if (rs.getInt("count") > currentCountLeader) {
				currentCountLeader = rs.getInt("count");
				finalPopularArtist = rs.getString("artist");
			}
		}
		
		return finalPopularArtist;
	}

	@Override
	public String getMostPopularCharacterInCollection(int collection_id) {
		String sql = "SELECT COUNT(comic_character.character_id), characters.charactername\r\n" + 
				"FROM collections \r\n" + 
				"INNER JOIN collection_comic ON collection_comic.collection_id = collections.collection_id\r\n" + 
				"INNER JOIN comics ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"INNER JOIN comic_character ON comic_character.comic_id = comics.comic_id\r\n" + 
				"INNER JOIN characters ON comic_character.character_id = characters.character_id \r\n" + 
				"WHERE collections.collection_id = ? \r\n" + 
				"GROUP BY characters.character_id";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collection_id);
		int currentCountLeader = 0;
		String finalPopularCharacter = null;
		while (rs.next() ) {
			if (rs.getInt("count") > currentCountLeader) {
				currentCountLeader = rs.getInt("count");
				finalPopularCharacter = rs.getString("charactername");
			}
		}
		
		return finalPopularCharacter;
	}

	@Override
	public String getMostPopularAuthorInCollection(int collection_id) {
		String sql = "SELECT count(comics.author), author from comics\r\n" + 
				"JOIN collection_comic ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"WHERE collection_comic.collection_id = ?\r\n" + 
				"GROUP BY comics.author ORDER BY count(comics.author) DESC LIMIT 1;";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collection_id);
		int currentCountLeader = 0;
		String finalPopularArtist = null;
		while (rs.next() ) {
			finalPopularArtist = rs.getString("author");
			
		}
		
		return finalPopularArtist;
	}

	@Override
	public String getMostPopularArtistInCollection(int collection_id) {
		String sql = "SELECT count(comics.artist), artist from comics\r\n" + 
				"JOIN collection_comic ON comics.comic_id = collection_comic.comic_id\r\n" + 
				"WHERE collection_comic.collection_id = ?\r\n" + 
				"GROUP BY comics.artist ORDER BY count(comics.artist) DESC LIMIT 1;";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, collection_id);
		int currentCountLeader = 0;
		String finalPopularArtist = null;
		while (rs.next() ) {
				finalPopularArtist = rs.getString("artist");
			
		}
		
		return finalPopularArtist;
	}

}
