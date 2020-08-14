import axios from 'axios';

export default {

addComic(id, comic){
    return axios.post(`/collections/${id}/add`, comic)


},
getComicByUPC(upc){
    return axios.get(`/comics/marvel/${upc}`)
}
}