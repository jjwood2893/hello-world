<template>
    <b-col md="4" class="whole-collection">
        
        
        
            <b-card
            v-on:click="goToIndividualCollection"
            img-src="@/assets/comic-background-darker.jpg"
            overlay
            
            >
            <b-card-text align="center" class="collection-name">
                
                {{collection.name}}
                
                
            </b-card-text>

            
            </b-card>

            <br>

    </b-col>
</template>

<script>

export default {
    name: 'collection-card',
    props: ['collection'],
    data(){
        return {
            
            
            
        }
    },
    methods: {
        goToIndividualCollection() {
            this.$router.push({name: 'collection-detail' , params: {id: this.collection.collectionId}});
        }
    }
}
</script>

<style>



.whole-collection {
    text-decoration: none;
    font-family: "Bangers";
    font-size: 2vw;
    cursor: pointer;
}

.collection-name {
    font-size: 3vw;
    color: seashell
    
    
}

@media only screen and (max-width: 767px) {

.collection-name {
    font-size: 10vw;
}

}



</style>