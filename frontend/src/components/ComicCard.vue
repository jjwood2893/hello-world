<template>
    
        <b-col md="4" no-body="true" class="whole-comic">

            <b-card
            v-if="frontFacing"
            v-on:click="flipCard"
            v-bind:img-src="imageUrl"
            overlay>
            </b-card>

            <b-card
              class="back-side"
              v-if="!frontFacing"
              v-on:click="flipCard"
              v-bind:img-src="imageUrl"
              overlay>

              <b-row>
                
              </b-row>
              <div class="back-content">
                <b-card-text class="stats-font">
                  <b-row class="stats">
                    Title:<br> {{comic.title}} <br><br>
                    Issue #:<br> {{comic.issue}} <br><br>
                    Author:<br> {{comic.author}} <br><br>
                    Artist:<br> {{comic.artist}} <br><br>
                    Publisher:<br> {{comic.publisher}}
                  </b-row>
                </b-card-text>

                  <b-row align-v="center">
                    <button 
                      type="button" 
                      class="btn btn-danger" 
                      v-on:click="deleteComic()"
                      v-if="$store.state.token != '' && 
                      this.$store.state.user.id == collection.userId">
                        Delete Comic
                    </button>
                  </b-row>
              </div>
              
                
            </b-card>

            <br><br>

        </b-col>

</template>

<script>
import CollectionService from '../services/CollectionService';
import samplecomiccover from '../assets/sample-comic-cover.png';

export default {
  name: "comic-card",
  props: ["comic", "collection"],
  data() {
    return {
      frontFacing: 'true'
    };
  },
  methods: {
    flipCard() {
      this.frontFacing = !this.frontFacing;
    },
    deleteComic() {
        if (confirm('Are you sure?'))
            CollectionService.deleteComicFromCollection(this.collection.collectionId, this.comic.comicId)
            .then((response) => {if (response.status === 200) {
                    this.$router.go();
                }
            });
    },
    frontOrBack() {
      if (this.comic.coverUrl === null || this.comic.coverUrl === '') {
        this.frontFacing = false;
      } else return this.frontFacing = true;
    }

  },
  computed: {
    imageUrl() {
      if (this.comic.coverUrl === null || this.comic.coverUrl === '') {
        return samplecomiccover;
      } else return this.comic.coverUrl;
    },
    
  },
  created() {
    this.frontOrBack();
  }
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Special+Elite&display=swap');

.back-content {
  background: rgba(0,0,0,.5);
  color: seashell;
  border: solid black 2.5px;
  border-radius: 10px;
  padding: 25px;
}

.stats {
  font-size: 60%;
}

.stats-font {
  
  font-family: "Special Elite";
  font-size:calc(12px + 1vw);
}

.back-content {
  opacity: 1;
}

.whole-comic {
  text-decoration: none;
  font-family: "Bangers";
  font-size: 1.4vw;
  cursor: pointer;
  
}
</style>