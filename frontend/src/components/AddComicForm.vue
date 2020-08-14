<template>
 <b-col>
   <div>
      <button type="button" class="btn btn-primary" v-if="$store.state.token != '' && 
      this.$store.state.user.id == collection.userId" 
      v-on:click="showForm = !showForm">
        <span v-show="showForm">Hide Form</span>
        <span v-show="!showForm">Add Comic Manually</span>
      </button>
    </div>
      
    <form class="form" v-on:submit.prevent="addNewComic" v-if="showForm">
      
      <div>
        <label for="comicName"></label>
        <input
          id="comicName"
          name="comicName"
          type="text"
          placeholder="Title (50 characters max)"
          v-model="newComic.title"
          required/>
      </div>

      <div>
        <label for="issueNumber"></label>
        <input
          id="issueNumber"
          name="issueNumber"
          type="number"
          min="0"
          placeholder="Issue Number"
          v-model="newComic.issue"
          required/>
      </div>

      <div>
        <label for="author"></label>
        <input
          id="author"
          name="author"
          type="text"
          placeholder="Author"
          v-model="newComic.author"
          required/>
      </div>

      <div>
        <label for="artist"></label>
        <input
          id="artist"
          name="artist"
          type="text"
          placeholder="Artist"
          v-model="newComic.artist"
          required/>
      </div>

      <div>
        <label for="publisher"></label>
        <input
          id="publisher"
          name="publisher"
          type="text"
          placeholder="Publisher"
          v-model="newComic.publisher"
          required/>
      </div>

  
      <div>
        <button type="submit" class="btn btn-primary">Submit</button>
      
        <button type="reset" class="btn btn-danger" v-on:click="resetForm">Reset Form</button>
      </div>

    </form>
</b-col>
  
</template>

<script>
import ComicService from '../services/ComicService';

export default {
name: 'add-comic-form',
props: ['collection'],
data(){
    return{
        showForm: false,
        newComic:{
            title: '',
            issue: '',
            author: '',
            artist: '',
            publisher: '',
            coverUrl: ''
        }
    }

},
methods:{
    addNewComic(){
        ComicService
        .addComic(this.collection.collectionId, this.newComic)
        .then((response) => {if (response.status === 201) {
            this.resetForm();
            location.reload();
          }
        })
        .catch((error) => {
          this.handleErrorResponse(error, "adding");
        });
    },
    handleErrorResponse(error, verb) {
      if (error.response) {
        this.errorMsg =
          "Error " +
          verb +
          " comic. Response received was '" +
          error.response.statusText +
          "'.";
      } else if (error.request) {
        this.errorMsg = "Error " + verb + " comic. Server could not be reached.";
      } else {
        this.errorMsg =
          "Error " + verb + " comic. Request could not be added.";
      }
    
    },

    resetForm(){
        this.newComic = {}
    },

}
}
</script>

<style>

</style>