<template>

  <body>
    <main>
        <h2>Welcome to Poker Tracker</h2>
        <p>Please sign in to View and Track Sessions</p>
    </main>
    <div class="image"></div>
    <Navigation class ="nav"/>
  </body>


</template>


<script>
import Navigation from '../components/Navigation.vue';
export default {
  components: {
    Navigation
  },
  created() {
    this.$store.commit("UPDATE_ENTRIES");
  },
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

<style scoped>
.spacer {
  height: .5rem;

}

body {
  display: grid;
  margin: 0;
  padding: 0;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
    "image image home"
    "image image nav";
}

main {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  grid-area: home;
}

.nav {
  grid-area: nav;
}
.image {
  height: 100vh;
  background-image: url(..\assets\60a7985ad1b2c054d81a4fde73b243b8.jpeg);
  background-size: cover;

  /* Cover the entire background */
  background-position: center;
  /* Center the background image */
  background-repeat: no-repeat;
  /* Do not repeat the background image */
  grid-area: image;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5);
}
</style>
