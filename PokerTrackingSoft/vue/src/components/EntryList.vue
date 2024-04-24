<template>
    <div class="entry-container">
        <DisplaySession v-bind:key="entry.id" v-for="entry in entries" v-bind:entry="entry"/>
    </div>
</template>

<script>
import DisplaySession from '../components/DisplaySession.vue'

export default {
  data() {
        return {
            entries: this.$store.state.entryList,
        }
    },
    components: {
        DisplaySession
    },
  created() {
        this.$store.commit("UPDATE_ENTRIES");
    },
};

</script>

<style>

</style>