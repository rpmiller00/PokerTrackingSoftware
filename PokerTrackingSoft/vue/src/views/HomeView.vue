<template>
  <div class="spacer"></div>
  <div class="home">
    <div class="intro">
      <h2>Welcome to Poker Tracker</h2>
    </div>
    <div class="box">
      <p>Please sign in to View and Track Sessions</p>
    </div>

  </div>
  <div class="image">

  </div>

</template>

<script>

export default {
  created() {
    this.$store.commit("UPDATE_ENTRIES");
  },
};
</script>

<style scoped>
.spacer {
  height: .5rem;

}
.home {
  text-align: center;
  font-size: 2rem;
}

.image {
  height: 100vh;
  background-image: url('..\assets\openart-image_X_PIo-cK_1715301203833_raw.jpg');
  background-size: cover;
  /* Cover the entire background */
  background-position: center;
  /* Center the background image */
  background-repeat: no-repeat;
  /* Do not repeat the background image */
}


</style>
