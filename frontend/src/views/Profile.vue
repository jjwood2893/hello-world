<template>


  <b-container>

      <b-row align-v="center">

          <b-col class="header-text">
            {{$store.state.user.username}}'s Collections
          </b-col>

            <b-col cols="4">
                <add-collection></add-collection>
            </b-col>
     </b-row>

        <hr>

     <b-row>
        <collection-card v-for="collection in $store.state.collections"
            v-bind:key="collection.id"
            v-bind:collection="collection">   
        </collection-card>
     </b-row>
            
            
  </b-container>
      
  
</template>

<script>
import CollectionCard from '@/components/CollectionCard'
import CollectionService from '../services/CollectionService'
import AddCollection from '@/components/AddCollection'


export default {
    name: "profile",
    created(){
        this.retrieveMyCollections(this.$store.state.user.username)
    },
methods: {
retrieveMyCollections(username){
    CollectionService.myCollections(username).then(response=> {
        this.$store.commit("SET_COLLECTIONS", response.data)
    } );
}
},

components:{
    CollectionCard,
    AddCollection
}
}
</script>

<style>

.header-text {
  font-family: "Bangers";
  font-size:5vw;
}

</style>     