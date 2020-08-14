<template>
  <div class="navbarroot">

        <b-navbar class="navbar" toggleable="lg"  >
            <router-link style="text-decoration: none;" class="logo" v-bind:to="{ name: 'home' }">
                <img src="@/assets/threepoint.png" height="50px" width="53px"/>
                THREE-POINT COLLECTIONS
            </router-link>
            

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto" >
                        <!-- <b-nav-form>
                            <b-form-input size="sm" class="mr-sm-2" placeholder="Search (not active)"></b-form-input>
                            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
                        </b-nav-form> -->

                        <b-navbar-nav>
                            <b-nav-item>
                                <router-link
                                style="text-decoration: none;"
                                v-bind:to="{path: `/collections/user/${username}`}" v-if="$store.state.token !=''">
                                My Collections
                                </router-link>
                            </b-nav-item>
                            <b-nav-item>
                                <router-link
                                style="text-decoration: none;"
                                v-bind:to="{ name: 'collections' }">
                                Public Collections
                                </router-link>
                            </b-nav-item>
                            <b-nav-item v-if="$store.state.token === ''">
                                <router-link v-bind:to="{ name: 'login' }" >Login</router-link>
                            </b-nav-item>
                        </b-navbar-nav>

                        <b-nav-item-dropdown right v-if="$store.state.token != ''">
                             <!-- Using 'button-content' slot -->
                            <template v-slot:button-content>
                                <em>{{username}}</em>
                                
                            </template>

                            <b-dropdown-item v-if="$store.state.token != '' && this.$store.state.user.authorities[0].name !='ROLE_PREMIUM'">
                                <router-link 
                                 v-bind:to="{ name: 'upgrade' }">Upgrade to Premium</router-link>
                            </b-dropdown-item>
                            <b-dropdown-item v-if="$store.state.token != ''">
                                <router-link v-bind:to="{ name: 'logout' }" >Logout</router-link>
                            </b-dropdown-item>
                        
                        </b-nav-item-dropdown>
                </b-navbar-nav>

            </b-collapse>
        </b-navbar>

  </div>
</template>

<script>
export default {
    name: 'navbar',
    computed: {
        username() {
            return this.$store.state.user.username
        }
    }

}
</script>

<style>


.logo {
    font-size: x-large;
}

.navbar {
    background-color: #5BC0DE;
    
    
}
.navbarroot {
    font-family: "Bangers";
}

a:visited {
    color: #402039;
    text-decoration: none;
}


a.router-link-active {
    color: #402039;
    /* opacity: .5; */
}

a.router-link-exact-active {
  color: black;
  border-top: solid white 2.5px;
  /* opacity: .5; */
}
</style>