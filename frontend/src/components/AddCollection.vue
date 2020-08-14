<template>
  <div>

    <div>
      <button 
      type="button"
      class="btn btn-primary"
      v-if="$store.state.token != ''" 
      v-on:click="showForm = !showForm">
        <span v-show="showForm">Hide Form</span>
        <span v-show="!showForm">Add Collection</span>
      </button>
    </div>

    <form class="form" v-on:submit="addNewCollection" v-if="showForm">
      
      <div>
        <label for="collectionName">Collection Name:</label>
        <input
          id="collectionName"
          name="collectionName"
          type="text"
          placeholder="(50 characters max)"
          v-model="newCollection.name"
          required/>
      </div>

      <div>
        <label>Check to make collection public</label>
        <input id="isPublic" name="isPublic" type="checkbox" v-model="newCollection.publicCollection" />
      </div>

      <div>
        <button type="submit">Submit</button>
      </div>

      <div>
        <button type="reset" v-on:click="resetForm">Reset Form</button>
      </div>

    </form>

  </div>
</template>

<script>
import collectionService from "../services/CollectionService";
export default {
  name: "add-collection",
  data() {
    return {
      newCollection: {
        name: "",
        publicCollection: false,
      },
      showForm: false,
    };
  },
  methods: {
    resetForm() {
      this.newCollection = {};
    },
    addNewCollection() {
      collectionService
        .createCollection(this.newCollection)
        .then((response) => {
          if (response.status === 201) {
            this.resetForm();
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
          " collection. Response received was '" +
          error.response.statusText +
          "'.";
      } else if (error.request) {
        this.errorMsg = "Error " + verb + " collection. Server could not be reached.";
      } else {
        this.errorMsg =
          "Error " + verb + " collection. Request could not be created.";
      }
    },
  },
};
</script>

<style>

.form {
  display: inline-block;
  border: solid 2.5px black;
  border-radius: 5px;
  background-color: skyblue;
}

.form div {
  margin: 5px
}

</style>