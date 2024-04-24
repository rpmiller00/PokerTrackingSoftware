<template>
    <div class="spacer"></div>
    <div>
        <EntryList/>
    </div>
</template>

<script>
import EntryList from '../components/EntryList.vue'
export default {
    components: {
        EntryList,
    },
  created() {
        this.$store.commit("UPDATE_ENTRIES");
    },

}
</script>

<style>
.spacer {
  background-color: rgb(255, 255, 255);
  opacity: .25;
  height: 4rem;
}

</style>
