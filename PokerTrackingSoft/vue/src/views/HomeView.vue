<template>
  <div class="home">
    <div class="spacer"></div>
    <h2>Welcome to Poker Tracker</h2>
    <div class="box">
      <h3>What We Do</h3>
      <p>We allow you to track your poker sessions </p>
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
  background-color: rgb(255, 255, 255);
  opacity: .25;
  height: 4rem;
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
  text-align: left;
}

.box h3 {
  color: rgb(0, 0, 0);
  font-size: 35px;
  text-align: left;
  padding-top: 10px;
}

.box {
  background-color: #E8E2DD;
  opacity: .8;
  width: 50%;
  margin: auto;
  border-radius: 10px;
  border-left: 10px solid #E8E2DD;
  border-bottom: .5px solid #E8E2DD;
}


</style>
