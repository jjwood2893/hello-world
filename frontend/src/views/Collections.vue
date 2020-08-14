<template>
  <div class="collections">
    
    <b-container>
      
      
        
        <hr>
      
      <b-row>
        <collection-card
          class="collection-card"
          v-for="collection in $store.state.collections"
          v-bind:key="collection.id"
          v-bind:collection="collection"
        ></collection-card>
      </b-row>

    </b-container>
    

  </div>
</template>

<script>
import collectionService from "../services/CollectionService";
// import AddCollection from '@/components/AddCollection'
import CollectionCard from "@/components/CollectionCard";

export default {
  data() {
    return {
        artist: 'Mike Del Mundo',
        author: 'Nick Spencer'
    };
  }, //Need to implement data for collections.
  created() {
    this.retrieveCollections();
    this.getArtistStat();
    this.getAuthorStat();
  },
  methods: {
    retrieveCollections() {
      collectionService.allCollections().then((response) => {
        this.$store.commit("SET_COLLECTIONS", response.data);
      });
    },
    seeCollectionDetails(collectionID) {
      this.$router.push({
        name: "collection-detail",
        params: { id: collectionID },
      });
    },
    getArtistStat() {
        let newArtist = this.artist.replace(/ /g,'_');
        collectionService.allCollectionsArtistStats(newArtist).then((response) => {
            this.$store.commit("SET_ARTIST_STAT", response.data);
        });
    },
    getAuthorStat() {
        let newAuthor = this.author.replace(/ /g,'_');
        collectionService.allCollectionsAuthorStats(newAuthor).then((response) => {
            this.$store.commit("SET_AUTHOR_STAT", response.data);
        });
    }
  },
  components: {
    //    AddCollection,
    CollectionCard,
  },
};
</script>

<style>
 .slide-left{
  animation-duration: 2s;
  animation-name: slide-left;
  font: 1.3em bangers;
}
</style>