import axios from 'axios';

export default {

createCollection(collection){
    return axios.post('/collections', collection)
},
allCollections(){
    return axios.get('/collections')
},
singleCollection(id){
    return axios.get(`/collections/${id}/comics`)
},

myCollections(username){
    return axios.get(`/collections/user/${username}`)
},

singleCollectionArtistStats(id, artist) {
    return axios.get(`/collections/${id}/artist/${artist}`)
},

singleCollectionAuthorStats(id, author) {
    return axios.get(`/collections/${id}/author/${author}`)
},

allCollectionsArtistStats(artist) {
    return axios.get(`/collections/artist/${artist}`)
},

allCollectionsAuthorStats(author) {
    return axios.get(`/collections/author/${author}`)
},

deleteComicFromCollection(collectionId, comicId) {
    return axios.delete(`/collections/${collectionId}/comic/${comicId}`)
},

getMostPopularAuthor() {
    return axios.get("/collections/author/popular")
},

getMostPopularArtist() {
    return axios.get("/collections/artist/popular")
},

getMostPopularCharacter() {
    return axios.get("/collections/character/popular")
},


getMostPopularAuthorInCollection(collectionId) {
    return axios.get(`/collections/${collectionId}/author/popular`)
},

getMostPopularArtistInCollection(collectionId) {
    return axios.get(`/collections/${collectionId}/artist/popular`)
}

}