<template>
  <div>
      <button v-if="$store.state.token != null" v-on:click="upgradeUser">Upgrade</button>
      <div v-show="isSuccesful">SUCCESS!</div>
      <div v-show="errorMessage != ''">{{errorMessage}}</div>
  </div>
</template>

<script>
import AuthService from '../services/AuthService'

export default {
    data() {
        return{
            isSuccesful: false,
            errorMessage:''
        }
    },
    methods: {
        upgradeUser() {
            AuthService.upgrade(this.$store.state.user).then((response) => {
                if(response.status===200) {
                    this.isSuccesful=true;
                }
            })
            .catch(error => {
                this.errorMessage = error.status;
            })

        }
    }

}
</script>

<style>

</style>