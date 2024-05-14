<template>

    <div class="nav">
        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>
        </div>
        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'sessions' }" v-if="checkCurrentUser()">View
                Sessions</router-link>
        </div>
        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'logsessions' }" v-if="checkCurrentUser()">Log
                Session</router-link>
        </div>

        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'login' }" v-if="$store.state.token === ''">Login</router-link>
        </div>
        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'register' }">Register</router-link>
        </div>
        <div class = "linkdiv">
            <router-link class="link" v-bind:to="{ name: 'logout' }"
                v-if="$store.state.token != ''">Logout</router-link>
        </div>
    </div>
</template>

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
};
</script>

<style>
.nav {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
}

.link {
  color: black;
  text-decoration: none;
  font-size: 1.7rem;
  font-weight: 450;
}
.linkdiv {
  padding: 3px;
}
.link:hover {
  color: red;
}

</style>
