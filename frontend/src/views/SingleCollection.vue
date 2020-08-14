<template>
  <div>
    
    <b-container>
      
        <b-row align-v="center">
          <b-col class="header-text" cols="6">

            <div class="header">
              {{collection[0].name}}
            </div>

           <div class="sub-header">
            Click on Comics to see their info!
           </div>
           
          </b-col>
            
          <b-col class="bangers">
            Most Popular Author: {{mostPopular.author}} &#40;{{$store.state.authorStat}} comics&#41; <br>
            Most Popular Artist: {{mostPopular.artist}} &#40;{{$store.state.artistStat}} comics&#41; <br>
          </b-col>

          

        </b-row>

        <hr>

        <b-row>
          
          <b-col>
            <add-comic-form 
            v-if="limitNotHit" 
            v-bind:collection="collection[0]">
            </add-comic-form>
            
            <div class="makethisred" v-if="!limitNotHit"> Upgrade to Premium to have more than 5 comics in a collection!</div>
          </b-col>
            
          <b-col>
            <u-p-c-lookup 
              v-if="limitNotHit" 
              v-bind:collection="collection[0]">
              </u-p-c-lookup>
          </b-col>
           
              
            
          
        </b-row>

        <br>

      <b-row class="slide-right">  
        <comic-card
          v-for="comic in $store.state.comics"
          v-bind:key="comic.comicId"
          v-bind:comic="comic"
          v-bind:collection="collection[0]"
          v-bind:showDeleteButton="showDeleteButton"
        ></comic-card>
      </b-row>

      

      <div class="slide-left">
        <b-row align-h="center">
          
        </b-row>
     </div> 
      
    </b-container>

  </div>
</template>



<script>
import ComicCard from "../components/ComicCard.vue";
import CollectionService from "@/services/CollectionService.js";
import addComicForm from "../components/AddComicForm.vue";
import UPCLookup from "../components/UPCLookup.vue";

export default {
  

  data() {
    return {
      collection: '',
      mostPopular: {
          author: 'Jacob Wood',
          authorNum: '',
          artist: 'Randy Proctor',
          artistNum: ''
          
    }
      
    }
  },
  components: {
    ComicCard,
    addComicForm,
    UPCLookup
  },
  created() {
    this.loadCollectionsIntoStore()


    
  },
  computed: {
    limitNotHit() {
      let premiumBoolean = (this.$store.state.user.authorities && this.$store.state.user.authorities[0].name === "ROLE_PREMIUM")
      let count = 0;
      this.$store.state.comics.forEach(() => {
        count+=1;
      });

      return (premiumBoolean || (count < 5))
    }

  },
  methods: {
    retrieveComics() {
      CollectionService
        .singleCollection(this.$route.params.id)
        .then((response) => {
          this.$store.commit("SET_COMICS", response.data);
        });
    },
    getArtistStat() {
        let newArtist = this.mostPopular.artist.replace(/ /g,'_');
        CollectionService.singleCollectionArtistStats(this.collection[0].collectionId, newArtist)
        .then((response) => {
            this.mostPopular.artistNum = response.data
            this.$store.commit("SET_ARTIST_STAT", response.data);
        });
    },
    getAuthorStat() {
        let newAuthor = this.mostPopular.author.replace(/ /g,'_');
        CollectionService.singleCollectionAuthorStats(this.collection[0].collectionId, newAuthor)
        .then((response) => {
            this.mostPopular.authorNum = response.data
            this.$store.commit("SET_AUTHOR_STAT", response.data);
        });
    },
    getCollection(id) {
      this.collection = this.$store.state.collections.filter(collection => {
        return collection.collectionId == id;
      }) 
    },
    loadCollectionsIntoStore() {
      CollectionService
      // NOTE TO REVIEWER: the 'allCollections' method would ideally be a different service that 
      // found a specific collection by name.  Due to time restrictions before demo we have decided
      // to load them all.  We know that this is not how we would scale up if this were an actual
      // live product
        .allCollections()
        .then((response) => {
          this.$store.commit("SET_COLLECTIONS", response.data);
          this.getCollection(this.$route.params.id);
          this.retrieveComics();
          this.popAuthor();
          this.popArtist();
          
        });
    },
    popArtist() {
      CollectionService.getMostPopularArtistInCollection(this.$route.params.id)
        .then(response => {
          this.mostPopular.artist = response.data;
          this.getArtistStat(this.mostPopular.artist);
        })
    },
    popAuthor() {
      CollectionService.getMostPopularAuthorInCollection(this.$route.params.id)
        .then(response => {
          this.mostPopular.author = response.data;
          this.getAuthorStat(this.mostPopular.author);
        })
    }
    
  },
};
</script>




<style>
.header-text {
  font-family: "Bangers";
}

.header {
  font-size: 5vw;
}

.sub-header{
  font-size: 2vw;
}

.bangers{
  font: 2vw bangers;
  text-align: center;
}

 .slide-left{
  animation-duration: 2s;
  animation-name: slide-left;
  font: 1.3em bangers;
}

@keyframes slide-left {
  from {
    margin-left: 100%;
    width: 300%; 
  }

  to {
    margin-left: 0%;
    width: 100%;
  }
}
 .slide-right{
  animation-duration: 1s;
  animation-name: slide-right;
  font: 1em bangers;
}


  @keyframes slide-right {
  from {
    margin-left: -100%;
    width: 300%; 
  }

  to {
    margin-left: 0%;
    width: 100%;
  }
}
  .makethisred{
    color: firebrick;
  }
</style>