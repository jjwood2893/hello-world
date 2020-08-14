<template>
  <div class="home">

    

    <b-container>
      <b-row>
        <b-col>

          <carousel-slide></carousel-slide>

        </b-col>
        
        
      </b-row>
      <hr>
      <b-row>
        <b-col class="leaderboard">
          <span class="leaderboardheader">LEADERBOARD</span> <br>
          Most Popular Artist:<span class="leader-stat"> {{mostPopular.artist}}</span> <br>
          Most Popular Author:<span class="leader-stat"> {{mostPopular.author}}</span> <br>
          Most Popular Character:<span class="leader-stat"> {{mostPopular.character}}</span>
        </b-col>
        <b-col>
          Designed by: <br/>
          Rory Johnson- Favorite character is Daredevil <br/>
          Randy Proctor- Favorite character is Judge Dredd <br/>
          Ben Hines- Favorite character is Nightcrawler <br/>
          Brandon Moore- Favorite character is Wolverine <br/>
          Jacob Wood- Favorite character is Spawn
        </b-col>
      </b-row>
    </b-container>

    

    

  </div>
</template>

<script>
import CarouselSlide from '../components/CarouselSlide.vue';
import CollectionService from '../services/CollectionService';


export default {
  data() {
    return {
        mostPopular: {
          artist: 'Jacob Wood',
          author: 'Randy Proctor',
          character: 'Squirrel Girl'
    }
  }
  },
  methods: {
    popArtist() {
      CollectionService.getMostPopularArtist()
        .then(response => {
          this.mostPopular.artist = response.data;
        })
    },
    popAuthor() {
      CollectionService.getMostPopularAuthor()
        .then(response => {
          this.mostPopular.author = response.data;
        })
    },
    popCharacter() {
      CollectionService.getMostPopularCharacter()
        .then(response => {
          this.mostPopular.character = response.data;
        })
    },
  },
  created() {
    this.popArtist(),
    this.popAuthor(),
    this.popCharacter()
  },
  components: {
    CarouselSlide
  },
  name: 'home'
};
</script>

<style>


.home {
  font-family: 'Bangers';
  
}

p {
  font-size: 3vw;
}

.headline {
  font-size: 5vw;
}

.welcome-message {
  
  background: #FCFC62;
  border: solid 2.5px black;
  border-radius: 10px;
  text-align: center;
  margin: auto;
 
}

.leaderboard {
  font-size: 1.75vw;
  
}

.leaderboardheader {
  font-size: 2.25vw;
}

.leader-stat {
  color:firebrick
}





</style>
