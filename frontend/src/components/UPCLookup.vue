<template>
  <b-col>

    <div>
      <button
        type="button"
        class="btn btn-primary"
        v-if="$store.state.token != '' && 
        this.$store.state.user.id == collection.userId"
        v-on:click="showForm = !showForm">
        <span v-show="showForm">Hide Form</span>
        <span v-show="!showForm">Add via UPC with Marvel API</span>
      </button>
    </div>

    <form class="form" v-on:submit.prevent="addNewComicToData"  v-if="showForm">
      <div>
        <label for="upc"></label>
        <input 
        id="upc"
        name="upc"
        type="text"
        placeholder="UPC"
        v-model="upc"/>
      </div>

      <div>
        <button type="submit" class="btn btn-primary">
          Submit
        </button>
        <button 
        type="reset" 
        class="btn btn-danger"
        v-on:click="resetForm">
          Reset
        </button>
      </div>
      


    </form>

  </b-col>

</template>

<script>
import ComicService from '../services/ComicService';

export default {
   

    name: 'upc-lookup',
    props: ["collection"],
    data() {
      return {
        showForm: false,
        upc: '',
        newComic:{
            title: '',
            issue: '',
            author: '',
            artist: '',
            publisher: ''
        }
      }
    },
    methods: {
      addNewComicToData() {
        ComicService
          .getComicByUPC(this.upc)
          .then((response) => {
            this.newComic = response.data[0];
            this.addNewComicToServer();
          })
      },
      addNewComicToServer(){
        ComicService
        .addComic(this.collection.collectionId, this.newComic)
        .then((response) => {if (response.status == 201) {
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
    }
    }
};
</script>

<style>
</style>