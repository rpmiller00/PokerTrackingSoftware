<template>
  <div class="home">
    <div class="spacer"></div>
    <h2>Welcome to Poker Tracker</h2>
    <div class="box">
      <h3>What We Do</h3>
      <p>We allow you to track your poker sessions </p>
      <p>Please sign in to View and Track Sessions</p>
    </div>
    <div class="spacer-bot"></div>

  </div>
</template>

<script>
import EntryList from '../components/EntryList.vue'

export default {
  created() {
        this.$store.commit("UPDATE_ENTRIES");
    },
};
</script>

<style scoped>
* {

  background-color: transparent;

}

.spacer {
  height: 4rem;
  background-image: linear-gradient(120deg, #4e54c8, #acadbd);
}

.spacer-bot {
  height: 5rem;
}


div>h2 {
  color: rgb(0, 0, 0);
  text-align: center;
  font-size: 22.3px;
  padding-bottom: 10px;
  font-weight: bolder;
}

.box p {
  color: rgb(0, 0, 0);
  font-weight: bolder;
  text-align: center;
}

.box h3 {
  color: rgb(0, 0, 0);
  font-size: 35px;
  text-align: center;
  padding-top: 10px;
}

.box {
  width: 50%;
  margin: auto;
  border-radius: 10px;
}

.home {
  background-image: linear-gradient(120deg, #4e54c8, #acadbd);
  height:100vh;
}


</style>
