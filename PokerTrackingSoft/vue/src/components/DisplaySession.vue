<template>
    <div class="entry-card">
        <p>Entry #: {{ entry.entryId }}</p>
        <p>Win/Loss: ${{ entry.amount }}</p>
        <p>Stake: {{ entry.gameSize }}</p>
        <p>Game: {{ entry.gameType }}</p>
        <p>Location: {{ entry.location }}</p>
        <button v-on:click="deleteEntry()">Delete</button>
    </div>

</template>

<script>
import EntryService from '../services/EntryService.js';
export default {
    props: {
        entry: {
            type: Object,
            required: true,
        },
    },
    methods: {

        deleteEntry(){
            EntryService.deleteEntry(this.entry.entryId);
            this.$router.go(0);
        }

    }

};

</script>

<style>
.entry-card {
    border: 2px solid black;
    margin: 10px;
    padding: 10px;
    background-color: gray;
}
</style>