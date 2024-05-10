<!------------------------- TEMPLATE -------------------------------->
<template>
  <div id="capstone-app">
    <!-- Links / Nav Bar / Header -->

    <div class="nav">
      <div class="left">
        <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link class="link" v-bind:to="{ name: 'sessions' }" v-if="checkCurrentUser()">View Sessions</router-link>
        <router-link class="link" v-bind:to="{ name: 'logsessions' }"  v-if="checkCurrentUser()">Log Session</router-link>

      </div>


      <div class="right">
        <router-link class="link" v-bind:to="{ name: 'login' }" v-if="$store.state.token === ''">Login</router-link>
        <router-link class="link" v-bind:to="{ name: 'register' }">Register</router-link>
        <router-link class="link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>

    </div>

    <router-view id="views" />

  </div>
</template>

<!------------------------- SCRIPT ---------------------------------->
<script>

export default {
  methods: {
    checkCurrentUser() {
      if (this.$store.state.token != '') {

        return true;
      }
      else {
        return false;
      }
    }
  },
}



</script>


<!------------------------- STYLE ---------------------------------->
<style scoped>




.link {
  color: rgb(0, 0, 0);
  text-decoration: none;
  font-size: 1.7rem;
  padding-left: 10px;
  padding-right: 10px;
  font-weight: 450;

}
.nav {
  display: flex;
  justify-content: space-between;
}
</style>