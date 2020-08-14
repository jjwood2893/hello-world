<template>
  <div id="login" class="text-center">
    
    <b-container>
      <b-row align-h="center" class="mt-5">
          <b-col cols="6">
            
            <form class="form-signin" @submit.prevent="login">
              
              <h1 class="h2 mb-3 font-weight-normal">Please Sign In</h1>
              
              <div
                class="alert alert-danger"
                role="alert"
                v-if="invalidCredentials"
              >Invalid username and password!</div>
              <div
                class="alert alert-success"
                role="alert"
                v-if="this.$route.query.registration"
              >Thank you for registering, please sign in.</div>
              
              <div>
                <label for="username" class="sr-only">Username</label>
                <input
                  type="text"
                  id="username"
                  class="form-control"
                  placeholder="Username"
                  v-model="user.username"
                  required
                  autofocus/>
              </div>
              <br/>
              <div>
                <label for="password" class="sr-only">Password</label>
                <input
                  type="password"
                  id="password"
                  class="form-control"
                  placeholder="Password"
                  v-model="user.password"
                  required/>
                </div>
              <br/>
              <router-link :to="{ name: 'register' }">Need an account?</router-link>
              <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
            </form>

          </b-col>
      </b-row>
    </b-container>
    

          
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'login',
  components: {},
  data() {
    return {
      user: {
        username: '',
        password: ''
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token);
            this.$store.commit('SET_USER', response.data.user);
            this.$router.push('/');
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style>

#login {
  font-family: 'Bangers';
  height: 90vh;
}

.form-signin {
  background-color: #5bc0de;
  border: solid 2px black;
  border-radius: 10px;
  padding: 10px;
}

</style>